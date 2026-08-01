package net.ashstarcrash.bonappetit.core.content.block;

import com.mojang.serialization.MapCodec;
import net.ashstarcrash.bonappetit.core.content.blockentity.CopperTankEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CopperTankBlock extends BaseEntityBlock implements EntityBlock {
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final MapCodec<CopperTankBlock> CODEC = simpleCodec(CopperTankBlock::new);

    public CopperTankBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean player = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
        return this.defaultBlockState().setValue(OPEN, false);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }


    public @NotNull BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        /*if (!level.isClientSide && player.isCreative()) {
            preventCreativeDropFromBottomPart(level, pos, state, player);
        }*/
        super.playerWillDestroy(level, pos, state, player);
        return state;
    }

    /*protected static void preventCreativeDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
        BlockPos alt = half == DoubleBlockHalf.UPPER ? pos.below() : pos.above();
        BlockState altState = level.getBlockState(alt);
        if (altState.is(state.getBlock()) && altState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) != half) {
            BlockState ans = altState.getFluidState().createLegacyBlock(); Blocks.AIR.defaultBlockState();
            level.setBlock(alt, ans, 35);
            level.levelEvent(player, 2001, alt, Block.getId(altState));
        }
    }*/

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof CopperTankEntity tub)) {
            return InteractionResult.PASS;
        }

        ItemStack inHand = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (level.isClientSide) {
            return InteractionResult.sidedSuccess(true);
        }

        if (player.isCrouching() || (!inHand.isEmpty() && inHand.is(ItemTags.PICKAXES))) {
            setOpen(state, level, pos, player);
            return InteractionResult.sidedSuccess(false);
        }

        if (!inHand.isEmpty() && tub.insertAugment(inHand)) {
            player.setItemInHand(InteractionHand.MAIN_HAND, inHand.isEmpty() ? ItemStack.EMPTY : inHand);
            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.8F, 1.0F);
            return InteractionResult.sidedSuccess(false);
        }
        if (inHand.isEmpty() && tub.hasAugment()) {
            ItemStack returned = tub.getAugment();
            tub.clearAugment();

            if (!player.getInventory().add(returned)) {
                player.drop(returned, false);
            }

            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.8F, 1.0F);
            return InteractionResult.sidedSuccess(false);
        }

        IFluidHandlerItem handler;
        if (inHand.is(Items.MILK_BUCKET)) {
            handler = new IFluidHandlerItem() {
                private ItemStack stack = inHand.copy();
                private final int amount = 1000;

                private Fluid milkFluid() {
                    ResourceLocation milkRL = ResourceLocation.fromNamespaceAndPath("minecraft", "milk");
                    return BuiltInRegistries.FLUID.get(milkRL);
                }

                @Override
                public ItemStack getContainer() { return stack; }

                @Override
                public int getTanks() { return 1; }

                @Override
                public FluidStack getFluidInTank(int tank) { return new FluidStack(milkFluid(), amount); }

                @Override
                public int getTankCapacity(int tank) { return amount; }

                @Override
                public boolean isFluidValid(int tank, FluidStack fluidStack) { return fluidStack.getFluid() == milkFluid(); }

                @Override
                public int fill(FluidStack resource, FluidAction action) {
                    if (resource.getFluid() != milkFluid()) return 0;
                    if (action.execute()) stack = new ItemStack(Items.BUCKET);
                    return resource.getAmount();
                }

                @Override
                public FluidStack drain(FluidStack resource, FluidAction action) {
                    if (resource.getFluid() != milkFluid()) return FluidStack.EMPTY;
                    if (action.execute()) stack = new ItemStack(Items.BUCKET);
                    return new FluidStack(milkFluid(), amount);
                }

                @Override
                public FluidStack drain(int maxDrain, FluidAction action) {
                    if (action.execute()) stack = new ItemStack(Items.BUCKET);
                    return new FluidStack(milkFluid(), Math.min(amount, maxDrain));
                }
            };
        } else {
            Optional<IFluidHandlerItem> optional = FluidUtil.getFluidHandler(inHand);
            if (optional.isEmpty()) return InteractionResult.PASS;
            handler = optional.get();
        }

        if (handler != null) {
            FluidStack fillTank = handler.drain(handler.getTankCapacity(0), IFluidHandler.FluidAction.SIMULATE);
            if (!fillTank.isEmpty()) {
                int filled = tub.getTank().fill(fillTank, IFluidHandler.FluidAction.EXECUTE);
                if (filled > 0) {
                    if (!player.isCreative()) {
                        handler.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                        player.setItemInHand(InteractionHand.MAIN_HAND, handler.getContainer());
                    }
                    level.playSound(null, pos, getFluidSound(fillTank, false), SoundSource.BLOCKS, 0.8F, 1.0F);
                    return InteractionResult.sidedSuccess(false);
                }
            }

            FluidStack drainTank = tub.getTank().drain(handler.getTankCapacity(0), IFluidHandler.FluidAction.SIMULATE);
            if (!drainTank.isEmpty()) {
                FluidStack drained = tub.getTank().drain(
                        player.isCreative() ? 0 : drainTank.getAmount(),
                        IFluidHandler.FluidAction.EXECUTE
                );

                ItemStack filledStack = new ItemStack(drainTank.getFluid().getBucket());

                if (!player.isCreative()) {
                    if (inHand.getCount() == 1) {
                        player.setItemInHand(InteractionHand.MAIN_HAND, filledStack);
                    } else {
                        inHand.shrink(1);
                        player.getInventory().add(filledStack);
                    }
                } else {
                    player.getInventory().add(filledStack);
                }

                level.playSound(null, pos, getFluidSound(drained, false), SoundSource.BLOCKS, 0.8F, 1.0F);
                return InteractionResult.sidedSuccess(false);
            }
        }

        return InteractionResult.PASS;
    }

    private static SoundEvent getFluidSound(FluidStack stack, boolean filling) {
        Fluid fluid = stack.getFluid();
        if (fluid == Fluids.WATER) return filling ? SoundEvents.BUCKET_FILL : SoundEvents.BUCKET_EMPTY;
        if (fluid == Fluids.LAVA) return filling ? SoundEvents.BUCKET_FILL_LAVA : SoundEvents.BUCKET_EMPTY_LAVA;

        return filling ? SoundEvents.BUCKET_FILL : SoundEvents.BUCKET_EMPTY;
    }

    //block entity
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public static void setOpen(BlockState state, Level level, BlockPos pos, Player player) {
        boolean open = state.getValue(OPEN);

        level.playSound(null, pos, SoundEvents.COPPER_DOOR_OPEN, SoundSource.BLOCKS, 0.8F, 0.8F);
        level.setBlock(pos, state.setValue(OPEN, !open), 3);
        level.gameEvent(player, !open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
    }

    @NotNull @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CopperTankEntity(blockPos, blockState);
    }

    public static void contentApply(Level level, BlockPos pos) {
        RandomSource rand = level.getRandom();
        BlockEntity entity = level.getBlockEntity(pos);

        if (entity instanceof CopperTankEntity tub) {
            FluidStack stack = tub.getTank().getFluid();
            if (stack.getFluid().isSame(Fluids.LAVA)) {
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 0.8F, 0.8F);
            } else {
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 0.8F, 0.8F);
            }
        }

        if (!level.isClientSide && level instanceof ServerLevel server)
            for(int i = 0; i < 6; ++i) {
                double d0 = (rand.nextDouble() - 0.5) * 0.02;
                double d1 = 0.075D;
                double d2 = (rand.nextDouble() - 0.5) * 0.02;

                double x = pos.getX() + 0.3 + rand.nextDouble() * 0.4;
                double y = pos.getY() + 1;
                double z = pos.getZ() + 0.3 + rand.nextDouble() * 0.4;
                server.sendParticles(ParticleTypes.SNOWFLAKE, x, y, z, 0, d0, d1, d2, 0.5);
            }
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : (lvl, pos, blockState, blockEntity) -> {
            if (blockEntity instanceof CopperTankEntity entity) {
                entity.tick();
            }
        };
    }
}