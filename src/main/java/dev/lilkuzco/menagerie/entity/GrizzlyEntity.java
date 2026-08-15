package dev.lilkuzco.menagerie.entity;

import dev.lilkuzco.menagerie.MenagerieSounds;
import dev.lilkuzco.menagerie.data.Species;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

/**
 * Neutral heavyweight driven by the species "diet" block: hunts salmon out of rivers
 * (paw-swipe fishing), raids berry bushes, sleeps at night in shelter. Mother-aggro:
 * hurting a cub or crowding within 6 blocks of one angers every adult within 16.
 * The timid black bear ("timid" special flag) flees players unless cubs are around.
 */
public class GrizzlyEntity extends SpeciesMob {
	private static final EntityDataAccessor<Boolean> SLEEPING =
			SynchedEntityData.defineId(GrizzlyEntity.class, EntityDataSerializers.BOOLEAN);
	private static final byte EVENT_SWIPE = 69;

	private int hungerTicks = 1200; // fishing appetite timer
	public int clientSwipeTicks;

	public GrizzlyEntity(EntityType<? extends GrizzlyEntity> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SLEEPING, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 12.0F, 1.1, 1.4) {
			@Override
			public boolean canUse() {
				// only the timid species flees, and never with cubs to protect
				return isTimid() && !cubsNearby() && !isBearSleeping() && super.canUse();
			}
		});
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25, true));
		this.goalSelector.addGoal(3, new SleepGoal());
		this.goalSelector.addGoal(4, new FishGoal());
		this.goalSelector.addGoal(5, new BerryRaidGoal());
		this.goalSelector.addGoal(6, new BreedGoal(this, 1.0));
		this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.2));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.8) {
			@Override
			public boolean canUse() {
				return !isBearSleeping() && super.canUse();
			}
		});
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
				(target, level) -> !isBaby() && target instanceof Player player && !player.isCreative()
						&& playerCrowdsCub(player)));
	}

	// ---------- mother aggro ----------
	public boolean cubsNearby() {
		return !level().getEntitiesOfClass(GrizzlyEntity.class, getBoundingBox().inflate(16.0, 8.0, 16.0),
				GrizzlyEntity::isBaby).isEmpty();
	}

	private boolean playerCrowdsCub(Player player) {
		for (GrizzlyEntity cub : level().getEntitiesOfClass(GrizzlyEntity.class,
				getBoundingBox().inflate(16.0, 8.0, 16.0), GrizzlyEntity::isBaby)) {
			if (cub.distanceToSqr(player) < 36.0) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void actuallyHurt(ServerLevel level, DamageSource source, float damage) {
		super.actuallyHurt(level, source, damage);
		setSleeping(false);
		// hurting a cub angers every adult in earshot
		if (isBaby() && source.getEntity() instanceof LivingEntity attacker) {
			for (GrizzlyEntity adult : level.getEntitiesOfClass(GrizzlyEntity.class,
					getBoundingBox().inflate(16.0, 8.0, 16.0), bear -> !bear.isBaby())) {
				adult.setTarget(attacker);
				adult.setSleeping(false);
			}
		}
	}

	public boolean isTimid() {
		Species species = species();
		return species != null && species.specialBool("timid", false);
	}

	// ---------- sleep ----------
	public boolean isBearSleeping() {
		return this.entityData.get(SLEEPING);
	}

	public void setSleeping(boolean sleeping) {
		this.entityData.set(SLEEPING, sleeping);
	}

	private boolean nightNow() {
		long dayTime = level().getOverworldClockTime() % 24000L;
		return dayTime >= 13000L && dayTime <= 23000L;
	}

	private class SleepGoal extends Goal {
		SleepGoal() {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
		}

		@Override
		public boolean canUse() {
			return nightNow() && getTarget() == null && !isInWater() && getLastHurtByMob() == null;
		}

		@Override
		public void start() {
			// prefer a sheltered spot (solid roof) if one is close, then lie down
			BlockPos here = blockPosition();
			for (BlockPos pos : BlockPos.betweenClosed(here.offset(-6, -1, -6), here.offset(6, 1, 6))) {
				if (!level().getBlockState(pos.above(2)).isAir()
						&& level().getBlockState(pos).isAir()
						&& level().getBlockState(pos.below()).isSolidRender()) {
					getNavigation().moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 1.0);
					break;
				}
			}
		}

		@Override
		public void tick() {
			if (getNavigation().isDone() && !isBearSleeping()) {
				setSleeping(true);
			}
		}

		@Override
		public boolean canContinueToUse() {
			return canUse();
		}

		@Override
		public void stop() {
			setSleeping(false);
		}
	}

	// ---------- fishing (diet block) ----------
	private boolean dietHunts(EntityType<?> type) {
		Species species = species();
		return species != null && species.diet() != null
				&& species.diet().hunts(BuiltInRegistries.ENTITY_TYPE.getKey(type));
	}

	private class FishGoal extends Goal {
		private @Nullable LivingEntity fish;
		private int swipeTimer;

		FishGoal() {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			if (isBaby() || hungerTicks > 0 || getTarget() != null || isBearSleeping()) {
				return false;
			}
			this.fish = findFish();
			return this.fish != null;
		}

		private @Nullable LivingEntity findFish() {
			List<LivingEntity> fishes = level().getEntitiesOfClass(LivingEntity.class,
					getBoundingBox().inflate(12.0, 6.0, 12.0),
					candidate -> candidate.isAlive() && candidate.isInWater() && dietHunts(candidate.getType()));
			LivingEntity best = null;
			for (LivingEntity candidate : fishes) {
				if (best == null || distanceToSqr(candidate) < distanceToSqr(best)) {
					best = candidate;
				}
			}
			return best;
		}

		@Override
		public boolean canContinueToUse() {
			return fish != null && fish.isAlive() && getTarget() == null && !isBearSleeping();
		}

		@Override
		public void start() {
			swipeTimer = 0;
		}

		@Override
		public void tick() {
			if (fish == null) {
				return;
			}
			getLookControl().setLookAt(fish);
			double dist = distanceToSqr(fish);
			if (dist > 9.0) {
				// wade to the bank / shallows near the fish
				getNavigation().moveTo(fish, 1.1);
				return;
			}
			getNavigation().stop();
			if (++swipeTimer < adjustedTickDelay(30)) {
				return;
			}
			swipeTimer = 0;
			// paw swipe: 40% per swing to connect
			if (level() instanceof ServerLevel serverLevel) {
				serverLevel.broadcastEntityEvent(GrizzlyEntity.this, EVENT_SWIPE);
				playSound(MenagerieSounds.GRIZZLY_SWIPE, 1.2F, 1.0F);
				if (getRandom().nextFloat() < 0.4F) {
					if (distanceToSqr(fish) <= 12.25) {
						fish.hurtServer(serverLevel, damageSources().mobAttack(GrizzlyEntity.this), 20.0F);
					} else {
						serverLevel.addFreshEntity(new ItemEntity(serverLevel,
								getX(), getY() + 0.5, getZ(), new ItemStack(Items.SALMON)));
					}
					eatCatch(serverLevel);
					fish = null;
				}
			}
		}

		private void eatCatch(ServerLevel serverLevel) {
			playSound(MenagerieSounds.GRIZZLY_EAT, 1.0F, 1.0F);
			heal(6.0F);
			hungerTicks = 3600 + getRandom().nextInt(3600); // 3-6 min until hungry again
			// hoover any dropped fish items at the bank
			for (ItemEntity item : serverLevel.getEntitiesOfClass(ItemEntity.class,
					getBoundingBox().inflate(3.0))) {
				if (item.getItem().is(Items.SALMON) || item.getItem().is(Items.COD)) {
					item.discard();
				}
			}
		}
	}

	// ---------- berry raiding ----------
	private class BerryRaidGoal extends Goal {
		private @Nullable BlockPos bush;

		BerryRaidGoal() {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			Species species = species();
			if (species == null || !species.specialBool("berry_raiding", false) || isBaby()
					|| isBearSleeping() || getTarget() != null
					|| getRandom().nextInt(reducedTickDelay(200)) != 0) {
				return false;
			}
			if (!(level() instanceof ServerLevel serverLevel)
					|| !serverLevel.getGameRules().get(GameRules.MOB_GRIEFING)) {
				return false;
			}
			this.bush = findBush();
			return this.bush != null;
		}

		private @Nullable BlockPos findBush() {
			BlockPos here = blockPosition();
			for (BlockPos pos : BlockPos.betweenClosed(here.offset(-8, -2, -8), here.offset(8, 2, 8))) {
				BlockState state = level().getBlockState(pos);
				if (state.is(Blocks.SWEET_BERRY_BUSH) && state.getValue(SweetBerryBushBlock.AGE) >= 2) {
					return pos.immutable();
				}
			}
			return null;
		}

		@Override
		public boolean canContinueToUse() {
			return bush != null && level().getBlockState(bush).is(Blocks.SWEET_BERRY_BUSH);
		}

		@Override
		public void tick() {
			if (bush == null) {
				return;
			}
			getLookControl().setLookAt(bush.getX() + 0.5, bush.getY() + 0.5, bush.getZ() + 0.5);
			if (bush.distToCenterSqr(position()) > 4.0) {
				getNavigation().moveTo(bush.getX() + 0.5, bush.getY(), bush.getZ() + 0.5, 1.0);
				return;
			}
			BlockState state = level().getBlockState(bush);
			if (state.is(Blocks.SWEET_BERRY_BUSH) && state.getValue(SweetBerryBushBlock.AGE) >= 2) {
				level().setBlockAndUpdate(bush, state.setValue(SweetBerryBushBlock.AGE, 1));
				playSound(MenagerieSounds.GRIZZLY_EAT, 1.0F, 1.1F);
				heal(2.0F);
			}
			bush = null;
		}
	}

	// ---------- ticking ----------
	@Override
	protected void customServerAiStep(ServerLevel level) {
		super.customServerAiStep(level);
		if (hungerTicks > 0) {
			hungerTicks--;
		}
		if (isBearSleeping() && (getTarget() != null || !nightNow())) {
			setSleeping(false);
		}
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == EVENT_SWIPE) {
			clientSwipeTicks = 15;
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide() && clientSwipeTicks > 0) {
			clientSwipeTicks--;
		}
	}

	// ---------- sounds ----------
	@Override
	protected SoundEvent getAmbientSound() {
		return MenagerieSounds.GRIZZLY_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return MenagerieSounds.GRIZZLY_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return MenagerieSounds.GRIZZLY_DEATH;
	}

	// ---------- persistence ----------
	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putInt("menagerie_hunger", hungerTicks);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		hungerTicks = input.getIntOr("menagerie_hunger", 1200);
	}
}
