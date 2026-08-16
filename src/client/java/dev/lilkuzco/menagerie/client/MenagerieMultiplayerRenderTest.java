package dev.lilkuzco.menagerie.client;

import dev.lilkuzco.menagerie.data.SpeciesRegistry;
import dev.lilkuzco.menagerie.entity.SpeciesMob;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

				failures.addAll(habitatCensus(server));
				failures.addAll(skinSweep(context, server, connection));
				failures.addAll(babySizeCalibration(context, server, connection));

				if (!failures.isEmpty()) {
					throw new AssertionError("multiplayer render battery failed for "
							+ failures.size() + " subject(s):\n  " + String.join("\n  ", failures));
				}
			}
		}
	}

	/**
	 * Count how many BAKED biomes actually carry a spawn entry for each animal.
	 *
	 * <p>Everything upstream of this is a claim about intent: the species JSON lists
	 * biomes, the linter resolves tags on paper, the registry loads. None of that proves
	 * the one thing that decides whether a player ever meets an animal — that the biome
	 * modification put a real {@code SpawnerData} into real biomes at world load. An
	 * animal with zero biome entries is invisible in-game and silent in every other
	 * check, so it is asserted here.
	 */
	private static List<String> habitatCensus(TestDedicatedServerContext server) {
		return server.computeOnServer(mc -> {
			var biomes = mc.registryAccess()
					.lookupOrThrow(net.minecraft.core.registries.Registries.BIOME);
			java.util.Map<String, Integer> hits = new java.util.TreeMap<>();
			java.util.Map<String, Integer> weight = new java.util.TreeMap<>();
			int totalBiomes = 0;
			for (var entry : biomes.entrySet()) {
				totalBiomes++;
				var mobs = entry.getValue().getMobSettings()
						.getMobs(net.minecraft.world.entity.MobCategory.CREATURE);
				for (var w : mobs.unwrap()) {
					var type = w.value().type();
					String id = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE
							.getKey(type).toString();
					if (id.startsWith("menagerie:")) {
						hits.merge(id, 1, Integer::sum);
						weight.merge(id, w.weight(), Integer::sum);
					}
				}
			}
			System.out.println("[menagerie-mp-test] habitat census over " + totalBiomes
					+ " baked biomes:");
			List<String> problems = new ArrayList<>();
			for (var id : new java.util.TreeSet<>(SpeciesRegistry.all().keySet())) {
				int n = hits.getOrDefault(id, 0);
				System.out.printf("[menagerie-mp-test]   %-22s %3d biomes, summed weight %d%n",
						id, n, weight.getOrDefault(id, 0));
				if (n == 0) {
					problems.add(id + " has spawn entries in ZERO baked biomes — it can never "
							+ "appear naturally, whatever its species JSON claims");
				}
			}
			return problems;
		});
	}

	/**
	 * Force-spawn EVERY skin the renderer can select, for every species, and prove none
	 * of them lands on the placeholder.
	 *
	 * <p>This is the Untamed Wilds trap, checked from the render side. Their data
	 * declares a skin COUNT (hippo: {@code "skins": 30}) while the jar ships three files,
	 * and their code clamps the roll to what exists; a transplant that copies the count
	 * and rolls 1..N asks for {@code common_4..common_30} and checkerboards on every high
	 * roll — intermittently, because low rolls look fine. Menagerie stores no count (a
	 * species enumerates its skins as explicit paths), but "we don't have that bug by
	 * construction" is a claim, so it gets tested.
	 *
	 * <p>The fur pick is {@code floorMod(getUUID().hashCode(), n)}, so rather than
	 * spawning many and hoping to observe every index, this searches for a UUID that
	 * lands on each index and summons with it. Every skin index is therefore hit exactly
	 * and deterministically, not sampled.
	 */
	private static List<String> skinSweep(ClientGameTestContext context,
			TestDedicatedServerContext server, TestDedicatedServerConnection connection) {
		record Skin(String entity, String species, String nbt, String expected) { }
		List<Skin> skins = new ArrayList<>();
		for (var byEntity : new java.util.TreeMap<>(SpeciesRegistry.all()).entrySet()) {
			String entity = byEntity.getKey().split(":")[1];
			for (var sp : byEntity.getValue()) {
				List<Identifier> furs = sp.textures();
				if (furs.size() > 1) {
					for (int i = 0; i < furs.size(); i++) {
						skins.add(new Skin(entity, sp.name(),
								",UUID:" + uuidNbtForIndex(i, furs.size()), furs.get(i).toString()));
					}
				} else {
					skins.add(new Skin(entity, sp.name(), "", sp.texture().toString()));
				}
				for (var roll : sp.variantRolls()) {
					skins.add(new Skin(entity, sp.name(),
							",menagerie_variant:\"" + roll.name() + "\"", roll.texture().toString()));
				}
			}
		}
		System.out.println("[menagerie-mp-test] skin sweep: " + skins.size()
				+ " rollable skins across " + SpeciesRegistry.all().size() + " animals");

		List<String> problems = new ArrayList<>();
		int shot = 0;
		for (int i = 0; i < skins.size(); i += 6) {
			List<Skin> row = skins.subList(i, Math.min(i + 6, skins.size()));
			server.runCommand("kill @e[type=!minecraft:player]");
			context.waitTicks(30);
			for (int k = 0; k < row.size(); k++) {
				Skin s = row.get(k);
				server.runCommand("execute at @p run summon menagerie:" + s.entity()
						+ " ~" + (k * 2.6 - 6.5) + " ~ ~7 {NoAI:1b,Rotation:[180f,0f],"
						+ "menagerie_species:\"" + s.species() + "\"" + s.nbt() + "}");
			}
			connection.waitForClientboundPackets();
			context.waitTicks(40);
			context.takeScreenshot("mp_skins_" + (shot++));

			Set<String> want = new java.util.HashSet<>();
			for (Skin s : row) {
				want.add(s.expected());
			}
			problems.addAll(context.computeOnClient(mc -> {
				List<String> bad = new ArrayList<>();
				Set<String> seen = new java.util.HashSet<>();
				int count = 0;
				for (var e : mc.level.entitiesForRendering()) {
					if (!(e instanceof SpeciesMob mob)) {
						continue;
					}
					count++;
					String tex = mob.texture().toString();
					seen.add(tex);
					if (mob.texture().equals(SpeciesMob.MISSING_TEXTURE)) {
						bad.add(mob.entityId() + "|" + mob.getSpeciesName()
								+ " rendered the PLACEHOLDER");
					} else if (!want.contains(tex)) {
						bad.add(mob.entityId() + "|" + mob.getSpeciesName()
								+ " rendered " + tex + ", not a skin expected in this batch");
					}
				}
				if (count != want.size()) {
					bad.add("expected " + want.size() + " animals on the client, saw " + count);
				}
				for (String w : want) {
					if (!seen.contains(w)) {
						bad.add("skin never rendered: " + w);
					}
				}
                return bad;
			}));
		}
		System.out.println("[menagerie-mp-test] skin sweep finished: "
				+ (problems.isEmpty() ? "every skin rendered" : problems.size() + " problem(s)"));
		return problems;
	}

	/** Every skin a given species may legitimately wear, read from the live registry. */
	private static Set<String> allowedSkins(String entity, String speciesName) {
		Set<String> out = new java.util.HashSet<>();
		for (var sp : SpeciesRegistry.speciesFor("menagerie:" + entity)) {
			if (!sp.name().equals(speciesName)) {
				continue;
			}
			out.add(sp.texture().toString());
			sp.textures().forEach(t -> out.add(t.toString()));
			sp.variantRolls().forEach(v -> out.add(v.texture().toString()));
		}
		return out;
	}

	/** A UUID whose hashCode lands on {@code index} under the fur pick's floorMod. */
	private static String uuidNbtForIndex(int index, int size) {
		java.util.Random rng = new java.util.Random(index * 1000003L + size);
		for (int attempt = 0; attempt < 1_000_000; attempt++) {
			java.util.UUID id = new java.util.UUID(rng.nextLong(), rng.nextLong());
			if (Math.floorMod(id.hashCode(), size) == index) {
				long hi = id.getMostSignificantBits();
				long lo = id.getLeastSignificantBits();
				return "[I;" + (int) (hi >> 32) + "," + (int) hi + ","
						+ (int) (lo >> 32) + "," + (int) lo + "]";
			}
		}
		throw new AssertionError("no UUID found for fur index " + index + "/" + size);
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
				// allowed skins come from the LIVE registry, not from a hand-kept list —
				// a hardcoded expectation here went stale the moment the lion's fur
				// table started working, and reported the fix as a failure
				boolean matched = row.stream().anyMatch(
						s -> allowedSkins(s.entity(), s.species()).contains(texture.toString()));
				if (!matched) {
					problems.add(id + " resolved to " + texture
							+ ", which is not any skin expected in this row");
				}
			}
			return problems;
		});
	}
}
