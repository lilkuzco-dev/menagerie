# Menagerie full-sweep audit — 0.4.2

Scope: every species, every asset path, every spawn rule. Triggered by two field
reports from the live server — a hippo rendering as the missing-texture checkerboard,
and a hippo spawning on the ocean floor at `[1746, 61, 480]`. The brief was to make the
whole *class* of bug extinct rather than patch two symptoms, so the mod was treated as
guilty until proven innocent, species by species.

Date: 2026-08-16. Environment: dev dedicated server driven by the Fabric client
gametest harness (MC 26.2, loader 0.19.3, Fabric API 0.157.0), plus static audits run
against the shipped jar and against Terralith 26.2 v2.6.4's real biome tags.

---

## Summary

| # | Finding | Class | Status |
|---|---|---|---|
| 1 | **Every animal renders as a checkerboard on a dedicated server.** Species live in a datapack, so a remote client's `SpeciesRegistry` is empty and `texture()` fell through to a fallback — which pointed at `minecraft:textures/entity/pig/temperate_pig.png`, a path that does not exist in 26.2 (vanilla ships `pig/pig_temperate.png`). | correctness | **FIXED** |
| 2 | **Hippos and crocodiles could spawn at any depth, including the sea floor.** `SpawnPlacementTypes.NO_RESTRICTIONS` performs no ground/depth/light test, and the predicate accepted *any* position containing water. | correctness | **FIXED** |
| 3 | **Babies rendered at a quarter of adult size.** `baby_scale: 0.5` multiplied the SCALE attribute on top of vanilla's `BabyModelTransform`, which already halves the body. | correctness | **FIXED** |
| 4 | **Seven species were invisible to Terralith.** Raw biome ids (`minecraft:swamp`, `minecraft:desert`, four peak ids) match no Terralith biome, so on this server those species had far less habitat than intended. | coverage | **FIXED** |
| 5 | **Ecology mismatches:** gorillas in windswept hills and a snowy conifer grove; snow leopards in volcanic craters and tropical jungle; crocodiles and hippos in frozen rivers and ice marsh; reptiles in snowy badlands. | correctness | **FIXED** |
| 6 | Asset resolution across all 16 species: models, textures, variants, baby forms, overlays, sounds, guide icons. | — | **PASS**, 0 gaps |
| 7 | Two build-time validators added; both gate `gradlew build` and assert their own anchors. | — | **GREEN** |

Nothing in the audit was left unfixed. Two items are recorded as reviewed-no-change
with reasoning (see *Deviations*).

---

## Part 1 — Asset resolution sweep

`tools/asset-audit.py` statically enumerates the **requestable set**: every asset path
the mod can ask for at runtime, from

* species JSON — `texture`, `textures[]` (fur tables), `variant_rolls.*.texture`
* the field guide — `textures/gui/guide/<entity>_<species>[_silhouette].png`
* Java string literals, including constant-prefix concatenation (`T + "gorilla/x.png"`)
* declared numbered families (the lion's 4 eye variants, rolled `0..3`)
* `sounds.json` file references, registered `SoundEvent`s, and subtitle lang keys
* block/item models, blockstates and their texture maps

…then diffs it against the **built jar**, catching three failure classes: (a)
requested-but-missing, (b) shipped-but-unreachable, (c) index-scheme mismatch.

### The matrix

| species | textures requested | variants | guide icon | verdict |
|---|---|---|---|---|
| `crocodile_nile` | 1 | 0 | `crocodile_nile` | PASS |
| `crocodile_saltwater` | 1 | 0 | `crocodile_saltwater` | PASS |
| `gorilla_lowland` | 4 | 1 | `gorilla_lowland` | PASS |
| `gorilla_mountain` | 3 | 1 | `gorilla_mountain` | PASS |
| `grizzly_black` | 1 | 0 | `grizzly_black` | PASS |
| `grizzly_taiga` | 1 | 0 | `grizzly_grizzly` | PASS |
| `hippo_river` | 1 | 0 | `hippo_river` | PASS |
| `hippo_swamp` | 1 | 0 | `hippo_swamp` | PASS |
| `leopard_jungle` | 1 | 0 | `leopard_leopard` | PASS |
| `leopard_snow` | 1 | 0 | `leopard_snow` | PASS |
| `lion_barbary` | 7 | 0 | `lion_barbary` | PASS |
| `lion_savanna` | 8 | 0 | `lion_savanna` | PASS |
| `snake_python` | 1 | 0 | `snake_python` | PASS |
| `snake_viper` | 1 | 0 | `snake_viper` | PASS |
| `tortoise_savanna` | 1 | 0 | `tortoise_savanna` | PASS |
| `vulture_griffon` | 1 | 0 | `vulture_griffon` | PASS |

Overlays: gorilla silverback saddle (`gorilla/silverback.png`) — PASS. Lion eye layer
(`lion/eye/{left,right}eye_0..3.png`, 8 files) — PASS, run contiguous and 0-indexed,
matching `LionEyeLayer.VARIANTS = 4`. Sounds: 40 registered events, 40 `sounds.json`
entries, 40 subtitles, all 21 shipped `.ogg` files reachable — PASS, no orphans in
either direction. Baby forms are the adult mesh through `BabyModelTransform` and
request no additional textures — PASS by construction.

**Result: 108 distinct paths in the requestable set, 0 failures in all three classes.**

### What the sweep actually found

The hippo's textures were never missing. `hippo/river.png` and `hippo/swamp.png` are
present and valid in the shipped 0.4.1 jar, and every other menagerie-owned path
resolved too. Running the new audit against the **shipped 0.4.1 jar** produced exactly
one failure — and it was the bug:

```
FAIL [a: requested-but-missing] requested-but-missing (vanilla):
     minecraft:textures/entity/pig/temperate_pig.png
     requested by: MenagerieRenderState.java:9 literal; SpeciesMob.java:139 literal
     no such path in the Minecraft assets
```

So the working hypothesis in the brief — that Untamed Wilds' 1-indexed
`common_1/2/3.png` scheme had left the renderer asking for a base filename that does
not exist — is **not** what happened here. The transplanted art was fully and correctly
imported. The real chain is:

1. Species definitions are a **datapack** (`data/menagerie/species/*.json`), loaded by a
   `PackType.SERVER_DATA` reload listener.
2. A client connected to a **dedicated** server never loads `data/`. Its
   `SpeciesRegistry` is empty, so `species()` returned null.
3. `texture()` then returned its fallback: `textures/entity/pig/temperate_pig.png`.
4. That path does not exist in 26.2 — vanilla ships `pig/pig_temperate.png`. The two
   words are transposed. Minecraft draws the missing-texture checkerboard.

This affected **every animal, not just the hippo** — the hippo is simply what walked
past. It never showed up in testing because the existing battery screenshots come from
a *client gametest*, whose integrated server shares a JVM with the renderer, so the
registry was populated and the fallback never ran.

### The fix

The client is no longer asked to resolve anything it may not know:

* `SpeciesMob` gained a synced `TEXTURE` entity-data field. The **server** resolves the
  skin (species → variant roll → per-individual fur table) and publishes it;
  `texture()` reads the synced value. Republished on spawn, on variant roll, on load,
  and on datapack reload, so existing animals in the live world repair themselves the
  first time they tick after the update.
* `GorillaEntity` now overrides `resolveTexture()` rather than `texture()`, so its fur
  table feeds the same synced value.
* Both fallbacks (`SpeciesMob` and `MenagerieRenderState`) now point at a **shipped**
  `menagerie:textures/entity/missing.png` — deliberately magenta/amber rather than
  vanilla's magenta/black, so a Menagerie fallback can never again be mistaken for, or
  hide behind, the vanilla checkerboard. The audit proves that file ships.

---

## Part 2 — Visual verification

A new battery, `MenagerieMultiplayerRenderTest`, drives a **real dedicated server** over
a **real client connection** (`worldBuilder().createServer()` → `connect()`) rather than
singleplayer — the environment where the bug lives. It walks the entire roster: 16
species × {adult, baby} + 2 forced albino variant rolls = **34 subjects**, screenshotting
each row and then reading, on the client, what the client actually resolved.

Per subject it asserts:

1. the synced skin string is **non-empty** — the skin came over the wire rather than
   being re-derived from a registry a remote client does not have;
2. the rendered texture is **not** the MISSING placeholder;
3. the rendered texture **equals** the synced one;
4. that texture is one this species is allowed to wear (fur tables accept any member).

**Result: 34/34 PASS.** Screenshots in `build/run-gametest/screenshots/mp_row_*.png`.

Eyeball pass over the captured rows: no checkerboards; every species wears its own
skin (nile vs saltwater crocodile, python vs viper, savanna vs barbary lion, black vs
grizzly bear all visibly distinct); no stretched or smeared UVs, i.e. no texture landing
on another animal's model; both albino gorillas show the cream coat with pink face,
confirming the rare coat still outranks the fur table over the network.

### Honest limits of this harness

The gametest's "dedicated" server runs **in the same JVM** as the client, so the static
`SpeciesRegistry` is shared and the client-side registry is *not* empty there — unlike a
real remote client. The test's first draft asserted the registry was empty and
**failed**, which is how this was caught rather than assumed. The test therefore does
not assert on the registry; it asserts the mechanism that actually fixes the bug (the
skin arriving as synced entity data), and prints the registry size with a note so the
next person does not "fix" the assertion back in. Final confirmation on a genuinely
remote client is Jesse's, after deployment.

### Baby size (raised mid-audit)

Babies were rendering at roughly a **quarter** of adult size. Two shrinks were stacking:

* the client bakes each baby layer as `Model.createBodyLayer().apply(BABY)` where
  `BABY = new BabyModelTransform(Set.of("head"))`. Read out of 26.2's bytecode, that
  resolves to `babyBodyScale = 2.0` (a divisor → body at ½) with `scaleHead = false` —
  the classic big-head baby silhouette. Vanilla babies get their size from this alone;
* on top of it, `applySpeciesAttributes` multiplied the SCALE attribute by
  `breeding.baby_scale`, which every species set to `0.5`.

½ × ½ ≈ ¼, which is what the screenshots showed. The SCALE attribute also scales the
hitbox, and vanilla *separately* halves baby dimensions via `getAgeScale()`, so the
collision box was over-shrunk too.

`baby_scale` now defaults to **1.0 = vanilla baby proportions** and all 13 breeding
species were updated. It survives as a real per-species knob, but 1.0 is the neutral
value and values below it stack a second shrink. The battery now includes a calibration
shot placing a Menagerie adult+calf beside a vanilla cow+calf
(`mp_baby_size_vs_vanilla_cow.png`) and asserts every baby carries SCALE ≥ 0.95;
measured 1.0. Visually the gorilla calf now matches the cow calf's proportion to its
adult.

---

## Part 3 — Spawn ecology audit

`tools/spawn-lint.py` dumps and checks every species' spawn rules, and (with
`--resolve`) expands every biome tag through the real vanilla + Fabric-API + Terralith
tag data. Full resolved coverage per species: **`docs/biome-coverage.md`**.

### The ocean-floor hippo

Not a stray biome tag. `hippo_river` was `#minecraft:is_river`, which is correct and
contains no ocean. The defect was in placement:

```java
SpawnPlacements.register(HIPPO, SpawnPlacementTypes.NO_RESTRICTIONS, ...)
```

`NO_RESTRICTIONS` performs **no** ground, depth or light test of its own, and the
predicate's in-water branch skipped the ground and light checks too, returning only the
species/biome gate. Vanilla's spawner offers candidate positions down the whole chunk
column, so *every submerged position in a river-tagged column was accepted* — including
the bottom. A river mouth meeting the sea is exactly where that reads as "hippo on the
ocean floor".

Replaced with a real waterline test (`WaterlineSpawn`): a wading animal must find open
air within 3 blocks **above** and a sturdy floor within 3 blocks **below**. Deep water
fails both halves, so the sea floor and the bottom of a deep river are excluded by
construction rather than by hoping the biome list never overlaps one. Dry-land
positions fall through to the normal ground+light rules, which they previously bypassed.

### Ecology table

| species | biomes (after) | verdict |
|---|---|---|
| `hippo_river` | `#is_river` − frozen_river | FIXED — excluded frozen rivers |
| `hippo_swamp` | `#c:is_swamp` − ice_marsh | FIXED — was raw ids (no Terralith); excluded frozen swamp |
| `crocodile_nile` | `#is_river`, `#c:is_swamp` − frozen_river, ice_marsh | FIXED — Nile crocs are a river animal and had no rivers |
| `crocodile_saltwater` | `#c:is_swamp`, `#is_beach` − snowy_beach, ice_marsh | FIXED — estuary/beach animal, gained beaches |
| `gorilla_lowland` | `#is_jungle` − bamboo/montane jungle | FIXED — cedes montane jungle to `mountain` |
| `gorilla_mountain` | bamboo_jungle, terralith jungle_mountains + rocky_jungle | FIXED — was `#is_hill` + `grove`: windswept hills and a **snowy** conifer forest |
| `leopard_snow` | `#is_mountain`, grove − meadow, cherry_grove, volcanic_crater, volcanic_peaks, caldera, ashen_savanna, savanna_slopes, tropical_jungle, painted_mountains | FIXED — was 4 raw peak ids (no Terralith); excluded Terralith's *hot* peaks |
| `leopard_jungle` | `#is_jungle` | reviewed, no change (see Deviations) |
| `lion_savanna` | `#is_savanna` | PASS |
| `lion_barbary` | `#is_badlands` | PASS — North African stand-in |
| `grizzly_black` | `#is_forest` | PASS |
| `grizzly_taiga` | `#is_taiga` | PASS (see Deviations) |
| `tortoise_savanna` | `#is_savanna`, `#is_badlands`, `#c:is_desert`, `#is_beach` − snowy_beach, snowy_badlands | FIXED — gained the arid+beach families the spec calls for |
| `snake_python` | `#is_jungle` | PASS |
| `snake_viper` | `#c:is_desert`, `#is_badlands` − snowy_badlands | FIXED — desert was a raw id |
| `vulture_griffon` | `#c:is_desert`, `#is_badlands`, `#is_savanna` | FIXED — desert was a raw id |

No species carries an ocean or deep-ocean tag; the linter now makes that impossible.

### Terralith reality

Terralith **does** add its biomes to the vanilla `minecraft:is_*` tags (via
`#terralith:reference/*`), so tag-keyed species inherit its ~85 biomes correctly. Raw
ids do not — which is why seven species were quietly Terralith-blind. Vanilla has no
`minecraft:is_swamp` or `is_desert`; the conventional `#c:is_swamp` / `#c:is_desert` are
supplied by Fabric API (vanilla members) *and* extended by Terralith, so they are the
correct keys and work with Terralith absent.

Resolving the tags for real also surfaced mismatches that were invisible on paper:
Terralith files **volcanic craters, calderas, ashen savanna, savanna slopes and even
tropical jungle** under `#minecraft:is_mountain` (a snow leopard in a volcanic crater is
the ocean-floor hippo wearing a different hat), and **ice marsh** under `#c:is_swamp`
(a frozen swamp is no more a hippo biome than a frozen river). All excluded.

`gorilla_mountain` initially resolved to a **single** biome with zero Terralith
coverage; it now also takes Terralith's `jungle_mountains` and `rocky_jungle`, which are
literally montane jungle. Naming a biome from an absent mod is harmless — it simply
never matches.

Selected coverage after the sweep (full table in `docs/biome-coverage.md`):

| species | biomes | vanilla | Terralith |
|---|---|---:|---|
| `hippo_river` | 2 | river | warm_river |
| `hippo_swamp` | 3 | swamp, mangrove_swamp | orchid_swamp |
| `crocodile_nile` | 5 | river, swamp, mangrove_swamp | orchid_swamp, warm_river |
| `gorilla_mountain` | 3 | bamboo_jungle | jungle_mountains, rocky_jungle |
| `leopard_snow` | 12 | frozen/jagged/stony peaks, snowy_slopes, grove | alpine_grove, emerald_peaks, rocky_mountains, scarlet_mountains, siberian_grove, snowy_maple_forest, snowy_shield |
| `vulture_griffon` | 23 | badlands ×3, desert, savanna ×3 | 16 arid Terralith biomes |

---

## Part 4 — Making the bug class extinct

Two validators, both wired into `check` **and** `build`, so `./gradlew build` cannot
produce a jar that skipped them.

**`assetAudit`** runs against the *remapped, shipping jar* — not the source tree — so it
validates the artifact that actually ships. Vanilla references are checked against the
real Minecraft assets, and an *unverifiable* vanilla reference is a hard failure rather
than a warning, because the bug that started this was precisely a vanilla path Mojang
had renamed.

**`spawnLint`** enforces nine rules: ≥1 biome (S1); no ocean tag without `aquatic`, and
none at all for an animal with no oceanic form (S2); the `aquatic` flag must agree with
the Java spawn-placement registration (S3); rarity tier exists (S4) and explicit
weight/cap stay inside the tier table (S5); valid group range (S6); **every texture must
live under `textures/entity/<its own entity>/`** (S7) — a static guard against a skin
landing on the wrong animal's model, which renders *smeared*, not missing, so no
resolution check would catch it; well-formed exclusions (S8); and `baby_scale` inside
0.9–1.6 (S9), so the double shrink cannot return.

**Anchors.** Both scripts assert their own inputs — species count, variant-roll and
fur-table presence, literal-scan hit count, the lion-eye family being reached, 40 sound
events, 5 rarity tiers, the waterline placements being found in the Java, and the tag
resolver loading tags. A refactor that makes a scanner match nothing fails the build
instead of passing vacuously. `asset-audit` additionally **refuses** to validate an
undeclared numbered-prefix family: adding a new `foo_<n>.png` run without registering
its index set is itself a build failure.

Both were negative-tested rather than trusted:

| injected fault | expected | actual |
|---|---|---|
| delete `hippo/river.png` | asset-audit fails | FAIL class (a), exit 1 |
| restore it | passes | exit 0 |
| point `hippo_river` at a leopard skin | spawn-lint fails | FAIL S7, exit 1 |
| give the (aquatic) hippo `#is_deep_ocean` | spawn-lint fails | FAIL S2, exit 1 |
| add a new prefix family in new Java | asset-audit fails | FAIL class (c) — caught this audit's own new test file |

That last row happened for real while writing the multiplayer battery.

### Final validator output

```
asset-audit: auditing build/libs/menagerie-0.4.2.jar
             223 shipped resources, vanilla assets from minecraft-client.jar
             108 distinct asset paths in the requestable set
             anchor OK   found 16 species files (expected >= 16)
             anchor OK   at least one species declares variant_rolls
             anchor OK   at least one species declares a multi-texture fur table
             anchor OK   Java literal scan found 23 .png literals (expected >= 2)
             anchor OK   lion eye prefix family was reached by the literal scan
             anchor OK   MenagerieSounds scan found 40 sound events (expected >= 40)
asset-audit: PASS — every requestable asset path resolves

spawn-lint: 16 species, waterline entities: ['crocodile', 'hippo']
            anchor OK   rarity.json defines 5 tiers (expected >= 5)
            anchor OK   MenagerieEntities scan found waterline placements: ['crocodile', 'hippo']
            anchor OK   MenagerieEntities.java still registers spawn placements
            anchor OK   found 16 species files (expected >= 16)
spawn-lint: PASS — every species' spawn rules are inside their ecology
```

---

## Sweep 2 (0.4.3) — what the first sweep's design could not see

The first sweep validated the requestable set *as I had modelled it*. The second asked
what the model itself was missing, and re-checked a claim I had published without
proving. No new defect was found in shipped behaviour; four gaps in the **verification**
were.

| # | Gap | Result |
|---|---|---|
| 1 | The placeholder had **never actually been rendered** — every gametest subject resolved a real skin, so the fallback path was untested. The whole "a Menagerie fallback can never be a checkerboard again" claim rested on it. | Now asserted on the client: present in the resource manager, decodes as 64×64, pixels are magenta `FF00FF` / amber `FFB000`. It loads. |
| 2 | **Vanilla sound EVENT ids** in `sounds.json` (30 of them) were never validated. Vanilla renames these exactly like texture paths, and a bad one is a silent no-sound. Same class as the pig path. | All 30 resolve. Now gated. |
| 3 | **Vanilla item/block/entity/effect ids** named by species data (breed items, forage blocks, diet hunts, venom effects) and recipes were never validated — this is the exact shape of the 0.4.1 bug where a hippo's breed item left it with no valid bait in the world. | All 61 references resolve. Now gated. |
| 4 | **Lang coverage** was unchecked: a registered entity/item/block with no key renders as a raw `entity.menagerie.x` string in nameplates, the guide and cage labels. | All 9 entities + 3 items/blocks have keys. Now gated. |

Also fixed in sweep 2: the `aquatic` species field was **read by nothing in Java** — a
dead record component whose lint rule (S3) claimed the flag and the Java placement could
not drift apart. The waterline placement now consults `species.aquatic()` directly, so
water counts as ground only for a species whose own data says so. Behaviour is unchanged
today (all four hippo/crocodile species declare it) but the flag is now load-bearing
rather than decorative.

Claim re-verified: 26.2's `LivingEntity.getDefaultDimensions` is
`getType().getDimensions().scale(getAgeScale())`, and the caller then multiplies by
`getScale()` (the SCALE attribute). So the hitbox really was base × 0.5 × 0.5 = ¼ under
the old `baby_scale`, as Part 2 states.

Negative tests added for all three new rules (nonexistent breed item, deleted lang key,
nonexistent vanilla sound event) — each fails the build, and the restored tree passes.

### Field report during sweep 2 — read this before filing a checkerboard bug

A screenshot came in during this sweep showing the hippo at `[1746, 61, 480]` still
checkerboarded. It is **magenta/black**, which is *vanilla's* missing texture — the
Menagerie placeholder is magenta/**amber**. That colour difference is now an asserted
invariant precisely so a screenshot alone tells you which code ran:

* **magenta + black** → vanilla's checkerboard → the renderer never reached Menagerie's
  fallback → that client is running **pre-0.4.2** code.
* **magenta + amber** → our placeholder → Menagerie ran, but no skin was synced → the
  **server** is older than the client, or has no species registry.

At the time of that report, `server-mods-staging` still held `menagerie-0.4.1.jar`. The
fix is server-side — the server must be on ≥0.4.2 for skins to sync at all — so it could
not yet be in effect. `SpeciesMob.texture()` now logs a one-shot WARN naming this exact
cause, so the next occurrence explains itself in the log instead of needing a colour
forensics pass.

---

## Sweep 3 (0.4.4) — the skin roll range

Prompted by a specific hypothesis: Untamed Wilds' `hippo.json` declares `"skins": 30`
while the jar ships three files (`common_1..3.png`), their code clamps the roll to what
exists, and a transplant that copied the count would request `common_4..common_30` and
checkerboard intermittently on high rolls.

**The source-data reading is exactly right.** Confirmed in
`work/sources/untamedanimalz-1.18.2-2.4.3-open-source/data/untamedwilds/entities/hippo.json`:
`"skins": 30`, and `assets/untamedwilds/textures/entity/hippo/` contains exactly
`common_1.png`, `common_2.png`, `common_3.png`. It is a real trap in the source.

### The numbers, for the hippo

| | count |
|---|---|
| Skins **our code can select** for hippo | **2** — one per species (`hippo/river.png`, `hippo/swamp.png`). No `textures[]`, no `variant_rolls`, no count field. |
| Hippo textures **shipped in our jar** | **2** — `hippo/river.png`, `hippo/swamp.png` |
| `common_*.png` anywhere in our jar or tree | **0** |
| Occurrences of `"skins"` / a skin-count in our source | **0** |

Menagerie never stores a skin *count*. A species enumerates its skins as explicit
paths, so the roll range **is** the list and there is no index arithmetic to overflow.
The hippo was also never transplanted — its art is painted by `tools/gen-textures.js`;
the only verbatim asset transplants are the gorilla and lion (both Animal Garden).

### But the sweep found the same defect class elsewhere — in the lion

Running the ordered force-spawn-every-index test caught a real bug, in the **safe**
direction (over-declared rather than over-rolled, so it lost variety instead of
checkerboarding):

> `lion_savanna` declares **8** coats and `lion_barbary` **7**. Every lion rendered its
> species' base skin — `body_0` or `body_8`. **13 of 15 lion textures were unreachable.**

Cause: the per-individual fur pick lived in `GorillaEntity.resolveTexture()` alone.
`LionEntity` has no texture code at all, so its `textures[]` was inert data. Path
validation could never see this — every declared path existed and shipped. Only
rendering every index exposed it.

**Fix:** the fur pick moved into `SpeciesMob.resolveTexture()`, so any species declaring
a `textures` list gets it with zero Java — the registry's stated promise. The
gorilla-specific override was deleted.

**New build-time guard** (path checks cannot catch a *shape* problem): the audit now
asserts the shared resolver still reads `species.textures()`, and that no entity
subclass overrides `resolveTexture()` without delegating to `super`. Negative-tested —
reverting the fur pick out of the shared resolver fails the build on both the rule and
its anchor.

### Roll-range table (`tools/asset-audit.py --skin-matrix`)

```
  species                base fur[] variants = rollable shipped  verdict
  ------------------------------------------------------------------------------
  crocodile_nile            1     0        0          1       1  PASS
  crocodile_saltwater       1     0        0          1       1  PASS
  gorilla_lowland           1     3        1          4       4  PASS
  gorilla_mountain          1     2        1          3       3  PASS
  grizzly_black             1     0        0          1       1  PASS
  grizzly_taiga             1     0        0          1       1  PASS
  hippo_river               1     0        0          1       1  PASS
  hippo_swamp               1     0        0          1       1  PASS
  leopard_jungle            1     0        0          1       1  PASS
  leopard_snow              1     0        0          1       1  PASS
  lion_barbary              1     7        0          7       7  PASS
  lion_savanna              1     8        0          8       8  PASS
  snake_python              1     0        0          1       1  PASS
  snake_viper               1     0        0          1       1  PASS
  tortoise_savanna          1     0        0          1       1  PASS
  vulture_griffon           1     0        0          1       1  PASS
  ------------------------------------------------------------------------------
  entity folder          files selectable  orphans (never rolled)
  crocodile                  2          2  —
  gorilla                    7          6  —
  grizzly                    2          2  —
  hippo                      2          2  —
  leopard                    2          2  —
  lion                      15         15  —
  snake                      2          2  —
  tortoise                   1          1  —
  vulture                    1          1  —

  34 rollable skins across 16 species; 0 unresolvable
```

### Force-spawning every index

The fur pick is `floorMod(getUUID().hashCode(), n)`, so rather than spawning many and
hoping to observe each index, the battery **searches for a UUID that lands on each
index** and summons with it. Every skin index is hit exactly and deterministically, not
sampled. Variant coats are forced by NBT.

Result after the fix: **34/34 skins rendered, zero placeholders** — `skin sweep
finished: every skin rendered`. Screenshots: `mp_skins_0..5.png`. Lions now visibly
vary within a pride.

### Why the amber checkerboard was never this bug

A **missing** texture file cannot render amber. Minecraft substitutes *its own*
magenta/black checkerboard when a path does not resolve. Magenta/amber is
`menagerie:textures/entity/missing.png` — a file that ships and loads (asserted on the
client in sweep 2) — and it is returned by exactly one code path: no skin was synced and
the client has no registry. That is a **server** older than the client, confirmed
independently via the panel API: the live server was still running `menagerie-0.4.1.jar`
while the client had 0.4.3.

---

## Deviations — flagged, reviewed, deliberately unchanged

* **`leopard_jungle` stays in the jungle family.** The brief's ecology table groups
  "leopard/lion = savanna-family". Real leopards (*Panthera pardus*) are very much
  rainforest animals, and moving them to savanna would collide with the lion's niche
  and delete a working species. Recorded as intentional in the linter's ecology table.
* **`grizzly_*` stays forest/taiga and does not take `#is_mountain`.** Forest and taiga
  are a *subset* of the correct family, not a mismatch, and adding mountains would
  materially widen the spawn footprint — a content change, not a bug fix. Noted for a
  future release.
* **The `hippo`/`crocodile` biome overlap on swamps is intentional.** Both crocodile
  species can appear in Terralith swamps; the weighted pick gives variety rather than a
  hard split.
