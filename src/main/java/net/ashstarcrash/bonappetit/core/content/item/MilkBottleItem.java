package net.ashstarcrash.bonappetit.core.content.item;

import net.ashstarcrash.bonappetit.core.common.template.BABottleDrinkItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.EffectCures;
import org.jetbrains.annotations.NotNull;

public class MilkBottleItem extends BABottleDrinkItem {
    public MilkBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof ServerPlayer sp) {
            CriteriaTriggers.CONSUME_ITEM.trigger(sp, stack);
            sp.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!level.isClientSide()) removeRandomEffect(entity);
        return super.finishUsingItem(stack, level, entity);
    }

    protected void removeRandomEffect(LivingEntity entity) {
        var curableEffects = entity.getActiveEffects().stream().filter(instance -> instance.getCures().contains(EffectCures.MILK)).toList();
        if (!curableEffects.isEmpty()) {
            var randomEffect = curableEffects.get(entity.getRandom().nextInt(curableEffects.size()));
            entity.removeEffect(randomEffect.getEffect());
        }
    }
}