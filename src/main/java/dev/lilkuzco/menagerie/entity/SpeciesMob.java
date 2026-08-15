package dev.lilkuzco.menagerie.entity;

import dev.lilkuzco.menagerie.data.Species;
import dev.lilkuzco.menagerie.data.SpeciesRegistry;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

/**
 * Base for every Menagerie animal: ONE Java class per animal, all variation is a
 * {@link Species} chosen from the data registry at spawn time (Untamed Wilds pattern).
 * Stats, taming, breeding, textures and spawn gating all route through the species.
 * Extends TamableAnimal so tamable species (gorilla) get the vanilla wolf-style
 * owner/sit/teleport machinery; species with an empty tame_item simply never tame.
 */
public abstract class SpeciesMob extends TamableAnimal {
	private static final EntityDataAccessor<String> SPECIES =
			SynchedEntityData.defineId(SpeciesMob.class, EntityDataSerializers.STRING);

	protected SpeciesMob(EntityType<? extends SpeciesMob> type, Level level) {
		super(type, level);
	}

	/** Baseline attributes; every real value is overwritten from species JSON at spawn/load. */
	public static AttributeSupplier.Builder createSpeciesAttributes() {
		return Animal.createAnimalAttributes()
				.add(Attributes.MAX_HEALTH, 20.0)
				.add(Attributes.MOVEMENT_SPEED, 0.28)
				.add(Attributes.ATTACK_DAMAGE, 2.0)
				.add(Attributes.FOLLOW_RANGE, 24.0);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SPECIES, "");
	}

	// ---------- species plumbing ----------
	public String entityId() {
		return BuiltInRegistries.ENTITY_TYPE.getKey(getType()).toString();
	}

	public String getSpeciesName() {
		return this.entityData.get(SPECIES);
	}

	public @Nullable Species species() {
		Species species = SpeciesRegistry.species(entityId(), getSpeciesName());
		return species != null ? species : SpeciesRegistry.fallback(entityId());
	}

	public void setSpecies(Species species, boolean healToFull) {
		this.entityData.set(SPECIES, species.name());
		applySpeciesAttributes(species);
		if (healToFull) {
			setHealth(getMaxHealth());
		}
	}

	protected void applySpeciesAttributes(Species species) {
		getAttribute(Attributes.MAX_HEALTH).setBaseValue(species.health());
		getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(species.speed());
		getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(species.attack());
		getAttribute(Attributes.SCALE).setBaseValue(species.scale());
		getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(species.knockback());
	}

	public Identifier texture() {
		Species species = species();
		return species != null ? species.texture()
				: Identifier.fromNamespaceAndPath("minecraft", "textures/entity/pig/temperate_pig.png");
	}

	// ---------- spawn ----------
	/** Group data shared by a whole spawn group: same species for everyone, plus a troop id. */
	public static class SpeciesGroupData extends AgeableMob.AgeableMobGroupData {
		public final Species species;
		public final UUID troopId = UUID.randomUUID();
		public boolean leaderAssigned;

		public SpeciesGroupData(Species species) {
			super(true);
			this.species = species;
		}
	}

	@Override
	public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
			EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
		Species species;
		if (groupData instanceof SpeciesGroupData speciesData && speciesData.species.entityId().equals(entityId())) {
			species = speciesData.species;
		} else {
			species = SpeciesRegistry.pickForBiome(entityId(), level.getBiome(blockPosition()), getRandom());
			if (species == null) {
				species = SpeciesRegistry.fallback(entityId());
			}
			if (species != null) {
				groupData = new SpeciesGroupData(species);
			}
		}
		if (species != null) {
			setSpecies(species, true);
		}
		return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
	}

	/**
	 * Spawn predicate for natural + chunk-generation spawns. Enforces: a species must
	 * exist for this biome; worldgen_only species never respawn NATURALly; live-vs-baked
	 * weight scaling (post-/reload tuning); and a permissive ground/light check that
	 * also accepts snow and stone so mountain species can spawn.
	 */
	public static boolean checkSpeciesSpawnRules(EntityType<? extends Mob> type, LevelAccessor level,
			EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
		if (spawnReason != EntitySpawnReason.NATURAL && spawnReason != EntitySpawnReason.CHUNK_GENERATION) {
			return true;
		}
		if (!checkSpeciesGate(type, level, spawnReason, pos, random)) {
			return false;
		}
		BlockState ground = level.getBlockState(pos.below());
		boolean groundOk = ground.is(BlockTags.ANIMALS_SPAWNABLE_ON) || ground.is(BlockTags.SNOW)
				|| ground.is(BlockTags.BASE_STONE_OVERWORLD) || ground.is(BlockTags.LEAVES)
				|| ground.is(BlockTags.SAND) || ground.is(BlockTags.BADLANDS_TERRACOTTA);
		return groundOk && level.getRawBrightness(pos, 0) > 6;
	}

	/** Just the data-driven gate: a species exists here, worldgen_only, live-weight scaling. */
	public static boolean checkSpeciesGate(EntityType<? extends Mob> type, LevelAccessor level,
			EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
		String entityId = BuiltInRegistries.ENTITY_TYPE.getKey(type).toString();
		Holder<Biome> biome = level.getBiome(pos);
		Species species = SpeciesRegistry.pickForBiome(entityId, biome, random);
		if (species == null) {
			return false;
		}
		if (spawnReason == EntitySpawnReason.NATURAL) {
			if (species.worldgenOnly()) {
				return false;
			}
			if (random.nextDouble() > SpeciesRegistry.spawnAcceptance(species)) {
				return false;
			}
		}
		return true;
	}

	// ---------- food / taming / breeding ----------
	protected boolean matchesItemId(ItemStack stack, String itemId) {
		if (itemId.isEmpty()) {
			return false;
		}
		return BuiltInRegistries.ITEM.getOptional(Identifier.parse(itemId))
				.map(stack::is).orElse(false);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		Species species = species();
		return species != null && matchesItemId(stack, species.breedItem());
	}

	public boolean isTameItem(ItemStack stack) {
		Species species = species();
		return species != null && !species.tameItem().isEmpty() && matchesItemId(stack, species.tameItem());
	}

	public boolean tamable() {
		Species species = species();
		return species != null && !species.tameItem().isEmpty();
	}

	/** Chance a single feeding tames (gorilla spec: 1 in 3). */
	protected int tameChanceIn() {
		return 3;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!isTame() && tamable() && isTameItem(stack)) {
			if (!level().isClientSide()) {
				usePlayerItem(player, hand, stack);
				if (getRandom().nextInt(tameChanceIn()) == 0) {
					tame(player);
					setOrderedToSit(false);
					level().broadcastEntityEvent(this, (byte) 7);
				} else {
					level().broadcastEntityEvent(this, (byte) 6);
				}
			}
			return InteractionResult.SUCCESS;
		}
		if (isTame() && isOwnedBy(player) && !isFood(stack) && !player.isSecondaryUseActive()) {
			if (!level().isClientSide()) {
				setOrderedToSit(!isOrderedToSit());
				setInSittingPose(isOrderedToSit());
			}
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(player, hand);
	}

	/** Tamed animals follow further than wolves before teleporting (spec: >32 blocks). */
	@Override
	public boolean shouldTryTeleportToOwner() {
		return getOwner() != null && distanceToSqr(getOwner()) >= 32.0 * 32.0;
	}

	@Override
	public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
		AgeableMob child = (AgeableMob) getType().create(level, EntitySpawnReason.BREEDING);
		if (child instanceof SpeciesMob speciesChild) {
			Species species = species();
			if (species != null) {
				speciesChild.setSpecies(species, true);
			}
		}
		return child;
	}

	// ---------- persistence ----------
	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putString("menagerie_species", getSpeciesName());
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		String name = input.getStringOr("menagerie_species", "");
		this.entityData.set(SPECIES, name);
		// re-apply attributes from (possibly retuned) JSON, keep current health fraction
		Species species = species();
		if (species != null) {
			float healthFraction = getMaxHealth() > 0 ? getHealth() / getMaxHealth() : 1.0F;
			applySpeciesAttributes(species);
			setHealth(getMaxHealth() * healthFraction);
		}
	}
}
