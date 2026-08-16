// Ported from Animal Garden - Western Gorilla 1.0.1 by aquarius_playz,
// released into the public domain under The Unlicense (see CREDITS.md).
// SRG names from the Forge 1.20.1 build were remapped to Mojang 26.2 names;
// the keyframe data itself is unchanged.
package dev.lilkuzco.menagerie.client.model;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public final class GorillaAnimations {
	public static final AnimationDefinition wink = Builder.withLength(0.75F)
	   .addAnimation(
	      "rightEye",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.2F, 0.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftEye",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.2F, 0.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightEyeball",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftEyeball",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition eyeLookLeft = Builder.withLength(1.75F)
	   .addAnimation(
	      "rightEyeball",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftEyeball",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.4F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.4F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition eyeLookRight = Builder.withLength(1.75F)
	   .addAnimation(
	      "rightEyeball",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.posVec(-0.4F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(-0.4F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftEyeball",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition eyeLookUp = Builder.withLength(1.75F)
	   .addAnimation(
	      "rightEyeball",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightEyeball",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.0, 0.75, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.scaleVec(1.0, 0.75, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftEyeball",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftEyeball",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.0, 0.75, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.scaleVec(1.0, 0.75, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition eyeLookDown = Builder.withLength(1.75F)
	   .addAnimation(
	      "rightEyeball",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -0.45F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, -0.45F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightEyeball",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.0, 0.75, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.scaleVec(1.0, 0.75, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftEyeball",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -0.45F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, -0.45F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftEyeball",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.scaleVec(1.0, 0.75, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.scaleVec(1.0, 0.75, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition eyeBrow1 = Builder.withLength(2.0F)
	   .addAnimation(
	      "eyebrow",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition eyeBrow2 = Builder.withLength(2.0F)
	   .addAnimation(
	      "eyebrow",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition eyeBrowUp = Builder.withLength(2.0F)
	   .addAnimation(
	      "eyebrow",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.65F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 0.65F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition eyeBrowDown = Builder.withLength(2.0F)
	   .addAnimation(
	      "eyebrow",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition breathing = Builder.withLength(3.25F)
	   .looping()
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.025F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.965F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "nose",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.scaleVec(1.1F, 1.1F, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(2.625F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition sniffing = Builder.withLength(1.0F)
	   .addAnimation(
	      "nose",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.1F, 1.3F, 1.2F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition eat = Builder.withLength(3.25F)
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "bottomMouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5833F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0833F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5833F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0833F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5833F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-3.8269F, -15.8282F, 12.3896F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-3.8269F, -15.8282F, 12.3896F), Interpolations.CATMULLROM),
	            new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-1.3269F, -15.8282F, 12.3896F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-3.0489F, -22.2823F, 9.3429F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(-3.8269F, -15.8282F, 12.3896F), Interpolations.CATMULLROM),
	            new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-1.3269F, -15.8282F, 12.3896F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(-3.0489F, -22.2823F, 9.3429F), Interpolations.CATMULLROM),
	            new Keyframe(2.75F, KeyframeAnimations.degreeVec(-3.8269F, -15.8282F, 12.3896F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-62.1982F, 16.5449F, -128.5583F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-69.5014F, 19.1895F, -152.0531F), Interpolations.CATMULLROM),
	            new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-67.1982F, 16.5449F, -128.5583F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-64.5014F, 19.1895F, -152.0531F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(-69.5014F, 19.1895F, -152.0531F), Interpolations.CATMULLROM),
	            new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-67.1982F, 16.5449F, -128.5583F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(-64.5014F, 19.1895F, -152.0531F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.75F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition makeSound1 = Builder.withLength(2.5F)
	   .addAnimation(
	      "bottomMouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.7083F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "bottomMouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.15F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.7083F, KeyframeAnimations.posVec(0.0F, 0.15F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition headLookLeft = Builder.withLength(3.0F)
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition headLookRight = Builder.withLength(3.0F)
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 35.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 35.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition headLookUp = Builder.withLength(3.0F)
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition headLookDown = Builder.withLength(3.0F)
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition headTiltLeft = Builder.withLength(3.5F)
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 24.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 24.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.75F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition headTiltRight = Builder.withLength(3.5F)
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -24.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -24.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.75F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition bite = Builder.withLength(0.8333F)
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-9.7676F, -2.1539F, -12.3159F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(2.3494F, 0.8548F, -19.9825F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(19.5623F, 4.2453F, -11.7678F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(19.9825F, 0.8548F, -2.3494F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "bottomMouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "bottomMouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.25F, -0.95F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.25F, -0.45F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "bottomMouth",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.1F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.1F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition fromNormalToSitting = Builder.withLength(0.5F)
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 3.0F, -6.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-41.1586F, 26.6904F, -2.7752F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(-1.0F, -2.0F, -3.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-41.1586F, -26.6904F, 2.7752F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(1.0F, -2.0F, -3.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.3185F, -42.0994F, 0.5659F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(88.2789F, -1.3261F, -41.9084F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition sittingLoop = Builder.withLength(2.0F)
	   .looping()
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -6.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -6.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-41.1586F, 26.6904F, -2.7752F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-41.1586F, 26.6904F, -2.7752F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(-1.0F, -2.0F, -3.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.posVec(-1.0F, -2.0F, -3.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-41.1586F, -26.6904F, 2.7752F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-41.1586F, -26.6904F, 2.7752F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, -2.0F, -3.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.posVec(1.0F, -2.0F, -3.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.3185F, -42.0994F, 0.5659F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(10.3185F, -42.0994F, 0.5659F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(88.2789F, -1.3261F, -41.9084F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(88.2789F, -1.3261F, -41.9084F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition fromSittingToNormal = Builder.withLength(0.5F)
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 3.0F, -6.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-41.1586F, 26.6904F, -2.7752F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(-1.0F, -2.0F, -3.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-41.1586F, -26.6904F, 2.7752F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, -2.0F, -3.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.3185F, -42.0994F, 0.5659F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(88.2789F, -1.3261F, -41.9084F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition punch = Builder.withLength(1.0F)
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0424F, 1.5864F, 1.1904F), Interpolations.CATMULLROM),
	            new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0264F, -2.9879F, -1.0628F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F), Interpolations.CATMULLROM),
	            new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.25F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.25F), Interpolations.CATMULLROM),
	            new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(76.6893F, 16.2323F, 54.8766F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-83.1345F, -20.5276F, 46.3831F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-23.7171F, -25.557F, 3.789F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-52.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.625F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-1.6611F, 24.5281F, -3.0912F), Interpolations.CATMULLROM),
	            new Keyframe(0.625F, KeyframeAnimations.degreeVec(14.8501F, -17.8937F, -2.1631F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(-0.1F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.625F, KeyframeAnimations.posVec(0.15F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.625F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition chestPump = Builder.withLength(2.5F)
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 2.0F, 7.0F), Interpolations.LINEAR),
	            new Keyframe(2.125F, KeyframeAnimations.posVec(0.0F, 2.0F, 7.0F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.125F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 2.0F, 7.0F), Interpolations.LINEAR),
	            new Keyframe(2.125F, KeyframeAnimations.posVec(0.0F, 2.0F, 7.0F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-9.6602F, -34.6677F, 48.1182F), Interpolations.LINEAR),
	            new Keyframe(0.625F, KeyframeAnimations.degreeVec(-15.2175F, -57.2535F, 62.6916F), Interpolations.LINEAR),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-9.6602F, -34.6677F, 48.1182F), Interpolations.LINEAR),
	            new Keyframe(0.875F, KeyframeAnimations.degreeVec(-15.2175F, -57.2535F, 62.6916F), Interpolations.LINEAR),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-9.6602F, -34.6677F, 48.1182F), Interpolations.LINEAR),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(-15.2175F, -57.2535F, 62.6916F), Interpolations.LINEAR),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-9.6602F, -34.6677F, 48.1182F), Interpolations.LINEAR),
	            new Keyframe(1.375F, KeyframeAnimations.degreeVec(-15.2175F, -57.2535F, 62.6916F), Interpolations.LINEAR),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-9.6602F, -34.6677F, 48.1182F), Interpolations.LINEAR),
	            new Keyframe(1.625F, KeyframeAnimations.degreeVec(-15.2175F, -57.2535F, 62.6916F), Interpolations.LINEAR),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(-9.6602F, -34.6677F, 48.1182F), Interpolations.LINEAR),
	            new Keyframe(1.875F, KeyframeAnimations.degreeVec(-15.2175F, -57.2535F, 62.6916F), Interpolations.LINEAR),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-9.6602F, -34.6677F, 48.1182F), Interpolations.LINEAR),
	            new Keyframe(2.125F, KeyframeAnimations.degreeVec(-15.2175F, -57.2535F, 62.6916F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(-0.5F, 3.0F, 4.0F), Interpolations.LINEAR),
	            new Keyframe(2.125F, KeyframeAnimations.posVec(-0.5F, 3.0F, 4.0F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.625F, KeyframeAnimations.degreeVec(-110.5778F, -14.2906F, 4.6066F), Interpolations.LINEAR),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.875F, KeyframeAnimations.degreeVec(-103.0778F, -14.2906F, 4.6066F), Interpolations.LINEAR),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(-103.0778F, -14.2906F, 4.6066F), Interpolations.LINEAR),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(1.375F, KeyframeAnimations.degreeVec(-110.5778F, -14.2906F, 4.6066F), Interpolations.LINEAR),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(1.625F, KeyframeAnimations.degreeVec(-110.5778F, -14.2906F, 4.6066F), Interpolations.LINEAR),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(1.875F, KeyframeAnimations.degreeVec(-103.0778F, -14.2906F, 4.6066F), Interpolations.LINEAR),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.125F, KeyframeAnimations.degreeVec(-103.0778F, -14.2906F, 4.6066F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.125F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-13.6199F, 43.4125F, -54.3828F), Interpolations.LINEAR),
	            new Keyframe(0.625F, KeyframeAnimations.degreeVec(-9.6602F, 34.6677F, -48.1182F), Interpolations.LINEAR),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-15.2175F, 57.2535F, -62.6916F), Interpolations.LINEAR),
	            new Keyframe(0.875F, KeyframeAnimations.degreeVec(-9.6602F, 34.6677F, -48.1182F), Interpolations.LINEAR),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-15.2175F, 57.2535F, -62.6916F), Interpolations.LINEAR),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(-9.6602F, 34.6677F, -48.1182F), Interpolations.LINEAR),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-15.2175F, 57.2535F, -62.6916F), Interpolations.LINEAR),
	            new Keyframe(1.375F, KeyframeAnimations.degreeVec(-9.6602F, 34.6677F, -48.1182F), Interpolations.LINEAR),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-15.2175F, 57.2535F, -62.6916F), Interpolations.LINEAR),
	            new Keyframe(1.625F, KeyframeAnimations.degreeVec(-9.6602F, 34.6677F, -48.1182F), Interpolations.LINEAR),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(-15.2175F, 57.2535F, -62.6916F), Interpolations.LINEAR),
	            new Keyframe(1.875F, KeyframeAnimations.degreeVec(-9.6602F, 34.6677F, -48.1182F), Interpolations.LINEAR),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-15.2175F, 57.2535F, -62.6916F), Interpolations.LINEAR),
	            new Keyframe(2.125F, KeyframeAnimations.degreeVec(-13.6199F, 43.4125F, -54.3828F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(-0.5F, 3.0F, 4.0F), Interpolations.LINEAR),
	            new Keyframe(2.125F, KeyframeAnimations.posVec(-0.5F, 3.0F, 4.0F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.625F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-110.5778F, 14.2906F, -4.6066F), Interpolations.LINEAR),
	            new Keyframe(0.875F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-110.5778F, 14.2906F, -4.6066F), Interpolations.LINEAR),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-110.5778F, 14.2906F, -4.6066F), Interpolations.LINEAR),
	            new Keyframe(1.375F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-103.0778F, 14.2906F, -4.6066F), Interpolations.LINEAR),
	            new Keyframe(1.625F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(-103.0778F, 14.2906F, -4.6066F), Interpolations.LINEAR),
	            new Keyframe(1.875F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-110.5778F, 14.2906F, -4.6066F), Interpolations.LINEAR),
	            new Keyframe(2.125F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.125F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition walk = Builder.withLength(1.5F)
	   .looping()
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.3F, 3.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -2.3F, 3.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0833F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.414F, 4.4406F, 3.9769F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.414F, 4.4406F, 3.9769F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.414F, -4.4406F, -3.9769F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.414F, -4.4406F, -3.9769F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.414F, 4.4406F, 3.9769F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(9.8801F, 19.0687F, -6.4051F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-34.2066F, 6.3562F, -2.135F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.1F, -1.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(-0.33F, -0.27F, 0.25F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(-0.66F, -0.51F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(-1.0F, -0.55F, 1.75F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(-0.67F, 1.38F, 0.83F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(-0.33F, 1.31F, -0.33F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -0.1F, -1.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(4.17F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-11.67F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(10.83F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-10.84F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightLeg2",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.1F, -0.75F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.1F, -0.75F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(9.8801F, -19.0687F, 6.4051F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-34.2066F, -6.3562F, 2.135F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(9.8801F, -19.0687F, 6.4051F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, -0.55F, 1.75F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.67F, 1.38F, 0.83F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.33F, 1.31F, -0.33F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.1F, -1.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.33F, -0.27F, 0.25F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.66F, -0.51F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(1.0F, -0.55F, 1.75F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(10.83F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.84F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(4.17F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-11.67F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftLeg2",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.1F, -0.75F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(51.1977F, -10.7377F, 12.4624F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(13.6977F, -10.7377F, 12.4624F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-28.5412F, -15.5392F, 6.0385F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(6.3718F, -13.9387F, 8.1798F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(51.1977F, -10.7377F, 12.4624F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.25F, 1.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 2.83F, 0.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 3.0F, -0.75F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.5F, 2.75F, -2.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.33F, 2.58F, -0.17F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.25F, 1.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-17.0615F, -7.053F, -7.1071F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-17.0615F, -7.053F, -7.1071F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-49.5615F, -7.053F, -7.1071F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(12.4967F, -5.4163F, -4.5455F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-18.3355F, -3.6109F, -3.0304F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-30.4178F, -1.8054F, -1.5152F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-17.0615F, -7.053F, -7.1071F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm2",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.75F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 1.25F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.75F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightArm3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-6.67F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-28.5412F, 15.5392F, -6.0385F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(6.3718F, 13.9387F, -8.1798F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(51.1977F, 10.7377F, -12.4624F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(13.6977F, 10.7377F, -12.4624F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-28.5412F, 15.5392F, -6.0385F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(-0.5F, 2.75F, -2.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(-0.33F, 2.58F, -0.17F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.25F, 1.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 2.83F, 0.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 3.0F, -0.75F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(-0.5F, 2.75F, -2.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.4967F, 5.4163F, 4.5455F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-18.3355F, 3.6109F, 3.0304F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-30.4178F, 1.8054F, 1.5152F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-17.0615F, 7.053F, 7.1071F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-17.0615F, 7.053F, 7.1071F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-49.5615F, 7.053F, 7.1071F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(12.4967F, 5.4163F, 4.5455F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm2",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.75F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 1.25F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftArm3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-6.67F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();

	private GorillaAnimations() {
	}
}
