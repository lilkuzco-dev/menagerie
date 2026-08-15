package dev.lilkuzco.menagerie.entity;

import dev.lilkuzco.menagerie.MenagerieSounds;
import dev.lilkuzco.menagerie.data.Species;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Territory tank. Claims a water-anchored zone at spawn (radius from the species
 * "territory" block). Passive outside it; intruders inside get a 3s yawn warning,
 * then a charge. Boats are the signature victim: two bites and they're planks.
 * Calms ~20s after the territory empties.
 */
public class HippoEntity extends SpeciesMob {
	private static final byte EVENT_YAWN = 68;

	private @Nullable BlockPos territoryCenter;
	private int warnTicks;          // counts down the 3s yawn before the charge
	private @Nullable LivingEntity warnedAt;
	private @Nullable AbstractBoat boatTarget;
	private int calmDownTicks;      // no intruders for this long -> stand down

	public int clientYawnTicks;

	public HippoEntity(EntityType<? extends HippoEntity> type, Level level) {
		super(type, level);
		setPathfindingMalus(PathType.WATER, 0.0F);
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new AmphibiousPathNavigation(this, level);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.4, true));
		this.goalSelector.addGoal(2, new BreedGoal(this, 0.9));
		this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 0.9, 60));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.6));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}

	// ---------- territory ----------
	public @Nullable BlockPos territoryCenter() {
		return territoryCenter;
	}

	public int territoryRadius() {
		Species species = species();
		return species != null && species.territory() != null ? species.territory().radius() : 16;
	}

	public boolean insideTerritory(Vec3 pos) {
		if (territoryCenter == null) {
			return false;
		}
		double dx = pos.x - (territoryCenter.getX() + 0.5);
		double dz = pos.z - (territoryCenter.getZ() + 0.5);
		int radius = territoryRadius();
		return dx * dx + dz * dz <= (double) radius * radius && Math.abs(pos.y - territoryCenter.getY()) <= 12;
	}

	private void claimTerritory() {
		BlockPos here = blockPosition();
		Species species = species();
		String anchor = species != null && species.territory() != null ? species.territory().anchor() : "spawn";
		if ("water".equals(anchor)) {
			// snap the claim to nearby water so the zone covers the pool, not the bank
			BlockPos best = null;
			for (BlockPos pos : BlockPos.betweenClosed(here.offset(-8, -3, -8), here.offset(8, 1, 8))) {
				if (level().getFluidState(pos).is(FluidTags.WATER)
						&& (best == null || pos.distSqr(here) < best.distSqr(here))) {
					best = pos.immutable();
				}
			}
			territoryCenter = best != null ? best : here;
		} else {
			territoryCenter = here;
		}
		if (isBaby()) {
			setHomeTo(territoryCenter, Math.max(4, territoryRadius() / 2));
		}
	}

	// NOTE deliberately NOT claiming in finalizeSpawn: it runs during chunk generation
	// (worldgen thread), and the water scan reads blocks across chunk borders — that
	// sync-loads a still-generating neighbor and deadlocks the server (watchdog kill,
	// found in the Phase 2 battery). First ticked frame is main-thread + loaded chunks.

	// ---------- intruders: warn, then charge ----------
	@Override
	protected void customServerAiStep(ServerLevel level) {
		super.customServerAiStep(level);
		if (territoryCenter == null) {
			claimTerritory();
		}
		if (isBaby()) {
			return;
		}
		Species species = species();
		boolean aggroInside = species == null || species.territory() == null || species.territory().aggroInside();

		if (warnTicks > 0) {
			warnTicks--;
			if (warnTicks == 0 && warnedAt != null && warnedAt.isAlive() && insideTerritory(warnedAt.position())) {
				setTarget(warnedAt);
			}
			return;
		}

		// boat destruction takes priority over everything
		if (boatTarget != null) {
			if (!boatTarget.isAlive() || !insideTerritory(boatTarget.position())) {
				boatTarget = null;
			} else {
				getNavigation().moveTo(boatTarget, 1.4);
				getLookControl().setLookAt(boatTarget);
				if (isInWater() && distanceToSqr(boatTarget) > 4.0) {
					// hippos outswim boats: surge toward the hull
					Vec3 toward = boatTarget.position().subtract(position()).normalize();
					setDeltaMovement(getDeltaMovement().add(toward.scale(0.12)));
				}
				if (distanceToSqr(boatTarget) < 6.5 && tickCount % 15 == 0) {
					biteBoat(level, boatTarget);
				}
				return;
			}
		}

		if (tickCount % 10 != 0 || !aggroInside) {
			return;
		}

		LivingEntity intruderPlayer = null;
		AbstractBoat intruderBoat = null;
		int radius = territoryRadius();
		AABB zone = AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(
				territoryCenter == null ? blockPosition() : territoryCenter)).inflate(radius, 12, radius);
		for (Player player : level.getEntitiesOfClass(Player.class, zone,
				p -> !p.isCreative() && !p.isSpectator() && insideTerritory(p.position()))) {
			intruderPlayer = player;
			break;
		}
		for (AbstractBoat boat : level.getEntitiesOfClass(AbstractBoat.class, zone,
				b -> insideTerritory(b.position()))) {
			intruderBoat = boat;
			break;
		}

		if (intruderPlayer == null && intruderBoat == null) {
			if (getTarget() instanceof Player) {
				calmDownTicks += 10;
				if (calmDownTicks >= 400) { // ~20s empty -> stand down
					setTarget(null);
					calmDownTicks = 0;
				}
			}
			return;
		}
		calmDownTicks = 0;

		if (getTarget() != null) {
			return; // already charging
		}

		// 3s warning yawn, then commit
		if (intruderBoat != null && intruderPlayer == null) {
			startYawn(level);
			boatTarget = intruderBoat;
			warnTicks = 0; // boats get chased immediately after the yawn starts
		} else if (intruderPlayer != null && warnedAt != intruderPlayer) {
			startYawn(level);
			warnedAt = intruderPlayer;
			warnTicks = 60;
		}
	}

	private void startYawn(ServerLevel level) {
		level.broadcastEntityEvent(this, EVENT_YAWN);
		playSound(MenagerieSounds.HIPPO_WARN, 1.6F, 1.0F);
	}

	private void biteBoat(ServerLevel level, AbstractBoat boat) {
		playSound(MenagerieSounds.HIPPO_ATTACK, 1.4F, 1.0F);
		level.broadcastEntityEvent(this, EVENT_YAWN);
		boat.setDamage(boat.getDamage() + 25.0F);
		if (boat.getDamage() >= 40.0F) {
			boat.ejectPassengers();
			// spec: boats crunch into planks and sticks, not a reusable boat item
			spawnBoatDebris(level, boat);
			boat.discard();
			boatTarget = null;
		}
	}

	private void spawnBoatDebris(ServerLevel level, AbstractBoat boat) {
		List<ItemStack> debris = List.of(new ItemStack(Items.OAK_PLANKS, 3), new ItemStack(Items.STICK, 2));
		for (ItemStack stack : debris) {
			level.addFreshEntity(new ItemEntity(level, boat.getX(), boat.getY() + 0.3, boat.getZ(), stack));
		}
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == EVENT_YAWN) {
			clientYawnTicks = 40;
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide() && clientYawnTicks > 0) {
			clientYawnTicks--;
		}
		// charge surge: faster than a fleeing boat/swimmer while in water
		if (!level().isClientSide() && isInWater() && getTarget() != null && tickCount % 5 == 0) {
			Vec3 toward = getTarget().position().subtract(position());
			if (toward.lengthSqr() > 1.0) {
				setDeltaMovement(getDeltaMovement().add(toward.normalize().scale(0.10)));
			}
		}
	}

	// ---------- sounds ----------
	@Override
	protected SoundEvent getAmbientSound() {
		return MenagerieSounds.HIPPO_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return MenagerieSounds.HIPPO_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return MenagerieSounds.HIPPO_DEATH;
	}

	// ---------- persistence ----------
	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.storeNullable("menagerie_territory", BlockPos.CODEC, territoryCenter);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		territoryCenter = input.read("menagerie_territory", BlockPos.CODEC).orElse(null);
		if (territoryCenter != null && isBaby()) {
			setHomeTo(territoryCenter, Math.max(4, territoryRadius() / 2));
		}
	}
}
