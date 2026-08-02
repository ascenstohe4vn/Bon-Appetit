package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BATags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

import static net.ashstarcrash.bonappetit.core.registry.BABlocks.*;
import static net.ashstarcrash.bonappetit.core.registry.BAItems.*;

public class BATagProvider {
    public static class BAItemTagProvider extends ItemTagsProvider {
        public BAItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, blockTags, BonAppetit.ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            this.tag(BATags.Items.CITRUS_FOODS)
                .add(GRAPEFRUIT.get())
                .add(ORANGE.get())
                .add(LEMON.get())
                .add(LIME.get());

            this.tag(Tags.Items.FOODS)
                .add(PIE_CRUST.get())
                .add(WAFER.get())
                .add(CHERRIES.get())
                .add(GOLDEN_CHERRIES.get())
                .add(APPLE_SLICE.get())
                .add(GRAPEFRUIT.get())
                .add(GRAPEFRUIT_SLICE.get())
                .add(ORANGE.get())
                .add(ORANGE_SLICE.get())
                .add(PUMPKIN_SLICE.get())
                .add(PUMPKIN_PIE_SLICE.get())
                .add(MANGO.get())
                .add(APRICOT.get())
                .add(PINEAPPLE.get())
                .add(LEMON.get())
                .add(LEMON_SLICE.get())
                .add(LIME.get())
                .add(LIME_SLICE.get())
                .add(KIWI.get())
                .add(PEAR.get())
                .add(GRAPES.get())
                .add(RAISINS.get())
                .add(PEACH.get())
                .add(DRAGON_FRUIT.get())
                .add(POMEGRANATE.get())
                .add(POMEGRANATE_SLICE.get())
                .add(COCONUT.get())
                .add(COCONUT_SLICE.get())
                .add(STRAWBERRIES.get())
                .add(GOLDEN_STRAWBERRIES.get())
                .add(WINGED_STRAWBERRY.get())
                .add(WINGED_GOLDEN_STRAWBERRY.get())
                .add(CRANBERRIES.get())
                .add(SALMONBERRIES.get())
                .add(BLUEBERRIES.get())
                .add(MULBERRIES.get())
                .add(RASPBERRIES.get())
                .add(BLACK_RASPBERRIES.get())
                .add(CORN.get())
                .add(CORN_ON_A_COB.get())
                .add(POPCORN.get())
                .add(GREEN_TEA_LEAVES.get())
                .add(YELLOW_TEA_LEAVES.get())
                .add(BLACK_TEA_LEAVES.get())
                .add(COFFEE_CHERRIES.get())
                .add(COFFEE_BEANS.get())
                .add(CANDIED_LIME_SLICE.get())
                .add(BROWNIE.get())
                .add(PLAIN_COOKIE.get())
                .add(SNICKERDOODLE.get())
                .add(ECLIPSE_COOKIE.get())
                .add(GOLDEN_COOKIE.get())
                .add(MACARON.get())
                .add(JOCKEY_SANDWICH.get())
                .add(PANETTONE.get())
                .add(AMBROSIA_SALAD.get());

            this.tag(Tags.Items.FOODS_FRUIT)
                .add(CHERRIES.get())
                .add(GOLDEN_CHERRIES.get())
                .add(APPLE_SLICE.get())
                .add(GRAPEFRUIT.get())
                .add(GRAPEFRUIT_SLICE.get())
                .add(ORANGE.get())
                .add(ORANGE_SLICE.get())
                .add(PUMPKIN_SLICE.get())
                .add(MANGO.get())
                .add(APRICOT.get())
                .add(PINEAPPLE.get())
                .add(LEMON_SLICE.get())
                .add(LIME.get())
                .add(LIME_SLICE.get())
                .add(KIWI.get())
                .add(PEAR.get())
                .add(GRAPES.get())
                .add(RAISINS.get())
                .add(PEACH.get())
                .add(DRAGON_FRUIT.get())
                .add(POMEGRANATE.get())
                .add(POMEGRANATE_SLICE.get())
                .add(COCONUT.get())
                .add(COCONUT_SLICE.get())
                .add(STRAWBERRIES.get())
                .add(GOLDEN_STRAWBERRIES.get())
                .add(WINGED_STRAWBERRY.get())
                .add(WINGED_GOLDEN_STRAWBERRY.get())
                .add(CRANBERRIES.get())
                .add(SALMONBERRIES.get())
                .add(BLUEBERRIES.get())
                .add(MULBERRIES.get())
                .add(RASPBERRIES.get())
                .add(BLACK_RASPBERRIES.get());

            this.tag(Tags.Items.FOODS_BERRY)
                .add(STRAWBERRIES.get())
                .add(GOLDEN_STRAWBERRIES.get())
                .add(WINGED_STRAWBERRY.get())
                .add(WINGED_GOLDEN_STRAWBERRY.get())
                .add(CRANBERRIES.get())
                .add(SALMONBERRIES.get())
                .add(BLUEBERRIES.get())
                .add(MULBERRIES.get())
                .add(RASPBERRIES.get())
                .add(BLACK_RASPBERRIES.get())
                .add(COFFEE_CHERRIES.get());

            this.tag(Tags.Items.FOODS_VEGETABLE)
                .add(CORN.get())
                .add(CORN_ON_A_COB.get());

            this.tag(Tags.Items.FOODS_COOKIE)
                .add(PLAIN_COOKIE.get())
                .add(SNICKERDOODLE.get())
                .add(ECLIPSE_COOKIE.get())
                .add(GOLDEN_COOKIE.get())
                .add(MACARON.get());

            this.tag(Tags.Items.FOODS_GOLDEN)
                .add(GOLDEN_CHERRIES.get())
                .add(GOLDEN_ORANGE.get())
                .add(GOLDEN_STRAWBERRIES.get())
                .add(WINGED_STRAWBERRY.get())
                .add(WINGED_GOLDEN_STRAWBERRY.get())
                .add(GOLDEN_COOKIE.get());

            this.tag(ItemTags.FOX_FOOD)
                .add(STRAWBERRIES.get())
                .add(GOLDEN_STRAWBERRIES.get())
                .add(CRANBERRIES.get())
                .add(SALMONBERRIES.get())
                .add(BLUEBERRIES.get())
                .add(MULBERRIES.get())
                .add(RASPBERRIES.get())
                .add(BLACK_RASPBERRIES.get())
                .add(COFFEE_CHERRIES.get());

            this.tag(Tags.Items.DRINKS)
                .add(LEMONADE.get())
                .add(LIMEADE.get())
                .add(COFFEE.get())
                .add(PINK_LEMONADE.get())
                .add(PINK_LIMEADE.get())
                .add(BLUEBERRY_LIMEADE.get())
                .add(LIME_GREEN_TEA.get())
                .add(CHERRY_LIME_RICKEY_REFRESHER.get())
                .add(PINK_LADY.get());

            this.tag(ItemTags.DYEABLE)
                .add(MACARON.get());
        }
    }

    public static class BABlockTagProvider extends BlockTagsProvider {
        public BABlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, BonAppetit.ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            this.tag(BlockTags.CROPS)
                .add(GRAPEFRUIT_VINE.get());
        }
    }
}