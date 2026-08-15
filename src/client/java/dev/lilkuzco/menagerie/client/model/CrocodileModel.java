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
 * Low, long crocodile: hinged jaw (opens on lunge), two-segment swaying tail,
 * stubby splayed legs. UV map mirrored in tools/gen-textures.js.
 */
public class CrocodileModel extends EntityModel<MenagerieRenderState> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart tail;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;

	public CrocodileModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.jaw = root.getChild("jaw");
		this.tail = root.getChild("tail");
		this.rightFrontLeg = root.getChild("right_front_leg");
		this.leftFrontLeg = root.getChild("left_front_leg");
		this.rightHindLeg = root.getChild("right_hind_leg");
		this.leftHindLeg = root.getChild("left_hind_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild("body", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-4.0F, -2.0F, -8.0F, 8.0F, 4.0F, 16.0F),
				PartPose.offset(0.0F, 20.0F, 0.0F));
		root.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(0, 20).addBox(-3.0F, -2.0F, -9.0F, 6.0F, 2.0F, 9.0F),
				PartPose.offset(0.0F, 21.0F, -8.0F));
		root.addOrReplaceChild("jaw", CubeListBuilder.create()
				.texOffs(30, 20).addBox(-3.0F, 0.0F, -9.0F, 6.0F, 2.0F, 9.0F),
				PartPose.offset(0.0F, 21.0F, -8.0F));
		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create()
				.texOffs(0, 31).addBox(-3.0F, -1.5F, 0.0F, 6.0F, 3.0F, 10.0F),
				PartPose.offset(0.0F, 20.0F, 8.0F));
		tail.addOrReplaceChild("tail2", CubeListBuilder.create()
				.texOffs(32, 31).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 8.0F),
				PartPose.offset(0.0F, 0.0F, 10.0F));
		CubeListBuilder leg = CubeListBuilder.create()
				.texOffs(0, 44).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F);
		root.addOrReplaceChild("right_front_leg", leg, PartPose.offset(-4.5F, 20.0F, -5.0F));
		root.addOrReplaceChild("left_front_leg", leg, PartPose.offset(4.5F, 20.0F, -5.0F));
		root.addOrReplaceChild("right_hind_leg", leg, PartPose.offset(-4.5F, 20.0F, 5.0F));
		root.addOrReplaceChild("left_hind_leg", leg, PartPose.offset(4.5F, 20.0F, 5.0F));
		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(MenagerieRenderState state) {
		super.setupAnim(state);
		float pos = state.walkAnimationPos;
		float speed = state.walkAnimationSpeed;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD * 0.5F;
		this.jaw.yRot = this.head.yRot;

		this.rightFrontLeg.xRot = Mth.cos(pos * 0.6662F) * 1.0F * speed;
		this.leftFrontLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 1.0F * speed;
		this.rightHindLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 1.0F * speed;
		this.leftHindLeg.xRot = Mth.cos(pos * 0.6662F) * 1.0F * speed;

		// tail sway: strong while swimming/walking, lazy when idle
		this.tail.yRot = Mth.cos(pos * 0.6662F) * 0.6F * speed
				+ Mth.sin(state.ageInTicks * 0.05F) * 0.08F;

		if (state.lungeTicks > 0.0F) {
			this.jaw.xRot = 0.7F * Mth.sin(state.lungeTicks / 15.0F * Mth.PI);
			this.head.xRot = -0.25F * Mth.sin(state.lungeTicks / 15.0F * Mth.PI);
		}
	}
}
