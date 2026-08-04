package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

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
                output.accept(CORN_KERNELS);
                output.accept(POMEGRANATE_SEEDS);

                //veggies
                output.accept(PUMPKIN_SLICE);

                output.accept(CORN);

                //fruits
                output.accept(CHERRIES);
                output.accept(GOLDEN_CHERRIES);

                output.accept(COFFEE_CHERRIES);

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
                output.accept(PLAIN_COOKIE);
                output.accept(LEMON_COOKIE);
                output.accept(LIME_COOKIE);
                output.accept(SNICKERDOODLE);
                output.accept(ECLIPSE_COOKIE);
                output.accept(GOLDEN_COOKIE);
                output.accept(MACARON);

                output.accept(LIME_POPSICLE);
                output.accept(DOUBLE_LIME_POPSICLE);

                //pies and cake
                output.accept(CAKE_SLICE);
                output.accept(CHERRY_PIE);
                output.accept(CHERRY_PIE_SLICE);
                output.accept(APPLE_PIE);
                output.accept(APPLE_PIE_SLICE);
                output.accept(APPLE_CAKE_SLICE);
                output.accept(GRAPEFRUIT_PIE);
                output.accept(GRAPEFRUIT_PIE_SLICE);
                output.accept(ORANGE_PIE);
                output.accept(ORANGE_PIE_SLICE);
                output.accept(ORANGE_CAKE_SLICE);
                output.accept(MANGO_PIE);
                output.accept(MANGO_PIE_SLICE);
                output.accept(BANANA_CAKE_SLICE);
                output.accept(LEMON_TART);
                output.accept(LEMON_TART_SLICE);
                output.accept(LEMON_CAKE);
                output.accept(LEMON_CAKE_SLICE);
                output.accept(LIME_CAKE);
                output.accept(LIME_CAKE_SLICE);
                output.accept(DRAGON_FRUIT_PIE);
                output.accept(DRAGON_FRUIT_PIE_SLICE);
                output.accept(PUMPKIN_PIE_SLICE);

                //drinks
                output.accept(WATER_MUG);
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
                output.accept(CHERRY_LIME_RICKEY_REFRESHER);
            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}