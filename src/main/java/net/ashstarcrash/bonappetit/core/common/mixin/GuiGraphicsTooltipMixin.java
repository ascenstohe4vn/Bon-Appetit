package net.ashstarcrash.bonappetit.core.common.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.ashstarcrash.bonappetit.core.common.util.FoodHealingHelper;
import net.ashstarcrash.bonappetit.core.registry.BAAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsTooltipMixin {
    @Shadow
    private ItemStack tooltipStack;

    @Shadow
    public abstract int guiWidth();

    @Shadow
    public abstract int guiHeight();

    @Unique
    private int bonappetit$rowX;

    @Unique
    private int bonappetit$rowY;

    @Unique
    private boolean bonappetit$shouldRender;

    @Unique
    private boolean bonappetit$showUndiscovered;

    @Unique
    private FoodProperties bonappetit$food;

    @Inject(method = "renderTooltipInternal", at = @At("HEAD"))
    private void bonappetit$computePosition(Font font, List<ClientTooltipComponent> components, int mouseX, int mouseY, ClientTooltipPositioner tooltipPositioner, CallbackInfo ci) {
        bonappetit$shouldRender = false;
        bonappetit$showUndiscovered = false;

        if (components.isEmpty()) return;

        ItemStack stack = this.tooltipStack;
        boolean usedEmiFallback = false;
        if (stack == null || stack.isEmpty()) {
            stack = ModUtil.getEmiFallbackStack(mouseX, mouseY);
            usedEmiFallback = true;
        }
        if (stack == null || stack.isEmpty()) return;

        FoodProperties food = stack.get(DataComponents.FOOD);
        if (food == null) return;

        BAConfig.FoodStatisticsTooltipDisplay displayMode = BAConfig.FOOD_STATISTICS_TOOLTIP_DISPLAY.get();
        if (displayMode == BAConfig.FoodStatisticsTooltipDisplay.NONE) return;

        boolean discoveredGate = false;
        if (displayMode == BAConfig.FoodStatisticsTooltipDisplay.DISCOVERY) {
            Player mcPlayer = Minecraft.getInstance().player;
            if (mcPlayer != null) {
                String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
                if (!mcPlayer.getData(BAAttachments.FOOD_DISCOVERY.get()).hasEaten(itemId)) {
                    discoveredGate = true;
                }
            }
        }

        GuiGraphics guiGraphics = (GuiGraphics) (Object) this;

        int contentWidth = 0;
        int contentHeight = components.size() == 1 ? -2 : 0;
        for (ClientTooltipComponent component : components) {
            int w = component.getWidth(font);
            if (w > contentWidth) contentWidth = w;
            contentHeight += component.getHeight();
        }

        Vector2ic pos = tooltipPositioner.positionTooltip(
                guiGraphics.guiWidth(), guiGraphics.guiHeight(),
                mouseX, mouseY, contentWidth, contentHeight
        );

        int rowOffset = components.get(0).getHeight() + 2;
        boolean isCreativeScreen = !usedEmiFallback && Minecraft.getInstance().screen instanceof CreativeModeInventoryScreen;
        if (isCreativeScreen && components.size() > 1) {
            rowOffset += components.get(1).getHeight();
        }

        bonappetit$rowX = pos.x();
        bonappetit$rowY = pos.y() + rowOffset;
        bonappetit$food = food;
        bonappetit$showUndiscovered = discoveredGate;
        bonappetit$shouldRender = true;
    }

    @Inject(method = "renderTooltipInternal", at = @At("RETURN"))
    private void bonappetit$drawIcons(Font font, List<ClientTooltipComponent> components, int mouseX, int mouseY, ClientTooltipPositioner tooltipPositioner, CallbackInfo ci) {
        if (!bonappetit$shouldRender) return;

        GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
        int rowX = bonappetit$rowX;
        int rowY = bonappetit$rowY;

        if (bonappetit$showUndiscovered) {
            guiGraphics.drawString(font, "?", rowX, rowY, 0x808080, false);
            bonappetit$shouldRender = false;
            return;
        }

        boolean hungerEnabled = BAConfig.HUNGER_BAR_ENABLED.get();
        FoodProperties food = bonappetit$food;

        if (!hungerEnabled) {
            float healAmount = (float) (food.nutrition() * BAConfig.FOOD_HEAL_MULTIPLIER.get());
            int regenDuration = FoodHealingHelper.computeRegenDuration(food);
            int regenTotalHp = regenDuration > 0 ? FoodHealingHelper.computeTotalRegenHp(regenDuration) : 0;

            drawHeartRow(guiGraphics, rowX, rowY, healAmount, regenTotalHp);
        } else {
            drawFoodRow(guiGraphics, rowX, rowY, food.nutrition() * 2.0F, food.saturation());
        }

        bonappetit$shouldRender = false;
    }

    @Unique
    private void drawHeartRow(GuiGraphics guiGraphics, int x, int y, float healAmount, int regenTotalHp) {
        int healHalfUnits = Math.round(healAmount);
        int healFullCount = healHalfUnits / 2;
        boolean healHasHalf = (healHalfUnits % 2) != 0;
        int healIcons = healFullCount + (healHasHalf ? 1 : 0);

        int regenHalfUnits = Math.round((float) regenTotalHp);
        int regenFullCount = regenHalfUnits / 2;
        boolean regenHasHalf = (regenHalfUnits % 2) != 0;
        int regenIcons = regenFullCount + (regenHasHalf ? 1 : 0);

        if (healIcons + regenIcons > 20) return;

        RenderSystem.enableBlend();

        int drawX = x;
        for (int i = 0; i < healFullCount; i++) {
            guiGraphics.blitSprite(Gui.HeartType.NORMAL.getSprite(false, false, false), drawX, y, 9, 9);
            drawX += 8;
        }
        if (healHasHalf) {
            guiGraphics.blitSprite(Gui.HeartType.NORMAL.getSprite(false, true, false), drawX, y, 9, 9);
            drawX += 8;
        }

        if (regenIcons > 0) {
            drawX += 2;

            RenderSystem.setShaderColor(0.65F, 0.65F, 0.65F, 1.0F);

            for (int i = 0; i < regenFullCount; i++) {
                guiGraphics.blitSprite(Gui.HeartType.NORMAL.getSprite(false, false, false), drawX, y, 9, 9);
                drawX += 8;
            }
            if (regenHasHalf) {
                guiGraphics.blitSprite(Gui.HeartType.NORMAL.getSprite(false, true, false), drawX, y, 9, 9);
            }

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }

        RenderSystem.disableBlend();
    }

    @Unique
    private void drawFoodRow(GuiGraphics guiGraphics, int x, int y, float foodLevel, float saturation) {
        int fullCount = (int) (foodLevel / 2.0F);
        boolean hasHalf = (foodLevel % 2.0F) >= 1.0F;
        int totalIcons = fullCount + (hasHalf ? 1 : 0);
        if (totalIcons > 20) return;

        RenderSystem.enableBlend();
        for (int i = 0; i < totalIcons; i++) {
            int drawX = x + i * 8;
            boolean isHalf = hasHalf && i == totalIcons - 1;
            guiGraphics.blitSprite(
                    isHalf ? ResourceLocation.withDefaultNamespace("hud/food_half")
                            : ResourceLocation.withDefaultNamespace("hud/food_full"),
                    drawX, y, 9, 9
            );

            if (BAConfig.SHOW_SATURATION_OVERLAY.get() && saturation > i * 2.0F) {
                guiGraphics.blitSprite(
                        ModUtil.BA.asResource("hud/saturation_overlay"),
                        drawX, y, 9, 9
                );
            }
        }
        RenderSystem.disableBlend();
    }
}