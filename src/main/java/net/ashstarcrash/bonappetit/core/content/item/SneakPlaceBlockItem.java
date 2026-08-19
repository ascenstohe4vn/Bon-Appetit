package net.ashstarcrash.bonappetit.core.content.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SneakPlaceBlockItem extends ItemNameBlockItem {
    private final Boolean tooltip;
    public SneakPlaceBlockItem(Block block, Properties properties, Boolean tooltip) {
        super(block, properties);
        this.tooltip = tooltip;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (!context.isSecondaryUseActive()) return InteractionResult.PASS;
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        if (tooltip) {
            tooltipComponents.add(Component.literal("Placeable").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY));
        }
    }
}