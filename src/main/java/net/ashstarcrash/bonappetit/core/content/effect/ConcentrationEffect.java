package net.ashstarcrash.bonappetit.core.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/*\
 * Concentration halves all potion effects once it's first applied
 * It also increases projectile velocity and damage
\*/
public class ConcentrationEffect extends MobEffect {
    public ConcentrationEffect() {super(MobEffectCategory.BENEFICIAL, 0xF5A332);}

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}