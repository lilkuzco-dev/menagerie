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
 * The lion's eyes are their own emissive pass over the body skin (concept and textures
 * from Animal Garden - Lion, public domain), which is what lets the eyelids animate as
 * geometry while the eyes keep catching light. Left and right are separate textures so
 * a lion can wink.
 */
public class LionEyeLayer extends RenderLayer<MenagerieRenderState, EntityModel<MenagerieRenderState>> {
	private static final int VARIANTS = 4;
	private static final Identifier[] LEFT = new Identifier[VARIANTS];
	private static final Identifier[] RIGHT = new Identifier[VARIANTS];

	static {
		for (int i = 0; i < VARIANTS; i++) {
			LEFT[i] = Menagerie.id("textures/entity/lion/eye/lefteye_" + i + ".png");
			RIGHT[i] = Menagerie.id("textures/entity/lion/eye/righteye_" + i + ".png");
		}
	}

	public LionEyeLayer(RenderLayerParent<MenagerieRenderState, EntityModel<MenagerieRenderState>> renderer) {
		super(renderer);
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords,
			MenagerieRenderState state, float yRot, float xRot) {
		if (state.isInvisible) {
			return;
		}
		int overlay = LivingEntityRenderer.getOverlayCoords(state, 0.0F);
		submit(poseStack, submitNodeCollector, lightCoords, state, overlay, LEFT[clamp(state.lionLeftEye)]);
		submit(poseStack, submitNodeCollector, lightCoords, state, overlay, RIGHT[clamp(state.lionRightEye)]);
	}

	private void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
			MenagerieRenderState state, int overlay, Identifier texture) {
		collector.order(1).submitModel(this.getParentModel(), state, poseStack,
				RenderTypes.eyes(texture), lightCoords, overlay, -1, null, state.outlineColor, null);
	}

	private static int clamp(int variant) {
		return Math.floorMod(variant, VARIANTS);
	}
}
