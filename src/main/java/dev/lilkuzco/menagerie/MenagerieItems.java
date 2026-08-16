package dev.lilkuzco.menagerie;

import dev.lilkuzco.menagerie.guide.FieldGuideItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public final class MenagerieItems {
	public static final Item FIELD_GUIDE = register("field_guide",
			properties -> new FieldGuideItem(properties.stacksTo(1)));

	private static Item register(String name, java.util.function.Function<Item.Properties, Item> factory) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Menagerie.id(name));
		return Registry.register(BuiltInRegistries.ITEM, key, factory.apply(new Item.Properties().setId(key)));
	}

	public static void init() {
	}

	private MenagerieItems() {
	}
}
