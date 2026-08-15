package dev.lilkuzco.menagerie.entity;

import dev.lilkuzco.menagerie.MenagerieSounds;
import dev.lilkuzco.menagerie.entity.ai.LeopardAttackGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

/**
 * Opportunist stalker: hunts small prey and baby animals; players only when already
 * below half health. Crouch-approaches, pounces, and backs off when outnumbered
 * (two or more players close by).
 */
public class LeopardEntity extends SpeciesMob {
	private static final EntityDataAccessor<Boolean> CROUCHING =
			SynchedEntityData.defineId(LeopardEntity.class, EntityDataSerializers.BOOLEAN);
	private static final byte EVENT_POUNCE = 67;

	public int clientPounceTicks;

	public LeopardEntity(EntityType<? extends LeopardEntity> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(CROUCHING, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 10.0F, 1.2, 1.5) {
			@Override
			public boolean canUse() {
				return LeopardEntity.this.isOutnumbered() && super.canUse();
			}
		});
		this.goalSelector.addGoal(2, new LeopardAttackGoal(this, 1.2));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 10.0F));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Chicken.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Rabbit.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Animal.class, 10, true, false,
				(target, level) -> target instanceof Animal animal && animal.isBaby()
						&& !(animal instanceof LeopardEntity)));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
				(target, level) -> target instanceof Player player && !player.isCreative()
						&& player.getHealth() < player.getMaxHealth() * 0.5F && !isOutnumbered()));
	}

	/** Two or more players within 8 blocks: too risky, back off. */
	public boolean isOutnumbered() {
		return level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(8.0),
				player -> !player.isCreative() && !player.isSpectator()).size() >= 2;
	}

	public boolean isCrouchingPose() {
		return this.entityData.get(CROUCHING);
	}

	public void setCrouching(boolean crouching) {
		this.entityData.set(CROUCHING, crouching);
	}

	public void startPounce() {
		if (level() instanceof ServerLevel serverLevel) {
			serverLevel.broadcastEntityEvent(this, EVENT_POUNCE);
			playSound(MenagerieSounds.LEOPARD_POUNCE, 1.0F, 1.0F);
		}
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == EVENT_POUNCE) {
			clientPounceTicks = 12;
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide() && clientPounceTicks > 0) {
			clientPounceTicks--;
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MenagerieSounds.LEOPARD_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return MenagerieSounds.LEOPARD_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return MenagerieSounds.LEOPARD_DEATH;
	}
}
