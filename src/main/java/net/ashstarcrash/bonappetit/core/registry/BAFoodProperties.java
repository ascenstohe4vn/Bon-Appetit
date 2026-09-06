package net.ashstarcrash.bonappetit.core.registry;

import com.google.common.collect.ImmutableMap;
import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.core.common.util.RandomMobEffectInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Map;

import static net.ashstarcrash.bonappetit.core.registry.BAEffects.*;
import static net.minecraft.world.effect.MobEffects.*;
import static net.minecraft.world.item.Items.*;

public class BAFoodProperties {
    //seeds
    public static final FoodProperties POMEGRANATE_SEEDS = (new FoodProperties.Builder())
            .nutrition(1).saturationModifier(0.0F).fast().alwaysEdible()
            .effect(() -> new MobEffectInstance(SEEDED, 100, 0), 0.8F).build();

    //fruits
    public static final FoodProperties GENERIC_FRUIT = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.3F).build();
    public static final FoodProperties GENERIC_FRUIT_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.175F).build();
    public static final FoodProperties GOLDEN_CHERRIES = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.85F)
            .effect(() -> new MobEffectInstance(REGENERATION, 100, 0), 1F)
            .effect(() -> new MobEffectInstance(TWIN_STRIKE, 1200, 1), 1F).build();
    public static final FoodProperties GOLDEN_ORANGE = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(1.1F)
            .effect(() -> new MobEffectInstance(CONCENTRATION, 2400, 0), 1F)
            .effect(() -> new MobEffectInstance(DAMAGE_RESISTANCE, 600, 0), 1F).build();

    //berries
    public static final FoodProperties GENERIC_BERRY = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.1F).build();

    //veggies
    public static final FoodProperties CORN = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.2F).build();

    public static final FoodProperties ONION = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.6f).build();
    public static final FoodProperties ONION_SLICE = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.4f).fast().build();

    //grains
    public static final FoodProperties RICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.05F).build();

    //tea and coffee
    public static final FoodProperties TEA_LEAVES = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.5F)
            .effect(() -> new MobEffectInstance(CONFUSION, 200, 0), 0.75F).build();

    //misc
    public static final FoodProperties ACORN = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(CONFUSION, 300, 0), 0.3F).build();
    public static final FoodProperties ROASTED_ACORN = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.275F).build();

    //basic ingredients/meals (<1 fruit)
    public static final FoodProperties RAW_CORN_TORTILLA = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.2F)
            .effect(() -> new MobEffectInstance(CONFUSION, 100, 0), 0.5f).build();
    public static final FoodProperties CORN_TORTILLA = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.8F).build();
    public static final FoodProperties CORNBREAD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5f).build();
    public static final FoodProperties ONION_RINGS = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.45f).build();
    public static final FoodProperties HONEY_APPLE = (new FoodProperties.Builder())
            .nutrition(7).saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(ABSORPTION, 200, 0), 0.8F).build();
    public static final FoodProperties CANDY_APPLE = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(ABSORPTION, 100, 0), 1F).build();
    public static final FoodProperties CARAMEL_APPLE = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.375F)
            .effect(() -> new MobEffectInstance(ABSORPTION, 150, 0), 1F)
            .effect(() -> new MobEffectInstance(VIGOR, 200, 0), 1F).build();
    public static final FoodProperties CANDIED_LIME_SLICE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.35f).build();
    public static final FoodProperties JERKY = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.1F).fast().build();
    public static final FoodProperties CHOCOLATE_BAR = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.3F)
            .effect(() -> new MobEffectInstance(VIGOR, 200, 0), 1F).build();
    public static final FoodProperties BROWNIE = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.3F)
            .effect(() -> new MobEffectInstance(VIGOR, 300, 0), 1F).build();
    public static final FoodProperties CARAMEL = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.2F)
            .effect(() -> new MobEffectInstance(VIGOR, 200, 0), 0.85F).build();

    //intermediate meals (multi-fruit)
    public static final FoodProperties CHICKEN_QUESADILLA = (new FoodProperties.Builder())
            .nutrition(10).saturationModifier(0.8F).build();
    public static final FoodProperties JOCKEY_SANDWICH = (new FoodProperties.Builder())
            .nutrition(11).saturationModifier(0.8F).build();

    //feasts/good meals
    public static final FoodProperties JEWELED_RICE_BOWL = (new FoodProperties.Builder())
            .nutrition(12).saturationModifier(1.15F).usingConvertsTo(BOWL)
            .effect(() -> new MobEffectInstance(FLAK, 750, 1), 1f)
            .effect(() -> new MobEffectInstance(PROLIFERATE, 450, 0), 1f)
            .effect(() -> new MobEffectInstance(SEEDED, 200, 1), 1f)
            .effect(() -> new MobEffectInstance(DISSONANCE, 300, 0), 1f).build();
    public static final FoodProperties PANETTONE = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.8F)
            .effect(() -> new RandomMobEffectInstance(
                    new RandomMobEffectInstance.EffectEntry(REFLECTION, 200, 0, 1.0F),
                    new RandomMobEffectInstance.EffectEntry(CONCENTRATION, 200, 0, 1.0F),
                    new RandomMobEffectInstance.EffectEntry(RESONANCE, 200, 0, 1.0F),
                    new RandomMobEffectInstance.EffectEntry(DISSONANCE, 200, 0, 1.0F)), 1.0F).build();
    public static final FoodProperties STOLLEN = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.9F)
            .effect(() -> new RandomMobEffectInstance(
                    new RandomMobEffectInstance.EffectEntry(REFLECTION, 300, 0, 1.0F),
                    new RandomMobEffectInstance.EffectEntry(CONCENTRATION, 300, 0, 1.0F),
                    new RandomMobEffectInstance.EffectEntry(RESONANCE, 300, 0, 1.0F),
                    new RandomMobEffectInstance.EffectEntry(DISSONANCE, 300, 0, 1.0F)), 1.0F).build();

    //sweets
    public static final FoodProperties BLOSSOM_DANGO = (new FoodProperties.Builder())
            .nutrition(10).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(TWIN_STRIKE, 450, 0), 1F)
            .effect(() -> new MobEffectInstance(CAFFEINATED, 450, 0 /* temp until the matcha effect is implemented */), 1F)
            .effect(() -> new MobEffectInstance(VIGOR, 300, 0), 1F).build();
    public static final FoodProperties GROVE_DANGO = (new FoodProperties.Builder())
            .nutrition(10).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(PROLIFERATE, 450, 0), 1F)
            .effect(() -> new MobEffectInstance(RESONANCE, 450, 0), 1F)
            .effect(() -> new MobEffectInstance(ABSORPTION, 450, 0), 1F)
            .effect(() -> new MobEffectInstance(VIGOR, 300, 0), 1F).build();
    public static final FoodProperties TWILIGHT_DANGO = (new FoodProperties.Builder())
            .nutrition(10).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(DISCHARGE, 450, 0 /* temp until the grape effect is implemented */), 1F)
            .effect(() -> new MobEffectInstance(CAFFEINATED, 450, 0 /* temp until the tea effects are implemented */), 1F)
            .effect(() -> new MobEffectInstance(VIGOR, 300, 0), 1F).build();
    public static final FoodProperties SUNRISE_DANGO = (new FoodProperties.Builder())
            .nutrition(10).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(FLAK, 450, 0), 1F)
            .effect(() -> new MobEffectInstance(RESONANCE, 450, 0), 1F)
            .effect(() -> new MobEffectInstance(CONCENTRATION, 450, 0), 1F)
            .effect(() -> new MobEffectInstance(VIGOR, 300, 0), 1F).build();
    public static final FoodProperties SPONGECAKE = (new FoodProperties.Builder())
            .nutrition(5).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(VIGOR, 300, 0), 1F).build();

    //pies and cake
    public static final FoodProperties CAKE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.1F).fast()
            .effect(() -> new MobEffectInstance(VIGOR, BAConfig.VANILLA_CAKE_EFFECT.get() ? 200 : 0, 0), 1f).build();
    public static final FoodProperties PUMPKIN_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.35F).fast().build();

    //drinks
    public static final FoodProperties TISANE = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.7F).alwaysEdible()
            .effect(() -> new MobEffectInstance(ABSORPTION, 200, 0), 1.0F).build();
    public static final FoodProperties COCONUT_MILK_BOTTLE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.5F).alwaysEdible().build();
    public static final FoodProperties CHOCOLATE_MILK_BOTTLE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.5F).alwaysEdible()
            .effect(() -> new MobEffectInstance(VIGOR, 150, 0), 0.75F).build();
    public static final FoodProperties STRAWBERRY_MILK_BOTTLE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.5F).alwaysEdible()
            .effect(() -> new MobEffectInstance(REGENERATION, 150, 0), 0.75F).build();
    public static final FoodProperties BLUEBERRY_MILK_BOTTLE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.5F).alwaysEdible()
            .effect(() -> new MobEffectInstance(HEAL /* temp effect */, 150, 0), 0.75F).build();
    public static final FoodProperties BANANA_MILK_BOTTLE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.5F).alwaysEdible()
            .effect(() -> new MobEffectInstance(AGILITY, 150, 0), 0.75F).build();
    public static final FoodProperties PEACH_MILK_BOTTLE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.5F).alwaysEdible()
            .effect(() -> new MobEffectInstance(VITALITY, 150, 0), 0.75F).build();
    public static final FoodProperties CARROT_MILK_BOTTLE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.5F).alwaysEdible()
            .effect(() -> new MobEffectInstance(NIGHT_VISION, 150, 0), 0.75F).build();
    public static final FoodProperties COFFEE_MILK_BOTTLE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.5F).alwaysEdible()
            .effect(() -> new MobEffectInstance(CAFFEINATED, 150, 0), 0.75F).build();
    public static final FoodProperties HORCHATA = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.6F).alwaysEdible().build();

    public static final FoodProperties LEMONADE = (new FoodProperties.Builder())
            .nutrition(5).saturationModifier(0.55F)
            .effect(() -> new MobEffectInstance(RESONANCE, 300, 0), 1f).alwaysEdible().build();
    public static final FoodProperties LIMEADE = (new FoodProperties.Builder())
            .nutrition(5).saturationModifier(0.575F)
            .effect(() -> new MobEffectInstance(DISSONANCE, 300, 0), 1f).alwaysEdible().build();
    public static final FoodProperties PINK_LEMONADE = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.8F)
            .effect(() -> new MobEffectInstance(TWIN_STRIKE, 600, 0), 1f)
            .effect(() -> new MobEffectInstance(RESONANCE, 300, 0), 1f).alwaysEdible().build();
    public static final FoodProperties PINK_LIMEADE = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.815F)
            .effect(() -> new MobEffectInstance(PROLIFERATE, 800, 0), 1f)
            .effect(() -> new MobEffectInstance(SEEDED, 100, 0), 1f)
            .effect(() -> new MobEffectInstance(DISSONANCE, 300, 0), 1f).alwaysEdible().build();
    public static final FoodProperties BLUEBERRY_LIMEADE = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.8F)
            .effect(() -> new MobEffectInstance(DISSONANCE, 300, 0), 1f).alwaysEdible().build();
    public static final FoodProperties LIME_GREEN_TEA = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.775F)
            .effect(() -> new MobEffectInstance(DISSONANCE, 300, 0), 1f)
            .effect(() -> new MobEffectInstance(CAFFEINATED, 300, 0 /* temp until the tea effects are implemented */), 0.8f).alwaysEdible().build();
    public static final FoodProperties DRAGON_FRUIT_LATTE = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.625F)
            .effect(() -> new MobEffectInstance(FLAK, 750, 1), 1F).build();
    public static final FoodProperties PINK_LADY = (new FoodProperties.Builder())
            .nutrition(9).saturationModifier(0.6F).alwaysEdible()
            .effect(() -> new MobEffectInstance(ABSORPTION, 600, 1), 0.9F)
            .effect(() -> new MobEffectInstance(PROLIFERATE, 300, 0), 1F)
            .effect(() -> new MobEffectInstance(RESONANCE, 300, 0), 1F)
            .effect(() -> new MobEffectInstance(TWIN_STRIKE, 200, 0), 0.75F).build();
    public static final FoodProperties CHERRY_LIME_REFRESHER = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.7F)
            .effect(() -> new MobEffectInstance(TWIN_STRIKE, 600, 1), 1f)
            .effect(() -> new MobEffectInstance(DISSONANCE, 300, 0), 1f)
            .effect(() -> new MobEffectInstance(CAFFEINATED, 200, 0 /* temp until the tea effects are implemented */), 0.8f).alwaysEdible().build();

    public static class Compat {
        public static final Map<Item, FoodProperties> VANILLA_EFFECTS = (new ImmutableMap.Builder<Item, FoodProperties>())
                .put(Items.BEETROOT_SOUP, new FoodProperties.Builder().effect(() -> new MobEffectInstance(ROOTED, 600), 1.0F).build()).build();
        public static final FoodProperties COCHINEAL_SPONGECAKE = (new FoodProperties.Builder())
                .nutrition(6).saturationModifier(0.65F)
                .effect(() -> new MobEffectInstance(NUZLOCKE, 12000, 1), 1F)
                .effect(() -> new MobEffectInstance(VIGOR, 300, 1), 1F).build();
    }
}