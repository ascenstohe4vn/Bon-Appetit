package net.ashstarcrash.bonappetit.compat;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public enum ModUtil {
    LOADER(BonAppetit.LOADER),
    COMMON(BonAppetit.COMMON),
    MC(BonAppetit.MC),
    BA(BonAppetit.ID),
    EMI(BonAppetit.EMI);

    @NotNull private final String id;

    ModUtil(@NotNull final String id) {
        this.id = id;
    }

    public String id() {
        return this.id;
    }

    public boolean isLoaded() {
        return ModList.get().isLoaded(this.id);
    }

    public ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(this.id, path);
    }

    public <T> TagKey<T> tag(ResourceKey<? extends Registry<T>> registryKey, String tag) {
        return TagKey.create(registryKey, this.asResource(tag));
    }

    @Nullable
    public static ItemStack getEmiFallbackStack(int mouseX, int mouseY) {
        if (!EMI.isLoaded()) return null;
        try {
            var interaction = dev.emi.emi.screen.EmiScreenManager.getHoveredStack(mouseX, mouseY, true, false);
            if (interaction == null || interaction.isEmpty()) return null;

            var ingredient = interaction.getStack();
            var emiStacks = ingredient.getEmiStacks();
            if (emiStacks.isEmpty()) return null;

            return emiStacks.get(0).getItemStack();
        } catch (Throwable t) {
            return null;
        }
    }
}