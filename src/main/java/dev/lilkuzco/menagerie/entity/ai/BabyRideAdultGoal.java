package dev.lilkuzco.menagerie.entity.ai;

import dev.lilkuzco.menagerie.entity.GorillaEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

/** Baby gorillas seek the nearest free adult in their troop and climb onto its back. */
public class BabyRideAdultGoal extends Goal {
	private final GorillaEntity baby;
	private @Nullable GorillaEntity adult;
	private int retryCooldown;

	public BabyRideAdultGoal(GorillaEntity baby) {
		this.baby = baby;
		setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if (!baby.isBaby() || baby.isPassenger() || baby.getTarget() != null) {
			return false;
		}
		if (retryCooldown > 0) {
			retryCooldown--;
			return false;
		}
		this.adult = findAdult();
		return this.adult != null;
	}

	private @Nullable GorillaEntity findAdult() {
		AABB box = baby.getBoundingBox().inflate(16.0, 8.0, 16.0);
		GorillaEntity best = null;
		double bestDist = Double.MAX_VALUE;
		for (GorillaEntity candidate : baby.level().getEntitiesOfClass(GorillaEntity.class, box)) {
			if (candidate != baby && !candidate.isBaby() && candidate.getPassengers().isEmpty()
					&& baby.sameTroop(candidate)) {
				double dist = baby.distanceToSqr(candidate);
				if (dist < bestDist) {
					best = candidate;
					bestDist = dist;
				}
			}
		}
		return best;
	}

	@Override
	public boolean canContinueToUse() {
		return baby.isBaby() && !baby.isPassenger() && adult != null && adult.isAlive()
				&& adult.getPassengers().isEmpty();
	}

	@Override
	public void tick() {
		if (adult == null) {
			return;
		}
		if (baby.distanceToSqr(adult) < 4.0) {
			baby.startRiding(adult);
			retryCooldown = reducedTickDelay(200);
		} else {
			baby.getNavigation().moveTo(adult, 1.2);
		}
	}

	@Override
	public void stop() {
		adult = null;
		retryCooldown = reducedTickDelay(100);
		baby.getNavigation().stop();
	}
}
