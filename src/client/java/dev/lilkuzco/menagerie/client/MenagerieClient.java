package dev.lilkuzco.menagerie.client;

import dev.lilkuzco.menagerie.Menagerie;
import dev.lilkuzco.menagerie.client.model.CrocodileModel;
import dev.lilkuzco.menagerie.client.model.GorillaModel;
import dev.lilkuzco.menagerie.client.model.LeopardModel;
import dev.lilkuzco.menagerie.client.model.TortoiseModel;
import dev.lilkuzco.menagerie.entity.MenagerieEntities;
import java.util.Set;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.geom.ModelLayerLocation;

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

	private static ModelLayerLocation layer(String name) {
		return new ModelLayerLocation(Menagerie.id(name), "main");
	}

	@Override
	public void onInitializeClient() {
		ModelLayerRegistry.registerModelLayer(GORILLA, GorillaModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(GORILLA_BABY, () -> GorillaModel.createBodyLayer().apply(BABY));
		ModelLayerRegistry.registerModelLayer(CROCODILE, CrocodileModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(CROCODILE_BABY, () -> CrocodileModel.createBodyLayer().apply(BABY));
		ModelLayerRegistry.registerModelLayer(TORTOISE, TortoiseModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(TORTOISE_BABY, () -> TortoiseModel.createBodyLayer().apply(BABY));
		ModelLayerRegistry.registerModelLayer(LEOPARD, LeopardModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(LEOPARD_BABY, () -> LeopardModel.createBodyLayer().apply(BABY));

		EntityRendererRegistry.register(MenagerieEntities.GORILLA, context -> new SpeciesRenderer<>(context,
				new GorillaModel(context.bakeLayer(GORILLA)), new GorillaModel(context.bakeLayer(GORILLA_BABY)),
				0.7F, (entity, state) -> {
					state.silverback = entity.isSilverback();
					state.beatTicks = entity.clientBeatTicks;
					state.eatTicks = entity.clientEatTicks;
				}));
		EntityRendererRegistry.register(MenagerieEntities.CROCODILE, context -> new SpeciesRenderer<>(context,
				new CrocodileModel(context.bakeLayer(CROCODILE)), new CrocodileModel(context.bakeLayer(CROCODILE_BABY)),
				0.8F, (entity, state) -> state.lungeTicks = entity.clientLungeTicks));
		EntityRendererRegistry.register(MenagerieEntities.TORTOISE, context -> new SpeciesRenderer<>(context,
				new TortoiseModel(context.bakeLayer(TORTOISE)), new TortoiseModel(context.bakeLayer(TORTOISE_BABY)),
				0.6F, (entity, state) -> state.shelled = entity.isShelled()));
		EntityRendererRegistry.register(MenagerieEntities.LEOPARD, context -> new SpeciesRenderer<>(context,
				new LeopardModel(context.bakeLayer(LEOPARD)), new LeopardModel(context.bakeLayer(LEOPARD_BABY)),
				0.6F, (entity, state) -> {
					state.crouching = entity.isCrouchingPose();
					state.pounceTicks = entity.clientPounceTicks;
				}));
	}
}
