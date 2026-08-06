package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.content.effect.VitalityEffect;
import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = BonAppetit.ID)
public class VitalityEvent {
    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        MobEffectInstance instance = event.getEffectInstance();
        if (instance != null && instance.getEffect().is(BAEffects.VITALITY)) {
            VitalityEffect.applyPenalty(event.getEntity(), instance.getAmplifier(), 1.0F);
        }
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        MobEffectInstance instance = event.getEffectInstance();
        if (instance != null && instance.getEffect().is(BAEffects.VITALITY)) {
            VitalityEffect.applyPenalty(event.getEntity(), instance.getAmplifier(), 0.75F);
        }
    }
}
