package net.ashstarcrash.bonappetit.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.ashstarcrash.bonappetit.core.common.recipe.CookingPotRecipe;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

public class CookingPotRecipeCategory implements IRecipeCategory<RecipeHolder<CookingPotRecipe>> {
    public static final RecipeType<RecipeHolder<CookingPotRecipe>> TYPE =
            RecipeType.create(BonAppetit.ID, "cooking_pot", (Class<RecipeHolder<CookingPotRecipe>>) (Class<?>) RecipeHolder.class);

    public static final ResourceLocation TEXTURE =
            ModUtil.BA.asResource("textures/gui/jei/cooking_pot.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final Component title;

    public CookingPotRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 130, 66); //random values for now until i actually make a gui texture
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BAItems.COOKING_POT.get()));
        this.title = Component.translatable("jei.bonappetit.category.cooking_pot");
    }

    @Override
    public @NotNull RecipeType<RecipeHolder<CookingPotRecipe>> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, RecipeHolder<CookingPotRecipe> recipeHolder, @NotNull IFocusGroup focuses) {
        CookingPotRecipe recipe = recipeHolder.value();

        int slotX = 5;
        int slotY = 5;
        int index = 0;
        for (var ingredient : recipe.getIngredients()) {
            int x = slotX + (index % 3) * 18;
            int y = slotY + (index / 3) * 18;
            builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredient);
            index++;
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 106, 24)
                .addItemStack(recipe.getResultItem(null));

        ItemStack container = recipe.getOutputContainer();
        if (!container.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 106, 44)
                    .addItemStack(container);
        }
    }

    @Override
    public void draw(RecipeHolder<CookingPotRecipe> recipe, @NotNull IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        int cookTime = recipe.value().getCookTime();
        Component time = Component.translatable("jei.bonappetit.category.cooking_pot.time", cookTime / 20);
        guiGraphics.drawString(Minecraft.getInstance().font, time, 5, 52, 0x404040, false);
    }
}