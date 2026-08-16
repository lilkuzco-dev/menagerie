package dev.lilkuzco.menagerie.entity.ai;

import dev.lilkuzco.menagerie.data.Species;
import dev.lilkuzco.menagerie.entity.SpeciesMob;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import org.jspecify.annotations.Nullable;

/**
 * Generic foraging, driven entirely by the species "forage" JSON block: seek a listed
 * block within range, eat it (melon-type blocks break, berry bushes get picked), then
 * report the meal via {@link SpeciesMob#onForaged()} (gorillas spread contentment to
 * the troop). mobGriefing-gated; a cooldown keeps farms from being stripped. Attached
 * automatically to ANY SpeciesMob whose species declares forage — zero Java per animal.
 */
public class ForageGoal extends Goal {
	private final SpeciesMob mob;
	private @Nullable BlockPos target;
	private int cooldown;

	public ForageGoal(SpeciesMob mob) {
		this.mob = mob;
		setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		Species species = mob.species();
		if (species == null || species.forage() == null || mob.isBaby() || mob.getTarget() != null) {
			return false;
		}
		if (cooldown > 0) {
			cooldown -= reducedTickDelay(10);
			return false;
		}
		if (mob.getRandom().nextInt(reducedTickDelay(100)) != 0) {
			return false;
		}
		if (!(mob.level() instanceof ServerLevel level) || !level.getGameRules().get(GameRules.MOB_GRIEFING)) {
			return false;
		}
		this.target = findForageBlock(species);
		return this.target != null;
	}

	private @Nullable BlockPos findForageBlock(Species species) {
		int range = species.forage().range();
		BlockPos here = mob.blockPosition();
		for (BlockPos pos : BlockPos.betweenClosed(here.offset(-range, -3, -range), here.offset(range, 3, range))) {
			BlockState state = mob.level().getBlockState(pos);
			if (!species.forage().matches(BuiltInRegistries.BLOCK.getKey(state.getBlock()))) {
				continue;
			}
			// unripe berry bushes are not a meal
			if (state.is(Blocks.SWEET_BERRY_BUSH) && state.getValue(SweetBerryBushBlock.AGE) < 2) {
				continue;
			}
			return pos.immutable();
		}
		return null;
	}

	@Override
	public boolean canContinueToUse() {
		return target != null && mob.getTarget() == null
				&& mob.species() != null && mob.species().forage() != null
				&& mob.species().forage().matches(
						BuiltInRegistries.BLOCK.getKey(mob.level().getBlockState(target).getBlock()));
	}

	@Override
	public void tick() {
		if (target == null) {
			return;
		}
		mob.getLookControl().setLookAt(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5);
		if (target.distToCenterSqr(mob.position()) > 4.5) {
			mob.getNavigation().moveTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5, 1.0);
			return;
		}
		BlockState state = mob.level().getBlockState(target);
		if (state.is(Blocks.SWEET_BERRY_BUSH)) {
			mob.level().setBlockAndUpdate(target, state.setValue(SweetBerryBushBlock.AGE, 1));
		} else {
			mob.level().destroyBlock(target, false, mob);
		}
		mob.onForaged();
		Species species = mob.species();
		cooldown = species != null && species.forage() != null ? species.forage().cooldownTicks() : 1200;
		target = null;
	}

	@Override
	public void stop() {
		target = null;
		mob.getNavigation().stop();
	}
}
