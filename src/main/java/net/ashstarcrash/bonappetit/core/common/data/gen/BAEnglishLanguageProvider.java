package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.ashstarcrash.bonappetit.core.registry.BABlocks;
import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.ashstarcrash.bonappetit.core.registry.BAEntities;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class BAEnglishLanguageProvider extends LanguageProvider {
    String BA = BonAppetit.ID;
    String C = ModUtil.COMMON.id();

    private static final Map<String, String> ITEM_OVERRIDES = Map.ofEntries(
            Map.entry("corn_on_a_cob", "Corn on a Cob")
    );
    private static final Map<String, String> BLOCK_OVERRIDES = Map.ofEntries(

    );

    public BAEnglishLanguageProvider(PackOutput output) {
        super(output, BonAppetit.ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("tab." + BA, "Bon Appétit");

        //emi info
        add("emi.info.macarons",
                "Macarons are able to be dyed like leather armor!");

        //effect desc
        add("effect." + BA + ".twin_strike.description",
                "Makes all attacks hit twice, with the second hit doing less damage; higher levels increase the second hit's damage.");
        add("effect." + BA + ".reflection.description",
                "Reflects any projectiles that make contact with the user; higher levels increase the chance.");
        add("effect." + BA + ".concentration.description",
                "Removes gravity from any projectile shot and amplifies the damage; higher levels increase the projectile velocity and damage.");
        add("effect." + BA + ".agility.description",
                "Temporary description!");
        add("effect." + BA + ".resonance.description",
                "Spawns a potion cloud with all your positive effects; higher levels increase the cloud duration and size.");
        add("effect." + BA + ".dissonance.description",
                "Spawns a potion cloud with all your negative effects; higher levels increase the cloud duration and size.");
        add("effect." + BA + ".rooted.description",
                "Increases knockback resistance and armor for the user.");
        add("effect." + BA + ".caffeinated.description",
                "Increases attack speed and mining speed, and reduces the movement speed reduction from blocks like Soul Sand.");

        //advancements
        add("advancements.husbandry.wake_and_bake.title", "Wake And Bake");
        add("advancements.husbandry.wake_and_bake.description",
                "Bake a cookie");
        add("advancements.husbandry.lucky_day.title", "Lucky Day");
        add("advancements.husbandry.lucky_day.description",
                "Consume a golden cookie");
        add("advancements.husbandry.black_cats_paw.title", "Black Cat's Paw");
        add("advancements.husbandry.black_cats_paw.description",
                "Obtain 777 golden cookies and question your sanity");
        add("advancements.husbandry.not_a_lie.title", "Not a Lie");
        add("advancements.husbandry.not_a_lie.description",
                "You will bake every flavor of cake there is... and then there will be cake.");
        add("advancements.story.not_tomorrow_thank_you.title", "Not Tomorrow Either, Thank You");
        add("advancements.story.not_tomorrow_thank_you.description",
                "Reflect a projectile using the power of grapefruits");
        add("advancements.adventure.shadow_strike.title", "Shadow Strike");
        add("advancements.adventure.shadow_strike.description",
                "Using the power of pears, perform a stealth strike on a mob");
        add("advancements.adventure.slip_under_the_door.title", "Slip Under The Door");
        add("advancements.adventure.slip_under_the_door.description",
                "Stay hidden in a group of at least five mobs using the power of pears");

        //tags
        add("tag.item." + BA + ".serving_containers", "Serving Containers");
        add("tag.item." + C  + ".citrus_foods", "Citrus Foods");

        //configs
        add(BA + ".configuration.title", "Bon Appétit Config");
        add(BA + ".configuration.section.bonappetit.common.toml", "Bon Appétit Config");
        add(BA + ".configuration.section.bonappetit.common.toml.title", "Bon Appétit Config");

        add(BA + ".configuration.hunger", "Hunger");
                add(BA + ".configuration.hungerBarEnabled", "Enable Vanilla Hunger Bar");
                add(BA + ".configuration.foodHealMultiplier", "Instant Heal per Nutrition");
                add(BA + ".configuration.foodRegenDurationPerSaturation", "Regen Duration per Saturation");
                add(BA + ".configuration.foodRegenMinDurationTicks", "Minimum Regen Duration Threshold");
                add(BA + ".configuration.foodRegenPulseIntervalTicks", "Regen Pulse Interval");
                add(BA + ".configuration.foodRegenPulseHealAmount", "Regen Pulse Heal Amount");
                add(BA + ".configuration.foodRegenMaxDurationTicks", "Maximum Regen Duration Cap");

        add(BA + ".configuration.registry", "Registry");
                add(BA + ".configuration.registerGrapefruit", "Grapefruit");
                add(BA + ".configuration.registerCoffee", "Coffee");

        add(BA + ".configuration.gameplay", "Gameplay");
                add(BA + ".configuration.cherry", "Cherry");
                        add(BA + ".configuration.cherryEffectInitialMulti", "Twin Strike Initial Damage Multiplier");
                        add(BA + ".configuration.cherryEffectAdditiveMulti", "Twin Strike Additive Damage Multiplier");
                add(BA + ".configuration.pomegranate", "Pomegranate");
                        add(BA + ".configuration.seededOverlay", "Seeded Health Overlay");
                        add(BA + ".configuration.seededMaxStacks", "Maximum Seeded Stacks");


        add(BA + ".configuration.tweaks", "Tweaks");
                add(BA + ".configuration.smartContainerReturn", "Smart Container Return");
                add(BA + ".configuration.foodMovementMultiplier", "Eating Movement Multiplier");
                add(BA + ".configuration.drinkMovementMultiplier", "Drinking Movement Multiplier");
                add(BA + ".configuration.cakes", "Cakes");
                        add(BA + ".configuration.vanillaCakeEffect", "Vanilla Cake Effect");
                        add(BA + ".configuration.cakeRepairing", "Cake Repairing");
                        add(BA + ".configuration.cakeFallCushioning", "Cake Fall Cushioning");

        add(BA + ".configuration.tooltips", "Tooltips");
                add(BA + ".configuration.foodStatistics", "Food Statistics Tooltips");
                        add(BA + ".configuration.foodStatisticsTooltipDisplay", "Food Statistics Tooltip Display");
                        add(BA + ".configuration.showSaturationOverlay", "Show Saturation Overlay");
                add(BA + ".configuration.effects", "Effect Tooltips");
                        add(BA + ".configuration.effectTooltipsDisplay", "Food Effect Display Mode");
                        add(BA + ".configuration.negativeEffectTooltips", "Enable Negative Food Effect Tooltips");
                        add(BA + ".configuration.chanceDisplayMode", "Effect Chance Display Mode");

        BAItems.ITEMS.getEntries().forEach(item -> {
            if (item.getId() == null) return;

            String path = item.getId().getPath();
            String name = ITEM_OVERRIDES.getOrDefault(path, toTitleCase(path));
            add("item." + BA + "." + path, name);
        });

        BABlocks.BLOCKS.getEntries().forEach(block -> {
            if (block.getId() == null) return;

            String path = block.getId().getPath();
            String name = BLOCK_OVERRIDES.getOrDefault(path, toTitleCase(path));
            add("block." + BA + "." + path, toTitleCase(path));
        });

        BAEntities.ENTITIES.getEntries().forEach(entity -> {
            if (entity.getId() == null) return;

            String path = entity.getId().getPath();
            add("entity." + BA + "." + path, toTitleCase(path));
        });

        BAEffects.EFFECTS.getEntries().forEach(effect -> {
            if (effect.getId() == null) return;

            String path = effect.getId().getPath();
            add("effect." + BA + "." + path, toTitleCase(path));
        });
    }

    private static String toTitleCase(String id) {
        return Arrays.stream(id.split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining(" "));
    }
}
