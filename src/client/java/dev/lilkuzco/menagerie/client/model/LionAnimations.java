// Keyframe animations from Animal Garden - Lion 1.0.3 by aquarius_playz (public
// domain, The Unlicense — see CREDITS.md). The jar is a native Fabric 26.2 build with
// Mojang names, so only the animation-API import needed swapping to vanilla's.
package dev.lilkuzco.menagerie.client.model;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public final class LionAnimations {
	public static final AnimationDefinition wink = Builder.withLength(0.75F)
	   .addAnimation(
	      "rightEye",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.2F, 0.1F, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.2F, 0.1F, 1.0), Interpolations.CATMULLROM),
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
	      "leftEye",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.2F, 0.1F, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.2F, 0.1F, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
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
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.45F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.45F, 0.0F, 0.0F), Interpolations.CATMULLROM),
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
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.65F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.65F, 0.0F, 0.0F), Interpolations.CATMULLROM),
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
	            new Keyframe(0.375F, KeyframeAnimations.posVec(-0.65F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(-0.65F, 0.0F, 0.0F), Interpolations.CATMULLROM),
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
	            new Keyframe(0.375F, KeyframeAnimations.posVec(-0.45F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(-0.45F, 0.0F, 0.0F), Interpolations.CATMULLROM),
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
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.7F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, 0.7F, 0.0F), Interpolations.CATMULLROM),
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
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.7F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, 0.7F, 0.0F), Interpolations.CATMULLROM),
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
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -0.55F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, -0.55F, 0.0F), Interpolations.CATMULLROM),
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
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -0.55F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, -0.55F, 0.0F), Interpolations.CATMULLROM),
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
	public static final AnimationDefinition rightEar = Builder.withLength(3.0F)
	   .addAnimation(
	      "rightEar",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition leftEar = Builder.withLength(3.0F)
	   .addAnimation(
	      "leftEar",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition rightEar2 = Builder.withLength(3.0F)
	   .addAnimation(
	      "rightEar",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 35.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 27.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 37.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 32.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition leftEar2 = Builder.withLength(3.0F)
	   .addAnimation(
	      "leftEar",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -27.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, -37.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, -32.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition tail = Builder.withLength(4.0F)
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(25.6867F, -22.9193F, 4.3737F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(20.46F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(25.6867F, 22.9193F, -4.3737F), Interpolations.CATMULLROM),
	            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail4",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail5",
	      new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)})
	   )
	   .build();
	public static final AnimationDefinition breathing = Builder.withLength(3.25F)
	   .looping()
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.degreeVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.scaleVec(1.025F, 1.1F, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.scaleVec(0.965F, 0.985F, 1.0), Interpolations.CATMULLROM),
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
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.9167F, KeyframeAnimations.degreeVec(7.3932F, 2.6151F, -6.4049F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(2.8412F, -4.3977F, 8.158F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, 0.1F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, -0.1F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.7083F, KeyframeAnimations.degreeVec(5.0582F, 7.6534F, 0.9837F), Interpolations.CATMULLROM),
	            new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-2.5095F, -5.2178F, 8.0E-4F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.7083F, KeyframeAnimations.degreeVec(4.9744F, 2.6696F, 0.3251F), Interpolations.CATMULLROM),
	            new Keyframe(1.0833F, KeyframeAnimations.degreeVec(4.9905F, 0.2178F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-2.5095F, -5.2178F, 8.0E-4F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail4",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.875F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail5",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5833F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
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
	public static final AnimationDefinition panting = Builder.withLength(4.5F)
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.75F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.5F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.75F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(4.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(4.25F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(4.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.05F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(4.25F, KeyframeAnimations.posVec(0.0F, -0.05F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(4.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition eat = Builder.withLength(3.0F)
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-0.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-0.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5833F, KeyframeAnimations.degreeVec(-0.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.8333F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-0.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.3333F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.75F, KeyframeAnimations.degreeVec(-0.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "headMane",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.625F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.875F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.625F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.875F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.125F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.375F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.7083F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition headLookMoreLeft = Builder.withLength(3.0F)
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
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "headMane",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition headLookMoreRight = Builder.withLength(3.0F)
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
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "headMane",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition headLookMoreUp = Builder.withLength(3.0F)
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
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.5F, -1.5F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.5F, -1.5F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "headMane",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition headLookMoreDown = Builder.withLength(3.0F)
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "headMane",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition headTiltMoreLeft = Builder.withLength(3.5F)
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
	   .addAnimation(
	      "headMane",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition headTiltMoreRight = Builder.withLength(3.5F)
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
	   .addAnimation(
	      "headMane",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
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
	            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-24.7676F, -2.1539F, -12.3159F), Interpolations.CATMULLROM),
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
	            new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
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
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(75.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, -0.3F, -0.9F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.25F, -0.55F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.1F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.1F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "headMane",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5833F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition bite2 = Builder.withLength(0.9167F)
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-19.1431F, -5.9032F, -16.5037F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-4.7197F, -3.4049F, -9.408F), Interpolations.CATMULLROM),
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
	            new Keyframe(0.1667F, KeyframeAnimations.posVec(-0.03F, 0.0F, 0.67F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.5F, 0.0F, -2.25F), Interpolations.CATMULLROM),
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
	            new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.1F, 1.0, 1.1F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "mouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "mouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "mouth",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.25, 1.1F, 1.5), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.25, 1.1F, 1.5), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(105.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, -0.5F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.2F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "backOfTheMouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.07F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.23F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "headMane",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition bite3 = Builder.withLength(1.25F)
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(2.4786F, 0.3262F, -7.4929F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -2.0F, 4.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 6.0F, -5.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.75F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.75F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-19.1431F, -5.9032F, -16.5037F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(17.7803F, -3.4049F, -9.408F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.1F, 1.0, 1.1F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "mouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "mouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "mouth",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.25, 1.1F, 1.4F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.25, 1.1F, 1.1F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(105.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.5F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.2F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "backOfTheMouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "headMane",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.625F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.875F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(19.3701F, -5.0785F, 14.1327F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(4.8304F, -1.2926F, 14.9455F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.5F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(3.7173F, 7.7999F, -4.4199F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.5F, -1.25F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -1.25F, -1.25F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.5F, -1.25F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(20.4941F, -12.5888F, -1.997F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -1.66F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(32.21F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-4.07F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition makeSound1 = Builder.withLength(1.2917F)
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(36.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.9167F, KeyframeAnimations.degreeVec(36.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition walk = Builder.withLength(1.5F)
	   .looping()
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "body",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -4.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 4.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "body",
	      new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM)})
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightEar",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftEar",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "headMane",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(-0.25F, 0.5F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(-0.25F, 2.5F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(-0.25F, 2.5F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(-0.25F, 0.5F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(-0.5F, -0.22F, -0.41F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(-0.25F, -0.03F, 0.33F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(-0.25F, 0.5F, 1.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-51.3F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-16.8F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-33.59F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(68.12F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(26.55F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(20.83F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.1F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.5F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.5F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 2.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 2.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -0.1F, -2.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-16.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.25F, 0.5F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.25F, -0.22F, -0.41F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.25F, -0.03F, 0.33F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.25F, 0.5F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.25F, 2.5F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.25F, 2.5F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.25F, 0.5F, -1.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-16.8F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-33.59F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-51.3F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(26.55F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.83F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(68.12F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.5F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 2.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 2.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.1F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.5F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.5F, 1.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-16.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(16.67F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(16.67F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-11.7507F, -4.5537F, -11.9149F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-0.72F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(-11.7507F, 4.5537F, 11.9149F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.25F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.25F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition run = Builder.withLength(2.25F)
	   .looping()
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 4.0F, -3.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.5F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.67F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.5F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 4.0F, -3.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "headMane",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(75.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -1.5F, 1.5F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-80.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-87.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(-80.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg2",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(155.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(175.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(115.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.5F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.25F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, -0.75F, 0.5F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.75F, -0.75F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, -0.5F, -1.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -1.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.25F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.5F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, -1.0F, -1.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 5.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(72.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -1.5F, 1.5F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-82.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(-82.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(-82.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg2",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(120.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(72.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(142.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(165.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(120.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.75F, -1.25F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.25F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -0.25F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -0.75F, -0.25F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, -1.5F, 0.25F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, -0.75F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(1.67F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(80.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.75F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, -0.5F, -0.75F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.75F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail4",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.75F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail5",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.2F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(3.2F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition swimming = Builder.withLength(1.5F)
	   .looping()
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(-0.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "body",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -4.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 4.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "body",
	      new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.CATMULLROM)})
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-97.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-97.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(80.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(157.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(157.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(80.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(80.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.posVec(-0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(120.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(120.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(120.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-97.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(-97.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-97.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(157.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(80.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(80.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(157.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(157.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.posVec(0.25F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.375F, KeyframeAnimations.degreeVec(120.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(120.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.125F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition fromStandingToSitting = Builder.withLength(0.5F)
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.25F, -1.5F, 3.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(12.5462F, 4.8812F, 1.0848F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.25F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-40.2506F, 14.9833F, 16.9822F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(87.5592F, 0.5409F, 12.4885F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(-0.25F, -1.5F, 3.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(12.7936F, -12.1991F, -2.7471F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 2.0F, -2.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-32.1038F, -5.3535F, -8.4586F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(75.2178F, -2.5759F, -9.6658F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.25F, -1.0F, -1.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(95.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition sittingLoop = Builder.withLength(1.0F)
	   .looping()
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.25F, -1.5F, 3.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.25F, -1.5F, 3.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5462F, 4.8812F, 1.0848F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.5462F, 4.8812F, 1.0848F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.25F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.25F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-40.2506F, 14.9833F, 16.9822F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-40.2506F, 14.9833F, 16.9822F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(87.5592F, 0.5409F, 12.4885F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(87.5592F, 0.5409F, 12.4885F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(-0.25F, -1.5F, 3.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(-0.25F, -1.5F, 3.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.7936F, -12.1991F, -2.7471F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.7936F, -12.1991F, -2.7471F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 2.0F, -2.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-32.1038F, -5.3535F, -8.4586F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-32.1038F, -5.3535F, -8.4586F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(75.2178F, -2.5759F, -9.6658F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(75.2178F, -2.5759F, -9.6658F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.25F, -1.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.25F, -1.0F, -1.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(95.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(95.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition fromSittingToStanding = Builder.withLength(0.5F)
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.25F, -1.5F, 3.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5462F, 4.8812F, 1.0848F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.25F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-40.2506F, 14.9833F, 16.9822F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(87.5592F, 0.5409F, 12.4885F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(-0.25F, -1.5F, 3.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.7936F, -12.1991F, -2.7471F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.0F, -2.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-32.1038F, -5.3535F, -8.4586F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(75.2178F, -2.5759F, -9.6658F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.25F, -1.0F, -1.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(95.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition fromStandingToSleep = Builder.withLength(1.0F)
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -6.45F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -2.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.5F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-50.0316F, 8.9582F, 0.9921F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 1.23F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.05F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-42.0765F, 33.1208F, 1.2562F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(-2.0F, 2.0F, -2.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(85.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.75F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-32.8761F, -15.8806F, -1.59F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 1.43F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.35F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-42.0765F, -33.1208F, -1.2562F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(2.0F, 2.0F, -2.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(85.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.75F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 5.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition sleepingLoop = Builder.withLength(1.0F)
	   .looping()
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -6.45F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -6.45F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.5F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-50.0316F, 8.9582F, 0.9921F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-50.0316F, 8.9582F, 0.9921F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.05F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.05F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-42.0765F, 33.1208F, 1.2562F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-42.0765F, 33.1208F, 1.2562F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(-2.0F, 2.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(-2.0F, 2.0F, -2.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(85.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(85.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.75F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.75F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-32.8761F, -15.8806F, -1.59F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-32.8761F, -15.8806F, -1.59F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.35F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.35F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-42.0765F, -33.1208F, -1.2562F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-42.0765F, -33.1208F, -1.2562F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(2.0F, 2.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(2.0F, 2.0F, -2.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(85.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(85.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.75F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.75F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition fromSleepingToStanding = Builder.withLength(1.0F)
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -6.45F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -6.45F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-50.0316F, 8.9582F, 0.9921F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-50.0316F, 8.9582F, 0.9921F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.05F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.05F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 1.48F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-42.0765F, 33.1208F, 1.2562F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(-2.0F, 2.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(-1.0F, 5.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(85.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.75F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-32.8761F, -15.8806F, -1.59F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-32.8761F, -15.8806F, -1.59F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.35F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.35F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 1.58F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-42.0765F, -33.1208F, -1.2562F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(2.0F, 2.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.25F, KeyframeAnimations.posVec(1.0F, 5.0F, -1.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(85.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.75F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 5.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition yawning = Builder.withLength(2.5F)
	   .addAnimation(
	      "rootz",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -6.45F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "head",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -1.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -0.33F, -1.66F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.91F, -0.61F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "mouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "mouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.3F), Interpolations.LINEAR),
	            new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.3F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "mouth",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
	            new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.2F), Interpolations.LINEAR),
	            new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.2F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(100.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(100.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "lowerMouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.24F, -0.64F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "backOfTheMouth",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), Interpolations.LINEAR),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "backOfTheMouth",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.1F, 0.2F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.15F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.15F), Interpolations.CATMULLROM),
	            new Keyframe(2.375F, KeyframeAnimations.posVec(0.0F, 0.1F, -0.08F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "backOfTheMouth",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR),
	            new Keyframe(0.2917F, KeyframeAnimations.scaleVec(1.0, 0.9F, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.5), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.7F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.scaleVec(1.0, 1.0, 0.7F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.LINEAR)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-50.0316F, 8.9582F, 0.9921F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 1.41F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.98F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.05F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.9F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 1.11F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "rightHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-32.8761F, -15.8806F, -1.59F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 1.52F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.18F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.35F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 1.15F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 1.13F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftFrontLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -1.0F, -0.5F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLeg2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "leftHindLegPaw",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 5.0F, -2.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();
	public static final AnimationDefinition breathing2 = Builder.withLength(3.25F)
	   .looping()
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.degreeVec(-1.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "realbody",
	      new AnimationChannel(
	         Targets.SCALE,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.scaleVec(1.0, 1.05F, 1.0), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.scaleVec(1.0, 0.985F, 1.0), Interpolations.CATMULLROM),
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
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.9167F, KeyframeAnimations.degreeVec(7.3932F, 2.6151F, -6.4049F), Interpolations.CATMULLROM),
	            new Keyframe(2.5F, KeyframeAnimations.degreeVec(2.8412F, -4.3977F, 8.158F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail",
	      new AnimationChannel(
	         Targets.POSITION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, 0.1F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.posVec(0.0F, -0.1F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail2",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.7083F, KeyframeAnimations.degreeVec(5.0582F, 7.6534F, 0.9837F), Interpolations.CATMULLROM),
	            new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-2.5095F, -5.2178F, 8.0E-4F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail3",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.7083F, KeyframeAnimations.degreeVec(4.9744F, 2.6696F, 0.3251F), Interpolations.CATMULLROM),
	            new Keyframe(1.0833F, KeyframeAnimations.degreeVec(4.9905F, 0.2178F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-2.5095F, -5.2178F, 8.0E-4F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail4",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.875F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .addAnimation(
	      "tail5",
	      new AnimationChannel(
	         Targets.ROTATION,
	         new Keyframe[]{
	            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(2.5833F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), Interpolations.CATMULLROM),
	            new Keyframe(3.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
	         }
	      )
	   )
	   .build();

	private LionAnimations() {
	}
}
