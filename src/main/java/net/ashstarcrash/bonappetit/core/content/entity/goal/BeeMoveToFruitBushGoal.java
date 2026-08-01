package net.ashstarcrash.bonappetit.core.content.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class BeeMoveToFruitBushGoal extends MoveToBlockGoal {
    private final Bee bee;
    public BeeMoveToFruitBushGoal(Bee bee) {
        super(bee, 1D, 4, 2);
        this.bee = bee;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    private Bee getBee() {
        return this.bee;
    }

    public boolean canBeeUse() {
        return (!this.getBee().hasRestriction() && !this.getBee().isAngry() && this.getBee().hasNectar());
    }

    @Override
    public boolean canUse() {
        return this.canBeeUse() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return this.canBeeUse() && super.canContinueToUse();
    }

    @Override
    protected boolean isValidTarget(LevelReader level, @NotNull BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return (false
        );
    }
}