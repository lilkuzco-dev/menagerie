package dev.lilkuzco.menagerie.entity;

import dev.lilkuzco.menagerie.MenagerieSounds;
import dev.lilkuzco.menagerie.data.Species;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Ambush predator: floats in water, lunges at prey within reach, briefly drags the
 * victim (slowness + damage over 2s), then releases. Neutral on land — it only
 * retaliates out of water. Lunge range / grab length tunable via species "special".
 */
public class CrocodileEntity extends SpeciesMob {
	private static final byte EVENT_LUNGE = 66;

	private final dev.lilkuzco.menagerie.entity.ai.GrabHold grab =
			new dev.lilkuzco.menagerie.entity.ai.GrabHold(this, 0.5);
	private int lungeCooldown;

	public int clientLungeTicks;

	public CrocodileEntity(EntityType<? extends CrocodileEntity> type, Level level) {
		super(type, level);
		setPathfindingMalus(PathType.WATER, 0.0F);
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new AmphibiousPathNavigation(this, level);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true));
		this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 0.8, 40));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.7));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}

	@Override
	protected void customServerAiStep(ServerLevel level) {
		super.customServerAiStep(level);
		Species species = species();
		double lungeRange = species != null ? species.specialDouble("lunge_range", 3.0) : 3.0;
		int grabLength = species != null ? species.specialInt("grab_ticks", 40) : 40;

		if (lungeCooldown > 0) {
			lungeCooldown--;
		}

		// active grab: drag victim toward us, slow it, chew twice over the grab
		if (grab.active()) {
			if (!grab.tick(level)) {
				lungeCooldown = Math.max(lungeCooldown, 200);
			}
			return;
		}

		// ambush: only in water, only against victims in the water with us
		if (!isInWater() || lungeCooldown > 0) {
			return;
		}
		LivingEntity victim = findAmbushVictim(level, lungeRange);
		if (victim == null) {
			return;
		}
		double dist = distanceToSqr(victim);
		if (dist < 2.6) {
			// close enough: bite and start the grab
			doHurtTarget(level, victim);
			grab.start(victim, grabLength);
			lungeCooldown = 200;
			level.broadcastEntityEvent(this, EVENT_LUNGE);
			playSound(MenagerieSounds.CROCODILE_SNAP, 1.2F, 1.0F);
		} else {
			// lunge toward the victim
			Vec3 toward = victim.position().subtract(position());
			setDeltaMovement(toward.normalize().scale(0.8).add(0.0, 0.15, 0.0));
			setYRot(-((float) Mth.atan2(toward.x, toward.z)) * Mth.RAD_TO_DEG);
			level.broadcastEntityEvent(this, EVENT_LUNGE);
		}
	}

	private @Nullable LivingEntity findAmbushVictim(ServerLevel level, double range) {
		LivingEntity best = null;
		double bestDist = Double.MAX_VALUE;
		for (LivingEntity candidate : level.getEntitiesOfClass(LivingEntity.class,
				getBoundingBox().inflate(range, 1.5, range))) {
			if (candidate == this || candidate instanceof CrocodileEntity || !candidate.isInWater()
					|| !candidate.attackable()) {
				continue;
			}
			if (candidate instanceof Player player && (player.isCreative() || player.isSpectator())) {
				continue;
			}
			double dist = distanceToSqr(candidate);
			if (dist < bestDist) {
				best = candidate;
				bestDist = dist;
			}
		}
		return best;
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == EVENT_LUNGE) {
			clientLungeTicks = 15;
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide() && clientLungeTicks > 0) {
			clientLungeTicks--;
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MenagerieSounds.CROCODILE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return MenagerieSounds.CROCODILE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return MenagerieSounds.CROCODILE_DEATH;
	}
}
