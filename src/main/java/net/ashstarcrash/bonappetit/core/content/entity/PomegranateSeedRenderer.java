package net.ashstarcrash.bonappetit.core.content.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BAModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PomegranateSeedRenderer extends EntityRenderer<PomegranateSeedEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BonAppetit.ID, "textures/entity/pomegranate_seed.png");
    private final PomegranateSeedModel<PomegranateSeedEntity> model;

    public PomegranateSeedRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PomegranateSeedModel<>(context.bakeLayer(BAModelLayers.POMEGRANATE_SEED));
    }

    @Override
    public void render(PomegranateSeedEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.15D, 0.0D);

        float yaw = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw - 90.0F));

        float pitch = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        poseStack.mulPose(Axis.ZP.rotationDegrees(pitch));

        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(PomegranateSeedEntity entity) {
        return TEXTURE;
    }
}