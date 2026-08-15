package dev.lilkuzco.menagerie.entity.ai;

import dev.lilkuzco.menagerie.entity.GorillaEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.phys.AABB;

/**
 * Neutral-retaliate with troop-wide anger: hurting one gorilla aggros every adult in
 * the SAME troop (concept from vanilla wolf/zombified-piglin group anger, scoped by
 * troop id instead of alerting the whole class).
 */
public class TroopRetaliateGoal extends HurtByTargetGoal {
	private final GorillaEntity gorilla;

	public TroopRetaliateGoal(GorillaEntity gorilla) {
		super(gorilla);
		this.gorilla = gorilla;
		setAlertOthers();
	}

	@Override
	protected void alertOthers() {
		double range = getFollowDistance();
		AABB box = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate(range, 10.0, range);
		for (GorillaEntity other : this.mob.level().getEntitiesOfClass(GorillaEntity.class, box)) {
			if (other != this.mob && other.getTarget() == null && !other.isBaby()
					&& gorilla.sameTroop(other)
					&& this.mob.getLastHurtByMob() != null
					&& !other.isAlliedTo(this.mob.getLastHurtByMob())) {
				alertOther(other, this.mob.getLastHurtByMob());
			}
		}
	}
}
