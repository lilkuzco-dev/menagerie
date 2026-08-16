package dev.lilkuzco.menagerie.data;

import com.google.gson.JsonObject;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;

/**
 * One species of one animal, loaded from data/&lt;ns&gt;/species/*.json. Everything
 * tunable about a variant lives here; the Java entity classes only read these values.
 * Biome entries starting with '#' are biome tags, anything else is a raw biome id.
 */
public record Species(
		String entityId,
		String name,
		List<String> biomes,
		int weight,
		int groupMin,
		int groupMax,
		boolean worldgenOnly,
		double health,
		double attack,
		double speed,
		double scale,
		double knockback,
		String tameItem,
		String breedItem,
		boolean neutral,
		Identifier texture,
		List<Identifier> textures,
		JsonObject special,
		@org.jspecify.annotations.Nullable Territory territory,
		@org.jspecify.annotations.Nullable Diet diet,
		@org.jspecify.annotations.Nullable Venom venom,
		int cageTier,
		String guideBlurb,
		@org.jspecify.annotations.Nullable Forage forage,
		String rarity,
		int nearbyCapOverride,
		List<VariantRoll> variantRolls,
		@org.jspecify.annotations.Nullable Breeding breeding) {

	/**
	 * Optional "variant_rolls": rare skins rolled ONCE at spawn and persisted, so a
	 * given animal never re-rolls. Pure JSON — any animal can gain a rare coat with no
	 * Java at all.
	 */
	public record VariantRoll(String name, float chance, Identifier texture) {
		static VariantRoll fromJson(String name, JsonObject json) {
			return new VariantRoll(name,
					GsonHelper.getAsFloat(json, "chance", 0.05F),
					Identifier.parse(GsonHelper.getAsString(json, "texture")));
		}
	}

	/** Optional "breeding" block: feed two adults -> baby, vanilla-style. */
	public record Breeding(List<String> items, int cooldownTicks, double babyScale) {
		static Breeding fromJson(JsonObject json) {
			List<String> items = GsonHelper.getAsJsonArray(json, "items", new com.google.gson.JsonArray())
					.asList().stream().map(e -> e.getAsString()).toList();
			return new Breeding(items,
					GsonHelper.getAsInt(json, "cooldown_ticks", 6000),
					GsonHelper.getAsDouble(json, "baby_scale", 0.5));
		}
	}

	// ---------- rarity resolution ----------
	// Explicit weight/group_size in the JSON always win; otherwise the rarity tier
	// supplies them, so one word ("rare") sets density, group size and the nearby cap.
	public RarityConfig.Tier tier() {
		return RarityConfig.get().tier(rarity);
	}

	public int effectiveWeight() {
		return weight >= 0 ? weight : tier().weight();
	}

	public int effectiveGroupMin() {
		return groupMin >= 0 ? groupMin : tier().groupMin();
	}

	public int effectiveGroupMax() {
		return groupMax >= 0 ? groupMax : tier().groupMax();
	}

	/** Max same-type animals within {@link RarityConfig#NEARBY_RADIUS} before spawns are denied. */
	public int nearbyCap() {
		return nearbyCapOverride > 0 ? nearbyCapOverride : tier().nearbyCap();
	}

	/** Fraction of otherwise-eligible spawn attempts that survive (epic tiers thin further). */
	public float attemptChance() {
		return tier().attemptChance();
	}

	public @org.jspecify.annotations.Nullable VariantRoll rollVariant(RandomSource random) {
		for (VariantRoll roll : variantRolls) {
			if (random.nextFloat() < roll.chance()) {
				return roll;
			}
		}
		return null;
	}

	public @org.jspecify.annotations.Nullable VariantRoll variant(String name) {
		for (VariantRoll roll : variantRolls) {
			if (roll.name().equals(name)) {
				return roll;
			}
		}
		return null;
	}

	/** Optional "forage" block: blocks the animal seeks out and eats (mobGriefing-gated). */
	public record Forage(List<String> blocks, int range, int cooldownTicks, int contentMinutes) {
		static Forage fromJson(JsonObject json) {
			List<String> blocks = GsonHelper.getAsJsonArray(json, "blocks", new com.google.gson.JsonArray())
					.asList().stream().map(e -> e.getAsString()).toList();
			return new Forage(blocks,
					GsonHelper.getAsInt(json, "range", 12),
					GsonHelper.getAsInt(json, "cooldown_ticks", 1200),
					GsonHelper.getAsInt(json, "content_minutes", 5));
		}

		public boolean matches(Identifier blockId) {
			return blocks.contains(blockId.toString());
		}
	}

	/** Optional "territory" block: water/spawn-anchored aggro zone (hippo). */
	public record Territory(int radius, String anchor, boolean aggroInside) {
		static Territory fromJson(JsonObject json) {
			return new Territory(
					GsonHelper.getAsInt(json, "radius", 16),
					GsonHelper.getAsString(json, "anchor", "spawn"),
					GsonHelper.getAsBoolean(json, "aggro_inside", true));
		}
	}

	/** Optional "diet" block: hunted entity ids + scavenging flag (bear, vulture). */
	public record Diet(List<String> hunts, boolean scavenges) {
		static Diet fromJson(JsonObject json) {
			List<String> hunts = GsonHelper.getAsJsonArray(json, "hunts", new com.google.gson.JsonArray())
					.asList().stream().map(e -> e.getAsString()).toList();
			return new Diet(hunts, GsonHelper.getAsBoolean(json, "scavenges", false));
		}

		public boolean hunts(Identifier entityTypeId) {
			return hunts.contains(entityTypeId.toString());
		}
	}

	/** Optional "venom" block: effect applied on strike (snake). */
	public record Venom(Identifier effect, int amplifier, int seconds) {
		static Venom fromJson(JsonObject json) {
			return new Venom(
					Identifier.parse(GsonHelper.getAsString(json, "effect", "minecraft:poison")),
					GsonHelper.getAsInt(json, "amplifier", 0),
					GsonHelper.getAsInt(json, "seconds", 5));
		}
	}

	public static Species fromJson(String fileName, JsonObject json) {
		String entity = GsonHelper.getAsString(json, "entity");
		String species = GsonHelper.getAsString(json, "species", fileName);
		List<String> biomes = GsonHelper.getAsJsonArray(json, "biomes").asList().stream()
				.map(e -> e.getAsString()).toList();
		var group = GsonHelper.getAsJsonArray(json, "group_size", null);
		String tame = GsonHelper.getAsString(json, "tame_item", "");
		// "size_scale" (Phase 2 name) and "scale" (Phase 1 name) are the same knob
		double scale = json.has("size_scale")
				? GsonHelper.getAsDouble(json, "size_scale")
				: GsonHelper.getAsDouble(json, "scale", 1.0);
		return new Species(
				entity,
				species,
				biomes,
				GsonHelper.getAsInt(json, "weight", -1),
				group == null ? -1 : group.get(0).getAsInt(),
				group == null ? -1 : group.get(1).getAsInt(),
				GsonHelper.getAsBoolean(json, "worldgen_only", false),
				GsonHelper.getAsDouble(json, "health"),
				GsonHelper.getAsDouble(json, "attack", 0.0),
				GsonHelper.getAsDouble(json, "speed"),
				scale,
				GsonHelper.getAsDouble(json, "knockback", 0.0),
				tame,
				GsonHelper.getAsString(json, "breed_item", tame),
				GsonHelper.getAsBoolean(json, "neutral", true),
				Identifier.parse(GsonHelper.getAsString(json, "texture")),
				// optional "textures": per-individual fur variants; defaults to just "texture"
				json.has("textures")
						? GsonHelper.getAsJsonArray(json, "textures").asList().stream()
								.map(e -> Identifier.parse(e.getAsString())).toList()
						: List.of(Identifier.parse(GsonHelper.getAsString(json, "texture"))),
				GsonHelper.getAsJsonObject(json, "special", new JsonObject()),
				json.has("territory") ? Territory.fromJson(GsonHelper.getAsJsonObject(json, "territory")) : null,
				json.has("diet") ? Diet.fromJson(GsonHelper.getAsJsonObject(json, "diet")) : null,
				json.has("venom") ? Venom.fromJson(GsonHelper.getAsJsonObject(json, "venom")) : null,
				GsonHelper.getAsInt(json, "cage_tier", 1),
				GsonHelper.getAsString(json, "guide_blurb", ""),
				json.has("forage") ? Forage.fromJson(GsonHelper.getAsJsonObject(json, "forage")) : null,
				GsonHelper.getAsString(json, "rarity", ""),
				GsonHelper.getAsInt(json, "nearby_cap", 0),
				parseVariantRolls(json),
				json.has("breeding") ? Breeding.fromJson(GsonHelper.getAsJsonObject(json, "breeding")) : null);
	}

	private static List<VariantRoll> parseVariantRolls(JsonObject json) {
		if (!json.has("variant_rolls")) {
			return List.of();
		}
		JsonObject rolls = GsonHelper.getAsJsonObject(json, "variant_rolls");
		List<VariantRoll> out = new java.util.ArrayList<>();
		for (String key : rolls.keySet()) {
			out.add(VariantRoll.fromJson(key, GsonHelper.getAsJsonObject(rolls, key)));
		}
		return List.copyOf(out);
	}

	public boolean matchesBiome(Holder<Biome> biome) {
		for (String entry : biomes) {
			if (entry.startsWith("#")) {
				if (biome.is(TagKey.create(Registries.BIOME, Identifier.parse(entry.substring(1))))) {
					return true;
				}
			} else if (biome.is(ResourceKey.create(Registries.BIOME, Identifier.parse(entry)))) {
				return true;
			}
		}
		return false;
	}

	public int groupSize(RandomSource random) {
		int min = effectiveGroupMin();
		int max = effectiveGroupMax();
		return min + random.nextInt(Math.max(1, max - min + 1));
	}

	// ---------- special-block accessors (per-animal knobs, all with defaults) ----------
	public int specialInt(String key, int fallback) {
		return GsonHelper.getAsInt(special, key, fallback);
	}

	public double specialDouble(String key, double fallback) {
		return GsonHelper.getAsDouble(special, key, fallback);
	}

	public boolean specialBool(String key, boolean fallback) {
		return GsonHelper.getAsBoolean(special, key, fallback);
	}

	/**
	 * The capitalised species word to hang in front of an animal's name, or "" when it
	 * would only stutter: the leopard's one species is "leopard", so the naive join reads
	 * "Leopard Leopard" in the guide, the discovery toast and on a cage label alike.
	 * Every place that builds that label goes through here so they cannot drift apart.
	 */
	public static String namePrefix(String speciesName, String entityPath) {
		if (speciesName.isEmpty() || speciesName.equalsIgnoreCase(entityPath)) {
			return "";
		}
		return Character.toUpperCase(speciesName.charAt(0)) + speciesName.substring(1) + " ";
	}
}
