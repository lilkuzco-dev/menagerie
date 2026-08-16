package dev.lilkuzco.menagerie.block;

import java.util.function.Consumer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

/**
 * The cage as an item. When it carries a captured animal (BLOCK_ENTITY_DATA from the
 * broken closed cage), the tooltip names the occupant — species + custom name.
 */
public class CageTrapItem extends BlockItem {
	public CageTrapItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display,
			Consumer<Component> builder, TooltipFlag flag) {
		CompoundTag captured = capturedTag(stack);
		if (captured == null) {
			builder.accept(Component.translatable("item.menagerie.cage_trap.empty")
					.withStyle(style -> style.withColor(0xFF9098A0)));
			return;
		}
		String entityId = captured.getStringOr("id", "");
		String species = captured.getStringOr("menagerie_species", "");
		Component animal = entityId.isEmpty()
				? Component.literal("?")
				: Component.translatable("entity." + Identifier.parse(entityId).getNamespace()
						+ "." + Identifier.parse(entityId).getPath());
		String speciesLabel = species.isEmpty() ? "" : capitalize(species) + " ";
		builder.accept(Component.translatable("item.menagerie.cage_trap.occupied",
				Component.literal(speciesLabel).append(animal))
				.withStyle(style -> style.withColor(0xFFE8C36A)));
		captured.getString("CustomName").ifPresent(name ->
				builder.accept(Component.literal("\"" + name.replaceAll("[\\[\\]{}\"]", "") + "\"")
						.withStyle(style -> style.withColor(0xFFB0B0B8))));
	}

	public static @Nullable CompoundTag capturedTag(ItemStack stack) {
		var data = stack.get(DataComponents.BLOCK_ENTITY_DATA);
		if (data == null) {
			return null;
		}
		return data.getUnsafe().getCompound("menagerie_captured").orElse(null);
	}

	private static String capitalize(String value) {
		return value.isEmpty() ? value : Character.toUpperCase(value.charAt(0)) + value.substring(1);
	}
}
