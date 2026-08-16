#!/usr/bin/env python3
"""
gen-spawn-eggs — recolour the vanilla spawn egg into one icon per Menagerie animal.

26.2 dropped the old tintable template (`template_spawn_egg` + a tint), so every egg is
now its own flat texture and a mod cannot simply declare two colours any more. Drawing an
egg from scratch looked wrong next to vanilla's — the silhouette, the shading ramp and
the spot placement are what make the icon read as "spawn egg" at 16px.

So this takes a real vanilla egg as the SHAPE AND SHADING template and recolours it:

  * the template's opaque colours are clustered into two families (base and spots) around
    its two most common colours;
  * every colour's luminance relative to its family's seed is measured;
  * that same relative luminance is re-applied to the Menagerie colour for that family.

The result keeps vanilla's exact silhouette, shading ramp and spot pattern while wearing
the animal's own colours. Those colours are sampled from the animal's BODY TEXTURE rather
than hand-picked, so an egg always matches the animal it produces.

Deterministic: rerunning produces byte-identical files. See ASSETS-ORIGIN.md.

Usage: python3 tools/gen-spawn-eggs.py [--vanilla-jar <minecraft-client.jar>]
"""

import argparse
import json
import os
import sys
import zipfile
from collections import Counter

from PIL import Image

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
RES = os.path.join(ROOT, "src", "main", "resources", "assets", "menagerie")
TEMPLATE = "assets/minecraft/textures/item/cow_spawn_egg.png"

# each animal's egg colours come from its own body texture
SOURCE = {
    "gorilla": "gorilla/default.png",
    "crocodile": "crocodile/nile.png",
    "tortoise": "tortoise/savanna.png",
    "leopard": "leopard/leopard.png",
    "hippo": "hippo/river.png",
    "grizzly": "grizzly/grizzly.png",
    "vulture": "vulture/griffon.png",
    "lion": "lion/body_0.png",
    "snake": "snake/python.png",
}


def dist(a, b):
    return sum((a[i] - b[i]) ** 2 for i in range(3)) ** 0.5


def luminance(c):
    return 0.2126 * c[0] + 0.7152 * c[1] + 0.4 * c[2]


def palette_of(path):
    """Most common opaque colour, plus the most common clearly-different one."""
    im = Image.open(path).convert("RGBA")
    counts = Counter()
    for px in im.get_flattened_data():
        if px[3] < 200:
            continue
        if sum(px[:3]) < 90:  # near-black outline pixels would make every egg black
            continue
        counts[px[:3]] += 1
    ranked = counts.most_common()
    if not ranked:
        raise SystemExit(f"no usable colours in {path}")
    base = ranked[0][0]
    accent = next((c for c, _ in ranked[1:] if dist(c, base) > 55), None)
    if accent is None:
        accent = tuple(min(255, int(v * 1.5) + 30) for v in base)
    return base, accent


def recolour(template, base, accent):
    counts = Counter(p for p in template.get_flattened_data() if p[3] > 0)
    seeds = [c[:3] for c, _ in counts.most_common(2)]
    targets = [base, accent]
    out = Image.new("RGBA", template.size, (0, 0, 0, 0))
    src, dst = template.load(), out.load()
    for y in range(template.height):
        for x in range(template.width):
            px = src[x, y]
            if px[3] == 0:
                continue
            fam = 0 if dist(px[:3], seeds[0]) <= dist(px[:3], seeds[1]) else 1
            seed_lum = max(1.0, luminance(seeds[fam]))
            ratio = luminance(px[:3]) / seed_lum
            dst[x, y] = tuple(min(255, max(0, round(v * ratio))) for v in targets[fam]) + (px[3],)
    return out


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--vanilla-jar", default=os.path.expanduser(
        "~/.gradle/caches/fabric-loom/26.2/minecraft-client.jar"))
    ap.add_argument("--contact-sheet")
    args = ap.parse_args()

    if not os.path.exists(args.vanilla_jar):
        sys.exit(f"vanilla jar not found: {args.vanilla_jar}")
    with zipfile.ZipFile(args.vanilla_jar) as z, z.open(TEMPLATE) as fh:
        template = Image.open(fh).convert("RGBA")
        template.load()

    out_dir = os.path.join(RES, "textures", "item")
    os.makedirs(out_dir, exist_ok=True)
    report = {}
    sheet = Image.new("RGBA", (16 * len(SOURCE), 16), (0, 0, 0, 0))
    for i, (animal, rel) in enumerate(SOURCE.items()):
        base, accent = palette_of(os.path.join(RES, "textures", "entity", rel))
        egg = recolour(template, base, accent)
        egg.save(os.path.join(out_dir, f"{animal}_spawn_egg.png"))
        sheet.paste(egg, (i * 16, 0))
        report[animal] = {
            "base": "#%02X%02X%02X" % base,
            "accent": "#%02X%02X%02X" % accent,
            "sampled_from": rel,
        }
        print(f"  {animal:10s} base {report[animal]['base']}  "
              f"accent {report[animal]['accent']}  <- entity/{rel}")

    if args.contact_sheet:
        sheet.resize((sheet.width * 8, sheet.height * 8), Image.NEAREST).save(args.contact_sheet)
        print(f"  contact sheet -> {args.contact_sheet}")
    print(json.dumps(report), file=sys.stderr)
    return 0


if __name__ == "__main__":
    sys.exit(main())
