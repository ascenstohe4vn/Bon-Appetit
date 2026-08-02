package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class BATags {
    public static class Items {
        public static final TagKey<Item> SERVING_CONTAINERS = ba(Registries.ITEM, "serving_containers");
        public static final TagKey<Item> FOODS_CITRUS = c(Registries.ITEM, "foods/citrus");
    }
    public static class EntityTypes {
        public static final TagKey<EntityType<?>> RICOCHET_IMMUNE = ba(Registries.ENTITY_TYPE, "ricochet_immune");
    }



    private static <T> TagKey<T> ba(ResourceKey<? extends Registry<T>> registry, String name) {
        return ModUtil.BA.tag(registry, name);
    }
    public static <T> TagKey<T> nf(ResourceKey<? extends Registry<T>> registry, String name) {
        return ModUtil.LOADER.tag(registry, name);
    }
    private static <T> TagKey<T> c(ResourceKey<? extends Registry<T>> registry, String name) {
        return ModUtil.COMMON.tag(registry, name);
    }
}