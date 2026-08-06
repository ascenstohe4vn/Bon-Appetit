package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BADamageTypes;
import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.*;

@EventBusSubscriber(modid = BonAppetit.ID)
public class TwinStrikeEvent {
    private static final List<CherryEcho> ECHO_QUEUE = new ArrayList<>();
    private static final Map<UUID, Integer> TWIN_STRIKE_COOLDOWN = new HashMap<>();

    private record CherryEcho(LivingEntity attacker, LivingEntity victim, float damage, int timer) {}

    @SubscribeEvent
    public static void onLivingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            MobEffectInstance cherryEffect = attacker.getEffect(BAEffects.TWIN_STRIKE);
            LivingEntity victim = event.getEntity();
            UUID attackerUUID = attacker.getUUID();
            float damage = event.getAmount();

            if (cherryEffect != null && !event.getSource().is(BADamageTypes.TWIN_HIT)) {
                int cherryAmplifier = cherryEffect.getAmplifier();
                float charge = (attacker instanceof Player p) ? p.getAttackStrengthScale(0.5f) : 1.0f;
                int cooldown = TWIN_STRIKE_COOLDOWN.getOrDefault(attackerUUID, 0);

                if (charge >= 0.9f && cooldown <= 0) {
                    ECHO_QUEUE.add(new CherryEcho(attacker, victim, (float)((damage * BAConfig.CHERRY_EFFECT_INITIAL_MULTI.get()) + (cherryAmplifier * BAConfig.CHERRY_EFFECT_ADDITIVE_MULTI.get())), 5));
                    TWIN_STRIKE_COOLDOWN.put(attackerUUID, 5);

                    attacker.level().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.CHERRY_SAPLING_PLACE, SoundSource.PLAYERS, 0.8f, 0.5f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (ECHO_QUEUE.isEmpty()) return;
        for (int i = ECHO_QUEUE.size() - 1; i >= 0; i--) {
            CherryEcho echo = ECHO_QUEUE.get(i);
            if (echo.timer <= 0) {
                if (echo.victim.isAlive() && !echo.victim.isRemoved()) {
                    echo.victim.invulnerableTime = 0;
                    echo.victim.hurtDuration = 0;
                    echo.victim.hurt(echo.victim.level().damageSources().source(BADamageTypes.TWIN_HIT, echo.attacker), echo.damage);
                    echo.victim.level().playSound(null, echo.victim.getX(), echo.victim.getY(), echo.victim.getZ(),
                            SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.6f, 1.3f);
                    if (echo.victim.level() instanceof ServerLevel sl) {
                        sl.sendParticles(ParticleTypes.SWEEP_ATTACK, echo.victim.getX(), echo.victim.getY() + 1, echo.victim.getZ(), 1, 0, 0, 0, 0);
                    }
                }
                ECHO_QUEUE.remove(i);
            } else {
                ECHO_QUEUE.set(i, new CherryEcho(echo.attacker, echo.victim, echo.damage, echo.timer - 1));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        UUID uuid = player.getUUID();

        if (TWIN_STRIKE_COOLDOWN.containsKey(uuid)) {
            int time = TWIN_STRIKE_COOLDOWN.get(uuid);
            if (time > 0) TWIN_STRIKE_COOLDOWN.put(uuid, time - 1);
        }
    }
}
