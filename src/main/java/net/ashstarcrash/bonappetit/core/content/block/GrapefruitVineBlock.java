package net.ashstarcrash.bonappetit.core.content.block;

import com.mojang.serialization.MapCodec;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class GrapefruitVineBlock extends CropBlock {
    public static final MapCodec<GrapefruitVineBlock> CODEC = simpleCodec(GrapefruitVineBlock::new);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    protected static final VoxelShape SHAPE_STEM = Block.box(7.0D, 12.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    protected static final VoxelShape SHAPE_SMALL = Block.box(6.0D, 10.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape SHAPE_MEDIUM = Block.box(5.0D, 7.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    protected static final VoxelShape SHAPE_RIPE = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public GrapefruitVineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    @NotNull
    public MapCodec<? extends CropBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AGE)) {
            case 1 -> SHAPE_SMALL;
            case 2 -> SHAPE_MEDIUM;
            case 3 -> SHAPE_RIPE;
            default -> SHAPE_STEM;
        };
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.above()).is(Blocks.SHROOMLIGHT);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !this.canSurvive(state, level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !this.isMaxAge(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getRawBrightness(pos, 0) >= 0) {
            int age = this.getAge(state);
            if (age < this.getMaxAge() && random.nextFloat() < 0.05F) {
                level.setBlock(pos, this.getStateForAge(age + 1), 2);
            }
        }
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        if (this.isMaxAge(state)) {
            return new ItemStack(BAItems.GRAPEFRUIT.get());
        }
        return new ItemStack(this);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (this.isMaxAge(state)) {
            int dropCount = level.random.nextInt(2) + 1;
            popResource(level, pos, new ItemStack(BAItems.GRAPEFRUIT.get(), dropCount));
            level.playSound(null, pos, net.minecraft.sounds.SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 0.85F + level.random.nextFloat() * 0.4F);
            level.setBlock(pos, this.getStateForAge(0), 2);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}