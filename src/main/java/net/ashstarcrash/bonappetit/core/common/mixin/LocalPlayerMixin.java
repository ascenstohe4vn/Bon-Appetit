package net.ashstarcrash.bonappetit.core.common.mixin;

import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
    @ModifyConstant(method = "aiStep", constant = @org.spongepowered.asm.mixin.injection.Constant(floatValue = 0.2F, ordinal = 0))
    private float bonappetit$modifyLeftImpulseMultiplier(float original) {
        return bonappetit$resolveMultiplier(original);
    }

    @ModifyConstant(method = "aiStep", constant = @org.spongepowered.asm.mixin.injection.Constant(floatValue = 0.2F, ordinal = 1))
    private float bonappetit$modifyForwardImpulseMultiplier(float original) {
        return bonappetit$resolveMultiplier(original);
    }

    @Unique
    private float bonappetit$resolveMultiplier(float original) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        InteractionHand hand = self.getUsedItemHand();
        ItemStack stack = self.getItemInHand(hand);

        TagKey<Item> drinksTag = ModUtil.COMMON.tag(Registries.ITEM, "drinks");
        TagKey<Item> magicDrinksTag = ModUtil.COMMON.tag(Registries.ITEM, "drinks/magic");
        TagKey<Item> ominousDrinksTag = ModUtil.COMMON.tag(Registries.ITEM, "drinks/ominous");

        boolean isPlainDrink = stack.is(drinksTag) && !stack.is(magicDrinksTag) && !stack.is(ominousDrinksTag);
        if (isPlainDrink) {
            return (float) BAConfig.DRINK_MOVEMENT_MULTIPLIER.get().doubleValue();
        }

        boolean isMagicOrOminousDrink = stack.is(magicDrinksTag) || stack.is(ominousDrinksTag);
        if (!isMagicOrOminousDrink) {
            return (float) BAConfig.FOOD_MOVEMENT_MULTIPLIER.get().doubleValue();
        }

        return original;
    }
}