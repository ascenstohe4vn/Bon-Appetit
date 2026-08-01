package net.ashstarcrash.bonappetit.core.content.entity;

import net.ashstarcrash.bonappetit.core.registry.BAEntities;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ThrownPitchfork extends ThrownTrident {
    public ThrownPitchfork(EntityType<? extends ThrownTrident> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownPitchfork(Level level, LivingEntity shooter, ItemStack pickupItemStack) {
        super(BAEntities.PITCHFORK.get(), level);
    }

    public ThrownPitchfork(Level level, double x, double y, double z, ItemStack pickupItemStack) {
        super(BAEntities.PITCHFORK.get(), level);
    }

    protected @NotNull ItemStack getDefaultPickupItem() {
        return new ItemStack(BAItems.PITCHFORK.get());
    }

    @Override
    protected float getWaterInertia() {
        return 0.3F;
    }

    public boolean shouldRender(double x, double y, double z) {
        return true;
    }
}