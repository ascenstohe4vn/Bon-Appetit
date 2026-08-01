package net.ashstarcrash.bonappetit.core.content.blockentity;

import net.ashstarcrash.bonappetit.core.registry.BABlockEntities;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class DryingRackEntity extends BlockEntity {
    private int dryingProgress = 0;
    private final int DRY_TIME = 1200;
    private boolean isDrying = false;

    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private float rotation;

    public DryingRackEntity(BlockPos pos, BlockState blockState) {
        super(BABlockEntities.DRYING_RACK.get(), pos, blockState);
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        ItemStack stack = inventory.getStackInSlot(0);
        if (stack.isEmpty()) {
            dryingProgress = 0;
            isDrying = false;
            return;
        }

        if (level.canSeeSky(worldPosition.above())) {
            isDrying = true;
            dryingProgress++;

            if (level.getGameTime() % 10 == 0) {
                level.addParticle(
                        ParticleTypes.CLOUD,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 1.0,
                        worldPosition.getZ() + 0.5,
                        0.0, 0.05, 0.0
                );
            }

            if (stack.getItem() == BAItems.GRAPES.get()) {
                if (dryingProgress >= DRY_TIME) {
                    inventory.setStackInSlot(0, new ItemStack(BAItems.RAISINS.get()));
                    dryingProgress = 0;
                    isDrying = false;

                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                }
            } else {
                dryingProgress = 0;
                isDrying = false;
            }
            if (stack.getItem() == Items.ROTTEN_FLESH) {
                if (dryingProgress >= DRY_TIME) {
                    inventory.setStackInSlot(0, new ItemStack(BAItems.JERKY.get()));
                    dryingProgress = 0;
                    isDrying = false;

                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                }
            } else {
                dryingProgress = 0;
                isDrying = false;
            }
        } else {
            isDrying = false;
        }
    }

    public void clearContents() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}