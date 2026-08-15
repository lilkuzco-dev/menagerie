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
 * Segmented snake: chained body parts curl into a coil at rest, the tail tip rattles
 * during the warning, the head lunges on strike. UV map in tools/gen-textures.js.
 */
public class SnakeModel extends EntityModel<MenagerieRenderState> {
	private final ModelPart head;
	private final ModelPart body1;
	private final ModelPart body2;
	private final ModelPart body3;
	private final ModelPart tail;

	public SnakeModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.body1 = root.getChild("body1");
		this.body2 = this.body1.getChild("body2");
		this.body3 = this.body2.getChild("body3");
		this.tail = this.body3.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F),
				PartPose.offset(0.0F, 22.5F, -5.0F));
		PartDefinition body1 = root.addOrReplaceChild("body1", CubeListBuilder.create()
				.texOffs(12, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 4.0F),
				PartPose.offset(0.0F, 22.5F, -5.0F));
		PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create()
				.texOffs(12, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 4.0F),
				PartPose.offset(0.0F, 0.0F, 4.0F));
		PartDefinition body3 = body2.addOrReplaceChild("body3", CubeListBuilder.create()
				.texOffs(12, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 4.0F),
				PartPose.offset(0.0F, 0.0F, 4.0F));
		body3.addOrReplaceChild("tail", CubeListBuilder.create()
				.texOffs(26, 0).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 4.0F),
				PartPose.offset(0.0F, 0.0F, 4.0F));
		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(MenagerieRenderState state) {
		super.setupAnim(state);
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD * 0.6F;

		float pos = state.walkAnimationPos;
		float speed = state.walkAnimationSpeed;
		// slither: body segments serpentine while moving
		this.body1.yRot = Mth.cos(pos * 1.2F) * 0.4F * speed;
		this.body2.yRot = Mth.cos(pos * 1.2F + Mth.PI * 0.5F) * 0.5F * speed;
		this.body3.yRot = Mth.cos(pos * 1.2F + Mth.PI) * 0.5F * speed;

		if (state.coiled && speed < 0.05F) {
			// curl up: each segment bends further round
			this.body1.yRot = 0.7F;
			this.body2.yRot = 1.15F;
			this.body3.yRot = 1.3F;
			this.head.xRot = -0.2F; // head raised out of the coil
			this.head.y -= 1.0F;
		}

		if (state.rattling) {
			this.tail.yRot = Mth.sin(state.ageInTicks * 1.4F) * 0.5F;
			this.tail.y -= 1.5F;
			this.head.xRot = -0.5F;
			this.head.y -= 1.5F;
		}

		if (state.strikeTicks > 0.0F) {
			float lunge = Mth.sin(state.strikeTicks / 10.0F * Mth.PI);
			this.head.z -= 3.0F * lunge;
			this.head.xRot = -0.4F * lunge;
			this.body1.yRot = 0.0F;
			this.body2.yRot = 0.2F * lunge;
		}
	}
}
