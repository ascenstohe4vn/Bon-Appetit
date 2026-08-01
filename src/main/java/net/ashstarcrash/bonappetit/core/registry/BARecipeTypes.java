package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.common.recipe.CookingPotRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BARecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, BonAppetit.ID);

    public static final Supplier<RecipeType<CookingPotRecipe>> COOKING = RECIPE_TYPES.register("cooking", () -> registerRecipeType("cooking"));
    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>() {
            public String toString() {
                return BonAppetit.ID + ":" + identifier;
            }
        };
    }
}