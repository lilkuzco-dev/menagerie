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
 * Sleek big cat. Crouch = lowered body with folded legs; pounce = body pitched down,
 * front legs reaching. UV map mirrored in tools/gen-textures.js.
 */
public class LeopardModel extends EntityModel<MenagerieRenderState> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;

	public LeopardModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.head = root.getChild("head");
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
				.texOffs(0, 0).addBox(-3.5F, -2.5F, -8.0F, 7.0F, 5.0F, 16.0F),
				PartPose.offset(0.0F, 17.0F, 0.0F));
		root.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(0, 21).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 5.0F, 5.0F)
				.texOffs(22, 21).addBox(-3.0F, -5.0F, -2.0F, 2.0F, 2.0F, 1.0F)
				.texOffs(22, 21).addBox(1.0F, -5.0F, -2.0F, 2.0F, 2.0F, 1.0F),
				PartPose.offset(0.0F, 16.0F, -8.0F));
		root.addOrReplaceChild("tail", CubeListBuilder.create()
				.texOffs(36, 21).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 8.0F),
				PartPose.offsetAndRotation(0.0F, 15.0F, 8.0F, 0.9F, 0.0F, 0.0F));
		CubeListBuilder leg = CubeListBuilder.create()
				.texOffs(28, 21).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F);
		root.addOrReplaceChild("right_front_leg", leg, PartPose.offset(-2.5F, 17.0F, -6.0F));
		root.addOrReplaceChild("left_front_leg", leg, PartPose.offset(2.5F, 17.0F, -6.0F));
		root.addOrReplaceChild("right_hind_leg", leg, PartPose.offset(-2.5F, 17.0F, 6.0F));
		root.addOrReplaceChild("left_hind_leg", leg, PartPose.offset(2.5F, 17.0F, 6.0F));
		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(MenagerieRenderState state) {
		super.setupAnim(state);
		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;

		float pos = state.walkAnimationPos;
		float speed = state.walkAnimationSpeed;
		this.rightFrontLeg.xRot = Mth.cos(pos * 0.6662F) * 1.1F * speed;
		this.leftFrontLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 1.1F * speed;
		this.rightHindLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 1.1F * speed;
		this.leftHindLeg.xRot = Mth.cos(pos * 0.6662F) * 1.1F * speed;
		this.tail.yRot = Mth.cos(pos * 0.6662F) * 0.3F * speed
				+ Mth.sin(state.ageInTicks * 0.07F) * 0.15F;

		if (state.crouching) {
			// belly to the ground, legs folded, tail low and still
			this.body.y += 2.5F;
			this.head.y += 3.0F;
			this.tail.y += 2.5F;
			this.tail.xRot = 0.3F;
			this.rightFrontLeg.y += 2.5F;
			this.leftFrontLeg.y += 2.5F;
			this.rightHindLeg.y += 2.5F;
			this.leftHindLeg.y += 2.5F;
			this.rightFrontLeg.xRot -= 0.5F;
			this.leftFrontLeg.xRot -= 0.5F;
			this.rightHindLeg.xRot += 0.5F;
			this.leftHindLeg.xRot += 0.5F;
		}

		if (state.pounceTicks > 0.0F) {
			float arc = Mth.sin(state.pounceTicks / 12.0F * Mth.PI);
			this.body.xRot = -0.4F * arc;
			this.rightFrontLeg.xRot = -1.6F * arc;
			this.leftFrontLeg.xRot = -1.6F * arc;
			this.rightHindLeg.xRot = 0.8F * arc;
			this.leftHindLeg.xRot = 0.8F * arc;
		}
	}
}
