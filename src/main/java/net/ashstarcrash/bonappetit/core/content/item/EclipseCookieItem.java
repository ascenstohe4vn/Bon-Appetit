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

public class EclipseCookieItem extends Item {
    public EclipseCookieItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (entity instanceof Player player) {
            InteractionHand hand = player.getUsedItemHand();

            if (hand == InteractionHand.MAIN_HAND) {
                if (!level.isClientSide) {
                    player.addEffect(new MobEffectInstance(BAEffects.RESONANCE, 200, 0, false, true));
                }
            } else {
                if (!level.isClientSide) {
                    player.addEffect(new MobEffectInstance(BAEffects.CAFFEINATED, 200, 0, false, true));
                }
            }
        }
        return result;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {return UseAnim.EAT;}
}