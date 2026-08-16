package dev.lilkuzco.menagerie.guide;

import dev.lilkuzco.menagerie.data.Species;
import dev.lilkuzco.menagerie.data.SpeciesRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

/**
 * The animal dictionary. Using it asks the server for a fresh registry snapshot +
 * this player's discoveries; the client opens the guide screen with that payload.
 * All entry content is generated from live species JSON — zero hand-written text.
 */
public class FieldGuideItem extends Item {
	public FieldGuideItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (player instanceof ServerPlayer serverPlayer) {
			ServerPlayNetworking.send(serverPlayer, buildPayload(serverPlayer));
		}
		return InteractionResult.SUCCESS;
	}

	public static MenagerieNet.GuideS2C buildPayload(ServerPlayer player) {
		MenagerieDiscoveries discoveries = MenagerieDiscoveries.get(player.level().getServer());
		List<MenagerieNet.GuideEntry> entries = new ArrayList<>();
		// stable order: by entity id, then species name
		Map<String, List<Species>> sorted = new TreeMap<>(SpeciesRegistry.all());
		for (Map.Entry<String, List<Species>> byEntity : sorted.entrySet()) {
			for (Species species : byEntity.getValue()) {
				String key = species.entityId() + "|" + species.name();
				boolean discovered = discoveries.has(player.getUUID(), key);
				entries.add(new MenagerieNet.GuideEntry(species.entityId(), species.name(), discovered,
						discovered ? describe(species) : List.of()));
			}
		}
		return new MenagerieNet.GuideS2C(entries);
	}

	/** Entry body straight from the registry — new JSON fields appear with zero Java. */
	private static List<String> describe(Species species) {
		List<String> lines = new ArrayList<>();
		if (!species.guideBlurb().isEmpty()) {
			lines.add("\"" + species.guideBlurb() + "\"");
		}
		lines.add("Biomes: " + String.join(", ", species.biomes()));
		lines.add("Health " + trim(species.health()) + "  Attack " + trim(species.attack())
				+ "  Speed " + species.speed());
		if (!species.tameItem().isEmpty()) {
			lines.add("Tame with: " + shortId(species.tameItem()));
		}
		if (!species.breedItem().isEmpty() && !species.breedItem().equals(species.tameItem())) {
			lines.add("Breed with: " + shortId(species.breedItem()));
		}
		if (species.diet() != null && !species.diet().hunts().isEmpty()) {
			lines.add("Hunts: " + String.join(", ", species.diet().hunts().stream().map(FieldGuideItem::shortId).toList()));
		}
		if (species.territory() != null) {
			lines.add("Territorial: " + species.territory().radius() + " block radius ("
					+ species.territory().anchor() + "-anchored)");
		}
		if (species.venom() != null) {
			lines.add("Venomous: " + shortId(species.venom().effect().toString()) + " "
					+ roman(species.venom().amplifier() + 1) + " for " + species.venom().seconds() + "s");
		}
		if (species.forage() != null && !species.forage().blocks().isEmpty()) {
			lines.add("Forages: " + String.join(", ", species.forage().blocks().stream().map(FieldGuideItem::shortId).toList()));
		}
		if (species.worldgenOnly()) {
			lines.add("Never respawns — protect the ones you find");
		}
		lines.add("Cage: " + (species.cageTier() >= 2 ? "reinforced only" : "standard"));
		return lines;
	}

	private static String shortId(String id) {
		return id.startsWith("#") ? id : Identifier.parse(id).getPath().replace('_', ' ');
	}

	private static String trim(double value) {
		return value == Math.floor(value) ? String.valueOf((int) value) : String.valueOf(value);
	}

	private static String roman(int value) {
		return switch (value) {
			case 1 -> "I";
			case 2 -> "II";
			case 3 -> "III";
			default -> String.valueOf(value);
		};
	}
}
