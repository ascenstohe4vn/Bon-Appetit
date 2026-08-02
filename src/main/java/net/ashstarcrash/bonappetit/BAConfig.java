package net.ashstarcrash.bonappetit;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BAConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // --- HUNGER CONFIG ---
    public static final ModConfigSpec.BooleanValue HUNGER_BAR_ENABLED;
    public static final ModConfigSpec.DoubleValue FOOD_HEAL_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue FOOD_REGEN_DURATION_PER_SATURATION;
    public static final ModConfigSpec.IntValue FOOD_REGEN_MIN_DURATION_TICKS;
    public static final ModConfigSpec.IntValue FOOD_REGEN_MAX_DURATION_TICKS;
    public static final ModConfigSpec.IntValue FOOD_REGEN_PULSE_INTERVAL_TICKS;
    public static final ModConfigSpec.DoubleValue FOOD_REGEN_PULSE_HEAL_AMOUNT;

    // --- REGISTRY CONFIG ---
    //public static final ModConfigSpec.BooleanValue REGISTER_GRAPEFRUIT;
    //public static final ModConfigSpec.BooleanValue REGISTER_COFFEE;

    // --- GAMEPLAY CONFIG ---
    public static final ModConfigSpec.DoubleValue CHERRY_EFFECT_INITIAL_MULTI;
    public static final ModConfigSpec.DoubleValue CHERRY_EFFECT_ADDITIVE_MULTI;

    // --- TWEAKS CONFIG ---
    public static final ModConfigSpec.BooleanValue VANILLA_CAKE_EFFECT;
    public static final ModConfigSpec.BooleanValue CAKE_REPAIRING;
    public static final ModConfigSpec.BooleanValue CAKE_FALL_CUSHIONING;

    // --- TOOLTIP CONFIG ---
    public static final ModConfigSpec.BooleanValue SHOW_SATURATION_OVERLAY;
    public static final ModConfigSpec.BooleanValue EFFECT_TOOLTIPS;
    public static final ModConfigSpec.BooleanValue NEGATIVE_EFFECT_TOOLTIPS;
    public static final ModConfigSpec.EnumValue<ChanceDisplayMode> CHANCE_DISPLAY;

    static {
        // --- Hunger ---
        BUILDER.comment("Controls for disabling vanilla hunger and replacing it with instant-heal-on-eat").push("hunger");
        HUNGER_BAR_ENABLED = BUILDER
                .comment("If true, vanilla hunger/saturation/exhaustion behaves normally. If false, hunger never depletes, natural passive regen is disabled, and eating instead grants an instant heal + temporary custom regeneration.")
                .define("hungerBarEnabled", false);
        FOOD_HEAL_MULTIPLIER = BUILDER
                .comment("Health healed per point of a food's nutrition value. Ex. steak (nutrition 8) at 1.0 heals 8 health; or 4 hearts.")
                .defineInRange("foodHealMultiplier", 1.0, 0.0, Double.MAX_VALUE);
        FOOD_REGEN_DURATION_PER_SATURATION = BUILDER
                .comment("Ticks of custom regen duration granted per point of a food's saturation value.")
                .defineInRange("foodRegenDurationPerSaturation", 50, 0.0, Double.MAX_VALUE);
        FOOD_REGEN_MIN_DURATION_TICKS = BUILDER
                .comment("Minimum regen duration in ticks required to actually apply the effect. Foods with low saturation (e.g. cookies) fall under this and grant no regen at all.")
                .defineInRange("foodRegenMinDurationTicks", 55, 0, Integer.MAX_VALUE);
        FOOD_REGEN_MAX_DURATION_TICKS = BUILDER
                .comment("Maximum total ticks of custom regen duration that can be stacked at once, regardless of how much food is eaten.")
                .defineInRange("foodRegenMaxDurationTicks", 1200, 0, Integer.MAX_VALUE);
        FOOD_REGEN_PULSE_INTERVAL_TICKS = BUILDER
                .comment("Ticks between each heal pulse. Vanilla's Regeneration I is a 50-tick pulse for reference")
                .defineInRange("foodRegenPulseIntervalTicks", 60, 1, Integer.MAX_VALUE);
        FOOD_REGEN_PULSE_HEAL_AMOUNT = BUILDER
                .comment("Health healed per pulse.")
                .defineInRange("foodRegenPulseHealAmount", 1.0, 0.0, Double.MAX_VALUE);
        BUILDER.pop(); // hunger end

        // --- Registry ---
        BUILDER.comment("Toggle which content modules are loaded into the game").push("registry");

        //REGISTER_GRAPEFRUIT = BUILDER.gameRestart()
        //        .comment("Registers all Grapefruit related items and blocks.")
        //        .define("registerGrapefruit", true);

        //REGISTER_COFFEE = BUILDER.gameRestart()
        //        .comment("Registers all Coffee related items and blocks.")
        //        .define("registerCoffee", true);

        BUILDER.pop(); // registry end

        // --- Gameplay ---
        BUILDER.comment("Configuration for the gameplay aspects of the mod, such as effects").push("gameplay");

            BUILDER.comment("Cherry and Twin Strike effect configurations").push("cherry");

            CHERRY_EFFECT_INITIAL_MULTI = BUILDER
                    .comment("The initial multiplier of Twin Strike's second strike, without any amplifiers")
                    .defineInRange("cherryEffectInitialMulti", 0.35, 0.0, Double.MAX_VALUE);

            CHERRY_EFFECT_ADDITIVE_MULTI = BUILDER
                    .comment("The additive multiplier of Twin Strike's second strike, which is used for higher effect levels")
                    .defineInRange("cherryEffectAdditiveMulti", 0.15, 0.0, Double.MAX_VALUE);

            BUILDER.pop(); // cherry end

        BUILDER.pop(); // gameplay end

        // --- Tweaks ---
        BUILDER.comment("Miscellaneous tweaks (Quality of Life)").push("tweaks");

            BUILDER.comment("Cake tweaks").push("cakes");

            VANILLA_CAKE_EFFECT = BUILDER.gameRestart().define("vanillaCakeEffect", true);
            CAKE_REPAIRING = BUILDER.define("cakeRepairing", true);
            CAKE_FALL_CUSHIONING = BUILDER.define("cakeFallCushioning", true);

            BUILDER.pop(); // cake end

        BUILDER.pop(); // tweaks end

        // --- Tooltips ---
        BUILDER.comment("Tooltip settings for how food tooltips are shown").push("tooltips");

        SHOW_SATURATION_OVERLAY = BUILDER
                .comment("If true, overlays a saturation icon on hunger drumsticks in tooltips. Requires a saturation_overlay texture to be present; disable if none is provided by your resource pack.")
                .define("showSaturationOverlay", false);
        EFFECT_TOOLTIPS = BUILDER.define("effectTooltips", true);
        NEGATIVE_EFFECT_TOOLTIPS = BUILDER.define("negativeEffectTooltips", false);
        CHANCE_DISPLAY = BUILDER.defineEnum("chanceDisplayMode", ChanceDisplayMode.DYNAMIC);

        BUILDER.pop(); // tooltip end
    }

    public enum ChanceDisplayMode {
        FULL, HIDDEN, DYNAMIC, NONE
    }

    public static final ModConfigSpec SPEC = BUILDER.build();
}