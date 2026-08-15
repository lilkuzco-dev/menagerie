package dev.lilkuzco.menagerie.entity;

import dev.lilkuzco.menagerie.MenagerieSounds;
import dev.lilkuzco.menagerie.data.Species;
import dev.lilkuzco.menagerie.entity.ai.GrabHold;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Coiled ambusher, near-motionless in grass and sand. Rattles a warning at 4 blocks;
 * strikes only inside 2 — a careful player is never bitten. Venom comes from the
 * species "venom" block (viper: Poison II 8s); the python instead takes a brief
 * grab, reusing the crocodile's GrabHold. Slithers away from sprinting players.
 */
public class SnakeEntity extends SpeciesMob {
	private static final EntityDataAccessor<Boolean> COILED =
			SynchedEntityData.defineId(SnakeEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> RATTLING =
			SynchedEntityData.defineId(SnakeEntity.class, EntityDataSerializers.BOOLEAN);
	private static final byte EVENT_STRIKE = 70;

	private final GrabHold grab = new GrabHold(this, 0.0); // python hold: no chewing
	private int rattleTicks;    // how long we have been warning
	private int strikeCooldown;
	public int clientStrikeTicks;

	public SnakeEntity(EntityType<? extends SnakeEntity> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(COILED, true);
		builder.define(RATTLING, false);
	}

	@Override
	protected void registerGoals() {
		// sprint check MUST be the avoidPredicate (3rd arg): in the 6-arg ctor it lands
		// in the selector slot and the snake flees every player (strike never reachable)
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class,
				entity -> entity instanceof Player player && player.isSprinting(),
				6.0F, 0.9, 1.1, entity -> true));
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6, 400));
	}

	public boolean isCoiled() {
		return this.entityData.get(COILED);
	}

	public boolean isRattling() {
		return this.entityData.get(RATTLING);
	}

	@Override
	protected void customServerAiStep(ServerLevel level) {
		super.customServerAiStep(level);
		this.entityData.set(COILED, getNavigation().isDone() && !grab.active());

		if (strikeCooldown > 0) {
			strikeCooldown--;
		}
		if (grab.active()) {
			grab.tick(level);
			return;
		}

		Player threat = level.getNearestPlayer(this, 6.0);
		boolean validThreat = threat != null && !threat.isCreative() && !threat.isSpectator();
		double dist = validThreat ? distanceToSqr(threat) : Double.MAX_VALUE;

		if (validThreat && dist <= 16.0) {
			if (!isRattling()) {
				this.entityData.set(RATTLING, true);
				rattleTicks = 0;
			}
			rattleTicks++;
			if (rattleTicks % 25 == 1) {
				playSound(MenagerieSounds.SNAKE_RATTLE, 1.0F, 1.0F);
			}
			getLookControl().setLookAt(threat);
			// strike only after a real warning window, and only in kissing distance
			if (dist <= 4.0 && rattleTicks >= 15 && strikeCooldown == 0) {
				strike(level, threat);
			}
		} else if (isRattling()) {
			this.entityData.set(RATTLING, false);
			rattleTicks = 0;
		}
	}

	private void strike(ServerLevel level, Player victim) {
		strikeCooldown = 40;
		level.broadcastEntityEvent(this, EVENT_STRIKE);
		playSound(MenagerieSounds.SNAKE_STRIKE, 1.0F, 1.0F);
		Vec3 toward = victim.position().subtract(position());
		if (toward.lengthSqr() > 0.01) {
			setDeltaMovement(toward.normalize().scale(0.35).add(0, 0.1, 0));
			setYRot(-((float) Mth.atan2(toward.x, toward.z)) * Mth.RAD_TO_DEG);
		}
		doHurtTarget(level, victim);
		Species species = species();
		if (species != null && species.venom() != null) {
			Species.Venom venom = species.venom();
			Optional<Holder.Reference<MobEffect>> effect =
					BuiltInRegistries.MOB_EFFECT.get(venom.effect());
			effect.ifPresent(holder -> victim.addEffect(
					new MobEffectInstance(holder, venom.seconds() * 20, venom.amplifier()), this));
		} else if (species != null && species.specialBool("constrict", false)) {
			grab.start(victim, species.specialInt("constrict_ticks", 30));
		}
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == EVENT_STRIKE) {
			clientStrikeTicks = 10;
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide() && clientStrikeTicks > 0) {
			clientStrikeTicks--;
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return MenagerieSounds.SNAKE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return MenagerieSounds.SNAKE_DEATH;
	}
}
