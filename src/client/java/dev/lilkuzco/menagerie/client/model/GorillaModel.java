// Model geometry and keyframe animations ported from Animal Garden - Western Gorilla
// 1.0.1 by aquarius_playz (public domain, The Unlicense — see CREDITS.md). Remapped
// from Forge 1.20.1 SRG names to Mojang 26.2 names and rebuilt on the 26.2 baked
// KeyframeAnimation API; the mod's hand-rolled animation runner is not needed here.
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
 * The gorilla: 26 parts with an articulated face (eyes, brows, jaw), three-segment
 * arms and two-segment legs, on a 128x128 skin. Animations are baked once here and
 * applied from synced state; part offsets are additive, so the look-at rotation set
 * below composes with whatever animation is playing.
 */
public class GorillaModel extends EntityModel<MenagerieRenderState> {
	private final ModelPart head;
	private final ModelPart headTop;
	private final KeyframeAnimation walkAnimation;
	private final KeyframeAnimation breathingAnimation;
	private final KeyframeAnimation chestPumpAnimation;
	private final KeyframeAnimation eatAnimation;
	private final KeyframeAnimation punchAnimation;
	private final KeyframeAnimation winkAnimation;
	private final KeyframeAnimation sniffingAnimation;
	private final KeyframeAnimation makeSoundAnimation;
	private final KeyframeAnimation sitStartAnimation;
	private final KeyframeAnimation sitLoopAnimation;
	private final KeyframeAnimation sitEndAnimation;

	public GorillaModel(ModelPart root) {
		super(root);
		ModelPart rootz = root.getChild("root").getChild("rootz");
		this.head = rootz.getChild("head");
		this.headTop = this.head.getChild("headTop");
		this.walkAnimation = GorillaAnimations.walk.bake(root);
		this.breathingAnimation = GorillaAnimations.breathing.bake(root);
		this.chestPumpAnimation = GorillaAnimations.chestPump.bake(root);
		this.eatAnimation = GorillaAnimations.eat.bake(root);
		this.punchAnimation = GorillaAnimations.punch.bake(root);
		this.winkAnimation = GorillaAnimations.wink.bake(root);
		this.sniffingAnimation = GorillaAnimations.sniffing.bake(root);
		this.makeSoundAnimation = GorillaAnimations.makeSound1.bake(root);
		this.sitStartAnimation = GorillaAnimations.fromNormalToSitting.bake(root);
		this.sitLoopAnimation = GorillaAnimations.sittingLoop.bake(root);
		this.sitEndAnimation = GorillaAnimations.fromSittingToNormal.bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.ZERO);
		PartDefinition rootz = root.addOrReplaceChild("rootz", CubeListBuilder.create(), PartPose.offset(0.0F, 26.0F, -3.0F));
		PartDefinition head = rootz.addOrReplaceChild(
			"head",
			CubeListBuilder.create().texOffs(0, 35).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, -18.0F, 1.0F)
		);
		PartDefinition headTop = head.addOrReplaceChild(
			"headTop",
			CubeListBuilder.create().texOffs(33, 35).addBox(-4.0F, -3.0F, -2.0F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, -8.0F, -1.0F)
		);
		PartDefinition eyebrow = head.addOrReplaceChild(
			"eyebrow",
			CubeListBuilder.create().texOffs(64, 25).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)),
			PartPose.offset(0.0F, -5.0F, -5.0F)
		);
		PartDefinition rightEye = head.addOrReplaceChild(
			"rightEye",
			CubeListBuilder.create().texOffs(37, 69).addBox(-1.0F, -1.5F, -0.51F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)),
			PartPose.offset(-2.0F, -2.75F, -4.6F)
		);
		PartDefinition rightEyeball = rightEye.addOrReplaceChild(
			"rightEyeball",
			CubeListBuilder.create().texOffs(28, 69).addBox(-1.0F, -1.0F, -0.511F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)),
			PartPose.offset(0.1F, -0.5F, -0.4F)
		);
		PartDefinition leftEye = head.addOrReplaceChild(
			"leftEye",
			CubeListBuilder.create().texOffs(69, 59).addBox(-1.0F, -1.5F, -0.51F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)),
			PartPose.offset(2.0F, -2.75F, -4.6F)
		);
		PartDefinition leftEyeball = leftEye.addOrReplaceChild(
			"leftEyeball",
			CubeListBuilder.create().texOffs(68, 62).addBox(-1.0F, -1.0F, -0.511F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)),
			PartPose.offset(-0.1F, -0.5F, -0.4F)
		);
		PartDefinition mouth = head.addOrReplaceChild(
			"mouth",
			CubeListBuilder.create()
				.texOffs(66, 0)
				.addBox(-2.5F, -2.0F, -2.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(83, 1)
				.addBox(-2.0F, -1.2F, -2.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(83, 1)
				.addBox(1.0F, -1.2F, -2.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)),
			PartPose.offset(0.0F, 0.1F, -4.5F)
		);
		PartDefinition nose = mouth.addOrReplaceChild(
			"nose",
			CubeListBuilder.create()
				.texOffs(64, 30)
				.addBox(-1.5F, -0.9F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(25, 53)
				.addBox(-1.1F, -0.4F, -1.21F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
				.texOffs(66, 11)
				.addBox(0.1F, -0.4F, -1.21F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)),
			PartPose.offset(0.0F, -2.1F, -1.5F)
		);
		PartDefinition bottomMouth = mouth.addOrReplaceChild(
			"bottomMouth",
			CubeListBuilder.create()
				.texOffs(43, 15)
				.addBox(-2.5F, 1.0F, -4.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F))
				.texOffs(83, 1)
				.addBox(-1.5F, 0.3F, -3.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
				.texOffs(83, 1)
				.addBox(0.5F, 0.3F, -3.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)),
			PartPose.offset(0.0F, -1.05F, 1.55F)
		);
		PartDefinition rightEar = head.addOrReplaceChild(
			"rightEar",
			CubeListBuilder.create().texOffs(66, 5).addBox(-1.5F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)),
			PartPose.offset(-4.0F, -3.5F, -1.0F)
		);
		PartDefinition leftEar = head.addOrReplaceChild(
			"leftEar",
			CubeListBuilder.create().texOffs(19, 69).addBox(-0.5F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)),
			PartPose.offset(4.0F, -3.5F, -1.0F)
		);
		PartDefinition body = rootz.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));
		PartDefinition realbody = body.addOrReplaceChild(
			"realbody",
			CubeListBuilder.create()
				.texOffs(0, 19)
				.addBox(-6.0F, 0.0F, -4.0F, 12.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-6.0F, 7.0F, -5.0F, 12.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(0.0F, -17.0F, 1.0F, 0.6545F, 0.0F, 0.0F)
		);
		PartDefinition rightLeg = body.addOrReplaceChild(
			"rightLeg",
			CubeListBuilder.create().texOffs(33, 45).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)),
			PartPose.offset(-5.0F, -6.5F, 9.0F)
		);
		PartDefinition rightLeg2 = rightLeg.addOrReplaceChild(
			"rightLeg2",
			CubeListBuilder.create().texOffs(0, 53).addBox(-2.5F, -1.0F, -5.5F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, 5.5F, 0.5F)
		);
		PartDefinition leftLeg = body.addOrReplaceChild(
			"leftLeg",
			CubeListBuilder.create().texOffs(54, 45).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)),
			PartPose.offset(5.0F, -6.5F, 9.0F)
		);
		PartDefinition leftLeg2 = leftLeg.addOrReplaceChild(
			"leftLeg2",
			CubeListBuilder.create().texOffs(25, 59).addBox(-2.5F, -1.0F, -5.5F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, 5.5F, 0.5F)
		);
		PartDefinition rightArm = body.addOrReplaceChild(
			"rightArm",
			CubeListBuilder.create().texOffs(50, 59).addBox(-3.0F, -2.0F, -2.5F, 4.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-7.0F, -14.25F, 2.5F, -0.3054F, 0.0F, 0.0F)
		);
		PartDefinition rightArm2 = rightArm.addOrReplaceChild(
			"rightArm2",
			CubeListBuilder.create().texOffs(41, 19).addBox(-2.75F, 0.0F, -2.75F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-1.0F, 6.0F, 0.0F, -0.3054F, 0.0F, 0.0F)
		);
		PartDefinition rightArm3 = rightArm2.addOrReplaceChild(
			"rightArm3",
			CubeListBuilder.create().texOffs(62, 34).addBox(-2.0F, -0.75F, -1.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(0.0F, 7.25F, -1.0F, 0.6109F, 0.0F, 0.0F)
		);
		PartDefinition leftArm = body.addOrReplaceChild(
			"leftArm",
			CubeListBuilder.create().texOffs(0, 63).addBox(-1.0F, -2.0F, -2.5F, 4.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(7.0F, -14.25F, 2.5F, -0.3054F, 0.0F, 0.0F)
		);
		PartDefinition leftArm2 = leftArm.addOrReplaceChild(
			"leftArm2",
			CubeListBuilder.create().texOffs(43, 0).addBox(-2.75F, 0.0F, -2.75F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(1.5F, 6.0F, 0.0F, -0.3054F, 0.0F, 0.0F)
		);
		PartDefinition leftArm3 = leftArm2.addOrReplaceChild(
			"leftArm3",
			CubeListBuilder.create().texOffs(64, 15).addBox(-2.0F, -0.75F, -1.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-0.5F, 7.25F, -1.0F, 0.6109F, 0.0F, 0.0F)
		);
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(MenagerieRenderState state) {
		super.setupAnim(state);
		// the sagittal crest reads as the silverback's dome
		this.headTop.visible = state.silverback && !state.isBaby;
		this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD;

		this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed,
				state.isBaby ? 2.5F : 5.0F, 10.0F);
		this.breathingAnimation.apply(state.gorillaBreathing, state.ageInTicks);
		this.chestPumpAnimation.apply(state.gorillaChestPump, state.ageInTicks);
		this.eatAnimation.apply(state.gorillaEat, state.ageInTicks);
		this.punchAnimation.apply(state.gorillaPunch, state.ageInTicks);
		this.winkAnimation.apply(state.gorillaWink, state.ageInTicks);
		this.sniffingAnimation.apply(state.gorillaSniff, state.ageInTicks);
		this.makeSoundAnimation.apply(state.gorillaSound, state.ageInTicks);
		// exactly one sitting animation is ever running (see GorillaEntity client tick)
		this.sitStartAnimation.apply(state.gorillaSitStart, state.ageInTicks);
		this.sitLoopAnimation.apply(state.gorillaSitLoop, state.ageInTicks);
		this.sitEndAnimation.apply(state.gorillaSitEnd, state.ageInTicks);
	}
}
