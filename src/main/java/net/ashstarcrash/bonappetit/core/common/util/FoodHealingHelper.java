package net.ashstarcrash.bonappetit.core.common.util;

import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.core.registry.BAAttachments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

public class FoodHealingHelper {
    public static void apply(Player player, ItemStack stack) {
        if (BAConfig.VANILLA_HUNGER_BAR.get()) return;

        FoodProperties food = stack.getFoodProperties(player);
        if (food == null) return;

        applyRaw(player, food.nutrition(), food.saturation());
    }

    public static void applyRaw(Player player, int nutrition, float saturation) {
        if (BAConfig.VANILLA_HUNGER_BAR.get()) return;

        applyHeal(player, nutrition);
        applyRegen(player, saturation);
    }

    private static void applyHeal(Player player, int nutrition) {
        double hpPerNutrition = BAConfig.HEAL_PER_NUTRITION.get();
        float healAmount = (float) (nutrition * hpPerNutrition);
        if (healAmount > 0.0F) {
            player.heal(healAmount);
        }
    }

    private static void applyRegen(Player player, float saturation) {
        double durationPerSaturation = BAConfig.REGEN_DURATION_PER_SATURATION.get();
        int minDuration = BAConfig.MINIMUM_REGEN_DURATION_THRESHOLD.get();
        int maxDuration = BAConfig.MAXIMUM_REGEN_DURATION_CAP.get();

        int duration = (int) Math.round(saturation * durationPerSaturation);
        if (duration < minDuration) return;

        FoodRegenData data = player.getData(BAAttachments.FOOD_REGEN.get());
        data.addDuration(duration, maxDuration);
    }

    public static int computeTotalRegenHp(int durationTicks) {
        if (durationTicks <= 0) return 0;
        int pulseInterval = BAConfig.REGEN_PULSE_INTERVAL.get();
        double healPerPulse = BAConfig.REGEN_PULSE_HEAL_AMOUNT.get();
        int pulseCount = durationTicks / pulseInterval;
        return (int) Math.round(pulseCount * healPerPulse);
    }

    public static int computeRegenDuration(FoodProperties food) {
        if (BAConfig.VANILLA_HUNGER_BAR.get()) return 0;
        int duration = (int) Math.round(food.saturation() * BAConfig.REGEN_DURATION_PER_SATURATION.get());
        int min = BAConfig.MINIMUM_REGEN_DURATION_THRESHOLD.get();
        int max = BAConfig.MAXIMUM_REGEN_DURATION_CAP.get();
        return duration >= min ? Math.min(duration, max) : 0;
    }
}