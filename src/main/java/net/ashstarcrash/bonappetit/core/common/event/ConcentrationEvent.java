package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@EventBusSubscriber(modid = BonAppetit.ID)
public class ConcentrationEvent {
    public static final Set<Projectile> CONCENTRATION_PROJECTILES = new HashSet<>();

    @SubscribeEvent
    public static void onProjectileSpawn(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Projectile projectile)) return;
        if (!(projectile.getOwner() instanceof LivingEntity shooter)) return;

        var registry = shooter.level().registryAccess().registryOrThrow(Registries.MOB_EFFECT);
        var concentration = registry.wrapAsHolder(BAEffects.CONCENTRATION.get());
        if (shooter.getEffect(concentration) == null) return;

        int amplifier = shooter.getEffect(concentration).getAmplifier();
        if (shooter.hasEffect(concentration)) {
            projectile.setDeltaMovement(projectile.getDeltaMovement().scale(0.75 + (amplifier * 0.25)));
            if (!(projectile instanceof ThrownTrident) && !(projectile instanceof FishingHook)) {
                CONCENTRATION_PROJECTILES.add(projectile);
                projectile.setNoGravity(true);
            }
            if (projectile instanceof AbstractArrow arrow) {
                arrow.setCritArrow(true);
                arrow.setBaseDamage(arrow.getBaseDamage() * (1.0 + (0.5 * (amplifier + 1))));
                if (shooter.level() instanceof ServerLevel server) {
                    server.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.NEUTRAL, 0.8F, 0.6F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(LevelTickEvent.Post event) {
        Level level = event.getLevel();
        if (level.isClientSide()) return;

        Iterator<Projectile> track = CONCENTRATION_PROJECTILES.iterator();
        while (track.hasNext()) {
            Projectile projectile = track.next();
            if (projectile.isRemoved()) { track.remove(); continue; }

            Vec3 motion = projectile.getDeltaMovement();
            if (projectile.getDeltaMovement().lengthSqr() < 0.0025) {
                projectile.discard();
                track.remove();
            }
        }
    }

}
