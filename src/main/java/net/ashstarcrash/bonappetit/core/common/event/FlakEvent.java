package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.ashstarcrash.bonappetit.core.content.entity.DragonShardEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = BonAppetit.ID)
public class FlakEvent {

    @SubscribeEvent
    public static void onEntityDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide() && entity.hasEffect(BAEffects.FLAK) && entity.invulnerableTime <= 10) {
            int amplifier = entity.getEffect(BAEffects.FLAK).getAmplifier();
            float chance = 0.5f + (0.1f * amplifier);
            if (entity.getRandom().nextFloat() < chance) {
                int projectileCount = 5 + (2 * amplifier);
                spawnProjectiles(entity, projectileCount, amplifier);
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDER_DRAGON_HURT, SoundSource.PLAYERS, 2.0f, 0.8f);
            }
        }
    }

    private static void spawnProjectiles(LivingEntity entity, int count, int amplifier) {
        Level level = entity.level();

        for (int i = 0; i < count; i++) {
            DragonShardEntity shard = new DragonShardEntity(level, entity, new ItemStack(BAItems.DRAGON_SHARD.get()));
            double xDir = entity.getRandom().nextDouble() - 0.5;
            double zDir = entity.getRandom().nextDouble() - 0.5;
            double yDir = (entity.getRandom().nextDouble() * 0.3) - 0.1;

            shard.setPos(
                    entity.getX() + (xDir * 0.3),
                    entity.getEyeY() - 0.7  ,
                    entity.getZ() + (zDir * 0.3)
            );
            shard.shoot(xDir, yDir, zDir, 1.6f, 5.0f);
            shard.setBaseDamage(2.5 + (amplifier * 1.0));
            shard.pickup = Arrow.Pickup.CREATIVE_ONLY;
            level.addFreshEntity(shard);
        }

        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH,
                    entity.getX(), entity.getY() + 1.0, entity.getZ(),
                    20, 0.4, 0.2, 0.4, 0.05);
        }
    }
}