package net.ashstarcrash.bonappetit.core.content.item;

import net.ashstarcrash.bonappetit.core.registry.BATags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BundleContents;

import java.util.List;

public class DinnerwareBundleItem extends BundleItem {
    public DinnerwareBundleItem(Properties properties) {
        super(properties);
    }

    private static final int BAR_COLOR = Mth.color(0.8F, 0.4F, 1.0F);
    private static final int TOOLTIP_MAX_WEIGHT = 128;

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (stack.getCount() == 1 && action == ClickAction.SECONDARY) {
            BundleContents bundlecontents = (BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS);
            if (bundlecontents == null) {
                return false;
            } else {
                ItemStack itemstack = slot.getItem();
                BundleContents.Mutable bundlecontents$mutable = new BundleContents.Mutable(bundlecontents);
                if (itemstack.isEmpty()) {
                    this.playRemoveOneSound(player);
                    ItemStack itemstack1 = bundlecontents$mutable.removeOne();
                    if (itemstack1 != null) {
                        ItemStack itemstack2 = slot.safeInsert(itemstack1);
                        bundlecontents$mutable.tryInsert(itemstack2);
                    }
                } else if (itemstack.canFitInsideContainerItems()) {
                    if (itemstack.is(BATags.Items.CITRUS_FRUITS)) {
                        int i = bundlecontents$mutable.tryTransfer(slot, player);
                        if (i > 0) {
                            this.playInsertSound(player);
                        }
                    }
                }

                stack.set(DataComponents.BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stack.getCount() != 1) {
            return false;
        } else if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            BundleContents bundlecontents = (BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS);
            if (bundlecontents == null) {
                return false;
            } else {
                BundleContents.Mutable bundlecontents$mutable = new BundleContents.Mutable(bundlecontents);
                if (other.isEmpty()) {
                    ItemStack itemstack = bundlecontents$mutable.removeOne();
                    if (itemstack != null) {
                        this.playRemoveOneSound(player);
                        access.set(itemstack);
                    }
                } else {
                    int i = bundlecontents$mutable.tryInsert(other);
                    if (i > 0) {
                        this.playInsertSound(player);
                    }
                }

                stack.set(DataComponents.BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BAR_COLOR;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        BundleContents bundlecontents = (BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS);
        if (bundlecontents != null) {
            int i = Mth.mulAndTruncate(bundlecontents.weight(), 64);
            tooltipComponents.add(Component.translatable("item.bonappetit.dinnerware_bundle.fullness", new Object[]{i, 64}).withStyle(ChatFormatting.GRAY));
        }
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }
}
