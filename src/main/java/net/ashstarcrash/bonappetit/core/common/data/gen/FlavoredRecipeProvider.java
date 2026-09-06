package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.ashstarcrash.bonappetit.core.registry.FlavoredItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Map;

public class FlavoredRecipeProvider {
    @FunctionalInterface
    public interface RecipeGenerator {
        void generate(RecipeOutput output, FlavoredItems.Flavor flavor, FlavoredItems.FlavorIngredient ingredient, DeferredItem<Item> resultItem, String resultId);
    }

    private static Criterion<?> hasIngredient(FlavoredItems.TagOrItem ingredient) {
        ItemPredicate predicate = switch (ingredient) {
            case FlavoredItems.TagOrItem.Tag tag -> ItemPredicate.Builder.item().of(tag.tag()).build();
            case FlavoredItems.TagOrItem.Single single -> ItemPredicate.Builder.item().of(single.item().get()).build();
        };
        return InventoryChangeTrigger.TriggerInstance.hasItems(predicate);
    }

    private static final RecipeGenerator GUMMY_RECIPE = (output, flavor, ingredient, resultItem, resultId) ->
            ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, resultItem.get(), 1)
                    .requires(ingredient.full().toIngredient())
                    .requires(Items.KELP)
                    .requires(Items.HONEY_BOTTLE)
                    .requires(Items.SUGAR)
                    .unlockedBy("has_" + resultId, hasIngredient(ingredient.full()))
                    .save(output, ModUtil.BA.asResource(resultId));

    private static final RecipeGenerator COOKIE_RECIPE = (output, flavor, ingredient, resultItem, resultId) ->
            ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, resultItem.get(), 8)
                    .requires(ingredient.full().toIngredient()).requires(Items.WHEAT).requires(Items.WHEAT)
                    .unlockedBy("has_" + resultId, hasIngredient(ingredient.full()))
                    .save(output, ModUtil.BA.asResource(resultId));

    private static final RecipeGenerator POPSICLE_RECIPE = (output, flavor, ingredient, resultItem, resultId) -> {
        FlavoredItems.TagOrItem fruitSlot = ingredient.preferSlice();
        Ingredient fruitIngredient = fruitSlot.toIngredient();

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, resultItem.get(), 2)
                .pattern(" FF")
                .pattern("SFF")
                .pattern("SI ")
                .define('F', fruitIngredient)
                .define('I', Items.ICE)
                .define('S', Items.STICK)
                .unlockedBy("has_" + resultId, hasIngredient(fruitSlot))
                .save(output, ModUtil.BA.asResource(resultId));
    };

    private static final RecipeGenerator PIE_RECIPE = (output, flavor, ingredient, resultItem, resultId) -> {
        FlavoredItems.TagOrItem fruitSlot = ingredient.preferSlice();
        Ingredient fruitIngredient = fruitSlot.toIngredient();

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, resultItem.get(), 2)
                .requires(fruitIngredient).requires(fruitIngredient).requires(fruitIngredient)
                .requires(Items.SUGAR).requires(BAItems.PIE_CRUST).requires(Tags.Items.EGGS)
                .unlockedBy("has_" + resultId, hasIngredient(ingredient.full()))
                .save(output, ModUtil.BA.asResource(resultId));
    };

    private static final RecipeGenerator CAKE_RECIPE = (output, flavor, ingredient, resultItem, resultId) -> {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, resultItem.get(), 1)
                .pattern("MFM")
                .pattern("SES")
                .pattern("WFW")
                .define('F', ingredient.full().toIngredient())
                .define('M', Tags.Items.DRINKS_MILK)
                .define('S', Items.SUGAR)
                .define('W', Items.WHEAT)
                .define('E', Tags.Items.EGGS)
                .unlockedBy("has_" + resultId, hasIngredient(ingredient.full()))
                .save(output, ModUtil.BA.asResource(resultId));
    };

    private static final Map<FlavoredItems.ItemType, RecipeGenerator> GENERATORS = Map.of(
            FlavoredItems.ItemType.GUMMY, GUMMY_RECIPE,
            FlavoredItems.ItemType.COOKIE, COOKIE_RECIPE,
            FlavoredItems.ItemType.POPSICLE, POPSICLE_RECIPE,
            FlavoredItems.ItemType.PIE, PIE_RECIPE,
            FlavoredItems.ItemType.CAKE, CAKE_RECIPE
    );

    private static void generateSliceRecipes(RecipeOutput output, FlavoredItems.Flavor flavor, FlavoredItems.ItemType type) {
        String fullId = flavor.id + type.suffix;
        String sliceId = flavor.id + type.slice.suffix();

        DeferredItem<Item> fullItem = FlavoredItems.ITEMS_BY_ID.get(fullId);
        DeferredItem<Item> sliceItem = FlavoredItems.ITEMS_BY_ID.get(sliceId);

        if (fullItem == null || sliceItem == null) return;
        int sliceCount = (type == FlavoredItems.ItemType.CAKE ? 7 : 4);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, sliceItem.get(), sliceCount)
                .requires(fullItem.get())
                .unlockedBy("has_" + fullId, InventoryChangeTrigger.TriggerInstance.hasItems(fullItem.get()))
                .save(output, ModUtil.BA.asResource(sliceId));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, fullItem.get(), 1)
                .requires(sliceItem.get(), sliceCount)
                .unlockedBy("has_" + sliceId, InventoryChangeTrigger.TriggerInstance.hasItems(sliceItem.get()))
                .save(output, ModUtil.BA.asResource(fullId + "_from_slices"));
    }

    public static void buildRecipes(RecipeOutput output) {
        for (FlavoredItems.Flavor flavor : FlavoredItems.Flavor.values()) {
            for (Map.Entry<FlavoredItems.ItemType, FlavoredItems.Variant> entry : flavor.variants.entrySet()) {
                FlavoredItems.ItemType type = entry.getKey();
                FlavoredItems.Variant variant = entry.getValue();

                if (!variant.isAvailable()) continue;

                RecipeGenerator generator = GENERATORS.get(type);
                if (generator != null) {
                    String resultId = flavor.id + type.suffix;
                    DeferredItem<Item> resultItem = FlavoredItems.ITEMS_BY_ID.get(resultId);
                    if (resultItem != null) {
                        generator.generate(output, flavor, flavor.ingredient, resultItem, resultId);
                    }
                }

                if (type.hasSlice()) generateSliceRecipes(output, flavor, type);
            }
        }
    }
}