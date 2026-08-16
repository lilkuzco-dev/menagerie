package dev.lilkuzco.menagerie;

import dev.lilkuzco.menagerie.entity.VultureEntity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * World-level hooks. Currently: carrion detection — a mob death is checked one second
 * later (after loot has hit the ground) for meat drops; if any, every vulture within
 * 48 blocks is pointed at the site.
 */
public final class MenagerieEvents {
	private record PendingCarrion(ResourceKey<Level> dimension, BlockPos pos, int ticksLeft) {
	}

	private static final List<PendingCarrion> PENDING = new ArrayList<>();
	private static final double CARRION_PULL_RANGE = 48.0;

	public static void init() {
		// Field Guide discovery: standing within 8 blocks of a living specimen documents it
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			if (server.getTickCount() % 20 != 0) {
				return;
			}
			dev.lilkuzco.menagerie.guide.MenagerieDiscoveries discoveries =
					dev.lilkuzco.menagerie.guide.MenagerieDiscoveries.get(server);
			for (net.minecraft.server.level.ServerPlayer player : server.getPlayerList().getPlayers()) {
				for (dev.lilkuzco.menagerie.entity.SpeciesMob mob :
						player.level().getEntitiesOfClass(dev.lilkuzco.menagerie.entity.SpeciesMob.class,
								player.getBoundingBox().inflate(8.0))) {
					dev.lilkuzco.menagerie.data.Species species = mob.species();
					if (species == null || !mob.isAlive()) {
						continue;
					}
					String key = species.entityId() + "|" + species.name();
					// rare coats are documented separately: seeing an albino is its own event
					if (mob.hasRareVariant()) {
						String variantKey = key + "|variant:" + mob.getVariantName();
						if (discoveries.discover(player.getUUID(), variantKey)) {
							player.sendSystemMessage(net.minecraft.network.chat.Component.translatable(
									"guide.menagerie.discovered_variant",
									net.minecraft.network.chat.Component.literal(mob.getVariantName())), true);
							player.level().playSound(null, player.blockPosition(),
									net.minecraft.sounds.SoundEvents.PLAYER_LEVELUP,
									net.minecraft.sounds.SoundSource.PLAYERS, 0.5F, 1.6F);
						}
					}
					if (discoveries.discover(player.getUUID(), key)) {
						String speciesName = species.name().substring(0, 1).toUpperCase() + species.name().substring(1);
						net.minecraft.network.chat.Component name = net.minecraft.network.chat.Component
								.literal(speciesName + " ").append(net.minecraft.network.chat.Component.translatable(
										"entity.menagerie." + net.minecraft.resources.Identifier.parse(species.entityId()).getPath()));
						player.sendSystemMessage(net.minecraft.network.chat.Component
								.translatable("guide.menagerie.discovered", name), true);
						player.level().playSound(null, player.blockPosition(),
								net.minecraft.sounds.SoundEvents.EXPERIENCE_ORB_PICKUP,
								net.minecraft.sounds.SoundSource.PLAYERS, 0.6F, 1.4F);
					}
				}
			}
		});

		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			if (entity.level() instanceof ServerLevel level && !(entity instanceof VultureEntity)) {
				synchronized (PENDING) {
					PENDING.add(new PendingCarrion(level.dimension(), entity.blockPosition(), 20));
				}
			}
		});

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			synchronized (PENDING) {
				Iterator<PendingCarrion> it = PENDING.iterator();
				List<PendingCarrion> requeue = new ArrayList<>();
				while (it.hasNext()) {
					PendingCarrion pending = it.next();
					it.remove();
					if (pending.ticksLeft() > 0) {
						requeue.add(new PendingCarrion(pending.dimension(), pending.pos(), pending.ticksLeft() - 1));
						continue;
					}
					ServerLevel level = server.getLevel(pending.dimension());
					if (level != null) {
						notifyVultures(level, pending.pos());
					}
				}
				PENDING.addAll(requeue);
			}
		});
	}

	private static void notifyVultures(ServerLevel level, BlockPos pos) {
		boolean hasMeat = !level.getEntitiesOfClass(ItemEntity.class,
				AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(pos)).inflate(4.0),
				item -> VultureEntity.CARRION.contains(item.getItem().getItem())).isEmpty();
		if (!hasMeat) {
			return;
		}
		for (VultureEntity vulture : level.getEntitiesOfClass(VultureEntity.class,
				AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(pos))
						.inflate(CARRION_PULL_RANGE, 64.0, CARRION_PULL_RANGE))) {
			vulture.notifyCarrion(pos);
		}
	}

	private MenagerieEvents() {
	}
}
