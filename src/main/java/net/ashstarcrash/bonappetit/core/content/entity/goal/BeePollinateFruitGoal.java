package net.ashstarcrash.bonappetit.core.content.entity.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Bee;

public class BeePollinateFruitGoal extends Goal {
    private final Bee bee;
    public BeePollinateFruitGoal(Bee bee) {
        this.bee = bee;
    }

    private Bee getBee() {
        return this.bee;
    }

    public boolean canBeeUse() {
        return this.getBee().hasNectar();
    }

    public boolean canBeeContinueToUse() {
        return this.canBeeUse();
    }

    @Override
    public boolean canUse() {
        return this.canBeeUse() && !this.getBee().isAngry();
    }

    @Override
    public boolean canContinueToUse() {
        return this.canBeeContinueToUse() && !this.getBee().isAngry();
    }

    @Override
    public void tick() {
        if (
                this.getBee().level() instanceof ServerLevel level && this.getBee().getRandom().nextInt(this.adjustedTickDelay(30)) == 0
        ) {
            for (int i = 1; i <= 2; ++i) {

            }
        }
    }
}