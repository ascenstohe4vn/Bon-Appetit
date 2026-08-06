package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.WeakHashMap;

@EventBusSubscriber(modid = BonAppetit.ID)
public class DischargeEvent {
    private static final WeakHashMap<LivingEntity, Float> RESERVOIR = new WeakHashMap<>();

    @SubscribeEvent
    public static void onAbsorb(LivingIncomingDamageEvent event) {
        LivingEntity victim = event.getEntity();
        MobEffectInstance effect = null;
        for (var effectInstance : victim.getActiveEffects()) {
            if (effectInstance.getEffect().getRegisteredName().equals("bonappetit:discharge")) {
                effect = effectInstance;
                break;
            }
        }

        if (effect != null) {
            victim.setDeltaMovement(victim.getDeltaMovement().scale(0.5));
            float ratio = 0.35f + (effect.getAmplifier() * 0.15f);
            float stored = event.getAmount() * ratio;
            accumulate(victim, stored);

            if (victim.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.ELECTRIC_SPARK, victim.getX(), victim.getY(1.0), victim.getZ(), 3, 0.1, 0.1, 0.1, 0.05);
            }
        }
    }

    @SubscribeEvent
    public static void onRelease(LivingDamageEvent.Pre event) {
        if (event.getSource().getDirectEntity() instanceof LivingEntity attacker) {
            MobEffectInstance dischargeInstance = null;
            for (var effectInstance : attacker.getActiveEffects()) {
                if (effectInstance.getEffect().getRegisteredName().equals("bonappetit:discharge")) {
                    dischargeInstance = effectInstance;
                    break;
                }
            }

            if (dischargeInstance != null) {
                float storedEnergy = RESERVOIR.getOrDefault(attacker, 0f);

                if (storedEnergy > 1.0f) {
                    float cap = 10.0f + (dischargeInstance.getAmplifier() * 2.5f);
                    float finalBonus = Math.min(storedEnergy, cap);

                    event.setNewDamage(event.getNewDamage() + finalBonus);
                    RESERVOIR.put(attacker, 0f);

                    if (attacker.level() instanceof ServerLevel server) {
                        LivingEntity victim = event.getEntity();
                        server.sendParticles(ParticleTypes.GUST_EMITTER_SMALL, victim.getX(), victim.getY(0.5), victim.getZ(), 1, 0, 0, 0, 0);
                        server.sendParticles(ParticleTypes.ELECTRIC_SPARK, victim.getX(), victim.getY(1.0), victim.getZ(), 15, 0.3, 0.3, 0.3, 0.2);
                        server.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS, 0.6f, 1.8f);
                        server.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.MACE_SMASH_GROUND, SoundSource.PLAYERS, 1.0f, 0.6f);

                        Vec3 launch = victim.getDeltaMovement().add(attacker.getLookAngle().scale(0.5 + (finalBonus * 0.15)));
                        victim.setDeltaMovement(launch);
                    }
                }
            }
        }
    }

    public static void accumulate(LivingEntity entity, float amount) {
        boolean hasEffect = false;
        for (var effectInstance : entity.getActiveEffects()) {
            if (effectInstance.getEffect().getRegisteredName().equals("bonappetit:discharge")) {
                hasEffect = true;
                break;
            }
        }

        if (hasEffect) {
            float current = RESERVOIR.getOrDefault(entity, 0f);
            RESERVOIR.put(entity, Math.min(current + amount, 40.0f));
        }
    }
}