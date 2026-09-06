package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static net.ashstarcrash.bonappetit.core.registry.BAItems.*;
import static net.minecraft.world.item.Items.*;

public class BACreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BonAppetit.ID);

    public static final Supplier<CreativeModeTab> BA_TAB = CREATIVE_MODE_TAB.register("bonappetit_tab", () -> CreativeModeTab.builder().withSearchBar().withTabsBefore(CreativeModeTabs.FOOD_AND_DRINKS)
            .icon(() -> new ItemStack(/*BAConfig.GENERALIZED_CREATIVE_TAB.get() ? */SMOKER/* : DRAGON_FRUIT.get()*/))
            .title(Component.translatable(/*BAConfig.GENERALIZED_CREATIVE_TAB.get() ? */"tab.cooking"/* : "tab.bonappetit"*/))
            .displayItems((itemDisplayParameters, output) -> {
                //woods
                //tools
                output.accept(PITCHFORK);

                //workstations
                altTabAccept(output, FURNACE);
                altTabAccept(output, SMOKER);
                altTabAccept(output, CAULDRON);
                output.accept(BABlocks.COOKING_POT);
                output.accept(BABlocks.DRYING_RACK);
                output.accept(BABlocks.COPPER_TANK);

                //cabinets
                //serving items
                altTabAccept(output, BOWL);
                output.accept(PAPER_PLATE);
                altTabAccept(output, GLASS_BOTTLE);
                output.accept(GLASS_MUG);
                output.accept(GLASS_COCKTAIL);

                //seeds
                altTabAccept(output, BEETROOT_SEEDS);
                altTabAccept(output, COCOA_BEANS);
                output.accept(CORN_KERNELS);
                if (ModUtil.SUP.isLoaded()) output.accept(ModUtil.SUP.getItem("flax_seeds"));
                altTabAccept(output, MELON_SEEDS);
                altTabAccept(output, PITCHER_POD);
                output.accept(POMEGRANATE_SEEDS);
                altTabAccept(output, PUMPKIN_SEEDS);
                altTabAccept(output, TORCHFLOWER_SEEDS);
                altTabAccept(output, WHEAT_SEEDS);

                //grains
                output.accept(CORN);
                if (ModUtil.SUP.isLoaded()) output.accept(ModUtil.SUP.getItem("flax"));
                output.accept(RICE);
                altTabAccept(output, WHEAT);

                //fruits
                altTabAccept(output, APPLE);
                output.accept(APPLE_SLICE);
                output.accept(GREEN_APPLE);
                altTabAccept(output, GOLDEN_APPLE);
                altTabAccept(output, ENCHANTED_GOLDEN_APPLE);
                output.accept(APRICOT);
                output.accept(BANANA);
                output.accept(CHERRIES);
                output.accept(GOLDEN_CHERRIES);
                altTabAccept(output, CHORUS_FRUIT);
                output.accept(COCONUT);
                output.accept(COCONUT_SLICE);
                output.accept(DRAGON_FRUIT);
                output.accept(DRAGON_FRUIT_SLICE);
                output.accept(GRAPEFRUIT);
                output.accept(GRAPEFRUIT_SLICE);
                output.accept(GRAPES);
                output.accept(KIWI);
                output.accept(LEMON);
                output.accept(LEMON_SLICE);
                output.accept(LIME);
                output.accept(LIME_SLICE);
                output.accept(MANGO);
                altTabAccept(output, MELON);
                altTabAccept(output, MELON_SLICE);
                altTabAccept(output, GLISTERING_MELON_SLICE);
                output.accept(ORANGE);
                output.accept(ORANGE_SLICE);
                if (ModUtil.AT.isLoaded()) output.accept(ModUtil.AT.getItem("blood_orange"));
                if (ModUtil.AT.isLoaded()) output.accept(ModUtil.AT.getItem("passion_fruit"));
                if (ModUtil.AT.isLoaded()) output.accept(ModUtil.AT.getItem("shimmering_passion_fruit"));
                output.accept(PEACH);
                output.accept(PEAR);
                output.accept(PINEAPPLE);
                output.accept(POMEGRANATE);
                output.accept(POMEGRANATE_SLICE);
                if (ModUtil.AT.isLoaded()) output.accept(ModUtil.AT.getItem("yucca_fruit"));

                //berries
                output.accept(BLUEBERRIES);
                output.accept(CRANBERRIES);
                if (ModUtil.AT.isLoaded()) output.accept(ModUtil.AT.getItem("currant"));
                altTabAccept(output, GLOW_BERRIES);
                if (ModUtil.UA.isLoaded()) output.accept(ModUtil.UA.getItem("mulberries"));
                output.accept(RASPBERRIES);
                output.accept(BLACK_RASPBERRIES);
                output.accept(STRAWBERRIES);
                output.accept(GOLDEN_STRAWBERRIES);
                output.accept(WINGED_STRAWBERRY);
                output.accept(WINGED_GOLDEN_STRAWBERRY);
                altTabAccept(output, SWEET_BERRIES);

                //veggies
                altTabAccept(output, BEETROOT);
                altTabAccept(output, CARROT);
                altTabAccept(output, GOLDEN_CARROT);
                output.accept(ONION);
                output.accept(ONION_SLICE);
                altTabAccept(output, PITCHER_PLANT);
                altTabAccept(output, POTATO);
                altTabAccept(output, PUMPKIN);
                output.accept(PUMPKIN_SLICE);

                //forages
                altTabAccept(output, RED_MUSHROOM);
                altTabAccept(output, BROWN_MUSHROOM);
                altTabAccept(output, CRIMSON_FUNGUS);
                altTabAccept(output, WARPED_FUNGUS);

                altTabAccept(output, SUNFLOWER);
                altTabAccept(output, TORCHFLOWER);

                if (ModUtil.AT.isLoaded()) output.accept(ModUtil.AT.getItem("aloe_leaves"));
                output.accept(GREEN_TEA_LEAVES);
                output.accept(YELLOW_TEA_LEAVES);
                output.accept(BLACK_TEA_LEAVES);
                output.accept(COFFEE_CHERRIES);
                output.accept(COFFEE_BEANS);

                //produce
                altTabAccept(output, EGG);
                altTabAccept(output, SNIFFER_EGG);

                altTabAccept(output, BEEF);
                altTabAccept(output, CHICKEN);
                altTabAccept(output, PORKCHOP);
                altTabAccept(output, MUTTON);
                altTabAccept(output, RABBIT);
                altTabAccept(output, COD);
                altTabAccept(output, SALMON);
                altTabAccept(output, TROPICAL_FISH);
                altTabAccept(output, PUFFERFISH);

                altTabAccept(output, COOKED_BEEF);
                altTabAccept(output, COOKED_CHICKEN);
                altTabAccept(output, COOKED_PORKCHOP);
                altTabAccept(output, COOKED_MUTTON);
                altTabAccept(output, COOKED_RABBIT);
                altTabAccept(output, COOKED_COD);
                altTabAccept(output, COOKED_SALMON);

                //spices
                output.accept(CINNAMON_STICKS);
                output.accept(CINNAMON_DUST);

                //misc
                output.accept(ACORN);
                output.accept(ROASTED_ACORN);

                //baking & cooking ingredients
                output.accept(DOUGH);
                output.accept(RAW_CORN_TORTILLA);
                output.accept(PIE_CRUST);

                altTabAccept(output, SUGAR_CANE);
                altTabAccept(output, SUGAR);
                //output.accept(MOLASSES);
                altTabAccept(output, HONEY_BOTTLE);
                if (ModUtil.AUT.isLoaded()) output.accept(ModUtil.AUT.getItem("maple_syrup"));

                //basic meals (<1 fruit)
                output.accept(DOUGH);
                output.accept(PIE_CRUST);
                output.accept(WAFER);
                output.accept(CORN_ON_A_COB);
                output.accept(POPCORN);
                output.accept(RAW_CORN_TORTILLA);
                output.accept(CORN_TORTILLA);
                output.accept(CORNBREAD);
                output.accept(ONION_RINGS);
                output.accept(HONEY_APPLE);
                output.accept(CANDY_APPLE);
                output.accept(CARAMEL_APPLE);
                output.accept(CANDIED_ORANGE_PEELS);
                output.accept(ORANGE_PUDDING);
                output.accept(ORANGE_SORBET);
                output.accept(ORANGE_SHERBET);
                output.accept(DRIED_BANANA);
                output.accept(BANANA_BREAD);
                output.accept(CANDIED_LIME_SLICE);
                output.accept(RAISINS);
                output.accept(DRIED_DRAGON_FRUIT);
                output.accept(JERKY);
                output.accept(CHOCOLATE_BAR);
                output.accept(BROWNIE);
                output.accept(CARAMEL);

                //intermediate meals (multi-fruit)
                output.accept(CHICKEN_QUESADILLA);
                output.accept(JOCKEY_SANDWICH);

                //feasts/good meals
                output.accept(AMBROSIA_SALAD);
                output.accept(JEWELED_RICE_BOWL);
                output.accept(PANETTONE);
                output.accept(PANETTONE_SLICE);
                output.accept(STOLLEN);
                output.accept(STOLLEN_SLICE);

                //sweets
                FlavoredItems.addGummies(output);
                output.accept(PLAIN_COOKIE);
                output.accept(SUGAR_COOKIE);
                FlavoredItems.addCookies(output);
                output.accept(SNICKERDOODLE);
                output.accept(ECLIPSE_COOKIE);
                output.accept(GOLDEN_COOKIE);
                output.accept(MACARON);

                output.accept(ORANGE_JAWBREAKER);

                FlavoredItems.addPopsicles(output);

                output.accept(BLOSSOM_DANGO);
                output.accept(GROVE_DANGO);
                output.accept(TWILIGHT_DANGO);
                output.accept(SUNRISE_DANGO);

                output.accept(SPONGECAKE);
                if (ModUtil.AT.isLoaded()) output.accept(COCHINEAL_SPONGECAKE);

                //pies and cake
                FlavoredItems.addPies(output);
                output.accept(PUMPKIN_PIE_SLICE);
                output.accept(CAKE_SLICE);
                FlavoredItems.addCakes(output);

                //drinks
                output.accept(WATER_MUG);
                generateTisanes(output, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(MILK_BOTTLE);
                output.accept(COCONUT_MILK_BOTTLE);
                output.accept(CHOCOLATE_MILK_BOTTLE);
                output.accept(STRAWBERRY_MILK_BOTTLE);
                output.accept(BLUEBERRY_MILK_BOTTLE);
                output.accept(BANANA_MILK_BOTTLE);
                output.accept(PEACH_MILK_BOTTLE);
                output.accept(CARROT_MILK_BOTTLE);
                output.accept(COFFEE_MILK_BOTTLE);
                output.accept(HORCHATA);
                output.accept(APPLE_JUICE);
                output.accept(APPLE_CIDER);
                output.accept(APPLEJACK);
                output.accept(ORANGE_JUICE);
                output.accept(BANANA_SMOOTHIE);
                output.accept(LEMONADE);
                output.accept(LIMEADE);
                output.accept(DRAGON_FRUIT_LATTE);
                output.accept(COFFEE);
                output.accept(STRAWBERRY_BANANA_SMOOTHIE);
                output.accept(PINK_LEMONADE);
                output.accept(PINK_LIMEADE);
                output.accept(BLUEBERRY_LIMEADE);
                output.accept(LIME_GREEN_TEA);
                output.accept(PINK_LADY);
                output.accept(CHERRY_LIME_REFRESHER);
            }).build());

    private static void altTabAccept(CreativeModeTab.Output output, ItemLike item) {
        if (BAConfig.GENERALIZED_CREATIVE_TAB.get()) output.accept(item);
    }

    private static void generateTisanes(CreativeModeTab.Output output, CreativeModeTab.TabVisibility tabVisibility) {
        List<SuspiciousEffectHolder> list = SuspiciousEffectHolder.getAllEffectHolders();
        Set<ItemStack> set = ItemStackLinkedSet.createTypeAndComponentsSet();

        for(SuspiciousEffectHolder suspiciouseffectholder : list) {
            ItemStack itemstack = new ItemStack(TISANE.get());
            itemstack.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, suspiciouseffectholder.getSuspiciousEffects());
            set.add(itemstack);
        }

        output.acceptAll(set, tabVisibility);
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}