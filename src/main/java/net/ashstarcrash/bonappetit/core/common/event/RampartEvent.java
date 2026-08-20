package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = BonAppetit.ID)
public class RampartEvent {
    @SubscribeEvent
    public static void onIncomingLivingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.hasEffect(BAEffects.RAMPART)) return;
        if (entity.level().isClientSide) return;

        int amplifier = entity.getEffect(BAEffects.RAMPART).getAmplifier();
        if ((entity.getHealth() - event.getContainer().getNewDamage() <= (amplifier * 2F + 2F)) && (entity.getHealth() > (amplifier * 2F + 2F))) {
            event.setAmount(entity.getHealth() - (amplifier * 2F + 2));
            entity.removeEffect(BAEffects.RAMPART);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SHIELD_BREAK, SoundSource.MASTER, 1.25F, 0.85F);
        }
    }
}
