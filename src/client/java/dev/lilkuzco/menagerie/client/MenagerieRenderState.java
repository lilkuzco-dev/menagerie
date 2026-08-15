package dev.lilkuzco.menagerie.client;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

/** Shared render state for all Menagerie animals; unused fields stay at defaults. */
public class MenagerieRenderState extends LivingEntityRenderState {
	public Identifier texture = Identifier.withDefaultNamespace("textures/entity/pig/temperate_pig.png");
	// gorilla
	public float beatTicks;
	public float eatTicks;
	public boolean silverback;
	public boolean sittingPose;
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
