package dev.lilkuzco.menagerie.block;

import dev.lilkuzco.menagerie.Menagerie;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public final class MenagerieBlocks {
	public static final CageTrapBlock CAGE_TRAP = register("cage_trap", 1,
			BlockBehaviour.Properties.of()
					.mapColor(MapColor.METAL)
					.strength(3.0F, 6.0F)
					.sound(SoundType.METAL)
					.noOcclusion());

	public static final CageTrapBlock REINFORCED_CAGE_TRAP = register("reinforced_cage_trap", 2,
			BlockBehaviour.Properties.of()
					.mapColor(MapColor.COLOR_GRAY)
					.strength(4.5F, 8.0F)
					.sound(SoundType.METAL)
					.noOcclusion());

	public static final BlockEntityType<CageTrapBlockEntity> CAGE_TRAP_BLOCK_ENTITY = Registry.register(
			BuiltInRegistries.BLOCK_ENTITY_TYPE, Menagerie.id("cage_trap"),
			new BlockEntityType<>(CageTrapBlockEntity::new, Set.of(CAGE_TRAP, REINFORCED_CAGE_TRAP)));

	private static CageTrapBlock register(String name, int tier, BlockBehaviour.Properties properties) {
		Identifier id = Menagerie.id(name);
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
		CageTrapBlock block = Registry.register(BuiltInRegistries.BLOCK, blockKey,
				new CageTrapBlock(tier, properties.setId(blockKey)));
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
		Registry.register(BuiltInRegistries.ITEM, itemKey,
				new CageTrapItem(block, new Item.Properties().useBlockDescriptionPrefix().setId(itemKey)));
		return block;
	}

	public static void init() {
	}

	private MenagerieBlocks() {
	}
}
