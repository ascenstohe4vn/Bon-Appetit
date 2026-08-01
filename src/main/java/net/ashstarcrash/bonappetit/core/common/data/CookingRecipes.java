package net.ashstarcrash.bonappetit.core.common.data;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DifferenceIngredient;

import static net.minecraft.world.item.Items.*;

public class CookingRecipes {
    public static void register(RecipeOutput output) {
        cookMiscellaneous(output);
        cookMinecraftSoups(output);
        cookMeals(output);
    }

    private static Ingredient vegetablesPatch() {
        return DifferenceIngredient.of(Ingredient.of(Tags.Items.FOODS_VEGETABLE), Ingredient.of(MELON_SLICE));
    }

    private static void cookMiscellaneous(RecipeOutput output) {

    }

    private static void cookMinecraftSoups(RecipeOutput output) {

    }

    private static void cookMeals(RecipeOutput output) {

    }
}