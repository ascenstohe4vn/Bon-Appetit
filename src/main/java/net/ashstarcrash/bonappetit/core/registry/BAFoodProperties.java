package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.core.common.util.RandomMobEffectInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

import static net.minecraft.world.item.Items.*;

public class BAFoodProperties {
    public static final FoodProperties CORN = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.2F).build();
    public static final FoodProperties CHERRIES = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.3F).build();
    public static final FoodProperties GOLDEN_CHERRIES = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.85F)
            .effect(() -> new MobEffectInstance(BAEffects.TWIN_STRIKE, 1200, 1), 1F).build();
    public static final FoodProperties LEMON = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.3F).build();
    public static final FoodProperties LEMON_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.175f).build();
    public static final FoodProperties LIME = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.3F).build();
    public static final FoodProperties LIME_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.175f).build();
    public static final FoodProperties DRAGON_FRUIT = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.3F).build();
    public static final FoodProperties DRAGON_FRUIT_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.2F).build();

    public static final FoodProperties RICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.05F).build();

    public static final FoodProperties ACORN = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 300, 0), 0.3F).build();
    public static final FoodProperties ROASTED_ACORN = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.275F).build();

    public static final FoodProperties RAW_CORN_TORTILLA = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100, 0), 0.5f).build();
    public static final FoodProperties CORN_TORTILLA = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.8F).build();
    public static final FoodProperties CANDIED_LIME_SLICE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.35f).build();
    public static final FoodProperties JERKY = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.1F).fast().build();
    public static final FoodProperties CHOCOLATE_BAR = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.3F)
            .effect(() -> new MobEffectInstance(BAEffects.VIGOR, 200, 0), 1F).build();
    public static final FoodProperties BROWNIE = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.3F)
            .effect(() -> new MobEffectInstance(BAEffects.VIGOR, 300, 0), 1F).build();
    public static final FoodProperties CARAMEL = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.2F)
            .effect(() -> new MobEffectInstance(BAEffects.VIGOR, 200, 0), 0.85F).build();

    public static final FoodProperties CHICKEN_QUESADILLA = (new FoodProperties.Builder())
            .nutrition(10).saturationModifier(0.8F).build();
    public static final FoodProperties JOCKEY_SANDWICH = (new FoodProperties.Builder())
            .nutrition(11).saturationModifier(0.8F).build();

    public static final FoodProperties JEWELED_RICE_BOWL = (new FoodProperties.Builder())
            .nutrition(12).saturationModifier(1.15F).usingConvertsTo(BOWL)
            .effect(() -> new MobEffectInstance(BAEffects.FLAK, 750, 1), 1f)
            .effect(() -> new MobEffectInstance(BAEffects.PROLIFERATE, 450, 0), 1f)
            .effect(() -> new MobEffectInstance(BAEffects.SEEDED, 100, 1), 1f)
            .effect(() -> new MobEffectInstance(BAEffects.DISSONANCE, 300, 0), 1f).build();
    public static final FoodProperties PANETTONE = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.8F)
            .effect(() -> new RandomMobEffectInstance(
                    new RandomMobEffectInstance.EffectEntry(BAEffects.REFLECTION, 200, 0, 1.0F),
                    new RandomMobEffectInstance.EffectEntry(BAEffects.CONCENTRATION, 200, 0, 1.0F),
                    new RandomMobEffectInstance.EffectEntry(BAEffects.RESONANCE, 200, 0, 1.0F),
                    new RandomMobEffectInstance.EffectEntry(BAEffects.DISSONANCE, 200, 0, 1.0F)), 1.0F).build();

    public static final FoodProperties STOLLEN = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.9F)
            .effect(() -> new RandomMobEffectInstance(
                    new RandomMobEffectInstance.EffectEntry(BAEffects.REFLECTION, 300, 0, 1.0F),
                    new RandomMobEffectInstance.EffectEntry(BAEffects.CONCENTRATION, 300, 0, 1.0F),
                    new RandomMobEffectInstance.EffectEntry(BAEffects.RESONANCE, 300, 0, 1.0F),
                    new RandomMobEffectInstance.EffectEntry(BAEffects.DISSONANCE, 300, 0, 1.0F)), 1.0F).build();

    public static final FoodProperties CANDY_APPLE = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 100, 0), 1F).build();
    public static final FoodProperties CARAMEL_APPLE = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.375F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 150, 0), 1F)
            .effect(() -> new MobEffectInstance(BAEffects.VIGOR, 200, 0), 1F).build();

    public static final FoodProperties CAKE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(BAEffects.VIGOR, 200, 0), 1f).fast().build();
    public static final FoodProperties CHERRY_PIE = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(BAEffects.TWIN_STRIKE, 900, 1), 1f).build();
    public static final FoodProperties CHERRY_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(BAEffects.TWIN_STRIKE, 200, 1), 1f).fast().build();
    public static final FoodProperties APPLE_PIE = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 900, 1), 1f).build();
    public static final FoodProperties APPLE_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 200, 1), 1f).fast().build();
    public static final FoodProperties GRAPEFRUIT_PIE = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(BAEffects.REFLECTION, 900, 1), 1f).build();
    public static final FoodProperties GRAPEFRUIT_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(BAEffects.REFLECTION, 200, 1), 1f).fast().build();
    public static final FoodProperties ORANGE_PIE = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(BAEffects.CONCENTRATION, 900, 1), 1f).build();
    public static final FoodProperties ORANGE_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(BAEffects.CONCENTRATION, 200, 1), 1f).fast().build();
    public static final FoodProperties MANGO_PIE = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(BAEffects.INSATIABLE, 900, 1), 1f).build();
    public static final FoodProperties MANGO_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(BAEffects.INSATIABLE, 200, 1), 1f).fast().build();
    public static final FoodProperties LEMON_TART = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.3F)
            .effect(() -> new MobEffectInstance(BAEffects.RESONANCE, 900, 1), 1f).build();
    public static final FoodProperties LEMON_TART_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.3F)
            .effect(() -> new MobEffectInstance(BAEffects.RESONANCE, 200, 0), 1f).fast().build();
    public static final FoodProperties LEMON_CAKE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(BAEffects.RESONANCE, 200, 0), 1f).fast().build();
    public static final FoodProperties LEMON_CAKE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(BAEffects.RESONANCE, 200, 0), 1f).fast().build();
    public static final FoodProperties LIME_CAKE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(BAEffects.DISSONANCE, 200, 0), 1f).fast().build();
    public static final FoodProperties LIME_CAKE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(BAEffects.DISSONANCE, 200, 0), 1f).fast().build();
    public static final FoodProperties DRAGON_FRUIT_PIE = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(BAEffects.FLAK, 900, 1), 1f).build();
    public static final FoodProperties DRAGON_FRUIT_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.35F)
            .effect(() -> new MobEffectInstance(BAEffects.FLAK, 200, 1), 1f).fast().build();
    public static final FoodProperties PUMPKIN_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.35F).fast().build();

    public static final FoodProperties LEMONADE = (new FoodProperties.Builder())
            .nutrition(5).saturationModifier(0.55F)
            .effect(() -> new MobEffectInstance(BAEffects.RESONANCE, 300, 0), 1f).alwaysEdible().build();
    public static final FoodProperties LIMEADE = (new FoodProperties.Builder())
            .nutrition(5).saturationModifier(0.575F)
            .effect(() -> new MobEffectInstance(BAEffects.DISSONANCE, 300, 0), 1f).alwaysEdible().build();
    public static final FoodProperties PINK_LEMONADE = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.8F)
            .effect(() -> new MobEffectInstance(BAEffects.TWIN_STRIKE, 600, 0), 1f)
            .effect(() -> new MobEffectInstance(BAEffects.RESONANCE, 300, 0), 1f).alwaysEdible().build();
    public static final FoodProperties PINK_LIMEADE = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.815F)
            .effect(() -> new MobEffectInstance(BAEffects.PROLIFERATE, 600, 0), 1f)
            .effect(() -> new MobEffectInstance(BAEffects.SEEDED, 100, 2), 1f)
            .effect(() -> new MobEffectInstance(BAEffects.DISSONANCE, 300, 0), 1f).alwaysEdible().build();
    public static final FoodProperties BLUEBERRY_LIMEADE = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.8F)
            .effect(() -> new MobEffectInstance(BAEffects.DISSONANCE, 300, 0), 1f).alwaysEdible().build();
    public static final FoodProperties LIME_GREEN_TEA = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.775F)
            .effect(() -> new MobEffectInstance(BAEffects.DISSONANCE, 300, 0), 1f)
            .effect(() -> new MobEffectInstance(BAEffects.CAFFEINATED, 300, 0 /* temp until the tea effects are implemented */), 0.8f).alwaysEdible().build();

    public static final FoodProperties DRAGON_FRUIT_LATTE = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.625F)
            .effect(() -> new MobEffectInstance(BAEffects.FLAK, 750, 1), 1F).build();
    public static final FoodProperties CHERRY_LIME_RICKEY_REFRESHER = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.7F)
            .effect(() -> new MobEffectInstance(BAEffects.TWIN_STRIKE, 600, 1), 1f)
            .effect(() -> new MobEffectInstance(BAEffects.DISSONANCE, 300, 0), 1f)
            .effect(() -> new MobEffectInstance(BAEffects.CAFFEINATED, 200, 0 /* temp until the tea effects are implemented */), 0.8f).alwaysEdible().build();
}