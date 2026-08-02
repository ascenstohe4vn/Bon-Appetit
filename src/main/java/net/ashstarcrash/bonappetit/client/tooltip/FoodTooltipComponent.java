package net.ashstarcrash.bonappetit.client.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;

public class FoodTooltipComponent implements ClientTooltipComponent {
    private static final ResourceLocation HEART_FULL = ResourceLocation.withDefaultNamespace("hud/heart/full");
    private static final ResourceLocation HEART_HALF = ResourceLocation.withDefaultNamespace("hud/heart/half");
    private static final ResourceLocation FOOD_FULL = ResourceLocation.withDefaultNamespace("hud/food_full");
    private static final ResourceLocation FOOD_HALF = ResourceLocation.withDefaultNamespace("hud/food_half");
    private static final ResourceLocation SATURATION_OVERLAY =
            ModUtil.BA.asResource("hud/saturation_overlay");

    private static final int ICON_SIZE = 9;
    private static final int MAX_ICONS = 20;

    private final FoodTooltipData data;

    public FoodTooltipComponent(FoodTooltipData data) {
        this.data = data;
    }

    @Override
    public int getHeight() {
        return data.regenDurationTicks() > 0 ? (ICON_SIZE * 2) + 4 : ICON_SIZE + 2;
    }

    @Override
    public int getWidth(Font font) {
        return Math.min((int) Math.ceil(data.foodLevel()), MAX_ICONS) * 8 + ICON_SIZE;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        RenderSystem.enableBlend();

        if (!data.hungerBarEnabled()) {
            drawHealRow(guiGraphics, x, y);
            if (data.regenDurationTicks() > 0) {
                drawRegenRow(guiGraphics, font, x, y + ICON_SIZE + 4);
            }
        } else {
            drawHungerRow(guiGraphics, x, y);
        }

        RenderSystem.disableBlend();
    }

    private void drawHealRow(GuiGraphics guiGraphics, int x, int y) {
        drawBarRow(guiGraphics, x, y, data.healAmount(), HEART_FULL, HEART_HALF);
    }

    private void drawHungerRow(GuiGraphics guiGraphics, int x, int y) {
        float amount = data.foodLevel();
        int fullUnits = (int) (amount / 2.0F);
        boolean hasHalf = (amount % 2.0F) >= 1.0F;
        int totalIcons = fullUnits + (hasHalf ? 1 : 0);

        if (totalIcons > MAX_ICONS) {
            return;
        }

        for (int i = 0; i < totalIcons; i++) {
            int drawX = x + i * 8;
            boolean isHalf = hasHalf && i == totalIcons - 1;
            guiGraphics.blitSprite(isHalf ? FOOD_HALF : FOOD_FULL, drawX, y, ICON_SIZE, ICON_SIZE);

            if (data.showSaturationOverlay() && data.saturationLevel() > i * 2.0F) {
                guiGraphics.blitSprite(SATURATION_OVERLAY, drawX, y, ICON_SIZE, ICON_SIZE);
            }
        }
    }

    private void drawBarRow(GuiGraphics guiGraphics, int x, int y, float amount, ResourceLocation full, ResourceLocation half) {
        int halfUnits = Math.round(amount);
        int fullCount = halfUnits / 2;
        boolean hasHalf = (halfUnits % 2) != 0;
        int totalIcons = fullCount + (hasHalf ? 1 : 0);

        if (totalIcons > MAX_ICONS) {
            return;
        }

        for (int i = 0; i < fullCount; i++) {
            guiGraphics.blitSprite(full, x + i * 8, y, ICON_SIZE, ICON_SIZE);
        }
        if (hasHalf) {
            guiGraphics.blitSprite(half, x + fullCount * 8, y, ICON_SIZE, ICON_SIZE);
        }
    }

    private void drawRegenRow(GuiGraphics guiGraphics, Font font, int x, int y) {
        int totalSeconds = data.regenDurationTicks() / 20;
        int totalHp = data.regenPulses();
        String text = "+" + totalHp + " Health over " + totalSeconds + "s";
        guiGraphics.drawString(font, text, x, y + 1, 0x808080, false);
    }
}