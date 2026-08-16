package dev.lilkuzco.menagerie;

import dev.lilkuzco.menagerie.block.MenagerieBlocks;
import dev.lilkuzco.menagerie.entity.MenagerieEntities;
import dev.lilkuzco.menagerie.guide.FieldGuideItem;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

public final class MenagerieItems {
	public static final Item FIELD_GUIDE = register("field_guide",
			properties -> new FieldGuideItem(properties.stacksTo(1)));

	/**
	 * One spawn egg per registered animal, in roster order.
	 *
	 * <p>Kept as a LIST rather than nine constants so the creative tab and the tests
	 * enumerate exactly what is registered: an animal added without an egg would show up
	 * as a gap in {@link #SPAWN_EGGS} rather than as a silently missing item.
	 */
	public static final Map<EntityType<?>, Item> SPAWN_EGGS = new LinkedHashMap<>();

	static {
		spawnEgg("gorilla", MenagerieEntities.GORILLA);
		spawnEgg("crocodile", MenagerieEntities.CROCODILE);
		spawnEgg("tortoise", MenagerieEntities.TORTOISE);
		spawnEgg("leopard", MenagerieEntities.LEOPARD);
		spawnEgg("hippo", MenagerieEntities.HIPPO);
		spawnEgg("grizzly", MenagerieEntities.GRIZZLY);
		spawnEgg("vulture", MenagerieEntities.VULTURE);
		spawnEgg("lion", MenagerieEntities.LION);
		spawnEgg("snake", MenagerieEntities.SNAKE);
	}

	/** The mod's own creative tab: every obtainable thing Menagerie adds, in one place. */
	public static final ResourceKey<CreativeModeTab> TAB_KEY =
			ResourceKey.create(Registries.CREATIVE_MODE_TAB, Menagerie.id("menagerie"));

	private static void spawnEgg(String animal, EntityType<?> type) {
		Item egg = register(animal + "_spawn_egg",
				properties -> new SpawnEggItem(properties.spawnEgg(type)));
		SPAWN_EGGS.put(type, egg);
	}

	private static Item register(String name, java.util.function.Function<Item.Properties, Item> factory) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Menagerie.id(name));
		return Registry.register(BuiltInRegistries.ITEM, key, factory.apply(new Item.Properties().setId(key)));
	}

	/** Everything the tab shows, in display order — also what the tests assert against. */
	public static List<Item> tabContents() {
		List<Item> out = new ArrayList<>();
		out.add(FIELD_GUIDE);
		// the cage blocks register their item form inline, so ask the block for it
		out.add(MenagerieBlocks.CAGE_TRAP.asItem());
		out.add(MenagerieBlocks.REINFORCED_CAGE_TRAP.asItem());
		out.addAll(SPAWN_EGGS.values());
		return out;
	}

	public static void init() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_KEY,
				FabricCreativeModeTab.builder()
						.title(Component.translatable("itemGroup.menagerie.menagerie"))
						.icon(() -> new ItemStack(FIELD_GUIDE))
						.displayItems((params, output) -> tabContents().forEach(output::accept))
						.build());
	}

	private MenagerieItems() {
	}
}
