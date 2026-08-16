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
					.sized(1.35F, 1.7F).eyeHeight(1.45F).clientTrackingRange(10));

	public static final EntityType<CrocodileEntity> CROCODILE = register("crocodile",
			EntityType.Builder.of(CrocodileEntity::new, MobCategory.CREATURE)
					.sized(2.0F, 0.4F).eyeHeight(0.32F).clientTrackingRange(10));

	public static final EntityType<TortoiseEntity> TORTOISE = register("tortoise",
			EntityType.Builder.of(TortoiseEntity::new, MobCategory.CREATURE)
					.sized(0.85F, 0.75F).eyeHeight(0.55F).clientTrackingRange(10));

	public static final EntityType<LeopardEntity> LEOPARD = register("leopard",
			EntityType.Builder.of(LeopardEntity::new, MobCategory.CREATURE)
					.sized(1.25F, 1.0F).eyeHeight(0.85F).clientTrackingRange(10));

	public static final EntityType<HippoEntity> HIPPO = register("hippo",
			EntityType.Builder.of(HippoEntity::new, MobCategory.CREATURE)
					.sized(1.3F, 1.06F).eyeHeight(0.9F).clientTrackingRange(10));

	public static final EntityType<GrizzlyEntity> GRIZZLY = register("grizzly",
			EntityType.Builder.of(GrizzlyEntity::new, MobCategory.CREATURE)
					.sized(1.25F, 1.25F).eyeHeight(1.05F).clientTrackingRange(10));

	public static final EntityType<VultureEntity> VULTURE = register("vulture",
			EntityType.Builder.of(VultureEntity::new, MobCategory.CREATURE)
					.sized(1.05F, 1.0F).eyeHeight(0.75F).clientTrackingRange(12));

	public static final EntityType<LionEntity> LION = register("lion",
			EntityType.Builder.of(LionEntity::new, MobCategory.CREATURE)
					.sized(1.6F, 1.1F).eyeHeight(0.95F).clientTrackingRange(10));

	public static final EntityType<SnakeEntity> SNAKE = register("snake",
			EntityType.Builder.of(SnakeEntity::new, MobCategory.CREATURE)
					.sized(0.9F, 0.3F).eyeHeight(0.22F).clientTrackingRange(8));

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
		FabricDefaultAttributeRegistry.register(LION, SpeciesMob.createSpeciesAttributes());

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
		SpawnPlacements.register(LION, SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpeciesMob::checkSpeciesSpawnRules);
		// crocodiles and hippos wade, so water counts as ground for them — but ONLY at the
		// water line (see WaterlineSpawn). NO_RESTRICTIONS is deliberate and is why the
		// predicate below has to do the depth work itself.
		SpawnPlacements.register(CROCODILE, SpawnPlacementTypes.NO_RESTRICTIONS,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterlineSpawn::check);
		SpawnPlacements.register(HIPPO, SpawnPlacementTypes.NO_RESTRICTIONS,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterlineSpawn::check);
		// vultures only spawn under open sky (never indoors or underground)
		SpawnPlacements.register(VULTURE, SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (type, level, reason, pos, random) ->
						level.canSeeSky(pos) && SpeciesMob.checkSpeciesSpawnRules(type, level, reason, pos, random));
	}

	/**
	 * Waterline placement (crocodile, hippo): water counts as valid ground, but only
	 * where an animal could actually wade.
	 *
	 * <p>This predicate used to accept ANY position that had water in it, which combined
	 * with {@link SpawnPlacementTypes#NO_RESTRICTIONS} — a placement type that performs no
	 * ground, depth or light test of its own — to allow a spawn at any y in the column.
	 * That is how a hippo ended up standing on the sea floor: the vanilla spawner offers
	 * random positions down the whole column, and every submerged one was accepted.
	 *
	 * <p>A wading animal now has to find the surface within {@link #WADE_DEPTH} blocks
	 * above it and a floor within {@link #WADE_DEPTH} below it. Deep water fails both
	 * halves, so open ocean and the bottom of a deep river are out by construction rather
	 * than by hoping the biome list never overlaps one.
	 */
	private static final class WaterlineSpawn {
		/** Blocks of water an animal of this size can stand in and still breathe. */
		private static final int WADE_DEPTH = 3;

		static <T extends net.minecraft.world.entity.Mob> boolean check(EntityType<T> type,
				net.minecraft.world.level.LevelAccessor level,
				net.minecraft.world.entity.EntitySpawnReason reason,
				net.minecraft.core.BlockPos pos, net.minecraft.util.RandomSource random) {
			// /summon, spawn eggs and cage releases place the animal deliberately
			if (reason != net.minecraft.world.entity.EntitySpawnReason.NATURAL
					&& reason != net.minecraft.world.entity.EntitySpawnReason.CHUNK_GENERATION) {
				return true;
			}
			dev.lilkuzco.menagerie.data.Species species =
					SpeciesMob.gateSpecies(type, level, reason, pos, random);
			if (species == null) {
				return false;
			}
			// water is only "ground" for a species that says so in its own data; the
			// aquatic flag is therefore load-bearing, not just something the linter reads
			return species.aquatic() && level.getFluidState(pos).is(net.minecraft.tags.FluidTags.WATER)
					? atWaterline(level, pos)
					: SpeciesMob.groundSpawnOk(level, pos);
		}

		private static boolean atWaterline(net.minecraft.world.level.LevelAccessor level,
				net.minecraft.core.BlockPos pos) {
			// the surface has to be close enough overhead to keep a head above water
			boolean surfaceNear = false;
			for (int up = 1; up <= WADE_DEPTH; up++) {
				if (!level.getFluidState(pos.above(up)).is(net.minecraft.tags.FluidTags.WATER)) {
					surfaceNear = true;
					break;
				}
			}
			if (!surfaceNear) {
				return false;
			}
			// ...and a riverbed close enough underfoot to stand on
			for (int down = 1; down <= WADE_DEPTH; down++) {
				net.minecraft.core.BlockPos floor = pos.below(down);
				if (level.getBlockState(floor).isFaceSturdy(level, floor,
						net.minecraft.core.Direction.UP)) {
					return true;
				}
			}
			return false;
		}
	}

	private MenagerieEntities() {
	}
}
