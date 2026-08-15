package dev.lilkuzco.menagerie.entity;

import dev.lilkuzco.menagerie.Menagerie;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public final class MenagerieEntities {
	public static final EntityType<GorillaEntity> GORILLA = register("gorilla",
			EntityType.Builder.of(GorillaEntity::new, MobCategory.CREATURE)
					.sized(1.1F, 1.4F).eyeHeight(1.2F).clientTrackingRange(10));

	public static final EntityType<CrocodileEntity> CROCODILE = register("crocodile",
			EntityType.Builder.of(CrocodileEntity::new, MobCategory.CREATURE)
					.sized(1.5F, 0.6F).eyeHeight(0.45F).clientTrackingRange(10));

	public static final EntityType<TortoiseEntity> TORTOISE = register("tortoise",
			EntityType.Builder.of(TortoiseEntity::new, MobCategory.CREATURE)
					.sized(1.0F, 0.6F).eyeHeight(0.45F).clientTrackingRange(10));

	public static final EntityType<LeopardEntity> LEOPARD = register("leopard",
			EntityType.Builder.of(LeopardEntity::new, MobCategory.CREATURE)
					.sized(0.9F, 0.9F).eyeHeight(0.75F).clientTrackingRange(10));

	public static final EntityType<HippoEntity> HIPPO = register("hippo",
			EntityType.Builder.of(HippoEntity::new, MobCategory.CREATURE)
					.sized(1.9F, 1.6F).eyeHeight(1.3F).clientTrackingRange(10));

	public static final EntityType<GrizzlyEntity> GRIZZLY = register("grizzly",
			EntityType.Builder.of(GrizzlyEntity::new, MobCategory.CREATURE)
					.sized(1.4F, 1.5F).eyeHeight(1.2F).clientTrackingRange(10));

	public static final EntityType<VultureEntity> VULTURE = register("vulture",
			EntityType.Builder.of(VultureEntity::new, MobCategory.CREATURE)
					.sized(0.9F, 0.7F).eyeHeight(0.5F).clientTrackingRange(12));

	public static final EntityType<SnakeEntity> SNAKE = register("snake",
			EntityType.Builder.of(SnakeEntity::new, MobCategory.CREATURE)
					.sized(0.8F, 0.35F).eyeHeight(0.25F).clientTrackingRange(8));

	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Menagerie.id(name));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
	}

	public static void init() {
		FabricDefaultAttributeRegistry.register(GORILLA, SpeciesMob.createSpeciesAttributes());
		FabricDefaultAttributeRegistry.register(CROCODILE, SpeciesMob.createSpeciesAttributes());
		FabricDefaultAttributeRegistry.register(TORTOISE, SpeciesMob.createSpeciesAttributes());
		FabricDefaultAttributeRegistry.register(LEOPARD, SpeciesMob.createSpeciesAttributes());
		FabricDefaultAttributeRegistry.register(HIPPO, SpeciesMob.createSpeciesAttributes());
		FabricDefaultAttributeRegistry.register(GRIZZLY, SpeciesMob.createSpeciesAttributes());
		FabricDefaultAttributeRegistry.register(VULTURE, SpeciesMob.createSpeciesAttributes());
		FabricDefaultAttributeRegistry.register(SNAKE, SpeciesMob.createSpeciesAttributes());

		SpawnPlacements.register(GORILLA, SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpeciesMob::checkSpeciesSpawnRules);
		SpawnPlacements.register(TORTOISE, SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpeciesMob::checkSpeciesSpawnRules);
		SpawnPlacements.register(LEOPARD, SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpeciesMob::checkSpeciesSpawnRules);
		SpawnPlacements.register(GRIZZLY, SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpeciesMob::checkSpeciesSpawnRules);
		SpawnPlacements.register(SNAKE, SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpeciesMob::checkSpeciesSpawnRules);
		// crocodiles and hippos may spawn at the water line, so skip the on-ground restriction
		SpawnPlacements.register(CROCODILE, SpawnPlacementTypes.NO_RESTRICTIONS,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrocodileSpawn::check);
		SpawnPlacements.register(HIPPO, SpawnPlacementTypes.NO_RESTRICTIONS,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrocodileSpawn::check);
		// vultures only spawn under open sky (never indoors or underground)
		SpawnPlacements.register(VULTURE, SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (type, level, reason, pos, random) ->
						level.canSeeSky(pos) && SpeciesMob.checkSpeciesSpawnRules(type, level, reason, pos, random));
	}

	/** Waterline placement (crocodile, hippo): normal species gate, but water counts as valid ground. */
	private static final class CrocodileSpawn {
		static <T extends net.minecraft.world.entity.Mob> boolean check(EntityType<T> type,
				net.minecraft.world.level.LevelAccessor level,
				net.minecraft.world.entity.EntitySpawnReason reason,
				net.minecraft.core.BlockPos pos, net.minecraft.util.RandomSource random) {
			boolean inWater = level.getFluidState(pos).is(net.minecraft.tags.FluidTags.WATER)
					|| level.getFluidState(pos.below()).is(net.minecraft.tags.FluidTags.WATER);
			if (inWater) {
				if (reason != net.minecraft.world.entity.EntitySpawnReason.NATURAL
						&& reason != net.minecraft.world.entity.EntitySpawnReason.CHUNK_GENERATION) {
					return true;
				}
				return SpeciesMob.checkSpeciesGate(type, level, reason, pos, random);
			}
			return SpeciesMob.checkSpeciesSpawnRules(type, level, reason, pos, random);
		}
	}

	private MenagerieEntities() {
	}
}
