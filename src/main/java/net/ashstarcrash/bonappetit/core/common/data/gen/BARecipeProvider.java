package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BATags;
import net.ashstarcrash.bonappetit.core.common.recipe.CookingPotRecipeBuilder;
import net.ashstarcrash.bonappetit.core.content.blockentity.CookingPotRecipeBookTab;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.crafting.DifferenceIngredient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.ashstarcrash.bonappetit.core.registry.BAItems.*;
import static net.minecraft.world.item.Items.*;

public class BARecipeProvider extends RecipeProvider implements IConditionBuilder {
    public BARecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {super(output, registries);}

    private static Ingredient vegetablesPatch() {
        return DifferenceIngredient.of(Ingredient.of(Tags.Items.FOODS_VEGETABLE), Ingredient.of(MELON_SLICE));
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, PITCHFORK.get(), 1).pattern(" ##").pattern(" S#").pattern("S  ")
                .define('#', IRON_BARS).define('S', STICK).unlockedBy("has_iron_bars", has(IRON_BARS)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, PAPER_PLATE.get(), 3).pattern("###")
                .define('#', PAPER).unlockedBy("has_paper", has(PAPER)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, PIE_CRUST.get(), 2).pattern("W W").pattern(" W ")
                .define('W', WHEAT).unlockedBy("has_wheat", has(WHEAT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, GLASS_PITCHER.get(), 5).pattern("# #").pattern("# #").pattern(" # ")
                .define('#', GLASS).unlockedBy("has_glass", has(GLASS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, GLASS_COCKTAIL.get(), 6).pattern("# #").pattern(" # ").pattern("###")
                .define('#', GLASS).unlockedBy("has_glass", has(GLASS)).save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, GOLDEN_CHERRIES.get(), 1).pattern("GGG").pattern("G#G").pattern("GGG")
                .define('#', CHERRIES.get()).define('G', GOLD_INGOT).unlockedBy("has_gold_ingot", has(GOLD_INGOT)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CAKE_SLICE.get(), 7).requires(CAKE)
                .unlockedBy("has_cake", has(CAKE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CAKE, 1).requires(CAKE_SLICE).requires(CAKE_SLICE).requires(CAKE_SLICE).requires(CAKE_SLICE).requires(CAKE_SLICE).requires(CAKE_SLICE).requires(CAKE_SLICE)
                .unlockedBy("has_cake", has(CAKE)).save(recipeOutput, "bonappetit:cake_from_cake_slices");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CHERRY_PIE, 2).requires(CHERRIES).requires(CHERRIES).requires(CHERRIES).requires(SUGAR).requires(Tags.Items.EGGS).requires(PIE_CRUST)
                .unlockedBy("has_cherries", has(CHERRIES)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CHERRY_PIE_SLICE.get(), 4).requires(CHERRY_PIE)
                .unlockedBy("has_cherry_pie", has(CHERRY_PIE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CHERRY_PIE, 1).requires(CHERRY_PIE_SLICE).requires(CHERRY_PIE_SLICE).requires(CHERRY_PIE_SLICE).requires(CHERRY_PIE_SLICE)
                .unlockedBy("has_cherry_pie_slice", has(CHERRY_PIE_SLICE)).save(recipeOutput, "bonappetit:cherry_pie_from_cherry_pie_slices");
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, APPLE_SLICE.get(), 2).requires(APPLE).unlockedBy("has_apple", has(APPLE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, CANDY_APPLE.get(), 1).pattern("T").pattern("S").pattern("#").define('#', APPLE).define('S', SUGAR).define('T', STICK).unlockedBy("has_apple", has(APPLE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, CARAMEL_APPLE.get(), 1).pattern("T").pattern("C").pattern("#").define('#', GREEN_APPLE).define('C', CARAMEL).define('T', STICK).unlockedBy("has_apple", has(APPLE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, APPLE_PIE, 2).requires(APPLE).requires(APPLE).requires(APPLE).requires(SUGAR).requires(Tags.Items.EGGS).requires(PIE_CRUST)
                .unlockedBy("has_apple", has(APPLE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, APPLE_PIE_SLICE.get(), 4).requires(APPLE_PIE)
                .unlockedBy("has_apple_pie", has(APPLE_PIE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, APPLE_PIE, 1).requires(APPLE_PIE_SLICE).requires(APPLE_PIE_SLICE).requires(APPLE_PIE_SLICE).requires(APPLE_PIE_SLICE)
                .unlockedBy("has_apple_pie_slice", has(APPLE_PIE_SLICE)).save(recipeOutput, "bonappetit:apple_pie_from_apple_pie_slices");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, GRAPEFRUIT_SLICE.get(), 2).requires(GRAPEFRUIT)
                .unlockedBy("has_grapefruit", has(GRAPEFRUIT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, GRAPEFRUIT_PIE, 2).requires(GRAPEFRUIT).requires(GRAPEFRUIT).requires(GRAPEFRUIT).requires(SUGAR).requires(Tags.Items.EGGS).requires(PIE_CRUST)
                .unlockedBy("has_grapefruit", has(GRAPEFRUIT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, GRAPEFRUIT_PIE_SLICE.get(), 4).requires(GRAPEFRUIT_PIE)
                .unlockedBy("has_grapefruit_pie", has(GRAPEFRUIT_PIE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, GRAPEFRUIT_PIE, 1).requires(GRAPEFRUIT_PIE_SLICE).requires(GRAPEFRUIT_PIE_SLICE).requires(GRAPEFRUIT_PIE_SLICE).requires(GRAPEFRUIT_PIE_SLICE)
                .unlockedBy("has_grapefruit_pie_slice", has(GRAPEFRUIT_PIE_SLICE)).save(recipeOutput, "bonappetit:grapefruit_pie_from_grapefruit_pie_slices");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ORANGE_SLICE.get(), 2).requires(ORANGE)
                .unlockedBy("has_orange", has(ORANGE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ORANGE_PIE, 2).requires(ORANGE).requires(ORANGE).requires(ORANGE).requires(SUGAR).requires(Tags.Items.EGGS).requires(PIE_CRUST)
                .unlockedBy("has_orange", has(ORANGE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ORANGE_PIE_SLICE.get(), 4).requires(ORANGE_PIE)
                .unlockedBy("has_orange_pie", has(ORANGE_PIE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ORANGE_PIE, 1).requires(ORANGE_PIE_SLICE).requires(ORANGE_PIE_SLICE).requires(ORANGE_PIE_SLICE).requires(ORANGE_PIE_SLICE)
                .unlockedBy("has_orange_pie_slice", has(ORANGE_PIE_SLICE)).save(recipeOutput, "bonappetit:orange_pie_from_orange_pie_slices");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, MANGO_PIE, 2).requires(MANGO).requires(MANGO).requires(MANGO).requires(SUGAR).requires(Tags.Items.EGGS).requires(PIE_CRUST)
                .unlockedBy("has_mango", has(MANGO)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, MANGO_PIE_SLICE.get(), 4).requires(MANGO_PIE)
                .unlockedBy("has_mango_pie", has(MANGO_PIE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, MANGO_PIE, 1).requires(MANGO_PIE_SLICE).requires(MANGO_PIE_SLICE).requires(MANGO_PIE_SLICE).requires(MANGO_PIE_SLICE)
                .unlockedBy("has_mango_pie_slice", has(MANGO_PIE_SLICE)).save(recipeOutput, "bonappetit:mango_pie_from_mango_pie_slices");
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, PUMPKIN_SLICE.get(), 9).requires(PUMPKIN)
                .unlockedBy("has_pumpkin", has(PUMPKIN)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, PUMPKIN_PIE, 2).requires(PUMPKIN).requires(PUMPKIN).requires(PUMPKIN).requires(SUGAR).requires(Tags.Items.EGGS).requires(PIE_CRUST)
                .unlockedBy("has_pumpkin", has(PUMPKIN)).save(recipeOutput, "bonappetit:pumpkin_pie");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, PUMPKIN_PIE_SLICE.get(), 4).requires(PUMPKIN_PIE)
                .unlockedBy("has_pumpkin_pie", has(PUMPKIN_PIE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, PUMPKIN_PIE, 1).requires(PUMPKIN_PIE_SLICE).requires(PUMPKIN_PIE_SLICE).requires(PUMPKIN_PIE_SLICE).requires(PUMPKIN_PIE_SLICE)
                .unlockedBy("has_pumpkin_pie_slice", has(PUMPKIN_PIE_SLICE)).save(recipeOutput, "bonappetit:pumpkin_pie_from_pumpkin_pie_slices");
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LEMON_SLICE.get(), 2).requires(LEMON)
                .unlockedBy("has_lemon", has(LEMON)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LEMON_TART, 2).requires(LEMON).requires(LEMON).requires(LEMON).requires(SUGAR).requires(Tags.Items.EGGS).requires(PIE_CRUST)
                .unlockedBy("has_lemon", has(LEMON)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LEMON_TART_SLICE.get(), 4).requires(LEMON_TART)
                .unlockedBy("has_lemon_tart", has(LEMON_TART)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LEMON_TART, 1).requires(LEMON_TART_SLICE).requires(LEMON_TART_SLICE).requires(LEMON_TART_SLICE).requires(LEMON_TART_SLICE)
                .unlockedBy("has_lemon_tart_slice", has(LEMON_TART_SLICE)).save(recipeOutput, "bonappetit:lemon_tart_from_lemon_tart_slices");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LEMON_CAKE_SLICE.get(), 7).requires(LEMON_CAKE)
                .unlockedBy("has_lemon_cake", has(LEMON_CAKE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LEMON_CAKE, 1).requires(LEMON_CAKE_SLICE).requires(LEMON_CAKE_SLICE).requires(LEMON_CAKE_SLICE).requires(LEMON_CAKE_SLICE).requires(LEMON_CAKE_SLICE).requires(LEMON_CAKE_SLICE).requires(LEMON_CAKE_SLICE)
                .unlockedBy("has_lemon_cake", has(LEMON_CAKE)).save(recipeOutput, "bonappetit:lemon_cake_from_lemon_cake_slices");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LIME_SLICE.get(), 2).requires(LIME)
                .unlockedBy("has_lime", has(LIME)).save(recipeOutput);
        CookingPotRecipeBuilder.cookingPotRecipe(CANDIED_LIME_SLICE.get(), 3, 100, 0.5f, null)
                .addIngredient(HONEY_BOTTLE)
                .addIngredient(LIME_SLICE, 3)
                .setRecipeBookTab(CookingPotRecipeBookTab.SWEETS)
                .unlockedBy("has_lime_slice", has(LIME_SLICE))
                .build(recipeOutput, "bonappetit:candied_lime_slices_from_cooking");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LIME_CAKE_SLICE.get(), 7).requires(LIME_CAKE)
                .unlockedBy("has_lime_cake", has(LIME_CAKE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LIME_CAKE, 1).requires(LIME_CAKE_SLICE).requires(LIME_CAKE_SLICE).requires(LIME_CAKE_SLICE).requires(LIME_CAKE_SLICE).requires(LIME_CAKE_SLICE).requires(LIME_CAKE_SLICE).requires(LIME_CAKE_SLICE)
                .unlockedBy("has_lime_cake", has(LIME_CAKE)).save(recipeOutput, "bonappetit:lime_cake_from_lime_cake_slices");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, MELON_SLICE, 9).requires(MELON)
                .unlockedBy("has_melon", has(MELON)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, POMEGRANATE_SLICE.get(), 2).requires(POMEGRANATE)
                .unlockedBy("has_pomegranate", has(POMEGRANATE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, POMEGRANATE_SEEDS.get(), 1).requires(POMEGRANATE_SLICE)
                .unlockedBy("has_pomegranate", has(POMEGRANATE)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, DRAGON_FRUIT_SLICE.get(), 2).requires(DRAGON_FRUIT)
                .unlockedBy("has_dragon_fruit", has(DRAGON_FRUIT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, DRAGON_FRUIT_PIE, 2).requires(DRAGON_FRUIT).requires(DRAGON_FRUIT).requires(DRAGON_FRUIT).requires(SUGAR).requires(Tags.Items.EGGS).requires(PIE_CRUST)
                .unlockedBy("has_dragon_fruit", has(DRAGON_FRUIT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, DRAGON_FRUIT_PIE_SLICE.get(), 4).requires(DRAGON_FRUIT_PIE)
                .unlockedBy("has_dragon_fruit_pie", has(DRAGON_FRUIT_PIE)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, DRAGON_FRUIT_PIE, 1).requires(DRAGON_FRUIT_PIE_SLICE).requires(DRAGON_FRUIT_PIE_SLICE).requires(DRAGON_FRUIT_PIE_SLICE).requires(DRAGON_FRUIT_PIE_SLICE)
                .unlockedBy("has_dragon_fruit_pie_slice", has(DRAGON_FRUIT_PIE_SLICE)).save(recipeOutput, "bonappetit:dragon_fruit_pie_from_dragon_fruit_pie_slices");
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, COCONUT_SLICE.get(), 2).requires(COCONUT)
                .unlockedBy("has_coconut", has(COCONUT)).save(recipeOutput);

        //strawberry
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, GOLDEN_STRAWBERRIES.get(), 1).pattern("GGG").pattern("G#G").pattern("GGG")
                .define('#', STRAWBERRIES.get()).define('G', GOLD_INGOT).unlockedBy("has_gold_ingot", has(GOLD_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, WINGED_STRAWBERRY.get(), 1).pattern("F#F")
                .define('#', STRAWBERRIES.get()).define('F', FEATHER).unlockedBy("has_strawberries", has(STRAWBERRIES)).save(recipeOutput);

        //corn
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CORN_KERNELS.get(), 1).requires(CORN)
                .unlockedBy("has_corn", has(CORN)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CORN_ON_A_COB.get(), 1).requires(STICK).requires(CORN)
                .unlockedBy("has_corn", has(CORN)).save(recipeOutput);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(CORN_KERNELS.get()), RecipeCategory.FOOD, POPCORN.get(), 0.25f, 200).unlockedBy("has_corn", has(CORN)).save(recipeOutput);
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(CORN_KERNELS.get()), RecipeCategory.FOOD, POPCORN.get(), 0.25f, 100).unlockedBy("has_corn", has(CORN)).save(recipeOutput, "bonappetit:popcorn_from_smoking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(CORN_KERNELS.get()), RecipeCategory.FOOD, POPCORN.get(), 0.25f, 600).unlockedBy("has_corn", has(CORN)).save(recipeOutput, "bonappetit:popcorn_from_campfire_cooking");
        CookingPotRecipeBuilder.cookingPotRecipe(POPCORN, 1, 100, 0.5f, null)
                .addIngredient(CORN_KERNELS)
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .unlockedBy("has_corn", has(CORN))
                .build(recipeOutput, "bonappetit:popcorn_from_cooking");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CINNAMON_DUST.get(), 2).requires(CINNAMON_STICKS)
                .unlockedBy("has_cinnamon_sticks", has(CINNAMON_STICKS)).save(recipeOutput);

        //basic meals
        CookingPotRecipeBuilder.cookingPotRecipe(CARAMEL.get(), 1, 100, 0.5f, null)
                .addIngredient(SUGAR)
                .addIngredient(WATER_BUCKET)
                .setRecipeBookTab(CookingPotRecipeBookTab.SWEETS)
                .unlockedBy("has_sugar", has(SUGAR))
                .build(recipeOutput, "bonappetit:caramel_from_cooking");

        //meals
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, JOCKEY_SANDWICH.get(), 1).requires(BREAD).requires(COOKED_CHICKEN).requires(JERKY).requires(POTATO).requires(BROWN_MUSHROOM)
                .unlockedBy("has_jerky", has(JERKY)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, PANETTONE.get(), 1)
                .requires(MILK_BUCKET).requires(DOUGH).requires(SUGAR).requires(BATags.Items.FOODS_CITRUS).requires(RAISINS).requires(RAISINS)
                .unlockedBy("has_corn", has(BATags.Items.FOODS_CITRUS)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LEMONADE.get(), 1).requires(LEMON_SLICE).requires(LEMON_SLICE).requires(SUGAR).requires(GLASS_PITCHER).unlockedBy("has_lemon", has(LEMON)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, PINK_LEMONADE.get(), 1).requires(LEMON_SLICE).requires(LEMON_SLICE).requires(CHERRIES).requires(SUGAR).requires(GLASS_PITCHER).unlockedBy("has_lemon", has(LEMON)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, PINK_LEMONADE.get(), 1).requires(LEMONADE).requires(CHERRIES).unlockedBy("has_lemon", has(LEMON)).save(recipeOutput, "bonappetit:pink_lemonade_from_lemonade");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LIMEADE.get(), 1).requires(LIME_SLICE).requires(LIME_SLICE).requires(SUGAR).requires(GLASS_PITCHER).unlockedBy("has_lime", has(LIME)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, PINK_LIMEADE.get(), 1).requires(LIME_SLICE).requires(LIME_SLICE).requires(POMEGRANATE_SLICE).requires(POMEGRANATE_SLICE).requires(POMEGRANATE_SLICE).requires(SUGAR).requires(GLASS_PITCHER).unlockedBy("has_lime", has(LIME)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, PINK_LIMEADE.get(), 1).requires(LIMEADE).requires(POMEGRANATE_SLICE).requires(POMEGRANATE_SLICE).requires(POMEGRANATE_SLICE).unlockedBy("has_lime", has(LIME)).save(recipeOutput, "bonappetit:pink_limeade_from_limeade");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, BLUEBERRY_LIMEADE.get(), 1).requires(LIME_SLICE).requires(LIME_SLICE).requires(BLUEBERRIES).requires(SUGAR).requires(GLASS_PITCHER).unlockedBy("has_lime", has(LIME)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, BLUEBERRY_LIMEADE.get(), 1).requires(LIMEADE).requires(BLUEBERRIES).unlockedBy("has_lime", has(LIME)).save(recipeOutput, "bonappetit:blueberry_limeade_from_limeade");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LIME_GREEN_TEA.get(), 1).requires(LIME_SLICE).requires(LIME_SLICE).requires(GREEN_TEA_LEAVES).requires(GREEN_TEA_LEAVES).requires(SUGAR).requires(GLASS_PITCHER).unlockedBy("has_lime", has(LIME)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LIME_GREEN_TEA.get(), 1).requires(LIMEADE).requires(GREEN_TEA_LEAVES).requires(GREEN_TEA_LEAVES).unlockedBy("has_lime", has(LIME)).save(recipeOutput, "bonappetit:lime_green_tea_from_limeade");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, DRAGON_FRUIT_LATTE.get(), 1).requires(DRAGON_FRUIT_SLICE).requires(DRAGON_FRUIT_SLICE).requires(Tags.Items.BUCKETS_MILK).requires(ROSE_BUSH).requires(GLASS_PITCHER).unlockedBy("has_dragon_fruit", has(DRAGON_FRUIT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CHERRY_LIME_RICKEY_REFRESHER.get(), 1).requires(LIME_SLICE).requires(LIME_SLICE).requires(CHERRIES).requires(CHERRIES).requires(GREEN_TEA_LEAVES).requires(ICE /*replace with tag later*/).requires(GLASS_PITCHER).unlockedBy("has_cherries", has(CHERRIES)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CHERRY_LIME_RICKEY_REFRESHER.get(), 1).requires(LIMEADE).requires(CHERRIES).requires(CHERRIES).requires(GREEN_TEA_LEAVES).requires(ICE /*replace with tag later*/).unlockedBy("has_cherries", has(CHERRIES)).save(recipeOutput, "bonappetit:cherry_lime_rickey_refresher_from_limeade");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, JEWELED_RICE_BOWL.get(), 1).requires(DRAGON_FRUIT_SLICE).requires(DRAGON_FRUIT_SLICE).requires(DRAGON_FRUIT_SLICE).requires(POMEGRANATE_SEEDS).requires(LIME).requires(ROASTED_ACORN).requires(RICE).requires(BOWL).unlockedBy("has_dragon_fruit", has(DRAGON_FRUIT)).save(recipeOutput);



        CookingPotRecipeBuilder.cookingPotRecipe(DRAGON_FRUIT_LATTE.get(), 1, 200, 1.0f, GLASS_PITCHER.get())
                .addIngredient(DRAGON_FRUIT_SLICE.get(), 2)
                .addIngredient(MILK_BUCKET)
                .addIngredient(ROSE_BUSH)
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .unlockedBy("has_dragon_fruit", has(DRAGON_FRUIT))
                .build(recipeOutput, "bonappetit:dragon_fruit_latte_from_cooking");



        CookingPotRecipeBuilder.cookingPotRecipe(CHICKEN_QUESADILLA.get(), 1, 200, 1.0f, CORN_TORTILLA.get())
                .addIngredient(COOKED_CHICKEN)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .unlockedBy("has_chicken", has(COOKED_CHICKEN))
                .build(recipeOutput, "bonappetit:chicken_quesadilla_from_cooking");

        CookingPotRecipeBuilder.cookingPotRecipe(JEWELED_RICE_BOWL.get(), 1, 200, 1.0f, BOWL)
                .addIngredient(DRAGON_FRUIT_SLICE.get(), 3)
                .addIngredient(POMEGRANATE_SEEDS.get())
                .addIngredient(LIME.get())
                .addIngredient(ROASTED_ACORN.get())
                .addIngredient(RICE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .unlockedBy("has_dragon_fruit", has(DRAGON_FRUIT))
                .build(recipeOutput, "bonappetit:jeweled_rice_bowl_from_cooking");



        CookingPotRecipeBuilder.cookingPotRecipe(MUSHROOM_STEW, 1, 200, 0.35f, BOWL)
                .addIngredient(RED_MUSHROOM)
                .addIngredient(BROWN_MUSHROOM)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .unlockedBy("has_mushrooms", has(BROWN_MUSHROOM))
                .build(recipeOutput, "bonappetit:mushroom_stew_from_cooking");

        CookingPotRecipeBuilder.cookingPotRecipe(BEETROOT_SOUP, 1, 200, 0.35f, BOWL)
                .addIngredient(BEETROOT, 4)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .unlockedBy("has_beetroot", has(BEETROOT))
                .build(recipeOutput, "bonappetit:beetroot_soup_from_cooking");

        CookingPotRecipeBuilder.cookingPotRecipe(RABBIT_STEW, 1, 200, 0.5f, BOWL)
                .addIngredient(BAKED_POTATO)
                .addIngredient(RABBIT)
                .addIngredient(CARROT)
                .addIngredient(BROWN_MUSHROOM)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .unlockedBy("has_rabbit", has(RABBIT))
                .build(recipeOutput, "bonappetit:rabbit_stew_from_cooking");

        //CookingRecipes.register(recipeOutput);
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");}
    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");}
    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {for(ItemLike itemlike : pIngredients) {SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(recipeOutput, BonAppetit.ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));}}
}
