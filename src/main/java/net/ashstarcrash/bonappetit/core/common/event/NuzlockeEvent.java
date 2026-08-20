package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BAAttachments;
import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = BonAppetit.ID)
public class NuzlockeEvent {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (attacker instanceof ServerPlayer player) {
            MobEffectInstance instance = player.getEffect(BAEffects.NUZLOCKE);
            if (instance != null) {
                int currentKills = player.getData(BAAttachments.NUZLOCKE_KILLS) + 1;
                player.setData(BAAttachments.NUZLOCKE_KILLS, currentKills);

                if (currentKills >= (20 * (instance.getAmplifier() + 1))) {
                    player.removeEffect(BAEffects.NUZLOCKE);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BELL_RESONATE, SoundSource.MASTER, 5.0F, 1.0F);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BELL_RESONATE, SoundSource.MASTER, 5.0F, 1.0F);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BELL_RESONATE, SoundSource.MASTER, 5.0F, 1.0F);

                    player.setData(BAAttachments.NUZLOCKE_KILLS, 0);
                }
            }
        }

        if (event.getEntity() instanceof ServerPlayer victim) {
            if (victim.hasEffect(BAEffects.NUZLOCKE)) {
                Level level = victim.level();
                BlockState floorState = level.getBlockState(victim.blockPosition().below());

                if (!floorState.isAir() && !floorState.canBeReplaced()) {
                    if (floorState.is(BlockTags.MOSS_REPLACEABLE)) {
                        level.setBlockAndUpdate(victim.blockPosition(), Blocks.POPPY.defaultBlockState());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Added event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getEffectInstance() != null && event.getEffectInstance().is(BAEffects.NUZLOCKE)) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BELL_BLOCK, SoundSource.MASTER, 5.0F, 0.5F);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BELL_BLOCK, SoundSource.MASTER, 5.0F, 0.5F);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BELL_BLOCK, SoundSource.MASTER, 5.0F, 0.5F);
            }
        }
    }

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getEffectInstance().is(BAEffects.NUZLOCKE)) {
                int amplifier = event.getEffectInstance().getAmplifier();
                int currentKills = player.getData(BAAttachments.NUZLOCKE_KILLS);

                if (currentKills < (20 * (amplifier + 1))) {
                    player.hurt(player.level().damageSources().source(DamageTypes.STARVE), 8266);
                }
                player.setData(BAAttachments.NUZLOCKE_KILLS, 0);
            }
        }
    }

    @SubscribeEvent
    public static void onEffectRemove(MobEffectEvent.Remove event) {
        if (event.getEntity() instanceof Player) {
            if (event.getEffectInstance() != null && event.getEffectInstance().is(BAEffects.NUZLOCKE) && event.getCure() instanceof EffectCure) {
                event.setCanceled(true);
            }
        }
    }
}