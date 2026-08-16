package dev.lilkuzco.menagerie.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.lilkuzco.menagerie.Menagerie;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

/**
 * The silver saddle, drawn as a translucent pass of the whole model over whatever fur
 * this gorilla happens to wear (concept from Animal Garden - Western Gorilla, public
 * domain). Overlaying beats swapping textures: one silverback skin composes with all
 * five fur colours instead of needing a silverback copy of each.
 */
public class SilverbackLayer extends RenderLayer<MenagerieRenderState, EntityModel<MenagerieRenderState>> {
	private static final Identifier SILVERBACK = Menagerie.id("textures/entity/gorilla/silverback.png");
	private static final int HALF_ALPHA = 0x80FFFFFF;

	public SilverbackLayer(RenderLayerParent<MenagerieRenderState, EntityModel<MenagerieRenderState>> renderer) {
		super(renderer);
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords,
			MenagerieRenderState state, float yRot, float xRot) {
		if (!state.silverback || state.isBaby || state.isInvisible) {
			return;
		}
		submitNodeCollector.order(1).submitModel(
				this.getParentModel(),
				state,
				poseStack,
				RenderTypes.entityTranslucent(SILVERBACK),
				lightCoords,
				LivingEntityRenderer.getOverlayCoords(state, 0.0F),
				HALF_ALPHA,
				null,
				state.outlineColor,
				null);
	}
}
