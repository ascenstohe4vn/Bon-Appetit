package net.ashstarcrash.bonappetit.core.common.mixin;

import net.ashstarcrash.bonappetit.core.common.util.IFoodDataOwner;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void bonappetit$linkFoodData(CallbackInfo ci) {
        Player self = (Player) (Object) this;
        ((IFoodDataOwner)self.getFoodData()).bonappetit$setOwner(self);
    }
}