package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.core.common.data.CookingRecipes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault @MethodsReturnNonnullByDefault
public class BARecipes extends RecipeProvider {
    public BARecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
       CookingRecipes.register(output);
    }
}