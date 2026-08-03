package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
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
            this.tag(BATags.Items.FOODS_CITRUS)
                    .add(GRAPEFRUIT.get())
                    .add(ORANGE.get())
                    .add(LEMON.get())
                    .add(LIME.get());

            this.tag(Tags.Items.FOODS)
                    .add(PIE_CRUST.get())
                    .add(WAFER.get())
                    .add(DOUGH.get())
                    .add(CHERRIES.get())
                    .add(GOLDEN_CHERRIES.get())
                    .add(APPLE_SLICE.get())
                    .add(GREEN_APPLE.get())
                    .add(GRAPEFRUIT.get())
                    .add(GRAPEFRUIT_SLICE.get())
                    .add(ORANGE.get())
                    .add(ORANGE_SLICE.get())
                    .add(PUMPKIN_SLICE.get())
                    .add(MANGO.get())
                    .add(APRICOT.get())
                    .add(PINEAPPLE.get())
                    .add(BANANA.get())
                    .add(LEMON.get())
                    .add(LEMON_SLICE.get())
                    .add(LIME.get())
                    .add(LIME_SLICE.get())
                    .add(KIWI.get())
                    .add(PEAR.get())
                    .add(GRAPES.get())
                    .add(PEACH.get())
                    .add(DRAGON_FRUIT.get())
                    .add(DRAGON_FRUIT_SLICE.get())
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
                    .add(ACORN.get())
                    .add(ROASTED_ACORN.get())
                    .add(CORN.get())
                    .add(CORN_ON_A_COB.get())
                    .add(POPCORN.get())
                    .add(RAW_CORN_TORTILLA.get())
                    .add(CORN_TORTILLA.get())
                    .add(RICE.get())
                    .add(GREEN_TEA_LEAVES.get())
                    .add(YELLOW_TEA_LEAVES.get())
                    .add(BLACK_TEA_LEAVES.get())
                    .add(COFFEE_CHERRIES.get())
                    .add(COFFEE_BEANS.get())
                    .add(HONEY_APPLE.get())
                    .add(CANDY_APPLE.get())
                    .add(CARAMEL_APPLE.get())
                    .add(CANDIED_ORANGE_PEELS.get())
                    .add(ORANGE_PUDDING.get())
                    .add(ORANGE_SORBET.get())
                    .add(ORANGE_SHERBET.get())
                    .add(BANANA_BREAD.get())
                    .add(CANDIED_LIME_SLICE.get())
                    .add(RAISINS.get())
                    .add(DRIED_DRAGON_FRUIT.get())
                    .add(JERKY.get())
                    .add(CHOCOLATE_BAR.get())
                    .add(BROWNIE.get())
                    .add(CARAMEL.get())
                    .add(CHICKEN_QUESADILLA.get())
                    .add(JOCKEY_SANDWICH.get())
                    .add(PANETTONE.get())
                    .add(AMBROSIA_SALAD.get())
                    .add(JEWELED_RICE_BOWL.get())
                    .add(PLAIN_COOKIE.get())
                    .add(LEMON_COOKIE.get())
                    .add(LIME_COOKIE.get())
                    .add(SNICKERDOODLE.get())
                    .add(ECLIPSE_COOKIE.get())
                    .add(GOLDEN_COOKIE.get())
                    .add(MACARON.get())
                    .add(LIME_POPSICLE.get())
                    .add(DOUBLE_LIME_POPSICLE.get())
                    .add(CAKE_SLICE.get())
                    .add(CHERRY_PIE.get())
                    .add(CHERRY_PIE_SLICE.get())
                    .add(APPLE_PIE.get())
                    .add(APPLE_PIE_SLICE.get())
                    .add(APPLE_CAKE_SLICE.get())
                    .add(GRAPEFRUIT_PIE.get())
                    .add(GRAPEFRUIT_PIE_SLICE.get())
                    .add(ORANGE_PIE.get())
                    .add(ORANGE_PIE_SLICE.get())
                    .add(ORANGE_CAKE_SLICE.get())
                    .add(MANGO_PIE.get())
                    .add(MANGO_PIE_SLICE.get())
                    .add(BANANA_CAKE_SLICE.get())
                    .add(LEMON_TART.get())
                    .add(LEMON_TART_SLICE.get())
                    .add(BAItems.LEMON_CAKE.get())
                    .add(LEMON_CAKE_SLICE.get())
                    .add(BAItems.LIME_CAKE.get())
                    .add(LIME_CAKE_SLICE.get())
                    .add(DRAGON_FRUIT_PIE.get())
                    .add(DRAGON_FRUIT_PIE_SLICE.get())
                    .add(PUMPKIN_PIE_SLICE.get());

            this.tag(Tags.Items.FOODS_FRUIT)
                    .add(CHERRIES.get())
                    .add(GOLDEN_CHERRIES.get())
                    .add(APPLE_SLICE.get())
                    .add(GREEN_APPLE.get())
                    .add(GRAPEFRUIT.get())
                    .add(GRAPEFRUIT_SLICE.get())
                    .add(ORANGE.get())
                    .add(ORANGE_SLICE.get())
                    .add(PUMPKIN_SLICE.get())
                    .add(MANGO.get())
                    .add(APRICOT.get())
                    .add(PINEAPPLE.get())
                    .add(BANANA.get())
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
                    .add(DRAGON_FRUIT_SLICE.get())
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

            this.tag(Tags.Items.FOODS_RAW_MEAT);
            this.tag(Tags.Items.FOODS_COOKED_MEAT)
                    .add(JERKY.get());
            this.tag(Tags.Items.FOODS_RAW_FISH);
            this.tag(Tags.Items.FOODS_COOKED_FISH);
            this.tag(Tags.Items.FOODS_FOOD_POISONING);

            this.tag(Tags.Items.FOODS_BREAD)
                    .add(BANANA_BREAD.get());

            this.tag(Tags.Items.FOODS_SOUP)
                    .add(AMBROSIA_SALAD.get());

            this.tag(Tags.Items.FOODS_CANDY)
                    .add(LIME_POPSICLE.get())
                    .add(DOUBLE_LIME_POPSICLE.get());

            this.tag(Tags.Items.FOODS_PIE)
                    .add(CHERRY_PIE.get())
                    .add(CHERRY_PIE_SLICE.get())
                    .add(APPLE_PIE.get())
                    .add(APPLE_PIE_SLICE.get())
                    .add(GRAPEFRUIT_PIE.get())
                    .add(GRAPEFRUIT_PIE_SLICE.get())
                    .add(ORANGE_PIE.get())
                    .add(ORANGE_PIE_SLICE.get())
                    .add(MANGO_PIE.get())
                    .add(MANGO_PIE_SLICE.get())
                    .add(LEMON_TART.get())
                    .add(LEMON_TART_SLICE.get())
                    .add(DRAGON_FRUIT_PIE.get())
                    .add(DRAGON_FRUIT_PIE_SLICE.get())
                    .add(PUMPKIN_PIE_SLICE.get());

            this.tag(Tags.Items.FOODS_COOKIE)
                    .add(PLAIN_COOKIE.get())
                    .add(LEMON_COOKIE.get())
                    .add(LIME_COOKIE.get())
                    .add(SNICKERDOODLE.get())
                    .add(ECLIPSE_COOKIE.get())
                    .add(GOLDEN_COOKIE.get())
                    .add(MACARON.get());

            this.tag(Tags.Items.FOODS_GOLDEN)
                    .add(GOLDEN_CHERRIES.get())
                    .add(GOLDEN_ORANGE.get())
                    .add(GOLDEN_STRAWBERRIES.get())
                    .add(WINGED_GOLDEN_STRAWBERRY.get())
                    .add(GOLDEN_COOKIE.get());

            this.tag(Tags.Items.FOODS_EDIBLE_WHEN_PLACED)
                    .add(BAItems.LEMON_CAKE.get())
                    .add(BAItems.LIME_CAKE.get());

            this.tag(Tags.Items.DRINKS)
                    .add(APPLE_JUICE.get())
                    .add(APPLE_CIDER.get())
                    .add(APPLEJACK.get())
                    .add(ORANGE_JUICE.get())
                    .add(BANANA_SMOOTHIE.get())
                    .add(LEMONADE.get())
                    .add(LIMEADE.get())
                    .add(DRAGON_FRUIT_LATTE.get())
                    .add(COFFEE.get())
                    .add(STRAWBERRY_BANANA_SMOOTHIE.get())
                    .add(PINK_LEMONADE.get())
                    .add(PINK_LIMEADE.get())
                    .add(BLUEBERRY_LIMEADE.get())
                    .add(LIME_GREEN_TEA.get())
                    .add(PINK_LADY.get())
                    .add(CHERRY_LIME_RICKEY_REFRESHER.get());
            this.tag(Tags.Items.DRINKS_WATER);
            this.tag(Tags.Items.DRINKS_WATERY);
            this.tag(Tags.Items.DRINKS_JUICE)
                    .add(APPLE_JUICE.get())
                    .add(APPLE_CIDER.get())
                    .add(ORANGE_JUICE.get());
            this.tag(Tags.Items.DRINKS_MILK);
            this.tag(Tags.Items.DRINKS_HONEY);

            this.tag(Tags.Items.SEEDS)
                    .add(POMEGRANATE_SEEDS.get())
                    .add(CORN_KERNELS.get());
            this.tag(ItemTags.VILLAGER_PLANTABLE_SEEDS)
                    .add(CORN_KERNELS.get());

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