package dev.lilkuzco.menagerie.entity.ai;

import dev.lilkuzco.menagerie.entity.GorillaEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jspecify.annotations.Nullable;

/**
 * Troop cohesion: adults trail their troop's silverback instead of each wandering off
 * alone, so a troop reads as a group on the move. Structure follows Animal Garden's
 * follow-the-male goal (public domain); membership is our troop id rather than gender,
 * and tamed gorillas are exempt — they follow their owner instead.
 */
public class FollowSilverbackGoal extends Goal {
	private static final double START_FOLLOW_DIST_SQR = 50.0;  // ~7 blocks
	private static final double GIVE_UP_DIST_SQR = 300.0;      // ~17 blocks
	private static final double SCAN_RANGE = 12.0;

	private final GorillaEntity gorilla;
	private final double speedModifier;
	private @Nullable GorillaEntity leader;
	private int timeToRecalcPath;

	public FollowSilverbackGoal(GorillaEntity gorilla, double speedModifier) {
		this.gorilla = gorilla;
		this.speedModifier = speedModifier;
		setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if (gorilla.isPassenger() || gorilla.getTarget() != null || gorilla.isBaby()
				|| gorilla.isTame() || gorilla.isSilverback() || gorilla.getTroopId() == null) {
			return false;
		}
		if (leader == null) {
			leader = findLeader();
		}
		if (leader == null || !leader.isAlive() || !leader.isSilverback()) {
			leader = null;
			return false;
		}
		double distSqr = gorilla.distanceToSqr(leader);
		if (distSqr > GIVE_UP_DIST_SQR) {
			leader = null; // wandered too far to bother — re-acquire later
			return false;
		}
		return distSqr > START_FOLLOW_DIST_SQR;
	}

	private @Nullable GorillaEntity findLeader() {
		GorillaEntity best = null;
		double bestDist = Double.MAX_VALUE;
		for (GorillaEntity candidate : gorilla.level().getEntitiesOfClass(GorillaEntity.class,
				gorilla.getBoundingBox().inflate(SCAN_RANGE, 4.0, SCAN_RANGE),
				other -> other.isSilverback() && !other.isBaby() && gorilla.sameTroop(other))) {
			double dist = gorilla.distanceToSqr(candidate);
			if (dist < bestDist) {
				best = candidate;
				bestDist = dist;
			}
		}
		return best;
	}

	@Override
	public boolean canContinueToUse() {
		return leader != null && leader.isAlive() && gorilla.getTarget() == null
				&& gorilla.distanceToSqr(leader) > START_FOLLOW_DIST_SQR
				&& gorilla.distanceToSqr(leader) <= GIVE_UP_DIST_SQR;
	}

	@Override
	public void start() {
		timeToRecalcPath = 0;
	}

	@Override
	public void stop() {
		leader = null;
		gorilla.getNavigation().stop();
	}

	@Override
	public void tick() {
		if (leader != null && --timeToRecalcPath <= 0) {
			timeToRecalcPath = adjustedTickDelay(10);
			gorilla.getNavigation().moveTo(leader, speedModifier);
		}
	}
}
