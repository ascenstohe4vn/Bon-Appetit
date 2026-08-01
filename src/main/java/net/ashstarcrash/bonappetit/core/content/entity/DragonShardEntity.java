package net.ashstarcrash.bonappetit.core.content.entity;

import net.ashstarcrash.bonappetit.core.registry.BAEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class DragonShardEntity extends AbstractArrow implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK =
            SynchedEntityData.defineId(DragonShardEntity.class, EntityDataSerializers.ITEM_STACK);

    public DragonShardEntity(EntityType<? extends DragonShardEntity> type, Level level) {
        super(type, level);
    }

    public DragonShardEntity(Level level, LivingEntity shooter, ItemStack pickupStack) {
        super(BAEntities.DRAGON_SHARD.get(), shooter, level, pickupStack, null);
        this.setItem(pickupStack);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ITEM_STACK, new ItemStack(Items.AMETHYST_SHARD));
    }

    public void setItem(ItemStack stack) {
        this.getEntityData().set(DATA_ITEM_STACK, stack);
    }

    @Override
    public ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.GLASS_BREAK, SoundSource.AMBIENT, 0.5f, 1.2f);
        this.shatter();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
    }

    private void shatter() {
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH,
                    this.getX(), this.getY(), this.getZ(), 5, 0.1, 0.1, 0.1, 0.05);
        }
        this.discard();
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }
}