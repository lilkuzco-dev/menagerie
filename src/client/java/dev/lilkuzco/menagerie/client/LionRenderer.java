package dev.lilkuzco.menagerie.client;

import dev.lilkuzco.menagerie.client.model.LionModel;
import dev.lilkuzco.menagerie.entity.LionEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/** Lion: eye layer on top of the body skin, plus the pride-male mane flag. */
public class LionRenderer extends SpeciesRenderer<LionEntity> {
	public LionRenderer(EntityRendererProvider.Context context) {
		super(context,
				new LionModel(context.bakeLayer(MenagerieClient.LION)),
				new LionModel(context.bakeLayer(MenagerieClient.LION_BABY)),
				0.8F,
				(entity, state) -> {
					// the mane doubles as the pride-leader tell, so it reuses the same flag
					state.silverback = entity.isManed();
					int seed = Math.floorMod(entity.getUUID().hashCode(), 4);
					state.lionLeftEye = seed;
					state.lionRightEye = seed;
					state.lionBreathing.copyFrom(entity.breathingAnimationState);
					state.lionTail.copyFrom(entity.tailAnimationState);
					state.lionEar.copyFrom(entity.earAnimationState);
					state.lionWink.copyFrom(entity.winkAnimationState);
					state.lionSniff.copyFrom(entity.sniffAnimationState);
					state.lionYawn.copyFrom(entity.yawnAnimationState);
					state.lionBite.copyFrom(entity.biteAnimationState);
					state.lionRoar.copyFrom(entity.roarAnimationState);
					state.lionSleepStart.copyFrom(entity.sleepStartAnimationState);
					state.lionSleepLoop.copyFrom(entity.sleepLoopAnimationState);
					state.lionSleepEnd.copyFrom(entity.sleepEndAnimationState);
				});
		addLayer(new LionEyeLayer(this));
	}
}
