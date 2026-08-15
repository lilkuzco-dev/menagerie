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
		}
	}
}
