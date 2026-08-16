# Asset origin

Provenance for art that ships in this mod. Third-party *source jars* used for the
Renaissance transplants are inventoried in `assets/SOURCES.md` with licence verification;
`CREDITS.md` carries the attribution notices.

## Spawn eggs (added 0.4.7) — RECOLOURED FROM VANILLA

**These are derived from Minecraft's own art, not original work**, and that is a
deliberate choice worth stating plainly. 26.2 removed the old tintable
`template_spawn_egg`, so a mod can no longer supply two colours and let the game draw the
egg — every egg is now its own flat texture. A hand-drawn egg looked wrong beside vanilla
ones: the silhouette, shading ramp and spot pattern are what make a 16px icon read as
"spawn egg" at all.

`tools/gen-spawn-eggs.py` therefore takes **`assets/minecraft/textures/item/cow_spawn_egg.png`
as a shape-and-shading template** and recolours it. The template's opaque colours are
clustered into two families around its two most common colours; each colour's luminance
relative to its family seed is measured; that same relative luminance is re-applied in the
Menagerie colour. Vanilla's silhouette, shading and spot placement survive; only the hue
changes.

The colours are **sampled from each animal's own body texture** (most common opaque
colour as the base, most common clearly-different colour as the spots), so an egg always
matches the animal it produces rather than being hand-picked.

| egg | base | spots | palette sampled from |
|---|---|---|---|
| `gorilla_spawn_egg.png` | `#282831` | `#52525E` | `textures/entity/gorilla/default.png` |
| `crocodile_spawn_egg.png` | `#2E6E3D` | `#888E58` | `textures/entity/crocodile/nile.png` |
| `tortoise_spawn_egg.png` | `#295D1F` | `#C59D4C` | `textures/entity/tortoise/savanna.png` |
| `leopard_spawn_egg.png` | `#FFE377` | `#72552F` | `textures/entity/leopard/leopard.png` |
| `hippo_spawn_egg.png` | `#4D463F` | `#7C5F5D` | `textures/entity/hippo/river.png` |
| `grizzly_spawn_egg.png` | `#685851` | `#292022` | `textures/entity/grizzly/grizzly.png` |
| `vulture_spawn_egg.png` | `#313131` | `#D27977` | `textures/entity/vulture/griffon.png` |
| `lion_spawn_egg.png` | `#E3A95A` | `#C4812F` | `textures/entity/lion/body_0.png` |
| `snake_spawn_egg.png` | `#2D4414` | `#81C440` | `textures/entity/snake/python.png` |

Those body textures are themselves either our generated art (`tools/gen-textures.js`) or
public-domain transplanted art already accounted for in `assets/SOURCES.md`.

Regenerate with `python3 tools/gen-spawn-eggs.py`. Output is deterministic — a rerun
produces byte-identical files — and the script needs the vanilla client jar present.

### If original egg art is wanted instead

The generator is a drop-in seam: replace `recolour()` with a routine that draws its own
silhouette and the rest of the pipeline (palette sampling, file layout, the build-time
check that every animal has an egg) is unchanged.
