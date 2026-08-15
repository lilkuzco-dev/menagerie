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
 * Broad-winged scavenger: bald snaking neck, long flat wings that flap while flying
 * and fold when grounded, tucked legs in the air. UV map in tools/gen-textures.js.
 */
public class VultureModel extends EntityModel<MenagerieRenderState> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart tail;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public VultureModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.rightWing = root.getChild("right_wing");
		this.leftWing = root.getChild("left_wing");
		this.tail = root.getChild("tail");
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild("body", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 4.0F, 8.0F),
				PartPose.offset(0.0F, 17.0F, 0.0F));
		root.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(28, 0).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 5.0F, 3.0F)
				.texOffs(40, 0).addBox(-2.0F, -7.0F, -4.0F, 4.0F, 3.0F, 5.0F),
				PartPose.offset(0.0F, 15.0F, -3.0F));
		root.addOrReplaceChild("right_wing", CubeListBuilder.create()
				.texOffs(0, 12).addBox(-12.0F, -0.5F, -4.0F, 12.0F, 1.0F, 8.0F),
				PartPose.offset(-3.0F, 15.5F, 0.0F));
		root.addOrReplaceChild("left_wing", CubeListBuilder.create()
				.texOffs(0, 12).mirror().addBox(0.0F, -0.5F, -4.0F, 12.0F, 1.0F, 8.0F),
				PartPose.offset(3.0F, 15.5F, 0.0F));
		root.addOrReplaceChild("tail", CubeListBuilder.create()
				.texOffs(0, 21).addBox(-2.5F, -0.5F, 0.0F, 5.0F, 1.0F, 6.0F),
				PartPose.offset(0.0F, 17.0F, 4.0F));
		CubeListBuilder leg = CubeListBuilder.create()
				.texOffs(22, 21).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F);
		root.addOrReplaceChild("right_leg", leg, PartPose.offset(-1.5F, 20.0F, 1.0F));
		root.addOrReplaceChild("left_leg", leg, PartPose.offset(1.5F, 20.0F, 1.0F));
		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(MenagerieRenderState state) {
		super.setupAnim(state);
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD * 0.5F;

		if (state.flying) {
			// slow soaring flap, banked body, tucked legs
			float flap = Mth.sin(state.ageInTicks * 0.13F) * 0.35F;
			this.rightWing.zRot = 0.15F + flap;
			this.leftWing.zRot = -0.15F - flap;
			this.body.xRot = state.xRot * Mth.DEG_TO_RAD * 0.6F;
			this.tail.xRot = 0.1F;
			this.rightLeg.xRot = 1.3F;
			this.leftLeg.xRot = 1.3F;
			this.head.xRot = -0.3F;
		} else {
			// grounded: wings folded against the body, hop-walk
			this.rightWing.zRot = -1.45F;
			this.leftWing.zRot = 1.45F;
			this.rightWing.y += 2.0F;
			this.leftWing.y += 2.0F;
			float pos = state.walkAnimationPos;
			float speed = state.walkAnimationSpeed;
			this.rightLeg.xRot = Mth.cos(pos * 0.6662F) * 1.2F * speed;
			this.leftLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 1.2F * speed;
			this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		}
	}
}
