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
 * Domed tortoise. Shell retract = head, legs and tail tuck away (hidden) while the
 * shell stays. UV map mirrored in tools/gen-textures.js.
 */
public class TortoiseModel extends EntityModel<MenagerieRenderState> {
	private final ModelPart shell;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;

	public TortoiseModel(ModelPart root) {
		super(root);
		this.shell = root.getChild("shell");
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
		root.addOrReplaceChild("shell", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-5.0F, -3.0F, -6.0F, 10.0F, 6.0F, 12.0F)
				.texOffs(0, 18).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 2.0F, 8.0F),
				PartPose.offset(0.0F, 17.0F, 0.0F));
		root.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(44, 0).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F),
				PartPose.offset(0.0F, 19.0F, -6.0F));
		root.addOrReplaceChild("tail", CubeListBuilder.create()
				.texOffs(32, 18).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F),
				PartPose.offset(0.0F, 20.0F, 6.0F));
		CubeListBuilder leg = CubeListBuilder.create()
				.texOffs(44, 8).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F);
		root.addOrReplaceChild("right_front_leg", leg, PartPose.offset(-4.0F, 20.0F, -4.0F));
		root.addOrReplaceChild("left_front_leg", leg, PartPose.offset(4.0F, 20.0F, -4.0F));
		root.addOrReplaceChild("right_hind_leg", leg, PartPose.offset(-4.0F, 20.0F, 4.0F));
		root.addOrReplaceChild("left_hind_leg", leg, PartPose.offset(4.0F, 20.0F, 4.0F));
		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(MenagerieRenderState state) {
		super.setupAnim(state);
		boolean out = !state.shelled;
		this.head.visible = out;
		this.tail.visible = out;
		this.rightFrontLeg.visible = out;
		this.leftFrontLeg.visible = out;
		this.rightHindLeg.visible = out;
		this.leftHindLeg.visible = out;
		if (!out) {
			return;
		}
		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;
		float pos = state.walkAnimationPos;
		float speed = state.walkAnimationSpeed;
		this.rightFrontLeg.xRot = Mth.cos(pos * 0.6662F) * 0.8F * speed;
		this.leftFrontLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 0.8F * speed;
		this.rightHindLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 0.8F * speed;
		this.leftHindLeg.xRot = Mth.cos(pos * 0.6662F) * 0.8F * speed;
	}
}
