package dev.lilkuzco.menagerie.guide;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.lilkuzco.menagerie.Menagerie;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

/**
 * Per-player species discoveries ("entityId|species" keys), persisted world-side —
 * the Field Guide's memory. Same SavedData pattern as Warfront's state (ours).
 */
public class MenagerieDiscoveries extends SavedData {
	private static final Codec<Map<String, List<String>>> ENTRIES_CODEC =
			Codec.unboundedMap(Codec.STRING, Codec.STRING.listOf());

	private static final Codec<MenagerieDiscoveries> CODEC = RecordCodecBuilder.create(i -> i.group(
			ENTRIES_CODEC.optionalFieldOf("discovered", Map.of()).forGetter(MenagerieDiscoveries::copyEntries)
	).apply(i, MenagerieDiscoveries::new));

	public static final SavedDataType<MenagerieDiscoveries> TYPE = new SavedDataType<>(
			Menagerie.id("discoveries"), MenagerieDiscoveries::new, CODEC,
			DataFixTypes.SAVED_DATA_COMMAND_STORAGE);

	private final Map<String, Set<String>> discovered = new HashMap<>();

	public MenagerieDiscoveries() {
	}

	private MenagerieDiscoveries(Map<String, List<String>> entries) {
		entries.forEach((player, keys) -> discovered.put(player, new HashSet<>(keys)));
	}

	private Map<String, List<String>> copyEntries() {
		Map<String, List<String>> copy = new HashMap<>();
		discovered.forEach((player, keys) -> copy.put(player, List.copyOf(keys)));
		return copy;
	}

	public static MenagerieDiscoveries get(MinecraftServer server) {
		return server.overworld().getDataStorage().computeIfAbsent(TYPE);
	}

	/** @return true if this is a NEW discovery for the player. */
	public boolean discover(UUID player, String speciesKey) {
		boolean added = discovered.computeIfAbsent(player.toString(), k -> new HashSet<>()).add(speciesKey);
		if (added) {
			setDirty();
		}
		return added;
	}

	public boolean has(UUID player, String speciesKey) {
		Set<String> keys = discovered.get(player.toString());
		return keys != null && keys.contains(speciesKey);
	}

	public int count(UUID player) {
		Set<String> keys = discovered.get(player.toString());
		return keys == null ? 0 : keys.size();
	}
}
