package dev.lilkuzco.menagerie.entity;

import dev.lilkuzco.menagerie.data.RarityConfig;
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
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
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
	/** Rare-variant name rolled once at spawn (see Species.VariantRoll); "" for the common coat. */
	private static final EntityDataAccessor<String> VARIANT =
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
		builder.define(VARIANT, "");
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
		double scale = species.scale();
		if (isBaby() && species.breeding() != null) {
			scale *= species.breeding().babyScale();
		}
		getAttribute(Attributes.SCALE).setBaseValue(scale);
		getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(species.knockback());
	}

	public String getVariantName() {
		return this.entityData.get(VARIANT);
	}

	public void setVariantName(String variant) {
		this.entityData.set(VARIANT, variant);
	}

	public boolean hasRareVariant() {
		return !getVariantName().isEmpty();
	}

	/** Roll the rare-coat table once; persisted afterwards so an animal never re-rolls. */
	protected void rollVariantOnce(Species species) {
		if (!getVariantName().isEmpty()) {
			return;
		}
		Species.VariantRoll roll = species.rollVariant(getRandom());
		if (roll != null) {
			setVariantName(roll.name());
		}
	}

	public Identifier texture() {
		Species species = species();
		if (species == null) {
			return Identifier.fromNamespaceAndPath("minecraft", "textures/entity/pig/temperate_pig.png");
		}
		Species.VariantRoll variant = species.variant(getVariantName());
		return variant != null ? variant.texture() : species.texture();
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
			rollVariantOnce(species);
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
		// Epic tiers thin further: only a fraction of otherwise-valid attempts survive.
		if (species.attemptChance() < 1.0F && random.nextFloat() > species.attemptChance()) {
			return false;
		}
		// The accumulation killer. CREATURE-category animals never despawn, so without a
		// ceiling even a modest weight piles up forever in a long-lived world. Counting
		// same-type entities nearby is what keeps a jungle reading alive instead of zoo.
		return !overNearbyCap(type, level, pos, species);
	}

	/**
	 * @return true when there are already enough of this animal near {@code pos}.
	 *
	 * Only applies to NATURAL spawns: chunk generation passes a WorldGenRegion rather than
	 * a ServerLevel, and neighbouring chunks may not exist yet, so counting there would be
	 * meaningless. That is the right split anyway — worldgen seeds a population once,
	 * while it is repeated natural spawning into a never-despawning category that
	 * compounds, and that is exactly the path this gates.
	 */
	private static boolean overNearbyCap(EntityType<? extends Mob> type, LevelAccessor level,
			BlockPos pos, Species species) {
		int cap = species.nearbyCap();
		if (cap <= 0 || !(level instanceof ServerLevel serverLevel)) {
			return false;
		}
		int radius = RarityConfig.NEARBY_RADIUS;
		AABB box = AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(pos))
				.inflate(radius, Math.min(radius, 64), radius);
		int seen = 0;
		for (Entity nearby : serverLevel.getEntities(type, box, e -> true)) {
			if (++seen >= cap) {
				return true;
			}
		}
		return false;
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
		if (species == null) {
			return false;
		}
		if (species.breeding() != null) {
			for (String item : species.breeding().items()) {
				if (matchesItemId(stack, item)) {
					return true;
				}
			}
			// a breeding block is authoritative: no accidental fallback food
			return false;
		}
		return matchesItemId(stack, species.breedItem());
	}

	public boolean breedable() {
		Species species = species();
		return species != null && (species.breeding() != null || !species.breedItem().isEmpty());
	}

	public boolean isTameItem(ItemStack stack) {
		Species species = species();
		return species != null && !species.tameItem().isEmpty() && matchesItemId(stack, species.tameItem());
	}

	public boolean tamable() {
		Species species = species();
		return species != null && !species.tameItem().isEmpty();
	}

	/** Chance a single feeding tames (gorilla spec: 1 in 3; doubled while content). */
	protected float tameChance() {
		return 1.0F / 3.0F;
	}

	// ---------- forage / contentment (generic; driven by the species "forage" block) ----------
	private long contentUntil;
	private boolean forageGoalAttached;
	/** Tracks age transitions so breeding's baby_scale is actually applied and undone. */
	private boolean lastBabyState;

	/** Animals that already register a BreedGoal in registerGoals must not get a second. */
	protected boolean hasBreedGoal() {
		return false;
	}

	/** A meal just happened (block forage or player feeding). Subclasses may spread it. */
	public void onForaged() {
		Species species = species();
		int minutes = species != null && species.forage() != null ? species.forage().contentMinutes() : 5;
		this.contentUntil = level().getGameTime() + minutes * 1200L;
	}

	public boolean isContent() {
		return level().getGameTime() < contentUntil;
	}

	@Override
	protected void customServerAiStep(net.minecraft.server.level.ServerLevel level) {
		super.customServerAiStep(level);
		// a bred calf is created before vanilla flips it to a baby, and it grows up later:
		// re-apply on every transition or baby_scale would be a knob that does nothing
		if (isBaby() != lastBabyState) {
			lastBabyState = isBaby();
			Species current = species();
			if (current != null) {
				float healthFraction = getMaxHealth() > 0 ? getHealth() / getMaxHealth() : 1.0F;
				applySpeciesAttributes(current);
				setHealth(getMaxHealth() * healthFraction);
			}
		}
		// species with a forage block get the goal attached lazily (species is data,
		// unknown at registerGoals time) — zero Java for future foraging animals
		if (!forageGoalAttached) {
			forageGoalAttached = true;
			Species species = species();
			if (species != null && species.forage() != null) {
				this.goalSelector.addGoal(6, new dev.lilkuzco.menagerie.entity.ai.ForageGoal(this));
			}
			// data-driven breeding: any species declaring a "breeding" block gets the
			// vanilla breed goal, so adding a breedable animal stays a JSON-only change
			// priority 2 deliberately: every animal here strolls at 3 or 4, and a goal that
			// loses the MOVE flag to wandering never gets to breed (vanilla puts BreedGoal
			// above wander for the same reason). Melee stays at 1, so fights still win.
			if (species != null && species.breeding() != null && !hasBreedGoal()) {
				this.goalSelector.addGoal(2, new net.minecraft.world.entity.ai.goal.BreedGoal(this, 1.0));
			}
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!isTame() && tamable() && isTameItem(stack)) {
			if (!level().isClientSide()) {
				usePlayerItem(player, hand, stack);
				// roll with the CURRENT mood, then count this feeding as a meal —
				// so the first melon is 1/3 and follow-ups within the window are 2/3
				boolean tamed = getRandom().nextFloat() < tameChance();
				onForaged();
				if (tamed) {
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
				// babies roll the rare coat independently — an albino born to a normal
				// troop is the jackpot sighting, and it is inherited chance, not colour
				speciesChild.rollVariantOnce(species);
			}
			// a calf born to two tamed parents belongs to their owner from the start
			if (isTame() && partner instanceof SpeciesMob other && other.isTame() && getOwner() != null) {
				speciesChild.setTame(true, true);
				speciesChild.setOwner(getOwner());
			}
		}
		return child;
	}

	// ---------- persistence ----------
	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putString("menagerie_species", getSpeciesName());
		output.putString("menagerie_variant", getVariantName());
		output.putLong("menagerie_content_until", contentUntil);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		contentUntil = input.getLongOr("menagerie_content_until", 0L);
		this.entityData.set(VARIANT, input.getStringOr("menagerie_variant", ""));
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
