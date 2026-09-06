package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static net.ashstarcrash.bonappetit.core.registry.BAItems.*;

public class BACreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BonAppetit.ID);

    public static final Supplier<CreativeModeTab> BA_TAB = CREATIVE_MODE_TAB.register("bonappetit_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(DRAGON_FRUIT.get()))
            .title(Component.translatable("tab.bonappetit"))
            .displayItems((itemDisplayParameters, output) -> {
                //woods
                //tools
                output.accept(PITCHFORK);

                //workstations
                output.accept(BABlocks.COOKING_POT);
                output.accept(BABlocks.DRYING_RACK);
                output.accept(BABlocks.COPPER_TANK);

                //cabinets
                //serving items
                output.accept(PAPER_PLATE);
                output.accept(GLASS_MUG);
                output.accept(GLASS_COCKTAIL);

                //seeds
                output.accept(POMEGRANATE_SEEDS);
                output.accept(CORN_KERNELS);

                //fruits
                output.accept(CHERRIES);
                output.accept(GOLDEN_CHERRIES);

                output.accept(APPLE_SLICE);
                output.accept(GREEN_APPLE);

                output.accept(GRAPEFRUIT);
                output.accept(GRAPEFRUIT_SLICE);

                output.accept(ORANGE);
                output.accept(ORANGE_SLICE);

                output.accept(MANGO);

                output.accept(APRICOT);

                output.accept(PINEAPPLE);

                output.accept(BANANA);

                output.accept(LEMON);
                output.accept(LEMON_SLICE);

                output.accept(LIME);
                output.accept(LIME_SLICE);

                output.accept(KIWI);

                output.accept(PEAR);

                output.accept(GRAPES);

                output.accept(PEACH);

                output.accept(DRAGON_FRUIT);
                output.accept(DRAGON_FRUIT_SLICE);

                output.accept(POMEGRANATE);
                output.accept(POMEGRANATE_SLICE);

                output.accept(COCONUT);
                output.accept(COCONUT_SLICE);

                //berries
                output.accept(COFFEE_CHERRIES);

                output.accept(CRANBERRIES);

                output.accept(STRAWBERRIES);
                output.accept(GOLDEN_STRAWBERRIES);
                output.accept(WINGED_STRAWBERRY);
                output.accept(WINGED_GOLDEN_STRAWBERRY);

                output.accept(SALMONBERRIES);

                output.accept(BLUEBERRIES);

                output.accept(MULBERRIES);

                output.accept(RASPBERRIES);
                output.accept(BLACK_RASPBERRIES);

                //veggies
                output.accept(CORN);

                output.accept(ONION);
                output.accept(ONION_SLICE);

                output.accept(PUMPKIN_SLICE);

                //grains
                output.accept(RICE);

                //tea and coffee
                output.accept(GREEN_TEA_LEAVES);
                output.accept(YELLOW_TEA_LEAVES);
                output.accept(BLACK_TEA_LEAVES);
                output.accept(COFFEE_BEANS);

                //meats
                //spices
                output.accept(CINNAMON_STICKS);
                output.accept(CINNAMON_DUST);

                //misc
                output.accept(ACORN);
                output.accept(ROASTED_ACORN);

                //basic ingredients/meals (<1 fruit)
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