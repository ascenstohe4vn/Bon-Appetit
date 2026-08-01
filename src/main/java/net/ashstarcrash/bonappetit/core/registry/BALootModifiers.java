package net.ashstarcrash.bonappetit.core.registry;

import com.mojang.serialization.MapCodec;
import net.ashstarcrash.bonappetit.core.common.loot.DungeonLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class BALootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, "bonappetit");

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<DungeonLootModifier>> DUNGEON_LOOT_MODIFIER =
            LOOT_MODIFIER_SERIALIZERS.register("add_item", () -> DungeonLootModifier.CODEC);
}
