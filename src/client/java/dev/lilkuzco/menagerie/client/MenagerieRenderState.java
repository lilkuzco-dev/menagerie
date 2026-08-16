package dev.lilkuzco.menagerie.client;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AnimationState;

/** Shared render state for all Menagerie animals; unused fields stay at defaults. */
public class MenagerieRenderState extends LivingEntityRenderState {
	public Identifier texture = Identifier.withDefaultNamespace("textures/entity/pig/temperate_pig.png");
	// gorilla — keyframe animation states, copied from the entity each frame
	public boolean silverback;
	public boolean sittingPose;
	public final AnimationState gorillaBreathing = new AnimationState();
	public final AnimationState gorillaChestPump = new AnimationState();
	public final AnimationState gorillaEat = new AnimationState();
	public final AnimationState gorillaPunch = new AnimationState();
	public final AnimationState gorillaWink = new AnimationState();
	public final AnimationState gorillaSniff = new AnimationState();
	public final AnimationState gorillaSound = new AnimationState();
	public final AnimationState gorillaSitStart = new AnimationState();
	public final AnimationState gorillaSitLoop = new AnimationState();
	public final AnimationState gorillaSitEnd = new AnimationState();
	// lion
	public int lionLeftEye;
	public int lionRightEye;
	public final AnimationState lionBreathing = new AnimationState();
	public final AnimationState lionTail = new AnimationState();
	public final AnimationState lionEar = new AnimationState();
	public final AnimationState lionWink = new AnimationState();
	public final AnimationState lionSniff = new AnimationState();
	public final AnimationState lionYawn = new AnimationState();
	public final AnimationState lionBite = new AnimationState();
	public final AnimationState lionRoar = new AnimationState();
	public final AnimationState lionSleepStart = new AnimationState();
	public final AnimationState lionSleepLoop = new AnimationState();
	public final AnimationState lionSleepEnd = new AnimationState();
	// crocodile
	public float lungeTicks;
	// tortoise
	public boolean shelled;
	// leopard
	public boolean crouching;
	public float pounceTicks;
	// hippo
	public float yawnTicks;
	// grizzly
	public float swipeTicks;
	public boolean bearSleeping;
	// vulture
	public boolean flying;
	// snake
	public boolean coiled;
	public boolean rattling;
	public float strikeTicks;
}
