package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BonAppetit.ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        BATagProvider.BABlockTagProvider blockTagProvider = new BATagProvider.BABlockTagProvider(packOutput, lookupProvider, existingFileHelper);

        // client
        generator.addProvider(event.includeClient(), new BABlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new BAItemModelProvider(packOutput, existingFileHelper));

        // server
        generator.addProvider(event.includeServer(), blockTagProvider); //BABlockTagProvider
        generator.addProvider(event.includeServer(),
                new BATagProvider.BAItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(
                BABlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
        generator.addProvider(event.includeServer(), new BARecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new BAEnglishLanguageProvider(packOutput));
    }
}