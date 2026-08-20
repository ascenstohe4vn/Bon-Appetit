package net.ashstarcrash.bonappetit.core.common.mixin;

import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.core.common.util.EffectRenderingInventoryScreenMixinAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectRenderingInventoryScreenMixin implements EffectRenderingInventoryScreenMixinAccess {
    @Shadow @Final private static ResourceLocation EFFECT_BACKGROUND_LARGE_SPRITE;
    @Shadow @Final private static ResourceLocation EFFECT_BACKGROUND_SMALL_SPRITE;

    private int bonappetit$scrollOffset = 0;
    private float bonappetit$smoothScroll = 0.0F;
    private long bonappetit$lastSmoothTime = System.currentTimeMillis();
    private int bonappetit$lastRestSize = 0;
    private int bonappetit$lastVisibleRows = 0;

    private int bonappetit$hScrollOffset = 0;
    private float bonappetit$hSmoothScroll = 0.0F;

    private int bonappetit$panelX = 0;
    private int bonappetit$panelY = 0;
    private int bonappetit$panelWidthPx = 0;
    private int bonappetit$panelHeightPx = 0;

    @Inject(method = "renderEffects", at = @At("HEAD"), cancellable = true)
    private void bonappetit$renderEffectsSplit(GuiGraphics guiGraphics, int mouseX, int mouseY, CallbackInfo ci) {
        ci.cancel();

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        AbstractContainerScreen<?> self = (AbstractContainerScreen<?>) (Object) this;
        Font font = mc.font;

        int baseX = self.getGuiLeft() + self.getXSize() + 2;
        int panelWidth = self.width - baseX;
        Collection<MobEffectInstance> all = mc.player.getActiveEffects();
        if (all.isEmpty() || panelWidth < 32) {
            bonappetit$lastRestSize = 0;
            bonappetit$lastVisibleRows = 0;
            return;
        }

        List<MobEffectInstance> rest = all.stream().filter(ClientHooks::shouldRenderEffect).sorted().toList();

        int y = self.getGuiTop();

        if (rest.isEmpty()) {
            bonappetit$lastRestSize = 0;
            bonappetit$lastVisibleRows = 0;
            return;
        }

        boolean flag = panelWidth >= 120;
        ScreenEvent.RenderInventoryMobEffects event = ClientHooks.onScreenPotionSize((EffectRenderingInventoryScreen<?>) (Object) this, panelWidth, !flag, baseX);
        if (event.isCanceled()) return;
        flag = !event.isCompact();
        int x = event.getHorizontalOffset();

        boolean verticalMode = BAConfig.VERTICAL_EFFECTS_SCROLLING.get();

        if (verticalMode) {
            bonappetit$renderVertical(guiGraphics, mc, font, x, y, self, rest, flag, mouseX, mouseY);
        } else {
            bonappetit$renderHorizontal(guiGraphics, mc, font, rest, flag, mouseX, mouseY);
        }
    }

    private void bonappetit$renderVertical(GuiGraphics guiGraphics, Minecraft mc, Font font, int x, int y, AbstractContainerScreen<?> self, List<MobEffectInstance> rest, boolean flag, int mouseX, int mouseY) {
        int rowHeight = 33;
        int availableHeight = (self.getGuiTop() + self.getYSize()) - y;
        int visibleRows = Math.max(1, availableHeight / rowHeight);

        bonappetit$lastRestSize = rest.size();
        bonappetit$lastVisibleRows = visibleRows;

        bonappetit$panelX = x;
        bonappetit$panelY = y;
        bonappetit$panelWidthPx = flag ? 120 : 32;
        bonappetit$panelHeightPx = availableHeight;

        int maxOffset = Math.max(0, rest.size() - visibleRows);
        bonappetit$scrollOffset = Math.clamp(bonappetit$scrollOffset, 0, maxOffset);

        long now = System.currentTimeMillis();
        float delta = Math.min(1.0F, (now - bonappetit$lastSmoothTime) / 150.0F);
        bonappetit$lastSmoothTime = now;
        bonappetit$smoothScroll += (bonappetit$scrollOffset - bonappetit$smoothScroll) * delta;
        if (Math.abs(bonappetit$scrollOffset - bonappetit$smoothScroll) < 0.01F) bonappetit$smoothScroll = bonappetit$scrollOffset;

        int startIndex = Math.max(0, (int) Math.floor(bonappetit$smoothScroll));
        int endIndex = Math.min(rest.size(), startIndex + visibleRows + 2);
        float pixelOffset = (bonappetit$smoothScroll - startIndex) * rowHeight;

        MobEffectTextureManager textures = mc.getMobEffectTextures();

        int scissorX2 = x + (flag ? 120 : 32);
        int scissorY2 = y + availableHeight;
        guiGraphics.enableScissor(x, y, scissorX2, scissorY2);

        int cursorY = y - (int) pixelOffset;
        int hoveredIndex = -1;
        int hoveredY = 0;

        for (int i = startIndex; i < endIndex; i++) {
            MobEffectInstance instance = rest.get(i);
            boolean isHovering = mouseX >= x && mouseX <= x + (flag ? 120 : 32) && mouseY >= cursorY && mouseY <= cursorY + rowHeight && mouseY >= y && mouseY <= scissorY2;

            if (isHovering) {
                hoveredIndex = i;
                hoveredY = cursorY;
            } else {
                bonappetit$renderRow(guiGraphics, textures, font, x, cursorY, instance, flag, mc);
            }
            cursorY += rowHeight;
        }

        if (hoveredIndex != -1) bonappetit$renderRow(guiGraphics, textures, font, x, hoveredY, rest.get(hoveredIndex), flag, mc);
        guiGraphics.disableScissor();
        if (hoveredIndex != -1) bonappetit$renderDescriptionTooltip(guiGraphics, font, rest.get(hoveredIndex), mouseX, mouseY);
    }

    private void bonappetit$renderHorizontal(GuiGraphics guiGraphics, Minecraft mc, Font font, List<MobEffectInstance> rest, boolean flag, int mouseX, int mouseY) {
        AbstractContainerScreen<?> self = (AbstractContainerScreen<?>) (Object) this;

        int cardWidth = flag ? 120 : 32;
        int rowHeight = 33;
        int peekWidth = 14;

        int x = self.getGuiLeft() + (self.getXSize() / 2) - (cardWidth / 2);
        int y = self.getGuiTop() - rowHeight - (mc.player != null && mc.player.hasInfiniteMaterials() ? rowHeight + 16 : 4);

        bonappetit$lastRestSize = rest.size();
        bonappetit$lastVisibleRows = 1;

        bonappetit$panelX = x;
        bonappetit$panelY = y;
        bonappetit$panelWidthPx = cardWidth;
        bonappetit$panelHeightPx = rowHeight;

        int maxOffset = Math.max(0, rest.size() - 1);
        bonappetit$hScrollOffset = Math.clamp(bonappetit$hScrollOffset, 0, maxOffset);

        long now = System.currentTimeMillis();
        float delta = Math.min(1.0F, (now - bonappetit$lastSmoothTime) / 150.0F);
        bonappetit$lastSmoothTime = now;
        bonappetit$hSmoothScroll += (bonappetit$hScrollOffset - bonappetit$hSmoothScroll) * delta;
        bonappetit$hSmoothScroll = Math.clamp(bonappetit$hSmoothScroll, 0, maxOffset);
        if (Math.abs(bonappetit$hScrollOffset - bonappetit$hSmoothScroll) < 0.01F) bonappetit$hSmoothScroll = bonappetit$hScrollOffset;

        MobEffectTextureManager textures = mc.getMobEffectTextures();

        int baseIndex = (int) Math.floor(bonappetit$hSmoothScroll);
        float frac = bonappetit$hSmoothScroll - baseIndex;
        int pixelShift = (int) (frac * cardWidth);

        int scissorX1 = x - peekWidth;
        int scissorX2 = x + cardWidth + peekWidth;
        guiGraphics.enableScissor(scissorX1, y, scissorX2, y + rowHeight);

        if (baseIndex > 0 && baseIndex - 1 < rest.size() && pixelShift == 0) bonappetit$renderDimmedPeek(guiGraphics, textures, x - peekWidth, y, rest.get(baseIndex - 1), flag, 0.35F);
        if (baseIndex >= 0 && baseIndex < rest.size()) bonappetit$renderRow(guiGraphics, textures, font, x - pixelShift, y, rest.get(baseIndex), flag, mc);

        if (baseIndex + 1 < rest.size()) {
            if (pixelShift != 0) {
                bonappetit$renderRow(guiGraphics, textures, font, x + cardWidth - pixelShift, y, rest.get(baseIndex + 1), flag, mc);
            } else {
                bonappetit$renderDimmedPeek(guiGraphics, textures, x + cardWidth, y, rest.get(baseIndex + 1), flag, 0.35F);
            }
        }

        guiGraphics.disableScissor();
        if (rest.size() > 1) bonappetit$renderScrollbar(guiGraphics, x + cardWidth + (cardWidth / 7), y, rowHeight, rest.size());

        boolean hovering = mouseX >= x && mouseX <= x + cardWidth && mouseY >= y && mouseY <= y + rowHeight;
        if (hovering && pixelShift == 0 && baseIndex >= 0 && baseIndex < rest.size()) bonappetit$renderDescriptionTooltip(guiGraphics, font, rest.get(baseIndex), mouseX, mouseY);
    }

    private void bonappetit$renderDimmedPeek(GuiGraphics guiGraphics, MobEffectTextureManager textures, int x, int y, MobEffectInstance instance, boolean flag, float opacity) {
        int alpha = (int) (opacity * 255) << 24;
        guiGraphics.blitSprite(flag ? EFFECT_BACKGROUND_LARGE_SPRITE : EFFECT_BACKGROUND_SMALL_SPRITE, x, y, flag ? 120 : 32, 32);

        Holder<MobEffect> holder = instance.getEffect();
        TextureAtlasSprite sprite = textures.get(holder);
        guiGraphics.blit(x + (flag ? 7 : 6), y + 7, 0, 18, 18, sprite);

        guiGraphics.fill(x, y, x + (flag ? 120 : 32), y + 32, alpha);
    }

    @Override
    public boolean bonappetit$handleScroll(double mouseX, double mouseY, double scrollY) {
        if (bonappetit$panelWidthPx <= 0 || bonappetit$panelHeightPx <= 0) return false;
        if (bonappetit$lastRestSize <= 1 && !BAConfig.VERTICAL_EFFECTS_SCROLLING.get()) return false;
        if (BAConfig.VERTICAL_EFFECTS_SCROLLING.get() && bonappetit$lastRestSize <= bonappetit$lastVisibleRows) return false;

        boolean withinX = mouseX >= bonappetit$panelX && mouseX <= bonappetit$panelX + bonappetit$panelWidthPx;
        boolean withinY = mouseY >= bonappetit$panelY && mouseY <= bonappetit$panelY + bonappetit$panelHeightPx;
        if (!withinX || !withinY) return false;

        int maxOffset;
        if (BAConfig.VERTICAL_EFFECTS_SCROLLING.get()) {
            maxOffset = Math.max(0, bonappetit$lastRestSize - bonappetit$lastVisibleRows);
            bonappetit$scrollOffset -= (int) Math.signum(scrollY);
            bonappetit$scrollOffset = Math.clamp(bonappetit$scrollOffset, 0, maxOffset);
        } else {
            maxOffset = Math.max(0, bonappetit$lastRestSize - 1);
            bonappetit$hScrollOffset -= (int) Math.signum(scrollY);
            bonappetit$hScrollOffset = Math.clamp(bonappetit$hScrollOffset, 0, maxOffset);
        }

        return true;
    }

    private void bonappetit$renderRow(GuiGraphics guiGraphics, MobEffectTextureManager textures, Font font, int x, int y, MobEffectInstance instance, boolean flag, Minecraft mc) {
        guiGraphics.blitSprite(flag ? EFFECT_BACKGROUND_LARGE_SPRITE : EFFECT_BACKGROUND_SMALL_SPRITE, x, y, flag ? 120 : 32, 32);

        IClientMobEffectExtensions renderer = IClientMobEffectExtensions.of(instance);
        EffectRenderingInventoryScreen<?> screen = (EffectRenderingInventoryScreen<?>) (Object) this;
        if (!renderer.renderInventoryIcon(instance, screen, guiGraphics, x + (flag ? 7 : 6), y, 0)) {
            Holder<MobEffect> holder = instance.getEffect();
            TextureAtlasSprite sprite = textures.get(holder);
            guiGraphics.blit(x + (flag ? 7 : 6), y + 7, 0, 18, 18, sprite);
        }

        if (flag) {
            if (!renderer.renderInventoryText(instance, screen, guiGraphics, x, y, 0)) {
                Component name = instance.getEffect().value().getDisplayName();
                guiGraphics.drawString(font, name, x + 10 + 18, y + 6, 16777215);
                float tickRate = mc.level != null ? mc.level.tickRateManager().tickrate() : 20.0F;
                Component dur = MobEffectUtil.formatDuration(instance, 1.0F, tickRate);
                guiGraphics.drawString(font, dur, x + 10 + 18, y + 16, 8355711);
            }
        }
    }

    private void bonappetit$renderDescriptionTooltip(GuiGraphics guiGraphics, Font font, MobEffectInstance instance, int mouseX, int mouseY) {
        ResourceLocation id = instance.getEffect().unwrapKey().map(k -> k.location()).orElse(null);
        if (id == null) return;

        String baseKey = "effect." + id.getNamespace() + "." + id.getPath();
        Component desc = bonappetit$resolveDescription(baseKey + ".description", baseKey + ".desc");
        if (desc == null) return;

        guiGraphics.renderTooltip(font, List.of(desc), Optional.empty(), mouseX, mouseY);
    }

    private Component bonappetit$resolveDescription(String primaryKey, String fallbackKey) {
        Component primary = Component.translatable(primaryKey);
        if (!primary.getString().equals(primaryKey)) return primary;

        Component fallback = Component.translatable(fallbackKey);
        if (!fallback.getString().equals(fallbackKey)) return fallback;

        return null;
    }

    private void bonappetit$renderScrollbar(GuiGraphics guiGraphics, int cardRightX, int y, int rowHeight, int totalCount) {
        int barWidth = 3;
        int barX = cardRightX + 4;
        int trackHeight = rowHeight;

        guiGraphics.fill(barX, y, barX + barWidth, y + trackHeight, 0x40FFFFFF);

        float thumbHeightRatio = 1.0F / totalCount;
        int thumbHeight = Math.max(4, (int) (trackHeight * thumbHeightRatio));
        float scrollProgress = totalCount <= 1 ? 0 : (float) bonappetit$hScrollOffset / (totalCount - 1);
        int thumbY = y + (int) ((trackHeight - thumbHeight) * scrollProgress);

        guiGraphics.fill(barX, thumbY, barX + barWidth, thumbY + thumbHeight, 0xFFAAAAAA);
    }
}