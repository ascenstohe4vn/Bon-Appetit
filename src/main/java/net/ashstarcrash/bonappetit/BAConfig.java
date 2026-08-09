package net.ashstarcrash.bonappetit;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class BAConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // --- HUNGER CONFIG ---
    public static final ModConfigSpec.BooleanValue VANILLA_HUNGER_BAR;
    public static final ModConfigSpec.DoubleValue HEAL_PER_NUTRITION;
    public static final ModConfigSpec.DoubleValue REGEN_DURATION_PER_SATURATION;
    public static final ModConfigSpec.IntValue MINIMUM_REGEN_DURATION_THRESHOLD;
    public static final ModConfigSpec.IntValue MAXIMUM_REGEN_DURATION_CAP;
    public static final ModConfigSpec.IntValue REGEN_PULSE_INTERVAL;
    public static final ModConfigSpec.DoubleValue REGEN_PULSE_HEAL_AMOUNT;

    // --- REGISTRY CONFIG ---
    //public static final ModConfigSpec.BooleanValue REGISTER_GRAPEFRUIT;
    //public static final ModConfigSpec.BooleanValue REGISTER_COFFEE;

    // --- GAMEPLAY CONFIG ---
        public static final ModConfigSpec.DoubleValue TWIN_STRIKE_INITIAL_DAMAGE_MULTIPLIER;
        public static final ModConfigSpec.DoubleValue TWIN_STRIKE_ADDITIVE_DAMAGE_MULTIPLIER;
        public static final ModConfigSpec.BooleanValue TWIN_STRIKE_MOB_SPAWNING;
        public static final ModConfigSpec.ConfigValue<List<? extends String>> SPAWNABLE_TWIN_STRIKE_MOBS;

        public static final ModConfigSpec.BooleanValue FLAK_MOB_SPAWNING;
        public static final ModConfigSpec.ConfigValue<List<? extends String>> SPAWNABLE_FLAK_MOBS;

        public static final ModConfigSpec.BooleanValue SEEDED_HEALTH_OVERLAY;
        public static final ModConfigSpec.IntValue MAXIMUM_SEEDED_STACKS;
        public static final ModConfigSpec.BooleanValue PROLIFERATE_MOB_SPAWNING;
        public static final ModConfigSpec.ConfigValue<List<? extends String>> SPAWNABLE_PROLIFERATE_MOBS;

        public static final ModConfigSpec.IntValue ONION_TEAR_RADIUS;

    // --- TWEAKS CONFIG ---
    public static final ModConfigSpec.BooleanValue SMART_CONTAINER_RETURN;
    public static final ModConfigSpec.DoubleValue EATING_MOVEMENT_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue DRINKING_MOVEMENT_MULTIPLIER;
        public static final ModConfigSpec.BooleanValue VANILLA_CAKE_EFFECT;
        public static final ModConfigSpec.BooleanValue CAKE_REPAIRING;
        public static final ModConfigSpec.BooleanValue CAKE_FALL_CUSHIONING;

    // --- TOOLTIP CONFIG ---
        public static final ModConfigSpec.BooleanValue SHOW_SATURATION_OVERLAY;
        public static final ModConfigSpec.BooleanValue NEGATIVE_EFFECT_TOOLTIPS;
        public static final ModConfigSpec.EnumValue<FoodStatisticsTooltipDisplay> FOOD_STATISTICS_TOOLTIP_DISPLAY;
        public static final ModConfigSpec.EnumValue<EffectTooltipDisplay> EFFECT_TOOLTIP_DISPLAY;
        public static final ModConfigSpec.EnumValue<ChanceDisplayMode> EFFECT_CHANCE_DISPLAY;

    static {
        // --- Hunger ---
        BUILDER.comment("Controls for disabling vanilla hunger and replacing it with instant-heal-on-eat").push("hunger");
        VANILLA_HUNGER_BAR = BUILDER
                .comment("If true, vanilla hunger/saturation/exhaustion behaves normally. If false, hunger never depletes, natural passive regen is disabled, and eating instead grants an instant heal + temporary custom regeneration.")
                .define("vanilla_hunger_bar", false);
        HEAL_PER_NUTRITION = BUILDER
                .comment("Health healed per point of a food's nutrition value. Ex. steak (nutrition 8) at 1.0 heals 8 health; or 4 hearts.")
                .defineInRange("heal_per_nutrition", 1.0, 0.0, Double.MAX_VALUE);
        REGEN_DURATION_PER_SATURATION = BUILDER
                .comment("Ticks of custom regen duration granted per point of a food's saturation value.")
                .defineInRange("regen_duration_per_saturation", 50, 0.0, Double.MAX_VALUE);
        MINIMUM_REGEN_DURATION_THRESHOLD = BUILDER
                .comment("Minimum regen duration in ticks required to actually apply the effect. Foods with low saturation (e.g. cookies) fall under this and grant no regen at all.")
                .defineInRange("minimum_regen_duration_threshold", 55, 0, Integer.MAX_VALUE);
        MAXIMUM_REGEN_DURATION_CAP = BUILDER
                .comment("Maximum total ticks of custom regen duration that can be stacked at once, regardless of how much food is eaten.")
                .defineInRange("maximum_regen_duration_cap", 1200, 0, Integer.MAX_VALUE);
        REGEN_PULSE_INTERVAL = BUILDER
                .comment("Ticks between each heal pulse. Vanilla's Regeneration I is a 50-tick pulse for reference")
                .defineInRange("regen_pulse_interval", 60, 1, Integer.MAX_VALUE);
        REGEN_PULSE_HEAL_AMOUNT = BUILDER
                .comment("Health healed per heal pulse")
                .defineInRange("regen_pulse_heal_amount", 1.0, 0.0, Double.MAX_VALUE);
        BUILDER.pop(); // hunger end

        // --- Registry ---
        BUILDER.comment("Toggle which content is loaded into the game").push("registry");

        BUILDER.pop(); // registry end

        // --- Gameplay ---
        BUILDER.comment("Configuration for the gameplay aspects of the mod, such as effects").push("gameplay");

            BUILDER.comment("Cherry and Twin Strike effect configurations").push("cherry");

            TWIN_STRIKE_INITIAL_DAMAGE_MULTIPLIER = BUILDER
                    .comment("The initial multiplier of Twin Strike's second strike, without any amplifiers")
                    .defineInRange("twin_strike_initial_damage_multiplier", 0.35, 0.0, Double.MAX_VALUE);
            TWIN_STRIKE_ADDITIVE_DAMAGE_MULTIPLIER = BUILDER
                    .comment("The additive multiplier of Twin Strike's second strike, which is used for higher effect levels")
                    .defineInRange("twin_strike_additive_damage_multiplier", 0.15, 0.0, Double.MAX_VALUE);
            TWIN_STRIKE_MOB_SPAWNING = BUILDER
                    .comment("Allows mobs to spawn with the Twin Strike effect on hard difficulty")
                    .define("twin_strike_mob_spawning", true);
            SPAWNABLE_TWIN_STRIKE_MOBS = BUILDER
                    .comment("List of entities that can spawn with the Twin Strike effect on hard difficulty if 'Allow Twin Strike Mob Spawning' is true")
                    .defineListAllowEmpty("spawnable_twin_strike_mobs", List.of("minecraft:spider"), () -> "", BAConfig::validateEntityName);

            BUILDER.pop(); // cherry end
            /* ------------------------------------------------------------------------------------------------ */
            BUILDER.comment("Dragon Fruit and Flak effect configurations").push("dragon_fruit");
            
            FLAK_MOB_SPAWNING = BUILDER
                    .comment("Allows mobs to spawn with the Flak effect on hard difficulty")
                    .define("flak_mob_spawning", true);
            SPAWNABLE_FLAK_MOBS = BUILDER
                    .comment("List of entities that can spawn with the Flak effect on hard difficulty if 'Allow Flak Mob Spawning' is true")
                    .defineListAllowEmpty("spawnable_flak_mobs", List.of("minecraft:enderman"), () -> "", BAConfig::validateEntityName);
    
            BUILDER.pop(); // dragon_fruit end
            /* ------------------------------------------------------------------------------------------------ */
            BUILDER.comment("Pomegranate, Proliferate effect and Seeded effect configurations").push("pomegranate");

            PROLIFERATE_MOB_SPAWNING = BUILDER
                    .comment("Allows mobs to spawn with the Proliferate effect on hard difficulty")
                    .define("proliferate_mob_spawning", true);
            SPAWNABLE_PROLIFERATE_MOBS = BUILDER
                    .comment("List of entities that can spawn with the Proliferate effect on hard difficulty")
                    .defineListAllowEmpty("spawnable_proliferate_mobs", List.of("minecraft:piglin", "minecraft:zombified_piglin"), () -> "", BAConfig::validateEntityName);
            SEEDED_HEALTH_OVERLAY = BUILDER
                    .comment("Enables or disables the health overlay displayed when you have any Seeded level")
                    .define("seeded_health_overlay", true);
            MAXIMUM_SEEDED_STACKS = BUILDER
                    .comment("Number of Proliferate hits required to burst Seeded. A value of 4 means the effect bursts on the 4th hit, not taking into account external modifiers like Twin Strike")
                    .defineInRange("maximum_seeded_stacks", 4, 1, 20);

            BUILDER.pop(); // pomegranate end
            /* ------------------------------------------------------------------------------------------------ */
            BUILDER.comment("Onion and onion tear configurations").push("onion");

            ONION_TEAR_RADIUS = BUILDER
                    .comment("The radius (in blocks) that cutting onions affects. Higher values may have a more noticeable performance impact on lower-end servers. Set to 0 to disable this mechanic")
                    .defineInRange("onion_tear_radius", 2, 0, 8);

            BUILDER.pop(); // onion end

        BUILDER.pop(); // gameplay end

        // --- Tweaks ---
        BUILDER.comment("Miscellaneous tweaks (Quality of Life)").push("tweaks");

        SMART_CONTAINER_RETURN = BUILDER
                .comment("If true, when the last food/drink item in a stack returns a container (bowl, bottle, mug, etc.) after use, " +
                        "it tries to stack with existing containers in the inventory instead of replacing the item in your hand. " +
                        "Disable if problems occur with items going to the inventory when they shouldn't")
                .define("smart_container_return", true);
        EATING_MOVEMENT_MULTIPLIER = BUILDER
                .comment("Movement speed multiplier when eating foods (edible items NOT tagged #c:drinks). Vanilla's default for all item use is 0.2. Set to 1.0 for no slowdown at all while eating.")
                .defineInRange("eating_movement_multiplier", 0.2, 0.0, 1.0);
        DRINKING_MOVEMENT_MULTIPLIER = BUILDER
                .comment("Movement speed multiplier when drinking drinks (edible items tagged #c:drinks; this does NOT affect potions or ominous bottles). Vanilla's default for all item use is 0.2. Set to 1.0 for no slowdown at all while drinking.")
                .defineInRange("drinking_movement_multiplier", 0.5, 0.0, 1.0);

            BUILDER.comment("Cake tweaks").push("cakes");

            VANILLA_CAKE_EFFECT = BUILDER.gameRestart()
                    .comment("Makes vanilla-flavored cakes and vanilla-flavored cake slices grant the Vigor effect when eaten")
                    .define("vanilla_cake_effect", true);
            CAKE_REPAIRING = BUILDER
                    .comment("Allows cakes to be repaired by right-clicking a partially eaten one with their specific flavor of cake slice")
                    .define("cake_repairing", true);
            CAKE_FALL_CUSHIONING = BUILDER
                    .comment("Allows cakes to cushion 80% of fall damage (akin to hay bales) at the expense of sometimes destroying the cake")
                    .define("cake_fall_cushioning", true);

            BUILDER.pop(); // cake end

        BUILDER.pop(); // tweaks end

        // --- Tooltips ---
        BUILDER.comment("Tooltip settings for how food tooltips are shown").push("tooltips");

            BUILDER.comment("Food statistics tooltip settings (heal/regen/hunger icons)").push("food_statistics_tooltips");

            FOOD_STATISTICS_TOOLTIP_DISPLAY = BUILDER
                    .comment(
                            "Controls when food/heal/regen icons show on food tooltips.",
                            "",
                            "FULL: Always show them.",
                            "DISCOVERY: Only show them for foods you've eaten in a specific world.",
                            "NONE: Show nothing."
                    ).defineEnum("food_statistics_tooltip_display", FoodStatisticsTooltipDisplay.FULL);
            SHOW_SATURATION_OVERLAY = BUILDER
                    .comment("DEV: If true, overlays a saturation icon on hunger drumsticks in tooltips. Requires a saturation_overlay texture to be present; disable if none is provided by your resource pack.")
                    .define("show_saturation_overlay", false);

            BUILDER.pop(); // food_statistics end
            /* ------------------------------------------------------------------------------------------------ */
            BUILDER.comment("Food effect tooltip settings").push("effect_tooltips");

            EFFECT_TOOLTIP_DISPLAY = BUILDER
                    .comment(
                            "Controls when food effect lines show on tooltips.",
                            "",
                            "FULL: Always show them.",
                            "DISCOVERY: Only show them for foods you've eaten in a specific world.",
                            "NONE: Show nothing."
                    ).defineEnum("effect_tooltip_display", EffectTooltipDisplay.FULL);
            NEGATIVE_EFFECT_TOOLTIPS = BUILDER
                    .define("negative_effect_tooltips", false);
            EFFECT_CHANCE_DISPLAY = BUILDER
                    .comment(
                            "Controls how effect probability is shown on food tooltips.",
                            "",
                            "FULL: Always show the exact percentage.",
                            "HIDDEN: Always show a '?' instead of a percentage.",
                            "DYNAMIC: Show the percentage for beneficial effects, '?' for harmful ones.",
                            "NONE: Show nothing."
                    ).defineEnum("effect_chance_display", ChanceDisplayMode.DYNAMIC);

            BUILDER.pop(); // effects end

        BUILDER.pop(); // tooltip end
    }

    public enum FoodStatisticsTooltipDisplay {
        FULL, DISCOVERY, NONE
    }
    public enum EffectTooltipDisplay {
        FULL, DISCOVERY, NONE
    }
    public enum ChanceDisplayMode {
        FULL, HIDDEN, DYNAMIC, NONE
    }

    private static ResourceLocation toEntityId(String input) {
        if (input == null) return null;
        input = input.trim();
        if (input.isEmpty()) return null;
        if (input.startsWith("#")) return null;
        if (input.endsWith(":*")) return null;
        if (!input.contains(":")) {
            input = "minecraft:" + input;
        }
        ResourceLocation rl = ResourceLocation.tryParse(input);
        if (rl == null) return null;
        return BuiltInRegistries.ENTITY_TYPE.containsKey(rl) ? rl : null;
    }
    public static boolean isValidEntity(EntityType<?> type, ModConfigSpec.BooleanValue enabledConfig, ModConfigSpec.ConfigValue<List<? extends String>> listConfig) {
        if (!enabledConfig.get()) return false;

        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
        if (id == null) return false;

        return listConfig.get().stream().map(BAConfig::toEntityId).anyMatch(id::equals);
    }
    private static boolean validateEntityName(final Object obj) {
        if (!(obj instanceof String s)) return false;
        if (s.isBlank()) return true;

        ResourceLocation rl = toEntityId(s);
        return rl != null;
    }

    public static final ModConfigSpec SPEC = BUILDER.build();
}