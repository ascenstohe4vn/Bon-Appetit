package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.ashstarcrash.bonappetit.core.registry.FlavoredItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

import static net.ashstarcrash.bonappetit.core.registry.BABlocks.*;
import static net.ashstarcrash.bonappetit.core.registry.BAEffects.*;
import static net.ashstarcrash.bonappetit.core.registry.BAItems.*;
import static net.ashstarcrash.bonappetit.core.registry.BATags.Items.*;
import static net.ashstarcrash.bonappetit.core.registry.BATags.MobEffects.*;
import static net.minecraft.tags.ItemTags.*;
import static net.minecraft.world.effect.MobEffects.*;
import static net.minecraft.world.item.Items.*;
import static net.neoforged.neoforge.common.Tags.Items.*;

public class BATagProvider {
    public static class BAItemTagProvider extends ItemTagsProvider {
        public BAItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, blockTags, BonAppetit.ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            this.tag(FOODS_CHERRY)
                    .add(CHERRIES.get());
            this.tag(FOODS_APPLE)
                    .add(APPLE)
                    .add(GREEN_APPLE.get());
            this.tag(FOODS_GRAPEFRUIT)
                    .add(GRAPEFRUIT.get());
            this.tag(FOODS_ORANGE)
                    .add(ORANGE.get());
            this.tag(FOODS_BLOOD_ORANGE)
                    .addOptional(ModUtil.AT.asResource("blood_orange"));
            this.tag(FOODS_BANANA)
                    .add(BANANA.get());
            this.tag(FOODS_LEMON)
                    .add(LEMON.get());
            this.tag(FOODS_LIME)
                    .add(LIME.get());
            this.tag(FOODS_GRAPE)
                    .add(GRAPES.get());
            this.tag(FOODS_PEACH)
                    .add(PEACH.get());
            this.tag(FOODS_DRAGON_FRUIT)
                    .add(DRAGON_FRUIT.get());
            this.tag(FOODS_POMEGRANATE)
                    .add(POMEGRANATE.get());
            this.tag(FOODS_COCONUT)
                    .add(COCONUT.get())
                    .add(COCONUT_SLICE.get());

            this.tag(FOODS_CITRUS)
                    .addOptionalTag(FOODS_GRAPEFRUIT)
                    .addOptionalTag(FOODS_ORANGE)
                    .addOptionalTag(FOODS_BLOOD_ORANGE)
                    .addOptionalTag(FOODS_LEMON)
                    .addOptionalTag(FOODS_LIME);
            this.tag(FOODS_STONE_FRUIT)
                    .addTag(FOODS_CHERRY)
                    .add(APRICOT.get())
                    .add(PEACH.get());

            this.tag(FOODS_VANILLA)
                    .addOptional(ModUtil.N.asResource("dried_vanilla_pods"));
            this.tag(FOODS_TEA_LEAVES_GREEN)
                    .add(GREEN_TEA_LEAVES.get());
            this.tag(FOODS_TEA_LEAVES_YELLOW)
                    .add(YELLOW_TEA_LEAVES.get());
            this.tag(FOODS_TEA_LEAVES_BLACK)
                    .add(BLACK_TEA_LEAVES.get());
            this.tag(FOODS_TEA_LEAVES_MATCHA);
            this.tag(FOODS_TEA_LEAVES)
                    .addOptionalTag(FOODS_TEA_LEAVES_GREEN)
                    .addOptionalTag(FOODS_TEA_LEAVES_YELLOW)
                    .addOptionalTag(FOODS_TEA_LEAVES_BLACK)
                    .addOptionalTag(FOODS_TEA_LEAVES_MATCHA);
            this.tag(CROPS_COFFEE)
                    .add(COFFEE_CHERRIES.get());
            this.tag(FOODS_COFFEE_BEANS)
                    .add(COFFEE_BEANS.get());



            this.tag(FOODS)
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
                    .add(COFFEE_CHERRIES.get())
                    .add(CRANBERRIES.get())
                    .add(STRAWBERRIES.get())
                    .add(GOLDEN_STRAWBERRIES.get())
                    .add(WINGED_STRAWBERRY.get())
                    .add(WINGED_GOLDEN_STRAWBERRY.get())
                    .add(SALMONBERRIES.get())
                    .add(BLUEBERRIES.get())
                    .add(MULBERRIES.get())
                    .add(RASPBERRIES.get())
                    .add(BLACK_RASPBERRIES.get())
                    .add(ACORN.get())
                    .add(ROASTED_ACORN.get())
                    .add(CORN.get())
                    .add(ONION.get())
                    .add(ONION_SLICE.get())
                    .add(PUMPKIN_SLICE.get())
                    .add(RICE.get())
                    .add(GREEN_TEA_LEAVES.get())
                    .add(YELLOW_TEA_LEAVES.get())
                    .add(BLACK_TEA_LEAVES.get())
                    .add(COFFEE_BEANS.get())
                    .add(CORN_ON_A_COB.get())
                    .add(POPCORN.get())
                    .add(RAW_CORN_TORTILLA.get())
                    .add(CORN_TORTILLA.get())
                    .add(CORNBREAD.get())
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
                    .add(AMBROSIA_SALAD.get())
                    .add(JEWELED_RICE_BOWL.get())
                    .add(BAItems.PANETTONE.get())
                    .add(PANETTONE_SLICE.get())
                    .add(BAItems.STOLLEN.get())
                    .add(STOLLEN_SLICE.get())
                    .add(PLAIN_COOKIE.get())
                    .add(SUGAR_COOKIE.get())
                    .add(SNICKERDOODLE.get())
                    .add(ECLIPSE_COOKIE.get())
                    .add(GOLDEN_COOKIE.get())
                    .add(MACARON.get())
                    .add(BLOSSOM_DANGO.get())
                    .add(GROVE_DANGO.get())
                    .add(TWILIGHT_DANGO.get())
                    .add(SUNRISE_DANGO.get())
                    .add(CAKE_SLICE.get())
                    .add(PUMPKIN_PIE_SLICE.get());
            FlavoredItems.forEachRegistered(FlavoredItems.ItemType.COOKIE, (flavor, item) -> this.tag(FOODS).add(item.get()));
            FlavoredItems.forEachRegistered(FlavoredItems.ItemType.POPSICLE, (flavor, item) -> this.tag(FOODS).add(item.get()));
            FlavoredItems.forEachRegistered(FlavoredItems.ItemType.GUMMY, (flavor, item) -> this.tag(FOODS).add(item.get()));
            FlavoredItems.forEachRegistered(FlavoredItems.ItemType.PIE, (flavor, item) -> this.tag(FOODS).add(item.get()));
            for (var entry : FlavoredItems.CAKE_BLOCKS_BY_ID.entrySet()) this.tag(FOODS).add(entry.getValue().get().asItem());
            FlavoredItems.forEachRegistered(FlavoredItems.ItemType.CAKE, (flavor, item) -> this.tag(FOODS).add(item.get()));


            this.tag(FOODS_FRUIT)
                    .add(CHERRIES.get())
                    .add(GOLDEN_CHERRIES.get())
                    .add(APPLE_SLICE.get())
                    .add(GREEN_APPLE.get())
                    .add(GRAPEFRUIT.get())
                    .add(GRAPEFRUIT_SLICE.get())
                    .add(ORANGE.get())
                    .add(ORANGE_SLICE.get())
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
                    .add(BLACK_RASPBERRIES.get())
                    .addOptional(ModUtil.AT.asResource("blood_orange"));

            this.tag(FOODS_BERRY)
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

            this.tag(FOODS_VEGETABLE)
                    .add(CORN.get())
                    .add(CORN_ON_A_COB.get());

            this.tag(FOODS_RAW_MEAT);
            this.tag(FOODS_COOKED_MEAT)
                    .add(JERKY.get());
            this.tag(FOODS_RAW_FISH);
            this.tag(FOODS_COOKED_FISH);
            this.tag(FOODS_FOOD_POISONING);

            this.tag(FOODS_BREAD)
                    .add(CORNBREAD.get())
                    .add(BANANA_BREAD.get());

            this.tag(FOODS_SOUP)
                    .add(AMBROSIA_SALAD.get());

            FlavoredItems.forEachRegistered(FlavoredItems.ItemType.POPSICLE, (flavor, item) -> this.tag(FOODS_CANDY).add(item.get()));

            FlavoredItems.forEachRegistered(FlavoredItems.ItemType.PIE, (flavor, item) -> this.tag(FOODS_PIE).add(item.get()));

            FlavoredItems.forEachRegistered(FlavoredItems.ItemType.COOKIE, (flavor, item) -> this.tag(FOODS_COOKIE).add(item.get()));
            this.tag(FOODS_COOKIE)
                    .add(PLAIN_COOKIE.get())
                    .add(SUGAR_COOKIE.get())
                    .add(SNICKERDOODLE.get())
                    .add(ECLIPSE_COOKIE.get())
                    .add(GOLDEN_COOKIE.get())
                    .add(MACARON.get());

            this.tag(FOODS_GOLDEN)
                    .add(GOLDEN_CHERRIES.get())
                    .add(GOLDEN_ORANGE.get())
                    .add(GOLDEN_STRAWBERRIES.get())
                    .add(WINGED_GOLDEN_STRAWBERRY.get())
                    .add(GOLDEN_COOKIE.get());

            this.tag(FOODS_EDIBLE_WHEN_PLACED)
                    .add(BAItems.PANETTONE.get())
                    .add(BAItems.STOLLEN.get());
            for (var entry : FlavoredItems.CAKE_BLOCKS_BY_ID.entrySet()) this.tag(FOODS_EDIBLE_WHEN_PLACED).add(entry.getValue().get().asItem());

            this.tag(DRINKS)
                    .add(WATER_MUG.get())
                    .add(TISANE.get())
                    .add(MILK_BOTTLE.get())
                    .add(CHOCOLATE_MILK_BOTTLE.get())
                    .add(STRAWBERRY_MILK_BOTTLE.get())
                    .add(BLUEBERRY_MILK_BOTTLE.get())
                    .add(BANANA_MILK_BOTTLE.get())
                    .add(PEACH_MILK_BOTTLE.get())
                    .add(CARROT_MILK_BOTTLE.get())
                    .add(COFFEE_MILK_BOTTLE.get())
                    .add(HORCHATA.get())
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
                    .add(CHERRY_LIME_REFRESHER.get());
            this.tag(DRINKS_WATER);
            this.tag(DRINKS_WATERY)
                    .add(WATER_MUG.get());
            this.tag(DRINKS_JUICE)
                    .add(APPLE_JUICE.get())
                    .add(APPLE_CIDER.get())
                    .add(ORANGE_JUICE.get());
            this.tag(DRINKS_MILK)
                    .add(MILK_BOTTLE.get());
            this.tag(DRINKS_HONEY);

            this.tag(SEEDS)
                    .add(POMEGRANATE_SEEDS.get())
                    .add(CORN_KERNELS.get());
            this.tag(VILLAGER_PLANTABLE_SEEDS)
                    .add(CORN_KERNELS.get());

            this.tag(FOX_FOOD)
                    .add(COFFEE_CHERRIES.get())
                    .add(CRANBERRIES.get())
                    .add(STRAWBERRIES.get())
                    .add(GOLDEN_STRAWBERRIES.get())
                    .add(SALMONBERRIES.get())
                    .add(BLUEBERRIES.get())
                    .add(MULBERRIES.get())
                    .add(RASPBERRIES.get())
                    .add(BLACK_RASPBERRIES.get());

            this.tag(DYEABLE)
                    .add(SUGAR_COOKIE.get())
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

    public static class BAMobEffectTagProvider extends IntrinsicHolderTagsProvider<MobEffect> {
        public BAMobEffectTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, Registries.MOB_EFFECT, lookupProvider, mobEffect -> BuiltInRegistries.MOB_EFFECT.getResourceKey(mobEffect).orElseThrow(), BonAppetit.ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            this.tag(LETHAL)
                    .add(NUZLOCKE.getKey());

            this.tag(CHOCOLATE_MILK_CURABLES)
                    .add(MOVEMENT_SLOWDOWN.getKey())
                    .add(WEAKNESS.getKey());
            this.tag(STRAWBERRY_MILK_CURABLES)
                    .add(POISON.getKey())
                    .add(SEEDED.getKey());
            this.tag(BLUEBERRY_MILK_CURABLES)
                    .add(WEAVING.getKey())
                    .add(OOZING.getKey());
            this.tag(BANANA_MILK_CURABLES)
                    .add(LEVITATION.getKey())
                    .add(MOVEMENT_SLOWDOWN.getKey());
            this.tag(PEACH_MILK_CURABLES)
                    .add(POISON.getKey())
                    .add(WITHER.getKey());
            this.tag(CARROT_MILK_CURABLES)
                    .add(BLINDNESS.getKey())
                    .add(DARKNESS.getKey());
            this.tag(COFFEE_MILK_CURABLES)
                    .add(DIG_SLOWDOWN.getKey())
                    .add(MOVEMENT_SLOWDOWN.getKey());
        }
    }
}