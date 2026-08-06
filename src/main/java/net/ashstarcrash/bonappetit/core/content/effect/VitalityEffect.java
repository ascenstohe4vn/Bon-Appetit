package net.ashstarcrash.bonappetit.core.content.effect;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class VitalityEffect extends MobEffect {
    public VitalityEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectStarted(@NotNull LivingEntity entity, int amplifier) {
        super.onEffectStarted(entity, amplifier);
        float additionalHealth = 4.0F + (amplifier * 4.0F);
        entity.heal(additionalHealth);
    }

    public static void applyPenalty(LivingEntity entity, int amplifier, float penaltyMultiplier) {
        if (!entity.isAlive()) return;

        int additionalHealth = 4 + (amplifier * 4);
        float basePenalty = additionalHealth * 2.0F;
        float finalPenalty = basePenalty * penaltyMultiplier;

        float maxAllowedDamage = Math.max(0.0F, entity.getHealth() - 0.1F);
        float actualDamage = Math.min(finalPenalty, maxAllowedDamage);

        if (actualDamage > 0.0F) {
            entity.hurt(entity.damageSources().starve(), actualDamage); //replace with a custom damagetype in the future. im lazy.
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, entity.getSoundSource(), 0.75F, 1.5f
            );
        }
    }
}