package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

import static net.ashstarcrash.bonappetit.core.registry.BAItems.*;

public class BADataMapProvider extends DataMapProvider {
    protected BADataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    public void gather() {
        var compostables = builder(NeoForgeDataMaps.COMPOSTABLES);

        compostables.add(POMEGRANATE_SEEDS.getId(), new Compostable(0.3F), false);
        compostables.add(CORN_KERNELS.getId(), new Compostable(0.3F), false);

        compostables.add(CHERRIES.getId(), new Compostable(0.65F), false);
        compostables.add(APPLE_SLICE.getId(), new Compostable(0.5F), false);
        compostables.add(GREEN_APPLE.getId(), new Compostable(0.65F), false);
        compostables.add(GRAPEFRUIT.getId(), new Compostable(0.65F), false);
        compostables.add(GRAPEFRUIT_SLICE.getId(), new Compostable(0.5F), false);
        compostables.add(ORANGE.getId(), new Compostable(0.65F), false);
        compostables.add(ORANGE_SLICE.getId(), new Compostable(0.5F), false);
        compostables.add(MANGO.getId(), new Compostable(0.65F), false);
        compostables.add(APRICOT.getId(), new Compostable(0.65F), false);
        compostables.add(PINEAPPLE.getId(), new Compostable(0.65F), false);
        compostables.add(BANANA.getId(), new Compostable(0.65F), false);
        compostables.add(LEMON.getId(), new Compostable(0.65F), false);
        compostables.add(LEMON_SLICE.getId(), new Compostable(0.5F), false);
        compostables.add(LIME.getId(), new Compostable(0.65F), false);
        compostables.add(LIME_SLICE.getId(), new Compostable(0.5F), false);
        compostables.add(KIWI.getId(), new Compostable(0.65F), false);
        compostables.add(PEAR.getId(), new Compostable(0.65F), false);
        compostables.add(GRAPES.getId(), new Compostable(0.65F), false);
        compostables.add(PEACH.getId(), new Compostable(0.65F), false);
        compostables.add(DRAGON_FRUIT.getId(), new Compostable(0.65F), false);
        compostables.add(DRAGON_FRUIT_SLICE.getId(), new Compostable(0.5F), false);
        compostables.add(POMEGRANATE.getId(), new Compostable(0.65F), false);
        compostables.add(POMEGRANATE_SLICE.getId(), new Compostable(0.5F), false);
        compostables.add(COCONUT.getId(), new Compostable(0.65F), false);
        compostables.add(COCONUT_SLICE.getId(), new Compostable(0.5F), false);

        compostables.add(COFFEE_CHERRIES.getId(), new Compostable(0.3F), false);
        compostables.add(CRANBERRIES.getId(), new Compostable(0.3F), false);
        compostables.add(STRAWBERRIES.getId(), new Compostable(0.3F), false);
        compostables.add(SALMONBERRIES.getId(), new Compostable(0.3F), false);
        compostables.add(BLUEBERRIES.getId(), new Compostable(0.3F), false);
        compostables.add(MULBERRIES.getId(), new Compostable(0.3F), false);
        compostables.add(RASPBERRIES.getId(), new Compostable(0.3F), false);
        compostables.add(BLACK_RASPBERRIES.getId(), new Compostable(0.3F), false);

        compostables.add(CORN.getId(), new Compostable(0.65F), false);
        compostables.add(ONION.getId(), new Compostable(0.65F), false);
        compostables.add(ONION_SLICE.getId(), new Compostable(0.5F), false);
        compostables.add(PUMPKIN_SLICE.getId(), new Compostable(0.5F), false);

        compostables.add(RICE.getId(), new Compostable(0.65F), false);

        compostables.add(GREEN_TEA_LEAVES.getId(), new Compostable(0.5F), false);
        compostables.add(YELLOW_TEA_LEAVES.getId(), new Compostable(0.5F), false);
        compostables.add(BLACK_TEA_LEAVES.getId(), new Compostable(0.5F), false);
        compostables.add(COFFEE_BEANS.getId(), new Compostable(0.5F), false);

        compostables.add(PIE_CRUST.getId(), new Compostable(0.65F), false);

        compostables.add(PANETTONE.getId(), new Compostable(1.0F), false);
        compostables.add(PANETTONE_SLICE.getId(), new Compostable(1.0F), false);
        compostables.add(STOLLEN.getId(), new Compostable(1.0F), false);
        compostables.add(STOLLEN_SLICE.getId(), new Compostable(1.0F), false);

        compostables.add(CHERRY_PIE.getId(), new Compostable(1.0F), false);
        compostables.add(CHERRY_PIE_SLICE.getId(), new Compostable(0.25F), false);
        compostables.add(APPLE_PIE.getId(), new Compostable(1.0F), false);
        compostables.add(APPLE_PIE_SLICE.getId(), new Compostable(0.25F), false);
        compostables.add(GRAPEFRUIT_PIE.getId(), new Compostable(1.0F), false);
        compostables.add(GRAPEFRUIT_PIE_SLICE.getId(), new Compostable(0.25F), false);
        compostables.add(ORANGE_PIE.getId(), new Compostable(1.0F), false);
        compostables.add(ORANGE_PIE_SLICE.getId(), new Compostable(0.25F), false);
        compostables.add(MANGO_PIE.getId(), new Compostable(1.0F), false);
        compostables.add(MANGO_PIE_SLICE.getId(), new Compostable(0.25F), false);
        compostables.add(LEMON_TART.getId(), new Compostable(1.0F), false);
        compostables.add(LEMON_TART_SLICE.getId(), new Compostable(0.25F), false);
        compostables.add(DRAGON_FRUIT_PIE.getId(), new Compostable(1.0F), false);
        compostables.add(DRAGON_FRUIT_PIE_SLICE.getId(), new Compostable(0.25F), false);
        compostables.add(CAKE_SLICE.getId(), new Compostable(0.2F), false);
        compostables.add(APPLE_CAKE_SLICE.getId(), new Compostable(0.2F), false);
        compostables.add(ORANGE_CAKE_SLICE.getId(), new Compostable(0.2F), false);
        compostables.add(BANANA_CAKE_SLICE.getId(), new Compostable(0.2F), false);
        compostables.add(LEMON_CAKE.getId(), new Compostable(1.0F), false);
        compostables.add(LEMON_CAKE_SLICE.getId(), new Compostable(0.2F), false);
        compostables.add(LIME_CAKE.getId(), new Compostable(1.0F), false);
        compostables.add(LIME_CAKE_SLICE.getId(), new Compostable(0.2F), false);
        compostables.add(PUMPKIN_PIE_SLICE.getId(), new Compostable(0.2F), false);
    }
}