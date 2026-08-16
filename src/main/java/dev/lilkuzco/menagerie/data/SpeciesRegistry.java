package dev.lilkuzco.menagerie.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.lilkuzco.menagerie.Menagerie;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleReloadListener;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import org.jspecify.annotations.Nullable;

/**
 * Loads every species definition from data/&lt;ns&gt;/species/*.json via a datapack
 * reload listener (same pattern as Warfront's registry). Adding a species of an
 * existing animal is dropping a JSON file — zero Java.
 *
 * Reload semantics: stats/behavior knobs go live on /reload. Spawn WEIGHTS are baked
 * into biomes once per world load (Fabric biome modifications are not re-applied by
 * /reload), so the natural-spawn predicate additionally scales acceptance by
 * liveWeight/bakedWeight — lowering a weight (or zeroing it) works on /reload;
 * raising it above the value at world load needs a restart.
 */
public final class SpeciesRegistry {
	private static final Gson GSON = new Gson();

	/** entityId -> its species, in stable (alphabetical file) order. */
	private static Map<String, List<Species>> byEntity = Map.of();
	/** "entityId|species" -> weight recorded when biome spawn entries were baked. */
	private static final Map<String, Integer> bakedWeights = new ConcurrentHashMap<>();

	public static void init() {
		ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(Menagerie.id("species"), new Listener());
	}

	public static Map<String, List<Species>> all() {
		return byEntity;
	}

	public static List<Species> speciesFor(String entityId) {
		return byEntity.getOrDefault(entityId, List.of());
	}

	public static @Nullable Species species(String entityId, String name) {
		for (Species species : speciesFor(entityId)) {
			if (species.name().equals(name)) {
				return species;
			}
		}
		return null;
	}

	/** Weighted pick among the entity's species whose biome list matches; null if none match. */
	public static @Nullable Species pickForBiome(String entityId, Holder<Biome> biome, RandomSource random) {
		List<Species> matches = new ArrayList<>();
		int totalWeight = 0;
		for (Species species : speciesFor(entityId)) {
			if (species.matchesBiome(biome)) {
				matches.add(species);
				totalWeight += Math.max(1, species.effectiveWeight());
			}
		}
		if (matches.isEmpty()) {
			return null;
		}
		int roll = random.nextInt(totalWeight);
		for (Species species : matches) {
			roll -= Math.max(1, species.effectiveWeight());
			if (roll < 0) {
				return species;
			}
		}
		return matches.get(matches.size() - 1);
	}

	/** Fallback for spawns outside any listed biome (e.g. /summon in a desert): first species. */
	public static @Nullable Species fallback(String entityId) {
		List<Species> all = speciesFor(entityId);
		return all.isEmpty() ? null : all.get(0);
	}

	public static void recordBakedWeight(Species species) {
		bakedWeights.put(species.entityId() + "|" + species.name(), species.effectiveWeight());
	}

	/** live/baked spawn-weight ratio, clamped to [0,1]; 1 when never baked (dev fallback). */
	public static double spawnAcceptance(Species species) {
		Integer baked = bakedWeights.get(species.entityId() + "|" + species.name());
		if (baked == null || baked <= 0) {
			return 1.0;
		}
		return Math.min(1.0, (double) species.effectiveWeight() / baked);
	}

	private static class Listener extends SimpleReloadListener<Map<String, List<Species>>> {
		private volatile @Nullable RarityConfig pendingRarity;

		@Override
		protected Map<String, List<Species>> prepare(PreparableReloadListener.SharedState state) {
			ResourceManager manager = state.resourceManager();
			// parsed here (worker thread) but NOT published until apply(): swapping global
			// state during prepare would race the game thread and would stick even if the
			// rest of the reload failed
			pendingRarity = manager.getResource(Menagerie.id("menagerie_config/rarity.json"))
					.map(res -> RarityConfig.fromJson(parse(res)))
					.orElseGet(RarityConfig::defaults);
			Map<String, List<Species>> map = new HashMap<>();
			manager.listResources("species", p -> p.getPath().endsWith(".json")).entrySet().stream()
					.sorted(Map.Entry.comparingByKey())
					.forEach(entry -> {
						String file = fileName(entry.getKey());
						try {
							Species species = Species.fromJson(file, parse(entry.getValue()));
							map.computeIfAbsent(species.entityId(), k -> new ArrayList<>()).add(species);
						} catch (Exception e) {
							Menagerie.LOGGER.error("Bad species file {}: {}", entry.getKey(), e.toString());
						}
					});
			return map;
		}

		@Override
		protected void apply(Map<String, List<Species>> data, PreparableReloadListener.SharedState state) {
			if (pendingRarity != null) {
				RarityConfig.set(pendingRarity);
				pendingRarity = null;
			}
			Map<String, List<Species>> frozen = new HashMap<>();
			data.forEach((key, value) -> frozen.put(key, List.copyOf(value)));
			byEntity = Map.copyOf(frozen);
			int total = byEntity.values().stream().mapToInt(List::size).sum();
			Menagerie.LOGGER.info("Loaded {} species across {} animals", total, byEntity.size());
		}
	}

	private static String fileName(Identifier id) {
		String path = id.getPath();
		return path.substring(path.lastIndexOf('/') + 1, path.length() - ".json".length());
	}

	private static JsonObject parse(Resource resource) {
		try (BufferedReader reader = resource.openAsReader()) {
			return GSON.fromJson(reader, JsonObject.class);
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse species file", e);
		}
	}

	private SpeciesRegistry() {
	}
}
