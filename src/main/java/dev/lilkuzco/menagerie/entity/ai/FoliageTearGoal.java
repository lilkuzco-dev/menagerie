package dev.lilkuzco.menagerie.entity.ai;

import dev.lilkuzco.menagerie.data.Species;
import dev.lilkuzco.menagerie.entity.GorillaEntity;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.gamerules.GameRules;
import org.jspecify.annotations.Nullable;

/**
 * Cosmetic foliage tearing: idle adults occasionally rip one leaves block within reach
 * and chew it. Gated behind the species JSON flag "foliage_tearing" AND mobGriefing.
 */
public class FoliageTearGoal extends Goal {
	private final GorillaEntity gorilla;
	private @Nullable BlockPos leaves;
	private int ticks;

	public FoliageTearGoal(GorillaEntity gorilla) {
		this.gorilla = gorilla;
		setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		if (gorilla.isBaby() || gorilla.getTarget() != null || gorilla.isOrderedToSit()
				|| gorilla.getRandom().nextInt(reducedTickDelay(400)) != 0) {
			return false;
		}
		Species species = gorilla.species();
		if (species == null || !species.specialBool("foliage_tearing", false)) {
			return false;
		}
		if (!(gorilla.level() instanceof ServerLevel serverLevel)
				|| !serverLevel.getGameRules().get(GameRules.MOB_GRIEFING)) {
			return false;
		}
		this.leaves = findLeaves();
		return this.leaves != null;
	}

	private @Nullable BlockPos findLeaves() {
		BlockPos eye = BlockPos.containing(gorilla.getEyePosition());
		for (BlockPos pos : BlockPos.betweenClosed(eye.offset(-2, -1, -2), eye.offset(2, 2, 2))) {
			if (gorilla.level().getBlockState(pos).is(BlockTags.LEAVES)) {
				return pos.immutable();
			}
		}
		return null;
	}

	@Override
	public void start() {
		this.ticks = adjustedTickDelay(30);
	}

	@Override
	public boolean canContinueToUse() {
		return ticks > 0 && leaves != null;
	}

	@Override
	public void tick() {
		ticks--;
		if (leaves == null) {
			return;
		}
		gorilla.getLookControl().setLookAt(leaves.getX() + 0.5, leaves.getY() + 0.5, leaves.getZ() + 0.5);
		if (ticks == adjustedTickDelay(15)) {
			if (gorilla.level().getBlockState(leaves).is(BlockTags.LEAVES)) {
				gorilla.level().destroyBlock(leaves, false, gorilla);
				gorilla.startEatingAnimation();
			}
			leaves = null;
		}
	}
}
