package net.ashstarcrash.bonappetit.core.content.entity;

import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.ashstarcrash.bonappetit.core.registry.BAEntities;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class PomegranateSeedEntity extends AbstractArrow implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK =
            SynchedEntityData.defineId(PomegranateSeedEntity.class, EntityDataSerializers.ITEM_STACK);

    public PomegranateSeedEntity(EntityType<? extends PomegranateSeedEntity> type, Level level) {
        super(type, level);
    }

    public PomegranateSeedEntity(Level level, LivingEntity shooter, ItemStack pickupStack) {
        super(BAEntities.POMEGRANATE_SEED.get(), shooter, level, pickupStack, null);
        this.setItem(pickupStack);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ITEM_STACK, new ItemStack(Items.AMETHYST_SHARD));
    }

    public void setSeedItem(ItemStack stack) {
        this.setItem(stack);
        this.setPickupItemStack(stack);
    }

    public void setItem(ItemStack stack) {
        this.getEntityData().set(DATA_ITEM_STACK, stack);
    }

    @Override
    public ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Item", this.getItem().save(this.registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Item", 10)) {
            ItemStack savedItem = ItemStack.parse(this.registryAccess(), tag.getCompound("Item")).orElse(new ItemStack(BAItems.POMEGRANATE_SEEDS.get()));
            this.setSeedItem(savedItem);
        }
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
        if (!this.level().isClientSide() && result.getEntity() instanceof LivingEntity target) {
            target.addEffect(new MobEffectInstance(BAEffects.SEEDED, 600, 1, false, true));
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.NETHER_WART_PLANTED, SoundSource.PLAYERS, 1.0F, 1.2F);
        }
        this.shatter();
    }

    private void shatter() {
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CRIMSON_SPORE,
                    this.getX(), this.getY(), this.getZ(), 5, 0.1, 0.1, 0.1, 0.05);
        }
        this.discard();
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(BAItems.POMEGRANATE_SEEDS.get());
    }
}