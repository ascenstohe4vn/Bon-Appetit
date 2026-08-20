package net.ashstarcrash.bonappetit.compat.emi;

import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.ashstarcrash.bonappetit.core.registry.BATags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.FlowerBlock;
import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiPatternCraftingRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.GeneratedSlotWidget;
import dev.emi.emi.api.widget.SlotWidget;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EmiTisaneRecipe extends EmiPatternCraftingRecipe {
    private static final List<Item> FLOWERS = EmiPort.getItemRegistry().stream()
            .filter(i -> i instanceof BlockItem bi && bi.getBlock() instanceof FlowerBlock)
            .collect(Collectors.toList());

    public EmiTisaneRecipe(ResourceLocation id) {
        super(List.of(
                        EmiStack.of(BAItems.GLASS_MUG.get()),
                        EmiIngredient.of(ItemTags.LEAVES),
                        EmiIngredient.of(BATags.Items.FOODS_APPLE),
                        EmiIngredient.of(FLOWERS.stream().map(EmiStack::of).collect(Collectors.toList()))
                ),
                EmiStack.of(BAItems.TISANE.get()), id);
    }

    @Override
    public SlotWidget getInputWidget(int slot, int x, int y) {
        if (slot == 0) {
            return new SlotWidget(EmiStack.of(BAItems.GLASS_MUG.get()), x, y);
        } else if (slot == 1) {
            return new SlotWidget(EmiIngredient.of(ItemTags.LEAVES), x, y);
        } else if (slot == 2) {
            return new SlotWidget(EmiIngredient.of(BATags.Items.FOODS_APPLE), x, y);
        } else if (slot == 3) {
            return new GeneratedSlotWidget(r -> EmiStack.of(getFlower(r)), unique, x, y);
        }
        return new SlotWidget(EmiStack.EMPTY, x, y);
    }

    @Override
    public SlotWidget getOutputWidget(int x, int y) {
        return new GeneratedSlotWidget(r -> {
            FlowerBlock block = (FlowerBlock) ((BlockItem) getFlower(r)).getBlock();
            ItemStack stack = new ItemStack(BAItems.TISANE.get());
            stack.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, block.getSuspiciousEffects());
            return EmiStack.of(stack);
        }, unique, x, y);
    }

    private Item getFlower(Random random) {
        if (FLOWERS.isEmpty()) {
            return net.minecraft.world.item.Items.DANDELION;
        }
        return FLOWERS.get(random.nextInt(FLOWERS.size()));
    }
}