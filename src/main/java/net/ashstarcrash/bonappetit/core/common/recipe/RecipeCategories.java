package net.ashstarcrash.bonappetit.core.common.recipe;

import com.google.common.collect.ImmutableList;
import net.ashstarcrash.bonappetit.core.registry.BARecipeTypes;
import net.ashstarcrash.bonappetit.core.content.blockentity.CookingPotRecipeBookTab;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;

public class RecipeCategories {
    public static RecipeBookCategories COOKING_SEARCH;
    public static RecipeBookCategories COOKING_MEALS;
    public static RecipeBookCategories COOKING_DRINKS;
    public static RecipeBookCategories COOKING_MISC;

    public static void init(RegisterRecipeBookCategoriesEvent event) {
        COOKING_SEARCH = RecipeBookCategories.valueOf("BONAPPETIT_COOKING_SEARCH");
        COOKING_MEALS = RecipeBookCategories.valueOf("BONAPPETIT_COOKING_MEALS");
        COOKING_DRINKS = RecipeBookCategories.valueOf("BONAPPETIT_COOKING_DRINKS");
        COOKING_MISC = RecipeBookCategories.valueOf("BONAPPETIT_COOKING_MISC");

        event.registerBookCategories(RecipeBookType.valueOf("BONAPPETIT_COOKING"),
                ImmutableList.of(COOKING_SEARCH, COOKING_MEALS, COOKING_DRINKS, COOKING_MISC));

        event.registerAggregateCategory(COOKING_SEARCH,
                ImmutableList.of(COOKING_MEALS, COOKING_DRINKS, COOKING_MISC));

        event.registerRecipeCategoryFinder(BARecipeTypes.COOKING.get(), recipe -> {
            if (recipe.value() instanceof CookingPotRecipe cookingRecipe) {
                CookingPotRecipeBookTab tab = cookingRecipe.getRecipeBookTab();
                if (tab != null) {
                    return switch (tab) {
                        case MEALS -> COOKING_MEALS;
                        case DRINKS -> COOKING_DRINKS;
                        case MISC -> COOKING_MISC;
                    };
                }
            }
            return COOKING_MISC;
        });
    }
}