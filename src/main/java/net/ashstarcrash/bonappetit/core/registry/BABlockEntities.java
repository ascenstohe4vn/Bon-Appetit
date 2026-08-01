package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.content.blockentity.CookingPotEntity;
import net.ashstarcrash.bonappetit.core.content.blockentity.CopperTankEntity;
import net.ashstarcrash.bonappetit.core.content.blockentity.DryingRackEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BABlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BonAppetit.ID);

    public static final Supplier<BlockEntityType<CookingPotEntity>> COOKING_POT =
            BLOCK_ENTITIES.register("cooking_pot", () -> BlockEntityType.Builder.of(
                    CookingPotEntity::new, BABlocks.COOKING_POT.get()).build(null));
    public static final Supplier<BlockEntityType<DryingRackEntity>> DRYING_RACK =
            BLOCK_ENTITIES.register("drying_rack", () -> BlockEntityType.Builder.of(
                    DryingRackEntity::new, BABlocks.DRYING_RACK.get()).build(null));
    public static final Supplier<BlockEntityType<CopperTankEntity>> COPPER_TANK =
            BLOCK_ENTITIES.register("copper_tank", () -> BlockEntityType.Builder.of(
                    CopperTankEntity::new, BABlocks.COPPER_TANK.get()).build(null));

    public static void register(IEventBus eventBus) {BLOCK_ENTITIES.register(eventBus);}
}