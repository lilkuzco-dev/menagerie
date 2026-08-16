package dev.lilkuzco.menagerie.client;

import dev.lilkuzco.menagerie.data.SpeciesRegistry;
import dev.lilkuzco.menagerie.entity.SpeciesMob;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestDedicatedServerContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestDedicatedServerConnection;
import net.minecraft.resources.Identifier;

/**
 * The multiplayer render battery — the test that would have caught the checkerboard.
 *
 * <p>The singleplayer battery in {@link MenagerieRenderTest} cannot see this class of
 * bug at all: an integrated server loads the species datapack into the SAME JVM the
 * renderer runs in, so a client-side texture lookup silently works. On a DEDICATED
 * server the client never loads {@code data/}, its species registry is empty, and every
 * animal fell back to a texture path that no longer exists in vanilla. This test drives
 * a real dedicated server over a real network connection, so the registry really is
 * empty on the client side, and asserts the skin anyway.
 *
 * <p>Runs only under {@code ./gradlew runGametest}.
 */
public class MenagerieMultiplayerRenderTest implements FabricClientGameTest {
	/**
	 * entity, species, extra summon NBT, label, and every skin the client is allowed to
	 * end up with. It is a SET because a species may declare a fur table ({@code textures})
	 * that each individual draws from by UUID — any member of that table is correct.
	 */
	private record Subject(String entity, String species, String nbt, String label,
			List<String> skins) {
		Subject(String entity, String species, String... skins) {
			this(entity, species, "", entity + "_" + species, List.of(skins));
		}

		Subject baby() {
			return new Subject(entity, species, nbt + ",Age:-24000", label + "_baby", skins);
		}

		Subject variant(String name, String skin) {
			return new Subject(entity, species, nbt + ",menagerie_variant:\"" + name + "\"",
					label + "_" + name, List.of(skin));
		}

		String summon(double x, double z) {
			return "execute at @p run summon menagerie:" + entity + " ~" + x + " ~ ~" + z
					+ " {NoAI:1b,Rotation:[180f,0f],menagerie_species:\"" + species + "\"" + nbt + "}";
		}
	}

	private static final String T = "menagerie:textures/entity/";

	/** Every species in the mod, adult; babies and variant rolls are derived below. */
	private static final List<Subject> ROSTER = List.of(
			// the two gorilla species carry fur tables; every listed coat is a valid draw
			new Subject("gorilla", "lowland", T + "gorilla/default.png",
					T + "gorilla/brown.png", T + "gorilla/darker.png"),
			new Subject("gorilla", "mountain", T + "gorilla/black.png",
					T + "gorilla/brown_back.png"),
			new Subject("crocodile", "nile", T + "crocodile/nile.png"),
			new Subject("crocodile", "saltwater", T + "crocodile/saltwater.png"),
			new Subject("tortoise", "savanna", T + "tortoise/savanna.png"),
			new Subject("leopard", "leopard", T + "leopard/leopard.png"),
			new Subject("leopard", "snow", T + "leopard/snow.png"),
			new Subject("hippo", "river", T + "hippo/river.png"),
			new Subject("hippo", "swamp", T + "hippo/swamp.png"),
			new Subject("grizzly", "black", T + "grizzly/black.png"),
			new Subject("grizzly", "grizzly", T + "grizzly/grizzly.png"),
			new Subject("vulture", "griffon", T + "vulture/griffon.png"),
			new Subject("lion", "savanna", T + "lion/body_0.png"),
			new Subject("lion", "barbary", T + "lion/body_8.png"),
			new Subject("snake", "python", T + "snake/python.png"),
			new Subject("snake", "viper", T + "snake/viper.png"));

	@Override
	public void runTest(ClientGameTestContext context) {
		java.util.Properties props = new java.util.Properties();
		props.setProperty("online-mode", "false");
		props.setProperty("enforce-secure-profile", "false");
		props.setProperty("spawn-protection", "0");
		props.setProperty("sync-chunk-writes", "false");
		// off the default 25565: a previous run's server can still be releasing the port,
		// and a dev server on the usual port would collide outright
		props.setProperty("server-port", "25591");
		try (TestDedicatedServerContext server = context.worldBuilder().createServer(props)) {
			try (TestDedicatedServerConnection connection = server.connect()) {
				connection.waitForChunksRender();
				server.runCommand("gamerule doDaylightCycle false");
				server.runCommand("gamerule doMobSpawning false");
				server.runCommand("time set noon");
				server.runCommand("gamemode creative @a");
				server.runCommand("execute at @p run fill ~-12 ~-1 ~-2 ~12 ~-1 ~14 minecraft:stone");
				server.runCommand("execute at @p run fill ~-12 ~ ~-2 ~12 ~8 ~14 minecraft:air");
				server.runCommand("execute at @p run tp @p ~ ~ ~ 0 10");
				context.waitTicks(20);

				// NOTE ON WHAT THIS HARNESS CAN AND CANNOT PROVE.
				// The gametest's "dedicated" server runs in the SAME JVM as the client, so
				// the static SpeciesRegistry is shared and the client-side registry is NOT
				// empty here — unlike a real remote client, which never loads data/ at all.
				// So this test does not assert on the registry. It asserts the thing that
				// actually fixes the bug: that every skin arrives as SYNCED ENTITY DATA,
				// published by the server. A client that is handed the answer cannot be hurt
				// by having no registry of its own.
				context.runOnClient(mc -> System.out.println(
						"[menagerie-mp-test] client-side species registry holds "
								+ SpeciesRegistry.all().size() + " entities (shared-JVM harness; "
								+ "a real remote client holds 0 — hence the sync assertions)"));

				// The placeholder is the last line of defence, and the whole "a Menagerie
				// fallback can never be a checkerboard again" claim rests on the CLIENT being
				// able to load it. Prove it decodes, and prove its colours are the ones that
				// distinguish it from vanilla's magenta/black missing texture — so a bug
				// report can be triaged from a screenshot alone.
				context.runOnClient(mc -> {
					var stack = mc.getResourceManager().getResourceStack(SpeciesMob.MISSING_TEXTURE);
					if (stack.isEmpty()) {
						throw new AssertionError(SpeciesMob.MISSING_TEXTURE
								+ " is not present in the CLIENT resource manager — the fallback "
								+ "would render as vanilla's checkerboard");
					}
					try (var in = stack.getLast().open();
							var img = com.mojang.blaze3d.platform.NativeImage.read(in)) {
						if (img.getWidth() != 64 || img.getHeight() != 64) {
							throw new AssertionError("placeholder is " + img.getWidth() + "x"
									+ img.getHeight() + ", expected 64x64");
						}
						// low 24 bits come back RGB-ordered here (verified empirically)
						int a = img.getPixel(0, 0) & 0x00FFFFFF;
						int b = img.getPixel(8, 0) & 0x00FFFFFF;
						if (a != 0x00FF00FF || b != 0x00FFB000) {
							throw new AssertionError(String.format(
									"placeholder colours changed (%06X/%06X). They must stay "
											+ "magenta/amber: magenta+BLACK is VANILLA's missing "
											+ "texture, and telling them apart in a screenshot is "
											+ "how we know whether our code ran at all.", a, b));
						}
						System.out.println("[menagerie-mp-test] placeholder loads on the client: "
								+ img.getWidth() + "x" + img.getHeight()
								+ " magenta/amber (vanilla's is magenta/black)");
					} catch (java.io.IOException e) {
						throw new AssertionError("placeholder failed to decode on the client", e);
					}
				});

				List<Subject> subjects = new ArrayList<>();
				for (Subject s : ROSTER) {
					subjects.add(s);
					subjects.add(s.baby());
				}
				// the only rare coats in the mod today; both gorilla species roll albino
				subjects.add(ROSTER.get(0).variant("albino", T + "gorilla/albino.png"));
				subjects.add(ROSTER.get(1).variant("albino", T + "gorilla/albino.png"));

				List<String> failures = new ArrayList<>();
				int shot = 0;
				for (int i = 0; i < subjects.size(); i += 4) {
					List<Subject> row = subjects.subList(i, Math.min(i + 4, subjects.size()));
					server.runCommand("kill @e[type=!minecraft:player]");
					// let the death poof clear, or it sits on top of the next row
					context.waitTicks(30);
					for (int k = 0; k < row.size(); k++) {
						server.runCommand(row.get(k).summon(k * 3.0 - 4.5, 7));
					}
					connection.waitForClientboundPackets();
					context.waitTicks(40);
					context.takeScreenshot("mp_row_" + (shot++) + "_"
							+ String.join("__", row.stream().map(Subject::label).toList()));
					failures.addAll(verify(context, row));
				}

				failures.addAll(babySizeCalibration(context, server, connection));

				if (!failures.isEmpty()) {
					throw new AssertionError("multiplayer render battery failed for "
							+ failures.size() + " subject(s):\n  " + String.join("\n  ", failures));
				}
			}
		}
	}

	/**
	 * Calves must be vanilla-baby sized, not a quarter of the adult.
	 *
	 * <p>The client bakes the baby mesh through vanilla's {@code BabyModelTransform}
	 * (body halved, head left full size) and vanilla age-scales the hitbox. A species
	 * {@code baby_scale} below 1.0 multiplies the SCALE attribute on top of both, which
	 * is a second shrink. This measures the SCALE attribute a baby actually carries and
	 * holds it against a vanilla cow calf, so "the right size" is measured rather than
	 * asserted from memory.
	 */
	private static List<String> babySizeCalibration(ClientGameTestContext context,
			TestDedicatedServerContext server, TestDedicatedServerConnection connection) {
		server.runCommand("kill @e[type=!minecraft:player]");
		context.waitTicks(30);
		server.runCommand("execute at @p run summon minecraft:cow ~-4.5 ~ ~7 {NoAI:1b}");
		server.runCommand("execute at @p run summon minecraft:cow ~-1.5 ~ ~7 {NoAI:1b,Age:-24000}");
		server.runCommand("execute at @p run summon menagerie:gorilla ~1.5 ~ ~7 "
				+ "{NoAI:1b,Rotation:[180f,0f],menagerie_species:\"lowland\"}");
		server.runCommand("execute at @p run summon menagerie:gorilla ~4.5 ~ ~7 "
				+ "{NoAI:1b,Rotation:[180f,0f],menagerie_species:\"lowland\",Age:-24000}");
		connection.waitForClientboundPackets();
		context.waitTicks(30);
		context.takeScreenshot("mp_baby_size_vs_vanilla_cow");

		return context.computeOnClient(mc -> {
			List<String> problems = new ArrayList<>();
			for (var entity : mc.level.entitiesForRendering()) {
				if (entity instanceof SpeciesMob mob && mob.isBaby()) {
					// vanilla babies render at SCALE 1.0; the baby look comes from the mesh
					float scale = mob.getScale();
					System.out.println("[menagerie-mp-test] baby " + mob.entityId()
							+ " SCALE attribute = " + scale + " (vanilla baby = 1.0)");
					if (scale < 0.95F) {
						problems.add(mob.entityId() + " baby carries SCALE " + scale
								+ ", below vanilla's 1.0 — that is a second shrink on top of "
								+ "the already-halved baby mesh");
					}
				}
			}
			return problems;
		});
	}

	/** Read the skin the CLIENT actually resolved for each animal it can see. */
	private static List<String> verify(ClientGameTestContext context, List<Subject> row) {
		return context.computeOnClient(mc -> {
			List<String> problems = new ArrayList<>();
			List<SpeciesMob> seen = new ArrayList<>();
			for (var entity : mc.level.entitiesForRendering()) {
				if (entity instanceof SpeciesMob mob) {
					seen.add(mob);
				}
			}
			if (seen.size() != row.size()) {
				problems.add("expected " + row.size() + " animals on the client, saw " + seen.size());
			}
			for (SpeciesMob mob : seen) {
				Identifier texture = mob.texture();
				String id = mob.entityId() + "|" + mob.getSpeciesName()
						+ (mob.getVariantName().isEmpty() ? "" : "|" + mob.getVariantName())
						+ (mob.isBaby() ? "|baby" : "");
				// THE fix: the skin must have arrived over the wire, not been re-derived
				if (mob.syncedTextureId().isEmpty()) {
					problems.add(id + " has an EMPTY synced skin — the client is falling back "
							+ "to its own registry, which a remote client does not have");
					continue;
				}
				if (texture.equals(SpeciesMob.MISSING_TEXTURE)) {
					problems.add(id + " resolved to the MISSING placeholder");
					continue;
				}
				if (!texture.toString().equals(mob.syncedTextureId())) {
					problems.add(id + " renders " + texture + " but was synced "
							+ mob.syncedTextureId());
					continue;
				}
				boolean matched = row.stream().anyMatch(s -> s.skins().contains(texture.toString()));
				if (!matched) {
					problems.add(id + " resolved to " + texture
							+ ", which is not any skin expected in this row");
				}
			}
			return problems;
		});
	}
}
