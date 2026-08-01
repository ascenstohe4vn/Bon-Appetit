package net.ashstarcrash.bonappetit.core.content.block;

import net.ashstarcrash.bonappetit.core.registry.BABlocks;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractCornBlock extends CropBlock {
    protected AbstractCornBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(this.getAgeProperty());
    }

    public boolean isRandomlyTicking(BlockState state) {
        return !this.isMaxAge(state);
    }

    public static boolean isAreaLoaded(LevelReader level, BlockPos pos, int maxRange) {return true;}
    public static boolean fireOnPreGrowCrops(ServerLevel level, BlockPos pos, BlockState state, boolean bool) {return true;}
    public static void fireOnPostGrowCrops(ServerLevel level, BlockPos pos, BlockState state) {}
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isAreaLoaded(level, pos, 1)) {
            if (level.getRawBrightness(pos, 0) >= 9 && (double)level.random.nextFloat() < 0.6 && this.isValidBonemealTarget(level, pos, state)) {
                float f = getGrowthSpeed(state, level, pos);
                if (fireOnPreGrowCrops(level, pos, state, random.nextInt((int)(40.0F / f) + 1) == 0)) {
                    this.growCropBy(level, pos, state, 1);
                    fireOnPostGrowCrops(level, pos, state);
                }
            }

        }
    }

    public void growCropBy(Level level, BlockPos pos, BlockState state, int increment) {
        if (increment > 0) {
            int newAge = this.getAge(state) + increment;
            int maxAge = this.getMaxAge();
            if (newAge > maxAge) {
                BlockPos above = pos.above();
                BlockState aboveState = level.getBlockState(above);
                Block var10 = aboveState.getBlock();
                if (var10 instanceof AbstractCornBlock) {
                    AbstractCornBlock cornBlock = (AbstractCornBlock) var10;
                    cornBlock.growCropBy(level, above, aboveState, increment);
                }
            } else {
                Block top = this.getTopBlock();
                if (newAge == maxAge && top != null) {
                    level.setBlock(pos.above(), top.defaultBlockState(), 2);
                }

                level.setBlock(pos, this.getStateForAge(newAge), 2);
            }

        }
    }

    public void growCrops(Level level, BlockPos pos, BlockState state) {
        this.growCropBy(level, pos, state, this.getBonemealAgeIncrease(level));
    }

    public boolean getPollinated(Level level, BlockPos pos, BlockState state) {
        this.growCropBy(level, pos, state, 1);
        return true;
    }

    protected ItemLike getBaseSeedId() {
        return BAItems.CORN_KERNELS.get();
    }

    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        int age = this.getAge(state);
        int maxAge = this.getMaxAge();
        if (age + 1 < maxAge) {
            return true;
        } else {
            BlockPos above = pos.above();
            BlockState aboveState = level.getBlockState(above);
            if (age == maxAge) {
                Block var9 = aboveState.getBlock();
                boolean var10000;
                if (var9 instanceof AbstractCornBlock) {
                    AbstractCornBlock cornBlock = (AbstractCornBlock) var9;
                    if (cornBlock.isValidBonemealTarget(level, above, aboveState)) {
                        var10000 = true;
                        return var10000;
                    }
                }

                var10000 = false;
                return var10000;
            } else {
                return this.getTopBlock() == null || aboveState.canBeReplaced();
            }
        }
    }

    protected int getBonemealAgeIncrease(Level level) {
        return super.getBonemealAgeIncrease(level) / 2;
    }

    protected abstract @Nullable Block getTopBlock();

    public abstract int getHeight();

    public boolean isPlantFullyGrown(BlockState state, BlockPos pos, Level level) {
        while (true) {
            Block var5 = state.getBlock();
            if (!(var5 instanceof AbstractCornBlock)) {
                return false;
            }

            AbstractCornBlock cornBlock = (AbstractCornBlock) var5;
            if (!cornBlock.isMaxAge(state)) {
                return false;
            }

            if (cornBlock.getTopBlock() == null) {
                return true;
            }

            pos = pos.above();
            state = level.getBlockState(pos);
        }
    }

    public static boolean spawn(BlockPos pos, LevelAccessor level, int age) {
        if (level.getBlockState(pos).isAir()) {
            boolean top = false;
            if (age > 2) {
                if (!level.getBlockState(pos.above()).isAir()) {
                    return false;
                }

                BlockPos above1 = pos.above();
                if (age > 3) {
                    BlockPos above = pos.above(1);
                    if (!level.getBlockState(above).isAir()) {
                        return false;
                    }

                    top = true;
                    level.setBlock(above, (BlockState) ((Block) BABlocks.CORN_TOP.get()).defaultBlockState().setValue(CornCropBlockTop.AGE, Math.min(age - 5, 1)), 2);
                }

                level.setBlock(pos, (BlockState) ((Block) BABlocks.CORN_BASE.get()).defaultBlockState().setValue(CornCropBlock.AGE, Math.min(age, 3)), 2);
                if (top && level.getBlockState(pos).isAir()) {
                    boolean var6 = true;
                }

                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}
