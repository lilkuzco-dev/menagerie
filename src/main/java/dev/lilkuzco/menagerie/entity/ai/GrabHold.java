package dev.lilkuzco.menagerie.entity.ai;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Shared "grab" mechanic (Phase 1 crocodile, Phase 2 python): hold a victim for N
 * ticks, dragging it toward the holder with heavy slowness and chewing it partway
 * through and at the end. Owner calls {@link #start} on a successful bite and
 * {@link #tick} every server tick; {@link #active} gates other AI while holding.
 */
public class GrabHold {
	private final Mob holder;
	private @Nullable LivingEntity grabbed;
	private int ticksLeft;
	private int totalTicks;
	private final double chewFraction;

	public GrabHold(Mob holder, double chewFraction) {
		this.holder = holder;
		this.chewFraction = chewFraction;
	}

	public void start(LivingEntity victim, int ticks) {
		this.grabbed = victim;
		this.ticksLeft = ticks;
		this.totalTicks = ticks;
	}

	public boolean active() {
		return ticksLeft > 0;
	}

	public @Nullable LivingEntity grabbed() {
		return grabbed;
	}

	/** @return true while the grab continues; false once released. */
	public boolean tick(ServerLevel level) {
		if (ticksLeft <= 0) {
			return false;
		}
		ticksLeft--;
		if (grabbed == null || !grabbed.isAlive() || holder.distanceToSqr(grabbed) > 25.0) {
			release();
			return false;
		}
		Vec3 pull = holder.position().subtract(grabbed.position());
		if (pull.lengthSqr() > 1.0) {
			grabbed.setDeltaMovement(pull.normalize().scale(0.25));
		}
		grabbed.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 15, 2), holder);
		if (chewFraction > 0 && (ticksLeft == totalTicks / 2 || ticksLeft == 1)) {
			grabbed.hurtServer(level, holder.damageSources().mobAttack(holder),
					(float) (holder.getAttributeValue(Attributes.ATTACK_DAMAGE) * chewFraction));
		}
		if (ticksLeft == 0) {
			release();
			return false;
		}
		return true;
	}

	public void release() {
		grabbed = null;
		ticksLeft = 0;
	}
}
