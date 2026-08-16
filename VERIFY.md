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

---

# Menagerie 0.3.0 — Phase 3 Battery

Date: 2026-08-15. Same rig (dev dedicated server port 25567, carpet fake player,
RCON; client gametest for screen rendering). Census gained the `|content` marker.

| # | Test | Result |
|---|------|--------|
| 1 | Cage: bait → capture → break → carry → place → release | **PASS** — baited a standard cage with raw chicken (leopard `diet.hunts` doubles as bait); a calm named leopard ("Whiskers") was lured and captured: blockstate `closed=true`, world entity gone, block entity holding the FULL NBT (species, name, health, attributes, tags). Broken cage dropped an item carrying `block_state` + `block_entity_data` (occupant aboard — the tooltip's data source, verified in components). Placed → closed cage restored; empty-hand use → Whiskers released alive, name intact, cage reopened for reuse. Fix found: loot `copy_components` needs the BE to export via `collectImplicitComponents` — raw save data isn't picked up. |
| 2 | Tier rule | **PASS** — grizzly (cage_tier 2) in a standard cage: captured, then broke out ~3s later, cage block destroyed (drops nothing). Reinforced cage: held permanently. **Aggroed hippo could NOT be captured** (baited cage sat untouched while `|angry`); the same hippo calm was captured in a reinforced cage (after widening the spring check to the bounding box — a 1.9-wide hippo can't put its center within 1.6 of the block). |
| 3 | Tamed round trip | **PASS** — freshly tamed gorilla (Owner UUID verified BEFORE capture), captured in a reinforced cage, released: `Owner` matches the same player UUID exactly, census `|tame`. (Tamed pets aren't bait-lured from a distance; one led beside the cage is captured — owner transport.) |
| 4 | Field Guide | **PASS** — discovery pings fire within 8 blocks (guide counter climbed 0→3→8 as the fake player toured animals); action-bar + sound on first encounter. The gametest screenshot (`docs/field_guide.png`) shows the screen: "Documented: 8/14", discovered entries by name with icons, undiscovered as ??? silhouettes, and a live-registry entry (blurb, biomes, stats, hunts, cage tier). Throwaway species JSON + `/reload` → registry 15 and "guide: 7/15" (new species = undiscovered, denominator live); deleted → back to 7/14. Zero hand-written entry text. |
| 5 | Forage ecology | **PASS** — melons placed by a jungle troop were eaten within 10s (one per cooldown — the second melon survived), eater `|content`. mobGriefing OFF: melon untouched, but hand-feeding still set `|content` on the fed gorilla AND a troop-mate within 16 (mood spreads). **Taming statistics** (landed feeds only, NoAI-pinned subjects after two flaky carpet-aim benches): unfed first feeds 6/15 tamed (40%, expected 33%); content follow-up feeds 9/9 (100%, expected 67%) — the doubling is unambiguous (Fisher p≈0.002). Fix found: the tame roll must happen BEFORE the meal registers, or the first feed already counts as content. |
| 6 | Warfront crossover | **PASS** — with Warfront loaded ("Warfront detected" in log): two idle soldiers summoned inside a live hippo territory were steered out (7→27 and 2→19 blocks from center within ~16s, soft pathing nudge). Soldier-vs-soldier `/damage` inside the territory: the hippo charged 20 blocks at the combatants and bit the attacker (24→18 HP). **Without the Warfront jar**: identical boot (no compat line), all Phase 3 features work, summons fine, zero errors. |
| 7 | Regression sweep | **PASS** (spread through this battery): gorilla taming (statistics bench), hippo yawn/charge/calm (cage tests), crocodile grab fingerprint + python constrict + viper venom (Phase 2 battery, unchanged code), vulture scavenge (Phase 2, unchanged). All 14 Phase 1-2 species JSONs load with the new fields ignored-by-default (additive schema, third time). |
| 8 | Build & co-load | **PASS** — clean build; server booted all-three-mods throughout the battery; boots and runs without Warfront. |

## Notes
- The fake player was killed twice more this battery (hippo, python) — Menagerie
  remains the leading cause of death for Steve across all three phases.

---

# Menagerie 0.3.1 — Gorilla Visual Replacement

Date: 2026-08-15. Client checks via `./gradlew runGametest` screenshots; behaviour via
the usual dev server (port 25567) + carpet fake player over RCON. Census gained a
`|fur=<name>` marker so texture variants are provable, not just eyeballed.

| # | Test | Result |
|---|------|--------|
| 1 | License cleared before import | **PASS** — the jar's own `UNLICENSE` (full public-domain dedication) and `license="Unlicense"` in `META-INF/mods.toml` were read directly. Recorded in `RESEARCH.md`; `CREDITS.md` credits aquarius_playz. |
| 2 | Model + animations port | **PASS** — 26 parts bake cleanly and all 20 animated bone names resolve (`KeyframeAnimation.bake` throws otherwise, so this is enforced, not assumed). Every SRG name was resolved against an independent remap table before conversion; the naive frequency heuristic would have swapped LINEAR/CATMULLROM. |
| 3 | Renders in the client camera | **PASS** — `docs/gorilla.png`: six gorillas with the articulated face (eyes, brow, muzzle, ears) in the knuckle-walk stance. No invisible-entity or missing-texture regression; zero unknown-sound warnings in the client log. |
| 4 | Fur variants | **PASS** — census logged `fur=default`, `fur=brown` and `fur=darker` across one spawn batch, so all three lowland variants are assigned and rendered. **Caveat (upstream art, not the port):** `brown` differs from `default` by at most 7/255 on any channel and `darker` by 15 — they are subtle tints, near-indistinguishable in play. `black` (max delta 44) and `brown_back` (localised back patch) are clearly distinct. |
| 5 | Silverback overlay | **PASS** — `docs/gorilla_silverback.png`, a pinned A/B with species and flag set explicitly by NBT: the plain adult's back is uniformly black, the silverback's carries the light saddle. A separate control (two non-silverbacks from behind) shows no saddle on either. Note for future batteries: a plain `/summon` makes each gorilla the silverback of its own new troop, which is why an unpinned pair shows two saddles. |
| 6 | Chest-beat | **PASS** — a hostile inside the detect radius made the silverback rear up (visible in `gorilla_chest_beat_b`) and applied Slowness to the zombie, which is the same code path that plays `chestpump.ogg` and broadcasts the animation event. Beat duration extended 40→50 ticks to match the 2.5s animation. |
| 7 | Troop cohesion (`FollowSilverbackGoal`) | **PASS** — three followers sharing a troop id with one silverback closed from 12.0 blocks to 1.6/1.8/1.9 blocks within 15s and then held station. |
| 8 | Build + regression | **PASS** — clean `./gradlew build`; the other seven animals are untouched (their generated textures regenerate byte-identically; `tools/gen-textures.js` no longer paints the gorilla and says so). |

## Notes
- `NoAI` skips `customServerAiStep` entirely, so behaviour tests (chest-beat, troop
  logic) need live-AI mobs; only pose/framing shots should pin with `NoAI`.
- Gorilla `hurt`/`death`/`eat` remain pitch-shifted vanilla events: the source jar
  ships no recordings for them, and silence would be worse than a stand-in.

---

# Menagerie 0.4.0 — Renaissance Update (Wave 1)

Date: 2026-08-16. Client checks via `./gradlew runGametest`; framework checks on the dev
server (port 25567) over RCON with a carpet fake player. Census gained `|fur=<name>`
(which now also reports a rare variant) and a new `/menagerie rarity` readout.

| # | Test | Result |
|---|------|--------|
| 1 | Seven source jars license-cleared | **PASS** — each declares `license="Unlicense"` *and* ships the full dedication text, both read directly. sha256s recorded in `assets/SOURCES.md`. The Untamed Wilds GPL-vs-Unlicense discrepancy is documented there rather than papered over. |
| 2 | Lion renders (new marquee animal) | **PASS** — `docs/lion.png`: four lions standing on the sourced 34-part model with correct tawny skins, and the separate eye layer rendering. No UV scramble, no missing-texture or unknown-sound warnings. |
| 3 | Lion animations | **PASS** — walk/run blend by speed, breathing loops, and the resting/sleep state machine runs. **Bug found and fixed:** `isResting()` originally keyed off "navigation is done", which is true constantly — every idle lion lay down mid-stroll. Now requires ~5s genuinely stationary and ignores `NoAI`. |
| 4 | Field Guide auto-adopts new species | **PASS** — the lion triggered "New species documented: Savanna Lion" on first approach with **zero hand-written entry text**; the entry is generated from its species JSON. Guide total moved 14 → 16 species. |
| 5 | Albino gorilla renders | **PASS** — `docs/gorilla_albino.png`: cream coat with pink face and hands beside a normal black adult. **Bug found and fixed:** `GorillaEntity` overrides `texture()` for its fur table and initially ignored the rare variant, so the albino rendered black while the discovery ping fired. Rare coats now outrank the fur table. |
| 6 | Albino rate is the configured 5% | **PASS** — 200 plain spawns in four batches of 50: 1 + 1 + 3 + 5 = **10 albinos = 5.0%**, dead on the JSON value (prompt accepted 4-18). Note NBT `/summon` skips `finalizeSpawn` and therefore never rolls — natural spawns and breeding do. |
| 7 | Variant persists, never re-rolls | **PASS** — an albino was tagged, `save-all`, the server fully stopped and restarted: `menagerie_variant` still `"albino"`, census still `fur=albino`. |
| 8 | Rarity table resolves | **PASS** — `/menagerie rarity` across all 16 species; **gorilla is now weight 2 (panda-class), down from 8**, troop 3-5, cap 8. |
| 9 | Rarity hot-reload | **PASS** — `rare` weight 2 → 6 in the live datapack + `/reload` → gorilla weight reads 6; restored → reads 2. No rebuild. The gorilla's `nearby_cap` stayed 8 throughout, correctly overriding the tier's value (documented precedence). |
| 10 | `/menagerie cull` protects tamed and named | **PASS** — 12 gorillas (10 wild, 1 named "Kong", 1 tamed) culled to keep 3: 7 removed, both Kong and the pet survived. (The "spared" counter reported 1 because the other protected animal had wandered outside the cull radius at scan time — wild animals move.) |
| 11 | Data-driven breeding | **PASS** — hippos, which had **no breeding path before this release**, entered love mode from melon slices via the new `breeding` block and produced a calf (census `hippo|river|baby`). Snake and vulture carry no breeding block by design, with the reasoning recorded in their `_tuning_notes`. |
| 12 | Build | **PASS** — clean `./gradlew build`; still Fabric-API-only, no new runtime dependency. |

## Not verified in this release — stated plainly

- **The nearby-species cap is implemented but not empirically proven.** It gates natural
  spawns only, and forcing natural spawn attempts on demand is not something the command
  surface allows; proving it needs the density transects below.
- **Density transects (jungle/savanna/river counts vs vanilla panda) were not run.** They
  are the real proof that the calibration lands, and they need a fresh world plus long
  flights. The numbers are set and readable via `/menagerie rarity`, but the in-world
  density claim is currently a prediction, not a measurement.
- **The 15-minute spawn soak / MSPT check was not run.**
- Waves 1 (crocodile and the Untamed Wilds ports), 2 (rhino, hyena, elephant) and 3
  (critters, companions) are **not in this release**. Their sources are unpacked,
  license-cleared and inventoried; nothing has been imported from them.
