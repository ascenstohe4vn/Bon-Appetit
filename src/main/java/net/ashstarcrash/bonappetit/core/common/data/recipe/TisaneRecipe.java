package net.ashstarcrash.bonappetit.core.common.data.recipe;

import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.ashstarcrash.bonappetit.core.registry.BARecipeSerializers;
import net.ashstarcrash.bonappetit.core.registry.BATags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import org.jetbrains.annotations.NotNull;

public class TisaneRecipe extends CustomRecipe {
    public TisaneRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, @NotNull Level level) {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemstack = input.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.is(ItemTags.LEAVES) && !flag2) {
                    flag2 = true;
                } else if (itemstack.is(BATags.Items.FOODS_APPLE) && !flag1) {
                    flag1 = true;
                } else if (itemstack.is(ItemTags.SMALL_FLOWERS) && !flag) {
                    flag = true;
                } else {
                    if (!itemstack.is(BAItems.GLASS_MUG) || flag3) {
                        return false;
                    }

                    flag3 = true;
                }
            }
        }

        return flag && flag2 && flag1 && flag3;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input, HolderLookup.@NotNull Provider registries) {
        ItemStack itemstack = new ItemStack(BAItems.TISANE.get(), 1);

        for (int i = 0; i < input.size(); ++i) {
            ItemStack itemstack1 = input.getItem(i);
            if (!itemstack1.isEmpty()) {
                Block block = Block.byItem(itemstack1.getItem());
                SuspiciousEffectHolder suspiciouseffectholder = SuspiciousEffectHolder.tryGet(block);

                if (suspiciouseffectholder != null) {
                    itemstack.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, suspiciouseffectholder.getSuspiciousEffects());
                    break;
                }
            }
        }

        return itemstack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return BARecipeSerializers.TISANE.get();
    }
}
