package net.ashstarcrash.bonappetit.core.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/*\
 * temp
\*/
public class ObscurityEffect extends MobEffect {
    public ObscurityEffect(MobEffectCategory neutral, int i) {super(MobEffectCategory.BENEFICIAL, 0xCDE262);}

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}