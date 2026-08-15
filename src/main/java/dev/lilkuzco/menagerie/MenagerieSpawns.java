package dev.lilkuzco.menagerie;

import dev.lilkuzco.menagerie.data.Species;
import dev.lilkuzco.menagerie.data.SpeciesRegistry;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

/**
 * Registers ONE dynamic biome modification. The modifier lambda runs per-biome when
 * biomes are baked at world load — AFTER datapack reload listeners — so it reads the
 * live species registry: even a species JSON added by an external datapack gets real
 * spawn entries with zero Java. Weights recorded here feed the live-vs-baked
 * acceptance scaling in the spawn predicate (see SpeciesRegistry docs).
 */
public final class MenagerieSpawns {
	public static void init() {
		BiomeModifications.create(Menagerie.id("species_spawns"))
				.add(ModificationPhase.ADDITIONS, ctx -> true, (selection, modification) -> {
					for (var speciesList : SpeciesRegistry.all().values()) {
						for (Species species : speciesList) {
							if (species.weight() <= 0 || !species.matchesBiome(selection.getBiomeHolder())) {
								continue;
							}
							Identifier entityKey = Identifier.parse(species.entityId());
							EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getValue(entityKey);
							// ENTITY_TYPE is a defaulted registry (falls back to pig) — verify the key round-trips
							if (type == null || !BuiltInRegistries.ENTITY_TYPE.getKey(type).equals(entityKey)) {
								Menagerie.LOGGER.warn("Species {} references unknown entity {}",
										species.name(), species.entityId());
								continue;
							}
							modification.getMobSpawnSettings().addSpawn(MobCategory.CREATURE,
									new MobSpawnSettings.SpawnerData(type, species.groupMin(), species.groupMax()),
									species.weight());
							SpeciesRegistry.recordBakedWeight(species);
						}
					}
				});
	}

	private MenagerieSpawns() {
	}
}
