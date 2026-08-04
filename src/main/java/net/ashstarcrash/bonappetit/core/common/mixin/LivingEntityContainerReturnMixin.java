package net.ashstarcrash.bonappetit.core.common.mixin;

import net.ashstarcrash.bonappetit.BAConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityContainerReturnMixin {
    @Redirect(method = "completeUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"))
    private void bonappetit$redirectContainerToInventory(LivingEntity self, InteractionHand hand, ItemStack itemstack) {
        if (!BAConfig.SMART_CONTAINER_RETURN.get() || !(self instanceof Player player) || player.hasInfiniteMaterials()) {
            self.setItemInHand(hand, itemstack);
            return;
        }

        ItemStack original = self.getItemInHand(hand);
        boolean isContainerSwap = !itemstack.isEmpty() && !ItemStack.isSameItem(itemstack, original) && itemstack.getMaxStackSize() > 1;
        if (isContainerSwap) {
            ItemStack containerCopy = itemstack.copy();
            if (player.getInventory().add(containerCopy)) {
                self.setItemInHand(hand, ItemStack.EMPTY);
                return;
            }
        }

        self.setItemInHand(hand, itemstack);
    }
}