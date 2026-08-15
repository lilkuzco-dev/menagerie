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
		String tameItem,
		String breedItem,
		boolean neutral,
		Identifier texture,
		JsonObject special) {

	public static Species fromJson(String fileName, JsonObject json) {
		String entity = GsonHelper.getAsString(json, "entity");
		String species = GsonHelper.getAsString(json, "species", fileName);
		List<String> biomes = GsonHelper.getAsJsonArray(json, "biomes").asList().stream()
				.map(e -> e.getAsString()).toList();
		var group = GsonHelper.getAsJsonArray(json, "group_size");
		String tame = GsonHelper.getAsString(json, "tame_item", "");
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
				GsonHelper.getAsDouble(json, "scale", 1.0),
				tame,
				GsonHelper.getAsString(json, "breed_item", tame),
				GsonHelper.getAsBoolean(json, "neutral", true),
				Identifier.parse(GsonHelper.getAsString(json, "texture")),
				GsonHelper.getAsJsonObject(json, "special", new JsonObject()));
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
