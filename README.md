# Menagerie

A wildlife mod for Fabric (Minecraft 26.2) in the spirit of Untamed Wilds and Alex's
Mobs: a flagship gorilla with real troop life plus a growing roster, all built on a
**data-driven species registry** — adding a new species of an existing animal is one
JSON file, zero Java.

![roster](docs/lineup.png)
![phase 2 roster](docs/lineup_phase2.png)

## Roster (Phase 2, v0.2.0)

- **Hippo** — rivers/swamps; claims a water-anchored **territory** (radius in JSON).
  Passive outside it; intruders get a 3-second yawn warning, then a 60-HP charge.
  Destroys boats in two bites (planks and sticks float where your ride was). Calms
  ~20s after you leave. Species: `river`, `swamp` (darker, smaller).
- **Grizzly Bear** — taiga/forests; neutral with **mother-aggro** (hurt a cub, or
  crowd within 6 blocks of one, and every adult within 16 comes). Fishes salmon out
  of rivers with paw-swipes when hungry (**diet** block), raids berry bushes
  (mobGriefing), sleeps at night in shelter — day is the threat window. Species:
  `grizzly`, `black` (smaller, flees players unless cubs are near).
- **Vulture** — deserts/badlands/savannas; circles 20-30 blocks up. Any mob death
  nearby that leaves meat pulls every vulture within 48 blocks: they converge,
  circle ~10s, land, and strip the drops — a "something died here" beacon you can
  read from across the biome. Only ever pecks players below 3 hearts. Never spawns
  indoors; drifts away and despawns if never interacted with.
- **Snake** — coiled and near-invisible until you're 4 blocks away (rattle warning),
  strikes only inside 2 — careful players are never bitten. Species: `viper`
  (desert/badlands, **venom** block: Poison II 8s), `python` (jungle, brief
  constricting grab — the crocodile's grab code, shared). Worldgen-only ambience,
  no drops.

## Roster (Phase 1, v0.1.0)

- **Gorilla** — jungle troops of 3–6 with exactly one **silverback** (bigger, +50%
  attack, silver saddle). Neutral until you hurt any troop member — then the whole
  troop retaliates. The silverback chest-beats on a 30–90s cadence and when hostile
  mobs close in (slows nearby monsters, never players). Tame with **melon slices**
  (1-in-3 per feed) into a wolf-style companion: sit, follow, teleports past 32
  blocks, defends you. Babies ride on adults' backs until grown. Idle adults
  occasionally tear a leaves block (needs `mobGriefing`). Species: `lowland`
  (jungle), `mountain` (windswept hills + grove, thicker coat, +4 health).
- **Crocodile** — swamp ambush predator: floats in water, lunges at prey in reach,
  drags it (slowness + damage over 2s), releases. Neutral on land. Species: `nile`
  (swamp), `saltwater` (mangrove, 20% bigger, harder-hitting).
- **Tortoise** — savanna/badlands, fully passive, `worldgen_only`: spawns only with
  newly generated chunks, never respawns, drops nothing — killing them permanently
  empties the area. When hit it tucks into its shell: +8 armor, immobile, 10s.
  Breeds with sweet berries.
- **Leopard** — jungle stalker: crouch-approaches chickens, rabbits and baby animals,
  pounces with a leap; only attacks players already below half health; backs off when
  two or more players are close. Species: `leopard` (jungle), `snow` (grove/peaks,
  white coat, +4 health).

## The species system

Every tunable lives in `data/menagerie/species/*.json`: biomes (tags preferred),
spawn weight, group size, `worldgen_only`, health/attack/speed/scale, tame & breed
items, texture, and a per-animal `special` block (chest-beat cadence, grab length,
shell armor…). Species are hot-reloaded (`/reload`) and datapack-extensible.

Debug commands (`/menagerie census|troops|silverback`) print species/troop state —
built for the headless test battery in `VERIFY.md`.

## Known limitations

- Spawn **weights** are baked into biomes at world load: `/reload` can lower a
  species' effective natural-spawn rate (weight 0 = stop respawns) but raising a
  weight needs a server restart. Everything else (stats, items, special knobs) is
  fully live.
- No spawn eggs yet; use `/summon` (plain summon picks the biome-correct species).
- No loot tables yet — the roster drops nothing by design in Phase 1.
- `/summon` with extra NBT skips species finalization (vanilla behavior); plain
  `/summon` does the right thing.

## Art & sound

Vanilla-derived by doctrine: every texture is painted by `tools/gen-textures.js` onto
our own cube-model UV layouts using palettes **sampled from vanilla mob textures**
(wolf/panda grays, turtle greens, ocelot golds, polar bear whites) — no pixels copied
from other mods. All sounds are pitch-shifted references to vanilla sound events via
`sounds.json`; no external audio. License research for the referenced mods is in
`RESEARCH.md` (Alex's Mobs and Untamed Wilds were studied as *patterns only*).

## Building

JDK 25. `./gradlew build` → `build/libs/menagerie-0.1.0.jar`. Dev client:
`./gradlew runClient`. Render regression test: `./gradlew runGametest` (screenshots in
`build/run-gametest/screenshots/`).

## Changelog

### 0.2.0 (2026-08-15)
- Four new animals: hippo, grizzly bear, vulture, snake (7 new species files, 14 total
  across 8 animals).
- Three new **registry systems**, all additive JSON blocks: `territory` (water/spawn-
  anchored aggro zones), `diet` (hunted entity ids + scavenging), `venom` (effect on
  strike). Phase 1 species files load byte-for-byte unchanged. `size_scale` accepted
  as an alias for `scale`; optional `knockback` stat feeds ATTACK_KNOCKBACK.
- Phantom-style flight (vulture), boat destruction (hippo), fishing/sleep/cub-defense
  (grizzly), telegraphed venom strikes (snake); crocodile grab refactored into shared
  `GrabHold` (used by the python) with a regression test.
- Fixed during verification: a worldgen chunk deadlock (territory claims during
  finalizeSpawn), vulture despawn/landing behavior, snake flee-predicate slot.

### 0.1.0 (2026-08-15)
- Initial release: gorilla (troops/silverback/chest-beat/taming/babies), crocodile
  (ambush + grab), tortoise (shell, worldgen-only), leopard (stalker/opportunist).
- Data-driven species registry, 7 species across 4 animals, biome-tag spawn routing
  through Fabric BiomeModifications.
- Vanilla-style cube models with programmatic animation (knuckle-walk, chest-beat,
  lunge, shell-retract, crouch + pounce); generated vanilla-palette textures.
