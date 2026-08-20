package net.ashstarcrash.bonappetit.client.render;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BAAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;

public class NuzlockeEffectClientExtensions implements IClientMobEffectExtensions {
    private float hoverProgress = 0.0F;
    private long lastFrameTime = System.currentTimeMillis();

    @Override
    public boolean renderInventoryText(@NotNull MobEffectInstance instance, @NotNull EffectRenderingInventoryScreen<?> screen, @NotNull GuiGraphics guiGraphics, int x, int y, int blitOffset) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return false;

        long now = System.currentTimeMillis();
        float delta = (now - lastFrameTime) / 500.0F;
        lastFrameTime = now;

        int cardWidth = 120;
        int cardHeight = 32;

        double mouseX = mc.mouseHandler.xpos() * (double) mc.getWindow().getGuiScaledWidth() / (double) mc.getWindow().getScreenWidth();
        double mouseY = mc.mouseHandler.ypos() * (double) mc.getWindow().getGuiScaledHeight() / (double) mc.getWindow().getScreenHeight();
        boolean isHovered = mouseX >= x && mouseX <= x + cardWidth && mouseY >= y && mouseY <= y + cardHeight;

        if (isHovered) {
            hoverProgress = Math.min(1.0F, hoverProgress + delta);
        } else {
            hoverProgress = Math.max(0.0F, hoverProgress - delta);
        }

        if (hoverProgress > 0.0F) {
            int alpha = (int) (hoverProgress * 120) << 24;
            int darkRedTint = alpha | 0x441111;
            guiGraphics.fill(x + 2, y + 2, x + cardWidth - 2, y + cardHeight - 2, darkRedTint);
        }

        Font font = mc.font;
        MutableComponent title = instance.getEffect().value().getDisplayName().copy();
        if (instance.getAmplifier() > 0) {
            title.append(" ").append(Component.translatable("enchantment.level." + (instance.getAmplifier() + 1)));
        }

        Component duration = MobEffectUtil.formatDuration(instance, 1.0F, mc.level != null ? mc.level.tickRateManager().tickrate() : 20.0F);
        int kills = player.getData(BAAttachments.NUZLOCKE_KILLS);
        int targetKills = 20 * (instance.getAmplifier() + 1);
        int remainingKills = Math.max(0, targetKills - kills);
        Component subtext = Component.literal(remainingKills + " left (" + duration.getString() + ")");

        int textX = x + 10 + 18;
        guiGraphics.drawString(font, title, textX, y + 6, 0xFFE06666, true);
        guiGraphics.drawString(font, subtext, textX, y + 18, 0xFFAAAAAA, true);

        return true;
    }
}