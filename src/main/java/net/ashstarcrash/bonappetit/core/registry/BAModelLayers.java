package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class BAModelLayers {
    public static final ModelLayerLocation PITCHFORK = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BonAppetit.ID, "pitchfork"), "main");
    public static final ModelLayerLocation DRAGON_SHARD = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(BonAppetit.ID, "dragon_shard"), "main");
}