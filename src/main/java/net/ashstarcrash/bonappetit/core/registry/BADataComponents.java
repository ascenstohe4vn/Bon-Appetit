package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.common.util.ItemStackWrapper;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BADataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(BonAppetit.ID);

    // Cooking Pot
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemStackWrapper>> MEAL = DATA_COMPONENTS.registerComponentType(
            "meal", builder -> builder.persistent(ItemStackWrapper.CODEC).networkSynchronized(ItemStackWrapper.STREAM_CODEC).cacheEncoding()
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemStackWrapper>> CONTAINER = DATA_COMPONENTS.registerComponentType(
            "container", builder -> builder.persistent(ItemStackWrapper.CODEC).networkSynchronized(ItemStackWrapper.STREAM_CODEC).cacheEncoding()
    );
/*
    // Skillet
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> COOKING_TIME_LENGTH = DATA_COMPONENTS.registerComponentType(
            "cooking_time_length", (builder) -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemStackWrapper>> SKILLET_INGREDIENT = DATA_COMPONENTS.registerComponentType(
            "skillet_ingredient", (builder) -> builder.persistent(ItemStackWrapper.CODEC).networkSynchronized(ItemStackWrapper.STREAM_CODEC).cacheEncoding()
    );*/
}