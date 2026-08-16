package dev.lilkuzco.menagerie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Public territory API (the Warfront-crossover hook, usable by any mod): active hippo
 * territories and gorilla troop centers, registered by the animals themselves each few
 * seconds (entries expire when the animal stops refreshing — despawn, chunk unload).
 * Query with {@link #isInside}; listen with {@link #TERRITORY_ACTIVE}.
 */
public final class MenagerieTerritories {
	/** A live no-go zone: center + radius, refreshed by its owner animal. */
	public record Zone(BlockPos center, int radius, String kind) {
		public boolean contains(Vec3 pos) {
			double dx = pos.x - (center.getX() + 0.5);
			double dz = pos.z - (center.getZ() + 0.5);
			return dx * dx + dz * dz <= (double) radius * radius && Math.abs(pos.y - center.getY()) <= 16;
		}
	}

	/** Fired (server side) whenever a zone is registered or refreshed. */
	@FunctionalInterface
	public interface TerritoryListener {
		void onTerritoryActive(ServerLevel level, Zone zone);
	}

	public static final Event<TerritoryListener> TERRITORY_ACTIVE =
			EventFactory.createArrayBacked(TerritoryListener.class,
					listeners -> (level, zone) -> {
						for (TerritoryListener listener : listeners) {
							listener.onTerritoryActive(level, zone);
						}
					});

	private record Entry(Zone zone, long expiresAtGameTime) {
	}

	private static final Map<ResourceKey<Level>, List<Entry>> ZONES = new ConcurrentHashMap<>();

	/** Animals call this every few seconds; the entry lives ~10s past the last refresh. */
	public static void refresh(ServerLevel level, BlockPos center, int radius, String kind) {
		List<Entry> entries = ZONES.computeIfAbsent(level.dimension(), k -> new ArrayList<>());
		Zone zone = new Zone(center.immutable(), radius, kind);
		synchronized (entries) {
			entries.removeIf(entry -> entry.zone().center().equals(zone.center())
					|| entry.expiresAtGameTime() < level.getGameTime());
			entries.add(new Entry(zone, level.getGameTime() + 200));
		}
		TERRITORY_ACTIVE.invoker().onTerritoryActive(level, zone);
	}

	public static boolean isInside(ServerLevel level, Vec3 pos) {
		return zoneAt(level, pos) != null;
	}

	public static @Nullable Zone zoneAt(ServerLevel level, Vec3 pos) {
		List<Entry> entries = ZONES.get(level.dimension());
		if (entries == null) {
			return null;
		}
		synchronized (entries) {
			Iterator<Entry> it = entries.iterator();
			while (it.hasNext()) {
				Entry entry = it.next();
				if (entry.expiresAtGameTime() < level.getGameTime()) {
					it.remove();
					continue;
				}
				if (entry.zone().contains(pos)) {
					return entry.zone();
				}
			}
		}
		return null;
	}

	public static List<Zone> zones(ServerLevel level) {
		List<Entry> entries = ZONES.get(level.dimension());
		if (entries == null) {
			return List.of();
		}
		synchronized (entries) {
			List<Zone> alive = new ArrayList<>();
			for (Entry entry : entries) {
				if (entry.expiresAtGameTime() >= level.getGameTime()) {
					alive.add(entry.zone());
				}
			}
			return alive;
		}
	}

	private MenagerieTerritories() {
	}
}
