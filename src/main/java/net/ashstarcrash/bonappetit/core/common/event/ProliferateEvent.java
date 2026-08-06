package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.ashstarcrash.bonappetit.core.registry.BATriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = BonAppetit.ID)
public class ProliferateEvent {
    //Proliferate
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
                    victim.level().playSound(null, victim.getX(), victim.getY(), victim.getZ(), SoundEvents.CHERRY_WOOD_BREAK, SoundSource.HOSTILE, 1.5f, 0.8f);
                    if (attacker instanceof ServerPlayer serverPlayer) BATriggers.SEEDED_RUPTURE.get().trigger(serverPlayer);
                    if (victim.level() instanceof ServerLevel sl) {
                        sl.sendParticles(ParticleTypes.CRIMSON_SPORE, victim.getX(), victim.getY() + 1, victim.getZ(), 30, 0.3, 0.3, 0.3, 0.1);
                        sl.sendParticles(ParticleTypes.EXPLOSION, victim.getX(), victim.getY() + 1, victim.getZ(), 1, 0, 0, 0, 0);
                    }
                } else {
                    victim.addEffect(new MobEffectInstance(BAEffects.SEEDED, 100, stacks));
                    victim.level().playSound(null, victim.getX(), victim.getY(), victim.getZ(), SoundEvents.CHERRY_SAPLING_STEP, SoundSource.PLAYERS, 0.5f, 1.2f);
                }
            }
        }
    }

    //Seeded
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            MobEffectInstance seeded = player.getEffect(BAEffects.SEEDED);
            if (seeded != null) {
                Level level = player.level();
                BlockPos startPos = player.blockPosition();
                BlockPos floorPos = startPos.below();
                BlockState floorState = level.getBlockState(floorPos);

                if (floorState.isAir() || floorState.canBeReplaced()) return;
                if (floorState.is(BlockTags.MOSS_REPLACEABLE)) {
                    level.setBlockAndUpdate(floorPos, Blocks.WARPED_NYLIUM.defaultBlockState());

                    int targetHeight = seeded.getAmplifier() + 1;
                    List<BlockPos> validPositions = new ArrayList<>();

                    for (int i = 0; i < targetHeight; i++) {
                        BlockPos targetPos = startPos.above(i);
                        if (level.getBlockState(targetPos).canBeReplaced()) {
                            validPositions.add(targetPos);
                        } else {
                            break;
                        }
                    }

                    int actualPlacedCount = validPositions.size();
                    if (actualPlacedCount == 0) return;

                    for (int i = 0; i < actualPlacedCount - 1; i++) {
                        level.setBlockAndUpdate(validPositions.get(i), Blocks.TWISTING_VINES_PLANT.defaultBlockState());
                    }

                    BlockPos topPos = validPositions.get(actualPlacedCount - 1);
                    level.setBlockAndUpdate(topPos, Blocks.TWISTING_VINES.defaultBlockState());
                }
            }
        }
    }
}
