package net.ashstarcrash.bonappetit;

import com.mojang.logging.LogUtils;
import net.ashstarcrash.bonappetit.core.common.data.recipe.RecipeCategories;
import net.ashstarcrash.bonappetit.core.common.event.*;
import net.ashstarcrash.bonappetit.core.content.blockentity.CopperTankEntity;
import net.ashstarcrash.bonappetit.core.registry.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(BonAppetit.ID)
public class BonAppetit {
    public static final String ID = "bonappetit";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BonAppetit(IEventBus modEventBus, ModContainer modContainer) {
        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(RecipeCategories::init);
        }

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::modifyComponents);

        BABlocks.register(modEventBus);
        BABlockEntities.register(modEventBus);
        BAEntities.ENTITIES.register(modEventBus);
        BAItems.register(modEventBus);
        BACreativeTabs.register(modEventBus);
        BAEffects.EFFECTS.register(modEventBus);

        BADataComponents.DATA_COMPONENTS.register(modEventBus);
        BAAttachments.ATTACHMENTS.register(modEventBus);
        BATriggers.TRIGGERS.register(modEventBus);

        BARecipeTypes.RECIPE_TYPES.register(modEventBus);
        BARecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        BAMenuTypes.MENU_TYPES.register(modEventBus);

        BAFeatures.FEATURES.register(modEventBus);
        BALootModifiers.LOOT_MODIFIERS.register(modEventBus);

        NeoForge.EVENT_BUS.register(ReflectionEvent.class);
        NeoForge.EVENT_BUS.register(ConcentrationEvent.class);
        NeoForge.EVENT_BUS.register(FervorEvent.class);
        NeoForge.EVENT_BUS.register(FlakEvent.class);
        NeoForge.EVENT_BUS.register(DischargeEvent.class);
        NeoForge.EVENT_BUS.register(VigorEvent.class);
        NeoForgeMod.enableMilkFluid();
        modEventBus.addListener((RegisterCapabilitiesEvent event) -> {event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BABlockEntities.COPPER_TANK.get(), (be, side) -> {if (be instanceof CopperTankEntity tank) {return tank.getTank();}return null;});});
        modContainer.registerConfig(ModConfig.Type.COMMON, BAConfig.SPEC, "bonappetit.toml");
    }

    @SubscribeEvent
    public void modifyComponents(ModifyDefaultComponentsEvent event) {
        event.modify(Items.COOKIE, builder -> builder.set(DataComponents.FOOD, new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).fast().build()));
        event.modify(Items.CAKE, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 16));
        event.modifyMatching(Item::hasCraftingRemainingItem, builder -> builder.remove(DataComponents.BUCKET_ENTITY_DATA));
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Bon Appetit successfully loaded");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Bon Appetit successfully ran serverside");
    }
}