package dev.lilkuzco.menagerie.client;

import dev.lilkuzco.menagerie.Menagerie;
import dev.lilkuzco.menagerie.client.model.CrocodileModel;
import dev.lilkuzco.menagerie.client.model.GorillaModel;
import dev.lilkuzco.menagerie.client.model.GrizzlyModel;
import dev.lilkuzco.menagerie.client.model.HippoModel;
import dev.lilkuzco.menagerie.client.model.LeopardModel;
import dev.lilkuzco.menagerie.client.model.LionModel;
import dev.lilkuzco.menagerie.client.model.SnakeModel;
import dev.lilkuzco.menagerie.client.model.TortoiseModel;
import dev.lilkuzco.menagerie.client.model.VultureModel;
import dev.lilkuzco.menagerie.entity.MenagerieEntities;
import java.util.Set;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class MenagerieClient implements ClientModInitializer {
	private static final BabyModelTransform BABY = new BabyModelTransform(Set.of("head"));

	public static final ModelLayerLocation GORILLA = layer("gorilla");
	public static final ModelLayerLocation GORILLA_BABY = layer("gorilla_baby");
	public static final ModelLayerLocation CROCODILE = layer("crocodile");
	public static final ModelLayerLocation CROCODILE_BABY = layer("crocodile_baby");
	public static final ModelLayerLocation TORTOISE = layer("tortoise");
	public static final ModelLayerLocation TORTOISE_BABY = layer("tortoise_baby");
	public static final ModelLayerLocation LEOPARD = layer("leopard");
	public static final ModelLayerLocation LEOPARD_BABY = layer("leopard_baby");
	public static final ModelLayerLocation HIPPO = layer("hippo");
	public static final ModelLayerLocation HIPPO_BABY = layer("hippo_baby");
	public static final ModelLayerLocation GRIZZLY = layer("grizzly");
	public static final ModelLayerLocation GRIZZLY_BABY = layer("grizzly_baby");
	public static final ModelLayerLocation VULTURE = layer("vulture");
	public static final ModelLayerLocation VULTURE_BABY = layer("vulture_baby");
	public static final ModelLayerLocation LION = layer("lion");
	public static final ModelLayerLocation LION_BABY = layer("lion_baby");
	public static final ModelLayerLocation SNAKE = layer("snake");
	public static final ModelLayerLocation SNAKE_BABY = layer("snake_baby");

	private static ModelLayerLocation layer(String name) {
		return new ModelLayerLocation(Menagerie.id(name), "main");
	}

	@Override
	public void onInitializeClient() {
		net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.registerGlobalReceiver(
				dev.lilkuzco.menagerie.guide.MenagerieNet.GuideS2C.TYPE, (payload, context) ->
						context.client().execute(() ->
								context.client().gui.setScreen(new GuideScreen(payload))));

		ModelLayerRegistry.registerModelLayer(GORILLA, GorillaModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(GORILLA_BABY, () -> GorillaModel.createBodyLayer().apply(BABY));
		ModelLayerRegistry.registerModelLayer(CROCODILE, CrocodileModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(CROCODILE_BABY, () -> CrocodileModel.createBodyLayer().apply(BABY));
		ModelLayerRegistry.registerModelLayer(TORTOISE, TortoiseModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(TORTOISE_BABY, () -> TortoiseModel.createBodyLayer().apply(BABY));
		ModelLayerRegistry.registerModelLayer(LEOPARD, LeopardModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(LEOPARD_BABY, () -> LeopardModel.createBodyLayer().apply(BABY));
		ModelLayerRegistry.registerModelLayer(HIPPO, HippoModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(HIPPO_BABY, () -> HippoModel.createBodyLayer().apply(BABY));
		ModelLayerRegistry.registerModelLayer(GRIZZLY, GrizzlyModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(GRIZZLY_BABY, () -> GrizzlyModel.createBodyLayer().apply(BABY));
		ModelLayerRegistry.registerModelLayer(VULTURE, VultureModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(VULTURE_BABY, () -> VultureModel.createBodyLayer().apply(BABY));
		ModelLayerRegistry.registerModelLayer(LION, LionModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(LION_BABY, () -> LionModel.createBodyLayer().apply(BABY));
		ModelLayerRegistry.registerModelLayer(SNAKE, SnakeModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(SNAKE_BABY, () -> SnakeModel.createBodyLayer().apply(BABY));

		EntityRenderers.register(MenagerieEntities.GORILLA, GorillaRenderer::new);
		EntityRenderers.register(MenagerieEntities.CROCODILE, context -> new SpeciesRenderer<>(context,
				new CrocodileModel(context.bakeLayer(CROCODILE)), new CrocodileModel(context.bakeLayer(CROCODILE_BABY)),
				0.8F, (entity, state) -> state.lungeTicks = entity.clientLungeTicks));
		EntityRenderers.register(MenagerieEntities.TORTOISE, context -> new SpeciesRenderer<>(context,
				new TortoiseModel(context.bakeLayer(TORTOISE)), new TortoiseModel(context.bakeLayer(TORTOISE_BABY)),
				0.6F, (entity, state) -> state.shelled = entity.isShelled()));
		EntityRenderers.register(MenagerieEntities.LEOPARD, context -> new SpeciesRenderer<>(context,
				new LeopardModel(context.bakeLayer(LEOPARD)), new LeopardModel(context.bakeLayer(LEOPARD_BABY)),
				0.6F, (entity, state) -> {
					state.crouching = entity.isCrouchingPose();
					state.pounceTicks = entity.clientPounceTicks;
				}));
		EntityRenderers.register(MenagerieEntities.HIPPO, context -> new SpeciesRenderer<>(context,
				new HippoModel(context.bakeLayer(HIPPO)), new HippoModel(context.bakeLayer(HIPPO_BABY)),
				1.0F, (entity, state) -> state.yawnTicks = entity.clientYawnTicks));
		EntityRenderers.register(MenagerieEntities.GRIZZLY, context -> new SpeciesRenderer<>(context,
				new GrizzlyModel(context.bakeLayer(GRIZZLY)), new GrizzlyModel(context.bakeLayer(GRIZZLY_BABY)),
				0.9F, (entity, state) -> {
					state.swipeTicks = entity.clientSwipeTicks;
					state.bearSleeping = entity.isBearSleeping();
				}));
		EntityRenderers.register(MenagerieEntities.VULTURE, context -> new SpeciesRenderer<>(context,
				new VultureModel(context.bakeLayer(VULTURE)), new VultureModel(context.bakeLayer(VULTURE_BABY)),
				0.5F, (entity, state) -> state.flying = entity.isFlyingState()));
		EntityRenderers.register(MenagerieEntities.LION, LionRenderer::new);
		EntityRenderers.register(MenagerieEntities.SNAKE, context -> new SpeciesRenderer<>(context,
				new SnakeModel(context.bakeLayer(SNAKE)), new SnakeModel(context.bakeLayer(SNAKE_BABY)),
				0.4F, (entity, state) -> {
					state.coiled = entity.isCoiled();
					state.rattling = entity.isRattling();
					state.strikeTicks = entity.clientStrikeTicks;
				}));
	}
}
