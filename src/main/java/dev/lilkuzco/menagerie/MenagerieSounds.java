package dev.lilkuzco.menagerie;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

/**
 * All sound EVENTS are ours; every actual sound FILE is a pitch-shifted reference to a
 * vanilla event via assets/menagerie/sounds.json (doctrine: no external audio).
 */
public final class MenagerieSounds {
	public static final SoundEvent GORILLA_AMBIENT = register("entity.gorilla.ambient");
	public static final SoundEvent GORILLA_HURT = register("entity.gorilla.hurt");
	public static final SoundEvent GORILLA_DEATH = register("entity.gorilla.death");
	public static final SoundEvent GORILLA_CHEST_BEAT = register("entity.gorilla.chest_beat");
	public static final SoundEvent GORILLA_EAT = register("entity.gorilla.eat");

	public static final SoundEvent CROCODILE_AMBIENT = register("entity.crocodile.ambient");
	public static final SoundEvent CROCODILE_HURT = register("entity.crocodile.hurt");
	public static final SoundEvent CROCODILE_DEATH = register("entity.crocodile.death");
	public static final SoundEvent CROCODILE_SNAP = register("entity.crocodile.snap");

	public static final SoundEvent TORTOISE_AMBIENT = register("entity.tortoise.ambient");
	public static final SoundEvent TORTOISE_HURT = register("entity.tortoise.hurt");
	public static final SoundEvent TORTOISE_DEATH = register("entity.tortoise.death");
	public static final SoundEvent TORTOISE_RETRACT = register("entity.tortoise.retract");

	public static final SoundEvent LEOPARD_AMBIENT = register("entity.leopard.ambient");
	public static final SoundEvent LEOPARD_HURT = register("entity.leopard.hurt");
	public static final SoundEvent LEOPARD_DEATH = register("entity.leopard.death");
	public static final SoundEvent LEOPARD_POUNCE = register("entity.leopard.pounce");

	private static SoundEvent register(String name) {
		Identifier id = Menagerie.id(name);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	public static void init() {
	}

	private MenagerieSounds() {
	}
}
