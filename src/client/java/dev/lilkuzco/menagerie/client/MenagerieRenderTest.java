package dev.lilkuzco.menagerie.client;

import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;

/**
 * In-camera render regression test (the check that bit Warfront): summons the whole
 * roster in front of the player and screenshots it. Runs only under
 * ./gradlew runGametest (-Dfabric.client.gametest) — never in normal play.
 */
public class MenagerieRenderTest implements FabricClientGameTest {
	@Override
	public void runTest(ClientGameTestContext context) {
		try (TestSingleplayerContext world = context.worldBuilder().create()) {
			context.waitTicks(80);
			var server = world.getServer();
			server.runCommand("time set noon");
			server.runCommand("gamemode creative @p");
			// stand the player on a stone stage with the roster lined up to the south
			server.runCommand("execute at @p run fill ~-8 ~-1 ~-2 ~8 ~-1 ~12 minecraft:stone");
			server.runCommand("execute at @p run fill ~-8 ~ ~-2 ~8 ~6 ~12 minecraft:air");
			server.runCommand("execute at @p run tp @p ~ ~ ~ 0 15");
			server.runCommand("execute at @p run summon menagerie:gorilla ~-5 ~ ~7 {NoAI:1b}");
			server.runCommand("execute at @p run summon menagerie:gorilla ~-2.5 ~ ~7 {NoAI:1b,menagerie_silverback:1b}");
			server.runCommand("execute at @p run summon menagerie:gorilla ~-4 ~ ~4 {NoAI:1b,Age:-24000}");
			server.runCommand("execute at @p run summon menagerie:crocodile ~0.5 ~ ~7 {NoAI:1b}");
			server.runCommand("execute at @p run summon menagerie:tortoise ~3 ~ ~7 {NoAI:1b}");
			server.runCommand("execute at @p run summon menagerie:leopard ~5 ~ ~7 {NoAI:1b}");
			context.waitTicks(60);
			context.takeScreenshot("menagerie_lineup");
			// clear the stage, then the Phase 2 row on its own
			server.runCommand("kill @e[type=!minecraft:player]");
			server.runCommand("execute at @p run summon menagerie:hippo ~-4.5 ~ ~8 {NoAI:1b}");
			server.runCommand("execute at @p run summon menagerie:grizzly ~-1 ~ ~7 {NoAI:1b}");
			server.runCommand("execute at @p run summon menagerie:vulture ~2 ~ ~6 {NoAI:1b,menagerie_flying:0b}");
			server.runCommand("execute at @p run summon menagerie:snake ~4.5 ~ ~5 {NoAI:1b}");
			context.waitTicks(60);
			context.takeScreenshot("menagerie_lineup_2");

			// gorilla showcase: fur variants side by side, then the silverback overlay.
			// NoAI keeps them posed where they are put; rotation is applied per-entity
			// (a plain `tp @e ~ ~ ~` inside `execute at @p` would stack them on the camera).
			server.runCommand("kill @e[type=!minecraft:player]");
			server.runCommand("execute at @p run tp @p ~ ~ ~ 0 0");
			for (int i = 0; i < 6; i++) {
				server.runCommand("execute at @p run summon menagerie:gorilla ~"
						+ (i * 1.9 - 4.75) + " ~ ~6 {NoAI:1b}");
			}
			context.waitTicks(30);
			server.runCommand("execute as @e[type=menagerie:gorilla] at @s run tp @s ~ ~ ~ 180 0");
			context.waitTicks(20);
			context.takeScreenshot("gorilla_lowland_furs");
			// log which fur each one drew — the screenshot cannot prove that on its own
			server.runCommand("execute at @p run menagerie census 20");

			// CONTROL: two plain adults from behind. If a light saddle shows here too,
			// it is the base texture, not the silverback overlay.
			server.runCommand("kill @e[type=menagerie:gorilla]");
			server.runCommand("execute at @p run summon menagerie:gorilla ~-1.6 ~ ~4.5 {NoAI:1b}");
			server.runCommand("execute at @p run summon menagerie:gorilla ~1.6 ~ ~4.5 {NoAI:1b}");
			context.waitTicks(30);
			server.runCommand("execute as @e[type=menagerie:gorilla] at @s run tp @s ~ ~ ~ 0 0");
			context.waitTicks(20);
			context.takeScreenshot("gorilla_control_no_silverback_back");

			server.runCommand("kill @e[type=menagerie:gorilla]");
			server.runCommand("execute at @p run summon menagerie:gorilla ~-1.6 ~ ~4.5");
			server.runCommand("execute at @p run summon menagerie:gorilla ~1.6 ~ ~4.5");
			context.waitTicks(30);
			// a plain /summon makes each gorilla the silverback of its own new troop, so
			// demote the left one to get one of each standing side by side
			server.runCommand("execute at @p positioned ~-1.6 ~ ~4.5 run data merge entity "
					+ "@e[type=menagerie:gorilla,limit=1,sort=nearest] {menagerie_silverback:0b}");
			server.runCommand("execute at @p positioned ~1.6 ~ ~4.5 run menagerie silverback");
			server.runCommand("execute as @e[type=menagerie:gorilla] at @s run data merge entity @s {NoAI:1b}");
			server.runCommand("execute as @e[type=menagerie:gorilla] at @s run tp @s ~ ~ ~ 180 0");
			context.waitTicks(30);
			context.takeScreenshot("gorilla_silverback");
			// the saddle sits on the torso UV, so the telling view is from behind
			server.runCommand("execute as @e[type=menagerie:gorilla] at @s run tp @s ~ ~ ~ 0 0");
			context.waitTicks(20);
			context.takeScreenshot("gorilla_silverback_back");

			// Saddle A/B, fully pinned: NBT summon skips finalizeSpawn, so species and the
			// silverback flag are both set explicitly and NoAI stops them wandering out of
			// frame. Left = plain adult, right = silverback, seen from behind.
			server.runCommand("kill @e[type=menagerie:gorilla]");
			server.runCommand("execute at @p run summon menagerie:gorilla ~-1.6 ~ ~4.5 "
					+ "{NoAI:1b,Rotation:[0f,0f],menagerie_species:\"lowland\",menagerie_silverback:0b}");
			server.runCommand("execute at @p run summon menagerie:gorilla ~1.6 ~ ~4.5 "
					+ "{NoAI:1b,Rotation:[0f,0f],menagerie_species:\"lowland\",menagerie_silverback:1b}");
			context.waitTicks(30);
			context.takeScreenshot("gorilla_saddle_ab_back");

			// Lion: fur variants plus the maned pride leader (mane part renders only for him)
			server.runCommand("kill @e[type=!minecraft:player]");
			for (int i = 0; i < 4; i++) {
				server.runCommand("execute at @p run summon menagerie:lion ~" + (i * 2.6 - 3.9)
						+ " ~ ~7 {NoAI:1b,Rotation:[180f,0f],menagerie_species:\"savanna\","
						+ "menagerie_maned:" + (i == 1 ? "1b" : "0b") + "}");
			}
			context.waitTicks(40);
			context.takeScreenshot("lion_pride");

			// Albino gorilla: the variant roll forced for the shot, beside a normal adult
			server.runCommand("kill @e[type=!minecraft:player]");
			server.runCommand("execute at @p run summon menagerie:gorilla ~-1.8 ~ ~5 "
					+ "{NoAI:1b,Rotation:[180f,0f],menagerie_species:\"lowland\"}");
			server.runCommand("execute at @p run summon menagerie:gorilla ~1.8 ~ ~5 "
					+ "{NoAI:1b,Rotation:[180f,0f],menagerie_species:\"lowland\",menagerie_variant:\"albino\"}");
			context.waitTicks(40);
			context.takeScreenshot("gorilla_albino");

			// Chest-beat needs a LIVE silverback: NoAI skips customServerAiStep entirely,
			// so these are plain summons (each becomes silverback of its own troop) with a
			// hostile parked inside the detect radius to trigger the pump.
			server.runCommand("kill @e[type=!minecraft:player]");
			server.runCommand("execute at @p run summon menagerie:gorilla ~ ~ ~4.5");
			server.runCommand("execute at @p run summon minecraft:zombie ~3 ~ ~5 "
					+ "{NoAI:1b,Silent:1b,ActiveEffects:[{Id:12,Duration:100000,Amplifier:0b}]}");
			context.waitTicks(50);
			context.takeScreenshot("gorilla_chest_beat_a");
			context.waitTicks(10);
			context.takeScreenshot("gorilla_chest_beat_b");
			context.waitTicks(10);
			context.takeScreenshot("gorilla_chest_beat_c");
			// Field Guide: the nearby row already triggered discovery pings; open it
			server.runCommand("give @p menagerie:field_guide");
			context.waitTicks(20);
			context.getInput().pressKey(options -> options.keyUse);
			context.waitTicks(20);
			context.takeScreenshot("menagerie_guide");
		}
	}
}
