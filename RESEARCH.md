# Menagerie — Stage 1 Research Notes

Date: 2026-08-15. Doctrine: read the actual LICENSE in each repo before touching entity
code. Patterns and architecture may always be studied; verbatim code only from
permissive (MIT/Apache/CC0) sources; assets never copied unless the license explicitly
permits with attribution.

## License table

| Repo | License (as found in the repo) | What we may take |
|------|-------------------------------|------------------|
| `AlexModGuy/AlexsMobs` (branch 1.20) | **No LICENSE file exists in the repo at all**, and the README contains no license grant. CurseForge reportedly lists LGPLv3 and an unofficial port claims GPLv3 — neither is corroborated by the repository itself. With no written grant, the default is **all rights reserved**. | Concepts and architecture only. No code, no assets, ever. (It is Forge + Citadel anyway — nothing would port to Fabric 26.2 verbatim.) |
| `RayTrace082/untamedwilds` (branch 1.18.2) | **GPL-3.0** (GitHub-detected, LICENSE present) | Patterns only. Copying code would force GPL onto our CC0 mod. No assets. |
| `bernie-g/geckolib` | **MIT** | License-safe: could be depended on or vendored with attribution. See decision below — we are NOT using it, for practical (not license) reasons. |
| `FrozenBlock/WilderWild` | Custom "FrozenBlock Modding Oasis License" (not GPL as commonly assumed; GitHub classifies it NOASSERTION/Other) | Patterns only, no code, no assets. |
| In-house: `warfront`, `vibranium` (lilkuzco-dev) | Ours (CC0) | Everything — these are the primary, already-verified 26.2 Fabric idiom sources. |

## GeckoLib decision

GeckoLib ships a Fabric build for MC 26.2 (5.5.x line) and is MIT — fully license-safe.
**Decision: not adopted.** Reasons:
1. GeckoLib models are Blockbench-authored `.geo.json` + keyframe `.animation.json`
   files. We have no Blockbench in this pipeline; hand-authoring geo JSON is strictly
   worse than hand-authoring vanilla `LayerDefinition` cube models in Java.
2. It adds a runtime dependency every friend in the installer group must sync.
3. Phase 1 animations (knuckle-walk, chest-beat, lunge, shell-retract, crouch/pounce)
   are all achievable with programmatic part rotation, the same way vanilla wolf-shake
   and Warfront soldiers work.

So: vanilla-style cube models (`EntityModel` + `LayerDefinition`), animation via
part-rotation driven from synced entity state. Revisit GeckoLib if a later phase wants
genuinely complex keyframed animation.

## Patterns adopted (studied, then written fresh for Fabric 26.2 Mojmap)

- **AlexsMobs gorilla — silverback as synced data:** `SILVERBACK` is a synced boolean
  on the one gorilla entity, not a subclass. Silverback bonus attributes are applied
  when the flag flips (promotion works on live entities). We do the same, plus a
  troop-scan promotion timer.
- **AlexsMobs gorilla — one silverback per group:** group spawn logic checks for a
  nearby existing silverback before assigning the flag at finalize-spawn. Adopted,
  but deterministic: first member of a spawn group becomes silverback, never random.
- **AlexsMobs gorilla — chest-beat cadence:** fired from `tick()` on a random interval
  while idle (their `random.nextInt(800)`-style check). Adopted with an explicit
  cooldown window (30–90s JSON-tunable) + a proximity trigger for hostiles.
- **AlexsMobs gorilla — baby rides adult:** `positionRider` override placing the
  passenger at a body-rotation-relative offset near the adult's shoulders. Concept
  adopted; our offsets/geometry are our own.
- **Untamed Wilds — species system:** ONE entity class per animal; the variant is
  synced entity data; texture, stats, and spawn rules route through the variant.
  Menagerie evolves this: species are **datapack JSON files** (`data/menagerie/
  species/*.json`) keyed by biome *tags*, hot-reloaded via a resource reload
  listener — adding a species of an existing animal requires zero Java.
- **Untamed Wilds — `worldgen_only`:** some animals only spawn with newly generated
  chunks and never naturally respawn (killing them permanently empties an area).
  Implemented via `EntitySpawnReason` filtering in the spawn predicate.
- **Warfront (in-house) — data registry:** `SimpleReloadListener` +
  `ResourceLoader.get(PackType.SERVER_DATA)` from fabric-resource-loader-v1, Gson
  parse into records, `Map.copyOf` swap on apply. Reused wholesale (ours).
- **Warfront (in-house) — 26.2 entity idioms:** `EntityType.Builder` +
  `ResourceKey.create(Registries.ENTITY_TYPE, id)` + `builder.build(key)`,
  `FabricDefaultAttributeRegistry`, `ValueInput`/`ValueOutput` persistence (26.2
  replaced NBT read/write on entities), render-state-based renderers
  (`extractRenderState`). Reused wholesale (ours).
- **Fabric biome API ground truth (javap'd from the 18.0.6 jar, not from blog posts):**
  `BiomeModifications.addSpawn(Predicate<BiomeSelectionContext>, MobCategory,
  EntityType, weight, minGroup, maxGroup)`; `BiomeSelectors.tag(TagKey<Biome>)`.
  Selectors evaluate lazily at biome bake (world load), so the selector can consult
  our species registry — but baked spawn *weights* are fixed until server restart.
  Registry-driven tuning after `/reload` therefore runs through the spawn placement
  predicate (live weight can gate/scale acceptance downward without a rebuild;
  raising weight above the shipped value needs a restart). Recorded as a known
  limitation in README.

## Spawning architecture note

Species JSONs are read twice: once from the classpath at mod init (to register
BiomeModifications spawn entries before biome bake), and continuously via the reload
listener (live behavior: stats, tame item, special abilities, spawn gating). This is
deliberate — Fabric bakes biome spawn lists once per server run.

---

## Phase 2 appendix (2026-08-15)

### License additions

| Repo | License (as found) | What we may take |
|------|--------------------|------------------|
| `starfish-studios/Naturalist` | **Split license, verified in LICENSE:** `/common/src/main/resources/`, `/fabric/src/main/resources/`, `/forge/src/main/resources/` (and everything under them) are **All Rights Reserved**; *all other files* — i.e. the Java code — are **MIT**. (The current single-loader branch keeps the same carve-out shape with `src/main/resources/`.) | Bear (fishing/sleep/cub-defense) and snake (coiled ambush/rattle) **code** usable under MIT with attribution; every asset directory is off-limits. We studied `Bear.java`/`Snake.java` for structure (sleep gating windows, goal ordering, rattle range checks) and wrote our own 26.2 implementations — nothing copied verbatim, since its 1.21.1 GeckoLib-based code doesn't port to our vanilla-model 26.2 codebase anyway. |
| `AlexModGuy/AlexsMobs` | unchanged from Phase 1: **no license file, all rights reserved** | Concepts only, again: hippo-class hitbox feel, vulture/eagle circling idea, rattlesnake telegraph-then-strike pacing. No code was even fetched this phase. |

### Patterns adopted in Phase 2

- **Vanilla `Phantom`** (mapped sources, ours to read): custom `MoveControl` steering
  toward a `moveTargetPoint` with velocity blending + `CircleAroundAnchorGoal`
  selecting orbit points; `travel() -> travelFlying(input, 0.2F)`. The vulture is a
  simplified port of this exact structure with a landed/flying state switch.
- **Vanilla `PolarBear`**: mother-aggro shape (baby-gated target goal + hurt-alert);
  our grizzly adds the Naturalist-style explicit "player crowds a cub" check.
- **Naturalist `Bear` (MIT)**: sleep scheduling gated on day-time windows + anger +
  not-in-water; goal priority layout for stacking fishing/sleeping/raiding on one mob.
- **Naturalist `Snake` (MIT)**: rattle-warning driven off a range check rather than a
  timer, so careful players never trigger the strike.
- **Vanilla `VehicleEntity`**: boat damage accumulates `damage * 10` and breaks past
  40 — the hippo bites for +25/bite and takes over the breakup to drop planks+sticks
  (spec) instead of the vanilla boat item.
- **In-house `GrabHold` extraction**: the Phase 1 crocodile grab was refactored into
  a shared class now also driving the python constrict — one code path, regression-
  tested on both.

---

## Phase 3 appendix (2026-08-15)

- **Vanilla `Bucketable`/`MobBucketItem`** (mapped sources): the capture-with-data
  idiom. We went further than the bucket (which saves a curated subset): the cage
  saves the FULL entity via `Entity.save(TagValueOutput)` and restores with
  `EntityType.loadEntityRecursive(tag, level, EntitySpawnRequest, processor)` —
  species, health, name, owner and tame state all survive (verified live).
- **Untamed Wilds cage-trap concept** (GPL, patterns only, from Phase 1 findings):
  capture item semantics + NBT persistence idea only; our implementation is a
  block-entity + BLOCK_ENTITY_DATA component ride. Key 26.2 discovery: loot
  `copy_components` only copies components a block entity EXPORTS via
  `collectImplicitComponents` — raw `saveAdditional` data is invisible to it.
- **Alex's Mobs animal dictionary** (all-rights-reserved, concept only): discovery
  gating idea. Ours is a server-authoritative SavedData set + a payload-driven
  screen whose entries are generated from the live species registry — no
  hand-written entry text anywhere.
- **In-house Warfront v0.2 client/network idioms** (ours): 26.2 `Screen.
  extractRenderState(GuiGraphicsExtractor,...)` drawing, `PayloadTypeRegistry`
  streams, SavedData codec registration.
- **Warfront compat decision**: implemented WITHOUT any compile or classload
  dependency — soldiers are identified by entity id string `warfront:soldier`
  via the vanilla Mob API, gated behind `FabricLoader.isModLoaded`. Full soldier
  deflection worked outside-in (idle soldiers pathed out of live territories in
  testing), so the API-only fallback wasn't needed. `MenagerieTerritories` is a
  public API any mod can query; `TERRITORY_ACTIVE` is the fabric-style event.

---

## Gorilla visual replacement (v0.3.1, 2026-08-15)

### License

| Source | License (verified in the artifact itself) | What we may take |
|--------|-------------------------------------------|------------------|
| `animalgarden-westerngorilla-1.0.1` by aquarius_playz | **The Unlicense — public domain.** The jar contains a full `UNLICENSE` file (complete dedication text) and `META-INF/mods.toml` declares `license="Unlicense"`. Both read directly before any import. | **Everything** — assets and code alike, no attribution required. Credited in `CREDITS.md` as a courtesy. |

This is the first source in the project cleared for verbatim asset reuse; every other
animal remains vanilla-remix art per the standing doctrine.

### Porting notes (Forge 1.20.1 → Fabric 26.2)

- The jar is a **production Forge build**: vanilla references are SRG-obfuscated
  (`m_232275_`, `f_232230_`, …). Decompiled with Vineflower 1.12.0, then every SRG
  name was resolved before conversion — **not guessed**. Two independent lines of
  evidence agreed: (a) an empirical pass over the data (Targets pair 1:1 with vec
  helpers; positions max out at 7.0 while rotations reach 152°; `scaleVec` is the
  only double-typed helper), and (b) an external remap table listing the obf/Mojmap/
  yarn/SRG names side by side. **The frequency heuristic would have inverted
  LINEAR/CATMULLROM** — `f_232229_` is LINEAR despite being the rarer of the two here
  (this animator favours smooth keyframes). Worth remembering: declaration order and
  usage frequency are both unreliable for SRG disambiguation.
- 26.2 has a **baked keyframe API** (`AnimationDefinition.bake(root)` →
  `KeyframeAnimation.apply(state, ageInTicks)` / `applyWalk(...)`), so the mod's
  hand-rolled reflective animation runner (`ModMobModel.animate`, `ModAnimationState`)
  was dropped entirely rather than ported. `bake()` throws if a bone is missing, so
  all 20 animated bone names were cross-checked against the 26 model parts first.
- Model part offsets are **additive** (`offsetPos/Rotation/Scale`), and `scaleVec`
  subtracts 1 — so an absolute look-at rotation set before applying animations
  composes correctly instead of fighting them.
- `AnimationState` lives on the entity and is copied into the render state each frame
  (vanilla Armadillo pattern). Triggers reuse our existing entity-event broadcasts,
  so the animation work added no new networking.
- Silverback presentation switched from a per-species `_silverback.png` texture swap
  to their **translucent overlay layer** (`RenderTypes.entityTranslucent` at
  `0x80FFFFFF`): one saddle skin composes with all five fur colours instead of
  needing a silverback copy of each. Our 1.15× scale and +50% attack are unchanged.

### AI goals reviewed (ported only what was clearly better)

- **`ModFollowMaleGoal` → PORTED** as `FollowSilverbackGoal`. It filled a real gap:
  our troop members had no cohesion goal and wandered independently. Retargeted from
  their gender check to our troop ids, and exempted tamed gorillas (they follow their
  owner). Distances kept (follow past ~7 blocks, give up past ~17).
- **`ModLeapAtTargetGoal` → SKIPPED.** It is vanilla's `LeapAtTargetGoal` plus a
  cooldown; a leaping heavyweight also fights the knuckle-walker read we want. Not
  clearly superior, so not taken.
- `ModMeleeAttackGoal`, `ModBreedGoal`, `ModFollowParentGoal`,
  `ModNearestAttackableTargetGoal`, `ModOwnerHurt*` → **SKIPPED**: they re-implement
  vanilla goals around the mod's own gender/behaviour-type state machine, which our
  troop/taming/disposition logic already covers more richly.
