package dev.lilkuzco.menagerie.entity;

import dev.lilkuzco.menagerie.MenagerieSounds;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Circling scavenger. Orbits phantom-style 20-30 blocks up; mob deaths that leave
 * meat pull every vulture within 48 blocks to circle the site, land after ~10s and
 * strip the drops — the visible "something died here" signal is the point.
 * Swoops (1-damage pecks) only at players below 3 hearts under open sky.
 * Ambient-style despawn unless interacted with.
 */
public class VultureEntity extends SpeciesMob {
	private static final EntityDataAccessor<Boolean> FLYING =
			SynchedEntityData.defineId(VultureEntity.class, EntityDataSerializers.BOOLEAN);
	public static final Set<Item> CARRION = Set.of(Items.BEEF, Items.PORKCHOP, Items.MUTTON,
			Items.CHICKEN, Items.RABBIT, Items.ROTTEN_FLESH, Items.SALMON, Items.COD);

	private Vec3 moveTargetPoint = Vec3.ZERO;
	private @Nullable BlockPos anchorPoint;
	private @Nullable BlockPos carrionSite;
	private int circleOverCarrion;

	public VultureEntity(EntityType<? extends VultureEntity> type, Level level) {
		super(type, level);
		this.moveControl = new VultureMoveControl(this);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(FLYING, true);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwoopGoal());
		this.goalSelector.addGoal(2, new CircleGoal());
		this.goalSelector.addGoal(3, new ScavengeGoal());

		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 20, false, false,
				(target, level) -> target instanceof Player player && !player.isCreative()
						&& player.getHealth() <= 6.0F && level.canSeeSky(player.blockPosition())));
	}

	// ---------- flight state ----------
	public boolean isFlyingState() {
		return this.entityData.get(FLYING);
	}

	private void setFlyingState(boolean flying) {
		this.entityData.set(FLYING, flying);
	}

	public void takeOff() {
		setFlyingState(true);
		anchorPoint = blockPosition().above(18 + getRandom().nextInt(8));
	}

	private void land() {
		setFlyingState(false);
		setDeltaMovement(Vec3.ZERO);
	}

	public void notifyCarrion(BlockPos site) {
		if (carrionSite == null) {
			carrionSite = site;
			circleOverCarrion = 0;
		}
	}

	private boolean carrionHasMeat(ServerLevel level) {
		return carrionSite != null && !meatItemsNear(level, carrionSite, 6.0).isEmpty();
	}

	private List<ItemEntity> meatItemsNear(ServerLevel level, BlockPos pos, double range) {
		return level.getEntitiesOfClass(ItemEntity.class,
				net.minecraft.world.phys.AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(pos)).inflate(range),
				item -> CARRION.contains(item.getItem().getItem()));
	}

	@Override
	public void travel(Vec3 input) {
		if (isFlyingState()) {
			travelFlying(input, 0.2F);
		} else {
			super.travel(input);
		}
	}

	@Override
	public void tick() {
		super.tick();
		setNoGravity(isFlyingState());
	}

	@Override
	public boolean causeFallDamage(double fallDistance, float damageModifier,
			net.minecraft.world.damagesource.DamageSource damageSource) {
		return false; // birds don't pancake
	}

	/**
	 * Ambient-style despawn, but only when genuinely left behind (96+ blocks): their
	 * anchor drifts past the vanilla 32-block random-despawn line all the time, and an
	 * unrestricted removeWhenFarAway had them silently vanishing mid-circle.
	 */
	@Override
	public boolean removeWhenFarAway(double distSqr) {
		return !hasCustomName() && distSqr > 96.0 * 96.0;
	}

	@Override
	protected void actuallyHurt(ServerLevel level, DamageSource source, float damage) {
		super.actuallyHurt(level, source, damage);
		setPersistenceRequired();
		if (!isFlyingState()) {
			takeOff();
		}
	}

	@Override
	public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
			EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
		SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnReason, groupData);
		takeOff();
		return result;
	}

	// ---------- flight goals ----------
	private class CircleGoal extends Goal {
		private float angle;
		private float distance;
		private float clockwise;
		private int driftTimer;

		CircleGoal() {
			setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			return isFlyingState() && getTarget() == null;
		}

		@Override
		public void start() {
			distance = 8.0F + getRandom().nextFloat() * 7.0F;
			clockwise = getRandom().nextBoolean() ? 1.0F : -1.0F;
			if (anchorPoint == null) {
				takeOff();
			}
		}

		@Override
		public void tick() {
			ServerLevel level = (ServerLevel) level();
			if (carrionSite != null) {
				// converge on the death site, orbit it, then drop in
				anchorPoint = carrionSite.above(14);
				circleOverCarrion++;
				if (circleOverCarrion > 200) {
					moveTargetPoint = Vec3.atCenterOf(carrionSite).add(0, 2, 0);
					// the phantom steering repels from steep targets, so once we're
					// roughly overhead just cut the engine and drop (no fall damage)
					double dx = getX() - (carrionSite.getX() + 0.5);
					double dz = getZ() - (carrionSite.getZ() + 0.5);
					if (dx * dx + dz * dz < 36.0 || onGround()) {
						land();
					}
					return;
				}
			} else {
				// lazy drift: re-anchor 20-30 above the terrain, occasionally wander
				if (++driftTimer > 500) {
					driftTimer = 0;
					BlockPos shifted = blockPosition().offset(getRandom().nextInt(65) - 32, 0,
							getRandom().nextInt(65) - 32);
					if (level.hasChunkAt(shifted)) { // never sync-load chunks for a drift target
						anchorPoint = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, shifted)
								.above(20 + getRandom().nextInt(10));
					}
				} else if (anchorPoint != null && tickCount % 100 == 0 && level.hasChunkAt(anchorPoint)) {
					BlockPos ground = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, anchorPoint);
					anchorPoint = ground.above(20 + getRandom().nextInt(10));
				}
			}
			if (anchorPoint == null) {
				takeOff();
				return;
			}
			angle += clockwise * 0.06F;
			moveTargetPoint = Vec3.atLowerCornerOf(anchorPoint)
					.add(distance * Mth.cos(angle), 0.0, distance * Mth.sin(angle));
		}
	}

	private class ScavengeGoal extends Goal {
		private int landedTicks;

		ScavengeGoal() {
			setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			return !isFlyingState() && carrionSite != null;
		}

		@Override
		public void start() {
			landedTicks = 0;
		}

		@Override
		public void tick() {
			ServerLevel level = (ServerLevel) level();
			landedTicks++;
			List<ItemEntity> meat = carrionSite == null ? List.of() : meatItemsNear(level, carrionSite, 6.0);
			if (meat.isEmpty() || landedTicks > 1200) {
				carrionSite = null;
				circleOverCarrion = 0;
				takeOff();
				return;
			}
			ItemEntity nearest = meat.get(0);
			for (ItemEntity item : meat) {
				if (distanceToSqr(item) < distanceToSqr(nearest)) {
					nearest = item;
				}
			}
			getNavigation().moveTo(nearest, 1.1);
			if (distanceToSqr(nearest) < 2.5 && tickCount % 20 == 0) {
				nearest.getItem().shrink(1);
				if (nearest.getItem().isEmpty()) {
					nearest.discard();
				}
				playSound(MenagerieSounds.VULTURE_EAT, 1.0F, 1.0F);
				heal(2.0F);
			}
		}
	}

	private class SwoopGoal extends Goal {
		SwoopGoal() {
			setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			LivingEntity target = getTarget();
			return isFlyingState() && target != null && target.isAlive()
					&& target.getHealth() <= 6.0F;
		}

		@Override
		public void tick() {
			LivingEntity target = getTarget();
			if (target == null) {
				return;
			}
			moveTargetPoint = target.position().add(0, target.getBbHeight() * 0.5, 0);
			if (distanceToSqr(target) < 2.5) {
				if (level() instanceof ServerLevel level) {
					doHurtTarget(level, target); // species attack stat: a 1-damage peck
					playSound(MenagerieSounds.VULTURE_SWOOP, 1.0F, 1.1F);
				}
				// climb away after the peck
				moveTargetPoint = position().add(0, 12, 0);
				setTarget(null);
			}
		}

		@Override
		public void stop() {
			if (anchorPoint == null) {
				takeOff();
			}
		}
	}

	/** Phantom-style steering toward moveTargetPoint (banked, velocity-blended). */
	private static class VultureMoveControl extends MoveControl {
		private final VultureEntity vulture;
		private float speed = 0.1F;

		VultureMoveControl(VultureEntity vulture) {
			super(vulture);
			this.vulture = vulture;
		}

		@Override
		public void tick() {
			if (!vulture.isFlyingState()) {
				super.tick();
				return;
			}
			if (vulture.horizontalCollision) {
				vulture.setYRot(vulture.getYRot() + 180.0F);
				this.speed = 0.1F;
			}
			double tdx = vulture.moveTargetPoint.x - vulture.getX();
			double tdy = vulture.moveTargetPoint.y - vulture.getY();
			double tdz = vulture.moveTargetPoint.z - vulture.getZ();
			double sd = Math.sqrt(tdx * tdx + tdz * tdz);
			if (Math.abs(sd) > 1.0E-5F) {
				double yScale = 1.0 - Math.abs(tdy * 0.7F) / sd;
				tdx *= yScale;
				tdz *= yScale;
				sd = Math.sqrt(tdx * tdx + tdz * tdz);
				double sd3 = Math.sqrt(tdx * tdx + tdz * tdz + tdy * tdy);
				float angle = (float) Mth.atan2(tdz, tdx);
				float a = Mth.wrapDegrees(vulture.getYRot() + 90.0F);
				float b = Mth.wrapDegrees(angle * Mth.RAD_TO_DEG);
				vulture.setYRot(Mth.approachDegrees(a, b, 5.0F) - 90.0F);
				vulture.yBodyRot = vulture.getYRot();
				this.speed = Mth.approach(this.speed, 1.2F, 0.04F);
				float xRot = (float) (-(Mth.atan2(-tdy, sd) * Mth.RAD_TO_DEG));
				vulture.setXRot(xRot);
				float moveAngle = vulture.getYRot() + 90.0F;
				double vx = this.speed * Mth.cos(moveAngle * Mth.DEG_TO_RAD) * Math.abs(tdx / sd3);
				double vz = this.speed * Mth.sin(moveAngle * Mth.DEG_TO_RAD) * Math.abs(tdz / sd3);
				double vy = this.speed * Mth.sin(xRot * Mth.DEG_TO_RAD) * Math.abs(tdy / sd3);
				Vec3 delta = vulture.getDeltaMovement();
				vulture.setDeltaMovement(delta.add(new Vec3(vx, vy, vz).subtract(delta).scale(0.2)));
			}
		}
	}

	// ---------- sounds ----------
	@Override
	protected SoundEvent getAmbientSound() {
		return MenagerieSounds.VULTURE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return MenagerieSounds.VULTURE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return MenagerieSounds.VULTURE_DEATH;
	}

	// ---------- persistence ----------
	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putBoolean("menagerie_flying", isFlyingState());
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		if (input.getBooleanOr("menagerie_flying", true)) {
			takeOff();
		} else {
			land();
		}
	}
}
