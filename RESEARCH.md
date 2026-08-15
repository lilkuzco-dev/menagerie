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
