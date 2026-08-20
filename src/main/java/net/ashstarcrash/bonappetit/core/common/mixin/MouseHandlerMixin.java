package net.ashstarcrash.bonappetit.core.common.mixin;

import net.ashstarcrash.bonappetit.core.common.util.EffectRenderingInventoryScreenMixinAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Shadow @Final private Minecraft minecraft;
    @Shadow private double xpos;
    @Shadow private double ypos;

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void bonappetit$onScroll(long windowPointer, double xOffset, double yOffset, CallbackInfo ci) {
        if (windowPointer != Minecraft.getInstance().getWindow().getWindow()) return;
        if (!(this.minecraft.screen instanceof EffectRenderingInventoryScreen<?> screen)) return;
        if (this.minecraft.getOverlay() != null) return;

        double mouseX = this.xpos * (double) this.minecraft.getWindow().getGuiScaledWidth() / (double) this.minecraft.getWindow().getScreenWidth();
        double mouseY = this.ypos * (double) this.minecraft.getWindow().getGuiScaledHeight() / (double) this.minecraft.getWindow().getScreenHeight();

        boolean handled = ((EffectRenderingInventoryScreenMixinAccess) screen).bonappetit$handleScroll(mouseX, mouseY, yOffset);
        if (handled) ci.cancel();
    }
}