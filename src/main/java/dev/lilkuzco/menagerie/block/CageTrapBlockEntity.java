package dev.lilkuzco.menagerie.block;

import dev.lilkuzco.menagerie.Menagerie;
import dev.lilkuzco.menagerie.data.Species;
import dev.lilkuzco.menagerie.entity.SpeciesMob;
import dev.lilkuzco.menagerie.entity.VultureEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Holds the bait and, once sprung, the captured animal's FULL entity NBT (species,
 * health, name, owner — everything survives the round trip). A wild calm animal whose
 * bait matches is lured to the cage and captured within a block. If the species'
 * cage_tier exceeds the block tier, it breaks free after ~3s and wrecks the cage.
 */
public class CageTrapBlockEntity extends BlockEntity {
	private ItemStack bait = ItemStack.EMPTY;
	private @Nullable CompoundTag captured;
	private int breakoutTicks = -1; // >=0 counts down to a breakout (wrong tier)

	public CageTrapBlockEntity(BlockPos pos, BlockState state) {
		super(MenagerieBlocks.CAGE_TRAP_BLOCK_ENTITY, pos, state);
	}

	public void setBait(ItemStack stack) {
		this.bait = stack;
		setChanged();
	}

	public boolean hasCaptured() {
		return captured != null;
	}

	public @Nullable CompoundTag capturedTag() {
		return captured;
	}

	private int blockTier() {
		return getBlockState().getBlock() instanceof CageTrapBlock cage ? cage.tier : 1;
	}

	// ---------- luring + capture ----------
	public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, CageTrapBlockEntity cage) {
		if (cage.captured != null) {
			if (cage.breakoutTicks > 0) {
				cage.breakoutTicks--;
				if (cage.breakoutTicks == 0) {
					cage.breakout(level, pos);
				}
			}
			return;
		}
		if (cage.bait.isEmpty() || level.getGameTime() % 20 != 0) {
			return;
		}
		SpeciesMob candidate = cage.findLuredAnimal(level, pos);
		if (candidate == null) {
			return;
		}
		// bounding-box check so wide animals (hippo) spring the trap from adjacency
		if (candidate.getBoundingBox().inflate(1.1).contains(Vec3.atCenterOf(pos))) {
			cage.capture(level, pos, state, candidate);
		} else {
			candidate.getNavigation().moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 1.0);
		}
	}

	private @Nullable SpeciesMob findLuredAnimal(ServerLevel level, BlockPos pos) {
		SpeciesMob best = null;
		for (SpeciesMob mob : level.getEntitiesOfClass(SpeciesMob.class,
				AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(pos)).inflate(8.0, 4.0, 8.0))) {
			// calm only: no aggroed hippos. Tamed pets aren't LURED from a distance,
			// but one led beside the cage is captured (owner transport, spec test 3)
			if (mob.getTarget() != null || mob.isBaby() && mob.isPassenger()) {
				continue;
			}
			if (mob.isTame() && !mob.getBoundingBox().inflate(1.1).contains(Vec3.atCenterOf(pos))) {
				continue;
			}
			if (!baitMatches(mob)) {
				continue;
			}
			if (best == null || mob.distanceToSqr(Vec3.atCenterOf(pos)) < best.distanceToSqr(Vec3.atCenterOf(pos))) {
				best = mob;
			}
		}
		return best;
	}

	private boolean baitMatches(SpeciesMob mob) {
		Species species = mob.species();
		if (species == null) {
			return false;
		}
		Identifier baitId = BuiltInRegistries.ITEM.getKey(bait.getItem());
		String baitStr = baitId.toString();
		if (baitStr.equals(species.tameItem()) || baitStr.equals(species.breedItem())) {
			return true;
		}
		// breeding items are bait too. This is not optional politeness: species that moved
		// their breed_item into the "breeding" block have an empty breedItem(), and without
		// this a hippo would have no bait item in the world at all.
		if (species.breeding() != null && species.breeding().items().contains(baitStr)) {
			return true;
		}
		if (species.diet() == null) {
			return false;
		}
		// a scavenger is lured by carrion, using the same table it strips off the ground —
		// without this the vulture has a cage_tier but no bait that exists, so its cage
		// could never be sprung
		if (species.diet().scavenges() && VultureEntity.CARRION.contains(bait.getItem())) {
			return true;
		}
		// diet lure: hunted entity ids map onto their item form (minecraft:salmon etc.)
		return species.diet().hunts().contains(baitStr);
	}

	private void capture(ServerLevel level, BlockPos pos, BlockState state, SpeciesMob mob) {
		TagValueOutput output = TagValueOutput.createWithContext(
				ProblemReporter.DISCARDING, level.registryAccess());
		if (!mob.save(output)) {
			return;
		}
		this.captured = output.buildResult();
		this.bait = ItemStack.EMPTY; // bait is consumed by the catch
		Species species = mob.species();
		int needed = species != null ? species.cageTier() : 1;
		mob.discard();
		level.setBlockAndUpdate(pos, state.setValue(CageTrapBlock.CLOSED, true));
		level.playSound(null, pos, SoundEvents.IRON_DOOR_CLOSE, SoundSource.BLOCKS, 1.0F, 0.8F);
		if (needed > blockTier()) {
			this.breakoutTicks = 60; // wrong tier: it will not hold
		}
		setChanged();
	}

	private void breakout(ServerLevel level, BlockPos pos) {
		spawnCaptured(level, pos, null);
		level.playSound(null, pos, SoundEvents.IRON_GOLEM_DAMAGE, SoundSource.BLOCKS, 1.4F, 0.7F);
		level.destroyBlock(pos, false); // the cage is wrecked, drops nothing
	}

	public void release(ServerLevel level, BlockPos pos, @Nullable Player player) {
		if (captured == null) {
			return;
		}
		spawnCaptured(level, pos, player);
		level.playSound(null, pos, SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);
		level.setBlockAndUpdate(pos, getBlockState().setValue(CageTrapBlock.CLOSED, false));
	}

	private void spawnCaptured(ServerLevel level, BlockPos pos, @Nullable Player player) {
		if (captured == null) {
			return;
		}
		CompoundTag tag = captured;
		this.captured = null;
		this.breakoutTicks = -1;
		setChanged();
		Entity entity = EntityType.loadEntityRecursive(tag, level,
				new net.minecraft.world.entity.EntitySpawnRequest(EntitySpawnReason.LOAD, false), e -> {
			// place just outside the cage, facing whoever opened it
			BlockPos front = player != null
					? pos.relative(net.minecraft.core.Direction.getApproximateNearest(
							player.position().subtract(Vec3.atCenterOf(pos)).with(net.minecraft.core.Direction.Axis.Y, 0)))
					: pos.above();
			e.snapTo(front.getX() + 0.5, front.getY(), front.getZ() + 0.5, e.getYRot(), e.getXRot());
			return e;
		});
		if (entity != null) {
			level.addFreshEntity(entity);
		} else {
			Menagerie.LOGGER.warn("Cage at {} failed to restore its captured animal", pos);
		}
	}

	// ---------- persistence (also rides the item via BLOCK_ENTITY_DATA) ----------
	/**
	 * Export our NBT as the BLOCK_ENTITY_DATA component so the loot table's
	 * copy_components carries the occupant onto the dropped item (copy_components
	 * only sees explicit components, not raw saveAdditional data).
	 */
	@Override
	protected void collectImplicitComponents(net.minecraft.core.component.DataComponentMap.Builder components) {
		super.collectImplicitComponents(components);
		if (captured == null && bait.isEmpty()) {
			return;
		}
		TagValueOutput output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING,
				level != null ? level.registryAccess() : net.minecraft.core.RegistryAccess.EMPTY);
		saveAdditional(output);
		components.set(net.minecraft.core.component.DataComponents.BLOCK_ENTITY_DATA,
				net.minecraft.world.item.component.TypedEntityData.of(getType(), output.buildResult()));
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		if (!bait.isEmpty()) {
			output.store("menagerie_bait", ItemStack.CODEC, bait);
		}
		if (captured != null) {
			output.store("menagerie_captured", CompoundTag.CODEC, captured);
		}
		output.putInt("menagerie_breakout", breakoutTicks);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		bait = input.read("menagerie_bait", ItemStack.CODEC).orElse(ItemStack.EMPTY);
		captured = input.read("menagerie_captured", CompoundTag.CODEC).orElse(null);
		breakoutTicks = input.getIntOr("menagerie_breakout", -1);
	}
}
