package net.ashstarcrash.bonappetit.compat;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public enum Modid {
    LOADER(BonAppetit.LOADER),
    MC(BonAppetit.MC),
    BA(BonAppetit.ID);

    @NotNull private final String id;
    Modid(@NotNull final String id) {
        this.id = id;
    }

    public ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(String.valueOf(this), path);
    }

    public TagKey<Item> it(String tag) {
        return ItemTags.create(this.asResource(tag));
    }
}