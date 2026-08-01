package net.ashstarcrash.bonappetit.core.common;

import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.List;
import java.util.function.Supplier;

public class EnumParameters {
    public static final EnumProxy<RecipeBookCategories> PROXY_COOKING_SEARCH = new EnumProxy<>(
            RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(Items.COMPASS))
    );
    public static final EnumProxy<RecipeBookCategories> PROXY_COOKING_MEALS = new EnumProxy<>(
            RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(BAItems.CHICKEN_QUESADILLA.get()))
    );
    public static final EnumProxy<RecipeBookCategories> PROXY_COOKING_DRINKS = new EnumProxy<>(
            RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(BAItems.APPLE_CIDER.get()))
    );
    public static final EnumProxy<RecipeBookCategories> PROXY_COOKING_MISC = new EnumProxy<>(
            RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(BAItems.CHOCOLATE_BAR.get()), new ItemStack(BAItems.BROWNIE.get()))
    );
}