#!/usr/bin/env python3
"""
spawn-lint — the ocean-floor-hippo killer.

Every species' spawn rules are checked against the shape of the spawn system and
against the animal's real ecology. A hippo whose biome list can resolve to open
ocean, a species whose texture belongs to a different animal, or a rarity value
outside the tier table all fail the build here.

Fatal rules
  S1  every species declares at least one biome entry
  S2  no ocean / deep-ocean biome tag unless the species declares "aquatic": true
      (a water biome plus a ground-seeking placement is exactly how a hippo ends
      up standing on the sea floor)
  S3  waterline-placed entities (parsed from MenagerieEntities.java) must declare
      "aquatic": true on every one of their species, and non-waterline entities
      must never declare it — the flag and the Java placement cannot drift apart
  S4  rarity names a tier that exists in rarity.json
  S5  explicit weight / group_size / nearby_cap stay inside the tier table's bounds
  S6  group_min >= 1 and group_min <= group_max
  S7  every texture the species names lives under textures/entity/<its own entity>/
      — a static guard against a texture rendering on the wrong animal's model
  S8  exclude_biomes entries are well-formed and actually narrow something

Advisory (printed, non-fatal)
  A1  raw biome ids where a tag exists: Terralith's ~85 biomes join the vanilla
      minecraft:is_* tags, so a raw id silently excludes all of them
  A2  biome families outside the animal's ecology table

Usage:
  tools/spawn-lint.py [--strict] [--resolve <jar> [<jar> ...]] [--report FILE]
    --resolve   additionally resolve every biome tag through the given jars
                (vanilla + Terralith + Fabric API) and report the concrete biome
                list per species. Reporting only; never changes the verdict.
"""

import argparse
import json
import os
import re
import sys
import zipfile
from collections import defaultdict

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
RES = os.path.join(ROOT, "src", "main", "resources")
SPECIES_DIR = os.path.join(RES, "data", "menagerie", "species")
RARITY = os.path.join(RES, "data", "menagerie", "menagerie_config", "rarity.json")
ENTITIES_JAVA = os.path.join(ROOT, "src", "main", "java", "dev", "lilkuzco",
                             "menagerie", "entity", "MenagerieEntities.java")

# Biome entries that mean "open salt water". A land animal reaching any of these
# is the ocean-floor-hippo bug.
OCEAN = {"#minecraft:is_ocean", "#minecraft:is_deep_ocean", "#c:is_ocean",
         "#c:is_deep_ocean", "#c:is_shallow_ocean", "#c:is_aquatic"}
OCEAN_PREFIX = ("minecraft:ocean", "minecraft:deep_", "minecraft:frozen_ocean",
                "minecraft:cold_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean")

# The ecology table, executable. Each entity may only draw biomes from these
# families; anything else is an A2 advisory naming the offending entry.
ECOLOGY = {
    "hippo":     {"#minecraft:is_river", "#c:is_river", "#c:is_swamp",
                  "minecraft:swamp", "minecraft:mangrove_swamp"},
    "crocodile": {"#minecraft:is_river", "#c:is_river", "#c:is_swamp",
                  "#minecraft:is_beach", "#c:is_beach",
                  "minecraft:swamp", "minecraft:mangrove_swamp"},
    "lion":      {"#minecraft:is_savanna", "#c:is_savanna", "#minecraft:is_badlands",
                  "#c:is_badlands"},
    "leopard":   {"#minecraft:is_jungle", "#c:is_jungle", "#minecraft:is_mountain",
                  "#c:is_mountain", "minecraft:grove"},
    # terralith:* entries are montane JUNGLE, i.e. genuine mountain-gorilla habitat.
    # An id from an absent mod simply never matches, so naming it is safe.
    # #minecraft:is_hill (windswept highlands) is deliberately allowed: removing it in
    # 0.4.4 halved gorilla habitat and players stopped finding them. minecraft:grove is
    # NOT allowed — a snowy conifer forest was the genuinely wrong entry.
    "gorilla":   {"#minecraft:is_jungle", "#c:is_jungle", "minecraft:jungle",
                  "minecraft:bamboo_jungle", "minecraft:sparse_jungle",
                  "terralith:jungle_mountains", "terralith:rocky_jungle",
                  "#minecraft:is_hill", "#c:is_hill"},
    "grizzly":   {"#minecraft:is_forest", "#c:is_forest", "#minecraft:is_taiga",
                  "#c:is_taiga", "#minecraft:is_mountain", "#c:is_mountain"},
    "tortoise":  {"#minecraft:is_savanna", "#c:is_savanna", "#minecraft:is_badlands",
                  "#c:is_badlands", "#c:is_desert", "minecraft:desert",
                  "#minecraft:is_beach", "#c:is_beach"},
    "snake":     {"#minecraft:is_badlands", "#c:is_badlands", "#c:is_desert",
                  "minecraft:desert", "#minecraft:is_jungle", "#c:is_jungle",
                  "#minecraft:is_savanna", "#c:is_savanna"},
    "vulture":   {"#minecraft:is_badlands", "#c:is_badlands", "#c:is_desert",
                  "minecraft:desert", "#minecraft:is_savanna", "#c:is_savanna"},
}

# Raw ids that a tag now covers -> the tag to prefer (A1 advisory).
PREFER_TAG = {
    "minecraft:swamp": "#c:is_swamp",
    "minecraft:mangrove_swamp": "#c:is_swamp",
    "minecraft:desert": "#c:is_desert",
    "minecraft:beach": "#minecraft:is_beach",
    "minecraft:snowy_beach": "#minecraft:is_beach",
    "minecraft:jagged_peaks": "#minecraft:is_mountain",
    "minecraft:frozen_peaks": "#minecraft:is_mountain",
    "minecraft:snowy_slopes": "#minecraft:is_mountain",
    "minecraft:stony_peaks": "#minecraft:is_mountain",
}


class Lint:
    def __init__(self):
        self.errors = []
        self.advisories = []
        self.anchors = []

    def fail(self, rule, msg):
        self.errors.append((rule, msg))

    def note(self, rule, msg):
        self.advisories.append((rule, msg))

    def anchor(self, ok, msg):
        self.anchors.append((bool(ok), msg))
        if not ok:
            self.fail("anchor", f"ANCHOR FAILED: {msg}")


def waterline_entities():
    """Parse MenagerieEntities.java for which entities skip the on-ground rule."""
    src = open(ENTITIES_JAVA).read()
    out = set()
    for m in re.finditer(r"SpawnPlacements\.register\(\s*(\w+)\s*,\s*"
                         r"SpawnPlacementTypes\.(\w+)", src):
        entity, placement = m.group(1), m.group(2)
        if placement != "ON_GROUND":
            out.add(entity.lower())
    return out, src


def load_tags(jars):
    """namespace:tag -> raw value list, merged across every jar (additive tags)."""
    tags = defaultdict(list)
    pat = re.compile(r"^data/([^/]+)/tags/worldgen/biome/(.+)\.json$")
    for jar in jars:
        if not os.path.exists(jar):
            continue
        with zipfile.ZipFile(jar) as z:
            for name in z.namelist():
                m = pat.match(name)
                if not m:
                    continue
                doc = json.loads(z.read(name))
                key = f"{m.group(1)}:{m.group(2)}"
                if doc.get("replace"):
                    tags[key] = []
                for v in doc.get("values", []):
                    tags[key].append(v if isinstance(v, str) else v.get("id"))
    return tags


def resolve(entry, tags, seen=None):
    """Expand a biome entry (#tag or raw id) to a set of concrete biome ids."""
    seen = seen or set()
    if not entry.startswith("#"):
        return {entry}
    key = entry[1:]
    if key in seen:
        return set()
    seen.add(key)
    out = set()
    for v in tags.get(key, []):
        out |= resolve(v, tags, seen) if v.startswith("#") else {v}
    return out


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--resolve", nargs="*", default=None,
                    help="jars to resolve biome tags through (reporting only)")
    ap.add_argument("--report")
    args = ap.parse_args()

    lint = Lint()
    tiers = json.load(open(RARITY))["tiers"]
    water, entities_src = waterline_entities()

    lint.anchor(len(tiers) >= 5, f"rarity.json defines {len(tiers)} tiers (expected >= 5)")

    # S10 — the ladder must stay monotonic. Retuning only the tiers currently in use is
    # the natural way to do it and quietly leaves "common" rarer than "uncommon".
    LADDER = ["ubiquitous", "common", "uncommon", "rare", "epic"]
    known = [t for t in LADDER if t in tiers]
    for a_name, b_name in zip(known, known[1:]):
        aw, bw = tiers[a_name]["weight"], tiers[b_name]["weight"]
        if aw < bw:
            lint.fail("S10", f"rarity ladder inverted: {a_name} weight {aw} is below "
                             f"{b_name} weight {bw} — a commoner tier must never be rarer")
    lint.anchor(len(known) == len(LADDER),
                f"rarity ladder covers all {len(LADDER)} named tiers ({len(known)} found)")
    lint.anchor(bool(water),
                f"MenagerieEntities scan found waterline placements: {sorted(water) or 'NONE'}")
    lint.anchor("SpawnPlacements.register" in entities_src,
                "MenagerieEntities.java still registers spawn placements")

    files = sorted(f for f in os.listdir(SPECIES_DIR) if f.endswith(".json"))
    lint.anchor(len(files) >= 16, f"found {len(files)} species files (expected >= 16)")

    rows = []
    tag_jars = args.resolve or []
    tags = load_tags(tag_jars) if tag_jars else {}
    if tag_jars:
        lint.anchor(len(tags) >= 10,
                    f"tag resolution loaded {len(tags)} biome tags from {len(tag_jars)} jar(s)")

    for f in files:
        name = f[:-5]
        doc = json.load(open(os.path.join(SPECIES_DIR, f)))
        entity = doc["entity"].split(":")[-1]
        biomes = doc.get("biomes", [])
        excludes = doc.get("exclude_biomes", [])
        aquatic = doc.get("aquatic", False)

        # S1
        if not biomes:
            lint.fail("S1", f"{name}: no biome entries — the species can never spawn")

        # S2 — two-pronged. "aquatic" means "wades at the water line", NOT "lives in
        # the sea": a hippo is aquatic and still must never see an ocean tag, which is
        # precisely the bug this rule exists to prevent.
        fam = ECOLOGY.get(entity, set())
        fam_has_ocean = any(x in OCEAN or x.startswith(OCEAN_PREFIX) for x in fam)
        for b in biomes:
            if b in OCEAN or b.startswith(OCEAN_PREFIX):
                if not aquatic:
                    lint.fail("S2", f"{name}: biome {b!r} is open ocean but the species "
                                    f'does not declare "aquatic": true')
                elif not fam_has_ocean:
                    lint.fail("S2", f"{name}: biome {b!r} is open ocean and {entity} has no "
                                    f'oceanic form — "aquatic": true licenses the water '
                                    f"line, not the sea floor")

        # S3
        if entity in water and not aquatic:
            lint.fail("S3", f"{name}: {entity} is registered with a waterline spawn "
                            f'placement but this species omits "aquatic": true')
        if entity not in water and aquatic:
            lint.fail("S3", f"{name}: declares \"aquatic\": true but {entity} uses the "
                            f"on-ground spawn placement")

        # S4 / S5
        rarity = doc.get("rarity", "")
        if rarity and rarity not in tiers:
            lint.fail("S4", f"{name}: rarity {rarity!r} is not a tier in rarity.json "
                            f"({', '.join(sorted(tiers))})")
        tier = tiers.get(rarity, {})
        wmax = max(t["weight"] for t in tiers.values())
        cmax = max(t["nearby_cap"] for t in tiers.values())
        if "weight" in doc and not (0 <= doc["weight"] <= wmax):
            lint.fail("S5", f"{name}: weight {doc['weight']} outside tier-table bounds "
                            f"0..{wmax}")
        if "nearby_cap" in doc and not (1 <= doc["nearby_cap"] <= cmax):
            lint.fail("S5", f"{name}: nearby_cap {doc['nearby_cap']} outside tier-table "
                            f"bounds 1..{cmax}")

        # S6
        if "group_size" in doc:
            lo, hi = doc["group_size"]
            if lo < 1 or lo > hi:
                lint.fail("S6", f"{name}: group_size {doc['group_size']} is not a valid "
                                f"1 <= min <= max range")

        # S7 — every texture must belong to this animal's own folder
        texs = [doc["texture"]] + list(doc.get("textures", [])) + \
               [v["texture"] for v in doc.get("variant_rolls", {}).values()]
        want = f"menagerie:textures/entity/{entity}/"
        for t in texs:
            if not t.startswith(want):
                lint.fail("S7", f"{name}: texture {t!r} does not live under {want} — "
                                f"a texture on the wrong animal's model renders smeared, "
                                f"not missing, so nothing else catches this")

        # S9 — baby_scale is a multiplier on top of vanilla's baby mesh transform, which
        # already halves the body. Anything meaningfully below 1.0 double-shrinks the
        # calf; 0.5 rendered them at a quarter of adult size.
        br = doc.get("breeding") or {}
        if "baby_scale" in br:
            bs = br["baby_scale"]
            if not (0.9 <= bs <= 1.6):
                lint.fail("S9", f"{name}: baby_scale {bs} is outside 0.9..1.6 — vanilla's "
                                f"BabyModelTransform already halves the body, so values "
                                f"below 1.0 stack a second shrink (1.0 = vanilla baby)")

        # S11 — size sanity. Attributes.SCALE multiplies the hitbox as well as the model,
        # so a runaway value makes an animal unspawnable (no headroom) or untouchable.
        scale = doc.get("size_scale", doc.get("scale", 1.0))
        if not (0.3 <= scale <= 3.0):
            lint.fail("S11", f"{name}: scale {scale} outside 0.3..3.0 — SCALE drives the "
                             f"hitbox too, so extremes break spawning or collision")
        if "size_scale" in doc and "scale" in doc:
            lint.fail("S11", f"{name}: declares BOTH size_scale and scale; size_scale "
                             f"silently wins and the other value is a lie")

        # S8
        for e in excludes:
            if not isinstance(e, str) or not e:
                lint.fail("S8", f"{name}: malformed exclude_biomes entry {e!r}")
            elif tags and e.startswith("#") and not resolve(e, tags):
                lint.fail("S8", f"{name}: exclude_biomes {e!r} resolves to nothing")

        # A1 / A2
        for b in biomes:
            if b in PREFER_TAG:
                lint.note("A1", f"{name}: raw id {b!r} excludes every Terralith variant — "
                                f"prefer {PREFER_TAG[b]}")
            if fam and b not in fam:
                lint.note("A2", f"{name}: biome {b!r} is outside the {entity} ecology "
                                f"family ({', '.join(sorted(fam))})")

        if tags:
            concrete = set()
            for b in biomes:
                concrete |= resolve(b, tags)
            for e in excludes:
                concrete -= resolve(e, tags)
            rows.append((name, entity, doc.get("species", name), sorted(concrete)))

    # ---- verdict -------------------------------------------------------------
    print(f"spawn-lint: {len(files)} species, waterline entities: {sorted(water)}")
    for ok, msg in lint.anchors:
        print(f"            anchor {'OK  ' if ok else 'FAIL'} {msg}")

    for rule, msg in lint.advisories:
        print(f"  note [{rule}] {msg}")

    if rows and args.report:
        with open(args.report, "w") as fh:
            fh.write("| species | entity | biomes resolved | vanilla | Terralith |\n")
            fh.write("|---|---|---:|---|---|\n")
            for name, entity, sp, concrete in rows:
                van = [b for b in concrete if b.startswith("minecraft:")]
                ter = [b for b in concrete if not b.startswith("minecraft:")]
                fh.write(f"| `{name}` | {entity} | {len(concrete)} | "
                         f"{', '.join(b.split(':')[1] for b in sorted(van)) or '—'} | "
                         f"{', '.join(sorted(ter)) or '—'} |\n")
        print(f"            wrote resolved biome report to {args.report}")

    if lint.errors:
        print()
        for rule, msg in lint.errors:
            print(f"  FAIL [{rule}] {msg}")
        print(f"\n!! spawn-lint FAILED — {len(lint.errors)} problem(s)")
        return 1

    print("\nspawn-lint: PASS — every species' spawn rules are inside their ecology")
    return 0


if __name__ == "__main__":
    sys.exit(main())
