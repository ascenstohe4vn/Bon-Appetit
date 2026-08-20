package net.ashstarcrash.bonappetit.core.content.item;

import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class OrangeJawbreakerItem extends Item {
    public OrangeJawbreakerItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        int layers = stack.getMaxDamage() - stack.getDamageValue();
        int duration = 0;
        int amplifier = 0;
        switch (layers) {
            case 4 -> {
                duration = 400;
            } case 3 -> {
                duration = 200; amplifier = 1;
            } case 2 -> {
                duration = 100; amplifier = 2;
            } case 1 -> {
                duration = 50; amplifier = 3;
            }
        }
        entity.addEffect(new MobEffectInstance(BAEffects.CONCENTRATION, duration, amplifier));

        if (layers == 1) {
            return super.finishUsingItem(stack, level, entity);
        } else {
            stack.setDamageValue(stack.getDamageValue() + 1);
            return stack;
        }
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return stack.getDamageValue() > 0;
    }

    @Override
    public @NotNull ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack copy = itemStack.copy();
        copy.setDamageValue(Math.max(0, copy.getDamageValue() - 1));
        return copy;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        int layers = stack.getMaxDamage() - stack.getDamageValue();
        return switch (layers) {
            case 4 -> 96;
            case 3 -> 72;
            case 2 -> 48;
            default -> 24;
        };
    }
}
