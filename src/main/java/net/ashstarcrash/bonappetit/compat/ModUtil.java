package net.ashstarcrash.bonappetit.compat;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public enum ModUtil {
    BA(BonAppetit.ID),
    LOADER("neoforge"),
    COMMON("c"),
    MC("minecraft"),

    AS("appleskin"),
    AT("atmospheric"),
    AUT("autumnity"),
    BB("buzzier_bees"),
    BG("berry_good"),
    C("create"),
    CCK("create_central_kitchen"),
    EMI("emi"),
    ENV("environmental"),
    FD("farmersdelight"),
    N("neapolitan"),
    NV("nirvana"),
    S("salt"),
    SS("snowyspirit"),
    UA("upgrade_aquatic"),
    WS("windswept"),
    WW("woodworks");

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

    public Item getItem(String path) {
        return BuiltInRegistries.ITEM.get(this.asResource(path));
    }

    public Block getBlock(String path) {
        return BuiltInRegistries.BLOCK.get(this.asResource(path));
    }

    @Nullable
    public <T> T getEffect(String path, @Nullable T templateOrFallback) {
        if (!this.isLoaded()) return templateOrFallback;

        if (templateOrFallback instanceof Holder<?> fallbackHolder) {
            return (T) BuiltInRegistries.MOB_EFFECT.getHolder(this.asResource(path))
                    .map(h -> (Holder<MobEffect>) (Holder<?>) h)
                    .orElse((Holder<MobEffect>) fallbackHolder);
        }

        if (templateOrFallback instanceof MobEffectInstance fallbackInstance) {
            return (T) BuiltInRegistries.MOB_EFFECT.getHolder(this.asResource(path))
                    .map(holder -> new MobEffectInstance(holder, fallbackInstance.getDuration(), fallbackInstance.getAmplifier()))
                    .orElse(fallbackInstance);
        }

        return (T) BuiltInRegistries.MOB_EFFECT.getHolder(this.asResource(path))
                .map(h -> (Holder<MobEffect>) (Holder<?>) h)
                .orElse(null);
    }

    public <T> TagKey<T> tag(ResourceKey<? extends Registry<T>> registryKey, String tag) {
        return TagKey.create(registryKey, this.asResource(tag));
    }

    public RecipeOutput output(RecipeOutput baseOutput) {
        return baseOutput.withConditions(new ModLoadedCondition(this.id));
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