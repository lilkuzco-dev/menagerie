package dev.lilkuzco.menagerie.compat.warfront;

import dev.lilkuzco.menagerie.Menagerie;
import dev.lilkuzco.menagerie.MenagerieTerritories;
import dev.lilkuzco.menagerie.entity.GorillaEntity;
import dev.lilkuzco.menagerie.entity.HippoEntity;
import java.util.List;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Optional Warfront crossover — ACTIVE ONLY when Warfront is loaded, and deliberately
 * free of any compile/classload dependency on it: soldiers are identified purely by
 * their registered entity type id ("warfront:soldier"), everything else is vanilla
 * Mob API. Menagerie builds and runs identically without Warfront.
 *
 * 1. Territory avoidance (soft): idle soldiers inside an active hippo territory or
 *    gorilla troop zone get steered to the nearest point outside — a path-cost nudge,
 *    not a wall. Zones come from {@link MenagerieTerritories}, which any mod can query.
 * 2. Skirmish consequences: combat damage inside a zone aggros the animals at the
 *    attacker (explosion damage flows through the same event) — the jungle fights back.
 */
public final class WarfrontCompat {
	private static final String SOLDIER_ID = "warfront:soldier";

	public static void init() {
		Menagerie.LOGGER.info("Warfront detected — Menagerie territory crossover active");

		// 1. soft no-go pathing for idle soldiers
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			if (server.getTickCount() % 40 != 0) {
				return;
			}
			for (ServerLevel level : server.getAllLevels()) {
				for (MenagerieTerritories.Zone zone : MenagerieTerritories.zones(level)) {
					deflectSoldiers(level, zone);
				}
			}
		});

		// 2. combat inside a zone wakes the neighbors
		ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
			if (entity.level() instanceof ServerLevel level
					&& source.getEntity() instanceof LivingEntity attacker
					&& MenagerieTerritories.isInside(level, entity.position())) {
				aggroAnimalsAt(level, entity.blockPosition(), attacker);
			}
			return true; // observe only, never cancel
		});
	}

	private static void deflectSoldiers(ServerLevel level, MenagerieTerritories.Zone zone) {
		Vec3 center = Vec3.atCenterOf(zone.center());
		AABB box = AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(zone.center()))
				.inflate(zone.radius(), 16, zone.radius());
		for (Mob mob : level.getEntitiesOfClass(Mob.class, box,
				candidate -> SOLDIER_ID.equals(BuiltInRegistries.ENTITY_TYPE.getKey(candidate.getType()).toString()))) {
			if (mob.getTarget() != null || !zone.contains(mob.position())) {
				continue; // fighting soldiers hold their ground; outside ones are fine
			}
			Vec3 away = mob.position().subtract(center);
			Vec3 flat = new Vec3(away.x, 0, away.z);
			if (flat.lengthSqr() < 0.01) {
				flat = new Vec3(1, 0, 0);
			}
			Vec3 exit = center.add(flat.normalize().scale(zone.radius() + 4.0));
			mob.getNavigation().moveTo(exit.x, mob.getY(), exit.z, 1.1);
		}
	}

	private static void aggroAnimalsAt(ServerLevel level, BlockPos pos, LivingEntity attacker) {
		AABB box = AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(pos)).inflate(16, 8, 16);
		for (HippoEntity hippo : level.getEntitiesOfClass(HippoEntity.class, box)) {
			if (!hippo.isBaby() && hippo.getTarget() == null && attacker != hippo) {
				hippo.setTarget(attacker);
			}
		}
		for (GorillaEntity gorilla : level.getEntitiesOfClass(GorillaEntity.class, box)) {
			if (!gorilla.isBaby() && !gorilla.isTame() && gorilla.getTarget() == null && attacker != gorilla) {
				gorilla.setTarget(attacker);
			}
		}
	}

	private WarfrontCompat() {
	}
}
