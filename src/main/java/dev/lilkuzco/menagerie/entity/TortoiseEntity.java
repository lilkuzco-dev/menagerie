package dev.lilkuzco.menagerie.entity;

import dev.lilkuzco.menagerie.MenagerieSounds;
import dev.lilkuzco.menagerie.data.Species;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/**
 * Fully passive. When hit it retreats into its shell: bonus armor + immobile for a
 * while (both tunable via species "special"). worldgen_only in JSON means killing them
 * permanently empties the area — keeping the world alive is the point.
 */
public class TortoiseEntity extends SpeciesMob {
	private static final EntityDataAccessor<Boolean> SHELLED =
			SynchedEntityData.defineId(TortoiseEntity.class, EntityDataSerializers.BOOLEAN);
	private static final Identifier SHELL_ARMOR_ID = Identifier.fromNamespaceAndPath("menagerie", "shell_armor");

	private int shellTicks;

	public TortoiseEntity(EntityType<? extends TortoiseEntity> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHELLED, false);
	}

	/** already registers its own BreedGoal below. */
	@Override
	protected boolean hasBreedGoal() {
		return true;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new BreedGoal(this, 0.8));
		this.goalSelector.addGoal(2, new TemptGoal(this, 0.9, this::isFood, false));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.7));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
	}

	public boolean isShelled() {
		return this.entityData.get(SHELLED);
	}

	private void setShelled(boolean shelled) {
		this.entityData.set(SHELLED, shelled);
		AttributeInstance armor = getAttribute(Attributes.ARMOR);
		Species species = species();
		double bonus = species != null ? species.specialDouble("shell_armor", 8.0) : 8.0;
		if (shelled) {
			armor.addOrUpdateTransientModifier(
					new AttributeModifier(SHELL_ARMOR_ID, bonus, AttributeModifier.Operation.ADD_VALUE));
		} else {
			armor.removeModifier(SHELL_ARMOR_ID);
		}
	}

	@Override
	protected void actuallyHurt(ServerLevel level, DamageSource source, float damage) {
		super.actuallyHurt(level, source, damage);
		Species species = species();
		shellTicks = species != null ? species.specialInt("shell_ticks", 200) : 200;
		if (!isShelled()) {
			setShelled(true);
			playSound(MenagerieSounds.TORTOISE_RETRACT, 1.0F, 1.0F);
		}
	}

	/**
	 * Shell timer lives in tick(), NOT customServerAiStep: isImmobile() (below) makes
	 * vanilla skip serverAiStep entirely, which would deadlock the timer there.
	 */
	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide() && shellTicks > 0) {
			shellTicks--;
			if (shellTicks == 0 && isShelled()) {
				setShelled(false);
			}
		}
	}

	/** In the shell the tortoise does not move at all. */
	@Override
	protected boolean isImmobile() {
		return super.isImmobile() || isShelled();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MenagerieSounds.TORTOISE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return MenagerieSounds.TORTOISE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return MenagerieSounds.TORTOISE_DEATH;
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putInt("menagerie_shell_ticks", shellTicks);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		shellTicks = input.getIntOr("menagerie_shell_ticks", 0);
		setShelled(shellTicks > 0);
	}
}
