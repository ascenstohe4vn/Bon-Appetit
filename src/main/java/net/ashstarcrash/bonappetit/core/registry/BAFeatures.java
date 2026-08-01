package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.common.worldgen.GrapefruitVineTreeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BAFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, BonAppetit.ID);

    public static final DeferredHolder<Feature<?>, GrapefruitVineTreeFeature> GRAPEFRUIT_VINE_TREE=
            FEATURES.register("grapefruit_vine_tree", () -> new GrapefruitVineTreeFeature(HugeFungusConfiguration.CODEC));
}