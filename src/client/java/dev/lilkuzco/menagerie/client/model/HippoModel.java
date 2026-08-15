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
 * Barrel-bodied hippo with a hinged jaw for the territorial yawn.
 * UV map mirrored in tools/gen-textures.js.
 */
public class HippoModel extends EntityModel<MenagerieRenderState> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;

	public HippoModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.jaw = root.getChild("jaw");
		this.rightFrontLeg = root.getChild("right_front_leg");
		this.leftFrontLeg = root.getChild("left_front_leg");
		this.rightHindLeg = root.getChild("right_hind_leg");
		this.leftHindLeg = root.getChild("left_hind_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild("body", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-7.0F, -6.0F, -8.0F, 14.0F, 12.0F, 16.0F),
				PartPose.offset(0.0F, 13.0F, 2.0F));
		root.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(0, 28).addBox(-5.0F, -4.0F, -10.0F, 10.0F, 6.0F, 10.0F)
				.texOffs(56, 0).addBox(-4.0F, -6.0F, -3.0F, 2.0F, 2.0F, 1.0F)
				.texOffs(56, 0).addBox(2.0F, -6.0F, -3.0F, 2.0F, 2.0F, 1.0F),
				PartPose.offset(0.0F, 15.0F, -6.0F));
		root.addOrReplaceChild("jaw", CubeListBuilder.create()
				.texOffs(0, 44).addBox(-4.5F, 0.0F, -9.0F, 9.0F, 3.0F, 9.0F),
				PartPose.offset(0.0F, 17.0F, -6.0F));
		CubeListBuilder leg = CubeListBuilder.create()
				.texOffs(40, 28).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F);
		root.addOrReplaceChild("right_front_leg", leg, PartPose.offset(-5.0F, 19.0F, -5.0F));
		root.addOrReplaceChild("left_front_leg", leg, PartPose.offset(5.0F, 19.0F, -5.0F));
		root.addOrReplaceChild("right_hind_leg", leg, PartPose.offset(-5.0F, 19.0F, 7.0F));
		root.addOrReplaceChild("left_hind_leg", leg, PartPose.offset(5.0F, 19.0F, 7.0F));
		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(MenagerieRenderState state) {
		super.setupAnim(state);
		this.head.xRot = state.xRot * Mth.DEG_TO_RAD * 0.5F;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD * 0.5F;
		this.jaw.yRot = this.head.yRot;

		float pos = state.walkAnimationPos;
		float speed = state.walkAnimationSpeed;
		this.rightFrontLeg.xRot = Mth.cos(pos * 0.6662F) * 0.9F * speed;
		this.leftFrontLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 0.9F * speed;
		this.rightHindLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 0.9F * speed;
		this.leftHindLeg.xRot = Mth.cos(pos * 0.6662F) * 0.9F * speed;

		if (state.yawnTicks > 0.0F) {
			float open = Mth.sin(state.yawnTicks / 40.0F * Mth.PI);
			this.jaw.xRot = 0.95F * open;
			this.head.xRot -= 0.35F * open;
		}
	}
}
