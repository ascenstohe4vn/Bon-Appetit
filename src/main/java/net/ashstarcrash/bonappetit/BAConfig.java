package net.ashstarcrash.bonappetit;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BAConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

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
    public static final ModConfigSpec.BooleanValue EFFECT_TOOLTIPS;
    public static final ModConfigSpec.BooleanValue NEGATIVE_EFFECT_TOOLTIPS;
    public static final ModConfigSpec.EnumValue<ChanceDisplayMode> CHANCE_DISPLAY;

    static {
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