package net.ashstarcrash.bonappetit.core.common.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Inject(method = "renderHearts", at = @At("RETURN"))
    private void bonappetit$renderSeededOverlay(GuiGraphics guiGraphics, Player player, int x, int y, int height, int offsetHeartIndex, float maxHealth, int currentHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        MobEffectInstance seeded = player.getEffect(BAEffects.SEEDED);
        if (seeded == null) return;

        int stacks = seeded.getAmplifier() + 1;
        int maxStacks = (BAConfig.MAXIMUM_SEEDED_STACKS.get());
        if (stacks > maxStacks) return;

        float rawHp = ((float) stacks / maxStacks) * 20.0F;
        int infectedHp = Mth.ceil(rawHp);
        if (infectedHp <= 0) return;

        int containerHearts = Mth.ceil((double) maxHealth / 2.0);
        int infectedHearts = Mth.ceil(infectedHp / 2.0F);

        RenderSystem.enableBlend();

        for (int h = 0; h < infectedHearts && h < containerHearts; h++) {
            int row = h / 10;
            int col = h % 10;
            int drawX = x + col * 8;
            int drawY = y - row * height;

            boolean isFullyInfected = (h * 2 + 2) <= infectedHp;
            boolean isHalfInfected = !isFullyInfected && (h * 2 + 1) <= infectedHp;

            if (isFullyInfected) {
                guiGraphics.blitSprite(ModUtil.BA.asResource("hud/heart/seeded_vine_full"), drawX, drawY, 9, 9);
            } else if (isHalfInfected) {
                guiGraphics.blitSprite(ModUtil.BA.asResource("hud/heart/seeded_vine_half"), drawX, drawY, 9, 9);
            }
        }
        RenderSystem.disableBlend();
    }

    @Inject(method = "renderHearts", at = @At("RETURN"))
    private void bonappetit$renderRampartOverlay(GuiGraphics guiGraphics, Player player, int x, int y, int height, int offsetHeartIndex, float maxHealth, int currentHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        MobEffectInstance rampart = player.getEffect(BAEffects.RAMPART);
        if (rampart == null) return;

        int totalHearts = Mth.ceil(currentHealth / 2.0f);
        if (totalHearts <= 0) return;

        int protectedHeartsCount = Math.min(rampart.getAmplifier() + 1, totalHearts);

        RenderSystem.enableBlend();

        for (int i = 0; i < protectedHeartsCount; i++) {
            int row = i / 10;
            int col = i % 10;

            int drawX = x + col * 8;
            int drawY = y - row * height;

            guiGraphics.blitSprite(ModUtil.BA.asResource("hud/heart/coconut_rampart"), drawX, drawY, 9, 9);
        }
        RenderSystem.disableBlend();
    }
}