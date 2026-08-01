package net.ashstarcrash.bonappetit.core.common.worldgen;

import com.mojang.serialization.Codec;
import net.ashstarcrash.bonappetit.core.registry.BABlocks;
import net.ashstarcrash.bonappetit.core.content.block.GrapefruitVineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;

public class GrapefruitVineTreeFeature extends Feature<HugeFungusConfiguration> {
    public GrapefruitVineTreeFeature(Codec<HugeFungusConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<HugeFungusConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        boolean generated = Feature.HUGE_FUNGUS.place(context);

        if (generated) {
            BlockPos start = origin.offset(-4, 0, -4);
            BlockPos end = origin.offset(4, 25, 4);

            for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
                if (level.getBlockState(pos).is(Blocks.SHROOMLIGHT)) {
                    BlockPos below = pos.below();
                    if (random.nextBoolean() && level.isEmptyBlock(below)) {
                        int randomAge = random.nextInt(3) + 1;

                        BlockState vineState = BABlocks.GRAPEFRUIT_VINE.get().defaultBlockState().setValue(GrapefruitVineBlock.AGE, randomAge);
                        level.setBlock(below, vineState, 2);
                    }
                }
            }
        }
        return generated;
    }
}