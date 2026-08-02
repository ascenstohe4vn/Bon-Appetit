package net.ashstarcrash.bonappetit.client.tooltip;

import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public class IconRow {
    private static final ResourceLocation FONT = ModUtil.BA.asResource("icons");
    private static final Style ICON_STYLE = Style.EMPTY.withFont(FONT);

    public static final char HEART_FULL = '\uE001';
    public static final char HEART_HALF = '\uE002';
    public static final char FOOD_FULL = '\uE003';
    public static final char FOOD_HALF = '\uE004';
    public static final char SATURATION_OVERLAY = '\uE005';

    private static final int MAX_ICONS_BEFORE_FALLBACK = 20;

    public static MutableComponent buildRow(float amount, char fullIcon, char halfIcon, String fallbackTranslationKey) {
        int halfUnits = Math.round(amount * 2.0F);
        int fullCount = halfUnits / 2;
        boolean hasHalf = (halfUnits % 2) != 0;
        int totalIcons = fullCount + (hasHalf ? 1 : 0);

        if (totalIcons > MAX_ICONS_BEFORE_FALLBACK) {
            return Component.translatable(fallbackTranslationKey, String.valueOf(fullIcon), amount);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fullCount; i++) {
            sb.append(fullIcon);
        }
        if (hasHalf) {
            sb.append(halfIcon);
        }

        return Component.literal(sb.toString()).withStyle(ICON_STYLE);
    }

    public static MutableComponent buildHungerRow(float foodLevel, boolean showSaturationOverlay) {
        return buildRow(foodLevel, FOOD_FULL, FOOD_HALF, "tooltip.bonappetit.food_fallback");
    }
}