package dev.lilkuzco.menagerie;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import dev.lilkuzco.menagerie.data.Species;
import dev.lilkuzco.menagerie.data.SpeciesRegistry;
import dev.lilkuzco.menagerie.entity.GorillaEntity;
import dev.lilkuzco.menagerie.entity.SpeciesMob;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;

/**
 * Headless-verification helpers (same spirit as /vibranium_census):
 *   /menagerie census [radius]  — animals by type|species (+silverback marker) near source
 *   /menagerie troops [radius]  — gorilla troop composition
 *   /menagerie silverback       — force-promote the nearest gorilla (deterministic tests)
 */
public final class MenagerieCommands {
	public static void init() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> dispatcher.register(
				Commands.literal("menagerie")
						.requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
						.then(Commands.literal("census")
								.executes(ctx -> census(ctx.getSource(), 64))
								.then(Commands.argument("radius", IntegerArgumentType.integer(1, 512))
										.executes(ctx -> census(ctx.getSource(),
												IntegerArgumentType.getInteger(ctx, "radius")))))
						.then(Commands.literal("troops")
								.executes(ctx -> troops(ctx.getSource(), 96))
								.then(Commands.argument("radius", IntegerArgumentType.integer(1, 512))
										.executes(ctx -> troops(ctx.getSource(),
												IntegerArgumentType.getInteger(ctx, "radius")))))
						.then(Commands.literal("silverback")
								.executes(ctx -> promoteNearest(ctx.getSource())))));
	}

	private static int census(CommandSourceStack source, int radius) {
		ServerLevel level = source.getLevel();
		AABB box = AABB.unitCubeFromLowerCorner(source.getPosition()).inflate(radius, radius, radius);
		Map<String, Integer> counts = new TreeMap<>();
		for (SpeciesMob mob : level.getEntitiesOfClass(SpeciesMob.class, box)) {
			Species species = mob.species();
			String key = mob.entityId() + "|" + (species == null ? "?" : species.name())
					+ (mob instanceof GorillaEntity gorilla && gorilla.isSilverback() ? "|silverback" : "")
					+ (mob instanceof dev.lilkuzco.menagerie.entity.TortoiseEntity tortoise && tortoise.isShelled() ? "|shelled" : "")
					+ (mob instanceof dev.lilkuzco.menagerie.entity.GrizzlyEntity bear && bear.isBearSleeping() ? "|sleeping" : "")
					+ (mob instanceof dev.lilkuzco.menagerie.entity.VultureEntity vulture && vulture.isFlyingState() ? "|flying" : "")
					+ (mob instanceof dev.lilkuzco.menagerie.entity.SnakeEntity snake && snake.isRattling() ? "|rattling" : "")
					+ (mob.isBaby() ? "|baby" : "")
					+ (mob.getTarget() != null ? "|angry" : "")
					+ (mob.isContent() ? "|content" : "")
					+ (mob.isTame() ? "|tame" : "")
					+ (mob.isOrderedToSit() ? "|sitting" : "")
					+ (mob.isPassenger() ? "|riding" : "");
			counts.merge(key, 1, Integer::sum);
		}
		if (counts.isEmpty()) {
			source.sendSuccess(() -> Component.literal("census r" + radius + ": none"), false);
		}
		counts.forEach((key, count) ->
				source.sendSuccess(() -> Component.literal("census " + key + " x" + count), false));
		int registered = SpeciesRegistry.all().values().stream().mapToInt(java.util.List::size).sum();
		source.sendSuccess(() -> Component.literal("registry: " + registered + " species loaded"), false);
		if (source.getEntity() instanceof net.minecraft.server.level.ServerPlayer player) {
			int documented = dev.lilkuzco.menagerie.guide.MenagerieDiscoveries
					.get(source.getServer()).count(player.getUUID());
			source.sendSuccess(() -> Component.literal("guide: " + documented + "/" + registered
					+ " documented"), false);
		}
		return counts.values().stream().mapToInt(Integer::intValue).sum();
	}

	private static int troops(CommandSourceStack source, int radius) {
		ServerLevel level = source.getLevel();
		AABB box = AABB.unitCubeFromLowerCorner(source.getPosition()).inflate(radius, radius, radius);
		Map<UUID, int[]> troops = new HashMap<>(); // [members, silverbacks, babies]
		for (GorillaEntity gorilla : level.getEntitiesOfClass(GorillaEntity.class, box)) {
			UUID troop = gorilla.getTroopId();
			int[] row = troops.computeIfAbsent(troop == null ? new UUID(0, 0) : troop, k -> new int[3]);
			row[0]++;
			if (gorilla.isSilverback()) {
				row[1]++;
			}
			if (gorilla.isBaby()) {
				row[2]++;
			}
		}
		if (troops.isEmpty()) {
			source.sendSuccess(() -> Component.literal("troops r" + radius + ": none"), false);
		}
		troops.forEach((id, row) -> source.sendSuccess(() -> Component.literal(
				"troop " + id.toString().substring(0, 8) + ": members=" + row[0]
						+ " silverbacks=" + row[1] + " babies=" + row[2]), false));
		return troops.size();
	}

	private static int promoteNearest(CommandSourceStack source) {
		ServerLevel level = source.getLevel();
		AABB box = AABB.unitCubeFromLowerCorner(source.getPosition()).inflate(32, 32, 32);
		GorillaEntity nearest = null;
		double best = Double.MAX_VALUE;
		for (GorillaEntity gorilla : level.getEntitiesOfClass(GorillaEntity.class, box)) {
			double dist = source.getPosition().distanceToSqr(gorilla.position());
			if (dist < best) {
				nearest = gorilla;
				best = dist;
			}
		}
		if (nearest == null) {
			source.sendFailure(Component.literal("no gorilla within 32 blocks"));
			return 0;
		}
		final GorillaEntity promoted = nearest;
		promoted.setSilverback(true);
		source.sendSuccess(() -> Component.literal("promoted " + promoted.getUUID()), false);
		return 1;
	}

	private MenagerieCommands() {
	}
}
