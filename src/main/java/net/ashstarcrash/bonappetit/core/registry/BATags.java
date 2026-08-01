package net.ashstarcrash.bonappetit.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class BATags {
    private static TagKey<Item> createItemTag(String name) {return TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace(name));}
    private static TagKey<EntityType<?>> createEntityTypeTag(String name) {return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.withDefaultNamespace(name));}

    public static class Items {
        public static final TagKey<Item> SERVING_CONTAINERS = createItemTag("serving_containers");
        public static final TagKey<Item> CITRUS_FRUITS = createItemTag("citrus_fruits");

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> RICOCHET_IMMUNE = createEntityTypeTag("ricochet_immune");
}}}