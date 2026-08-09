package net.ashstarcrash.bonappetit.core.registry;

import com.mojang.serialization.MapCodec;
import net.ashstarcrash.bonappetit.core.common.loot.AbandonedMineshaftLootModifier;
import net.ashstarcrash.bonappetit.core.common.loot.DungeonLootModifier;
import net.ashstarcrash.bonappetit.core.common.loot.LeavesLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class BALootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, "bonappetit");

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<DungeonLootModifier>> DUNGEON_LOOT_MODIFIER =
            LOOT_MODIFIERS.register("add_dungeon_item", () -> DungeonLootModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<AbandonedMineshaftLootModifier>> ABANDONED_MINESHAFT_LOOT_MODIFIER =
            LOOT_MODIFIERS.register("add_mineshaft_item", () -> AbandonedMineshaftLootModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<LeavesLootModifier>> LEAVES_LOOT_MODIFIER =
            LOOT_MODIFIERS.register("add_leaves_item", () -> LeavesLootModifier.CODEC);
}
