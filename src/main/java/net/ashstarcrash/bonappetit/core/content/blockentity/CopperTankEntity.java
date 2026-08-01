package net.ashstarcrash.bonappetit.core.content.blockentity;

import net.ashstarcrash.bonappetit.core.registry.BABlockEntities;
import net.ashstarcrash.bonappetit.core.content.block.CopperTankBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

public class CopperTankEntity extends BlockEntity {
    public static final int CAPACITY = 8000;
    private int conversionProgress = 0;

    private ItemStack augmentSlot = ItemStack.EMPTY;

    public boolean insertAugment(ItemStack stack) {
        if (hasAugment() || stack.isEmpty() || !isValidAugment(stack)) return false;

        augmentSlot = stack.split(1); // take one item
        setChanged();
        return true;
    }

    public boolean hasAugment() {
        return !augmentSlot.isEmpty();
    }

    public ItemStack getAugment() {
        return augmentSlot;
    }

    public void clearAugment() {
        augmentSlot = ItemStack.EMPTY;
        setChanged();
    }

    private boolean isValidAugment(ItemStack stack) {
        return stack.is(Items.REDSTONE) || stack.is(Items.GLOWSTONE_DUST);
    }

    private final FluidTank tank = new FluidTank(CAPACITY) {
        @Override
        public int fill(FluidStack stack, FluidAction action) {
            BlockState state = level.getBlockState(worldPosition);
            if (!state.getValue(CopperTankBlock.OPEN)) return 0;
            return super.fill(stack, action);
        }

        @Override
        public FluidStack drain(FluidStack stack, FluidAction action) {
            BlockState state = level.getBlockState(worldPosition);
            if (!state.getValue(CopperTankBlock.OPEN)) return FluidStack.EMPTY;
            return super.drain(stack, action);
        }

        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }
    };

    public FluidTank getTank() {
        return tank;
    }
    public IFluidHandler getFluidHandler() {
        return tank;
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        FluidStack stack = tank.getFluid();
        if (stack.isEmpty() || stack.getFluid() != NeoForgeMod.MILK.get()) {
            conversionProgress = 0;
            return;
        }

        BlockPos below = worldPosition.below();
        BlockState belowState = level.getBlockState(below);
        if (belowState.is(Blocks.ICE) || belowState.is(Blocks.BLUE_ICE) || belowState.is(Blocks.PACKED_ICE)) {
            int fluidAmount = stack.getAmount();
            int baseTime = 300;
            int extra = fluidAmount - 1000;
            if (extra < 0) extra = 0;

            int extraTicks = Math.round(extra * 0.15f);
            int conversionTime = baseTime + extraTicks;

            conversionProgress++;

            if (conversionProgress >= conversionTime) {
                int amount = stack.getAmount();
                tank.drain(amount, IFluidHandler.FluidAction.EXECUTE);

                Fluid resultFluid = Fluids.WATER;
                if (hasAugment()) {
                    ItemStack augment = getAugment();
                    if (augment.is(Items.REDSTONE)) {
                        resultFluid = Fluids.LAVA;
                    } else if (augment.is(Items.GLOWSTONE_DUST)) {
                        resultFluid = Fluids.EMPTY;
                    }
                    clearAugment();
                }
                tank.fill(new FluidStack(resultFluid, amount), IFluidHandler.FluidAction.EXECUTE);
                level.playSound(null, worldPosition, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 0.8F, 1.0F);
                RandomSource rand = level.getRandom();
                if (level instanceof ServerLevel server) {
                    for (int i = 0; i < 6; i++) {
                        double d0 = (rand.nextDouble() - 0.5) * 0.02;
                        double d1 = 0.075D;
                        double d2 = (rand.nextDouble() - 0.5) * 0.02;
                        double x = worldPosition.getX() + 0.3 + rand.nextDouble() * 0.4;
                        double y = worldPosition.getY() + 1;
                        double z = worldPosition.getZ() + 0.3 + rand.nextDouble() * 0.4;
                        server.sendParticles(ParticleTypes.SNOWFLAKE, x, y, z, 0, d0, d1, d2, 0.5);
                    }
                }
                conversionProgress = 0;
            }
        } else {
            conversionProgress = 0;
        }
    }

    public CopperTankEntity(BlockPos pos, BlockState blockState) {
        super(BABlockEntities.COPPER_TANK.get(), pos, blockState);
    }

    @Nullable @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}