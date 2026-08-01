package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

@Mod(value = BonAppetit.ID) @EventBusSubscriber(modid = BonAppetit.ID)
public class ReflectionEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onReflectProjectile(ProjectileImpactEvent impact) {
        HitResult hit = impact.getRayTraceResult();
        if (hit != null && hit.getType() == HitResult.Type.ENTITY && hit instanceof EntityHitResult result && result.getEntity() instanceof LivingEntity victim && !victim.level().isClientSide) {
            MobEffectInstance reflectionInstance = null;
            for (var effectInstance : victim.getActiveEffects()) {
                if (effectInstance.getEffect().getRegisteredName().equals("bonappetit:reflection")) {
                    reflectionInstance = effectInstance;
                    break;
                }
            }

            if (reflectionInstance != null && victim.level() instanceof ServerLevel server) {
                float chance = Math.min(0.4f + 0.35f * reflectionInstance.getAmplifier(), 1.0f);
                if (server.random.nextFloat() >= chance) return;

                boolean hasDischarge = false;
                for (var effectInstance : victim.getActiveEffects()) {
                    if (effectInstance.getEffect().getRegisteredName().equals("bonappetit:discharge")) {
                        hasDischarge = true;
                        break;
                    }
                }

                if (hasDischarge) {
                    float damageAmount = 4.0f;
                    if (impact.getProjectile() instanceof AbstractArrow arrow) {
                        damageAmount = (float) arrow.getBaseDamage();
                    }
                    DischargeEvent.accumulate(victim, damageAmount * 0.75f);
                }

                Vec3 motion = impact.getProjectile().getDeltaMovement();
                Vec3 newMotion = new Vec3(-motion.x, -motion.y, -motion.z).scale(1.3).add(0, 0.1, 0);
                impact.getProjectile().setDeltaMovement(newMotion);

                server.getChunkSource().broadcastAndSend(
                        impact.getProjectile(),
                        new ClientboundSetEntityMotionPacket(impact.getProjectile().getId(), newMotion));

                server.sendParticles(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER, victim.getX(), victim.getY(0.5), victim.getZ(), 12, 0.3, 0.3, 0.3, 0.05);

                for (int i = 0; i < 10; i++) {
                    double angle = i * Math.PI / 5;
                    server.sendParticles(ParticleTypes.WAX_OFF,
                            victim.getX() + Math.cos(angle) * 0.9,
                            victim.getY() + 0.2 + (server.random.nextFloat() * 1.2),
                            victim.getZ() + Math.sin(angle) * 0.9,
                            1, 0, 0, 0, 0.02);
                }

                server.playSound(null, victim.getX(), victim.getY(), victim.getZ(), SoundEvents.SLIME_SQUISH, SoundSource.NEUTRAL, 1.2F, 1.5F);
                server.playSound(null, victim.getX(), victim.getY(), victim.getZ(), SoundEvents.COPPER_BULB_TURN_ON, SoundSource.NEUTRAL, 1.0F, 1.8F);
                server.playSound(null, victim.getX(), victim.getY(), victim.getZ(), SoundEvents.SHIELD_BLOCK, SoundSource.NEUTRAL, 0.6F, 1.1F);
                server.playSound(null, victim.getX(), victim.getY(), victim.getZ(), SoundEvents.HONEY_BLOCK_BREAK, SoundSource.NEUTRAL, 0.8F, 1.4F);

                impact.setCanceled(true);
            }
        }
    }
}