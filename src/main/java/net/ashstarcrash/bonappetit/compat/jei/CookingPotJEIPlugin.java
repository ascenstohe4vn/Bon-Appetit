package net.ashstarcrash.bonappetit.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.common.recipe.CookingPotRecipe;
import net.ashstarcrash.bonappetit.core.content.blockentity.CookingPotMenu;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.ashstarcrash.bonappetit.core.registry.BAMenuTypes;
import net.ashstarcrash.bonappetit.core.registry.BARecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class CookingPotJEIPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_ID =
            ResourceLocation.fromNamespaceAndPath(BonAppetit.ID, "jei_plugin");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CookingPotRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        var level = Minecraft.getInstance().level;
        if (level == null) return;

        List<RecipeHolder<CookingPotRecipe>> recipes = level.getRecipeManager()
                .getAllRecipesFor(BARecipeTypes.COOKING.get());

        registration.addRecipes(CookingPotRecipeCategory.TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BAItems.COOKING_POT.get()), CookingPotRecipeCategory.TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(
                CookingPotMenu.class,
                BAMenuTypes.COOKING_POT.get(),
                CookingPotRecipeCategory.TYPE,
                0, 6,
                9, 36
        );
    }
}