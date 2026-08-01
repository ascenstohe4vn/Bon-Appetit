package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.joml.Vector3f;

import java.util.WeakHashMap;

@Mod(value = BonAppetit.ID) @EventBusSubscriber(modid = BonAppetit.ID)
public class FervorEvent {
    private static final WeakHashMap<Player, MiningState> STATES = new WeakHashMap<>();

    private static class MiningState {
        int blocksBroken;
        long lastActionTick;
        boolean maxReached;

        MiningState(long tick) {
            this.blocksBroken = 0;
            this.lastActionTick = tick;
            this.maxReached = false;
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        MiningState state = STATES.get(player);
        if (state != null) {
            long currentTick = player.level().getGameTime();

            if (currentTick - state.lastActionTick > 8) {
                state.blocksBroken = 0;
                state.maxReached = false;
            }

            state.blocksBroken++;
            state.lastActionTick = currentTick;
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        MobEffectInstance effect = null;
        for (var effectInstance : player.getActiveEffects()) {
            if (effectInstance.getEffect().getRegisteredName().equals("bonappetit:fervor")) {
                effect = effectInstance;
                break;
            }
        }

        if (effect != null) {
            long currentTick = player.level().getGameTime();
            MiningState state = STATES.computeIfAbsent(player, p -> new MiningState(currentTick));

            if (currentTick - state.lastActionTick > 8) {
                state.blocksBroken = 0;
                state.maxReached = false;
            }

            state.lastActionTick = currentTick;

            int amp = effect.getAmplifier();
            float baseSpeed = 0.90f;
            float maxSpeedLimit = 1.30f + (amp * 0.10f);
            float accelPerBlock = 0.05f + (amp * 0.025f);

            float currentMultiplier = baseSpeed + (state.blocksBroken * accelPerBlock);

            if (state.maxReached || currentMultiplier >= maxSpeedLimit) {
                state.maxReached = true;
                currentMultiplier = maxSpeedLimit;
            }

            event.setNewSpeed(event.getOriginalSpeed() * currentMultiplier);

            if (state.maxReached && player.level().isClientSide() && player.level().random.nextFloat() < 0.05f) {
                event.getPosition().ifPresent(pos -> spawnEdgeParticle(player, pos));
            }
        } else {
            STATES.remove(player);
        }
    }

    private static void spawnEdgeParticle(Player player, BlockPos pos) {
        var random = player.level().random;
        int axis = random.nextInt(3);
        double x = pos.getX() + (axis == 0 ? random.nextDouble() : random.nextInt(2));
        double y = pos.getY() + (axis == 1 ? random.nextDouble() : random.nextInt(2));
        double z = pos.getZ() + (axis == 2 ? random.nextDouble() : random.nextInt(2));
        player.level().addParticle(new DustParticleOptions(new Vector3f(1.0f, 0.68f, 0.12f), 0.7f), x, y, z, 0, 0, 0);
    }
}