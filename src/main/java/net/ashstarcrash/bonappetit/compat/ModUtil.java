package net.ashstarcrash.bonappetit.compat;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

public enum ModUtil {
    LOADER(BonAppetit.LOADER),
    COMMON(BonAppetit.COMMON),
    MC(BonAppetit.MC),
    BA(BonAppetit.ID);

    @NotNull private final String id;
    ModUtil(@NotNull final String id) {
        this.id = id;
    }

    public String id() {
        return this.id;
    }

    public ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(this.id, path);
    }

    public <T> TagKey<T> tag(ResourceKey<? extends Registry<T>> registryKey, String tag) {
        return TagKey.create(registryKey, this.asResource(tag));
    }
}