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
 * Knuckle-walking gorilla: chunky torso, long arms to the ground, short hind legs.
 * Chest-beat = torso rears upright while the arms thump alternately (programmatic
 * part rotation, vanilla-wolf-shake style). UV map mirrored in tools/gen-textures.js.
 */
public class GorillaModel extends EntityModel<MenagerieRenderState> {
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public GorillaModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.rightArm = root.getChild("right_arm");
		this.leftArm = root.getChild("left_arm");
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-4.0F, -5.0F, -6.0F, 8.0F, 8.0F, 6.0F)
				.texOffs(28, 0).addBox(-2.0F, -1.0F, -8.0F, 4.0F, 3.0F, 2.0F),
				PartPose.offset(0.0F, 9.0F, -5.0F));
		root.addOrReplaceChild("body", CubeListBuilder.create()
				.texOffs(0, 16).addBox(-6.0F, -6.0F, -7.0F, 12.0F, 12.0F, 14.0F),
				PartPose.offset(0.0F, 13.0F, 1.0F));
		root.addOrReplaceChild("right_arm", CubeListBuilder.create()
				.texOffs(44, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
				PartPose.offset(-5.0F, 12.0F, -4.0F));
		root.addOrReplaceChild("left_arm", CubeListBuilder.create()
				.texOffs(44, 0).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
				PartPose.offset(5.0F, 12.0F, -4.0F));
		root.addOrReplaceChild("right_leg", CubeListBuilder.create()
				.texOffs(0, 42).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F),
				PartPose.offset(-3.5F, 17.0F, 6.0F));
		root.addOrReplaceChild("left_leg", CubeListBuilder.create()
				.texOffs(0, 42).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F),
				PartPose.offset(3.5F, 17.0F, 6.0F));
		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(MenagerieRenderState state) {
		super.setupAnim(state);
		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;

		// knuckle-walk: arms and legs swing in opposing pairs
		float pos = state.walkAnimationPos;
		float speed = state.walkAnimationSpeed;
		this.rightArm.xRot = Mth.cos(pos * 0.6662F) * 1.2F * speed;
		this.leftArm.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 1.2F * speed;
		this.rightLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 1.2F * speed;
		this.leftLeg.xRot = Mth.cos(pos * 0.6662F) * 1.2F * speed;

		if (state.sittingPose) {
			this.body.xRot = -0.5F;
			this.body.y += 2.0F;
			this.head.y += 1.5F;
			this.rightArm.xRot = 0.0F;
			this.leftArm.xRot = 0.0F;
		}

		if (state.beatTicks > 0.0F) {
			// rear up and thump: arms alternate against the chest
			float thump = state.beatTicks * 0.8F;
			this.body.xRot = -1.0F;
			this.body.y += 1.0F;
			this.head.y -= 5.0F;
			this.head.z -= 3.0F;
			this.head.xRot -= 0.4F;
			this.rightArm.y -= 5.0F;
			this.leftArm.y -= 5.0F;
			this.rightArm.z -= 2.0F;
			this.leftArm.z -= 2.0F;
			this.rightArm.xRot = -2.4F + Mth.sin(thump) * 0.5F;
			this.leftArm.xRot = -2.4F + Mth.sin(thump + Mth.PI) * 0.5F;
		}

		if (state.eatTicks > 0.0F) {
			this.head.xRot = 0.6F + Mth.sin(state.eatTicks) * 0.15F;
		}
	}
}
