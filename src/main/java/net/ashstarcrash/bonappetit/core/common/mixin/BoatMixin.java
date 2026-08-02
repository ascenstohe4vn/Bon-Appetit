package net.ashstarcrash.bonappetit.core.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.ashstarcrash.bonappetit.core.common.event.GameEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Boat.class)
public class BoatMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F", remap = false), method = "getGroundFriction")
    public float bonappetit$getfriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity, @NotNull Operation<Float> operation) {
        float v = operation.call(state, level, pos, entity);
        if (entity instanceof Boat boat) {
            return GameEvents.getBoatFriction(boat, v);
        }
        return v;
    }
}