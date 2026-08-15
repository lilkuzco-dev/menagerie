package dev.lilkuzco.menagerie.entity.ai;

import dev.lilkuzco.menagerie.entity.LeopardEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.phys.Vec3;

/**
 * Stalker melee: crouch-approach while far, spring into a pounce once in leap range,
 * then finish with normal melee. The crouch flag drives the model pose.
 */
public class LeopardAttackGoal extends MeleeAttackGoal {
	private final LeopardEntity leopard;
	private int pounceCooldown;

	public LeopardAttackGoal(LeopardEntity leopard, double speedModifier) {
		super(leopard, speedModifier, true);
		this.leopard = leopard;
	}

	@Override
	public void tick() {
		super.tick();
		if (pounceCooldown > 0) {
			pounceCooldown--;
		}
		LivingEntity target = leopard.getTarget();
		if (target == null) {
			leopard.setCrouching(false);
			return;
		}
		double distSqr = leopard.distanceToSqr(target);
		leopard.setCrouching(distSqr > 20.0);
		if (distSqr <= 30.0 && distSqr >= 9.0 && pounceCooldown == 0
				&& leopard.onGround() && leopard.getSensing().hasLineOfSight(target)) {
			Vec3 toward = target.position().subtract(leopard.position());
			leopard.setDeltaMovement(toward.normalize().scale(0.75).add(0.0, 0.4, 0.0));
			leopard.startPounce();
			pounceCooldown = 60;
		}
	}

	@Override
	public void stop() {
		super.stop();
		leopard.setCrouching(false);
	}
}
