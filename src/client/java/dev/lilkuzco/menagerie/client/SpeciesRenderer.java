package dev.lilkuzco.menagerie.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.lilkuzco.menagerie.entity.SpeciesMob;
import java.util.function.BiConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

/**
 * One renderer for every Menagerie animal: the species JSON supplies the texture, a
 * per-animal extractor copies animation state, and the baby model is the adult mesh
 * run through BabyModelTransform (adult/baby swap like vanilla AgeableMobRenderer).
 */
public class SpeciesRenderer<T extends SpeciesMob> extends MobRenderer<T, MenagerieRenderState, EntityModel<MenagerieRenderState>> {
	private final EntityModel<MenagerieRenderState> adultModel;
	private final EntityModel<MenagerieRenderState> babyModel;
	private final BiConsumer<T, MenagerieRenderState> extraExtract;

	public SpeciesRenderer(EntityRendererProvider.Context context,
			EntityModel<MenagerieRenderState> adultModel, EntityModel<MenagerieRenderState> babyModel,
			float shadow, BiConsumer<T, MenagerieRenderState> extraExtract) {
		super(context, adultModel, shadow);
		this.adultModel = adultModel;
		this.babyModel = babyModel;
		this.extraExtract = extraExtract;
	}

	@Override
	public MenagerieRenderState createRenderState() {
		return new MenagerieRenderState();
	}

	@Override
	public Identifier getTextureLocation(MenagerieRenderState state) {
		return state.texture;
	}

	@Override
	public void extractRenderState(T entity, MenagerieRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.texture = entity.texture();
		state.sittingPose = entity.isInSittingPose();
		extraExtract.accept(entity, state);
	}

	@Override
	public void submit(MenagerieRenderState state, PoseStack poseStack,
			SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		this.model = state.isBaby ? this.babyModel : this.adultModel;
		super.submit(state, poseStack, submitNodeCollector, camera);
	}
}
