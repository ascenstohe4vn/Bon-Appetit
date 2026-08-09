package net.ashstarcrash.bonappetit.core.content.block;

import com.mojang.serialization.MapCodec;
import net.ashstarcrash.bonappetit.core.content.entity.PomegranateSeedEntity;
import net.ashstarcrash.bonappetit.core.registry.BAEntities;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class PomegranateBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<PomegranateBlock> CODEC = simpleCodec(PomegranateBlock::new);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public PomegranateBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, 0));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AGE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState();
        LevelReader level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        for (Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                state = state.setValue(FACING, direction);
                if (state.canSurvive(level, pos)) {
                    return state;
                }
            }
        }
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction dir = state.getValue(FACING);
        BlockPos supportPos = pos.relative(dir.getOpposite());
        BlockState supportState = level.getBlockState(supportPos);

        return supportState.is(Blocks.TWISTING_VINES_PLANT);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);

        if (age < 2) {
            level.setBlock(pos, state.setValue(AGE, age + 1), 2);
        } else if (age == 2) {
            level.setBlock(pos, state.setValue(AGE, 3), 2);
        } else if (age == 3) {
            burstPomegranate(level, pos);
        }
    }

    private void burstPomegranate(ServerLevel level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.NETHER_WART_PLANTED, SoundSource.BLOCKS, 2.0F, 0.8F);
        level.sendParticles(
                new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(BAItems.POMEGRANATE_SEEDS.get())),
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                25, 0.3, 0.3, 0.3, 0.2
        );
        Block.popResource(level, pos, new ItemStack(BAItems.POMEGRANATE_SEEDS.get(), level.random.nextInt(2) + 1));

        double centerX = pos.getX() + 0.5;
        double centerY = pos.getY() + 0.5;
        double centerZ = pos.getZ() + 0.5;
        for (int i = 0; i < 12; i++) {
            PomegranateSeedEntity seed = new PomegranateSeedEntity(BAEntities.POMEGRANATE_SEED.get(), level);
            seed.setPos(centerX, centerY, centerZ);
            double xDir = level.random.nextGaussian();
            double yDir = level.random.nextGaussian();
            double zDir = level.random.nextGaussian();

            seed.shoot(xDir, yDir, zDir, 0.85F, 1.5F);
            level.addFreshEntity(seed);
        }

        level.destroyBlock(pos, false);
    }
}