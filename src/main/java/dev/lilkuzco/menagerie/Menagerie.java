package dev.lilkuzco.menagerie;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Menagerie implements ModInitializer {
	public static final String MOD_ID = "menagerie";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		dev.lilkuzco.menagerie.data.SpeciesRegistry.init();
		MenagerieSounds.init();
		dev.lilkuzco.menagerie.entity.MenagerieEntities.init();
		MenagerieSpawns.init();
		MenagerieCommands.init();
		MenagerieEvents.init();
		LOGGER.info("Menagerie initialized");
	}
}
