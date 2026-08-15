# Menagerie 0.1.0 — Verification Battery

Date: 2026-08-15. Environment: dev dedicated server (Fabric loader 0.19.3, MC 26.2,
fabric-carpet fake player "Steve" kept online — 26.2 servers pause ticking when empty),
driven headless over RCON via `tools/rcon.js 25577 menagtest "<cmd>"`. Client render
check automated via Fabric client gametest (`./gradlew runGametest`).

Debug tooling built for this battery: `/menagerie census [r]` (per-species counts with
`|silverback|shelled|baby|angry|tame|sitting|riding` markers), `/menagerie troops [r]`
(troop composition), `/menagerie silverback` (force-promote nearest).

| # | Test | Result |
|---|------|--------|
| 1 | Roster renders in the client camera | **PASS** — automated screenshot via client gametest (`docs/lineup.png`): leopard, tortoise, crocodile, silverback (visible silver saddle), adult + baby gorilla all render with correct models/textures. No invisible-entity regression (the bug that bit Warfront). |
| 2 | Jungle troops, one silverback each | **PASS** — natural chunk-gen troops observed: sizes 6, 4, 3 (config [3,6]), each with exactly one silverback. Two stray troops of 1 observed (spawn-attempt cutoffs / partial chunk loads — the lone adult self-promotes, see #3). |
| 3 | Troop retaliation + promotion | **PASS** — `/damage` one member `by Steve` → whole troop aggro'd; log: "Steve was slain by Gorilla". Killed a troop's silverback → promotion after the 60s leaderless window. Caveat found & mitigated: a troop scattered beyond the scan radius can double-promote; the merge-dedup demotes the higher-UUID silverback within 2 scan ticks of contact (observed live: silverbacks=2 → 1). Scan radius widened 32→48 to make the window rare. |
| 4 | Chest-beat + intimidation | **PASS** — zombie placed 3 blocks from a silverback: `active_effects` showed `minecraft:slowness` on the zombie; the adjacent player had none. Idle cadence and promotion-announcement beats share the same code path. |
| 5 | Taming / sit / follow / teleport / baby | **PASS** — melon feed tamed (1-in-3 roll); census `|tame`. Interact toggles `|sitting`. Sitting gorilla correctly refuses to follow. Standing gorilla teleported 47 blocks to its owner (sky-platform test). Fed tamed adult → `InLove: 575`. Summoned baby adopted the troop, climbed the adult (`|baby|riding`, y exactly adult+1.29); aged to adult → dismounted. |
| 6 | Crocodile ambush / land neutrality | **PASS** — wild nile croc spawned in swamp by chunk gen. In a test pool: 10-HP pig killed inside 6s; 40-HP pig showed the full cycle — bite 7.0 + two 3.5 chews + release (40→26), mid-grab `slowness` amplifier 2 captured, victim fled to land and was NOT pursued. |
| 7 | Tortoise shell armor | **PASS** — hit 1: 20→16 (full 4.0, then `|shelled`, armor attribute = 8.0). Hit 2 while shelled: 16→12.96 (exactly the 8-armor formula). Shell exits after 10s (armor back to 0.0). **Bug found & fixed during battery:** `isImmobile()` makes vanilla skip `serverAiStep`, which deadlocked the shell timer — timer moved to `tick()`. |
| 8 | Leopard stalking / opportunism | **PASS** — summoned chicken hunted and killed. Full-health survival player 3 blocks away: ignored for minutes. Player brought below half health: log "Steve was slain by Leopard". (It also stalks baby animals incl. baby gorillas — spec-intended prey list.) |
| 9 | Cold-biome species routing | **PASS** — same entity classes summoned in a grove: `gorilla|mountain` (max health 34) and `leopard|snow` (max health 26). Saltwater croc in mangrove swamp: `crocodile|saltwater`, scale attribute 1.2. Zero Java per species. |
| 10 | JSON edit → /reload → changed behavior | **PASS** — edited `gorilla_lowland.json` health 30→55 in the live datapack, `/reload` (log: species registry re-ran), fresh summon had max health 55.0; reverted the same way. Spawn *weights* are baked into biomes at world load (Fabric biome API limitation); after /reload the live weight scales the natural-spawn acceptance downward (weight 0 = no natural respawns) — raising a weight above the shipped value needs a restart. |
| 11 | Build + co-load with vibranium/warfront | **PASS** — `./gradlew build` clean. Dedicated server booted with menagerie 0.1.0 + vibranium 1.6.0 + warfront 0.1.0: all three initialized, both sibling data registries loaded, no mixin conflicts, Done in 0.531s. In-camera co-load on the real client is expected to match (shared code paths verified; renderers registered per-mod). |

## Other findings
- `/summon` **with NBT** skips `finalizeSpawn` (vanilla behavior): such mobs keep
  baseline attributes and fall back to the entity's first species for texture/behavior
  until values are touched. Plain `/summon` finalizes normally.
- Entities in **forceloaded chunks do not tick** without a nearby player (vanilla
  chunk-level quirk) — mid-battery red herring, worth remembering for future batteries.
- A lone summoned gorilla becomes silverback of its own new troop; separately summoned
  gorillas coalesce into nearby troops within ~2s and any duplicate silverbacks dedup.

---

# Menagerie 0.2.0 — Phase 2 Battery

Date: 2026-08-15. Same rig: dev dedicated server (port 25567 this time — a parallel
Warfront dev server held 25565), carpet fake player, RCON, client gametest for render
checks. Census gained `|sleeping`, `|flying`, `|rattling` markers.

| # | Test | Result |
|---|------|--------|
| 1 | All four new mobs render in-camera | **PASS** — client gametest screenshot (`docs/lineup_phase2.png`): hippo (mauve hide, pale belly), grizzly (dark coat, muzzle, ears), vulture (dark feathers, bald pink head/neck, folded wings on the ground), coiled green snake. Phase 1 row re-shot too (`docs/lineup.png`). |
| 2 | Hippo territory | **PASS** — water-anchored claim (`menagerie_territory` snapped to the pool block). Survival player at 20 blocks: ignored for 6s+. At 7 blocks: yawn warning then `|angry` charge within 5s. Left the zone: stood down after ~25s. **Boat**: destroyed in 2 bites inside 10s, oak planks + sticks item entities at the wreck (custom break — vanilla would drop a boat item). |
| 3 | Grizzly | **PASS** — cub-defense: adult calm with player 10 blocks from cub, `|angry` within 4 blocks of it (timid black bear correctly overrode its flee instinct). Fishing: salmon killed by paw-swipe and consumed within 20s of going hungry, no leftover items, no anger. Sleep: `|sleeping` at midnight (both bears), awake at noon. |
| 4 | Vulture | **PASS** — natural spawns airborne in fresh savanna chunks (4 × `|flying`). Cow killed → vultures converged within 10s, circled ~10s, landed, **beef consumed at t=30s**. Full-health survival player ignored for 8s+; at ≤3 hearts a vulture pecked for exactly 1 (and the flock later finished the test player off — log: "Steve was slain by Vulture"). Two bugs found & fixed mid-battery: ambient despawn was culling them 32 blocks out (now only 96+), and the phantom-style steering repels from steep descent targets (landing now cuts the engine overhead + fall-damage immunity). |
| 5 | Snake | **PASS** — python: no strike at 3.3 blocks (rattle `|rattling` only), strike inside 2 with Slowness III constrict via the shared GrabHold; repeated strikes eventually killed the unattended fake player ("Steve was slain by Snake"). Viper (badlands biome routing ✓, 8 HP from JSON): strike applied `minecraft:poison` amplifier 1 for 8s. **Crocodile grab regression through the same refactored GrabHold: exact Phase 1 damage fingerprint (40→26: 7 + 3.5 + 3.5)**. Bug found & fixed: the sprint-flee predicate was in the wrong AvoidEntityGoal ctor slot — snakes fled everyone, strikes unreachable. |
| 6 | Registry reload | **PASS** — all 14 species load with Phase 1 files untouched (additive schema). Hippo territory radius 16→6 in the live datapack + `/reload`: same 8-block position flipped from aggro to ignored, 4-block position still aggroed; restored the same way. Zero rebuilds. |
| 7 | Phase 1 regression | **PASS** — jungle troops intact (one silverback each, incl. self-promoted loner troops). Fresh gorilla tamed on the first pinned feed. The Phase-1 tame gorilla is parked in an unloaded chunk somewhere along the test route (it follow-teleports, and the fake player crossed ~4 km of test sites) — expected pet behavior, not a regression. |
| 8 | Build & co-load | **PASS** — clean `./gradlew build`; every dev-server boot this battery co-loaded menagerie + vibranium 1.6.0 + warfront 0.1.0 with no mixin conflicts. |

## Bugs found by this battery (all fixed before ship)
- **Worldgen deadlock**: hippo territory claim ran inside `finalizeSpawn` (worldgen
  thread) and its water scan read across chunk borders → server watchdog kill. Claims
  now happen lazily on the first ticked frame. Vulture heightmap reads similarly
  guarded with `hasChunkAt`.
- Vulture ambient-despawn distance, vulture descent steering (both above).
- Snake AvoidEntityGoal predicate slot (above).
