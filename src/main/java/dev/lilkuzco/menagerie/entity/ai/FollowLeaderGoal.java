package dev.lilkuzco.menagerie.entity.ai;

import dev.lilkuzco.menagerie.entity.SpeciesMob;
import java.util.EnumSet;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jspecify.annotations.Nullable;

/**
 * Group cohesion: followers trail their group's leader instead of each wandering off
 * alone, so a troop or pride reads as one animal on the move. Structure follows Animal
 * Garden's follow-the-male goal (public domain); who counts as leader and who counts as
 * kin are supplied per animal, so gorilla troops and lion prides share one goal.
 * Tamed animals are exempt — they follow their owner instead.
 */
public class FollowLeaderGoal<T extends SpeciesMob> extends Goal {
	private static final double START_FOLLOW_DIST_SQR = 50.0;  // ~7 blocks
	private static final double GIVE_UP_DIST_SQR = 300.0;      // ~17 blocks
	private static final double SCAN_RANGE = 12.0;

	private final T mob;
	private final Class<T> type;
	private final double speedModifier;
	private final Predicate<T> isLeader;
	private final BiPredicate<T, T> sameGroup;
	private @Nullable T leader;
	private int timeToRecalcPath;

	public FollowLeaderGoal(T mob, Class<T> type, double speedModifier,
			Predicate<T> isLeader, BiPredicate<T, T> sameGroup) {
		this.mob = mob;
		this.type = type;
		this.speedModifier = speedModifier;
		this.isLeader = isLeader;
		this.sameGroup = sameGroup;
		setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if (mob.isPassenger() || mob.getTarget() != null || mob.isBaby()
				|| mob.isTame() || isLeader.test(mob)) {
			return false;
		}
		if (leader == null) {
			leader = findLeader();
		}
		if (leader == null || !leader.isAlive() || !isLeader.test(leader)) {
			leader = null;
			return false;
		}
		double distSqr = mob.distanceToSqr(leader);
		if (distSqr > GIVE_UP_DIST_SQR) {
			leader = null; // wandered too far to bother — re-acquire later
			return false;
		}
		return distSqr > START_FOLLOW_DIST_SQR;
	}

	private @Nullable T findLeader() {
		T best = null;
		double bestDist = Double.MAX_VALUE;
		for (T candidate : mob.level().getEntitiesOfClass(type,
				mob.getBoundingBox().inflate(SCAN_RANGE, 4.0, SCAN_RANGE),
				other -> isLeader.test(other) && !other.isBaby() && sameGroup.test(mob, other))) {
			double dist = mob.distanceToSqr(candidate);
			if (dist < bestDist) {
				best = candidate;
				bestDist = dist;
			}
		}
		return best;
	}

	@Override
	public boolean canContinueToUse() {
		return leader != null && leader.isAlive() && mob.getTarget() == null
				&& mob.distanceToSqr(leader) > START_FOLLOW_DIST_SQR
				&& mob.distanceToSqr(leader) <= GIVE_UP_DIST_SQR;
	}

	@Override
	public void start() {
		timeToRecalcPath = 0;
	}

	@Override
	public void stop() {
		leader = null;
		mob.getNavigation().stop();
	}

	@Override
	public void tick() {
		if (leader != null && --timeToRecalcPath <= 0) {
			timeToRecalcPath = adjustedTickDelay(10);
			mob.getNavigation().moveTo(leader, speedModifier);
		}
	}
}
