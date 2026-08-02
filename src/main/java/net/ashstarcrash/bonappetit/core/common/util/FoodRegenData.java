package net.ashstarcrash.bonappetit.core.common.util;

public class FoodRegenData {
    private int ticksRemaining;
    private int pulseTimer;

    public FoodRegenData() {
        this.ticksRemaining = 0;
        this.pulseTimer = 0;
    }

    public void addDuration(int ticks, int maxTicks) {
        this.ticksRemaining = Math.min(this.ticksRemaining + ticks, maxTicks);
    }

    public int getTicksRemaining() {
        return ticksRemaining;
    }

    public void setTicksRemaining(int ticksRemaining) {
        this.ticksRemaining = ticksRemaining;
    }

    public int getPulseTimer() {
        return pulseTimer;
    }

    public void setPulseTimer(int pulseTimer) {
        this.pulseTimer = pulseTimer;
    }

    public boolean isActive() {
        return ticksRemaining > 0;
    }
}