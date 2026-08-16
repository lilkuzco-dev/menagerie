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

	public static final SoundEvent LION_AMBIENT = register("entity.lion.ambient");
	public static final SoundEvent LION_HURT = register("entity.lion.hurt");
	public static final SoundEvent LION_DEATH = register("entity.lion.death");
	public static final SoundEvent LION_ROAR = register("entity.lion.roar");

	public static final SoundEvent HIPPO_AMBIENT = register("entity.hippo.ambient");
	public static final SoundEvent HIPPO_HURT = register("entity.hippo.hurt");
	public static final SoundEvent HIPPO_DEATH = register("entity.hippo.death");
	public static final SoundEvent HIPPO_WARN = register("entity.hippo.warn");
	public static final SoundEvent HIPPO_ATTACK = register("entity.hippo.attack");

	public static final SoundEvent GRIZZLY_AMBIENT = register("entity.grizzly.ambient");
	public static final SoundEvent GRIZZLY_HURT = register("entity.grizzly.hurt");
	public static final SoundEvent GRIZZLY_DEATH = register("entity.grizzly.death");
	public static final SoundEvent GRIZZLY_SWIPE = register("entity.grizzly.swipe");
	public static final SoundEvent GRIZZLY_EAT = register("entity.grizzly.eat");

	public static final SoundEvent VULTURE_AMBIENT = register("entity.vulture.ambient");
	public static final SoundEvent VULTURE_HURT = register("entity.vulture.hurt");
	public static final SoundEvent VULTURE_DEATH = register("entity.vulture.death");
	public static final SoundEvent VULTURE_SWOOP = register("entity.vulture.swoop");
	public static final SoundEvent VULTURE_EAT = register("entity.vulture.eat");

	public static final SoundEvent SNAKE_RATTLE = register("entity.snake.rattle");
	public static final SoundEvent SNAKE_STRIKE = register("entity.snake.strike");
	public static final SoundEvent SNAKE_HURT = register("entity.snake.hurt");
	public static final SoundEvent SNAKE_DEATH = register("entity.snake.death");

	private static SoundEvent register(String name) {
		Identifier id = Menagerie.id(name);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	public static void init() {
	}

	private MenagerieSounds() {
	}
}
