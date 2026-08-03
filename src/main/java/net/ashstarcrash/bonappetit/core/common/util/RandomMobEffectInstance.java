package net.ashstarcrash.bonappetit.core.common.util;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RandomMobEffectInstance extends MobEffectInstance {
    public record EffectEntry(Holder<MobEffect> effect, int duration, int amplifier, float chance) {}

    private final List<EffectEntry> pool;
    private final EffectEntry selectedEntry;
    private static final RandomSource RANDOM = RandomSource.create();

    @SafeVarargs
    public RandomMobEffectInstance(EffectEntry... entries) {
        this(List.of(entries), selectEntry(List.of(entries)));
    }

    private RandomMobEffectInstance(List<EffectEntry> pool, EffectEntry selected) {
        super(selected.effect(), selected.duration(), selected.amplifier());
        this.pool = pool;
        this.selectedEntry = selected;
    }

    private static EffectEntry selectEntry(List<EffectEntry> pool) {
        if (pool.isEmpty()) {
            throw new IllegalArgumentException("RandomMobEffectInstance requires at least one entry");
        }
        EffectEntry candidate = pool.get(RANDOM.nextInt(pool.size()));
        if (RANDOM.nextFloat() <= candidate.chance()) {
            return candidate;
        }
        return pool.get(0);
    }

    public List<EffectEntry> getPool() {
        return pool;
    }

    public EffectEntry getSelectedEntry() {
        return selectedEntry;
    }

    @Override
    public @NotNull Holder<MobEffect> getEffect() {
        return selectedEntry.effect();
    }

    @Override
    public int getDuration() {
        return selectedEntry.duration();
    }

    @Override
    public int getAmplifier() {
        return selectedEntry.amplifier();
    }
}