package dev.lilkuzco.menagerie.entity;

import dev.lilkuzco.menagerie.MenagerieSounds;
import dev.lilkuzco.menagerie.data.Species;
import dev.lilkuzco.menagerie.entity.ai.FollowLeaderGoal;
import java.util.List;
import java.util.UUID;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

/**
 * Savanna apex. Prides work like gorilla troops — one maned male per pride, assigned at
 * group spawn — but a lion's confidence is social rather than personal: it only picks a
 * fight with a healthy player when the pride is at strength ("pride_courage" in JSON).
 * Alone or in a pair it behaves like the leopard, taking only weakened or small prey.
 */
public class LionEntity extends SpeciesMob {
	private static final EntityDataAccessor<Boolean> MANED =
			SynchedEntityData.defineId(LionEntity.class, EntityDataSerializers.BOOLEAN);
	private static final byte EVENT_BITE = 73;
	private static final byte EVENT_ROAR = 74;
	private static final Identifier MANE_SCALE_ID = Identifier.fromNamespaceAndPath("menagerie", "lion_mane_scale");
	private static final Identifier MANE_ATTACK_ID = Identifier.fromNamespaceAndPath("menagerie", "lion_mane_attack");
	private static final int SLEEP_TRANSITION_TICKS = 20;
	private static final int REST_AFTER_STILL_TICKS = 100;

	private int stillTicks;

	private @Nullable UUID prideId;

	public final AnimationState breathingAnimationState = new AnimationState();
	public final AnimationState tailAnimationState = new AnimationState();
	public final AnimationState earAnimationState = new AnimationState();
	public final AnimationState winkAnimationState = new AnimationState();
	public final AnimationState sniffAnimationState = new AnimationState();
	public final AnimationState yawnAnimationState = new AnimationState();
	public final AnimationState biteAnimationState = new AnimationState();
	public final AnimationState roarAnimationState = new AnimationState();
	public final AnimationState sleepStartAnimationState = new AnimationState();
	public final AnimationState sleepLoopAnimationState = new AnimationState();
	public final AnimationState sleepEndAnimationState = new AnimationState();
	private boolean lastResting;
	private int sleepTransitionTicks;

	public LionEntity(EntityType<? extends LionEntity> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(MANED, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25, true));
		this.goalSelector.addGoal(3, new FollowLeaderGoal<>(this, LionEntity.class, 1.0,
				LionEntity::isManed, LionEntity::samePride));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(LionEntity.class));
		// small prey is always fair game
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Animal.class, 10, true, false,
				(target, level) -> target instanceof Animal animal && !(animal instanceof LionEntity)
						&& (animal.isBaby() || animal.getMaxHealth() <= 10.0F)));
		// players only when weakened, or when the pride is big enough to be bold
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
				(target, level) -> target instanceof Player player && !player.isCreative()
						&& !player.isSpectator()
						&& (player.getHealth() < player.getMaxHealth() * 0.5F || prideAtStrength())));
	}

	// ---------- pride ----------
	public boolean isManed() {
		return this.entityData.get(MANED);
	}

	public void setManed(boolean maned) {
		this.entityData.set(MANED, maned);
		AttributeInstance scale = getAttribute(Attributes.SCALE);
		AttributeInstance attack = getAttribute(Attributes.ATTACK_DAMAGE);
		if (maned) {
			scale.addOrReplacePermanentModifier(
					new AttributeModifier(MANE_SCALE_ID, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
			attack.addOrReplacePermanentModifier(
					new AttributeModifier(MANE_ATTACK_ID, 0.35, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		} else {
			scale.removeModifier(MANE_SCALE_ID);
			attack.removeModifier(MANE_ATTACK_ID);
		}
	}

	public @Nullable UUID getPrideId() {
		return prideId;
	}

	public boolean samePride(LionEntity other) {
		return prideId != null && prideId.equals(other.prideId);
	}

	/** Lions are bold in numbers: JSON "pride_courage" is how many it takes. */
	private boolean prideAtStrength() {
		Species species = species();
		int needed = species != null ? species.specialInt("pride_courage", 3) : 3;
		List<LionEntity> pride = level().getEntitiesOfClass(LionEntity.class,
				getBoundingBox().inflate(16.0, 8.0, 16.0), other -> other == this || samePride(other));
		return pride.size() >= needed;
	}

	@Override
	public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
			EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
		SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnReason, groupData);
		if (result instanceof SpeciesGroupData speciesData) {
			this.prideId = speciesData.troopId;
			if (!speciesData.leaderAssigned && !isBaby()) {
				setManed(true);
				speciesData.leaderAssigned = true;
			}
		}
		return result;
	}

	// ---------- ticking ----------
	@Override
	protected void customServerAiStep(ServerLevel level) {
		super.customServerAiStep(level);
		if (prideId == null) {
			prideId = UUID.randomUUID();
		}
	}

	@Override
	public boolean doHurtTarget(ServerLevel level, Entity target) {
		level.broadcastEntityEvent(this, EVENT_BITE);
		return super.doHurtTarget(level, target);
	}

	@Override
	public void playAmbientSound() {
		super.playAmbientSound();
		if (level() instanceof ServerLevel serverLevel) {
			serverLevel.broadcastEntityEvent(this, EVENT_ROAR);
		}
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == EVENT_BITE) {
			biteAnimationState.start(tickCount);
		} else if (id == EVENT_ROAR) {
			roarAnimationState.start(tickCount);
		} else {
			super.handleEntityEvent(id);
		}
	}

	/**
	 * Lions doze most of the day, but "navigation is idle" fires constantly — a lion that
	 * merely paused between strolls would look asleep on its feet. Require it to have been
	 * genuinely still for a few seconds, and never while frozen by NoAI.
	 */
	public boolean isResting() {
		return stillTicks > REST_AFTER_STILL_TICKS && getTarget() == null && !isInWater() && !isNoAi();
	}

	@Override
	public void tick() {
		super.tick();
		// tracked on both sides so the client can decide the pose without a sync field
		if (getDeltaMovement().horizontalDistanceSqr() < 1.0E-4 && !isNoAi()) {
			stillTicks++;
		} else {
			stillTicks = 0;
		}
		if (!level().isClientSide()) {
			return;
		}
		breathingAnimationState.startIfStopped(tickCount);
		boolean resting = isResting();
		if (resting != lastResting) {
			lastResting = resting;
			sleepTransitionTicks = 0;
			sleepStartAnimationState.stop();
			sleepLoopAnimationState.stop();
			sleepEndAnimationState.stop();
			(resting ? sleepStartAnimationState : sleepEndAnimationState).start(tickCount);
		} else if (resting) {
			if (sleepTransitionTicks < SLEEP_TRANSITION_TICKS) {
				sleepTransitionTicks++;
			} else if (!sleepLoopAnimationState.isStarted()) {
				sleepStartAnimationState.stop();
				sleepLoopAnimationState.start(tickCount);
			}
		}
		if (getRandom().nextInt(200) == 0) {
			tailAnimationState.start(tickCount);
		}
		if (getRandom().nextInt(260) == 0) {
			earAnimationState.start(tickCount);
		}
		if (getRandom().nextInt(320) == 0) {
			winkAnimationState.start(tickCount);
		}
		if (getRandom().nextInt(500) == 0) {
			sniffAnimationState.start(tickCount);
		}
		if (resting && getRandom().nextInt(700) == 0) {
			yawnAnimationState.start(tickCount);
		}
	}

	// ---------- sounds ----------
	@Override
	protected SoundEvent getAmbientSound() {
		return MenagerieSounds.LION_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return MenagerieSounds.LION_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return MenagerieSounds.LION_DEATH;
	}

	// ---------- persistence ----------
	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putBoolean("menagerie_maned", isManed());
		output.putString("menagerie_pride", prideId == null ? "" : prideId.toString());
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		setManed(input.getBooleanOr("menagerie_maned", false));
		String pride = input.getStringOr("menagerie_pride", "");
		prideId = pride.isEmpty() ? null : UUID.fromString(pride);
	}
}
