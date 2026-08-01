package net.ashstarcrash.bonappetit.core.content.item;

import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PopcornItem extends Item {
    private static final double KERNEL_CHANCE = 0.1;
    public PopcornItem(Properties properties) {super(properties);}

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (!world.isClientSide() && entityLiving instanceof Player player) {
            if (stack.getItem() == this) {
                if (!player.hasInfiniteMaterials()) {
                    if (world.getRandom().nextFloat() < KERNEL_CHANCE) {
                        ItemStack returnStack = new ItemStack(BAItems.CORN_KERNELS.get());
                        if (!player.getInventory().add(returnStack)) {
                            player.drop(returnStack, false);
                        }
                    }
                }
            }
            return super.finishUsingItem(stack, world, entityLiving);
        }
        return stack;
    }
}