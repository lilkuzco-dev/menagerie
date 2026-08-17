package dev.lilkuzco.menagerie.data;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.GsonHelper;

/**
 * Spawn rarity tiers, calibrated against vanilla's own anchors rather than invented
 * numbers. Vanilla's jungle pool runs parrot 40 / chicken 10 / ocelot 2 / panda 1, and
 * because CREATURE-category mobs never despawn, a generous weight compounds forever —
 * which is exactly how gorillas ended up roughly thirty times panda density.
 *
 * A tier sets three things at once: spawn weight, group size, and the nearby cap that
 * stops persistent animals accumulating. Species JSON says one word ("rare") and gets
 * all three; explicit weight/group_size/nearby_cap still override.
 *
 * Loaded from data/&lt;ns&gt;/menagerie_config/rarity.json, so retuning density is a
 * /reload away with no rebuild.
 */
public final class RarityConfig {
	/** Radius the nearby-species cap counts within. */
	public static final int NEARBY_RADIUS = 64;

	/**
	 * @param weight         biome spawn weight (vanilla panda is 1, ocelot 2, chicken 10)
	 * @param groupMin       smallest spawn group
	 * @param groupMax       largest spawn group
	 * @param nearbyCap      max same-type animals within {@link #NEARBY_RADIUS}
	 * @param attemptChance  extra thinning applied per spawn attempt (epic tiers)
	 */
	public record Tier(int weight, int groupMin, int groupMax, int nearbyCap, float attemptChance) {
	}

	// These MUST stay equal to data/menagerie/menagerie_config/rarity.json — spawn-lint
	// S11 fails the build if they drift. They are not merely "the same numbers twice":
	// this table is what SpeciesRegistry falls back to when rarity.json does not resolve
	// (absent, unreadable, or overridden away by a datapack). Left at the pre-0.4.5
	// values, that fallback silently reverted the whole ladder to panda-class density —
	// no error, no log line, just an emptier world, which is the hardest failure to see.
	public static final Tier UBIQUITOUS = new Tier(30, 2, 4, 12, 1.0F);
	public static final Tier COMMON = new Tier(20, 1, 3, 10, 1.0F);
	public static final Tier UNCOMMON = new Tier(10, 1, 3, 6, 1.0F);
	public static final Tier RARE = new Tier(5, 1, 2, 4, 1.0F);
	public static final Tier EPIC = new Tier(2, 1, 2, 3, 0.35F);

	private static final Map<String, Tier> DEFAULTS = Map.of(
			"ubiquitous", UBIQUITOUS,
			"common", COMMON,
			"uncommon", UNCOMMON,
			"rare", RARE,
			"epic", EPIC);

	private static RarityConfig instance = new RarityConfig(DEFAULTS);

	private final Map<String, Tier> tiers;

	private RarityConfig(Map<String, Tier> tiers) {
		this.tiers = tiers;
	}

	public static RarityConfig get() {
		return instance;
	}

	static void set(RarityConfig config) {
		instance = config;
	}

	/** Unknown or empty tier names fall back to "common" — a missing tier never breaks spawning. */
	public Tier tier(String name) {
		Tier tier = tiers.get(name);
		return tier != null ? tier : COMMON;
	}

	public static RarityConfig fromJson(JsonObject json) {
		Map<String, Tier> tiers = new HashMap<>(DEFAULTS);
		JsonObject block = GsonHelper.getAsJsonObject(json, "tiers", new JsonObject());
		for (String key : block.keySet()) {
			JsonObject t = GsonHelper.getAsJsonObject(block, key);
			Tier fallback = tiers.getOrDefault(key, COMMON);
			tiers.put(key, new Tier(
					GsonHelper.getAsInt(t, "weight", fallback.weight()),
					GsonHelper.getAsInt(t, "group_min", fallback.groupMin()),
					GsonHelper.getAsInt(t, "group_max", fallback.groupMax()),
					GsonHelper.getAsInt(t, "nearby_cap", fallback.nearbyCap()),
					GsonHelper.getAsFloat(t, "attempt_chance", fallback.attemptChance())));
		}
		return new RarityConfig(Map.copyOf(tiers));
	}

	public static RarityConfig defaults() {
		return new RarityConfig(DEFAULTS);
	}
}
