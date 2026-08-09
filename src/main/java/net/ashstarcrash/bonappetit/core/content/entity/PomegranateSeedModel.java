package net.ashstarcrash.bonappetit.core.content.entity;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class PomegranateSeedModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart seed;

    public PomegranateSeedModel(ModelPart root) {
        this.root = root;
        this.seed = root.getChild("seed");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("seed", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -4.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.seed.xRot = headPitch * ((float)Math.PI / 180F);
        this.seed.yRot = netHeadYaw * ((float)Math.PI / 180F);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}