package net.ashstarcrash.bonappetit.core.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/*\
 * temp
\*/
public class FlakEffect extends MobEffect {
    public FlakEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xD80073);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }
}