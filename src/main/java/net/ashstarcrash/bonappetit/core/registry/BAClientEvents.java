package net.ashstarcrash.bonappetit.core.registry;

import com.mojang.blaze3d.systems.RenderSystem;
import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.BonAppetit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@Mod(value = BonAppetit.ID) @EventBusSubscriber(modid = BonAppetit.ID, value = Dist.CLIENT)
public class BAClientEvents {
    private static final ResourceLocation VIGNETTE_TEXTURE = ResourceLocation.withDefaultNamespace("textures/misc/vignette.png");
    private static float vignetteAlpha = 0.0f;

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiLayerEvent.Post event) {
        if (event.getName().equals(VanillaGuiLayers.CAMERA_OVERLAYS)) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            boolean hidden = BAEvents.isHidden(mc.player);
            float pTicks = event.getPartialTick().getGameTimeDeltaPartialTick(true);

            if (hidden) {
                vignetteAlpha = Math.min(1.0f, vignetteAlpha + 0.02f * pTicks);
            } else {
                vignetteAlpha = Math.max(0.0f, vignetteAlpha - 0.05f * pTicks);
            }

            if (vignetteAlpha > 0) {
                GuiGraphics guiGraphics = event.getGuiGraphics();
                int width = mc.getWindow().getGuiScaledWidth();
                int height = mc.getWindow().getGuiScaledHeight();

                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                guiGraphics.setColor(0.0F, 0.02F, 0.08F, vignetteAlpha * 0.5f);
                guiGraphics.blit(VIGNETTE_TEXTURE, 0, 0, -90, 0.0F, 0.0F, width, height, width, height);

                guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
                RenderSystem.enableDepthTest();
            }
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        FoodProperties effects = stack.getFoodProperties(event.getEntity());
        if (effects != null && BAConfig.EFFECT_TOOLTIPS.get()) {
            List<Component> tooltip = event.getToolTip();
            Player player = event.getEntity();

            for (FoodProperties.PossibleEffect effect : effects.effects()) {
                MobEffectInstance effectInstance = effect.effect();
                net.minecraft.world.effect.MobEffectCategory category = effectInstance.getEffect().value().getCategory();
                boolean isHarmful = (category == net.minecraft.world.effect.MobEffectCategory.HARMFUL);
                if (isHarmful && !BAConfig.NEGATIVE_EFFECT_TOOLTIPS.get()) {
                    continue;
                }

                MutableComponent effectText = Component.translatable(effectInstance.getDescriptionId());
                float probability = effect.probability();

                if (effectInstance.getAmplifier() > 0) {
                    effectText = Component.translatable("potion.withAmplifier", effectText, Component.translatable("potion.potency." + effectInstance.getAmplifier()));
                }
                if (!effectInstance.endsWithin(20)) {
                    effectText = Component.translatable("potion.withDuration", effectText, MobEffectUtil.formatDuration(effectInstance, 1.0F, player == null ? 20 : player.level().tickRateManager().tickrate()));
                }
                effectText.withStyle(category.getTooltipFormatting());
                if (probability < 1.0F) {
                    BAConfig.ChanceDisplayMode mode = BAConfig.CHANCE_DISPLAY.get();

                    switch (mode) {
                        case FULL -> {
                            int percent = (int) (probability * 100);
                            effectText.append(Component.literal(" • " + percent + "%").withStyle(ChatFormatting.GRAY));
                        }
                        case HIDDEN -> {
                            effectText.append(Component.literal("?").withStyle(ChatFormatting.GRAY));
                        }
                        case DYNAMIC -> {
                            if (category == net.minecraft.world.effect.MobEffectCategory.BENEFICIAL) {
                                int percent = (int) (probability * 100);
                                effectText.append(Component.literal(" • " + percent + "%").withStyle(ChatFormatting.GRAY));
                            } else {
                                effectText.append(Component.literal("?").withStyle(ChatFormatting.GRAY));
                            }
                        }
                        case NONE -> {}
                    }
                }
                tooltip.add(effectText);
            }
        }
    }
}