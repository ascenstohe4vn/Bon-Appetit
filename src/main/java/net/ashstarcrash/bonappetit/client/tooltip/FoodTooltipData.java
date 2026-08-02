package net.ashstarcrash.bonappetit.client.tooltip;

import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record FoodTooltipData(
        float foodLevel,
        float saturationLevel,
        boolean showSaturationOverlay,
        boolean hungerBarEnabled,
        float healAmount,
        int regenPulses,
        int regenDurationTicks
) implements TooltipComponent {}