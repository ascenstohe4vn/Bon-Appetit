package net.ashstarcrash.bonappetit.core.common.mixin;

import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.core.common.util.FoodHealingHelper;
import net.ashstarcrash.bonappetit.core.common.util.IFoodDataOwner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoodData.class)
public abstract class FoodDataMixin implements IFoodDataOwner {
    @Unique
    private Player bonappetit$owner;

    @Override
    public void bonappetit$setOwner(Player player) {
        this.bonappetit$owner = player;
    }

    @Override
    public Player bonappetit$getOwner() {
        return this.bonappetit$owner;
    }

    @Inject(method = "eat(IF)V", at = @At("HEAD"), cancellable = true)
    private void bonappetit$cancelEatRaw(int nutrition, float saturationModifier, CallbackInfo ci) {
        if (!BAConfig.HUNGER_BAR_ENABLED.get()) {
            if (this.bonappetit$owner != null) {
                float saturation = net.minecraft.world.food.FoodConstants.saturationByModifier(nutrition, saturationModifier);
                FoodHealingHelper.applyRaw(this.bonappetit$owner, nutrition, saturation);
            }
            ci.cancel();
        }
    }

    @Inject(method = "eat(Lnet/minecraft/world/food/FoodProperties;)V", at = @At("HEAD"), cancellable = true)
    private void bonappetit$cancelEatProps(FoodProperties foodProperties, CallbackInfo ci) {
        if (!BAConfig.HUNGER_BAR_ENABLED.get()) {
            if (this.bonappetit$owner != null) {
                FoodHealingHelper.applyRaw(this.bonappetit$owner, foodProperties.nutrition(), foodProperties.saturation());
            }
            ci.cancel();
        }
    }

    @Inject(method = "tick(Lnet/minecraft/world/entity/player/Player;)V", at = @At("HEAD"), cancellable = true)
    private void bonappetit$cancelTick(Player player, CallbackInfo ci) {
        if (!BAConfig.HUNGER_BAR_ENABLED.get()) {
            ((FoodData)(Object)this).setExhaustion(0.0F);
            ((FoodData)(Object)this).setSaturation(0.0F);
            ci.cancel();
        }
    }

    @Inject(method = "needsFood", at = @At("HEAD"), cancellable = true)
    private void bonappetit$alwaysNeedsFood(CallbackInfoReturnable<Boolean> cir) {
        if (!BAConfig.HUNGER_BAR_ENABLED.get()) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(method = "addExhaustion", at = @At("HEAD"), cancellable = true)
    private void bonappetit$cancelExhaustion(float exhaustion, CallbackInfo ci) {
        if (!BAConfig.HUNGER_BAR_ENABLED.get()) {
            ci.cancel();
        }
    }
}