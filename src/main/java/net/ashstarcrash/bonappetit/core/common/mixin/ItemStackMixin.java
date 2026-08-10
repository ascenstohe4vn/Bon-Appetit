package net.ashstarcrash.bonappetit.core.common.mixin;

import net.ashstarcrash.bonappetit.BAConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "getUseDuration", at = @At("RETURN"), cancellable = true)
    private void bonappetit$scaleFoodUseDuration(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        if (entity != null && !BAConfig.VANILLA_HUNGER_BAR.get() && entity.hasEffect(MobEffects.HUNGER)) {
            ItemStack self = (ItemStack) (Object) this;
            if (self.has(DataComponents.FOOD)) {
                MobEffectInstance hunger = entity.getEffect(MobEffects.HUNGER);
                if (hunger != null) {
                    int slowdownFactor = hunger.getAmplifier() + 2;
                    cir.setReturnValue(cir.getReturnValue() * slowdownFactor);
                }
            }
        }
    }
}