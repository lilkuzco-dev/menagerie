package dev.lilkuzco.menagerie.client.model;

import dev.lilkuzco.menagerie.client.MenagerieRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Bulky bear: paw-swipe fishing animation on the right foreleg, flat-out sleep pose.
 * UV map mirrored in tools/gen-textures.js.
 */
public class GrizzlyModel extends EntityModel<MenagerieRenderState> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;

	public GrizzlyModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.rightFrontLeg = root.getChild("right_front_leg");
		this.leftFrontLeg = root.getChild("left_front_leg");
		this.rightHindLeg = root.getChild("right_hind_leg");
		this.leftHindLeg = root.getChild("left_hind_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild("body", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-6.0F, -5.0F, -8.0F, 12.0F, 11.0F, 16.0F),
				PartPose.offset(0.0F, 12.0F, 2.0F));
		root.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(0, 27).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 7.0F, 6.0F)
				.texOffs(28, 27).addBox(-2.0F, -1.0F, -9.0F, 4.0F, 3.0F, 3.0F)
				.texOffs(42, 27).addBox(-4.0F, -6.0F, -3.0F, 2.0F, 2.0F, 1.0F)
				.texOffs(42, 27).addBox(2.0F, -6.0F, -3.0F, 2.0F, 2.0F, 1.0F),
				PartPose.offset(0.0F, 10.0F, -7.0F));
		CubeListBuilder leg = CubeListBuilder.create()
				.texOffs(0, 40).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 6.0F, 5.0F);
		root.addOrReplaceChild("right_front_leg", leg, PartPose.offset(-3.5F, 18.0F, -5.5F));
		root.addOrReplaceChild("left_front_leg", leg, PartPose.offset(3.5F, 18.0F, -5.5F));
		root.addOrReplaceChild("right_hind_leg", leg, PartPose.offset(-3.5F, 18.0F, 6.5F));
		root.addOrReplaceChild("left_hind_leg", leg, PartPose.offset(3.5F, 18.0F, 6.5F));
		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(MenagerieRenderState state) {
		super.setupAnim(state);
		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;

		float pos = state.walkAnimationPos;
		float speed = state.walkAnimationSpeed;
		this.rightFrontLeg.xRot = Mth.cos(pos * 0.6662F) * 1.0F * speed;
		this.leftFrontLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 1.0F * speed;
		this.rightHindLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 1.0F * speed;
		this.leftHindLeg.xRot = Mth.cos(pos * 0.6662F) * 1.0F * speed;

		if (state.bearSleeping) {
			// belly to the ground, legs folded flat, head resting
			this.body.y += 4.0F;
			this.head.y += 5.0F;
			this.head.xRot = 0.2F;
			this.rightFrontLeg.y += 4.0F;
			this.leftFrontLeg.y += 4.0F;
			this.rightHindLeg.y += 4.0F;
			this.leftHindLeg.y += 4.0F;
			this.rightFrontLeg.xRot = -1.4F;
			this.leftFrontLeg.xRot = -1.4F;
			this.rightHindLeg.xRot = 1.4F;
			this.leftHindLeg.xRot = 1.4F;
		}

		if (state.swipeTicks > 0.0F) {
			// fishing paw-swipe: right foreleg sweeps up and across
			float arc = Mth.sin(state.swipeTicks / 15.0F * Mth.PI);
			this.rightFrontLeg.xRot = -1.9F * arc;
			this.rightFrontLeg.zRot = -0.5F * arc;
			this.head.xRot += 0.3F * arc;
		}
	}
}
