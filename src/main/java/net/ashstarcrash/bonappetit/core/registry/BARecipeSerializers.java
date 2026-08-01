package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.common.recipe.CookingPotRecipe;
import net.ashstarcrash.bonappetit.core.common.recipe.FoodServingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BARecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, BonAppetit.ID);

    public static final Supplier<RecipeSerializer<?>> COOKING = RECIPE_SERIALIZERS.register("cooking", CookingPotRecipe.Serializer::new);

    public static final Supplier<SimpleCraftingRecipeSerializer<?>> FOOD_SERVING =
            RECIPE_SERIALIZERS.register("food_serving", () -> new SimpleCraftingRecipeSerializer<>(FoodServingRecipe::new));
    //public static final Supplier<SimpleCraftingRecipeSerializer<?>> DOUGH =
    //        RECIPE_SERIALIZERS.register("dough", () -> new SimpleCraftingRecipeSerializer<>(DoughRecipe::new));
}