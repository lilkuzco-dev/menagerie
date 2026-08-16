package dev.lilkuzco.menagerie;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import dev.lilkuzco.menagerie.data.Species;
import dev.lilkuzco.menagerie.data.SpeciesRegistry;
import dev.lilkuzco.menagerie.entity.GorillaEntity;
import dev.lilkuzco.menagerie.entity.SpeciesMob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.IdentifierArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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
						.then(Commands.literal("cull")
								.then(Commands.argument("entity", IdentifierArgument.id())
										.then(Commands.argument("radius", IntegerArgumentType.integer(1, 512))
												.then(Commands.argument("keep", IntegerArgumentType.integer(0, 512))
														.executes(ctx -> cull(ctx.getSource(),
																IdentifierArgument.getId(ctx, "entity"),
																IntegerArgumentType.getInteger(ctx, "radius"),
																IntegerArgumentType.getInteger(ctx, "keep")))))))
						.then(Commands.literal("spawntest")
								.then(Commands.argument("entity", IdentifierArgument.id())
										.then(Commands.argument("trials", IntegerArgumentType.integer(1, 2000))
												.executes(ctx -> spawnTest(ctx.getSource(),
														IdentifierArgument.getId(ctx, "entity"),
														IntegerArgumentType.getInteger(ctx, "trials"))))))
						.then(Commands.literal("rarity")
								.executes(ctx -> rarity(ctx.getSource())))
						.then(Commands.literal("silverback")
								.executes(ctx -> promoteNearest(ctx.getSource())))));
	}

	private static int census(CommandSourceStack source, int radius) {
		ServerLevel level = source.getLevel();
		AABB box = AABB.unitCubeFromLowerCorner(source.getPosition()).inflate(radius, radius, radius);
		Map<String, Integer> counts = new TreeMap<>();
		for (SpeciesMob mob : level.getEntitiesOfClass(SpeciesMob.class, box)) {
			Species species = mob.species();
			String texturePath = mob.texture().getPath();
			String fur = texturePath.substring(texturePath.lastIndexOf('/') + 1).replace(".png", "");
			String key = mob.entityId() + "|" + (species == null ? "?" : species.name())
					+ (mob instanceof GorillaEntity ? "|fur=" + fur : "")
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

	/**
	 * Thin out an already-overrun area (the pre-cap gorilla jungles). Removes excess
	 * animals of one type beyond {@code keep}, farthest first, and NEVER touches a tamed
	 * or named animal — someone's ranch is not overcrowding.
	 */
	private static int cull(CommandSourceStack source, Identifier entityId, int radius, int keep) {
		ServerLevel level = source.getLevel();
		Vec3 origin = source.getPosition();
		AABB box = AABB.unitCubeFromLowerCorner(origin).inflate(radius, radius, radius);
		List<SpeciesMob> candidates = new ArrayList<>();
		int protectedCount = 0;
		for (SpeciesMob mob : level.getEntitiesOfClass(SpeciesMob.class, box)) {
			if (!mob.entityId().equals(entityId.toString())) {
				continue;
			}
			if (mob.isTame() || mob.hasCustomName()) {
				protectedCount++;
				continue;
			}
			candidates.add(mob);
		}
		// farthest first: culling thins the edges and leaves what is around you intact
		candidates.sort(java.util.Comparator.comparingDouble(
				(SpeciesMob mob) -> mob.position().distanceToSqr(origin)).reversed());
		int removed = 0;
		for (SpeciesMob mob : candidates) {
			if (candidates.size() - removed <= keep) {
				break;
			}
			mob.discard();
			removed++;
		}
		final int culled = removed;
		final int kept = candidates.size() - removed;
		final int spared = protectedCount;
		source.sendSuccess(() -> Component.literal("culled " + culled + " " + entityId.getPath()
				+ " (kept " + kept + " wild, spared " + spared + " tamed/named)"), true);
		return culled;
	}

	/**
	 * Runs the REAL natural-spawn predicate N times at the command position and reports
	 * how many attempts would have been allowed. This is how the nearby-species cap gets
	 * verified without waiting on the spawner: same code path, deterministic sample.
	 */
	private static int spawnTest(CommandSourceStack source, Identifier entityId, int trials) {
		ServerLevel level = source.getLevel();
		BlockPos pos = BlockPos.containing(source.getPosition());
		EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getValue(entityId);
		if (type == null || !BuiltInRegistries.ENTITY_TYPE.getKey(type).equals(entityId)
				|| !(type.create(level, EntitySpawnReason.COMMAND) instanceof SpeciesMob probe)) {
			source.sendFailure(Component.literal("not a menagerie animal: " + entityId));
			return 0;
		}
		probe.discard();
		@SuppressWarnings("unchecked")
		EntityType<? extends net.minecraft.world.entity.Mob> mobType =
				(EntityType<? extends net.minecraft.world.entity.Mob>) type;
		net.minecraft.util.RandomSource random = net.minecraft.util.RandomSource.create();
		int allowed = 0;
		for (int i = 0; i < trials; i++) {
			if (SpeciesMob.checkSpeciesSpawnRules(mobType, level, EntitySpawnReason.NATURAL, pos, random)) {
				allowed++;
			}
		}
		int nearby = level.getEntities(mobType,
				AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(pos))
						.inflate(dev.lilkuzco.menagerie.data.RarityConfig.NEARBY_RADIUS, 64,
								dev.lilkuzco.menagerie.data.RarityConfig.NEARBY_RADIUS),
				e -> true).size();
		final int pass = allowed;
		source.sendSuccess(() -> Component.literal(String.format(
				"spawntest %s: %d/%d attempts allowed (%.0f%%), %d already within %d blocks",
				entityId.getPath(), pass, trials, 100.0 * pass / trials, nearby,
				dev.lilkuzco.menagerie.data.RarityConfig.NEARBY_RADIUS)), false);
		return pass;
	}

	/** Resolved spawn numbers per species — makes a /reload retune observable. */
	private static int rarity(CommandSourceStack source) {
		Map<String, List<Species>> all = new TreeMap<>(SpeciesRegistry.all());
		int count = 0;
		for (Map.Entry<String, List<Species>> entry : all.entrySet()) {
			for (Species species : entry.getValue()) {
				count++;
				source.sendSuccess(() -> Component.literal(String.format(
						"%s|%s tier=%s weight=%d group=%d-%d cap=%d attempt=%.2f",
						Identifier.parse(species.entityId()).getPath(), species.name(),
						species.rarity().isEmpty() ? "(default)" : species.rarity(),
						species.effectiveWeight(), species.effectiveGroupMin(), species.effectiveGroupMax(),
						species.nearbyCap(), species.attemptChance())), false);
			}
		}
		return count;
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
