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
