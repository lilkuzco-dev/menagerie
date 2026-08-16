package dev.lilkuzco.menagerie.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

/**
 * The capture block, both tiers (tier field: 1 standard, 2 reinforced). Bait it by
 * right-clicking with a species' tame/breed/diet item; a calm wild Menagerie animal
 * that wanders within a block is captured into the block entity's NBT. Break the
 * closed cage to carry the animal (BLOCK_ENTITY_DATA rides the item); right-click a
 * closed cage to release. Wrong tier = the animal breaks out and wrecks the cage.
 */
public class CageTrapBlock extends BaseEntityBlock {
	public static final MapCodec<CageTrapBlock> CODEC = simpleCodec(properties -> new CageTrapBlock(1, properties));
	public static final BooleanProperty CLOSED = BooleanProperty.create("closed");

	public final int tier;

	public CageTrapBlock(int tier, Properties properties) {
		super(properties);
		this.tier = tier;
		registerDefaultState(stateDefinition.any().setValue(CLOSED, false));
	}

	@Override
	protected MapCodec<? extends CageTrapBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CLOSED);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CageTrapBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		return level instanceof ServerLevel
				? createTickerHelper(type, MenagerieBlocks.CAGE_TRAP_BLOCK_ENTITY,
						(innerLevel, pos, innerState, entity) ->
								CageTrapBlockEntity.serverTick((ServerLevel) innerLevel, pos, innerState, entity))
				: null;
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
			Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (state.getValue(CLOSED) || stack.isEmpty()) {
			return InteractionResult.TRY_WITH_EMPTY_HAND;
		}
		if (!(level.getBlockEntity(pos) instanceof CageTrapBlockEntity cage)) {
			return InteractionResult.PASS;
		}
		// baiting: one item goes into the trap
		if (!level.isClientSide()) {
			cage.setBait(stack.copyWithCount(1));
			if (!player.hasInfiniteMaterials()) {
				stack.shrink(1);
			}
			level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, net.minecraft.sounds.SoundSource.BLOCKS,
					1.0F, 1.0F);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
			BlockHitResult hitResult) {
		if (!(level.getBlockEntity(pos) instanceof CageTrapBlockEntity cage)) {
			return InteractionResult.PASS;
		}
		if (state.getValue(CLOSED)) {
			// release the occupant
			if (level instanceof ServerLevel serverLevel) {
				cage.release(serverLevel, pos, player);
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
