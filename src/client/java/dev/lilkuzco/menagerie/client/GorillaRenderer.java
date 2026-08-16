package dev.lilkuzco.menagerie.client;

import dev.lilkuzco.menagerie.client.model.GorillaModel;
import dev.lilkuzco.menagerie.entity.GorillaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * The gorilla needs two things the shared SpeciesRenderer does not: the silverback
 * overlay layer, and a pile of keyframe animation states copied off the entity.
 */
public class GorillaRenderer extends SpeciesRenderer<GorillaEntity> {
	public GorillaRenderer(EntityRendererProvider.Context context) {
		super(context,
				new GorillaModel(context.bakeLayer(MenagerieClient.GORILLA)),
				new GorillaModel(context.bakeLayer(MenagerieClient.GORILLA_BABY)),
				0.7F,
				(entity, state) -> {
					state.silverback = entity.isSilverback();
					state.gorillaBreathing.copyFrom(entity.breathingAnimationState);
					state.gorillaChestPump.copyFrom(entity.chestPumpAnimationState);
					state.gorillaEat.copyFrom(entity.eatAnimationState);
					state.gorillaPunch.copyFrom(entity.punchAnimationState);
					state.gorillaWink.copyFrom(entity.winkAnimationState);
					state.gorillaSniff.copyFrom(entity.sniffAnimationState);
					state.gorillaSound.copyFrom(entity.soundAnimationState);
					state.gorillaSitStart.copyFrom(entity.sitStartAnimationState);
					state.gorillaSitLoop.copyFrom(entity.sitLoopAnimationState);
					state.gorillaSitEnd.copyFrom(entity.sitEndAnimationState);
				});
		addLayer(new SilverbackLayer(this));
	}
}
