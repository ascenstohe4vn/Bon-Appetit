package net.ashstarcrash.bonappetit.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.ashstarcrash.bonappetit.core.content.blockentity.DryingRackEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DryingRackRenderer implements BlockEntityRenderer<DryingRackEntity> {
    public DryingRackRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(DryingRackEntity be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack stack = be.inventory.getStackInSlot(0);
        if (stack.isEmpty()) return;

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BlockState state = be.getBlockState();
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

        float yRot = switch (facing) {case NORTH -> 180.0f; case SOUTH -> 0.0f; case WEST -> 270.0f; case EAST -> 90.0f; default -> 0.0f;};

        poseStack.pushPose();

        poseStack.translate(0.5f, 0.8f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.scale(0.75f, 0.75f, 0.75f);

        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(be.getLevel(), be.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, be.getLevel(), 1);
        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}