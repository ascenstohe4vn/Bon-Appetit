package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.ashstarcrash.bonappetit.core.common.template.BAFlavorCakeBlock;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static net.ashstarcrash.bonappetit.core.registry.BAEffects.*;
import static net.ashstarcrash.bonappetit.core.registry.BAItems.*;
import static net.ashstarcrash.bonappetit.core.registry.BATags.Items.*;
import static net.minecraft.world.effect.MobEffects.*;

public class FlavoredItems {
    @FunctionalInterface
    public interface ModRequirement {
        boolean isMet();

        static ModRequirement of(ModUtil mod) {
            return mod::isLoaded;
        }

        static ModRequirement anyOf(ModUtil... mods) {
            return () -> {
                for (ModUtil mod : mods) if (mod.isLoaded()) return true;
                return false;
            };
        }

        static ModRequirement allOf(ModUtil... mods) {
            return () -> {
                for (ModUtil mod : mods) if (!mod.isLoaded()) return false;
                return true;
            };
        }

        default ModRequirement and(ModRequirement other) {
            return () -> this.isMet() && other.isMet();
        }

        default ModRequirement or(ModRequirement other) {
            return () -> this.isMet() || other.isMet();
        }

        default ModRequirement not() {
            return () -> !this.isMet();
        }
    }

    public record Effect(Supplier<Holder<MobEffect>> source, int amplifier, int duration, float chance) {
        public static Effect of(Supplier<Holder<MobEffect>> source, int amplifier, int duration) {
            return new Effect(source, amplifier, duration, 1F);
        }

        public static Effect of(Supplier<Holder<MobEffect>> source, int amplifier, int duration, float chance) {
            return new Effect(source, amplifier, duration, chance);
        }
    }

    public record SliceForm(String suffix, int nutrition, float saturation, boolean fast) {}

    public sealed interface RegisteredFood {
        ItemLike asItemLike();
        ResourceLocation getId();

        record ItemBacked(DeferredItem<Item> item) implements RegisteredFood {
            @Override public ItemLike asItemLike() { return item.get(); }
            @Override public ResourceLocation getId() { return item.getId(); }
        }

        record BlockBacked(DeferredBlock<? extends net.minecraft.world.level.block.Block> block) implements RegisteredFood {
            @Override public ItemLike asItemLike() { return block.get(); }
            @Override public ResourceLocation getId() { return block.getId(); }
        }
    }

    public enum ItemType {
        GUMMY(2, 0.2F, true, "_gummy", null),
        COOKIE(2, 0.125F, true, "_cookie", null),
        POPSICLE(3, 0.3F, false, "_popsicle", null),
        PIE(8, 0.5F, false, "_pie", new SliceForm("_pie_slice", 2, 0.5F, true)),
        CAKE(0, 0F, false, "_cake", new SliceForm("_cake_slice", 2, 0.1F, true));

        public final int nutrition;
        public final float saturation;
        public final boolean fast;
        public final String suffix;
        @Nullable public final SliceForm slice;

        ItemType(int nutrition, float saturation, boolean fast, String suffix, @Nullable SliceForm slice) {
            this.nutrition = nutrition;
            this.saturation = saturation;
            this.fast = fast;
            this.suffix = suffix;
            this.slice = slice;
        }

        public boolean hasSlice() {
            return slice != null;
        }
    }

    public static final class Variant {
        @Nullable private Effect effect;
        @Nullable private Effect sliceEffect;
        @Nullable private Integer nutrition;
        @Nullable private Float saturation;
        @Nullable private Integer sliceNutrition;
        @Nullable private Float sliceSaturation;
        public boolean baseDisabled;
        public boolean sliceDisabled;
        @Nullable private ModUtil requiredMod;

        private Variant() {}

        public static Variant plain() {
            return new Variant();
        }

        public static Variant effect(Supplier<Holder<MobEffect>> source, int amplifier, int duration) {
            Variant v = new Variant();
            v.effect = Effect.of(source, amplifier, duration);
            return v;
        }

        public static Variant effect(Supplier<Holder<MobEffect>> source, int amplifier, int duration, float chance) {
            Variant v = new Variant();
            v.effect = Effect.of(source, amplifier, duration, chance);
            return v;
        }

        public Variant sliceEffect(Supplier<Holder<MobEffect>> source, int amplifier, int duration) {
            this.sliceEffect = Effect.of(source, amplifier, duration);
            return this;
        }

        public Variant sliceEffect(Supplier<Holder<MobEffect>> source, int amplifier, int duration, float chance) {
            this.sliceEffect = Effect.of(source, amplifier, duration, chance);
            return this;
        }

        public Variant nutrition(int nutrition) {
            this.nutrition = nutrition;
            return this;
        }

        public Variant saturation(float saturation) {
            this.saturation = saturation;
            return this;
        }

        public Variant sliceNutrition(int nutrition) {
            this.sliceNutrition = nutrition;
            return this;
        }

        public Variant sliceSaturation(float saturation) {
            this.sliceSaturation = saturation;
            return this;
        }

        public Variant noSlice() {
            this.sliceDisabled = true;
            return this;
        }

        public Variant sliceOnly() {
            this.baseDisabled = true;
            return this;
        }

        public boolean isAvailable() {
            return requiredMod == null || requiredMod.isLoaded();
        }
    }

    public sealed interface TagOrItem {
        Ingredient toIngredient();

        record Tag(TagKey<Item> tag) implements TagOrItem {
            @Override public Ingredient toIngredient() { return Ingredient.of(tag); }
        }

        record Single(Supplier<ItemLike> item) implements TagOrItem {
            @Override public Ingredient toIngredient() { return Ingredient.of(item.get()); }
        }

        static TagOrItem tag(TagKey<Item> tag) { return new Tag(tag); }
        static TagOrItem item(Supplier<ItemLike> item) { return new Single(item); }
    }

    public record FlavorIngredient(TagOrItem full, @Nullable TagOrItem slice) {
        public static FlavorIngredient of(TagOrItem full) {
            return new FlavorIngredient(full, null);
        }

        public static FlavorIngredient of(TagOrItem full, TagOrItem slice) {
            return new FlavorIngredient(full, slice);
        }

        public TagOrItem preferSlice() {
            return slice != null ? slice : full;
        }
    }

    public enum Flavor {
        APPLE("apple", FlavorIngredient.of(TagOrItem.tag(FOODS_APPLE), TagOrItem.item(() -> APPLE_SLICE)), Map.of(
                ItemType.PIE, Variant.effect(() -> ABSORPTION, 1, 900).sliceEffect(() -> ABSORPTION, 1, 200),
                ItemType.CAKE, Variant.effect(() -> ABSORPTION, 0, 200).sliceEffect(() -> ABSORPTION, 0, 200),
                ItemType.GUMMY, Variant.effect(() -> ABSORPTION, 4, 100)
        )),
        CHERRY("cherry", FlavorIngredient.of(TagOrItem.tag(FOODS_CHERRY)), Map.of(
                ItemType.PIE, Variant.effect(() -> TWIN_STRIKE, 1, 900).sliceEffect(() -> TWIN_STRIKE, 1, 200),
                ItemType.CAKE, Variant.effect(() -> TWIN_STRIKE, 0, 200).sliceEffect(() -> TWIN_STRIKE, 0, 200),
                ItemType.GUMMY, Variant.effect(() -> TWIN_STRIKE, 3, 200)
        )),
        GRAPEFRUIT("grapefruit", FlavorIngredient.of(TagOrItem.tag(FOODS_GRAPEFRUIT), TagOrItem.item(() -> GRAPEFRUIT_SLICE)), Map.of(
                ItemType.PIE, Variant.effect(() -> REFLECTION, 1, 900).sliceEffect(() -> REFLECTION, 1, 200),
                ItemType.GUMMY, Variant.plain()
        )),
        ORANGE("orange", FlavorIngredient.of(TagOrItem.tag(FOODS_ORANGE), TagOrItem.item(() -> ORANGE_SLICE)), Map.of(
                ItemType.PIE, Variant.effect(() -> CONCENTRATION, 1, 900).sliceEffect(() -> CONCENTRATION, 1, 200),
                ItemType.CAKE, Variant.effect(() -> CONCENTRATION, 0, 200).sliceEffect(() -> CONCENTRATION, 0, 150),
                ItemType.GUMMY, Variant.effect(() -> CONCENTRATION, 0, 100)
        )),
        BLOOD_ORANGE("blood_orange", FlavorIngredient.of(TagOrItem.tag(FOODS_BLOOD_ORANGE)), Map.of(
                ItemType.GUMMY, Variant.effect(() -> NUZLOCKE, 4, 6000)
        )),
        MANGO("mango", FlavorIngredient.of(TagOrItem.tag(FOODS_MANGO)), Map.of(
                ItemType.PIE, Variant.plain().sliceNutrition(2).sliceSaturation(0.5F),
                ItemType.GUMMY, Variant.plain()
        )),
        PINEAPPLE("pineapple", FlavorIngredient.of(TagOrItem.tag(FOODS_PINEAPPLE)), Map.of(
                ItemType.CAKE, Variant.plain().sliceNutrition(2).sliceSaturation(0.125F),
                ItemType.GUMMY, Variant.effect(() -> AGILITY, 0, 100)
        )),
        BANANA("banana", FlavorIngredient.of(TagOrItem.tag(FOODS_BANANA)), Map.of(
                ItemType.CAKE, Variant.plain().sliceNutrition(2).sliceSaturation(0.125F),
                ItemType.GUMMY, Variant.effect(() -> AGILITY, 0, 100)
        )),
        LEMON("lemon", FlavorIngredient.of(TagOrItem.tag(FOODS_LEMON), TagOrItem.item(() -> LEMON_SLICE)), Map.of(
                ItemType.COOKIE, Variant.effect(() -> RESONANCE, 0, 100),
                ItemType.PIE, Variant.effect(() -> RESONANCE, 1, 900).sliceEffect(() -> RESONANCE, 1, 200),
                ItemType.CAKE, Variant.effect(() -> RESONANCE, 0, 200).sliceEffect(() -> RESONANCE, 0, 200),
                ItemType.GUMMY, Variant.effect(() -> RESONANCE, 9, 20)
        )),
        LIME("lime", FlavorIngredient.of(TagOrItem.tag(FOODS_LIME), TagOrItem.item(() -> LIME_SLICE)), Map.of(
                ItemType.COOKIE, Variant.effect(() -> DISSONANCE, 0, 100),
                ItemType.POPSICLE, Variant.effect(() -> DISSONANCE, 0, 300).nutrition(3).saturation(0.4F),
                ItemType.PIE, Variant.effect(() -> DISSONANCE, 1, 900).sliceEffect(() -> DISSONANCE, 1, 200),
                ItemType.CAKE, Variant.effect(() -> DISSONANCE, 0, 200).sliceEffect(() -> DISSONANCE, 0, 200),
                ItemType.GUMMY, Variant.effect(() -> DISSONANCE, 9, 20)
        )),
        PEACH("peach", FlavorIngredient.of(TagOrItem.tag(FOODS_PEACH)), Map.of(
                ItemType.GUMMY, Variant.effect(() -> VITALITY, 4, 200)
        )),
        DRAGON_FRUIT("dragon_fruit", FlavorIngredient.of(TagOrItem.tag(FOODS_DRAGON_FRUIT), TagOrItem.item(() -> DRAGON_FRUIT_SLICE)), Map.of(
                ItemType.PIE, Variant.plain(),
                ItemType.GUMMY, Variant.plain()
        )),
        POMEGRANATE("pomegranate", FlavorIngredient.of(TagOrItem.tag(FOODS_POMEGRANATE), TagOrItem.item(() -> POMEGRANATE_SLICE)), Map.of(
                ItemType.PIE, Variant.effect(() -> PROLIFERATE, 1, 900).sliceEffect(() -> PROLIFERATE, 1, 200),
                ItemType.GUMMY, Variant.effect(() -> PROLIFERATE, 4, 100)
        )),
        COCONUT("coconut", FlavorIngredient.of(TagOrItem.tag(FOODS_COCONUT)), Map.of(
                ItemType.GUMMY, Variant.effect(() -> RAMPART, 2, 200)
        )),
        COFFEE_CHERRY("coffee_cherry", FlavorIngredient.of(TagOrItem.tag(CROPS_COFFEE)), Map.of(
                ItemType.GUMMY, Variant.effect(() -> CAFFEINATED, 2, 100)
        )),

        PASSION_FRUIT("passion_fruit", FlavorIngredient.of(
                TagOrItem.tag(ModUtil.COMMON.tag(Registries.ITEM, "foods/passion_fruit"))
        ), Map.of(
                ItemType.GUMMY, Variant.effect(() -> ModUtil.AT.getEffect("spitting", null), 4, 75)
        ), ModRequirement.of(ModUtil.AT));

        public final String id;
        public final FlavorIngredient ingredient;
        public final Map<ItemType, Variant> variants;
        @Nullable public final ModRequirement requirement;

        Flavor(String id, FlavorIngredient ingredient, Map<ItemType, Variant> variants) {
            this(id, ingredient, variants, null);
        }

        Flavor(String id, FlavorIngredient ingredient, Map<ItemType, Variant> variants, @Nullable ModRequirement requirement) {
            this.id = id;
            this.ingredient = ingredient;
            this.variants = variants;
            this.requirement = requirement;
        }

        public boolean isAvailable() {
            return requirement == null || requirement.isMet();
        }
    }

    public static final Map<String, RegisteredFood> REGISTRY = new LinkedHashMap<>();
    public static final Map<String, Boolean> IS_SLICE = new LinkedHashMap<>();

    static {
        for (Flavor flavor : Flavor.values()) {
            for (Map.Entry<ItemType, Variant> entry : flavor.variants.entrySet()) {
                ItemType type = entry.getKey();
                Variant v = entry.getValue();

                if (type == ItemType.CAKE) {
                    if (!v.baseDisabled) {
                        registerCake(flavor.id + type.suffix,
                                v.nutrition != null ? v.nutrition : type.nutrition,
                                v.saturation != null ? v.saturation : type.saturation,
                                v.effect);
                    }
                    if (type.hasSlice() && !v.sliceDisabled) {
                        SliceForm slice = type.slice;
                        register(flavor.id + slice.suffix(),
                                v.sliceNutrition != null ? v.sliceNutrition : slice.nutrition(),
                                v.sliceSaturation != null ? v.sliceSaturation : slice.saturation(),
                                slice.fast(), v.sliceEffect, true);
                    }
                    continue;
                }

                if (!v.baseDisabled) {
                    register(flavor.id + type.suffix,
                            v.nutrition != null ? v.nutrition : type.nutrition,
                            v.saturation != null ? v.saturation : type.saturation,
                            type.fast, v.effect, false);
                }

                if (type.hasSlice() && !v.sliceDisabled) {
                    SliceForm slice = type.slice;
                    register(flavor.id + slice.suffix(),
                            v.sliceNutrition != null ? v.sliceNutrition : slice.nutrition(),
                            v.sliceSaturation != null ? v.sliceSaturation : slice.saturation(),
                            slice.fast(), v.sliceEffect, true);
                }
            }
        }
    }

    public static void touch() {}

    private static void register(String id, int nutrition, float saturation, boolean fast, @Nullable Effect effect, boolean isSlice) {
        DeferredItem<Item> item = ITEMS.registerItem(id, props -> {
            FoodProperties.Builder food = new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation);
            if (fast) food.fast();
            if (effect != null) {
                food.effect(() -> {
                    Holder<MobEffect> holder = effect.source().get();
                    return holder == null ? null : new MobEffectInstance(holder, effect.duration(), effect.amplifier());
                }, effect.chance());
            }
            return new Item(props.food(food.build()));
        });

        REGISTRY.put(id, new RegisteredFood.ItemBacked(item));
        IS_SLICE.put(id, isSlice);
    }

    private static void registerCake(String id, int nutrition, float saturation, @Nullable Effect effect) {
        FoodProperties.Builder food = new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation);
        if (effect != null) {
            food.effect(() -> {
                Holder<MobEffect> holder = effect.source().get();
                return holder == null ? null : new MobEffectInstance(holder, effect.duration(), effect.amplifier());
            }, effect.chance());
        }
        DeferredBlock<BAFlavorCakeBlock> block = BABlocks.registerFlavorCakes(id, food.build());
        REGISTRY.put(id, new RegisteredFood.BlockBacked(block));
        IS_SLICE.put(id, false);
    }

    public static boolean isAvailable(Flavor flavor, ItemType type) {
        return flavor.variants.containsKey(type) && flavor.isAvailable();
    }

    public static void forEachRegistered(ItemType type, BiConsumer<Flavor, RegisteredFood> baseConsumer, BiConsumer<Flavor, RegisteredFood> sliceConsumer) {
        for (Flavor flavor : Flavor.values()) {
            if (!isAvailable(flavor, type)) continue;
            Variant v = flavor.variants.get(type);

            if (!v.baseDisabled) {
                RegisteredFood base = REGISTRY.get(flavor.id + type.suffix);
                if (base != null) baseConsumer.accept(flavor, base);
            }
            if (type.hasSlice() && !v.sliceDisabled) {
                RegisteredFood slice = REGISTRY.get(flavor.id + type.slice.suffix());
                if (slice != null) sliceConsumer.accept(flavor, slice);
            }
        }
    }

    public static void forEachRegistered(ItemType type, BiConsumer<Flavor, RegisteredFood> consumer) {
        forEachRegistered(type, consumer, consumer);
    }

    public static void addAllOfType(CreativeModeTab.Output output, ItemType type) {
        forEachRegistered(type, (flavor, food) -> output.accept(food.asItemLike()));
    }

    public static void addGummies(CreativeModeTab.Output output) { addAllOfType(output, ItemType.GUMMY); }
    public static void addCookies(CreativeModeTab.Output output) { addAllOfType(output, ItemType.COOKIE); }
    public static void addPopsicles(CreativeModeTab.Output output) { addAllOfType(output, ItemType.POPSICLE); }
    public static void addPies(CreativeModeTab.Output output) { addAllOfType(output, ItemType.PIE); }
    public static void addCakes(CreativeModeTab.Output output) { addAllOfType(output, ItemType.CAKE); }
}