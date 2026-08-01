package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class BADamageTypes {
    public static final ResourceKey<DamageType> TWIN_HIT = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(BonAppetit.ID, "twin_hit"));

    public static DamageSource getSource(Level level, ResourceKey<DamageType> type, Entity attacker) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type), attacker);
    }
}