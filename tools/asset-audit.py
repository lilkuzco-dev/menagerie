#!/usr/bin/env python3
"""
asset-audit — the checkerboard killer.

Statically enumerates every asset path the mod can ever REQUEST at runtime (the
"requestable set"), then proves each one exists in what actually ships. A build
that would render a missing-texture checkerboard fails here instead of on a
player's screen.

Three failure classes, all fatal:
  (a) requested-but-missing   — the hippo bug: code/data asks for a path nothing ships
  (b) shipped-but-unreachable — dead weight, or the tell of broken wiring
  (c) index-scheme mismatch   — 0- vs 1-indexed numbered families, gaps in a run

Sources of REQUESTS:
  * species JSON            texture, textures[], variant_rolls.*.texture
  * field guide             textures/gui/guide/<entity>_<species>[_silhouette].png
  * Java literals           any "...png" string in src/**.java
  * Java prefix families    "..._" fragments that get an index appended (declared below)
  * sounds.json             menagerie: file refs -> sounds/<name>.ogg
  * sound events            every SoundEvent registered in Java needs a sounds.json key
  * subtitles               every sounds.json subtitle needs a lang key
  * models/blockstates      menagerie: model + texture refs, recursively

ANCHORS: the audit asserts its own inputs (species count, family coverage, the
literal scan finding known files). A refactor that makes the scanner silently
match nothing fails the build rather than passing vacuously.

Usage:
  tools/asset-audit.py [--jar build/libs/menagerie-<v>.jar]
                       [--vanilla-jar <minecraft-client.jar>]
                       [--matrix AUDIT-matrix.md]
"""

import argparse
import glob
import json
import os
import re
import sys
import zipfile
from collections import defaultdict

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
RES = os.path.join(ROOT, "src", "main", "resources")
NS = "menagerie"

# ---------------------------------------------------------------------------
# Declared prefix families. A Java literal that contains "textures/" but does
# not end in ".png" is a concatenation site: code appends an index. Every such
# prefix MUST appear here with the exact index set the code rolls, or the audit
# fails. That is what makes an index-scheme mismatch (class c) impossible to
# introduce silently.
# ---------------------------------------------------------------------------
FAMILIES = {
    # LionEyeLayer: VARIANTS = 4, rolled 0..3 via Math.floorMod(seed, 4)
    "textures/entity/lion/eye/lefteye_": {
        "indices": list(range(4)),
        "source": "LionEyeLayer.VARIANTS",
    },
    "textures/entity/lion/eye/righteye_": {
        "indices": list(range(4)),
        "source": "LionEyeLayer.VARIANTS",
    },
    # GuideScreen builds "<entity>_<species>[_silhouette].png" from the live
    # registry; expanded from the species files rather than a fixed index set.
    "textures/gui/guide/": {"indices": None, "source": "GuideScreen.icon"},
}

# Entity-texture directories that are allowed to hold files nothing requests,
# with the reason. Anything else unreferenced is a class (b) failure.
UNREACHABLE_ALLOWED = {}


class Audit:
    def __init__(self, shipped, vanilla):
        self.shipped = shipped          # set of resource paths present in the build
        self.vanilla = vanilla          # set of vanilla asset paths, or None
        self.requests = defaultdict(list)   # path -> [why, ...]
        self.errors = []
        self.warnings = []
        self.anchors = []

    # -- recording -----------------------------------------------------------
    def request(self, ident, why):
        """Record a request for a namespaced asset id like menagerie:textures/x.png."""
        ns, _, path = ident.partition(":")
        if not path:
            ns, path = "minecraft", ns
        self.requests[f"{ns}:{path}"].append(why)

    def fail(self, cls, msg):
        self.errors.append((cls, msg))

    def warn(self, msg):
        self.warnings.append(msg)

    def anchor(self, ok, msg):
        self.anchors.append((bool(ok), msg))
        if not ok:
            self.fail("anchor", f"ANCHOR FAILED: {msg}")

    # -- resolution ----------------------------------------------------------
    def resolve(self):
        for ident, whys in sorted(self.requests.items()):
            ns, path = ident.split(":", 1)
            if ns == NS:
                if f"assets/{NS}/{path}" not in self.shipped:
                    self.fail("a", f"requested-but-missing: {ident}\n"
                                   f"        requested by: {'; '.join(sorted(set(whys)))}")
            elif ns == "minecraft":
                if self.vanilla is None:
                    # never downgrade this to a warning: the checkerboard that started
                    # this audit was a vanilla path Mojang had renamed
                    self.fail("a", f"unverifiable vanilla reference {ident} — pass "
                                   f"--vanilla-jar so it can be checked\n"
                                   f"        requested by: {'; '.join(sorted(set(whys)))}")
                elif f"assets/minecraft/{path}" not in self.vanilla:
                    self.fail("a", f"requested-but-missing (vanilla): {ident}\n"
                                   f"        requested by: {'; '.join(sorted(set(whys)))}\n"
                                   f"        no such path in the Minecraft assets")
            # other namespaces: another mod's problem, not ours

    def unreachable(self):
        requested = {p.split(":", 1)[1] for p in self.requests if p.startswith(NS + ":")}
        for res in sorted(self.shipped):
            if not res.startswith(f"assets/{NS}/"):
                continue
            rel = res[len(f"assets/{NS}/"):]
            if not (rel.startswith("textures/") or rel.startswith("sounds/")):
                continue
            if rel in requested or rel in UNREACHABLE_ALLOWED:
                continue
            self.fail("b", f"shipped-but-unreachable: {NS}:{rel}")

    def index_schemes(self):
        """Class (c): numbered families must be contiguous and start where code rolls."""
        runs = defaultdict(set)
        pat = re.compile(r"^(.*?)(\d+)(\.png|\.ogg)$")
        for ident in self.requests:
            ns, path = ident.split(":", 1)
            if ns != NS:
                continue
            m = pat.match(path)
            if m:
                runs[(m.group(1), m.group(3))].add(int(m.group(2)))
        for (prefix, ext), idx in sorted(runs.items()):
            if len(idx) < 2:
                continue
            lo, hi = min(idx), max(idx)
            missing = sorted(set(range(lo, hi + 1)) - idx)
            if missing:
                self.fail("c", f"index-scheme gap in {NS}:{prefix}<n>{ext} — "
                               f"requested {lo}..{hi} but {missing} are absent from the run")
            # cross-check what is actually shipped under the same prefix
            shipped_idx = set()
            for res in self.shipped:
                if not res.startswith(f"assets/{NS}/{prefix}"):
                    continue
                tail = res[len(f"assets/{NS}/{prefix}"):]
                if tail.endswith(ext) and tail[: -len(ext)].isdigit():
                    shipped_idx.add(int(tail[: -len(ext)]))
            extra = sorted(shipped_idx - idx)
            if extra:
                self.fail("c", f"index-scheme mismatch in {NS}:{prefix}<n>{ext} — "
                               f"ships {sorted(shipped_idx)} but code/data only ever "
                               f"requests {sorted(idx)} (indices {extra} unreachable)")


def read_shipped(jar_path):
    if jar_path:
        with zipfile.ZipFile(jar_path) as z:
            return {n for n in z.namelist() if not n.endswith("/")}
    out = set()
    for base, _, files in os.walk(RES):
        for f in files:
            rel = os.path.relpath(os.path.join(base, f), RES)
            out.add(rel.replace(os.sep, "/"))
    return out


def read_vanilla(jar_path):
    if not jar_path or not os.path.exists(jar_path):
        return None
    with zipfile.ZipFile(jar_path) as z:
        return {n for n in z.namelist() if n.startswith("assets/") and not n.endswith("/")}


def read_vanilla_ids(jar_path):
    """
    Vanilla item/block/entity/effect ids, via assets/minecraft/lang/en_us.json.

    A referenced id that vanilla does not have fails silently at runtime — the 0.4.1
    bait bug (a species whose breed item did not exist) and the pig-texture
    checkerboard are the same shape. Cheap to check, so it is checked.
    """
    if not jar_path or not os.path.exists(jar_path):
        return None
    with zipfile.ZipFile(jar_path) as z:
        lang = json.loads(z.read("assets/minecraft/lang/en_us.json"))
    out = {}
    for kind in ("item", "block", "entity", "effect"):
        out[kind] = {k.split(".", 2)[2] for k in lang if k.startswith(f"{kind}.minecraft.")}
    return out


def read_vanilla_sound_events(assets_dir):
    """Vanilla sound EVENT ids, from the downloaded asset index (not the jar)."""
    if not assets_dir or not os.path.isdir(assets_dir):
        return None
    indexes = sorted(glob.glob(os.path.join(assets_dir, "indexes", "*.json")))
    if not indexes:
        return None
    objects = json.load(open(indexes[-1]))["objects"]
    entry = objects.get("minecraft/sounds.json")
    if not entry:
        return None
    h = entry["hash"]
    path = os.path.join(assets_dir, "objects", h[:2], h)
    if not os.path.exists(path):
        return None
    return set(json.load(open(path)).keys())


def species_files():
    d = os.path.join(RES, "data", NS, "species")
    return sorted(os.path.join(d, f) for f in os.listdir(d) if f.endswith(".json"))


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--jar")
    ap.add_argument("--vanilla-jar")
    ap.add_argument("--vanilla-assets",
                    help="loom assets dir (indexes/ + objects/), for vanilla sound events")
    ap.add_argument("--matrix")
    args = ap.parse_args()

    shipped = read_shipped(args.jar)
    vanilla = read_vanilla(args.vanilla_jar)
    vanilla_ids = read_vanilla_ids(args.vanilla_jar)
    vanilla_sounds = read_vanilla_sound_events(args.vanilla_assets)
    a = Audit(shipped, vanilla)

    where = args.jar if args.jar else "src/main/resources"
    print(f"asset-audit: auditing {where}")
    print(f"             {len(shipped)} shipped resources"
          + (f", vanilla assets from {os.path.basename(args.vanilla_jar)}" if vanilla else ""))

    # ---- 1. species JSON -----------------------------------------------------
    species = []
    for path in species_files():
        with open(path) as fh:
            doc = json.load(fh)
        name = os.path.basename(path)[:-5]
        entity = doc["entity"].split(":")[-1]
        sp = doc.get("species", name)
        species.append((name, entity, sp, doc))

        a.request(doc["texture"], f"{name}.texture")
        for i, t in enumerate(doc.get("textures", [])):
            a.request(t, f"{name}.textures[{i}]")
        for vname, v in doc.get("variant_rolls", {}).items():
            a.request(v["texture"], f"{name}.variant_rolls.{vname}")

        # ---- 2. field guide icons (GuideScreen.icon) -------------------------
        base = f"textures/gui/guide/{entity}_{sp}"
        a.request(f"{NS}:{base}.png", f"guide icon ({name}, discovered)")
        a.request(f"{NS}:{base}_silhouette.png", f"guide icon ({name}, undiscovered)")

    a.anchor(len(species) >= 16, f"found {len(species)} species files (expected >= 16)")
    a.anchor(any(d.get("variant_rolls") for _, _, _, d in species),
             "at least one species declares variant_rolls")
    a.anchor(any(d.get("textures") for _, _, _, d in species),
             "at least one species declares a multi-texture fur table")

    # ---- 3. Java literals + declared prefix families -------------------------
    lit_re = re.compile(r'"([^"\n]*?textures/[^"\n]*?)"')
    seen_literals, seen_prefixes = 0, set()
    for base, _, files in os.walk(os.path.join(ROOT, "src")):
        for f in files:
            if not f.endswith(".java"):
                continue
            fp = os.path.join(base, f)
            with open(fp) as fh:
                src = fh.read()
            src_lines = src.splitlines()

            # String constants used as path prefixes, e.g.
            #   private static final String T = "menagerie:textures/entity/";
            #   ... T + "gorilla/default.png"
            consts = dict(re.findall(
                r'static final String (\w+)\s*=\s*"([^"\n]*)"', src))
            resolved_prefixes = set()
            for cname, cval in consts.items():
                for suffix in re.findall(cname + r'\s*\+\s*"([^"\n]+)"', src):
                    whole = cval + suffix
                    if "textures/" not in whole:
                        continue
                    resolved_prefixes.add(cval)
                    seen_literals += 1
                    ident = whole if re.match(r"^[a-z0-9_]+:", whole) else f"{NS}:{whole}"
                    a.request(ident, f"{f} {cname} + {suffix!r}")

            for lineno, line in enumerate(src_lines, 1):
                for lit in lit_re.findall(line):
                    if lit in resolved_prefixes:
                        continue  # a constant prefix already expanded above
                    if lit.endswith(".png"):
                        seen_literals += 1
                        # the namespace is decided by the call wrapping the literal:
                        # Menagerie.id(..) is ours, withDefaultNamespace/"minecraft" is vanilla
                        ns = ("minecraft"
                              if ("withDefaultNamespace" in line or '"minecraft"' in line)
                              else NS)
                        a.request(f"{ns}:{lit}", f"{f}:{lineno} literal")
                    else:
                        if lit not in FAMILIES:
                            a.fail("c", f"undeclared prefix family {lit!r} in {f}:{lineno} — "
                                        f"add it to FAMILIES in tools/asset-audit.py with its "
                                        f"exact index set, or the run cannot be validated")
                            continue
                        seen_prefixes.add(lit)
                        fam = FAMILIES[lit]
                        if fam["indices"] is not None:
                            for i in fam["indices"]:
                                a.request(f"{NS}:{lit}{i}.png",
                                          f"{f}:{lineno} family {lit}<n> ({fam['source']})")

    a.anchor(seen_literals >= 2, f"Java literal scan found {seen_literals} .png literals (expected >= 2)")
    a.anchor("textures/entity/lion/eye/lefteye_" in seen_prefixes,
             "lion eye prefix family was reached by the literal scan")

    # ---- 4. sounds -----------------------------------------------------------
    with open(os.path.join(RES, "assets", NS, "sounds.json")) as fh:
        sounds = json.load(fh)
    with open(os.path.join(RES, "assets", NS, "lang", "en_us.json")) as fh:
        lang = json.load(fh)

    vanilla_event_refs = 0
    for event, body in sounds.items():
        for entry in body.get("sounds", []):
            if isinstance(entry, dict):
                nm, kind = entry["name"], entry.get("type", "sound")
            else:
                nm, kind = entry, "sound"
            if kind == "event":
                # a reference to another sound EVENT, not to a file. Vanilla renames
                # these between versions exactly like texture paths do, and a bad one
                # is a silent no-sound rather than an error.
                target = nm.split(":", 1)[-1]
                if nm.startswith("menagerie:"):
                    if target not in sounds:
                        a.fail("a", f"sounds.json {event} redirects to menagerie event "
                                    f"{target!r}, which sounds.json does not define")
                else:
                    vanilla_event_refs += 1
                    if vanilla_sounds is None:
                        a.fail("a", f"cannot verify vanilla sound event {nm!r} "
                                    f"(pass --vanilla-assets)")
                    elif target not in vanilla_sounds:
                        a.fail("a", f"sounds.json {event} references vanilla sound event "
                                    f"{nm!r}, which does not exist in this Minecraft")
                continue
            ns, _, rel = nm.partition(":")
            if not rel:
                ns, rel = "minecraft", ns
            if ns == NS:
                a.request(f"{NS}:sounds/{rel}.ogg", f"sounds.json {event}")
        sub = body.get("subtitle")
        if sub and sub not in lang:
            a.fail("a", f"sounds.json {event} subtitle {sub!r} has no en_us.json key")

    # every registered SoundEvent must have a sounds.json entry, or the client
    # logs "Unable to play unknown soundEvent" and the animal is mute
    ms = open(os.path.join(ROOT, "src", "main", "java", "dev", "lilkuzco", NS,
                           "MenagerieSounds.java")).read()
    registered = set(re.findall(r'register\("([^"]+)"\)', ms))
    a.anchor(len(registered) >= 40,
             f"MenagerieSounds scan found {len(registered)} sound events (expected >= 40)")
    for ev in sorted(registered):
        if ev not in sounds:
            a.fail("a", f"sound event {NS}:{ev} is registered in Java but absent from sounds.json")
    for ev in sorted(sounds):
        if ev not in registered:
            a.fail("b", f"sounds.json defines {ev} but no Java SoundEvent registers it")

    # ---- 4b. lang coverage ---------------------------------------------------
    # A registered thing with no lang key renders as a raw key ("entity.menagerie.x")
    # in nameplates, the guide and cage labels. Nothing else catches it.
    ents = re.findall(r'register\("(\w+)",\s*\n?\s*EntityType\.Builder',
                      open(os.path.join(ROOT, "src", "main", "java", "dev", "lilkuzco", NS,
                                        "entity", "MenagerieEntities.java")).read())
    reg_src = (open(os.path.join(ROOT, "src", "main", "java", "dev", "lilkuzco", NS,
                                 "MenagerieItems.java")).read()
               + open(os.path.join(ROOT, "src", "main", "java", "dev", "lilkuzco", NS,
                                   "block", "MenagerieBlocks.java")).read())
    things = set(re.findall(r'(?:register|Menagerie\.id)\("([a-z0-9_]+)"', reg_src))
    a.anchor(len(ents) >= 9, f"entity registry scan found {len(ents)} entities (expected >= 9)")
    a.anchor(len(things) >= 3, f"item/block registry scan found {len(things)} (expected >= 3)")
    for e in ents:
        if f"entity.{NS}.{e}" not in lang:
            a.fail("a", f"entity {NS}:{e} has no en_us.json key entity.{NS}.{e}")
    for t in sorted(things):
        if f"item.{NS}.{t}" not in lang and f"block.{NS}.{t}" not in lang:
            a.fail("a", f"{NS}:{t} has no en_us.json key (item.{NS}.{t} / block.{NS}.{t})")

    # ---- 4c. vanilla ids named by our data -----------------------------------
    # A breed item or forage block that vanilla does not have fails SILENTLY: the
    # animal simply can never be fed. That is the 0.4.1 bait bug's exact shape.
    if vanilla_ids is not None:
        checked_ids = 0

        def vcheck(kind, pools, ref, where):
            nonlocal checked_ids
            if not ref.startswith("minecraft:"):
                return
            checked_ids += 1
            name = ref.split(":", 1)[1]
            if not any(name in vanilla_ids[p] for p in pools):
                a.fail("a", f"{where}: {kind} {ref!r} does not exist in this Minecraft")

        for name, entity, sp, doc in species:
            for it in (doc.get("breeding") or {}).get("items", []):
                vcheck("breed item", ("item", "block"), it, name)
            for key in ("tame_item", "breed_item"):
                if doc.get(key):
                    vcheck(key, ("item", "block"), doc[key], name)
            for b in (doc.get("forage") or {}).get("blocks", []):
                vcheck("forage block", ("block",), b, name)
            for h in (doc.get("diet") or {}).get("hunts", []):
                vcheck("diet hunt", ("entity",), h, name)
            if doc.get("venom"):
                vcheck("venom effect", ("effect",), doc["venom"]["effect"], name)
        d = os.path.join(RES, "data", NS, "recipe")
        for f in sorted(os.listdir(d)):
            blob = open(os.path.join(d, f)).read()
            for ref in sorted(set(re.findall(r'"(minecraft:[a-z0-9_]+)"', blob))):
                if ref.startswith("minecraft:crafting"):
                    continue  # recipe type, not an ingredient
                vcheck("recipe id", ("item", "block"), ref, f"recipe/{f}")
        a.anchor(checked_ids >= 10,
                 f"vanilla-id check covered {checked_ids} references (expected >= 10)")
    else:
        a.warn("vanilla id check skipped (no --vanilla-jar)")

    # ---- 5. models / blockstates / item defs ---------------------------------
    ref_re = re.compile(r'"(' + NS + r':[a-z0-9_/]+)"')
    for sub in ("blockstates", "items", os.path.join("models", "block"), os.path.join("models", "item")):
        d = os.path.join(RES, "assets", NS, sub)
        if not os.path.isdir(d):
            continue
        for f in sorted(os.listdir(d)):
            if not f.endswith(".json"):
                continue
            with open(os.path.join(d, f)) as fh:
                doc = json.load(fh)
            blob = json.dumps(doc)
            for ref in set(ref_re.findall(blob)):
                path = ref.split(":", 1)[1]
                # a ref under a "textures" object is a texture; otherwise a model
                if f'"{ref}"' in json.dumps(doc.get("textures", {})):
                    a.request(f"{NS}:textures/{path}.png", f"{sub}/{f} texture ref")
                else:
                    a.request(f"{NS}:models/{path}.json", f"{sub}/{f} model ref")
            # models also carry their own texture map
            for key, val in (doc.get("textures") or {}).items():
                if val.startswith(NS + ":"):
                    a.request(f"{NS}:textures/{val.split(':', 1)[1]}.png", f"{sub}/{f} textures.{key}")

    # icon.png is referenced from fabric.mod.json
    with open(os.path.join(RES, "fabric.mod.json")) as fh:
        modjson = fh.read()
    icon = json.loads(modjson.replace("${version}", "0.0.0")).get("icon")
    if icon:
        a.request(f"{NS}:{icon.split('/', 2)[-1]}" if icon.startswith("assets/") else f"{NS}:{icon}",
                  "fabric.mod.json icon")

    # ---- verdict -------------------------------------------------------------
    a.resolve()
    a.index_schemes()
    a.unreachable()

    print(f"             {len(a.requests)} distinct asset paths in the requestable set")
    for ok, msg in a.anchors:
        print(f"             anchor {'OK  ' if ok else 'FAIL'} {msg}")

    if args.matrix:
        write_matrix(args.matrix, species, a)

    for w in a.warnings:
        print(f"  WARN  {w}")

    if a.errors:
        print()
        by_cls = defaultdict(list)
        for cls, msg in a.errors:
            by_cls[cls].append(msg)
        names = {"a": "requested-but-missing", "b": "shipped-but-unreachable",
                 "c": "index-scheme mismatch", "anchor": "audit anchor"}
        for cls in ("a", "b", "c", "anchor"):
            for msg in by_cls.get(cls, []):
                print(f"  FAIL [{cls}: {names[cls]}] {msg}")
        print(f"\n!! asset-audit FAILED — {len(a.errors)} problem(s)")
        return 1

    print("\nasset-audit: PASS — every requestable asset path resolves")
    return 0


def write_matrix(path, species, a):
    by_species = defaultdict(list)
    for ident, whys in a.requests.items():
        for why in whys:
            by_species[why.split(".")[0].split(" ")[0]].append(ident)
    lines = ["| species | textures requested | variants | guide icon | verdict |",
             "|---|---|---|---|---|"]
    missing = {m.split(": ")[1].split("\n")[0] for cls, m in a.errors if cls == "a"}
    for name, entity, sp, doc in species:
        reqs = [doc["texture"]] + list(doc.get("textures", [])) + \
               [v["texture"] for v in doc.get("variant_rolls", {}).values()]
        reqs = sorted(set(reqs))
        guide = f"{NS}:textures/gui/guide/{entity}_{sp}.png"
        bad = [r for r in reqs + [guide] if r in missing]
        lines.append(f"| `{name}` | {len(reqs)} | {len(doc.get('variant_rolls', {}))} | "
                     f"`{entity}_{sp}` | {'**FAIL** ' + ', '.join(bad) if bad else 'PASS'} |")
    with open(path, "w") as fh:
        fh.write("\n".join(lines) + "\n")


if __name__ == "__main__":
    sys.exit(main())
