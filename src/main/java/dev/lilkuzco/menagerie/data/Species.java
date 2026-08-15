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
		JsonObject special,
		@org.jspecify.annotations.Nullable Territory territory,
		@org.jspecify.annotations.Nullable Diet diet,
		@org.jspecify.annotations.Nullable Venom venom) {

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
		var group = GsonHelper.getAsJsonArray(json, "group_size");
		String tame = GsonHelper.getAsString(json, "tame_item", "");
		// "size_scale" (Phase 2 name) and "scale" (Phase 1 name) are the same knob
		double scale = json.has("size_scale")
				? GsonHelper.getAsDouble(json, "size_scale")
				: GsonHelper.getAsDouble(json, "scale", 1.0);
		return new Species(
				entity,
				species,
				biomes,
				GsonHelper.getAsInt(json, "weight"),
				group.get(0).getAsInt(),
				group.get(1).getAsInt(),
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
				GsonHelper.getAsJsonObject(json, "special", new JsonObject()),
				json.has("territory") ? Territory.fromJson(GsonHelper.getAsJsonObject(json, "territory")) : null,
				json.has("diet") ? Diet.fromJson(GsonHelper.getAsJsonObject(json, "diet")) : null,
				json.has("venom") ? Venom.fromJson(GsonHelper.getAsJsonObject(json, "venom")) : null);
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
		return groupMin + random.nextInt(Math.max(1, groupMax - groupMin + 1));
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
}
