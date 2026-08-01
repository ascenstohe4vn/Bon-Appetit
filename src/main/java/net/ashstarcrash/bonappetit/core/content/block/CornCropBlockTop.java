package net.ashstarcrash.bonappetit.core.content.block;

import net.ashstarcrash.bonappetit.core.registry.BABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CornCropBlockTop extends AbstractCornBlock {
    public static final int MAX_AGE = 1;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 1);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(1.0F, 0.0F, 1.0F, 15.0F, 5.0F, 15.0F), Block.box(1.0F, 0.0F, 1.0F, 15.0F, 14.0F, 15.0F)};
    public CornCropBlockTop(BlockBehaviour.Properties properties) {super(properties);}

    public Block getTopBlock() {return null;}

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        Block var6 = below.getBlock();
        if (var6 instanceof CornCropBlock base) {
            if (base.isMaxAge(below)) {
                return super.canSurvive(state, level, pos);
            }
        }
        return false;
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[(Integer)state.getValue(this.getAgeProperty())];
    }

    public int getMaxAge() {
        return MAX_AGE;
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BABlocks.CORN_BASE.get());
    }

    public int getHeight() {
        return 1;
    }
}