package net.ashstarcrash.bonappetit.core.content.item;

import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EclipseCookieItem extends Item {
    public EclipseCookieItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        if (entity instanceof Player player && !level.isClientSide()) {
            if (player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                player.addEffect(new MobEffectInstance(BAEffects.RESONANCE, 200, 0, false, true));
            } else {
                player.addEffect(new MobEffectInstance(BAEffects.VIGOR, 200, 0, false, true));
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.EAT;
    }
}