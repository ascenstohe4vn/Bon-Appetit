package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.ashstarcrash.bonappetit.core.registry.BATriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = BonAppetit.ID)
public class ProliferateEvent {
    @SubscribeEvent
    public static void onLivingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            MobEffectInstance pomegranateEffect = attacker.getEffect(BAEffects.PROLIFERATE);
            if (pomegranateEffect != null) {
                int pomegranateAmplifier = pomegranateEffect.getAmplifier();
                LivingEntity victim = event.getEntity();
                MobEffectInstance seeded = victim.getEffect(BAEffects.SEEDED);
                int stacks = (seeded == null) ? 0 : seeded.getAmplifier() + 1;

                if (stacks >= (BAConfig.SEEDED_MAX_STACKS.get() - 1)) {
                    victim.removeEffect(BAEffects.SEEDED);
                    victim.hurt(victim.level().damageSources().magic(), 4.0f + (pomegranateAmplifier * 1.5f));
                    victim.level().playSound(null, victim.getX(), victim.getY(), victim.getZ(),
                            SoundEvents.CHERRY_WOOD_BREAK, SoundSource.HOSTILE, 1.5f, 0.8f);
                    if (victim.level() instanceof ServerLevel sl) {
                        sl.sendParticles(ParticleTypes.CRIMSON_SPORE, victim.getX(), victim.getY() + 1, victim.getZ(), 30, 0.3, 0.3, 0.3, 0.1);
                        sl.sendParticles(ParticleTypes.EXPLOSION, victim.getX(), victim.getY() + 1, victim.getZ(), 1, 0, 0, 0, 0);
                    }
                } else {
                    victim.addEffect(new MobEffectInstance(BAEffects.SEEDED, 100, stacks));
                    victim.level().playSound(null, victim.getX(), victim.getY(), victim.getZ(), SoundEvents.CHERRY_SAPLING_STEP, SoundSource.PLAYERS, 0.5f, 1.2f);
                    if (victim instanceof ServerPlayer serverPlayer) BATriggers.SEEDED_RUPTURE.get().trigger(serverPlayer);
                }
            }
        }
    }
}
