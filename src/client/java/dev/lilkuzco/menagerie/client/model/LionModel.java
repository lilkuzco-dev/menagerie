// Model geometry and keyframe animations from Animal Garden - Lion 1.0.3 by
// aquarius_playz (public domain, The Unlicense — see CREDITS.md). The source is a
// native Fabric 26.2 build in Mojang names, so the geometry is verbatim; only the
// animation runner was swapped for vanilla's baked KeyframeAnimation API and the
// aquarius_libs base classes for Menagerie's own.
package dev.lilkuzco.menagerie.client.model;

import dev.lilkuzco.menagerie.client.MenagerieRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * The lion: 34 parts with an articulated jaw, ears and tail on a 128x128 skin. The mane
 * is a separate part shown only on pride males, which is also how the pride leader reads
 * at a glance — the lion's answer to the silverback's saddle.
 */
public class LionModel extends EntityModel<MenagerieRenderState> {
	private final ModelPart head;
	private final ModelPart headMane;
	private final KeyframeAnimation walkAnimation;
	private final KeyframeAnimation runAnimation;
	private final KeyframeAnimation breathingAnimation;
	private final KeyframeAnimation biteAnimation;
	private final KeyframeAnimation roarAnimation;
	private final KeyframeAnimation tailAnimation;
	private final KeyframeAnimation earAnimation;
	private final KeyframeAnimation winkAnimation;
	private final KeyframeAnimation sniffAnimation;
	private final KeyframeAnimation yawnAnimation;
	private final KeyframeAnimation sleepStartAnimation;
	private final KeyframeAnimation sleepLoopAnimation;
	private final KeyframeAnimation sleepEndAnimation;

	public LionModel(ModelPart root) {
		super(root);
		ModelPart rootz = root.getChild("rootz");
		this.head = rootz.getChild("head");
		this.headMane = this.head.getChild("headMane");
		this.walkAnimation = LionAnimations.walk.bake(root);
		this.runAnimation = LionAnimations.run.bake(root);
		this.breathingAnimation = LionAnimations.breathing.bake(root);
		this.biteAnimation = LionAnimations.bite.bake(root);
		this.roarAnimation = LionAnimations.makeSound1.bake(root);
		this.tailAnimation = LionAnimations.tail.bake(root);
		this.earAnimation = LionAnimations.rightEar.bake(root);
		this.winkAnimation = LionAnimations.wink.bake(root);
		this.sniffAnimation = LionAnimations.sniffing.bake(root);
		this.yawnAnimation = LionAnimations.yawning.bake(root);
		this.sleepStartAnimation = LionAnimations.fromStandingToSleep.bake(root);
		this.sleepLoopAnimation = LionAnimations.sleepingLoop.bake(root);
		this.sleepEndAnimation = LionAnimations.fromSleepingToStanding.bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition rootz = partdefinition.addOrReplaceChild("rootz", CubeListBuilder.create(), PartPose.offset(0.0F, 20.6F, 5.0F));
		PartDefinition head = rootz.addOrReplaceChild(
			"head",
			CubeListBuilder.create().texOffs(0, 26).addBox(-4.0F, -7.0F, -6.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, -5.0F, -10.9F)
		);
		PartDefinition mouth = head.addOrReplaceChild(
			"mouth",
			CubeListBuilder.create()
				.texOffs(0, 65)
				.addBox(-2.0F, -1.1031F, -2.9562F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(41, 72)
				.addBox(1.25F, -1.1031F, -2.9562F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(75, 26)
				.addBox(-2.25F, -1.1031F, -2.9562F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(69, 70)
				.addBox(-1.0F, -1.3531F, -2.9562F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, -1.8969F, -6.0438F)
		);
		PartDefinition lowerMouth = mouth.addOrReplaceChild(
			"lowerMouth",
			CubeListBuilder.create().texOffs(62, 35).addBox(-2.0F, 0.25F, -3.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, 0.6469F, 0.7938F)
		);
		PartDefinition bottomTeeth = lowerMouth.addOrReplaceChild(
			"bottomTeeth",
			CubeListBuilder.create()
				.texOffs(22, 71)
				.addBox(1.0F, -1.0833F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(50, 72)
				.addBox(-2.0F, -1.0833F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(28, 70)
				.addBox(-2.0F, -1.3333F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-0.85F)),
			PartPose.offset(0.0F, 0.3333F, -3.0F)
		);
		PartDefinition nose = mouth.addOrReplaceChild(
			"nose",
			CubeListBuilder.create().texOffs(75, 32).addBox(-1.0F, 0.0F, -0.6F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)),
			PartPose.offset(0.0F, -1.3031F, -2.4562F)
		);
		PartDefinition backOfTheMouth = mouth.addOrReplaceChild(
			"backOfTheMouth",
			CubeListBuilder.create().texOffs(71, 61).addBox(-2.0F, -1.0F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(-0.04F)),
			PartPose.offset(0.0F, 0.8969F, 0.4938F)
		);
		PartDefinition topTeeth = mouth.addOrReplaceChild(
			"topTeeth",
			CubeListBuilder.create()
				.texOffs(50, 76)
				.addBox(1.1F, -0.875F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
				.texOffs(55, 76)
				.addBox(-2.1F, -0.875F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
				.texOffs(56, 70)
				.addBox(-2.0F, -1.75F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-0.85F)),
			PartPose.offset(0.0F, 0.9219F, -2.2062F)
		);
		PartDefinition rightEar = head.addOrReplaceChild(
			"rightEar",
			CubeListBuilder.create().texOffs(71, 65).addBox(-1.98F, -2.0F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
			PartPose.offset(-3.0F, -7.0F, -3.5F)
		);
		PartDefinition leftEar = head.addOrReplaceChild(
			"leftEar",
			CubeListBuilder.create().texOffs(73, 40).addBox(-1.02F, -2.0F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
			PartPose.offset(3.0F, -7.0F, -3.5F)
		);
		PartDefinition rightEye = head.addOrReplaceChild(
			"rightEye",
			CubeListBuilder.create()
				.texOffs(27, 76)
				.addBox(-1.0F, -1.75F, -0.475F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.06F))
				.texOffs(27, 80)
				.addBox(-1.0F, -1.75F, -0.395F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
			PartPose.offset(-2.25F, -3.25F, -5.615F)
		);
		PartDefinition rightEyeball = rightEye.addOrReplaceChild(
			"rightEyeball",
			CubeListBuilder.create().texOffs(9, 76).addBox(-1.0F, -1.0F, -1.02F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)),
			PartPose.offset(0.1F, -0.65F, -0.105F)
		);
		PartDefinition leftEye = head.addOrReplaceChild(
			"leftEye",
			CubeListBuilder.create()
				.texOffs(34, 76)
				.addBox(-1.0F, -1.75F, -0.475F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.06F))
				.texOffs(34, 80)
				.addBox(-1.0F, -1.75F, -0.395F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
			PartPose.offset(2.25F, -3.25F, -5.615F)
		);
		PartDefinition leftEyeball = leftEye.addOrReplaceChild(
			"leftEyeball",
			CubeListBuilder.create().texOffs(18, 76).addBox(-1.0F, -1.0F, -1.02F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)),
			PartPose.offset(-0.1F, -0.65F, -0.105F)
		);
		PartDefinition headMane = head.addOrReplaceChild(
			"headMane",
			CubeListBuilder.create()
				.texOffs(31, 26)
				.addBox(-5.0F, -5.0F, -3.0F, 10.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(31, 40)
				.addBox(-5.0F, -4.0F, 2.0F, 10.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(34, 49)
				.addBox(5.0F, -4.0F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(49, 49)
				.addBox(-6.0F, -4.0F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(78, 42)
				.addBox(-3.0F, 3.0F, -3.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 71)
				.addBox(-5.0F, 3.0F, -3.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(11, 71)
				.addBox(3.0F, 3.0F, -3.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(70, 0)
				.addBox(-2.0F, 4.0F, -3.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(54, 45)
				.addBox(-1.0F, 5.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(69, 75)
				.addBox(-3.0F, 4.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 76)
				.addBox(2.0F, 4.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, -3.0F, 0.0F)
		);
		PartDefinition body = rootz.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, -8.0F));
		PartDefinition realbody = body.addOrReplaceChild(
			"realbody",
			CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -8.5F, 10.0F, 8.0F, 17.0F, new CubeDeformation(-0.05F)),
			PartPose.offset(0.0F, 0.0F, 5.5F)
		);
		PartDefinition rightFrontLeg = body.addOrReplaceChild(
			"rightFrontLeg",
			CubeListBuilder.create().texOffs(0, 54).addBox(-1.5F, -1.95F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)),
			PartPose.offset(-4.0F, 0.95F, -1.1F)
		);
		PartDefinition rightFrontLeg2 = rightFrontLeg.addOrReplaceChild(
			"rightFrontLeg2",
			CubeListBuilder.create().texOffs(43, 63).addBox(-1.5F, -0.1F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, 3.05F, 0.0F)
		);
		PartDefinition rightFrontLegPaw = rightFrontLeg2.addOrReplaceChild(
			"rightFrontLegPaw",
			CubeListBuilder.create().texOffs(56, 63).addBox(-1.5F, 0.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)),
			PartPose.offset(0.0F, 4.4F, -0.5F)
		);
		PartDefinition leftFrontLeg = body.addOrReplaceChild(
			"leftFrontLeg",
			CubeListBuilder.create().texOffs(55, 0).addBox(-1.5F, -1.95F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)),
			PartPose.offset(4.0F, 0.95F, -1.1F)
		);
		PartDefinition leftFrontLeg2 = leftFrontLeg.addOrReplaceChild(
			"leftFrontLeg2",
			CubeListBuilder.create().texOffs(64, 45).addBox(-1.5F, -0.1F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, 3.05F, 0.0F)
		);
		PartDefinition leftFrontLegPaw = leftFrontLeg2.addOrReplaceChild(
			"leftFrontLegPaw",
			CubeListBuilder.create().texOffs(64, 54).addBox(-1.5F, 0.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)),
			PartPose.offset(0.0F, 4.4F, -0.5F)
		);
		PartDefinition rightHindLeg = body.addOrReplaceChild(
			"rightHindLeg",
			CubeListBuilder.create().texOffs(0, 41).addBox(-1.5F, -2.25F, -2.5F, 3.0F, 7.0F, 5.0F, new CubeDeformation(0.01F)),
			PartPose.offset(-4.0F, 0.65F, 11.95F)
		);
		PartDefinition rightHindLeg2 = rightHindLeg.addOrReplaceChild(
			"rightHindLeg2",
			CubeListBuilder.create().texOffs(15, 62).addBox(-1.5F, 0.0F, -2.25F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, 3.75F, 2.5F)
		);
		PartDefinition rightHindLegPaw = rightHindLeg2.addOrReplaceChild(
			"rightHindLegPaw",
			CubeListBuilder.create().texOffs(55, 19).addBox(-1.5F, 0.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)),
			PartPose.offset(0.0F, 4.0F, -0.75F)
		);
		PartDefinition leftHindLeg = body.addOrReplaceChild(
			"leftHindLeg",
			CubeListBuilder.create().texOffs(17, 49).addBox(-1.5F, -2.25F, -2.5F, 3.0F, 7.0F, 5.0F, new CubeDeformation(0.01F)),
			PartPose.offset(4.0F, 0.65F, 11.95F)
		);
		PartDefinition leftHindLeg2 = leftHindLeg.addOrReplaceChild(
			"leftHindLeg2",
			CubeListBuilder.create().texOffs(62, 26).addBox(-1.5F, 0.0F, -2.25F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, 3.75F, 2.5F)
		);
		PartDefinition leftHindLegPaw = leftHindLeg2.addOrReplaceChild(
			"leftHindLegPaw",
			CubeListBuilder.create().texOffs(28, 63).addBox(-1.5F, 0.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)),
			PartPose.offset(0.0F, 4.0F, -0.75F)
		);
		PartDefinition tail = body.addOrReplaceChild(
			"tail",
			CubeListBuilder.create().texOffs(17, 41).addBox(-1.0F, -1.4F, -1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)),
			PartPose.offsetAndRotation(0.0F, -2.6F, 13.75F, -1.0036F, 0.0F, 0.0F)
		);
		PartDefinition tail2 = tail.addOrReplaceChild(
			"tail2",
			CubeListBuilder.create().texOffs(70, 5).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.27F)),
			PartPose.offset(0.0F, -0.9F, 2.5F)
		);
		PartDefinition tail3 = tail2.addOrReplaceChild(
			"tail3",
			CubeListBuilder.create().texOffs(70, 12).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.29F)),
			PartPose.offset(0.0F, 0.0F, 3.0F)
		);
		PartDefinition tail4 = tail3.addOrReplaceChild(
			"tail4",
			CubeListBuilder.create().texOffs(70, 19).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.31F)),
			PartPose.offset(0.0F, 0.0F, 3.0F)
		);
		PartDefinition tail5 = tail4.addOrReplaceChild(
			"tail5",
			CubeListBuilder.create().texOffs(55, 11).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.7F)),
			PartPose.offset(0.0F, 0.0F, 3.0F)
		);
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(MenagerieRenderState state) {
		super.setupAnim(state);
		// the mane is the pride male's badge (source shows it for adult males)
		this.headMane.visible = state.silverback && !state.isBaby;
		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;

		// run and walk share the leg bones; blend by speed so a sprinting lion reads right
		float speed = state.walkAnimationSpeed;
		if (speed > 0.55F) {
			this.runAnimation.applyWalk(state.walkAnimationPos, speed, state.isBaby ? 2.5F : 7.0F, 10.0F);
		} else {
			this.walkAnimation.applyWalk(state.walkAnimationPos, speed, state.isBaby ? 2.5F : 3.0F, 10.0F);
		}
		this.breathingAnimation.apply(state.lionBreathing, state.ageInTicks);
		this.tailAnimation.apply(state.lionTail, state.ageInTicks);
		this.earAnimation.apply(state.lionEar, state.ageInTicks);
		this.winkAnimation.apply(state.lionWink, state.ageInTicks);
		this.sniffAnimation.apply(state.lionSniff, state.ageInTicks);
		this.yawnAnimation.apply(state.lionYawn, state.ageInTicks);
		this.biteAnimation.apply(state.lionBite, state.ageInTicks);
		this.roarAnimation.apply(state.lionRoar, state.ageInTicks);
		// exactly one sleep animation runs at a time (see LionEntity client tick)
		this.sleepStartAnimation.apply(state.lionSleepStart, state.ageInTicks);
		this.sleepLoopAnimation.apply(state.lionSleepLoop, state.ageInTicks);
		this.sleepEndAnimation.apply(state.lionSleepEnd, state.ageInTicks);
	}
}
