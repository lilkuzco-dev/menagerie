package dev.lilkuzco.menagerie.entity;

import dev.lilkuzco.menagerie.MenagerieSounds;
import dev.lilkuzco.menagerie.data.Species;
import dev.lilkuzco.menagerie.entity.ai.BabyRideAdultGoal;
import dev.lilkuzco.menagerie.entity.ai.FoliageTearGoal;
import dev.lilkuzco.menagerie.entity.ai.FollowSilverbackGoal;
import dev.lilkuzco.menagerie.entity.ai.TroopRetaliateGoal;
import java.util.List;
import java.util.UUID;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * The flagship. Troop life: exactly one silverback per troop (bigger, stronger,
 * chest-beats); hurting any member angers the adults; babies ride adults; melon
 * slices tame (1-in-3) into a wolf-style companion. All numbers live in species JSON.
 */
public class GorillaEntity extends SpeciesMob {
	private static final EntityDataAccessor<Boolean> SILVERBACK =
			SynchedEntityData.defineId(GorillaEntity.class, EntityDataSerializers.BOOLEAN);

	private static final Identifier SILVERBACK_SCALE_ID = Identifier.fromNamespaceAndPath("menagerie", "silverback_scale");
	private static final Identifier SILVERBACK_ATTACK_ID = Identifier.fromNamespaceAndPath("menagerie", "silverback_attack");
	private static final byte EVENT_CHEST_BEAT = 64;
	private static final byte EVENT_EAT_LEAVES = 65;
	private static final byte EVENT_PUNCH = 71;
	private static final byte EVENT_HOOT = 72;
	private static final int PROMOTION_SCAN_INTERVAL = 40;
	private static final int PROMOTION_DELAY_TICKS = 1200; // 60s without a silverback

	private @Nullable UUID troopId;
	private int leaderlessTicks;
	private int beatCooldown = -1;
	private int hostileBeatCooldown;
	private int serverBeatTicks;

	// client-side keyframe animation states (see tick()/handleEntityEvent)
	public final AnimationState breathingAnimationState = new AnimationState();
	public final AnimationState chestPumpAnimationState = new AnimationState();
	public final AnimationState eatAnimationState = new AnimationState();
	public final AnimationState punchAnimationState = new AnimationState();
	public final AnimationState winkAnimationState = new AnimationState();
	public final AnimationState sniffAnimationState = new AnimationState();
	public final AnimationState soundAnimationState = new AnimationState();
	public final AnimationState sitStartAnimationState = new AnimationState();
	public final AnimationState sitLoopAnimationState = new AnimationState();
	public final AnimationState sitEndAnimationState = new AnimationState();
	/** fromNormalToSitting / fromSittingToNormal are both 0.5s of keyframes. */
	private static final int SIT_TRANSITION_TICKS = 10;
	private boolean lastSittingPose;
	private int sitTransitionTicks;

	public GorillaEntity(EntityType<? extends GorillaEntity> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SILVERBACK, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25, true));
		this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.3, 10.0F, 3.0F));
		this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
		this.goalSelector.addGoal(5, new TemptGoal(this, 1.1, stack -> isFood(stack) || isTameItem(stack), false));
		this.goalSelector.addGoal(6, new BabyRideAdultGoal(this));
		this.goalSelector.addGoal(7, new FoliageTearGoal(this));
		this.goalSelector.addGoal(8, new FollowSilverbackGoal(this, 1.0));
		this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 0.9) {
			@Override
			public boolean canUse() {
				// hungry troops roam restlessly; content ones lounge near the food
				setInterval(isContent() ? 240 : 80);
				return super.canUse();
			}
		});
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, new TroopRetaliateGoal(this));
	}

	// ---------- silverback ----------
	public boolean isSilverback() {
		return this.entityData.get(SILVERBACK);
	}

	public void setSilverback(boolean silverback) {
		this.entityData.set(SILVERBACK, silverback);
		AttributeInstance scale = getAttribute(Attributes.SCALE);
		AttributeInstance attack = getAttribute(Attributes.ATTACK_DAMAGE);
		if (silverback) {
			scale.addOrReplacePermanentModifier(new AttributeModifier(SILVERBACK_SCALE_ID, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
			attack.addOrReplacePermanentModifier(new AttributeModifier(SILVERBACK_ATTACK_ID, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		} else {
			scale.removeModifier(SILVERBACK_SCALE_ID);
			attack.removeModifier(SILVERBACK_ATTACK_ID);
		}
	}

	/** A fed troop is easier to befriend: 1-in-3 doubles to 2-in-3 while content. */
	@Override
	protected float tameChance() {
		return isContent() ? 2.0F / 3.0F : 1.0F / 3.0F;
	}

	/** Meals are troop news: everyone within 16 blocks shares the contentment. */
	@Override
	public void onForaged() {
		super.onForaged();
		for (GorillaEntity other : level().getEntitiesOfClass(GorillaEntity.class,
				getBoundingBox().inflate(16.0, 8.0, 16.0), other -> other != this && sameTroop(other))) {
			other.markContentFromTroop();
		}
	}

	void markContentFromTroop() {
		super.onForaged(); // set the timer without re-broadcasting (no infinite recursion)
	}

	/**
	 * Fur colour is a per-individual pick from the species' texture list (stable for the
	 * life of the entity). The silverback's saddle is NOT a texture swap any more — it is
	 * a translucent overlay layer, so it composes with whichever fur this gorilla drew.
	 */
	@Override
	public Identifier texture() {
		Species species = species();
		if (species == null || species.textures().isEmpty()) {
			return super.texture();
		}
		List<Identifier> options = species.textures();
		int pick = Math.floorMod(getUUID().hashCode(), options.size());
		return options.get(pick);
	}

	public @Nullable UUID getTroopId() {
		return troopId;
	}

	public boolean sameTroop(GorillaEntity other) {
		return troopId != null && troopId.equals(other.troopId);
	}

	private List<GorillaEntity> troopNearby() {
		// wide radius: a scattered troop that can't see its leader would double-promote
		// (the merge dedup below self-heals it, but a big scan window keeps it rare)
		AABB box = getBoundingBox().inflate(48.0, 24.0, 48.0);
		return level().getEntitiesOfClass(GorillaEntity.class, box, other -> sameTroop(other) || other == this);
	}

	// ---------- spawn ----------
	@Override
	public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
			EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
		SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnReason, groupData);
		if (result instanceof SpeciesGroupData speciesData) {
			this.troopId = speciesData.troopId;
			if (!speciesData.leaderAssigned && !isBaby()) {
				setSilverback(true);
				speciesData.leaderAssigned = true;
			}
		}
		return result;
	}

	// ---------- troop upkeep: formation, promotion, chest-beat ----------
	@Override
	protected void customServerAiStep(ServerLevel level) {
		super.customServerAiStep(level);

		if (serverBeatTicks > 0) {
			serverBeatTicks--;
		}

		// grown babies climb off
		if (isPassenger() && !isBaby()) {
			stopRiding();
		}

		if (tickCount % PROMOTION_SCAN_INTERVAL != 0) {
			return;
		}

		// summoned gorillas coalesce into troops lazily
		if (troopId == null) {
			GorillaEntity nearest = null;
			double best = Double.MAX_VALUE;
			for (GorillaEntity other : level.getEntitiesOfClass(GorillaEntity.class, getBoundingBox().inflate(16.0))) {
				if (other != this && other.troopId != null && distanceToSqr(other) < best) {
					nearest = other;
					best = distanceToSqr(other);
				}
			}
			troopId = nearest != null ? nearest.troopId : UUID.randomUUID();
		}

		List<GorillaEntity> troop = troopNearby();
		boolean hasLeader = troop.stream().anyMatch(GorillaEntity::isSilverback);

		if (isSilverback()) {
			// two silverbacks in one troop (merge edge case): lowest UUID keeps the job
			for (GorillaEntity other : troop) {
				if (other != this && other.isSilverback() && other.getUUID().compareTo(getUUID()) < 0) {
					setSilverback(false);
					break;
				}
			}
			// the silverback's position IS the troop center for the territory API
			if (!isTame() && tickCount % 120 == 0) {
				dev.lilkuzco.menagerie.MenagerieTerritories.refresh(level, blockPosition(), 12, "gorilla_troop");
			}
			runChestBeat(level);
			return;
		}

		if (isBaby() || isTame()) {
			leaderlessTicks = 0;
			return;
		}

		if (hasLeader) {
			leaderlessTicks = 0;
			return;
		}
		leaderlessTicks += PROMOTION_SCAN_INTERVAL;
		if (leaderlessTicks >= PROMOTION_DELAY_TICKS && promotionCandidate(troop) == this) {
			setSilverback(true);
			leaderlessTicks = 0;
			triggerChestBeat(level); // announce the new leader
		}
	}

	/** "Largest remaining adult": highest max health, tie-broken by UUID for determinism. */
	private @Nullable GorillaEntity promotionCandidate(List<GorillaEntity> troop) {
		GorillaEntity best = null;
		for (GorillaEntity candidate : troop) {
			if (candidate.isBaby() || candidate.isTame()) {
				continue;
			}
			if (best == null
					|| candidate.getMaxHealth() > best.getMaxHealth()
					|| (candidate.getMaxHealth() == best.getMaxHealth()
							&& candidate.getUUID().compareTo(best.getUUID()) < 0)) {
				best = candidate;
			}
		}
		return best;
	}

	private void runChestBeat(ServerLevel level) {
		Species species = species();
		int cooldownMin = species != null ? species.specialInt("chest_beat_cooldown_min", 600) : 600;
		int cooldownMax = species != null ? species.specialInt("chest_beat_cooldown_max", 1800) : 1800;
		double detectRadius = species != null ? species.specialDouble("chest_beat_detect_radius", 8.0) : 8.0;

		if (beatCooldown < 0) {
			beatCooldown = cooldownMin + getRandom().nextInt(Math.max(1, cooldownMax - cooldownMin));
		}
		beatCooldown -= PROMOTION_SCAN_INTERVAL;
		if (hostileBeatCooldown > 0) {
			hostileBeatCooldown -= PROMOTION_SCAN_INTERVAL;
		}

		boolean hostileNear = hostileBeatCooldown <= 0 && !level.getEntitiesOfClass(Monster.class,
				getBoundingBox().inflate(detectRadius, 4.0, detectRadius), Entity::isAlive).isEmpty();

		if (hostileNear || beatCooldown <= 0) {
			triggerChestBeat(level);
			beatCooldown = cooldownMin + getRandom().nextInt(Math.max(1, cooldownMax - cooldownMin));
			if (isContent()) {
				beatCooldown *= 2; // well-fed silverbacks have less to prove
			}
			if (hostileNear) {
				hostileBeatCooldown = 200;
			}
		}
	}

	private void triggerChestBeat(ServerLevel level) {
		if (serverBeatTicks > 0 || isOrderedToSit()) {
			return;
		}
		serverBeatTicks = 50; // matches the 2.5s chestPump animation
		Species species = species();
		double radius = species != null ? species.specialDouble("intimidate_radius", 6.0) : 6.0;
		int slownessTicks = species != null ? species.specialInt("intimidate_slowness_ticks", 100) : 100;
		level.broadcastEntityEvent(this, EVENT_CHEST_BEAT);
		playSound(MenagerieSounds.GORILLA_CHEST_BEAT, 2.0F, 1.0F);
		// intimidation: hostile mobs only, never players
		for (Monster monster : level.getEntitiesOfClass(Monster.class,
				getBoundingBox().inflate(radius, 4.0, radius))) {
			monster.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, slownessTicks, 0), this);
		}
	}

	public void startEatingAnimation() {
		if (level() instanceof ServerLevel serverLevel) {
			serverLevel.broadcastEntityEvent(this, EVENT_EAT_LEAVES);
			playSound(MenagerieSounds.GORILLA_EAT, 1.0F, 1.0F);
		}
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == EVENT_CHEST_BEAT) {
			chestPumpAnimationState.start(tickCount);
		} else if (id == EVENT_EAT_LEAVES) {
			eatAnimationState.start(tickCount);
		} else if (id == EVENT_PUNCH) {
			punchAnimationState.start(tickCount);
		} else if (id == EVENT_HOOT) {
			soundAnimationState.start(tickCount);
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	public boolean doHurtTarget(ServerLevel level, net.minecraft.world.entity.Entity target) {
		level.broadcastEntityEvent(this, EVENT_PUNCH);
		return super.doHurtTarget(level, target);
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide()) {
			tickAnimations();
		}
	}

	/**
	 * Client-only animation driving. Breathing always loops; the sitting set is a small
	 * state machine so exactly ONE of start/loop/end is ever running (they are absolute
	 * poses — overlapping them would double the rotations); wink and sniff are random
	 * idle flavour.
	 */
	private void tickAnimations() {
		breathingAnimationState.startIfStopped(tickCount);

		boolean sitting = isInSittingPose();
		if (sitting != lastSittingPose) {
			lastSittingPose = sitting;
			sitTransitionTicks = 0;
			sitStartAnimationState.stop();
			sitLoopAnimationState.stop();
			sitEndAnimationState.stop();
			(sitting ? sitStartAnimationState : sitEndAnimationState).start(tickCount);
		} else if (sitting) {
			if (sitTransitionTicks < SIT_TRANSITION_TICKS) {
				sitTransitionTicks++;
			} else if (!sitLoopAnimationState.isStarted()) {
				sitStartAnimationState.stop();
				sitLoopAnimationState.start(tickCount);
			}
		}

		if (!sitting && getTarget() == null) {
			if (getRandom().nextInt(220) == 0) {
				winkAnimationState.start(tickCount);
			}
			if (getRandom().nextInt(420) == 0) {
				sniffAnimationState.start(tickCount);
			}
		}
	}

	@Override
	public void playAmbientSound() {
		super.playAmbientSound();
		// ambient sounds are chosen server-side; tell clients so the mouth moves with it
		if (level() instanceof ServerLevel serverLevel) {
			serverLevel.broadcastEntityEvent(this, EVENT_HOOT);
		}
	}

	// ---------- baby riding ----------
	@Override
	protected boolean canAddPassenger(Entity passenger) {
		return passenger instanceof GorillaEntity baby && baby.isBaby() && getPassengers().isEmpty();
	}

	@Override
	protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scale) {
		return new Vec3(0.0, dimensions.height() * 0.92, -0.3 * getScale())
				.yRot(-this.yBodyRot * Mth.DEG_TO_RAD);
	}

	// ---------- sounds ----------
	@Override
	protected SoundEvent getAmbientSound() {
		return MenagerieSounds.GORILLA_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource source) {
		return MenagerieSounds.GORILLA_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return MenagerieSounds.GORILLA_DEATH;
	}

	// ---------- persistence ----------
	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putBoolean("menagerie_silverback", isSilverback());
		output.putString("menagerie_troop", troopId == null ? "" : troopId.toString());
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		setSilverback(input.getBooleanOr("menagerie_silverback", false));
		String troop = input.getStringOr("menagerie_troop", "");
		this.troopId = troop.isEmpty() ? null : UUID.fromString(troop);
	}
}
