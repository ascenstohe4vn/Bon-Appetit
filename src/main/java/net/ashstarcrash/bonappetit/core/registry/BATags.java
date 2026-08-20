package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class BATags {
    public static class Items {
        public static final TagKey<Item> SERVING_CONTAINERS = ba(Registries.ITEM, "serving_containers");
        public static final TagKey<Item> SEEDS_CORN = c(Registries.ITEM, "seeds/corn");
        public static final TagKey<Item> CROPS_CORN = c(Registries.ITEM, "crops/corn");
        public static final TagKey<Item> FOODS_CORN = c(Registries.ITEM, "foods/corn");
        public static final TagKey<Item> FOODS_CHERRY= c(Registries.ITEM, "foods/cherry");
        public static final TagKey<Item> FOODS_APPLE = c(Registries.ITEM, "foods/apple");
        public static final TagKey<Item> FOODS_GRAPEFRUIT = c(Registries.ITEM, "foods/grapefruit");
        public static final TagKey<Item> FOODS_ORANGE = c(Registries.ITEM, "foods/orange");
        public static final TagKey<Item> FOODS_BLOOD_ORANGE = c(Registries.ITEM, "foods/blood_orange");
        public static final TagKey<Item> FOODS_MANGO = c(Registries.ITEM, "foods/mango");
        public static final TagKey<Item> FOODS_APRICOT = c(Registries.ITEM, "foods/apricot");
        public static final TagKey<Item> FOODS_PINEAPPLE = c(Registries.ITEM, "foods/pineapple");
        public static final TagKey<Item> FOODS_BANANA = c(Registries.ITEM, "foods/banana");
        public static final TagKey<Item> FOODS_LEMON = c(Registries.ITEM, "foods/lemon");
        public static final TagKey<Item> FOODS_LIME = c(Registries.ITEM, "foods/lime");
        public static final TagKey<Item> FOODS_KIWI = c(Registries.ITEM, "foods/kiwi");
        public static final TagKey<Item> FOODS_PEAR = c(Registries.ITEM, "foods/pear");
        public static final TagKey<Item> FOODS_GRAPE = c(Registries.ITEM, "foods/grape");
        public static final TagKey<Item> FOODS_PEACH = c(Registries.ITEM, "foods/peach");
        public static final TagKey<Item> FOODS_DRAGON_FRUIT = c(Registries.ITEM, "foods/dragon_fruit");
        public static final TagKey<Item> FOODS_POMEGRANATE = c(Registries.ITEM, "foods/pomegranate");
        public static final TagKey<Item> FOODS_COCONUT = c(Registries.ITEM, "foods/coconut");

        public static final TagKey<Item> FOODS_STRAWBERRY = c(Registries.ITEM, "foods/strawberry");

        public static final TagKey<Item> FOODS_VANILLA = c(Registries.ITEM, "foods/vanilla");
        public static final TagKey<Item> FOODS_TEA_LEAVES = c(Registries.ITEM, "foods/tea_leaves");
        public static final TagKey<Item> FOODS_TEA_LEAVES_GREEN = c(Registries.ITEM, "foods/tea_leaves/green");
        public static final TagKey<Item> FOODS_TEA_LEAVES_YELLOW = c(Registries.ITEM, "foods/tea_leaves/yellow");
        public static final TagKey<Item> FOODS_TEA_LEAVES_BLACK = c(Registries.ITEM, "foods/tea_leaves/black");
        public static final TagKey<Item> FOODS_TEA_LEAVES_MATCHA = c(Registries.ITEM, "foods/tea_leaves/matcha");
        public static final TagKey<Item> CROPS_COFFEE = c(Registries.ITEM, "crops/coffee");
        public static final TagKey<Item> FOODS_COFFEE_BEANS = c(Registries.ITEM, "foods/coffee_beans");

        public static final TagKey<Item> FOODS_CITRUS = c(Registries.ITEM, "foods/citrus");
        public static final TagKey<Item> FOODS_STONE_FRUIT = c(Registries.ITEM, "foods/stone_fruit");
    }
    public static class MobEffects {
        public static final TagKey<MobEffect> LETHAL = c(Registries.MOB_EFFECT, "categories/lethal");
        public static final TagKey<MobEffect> CHOCOLATE_MILK_CURABLES = ba(Registries.MOB_EFFECT, "cures/chocolate_milk");
        public static final TagKey<MobEffect> STRAWBERRY_MILK_CURABLES = ba(Registries.MOB_EFFECT, "cures/strawberry_milk");
        public static final TagKey<MobEffect> BLUEBERRY_MILK_CURABLES = ba(Registries.MOB_EFFECT, "cures/blueberry_milk");
        public static final TagKey<MobEffect> BANANA_MILK_CURABLES = ba(Registries.MOB_EFFECT, "cures/banana_milk");
        public static final TagKey<MobEffect> PEACH_MILK_CURABLES = ba(Registries.MOB_EFFECT, "cures/peach_milk");
        public static final TagKey<MobEffect> CARROT_MILK_CURABLES = ba(Registries.MOB_EFFECT, "cures/carrot_milk");
        public static final TagKey<MobEffect> COFFEE_MILK_CURABLES = ba(Registries.MOB_EFFECT, "cures/coffee_milk");
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