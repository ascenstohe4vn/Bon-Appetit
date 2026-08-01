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

public class CornCropBlock extends AbstractCornBlock {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(6.0F, 0.0F, 6.0F, 10.0F, 4.0F, 10.0F), Block.box(5.0F, 0.0F, 5.0F, 11.0F, 8.0F, 11.0F), Block.box(3.0F, 0.0F, 3.0F, 13.0F, 12.0F, 13.0F), Block.box(1.0F, 0.0F, 1.0F, 15.0F, 16.0F, 15.0F)};

    public CornCropBlock(BlockBehaviour.Properties properties) {super(properties);}

    public Block getTopBlock() {
        return BABlocks.CORN_TOP.get();
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return this.getAge(state) == this.getMaxAge() && !level.getBlockState(pos.above()).is(this.getTopBlock()) ? false : super.canSurvive(state, level, pos);
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
    }

    public int getMaxAge() {return MAX_AGE;}
    public int getHeight() {return 0;}
}