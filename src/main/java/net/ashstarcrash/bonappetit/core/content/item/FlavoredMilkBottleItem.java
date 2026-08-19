package net.ashstarcrash.bonappetit.core.content.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FlavoredMilkBottleItem extends MilkBottleItem {
    private final TagKey<MobEffect> targetTag;

    public FlavoredMilkBottleItem(Properties properties, TagKey<MobEffect> targetTag) {
        super(properties);
        this.targetTag = targetTag;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof ServerPlayer sp) {
            CriteriaTriggers.CONSUME_ITEM.trigger(sp, stack);
            sp.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!level.isClientSide()) {
            var taggedEffects = entity.getActiveEffects().stream().map(MobEffectInstance::getEffect).filter(effect -> effect.is(targetTag)).toList();
            for (Holder<MobEffect> effect : taggedEffects) entity.removeEffect(effect);
        }
        return super.finishUsingItem(stack, level, entity);
    }
}