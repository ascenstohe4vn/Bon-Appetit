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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;

import java.util.Map;

public class FlavoredRecipeProvider {
    @FunctionalInterface
    public interface RecipeGenerator {
        void generate(RecipeOutput output, FlavoredItems.Flavor flavor, FlavoredItems.FlavorIngredient ingredient, FlavoredItems.RegisteredFood resultFood, String resultId);
    }

    private static Criterion<?> hasIngredient(FlavoredItems.TagOrItem ingredient) {
        ItemPredicate predicate = switch (ingredient) {
            case FlavoredItems.TagOrItem.Tag tag -> ItemPredicate.Builder.item().of(tag.tag()).build();
            case FlavoredItems.TagOrItem.Single single -> ItemPredicate.Builder.item().of(single.item().get()).build();
        };
        return InventoryChangeTrigger.TriggerInstance.hasItems(predicate);
    }

    private static final RecipeGenerator GUMMY_RECIPE = (output, flavor, ingredient, resultFood, resultId) ->
            ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, resultFood.asItemLike(), 1)
                    .requires(ingredient.full().toIngredient())
                    .requires(Items.KELP)
                    .requires(Items.HONEY_BOTTLE)
                    .requires(Items.SUGAR)
                    .unlockedBy("has_" + resultId, hasIngredient(ingredient.full()))
                    .save(output, ModUtil.BA.asResource(resultId));

    private static final RecipeGenerator COOKIE_RECIPE = (output, flavor, ingredient, resultFood, resultId) ->
            ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, resultFood.asItemLike(), 8)
                    .requires(ingredient.full().toIngredient()).requires(Items.WHEAT).requires(Items.WHEAT)
                    .unlockedBy("has_" + resultId, hasIngredient(ingredient.full()))
                    .save(output, ModUtil.BA.asResource(resultId));

    private static final RecipeGenerator POPSICLE_RECIPE = (output, flavor, ingredient, resultFood, resultId) -> {
        FlavoredItems.TagOrItem fruitSlot = ingredient.preferSlice();
        Ingredient fruitIngredient = fruitSlot.toIngredient();

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, resultFood.asItemLike(), 2)
                .pattern(" FF")
                .pattern("SFF")
                .pattern("SI ")
                .define('F', fruitIngredient)
                .define('I', Items.ICE)
                .define('S', Items.STICK)
                .unlockedBy("has_" + resultId, hasIngredient(fruitSlot))
                .save(output, ModUtil.BA.asResource(resultId));
    };

    private static final RecipeGenerator PIE_RECIPE = (output, flavor, ingredient, resultFood, resultId) -> {
        FlavoredItems.TagOrItem fruitSlot = ingredient.preferSlice();
        Ingredient fruitIngredient = fruitSlot.toIngredient();

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, resultFood.asItemLike(), 2)
                .requires(fruitIngredient).requires(fruitIngredient).requires(fruitIngredient)
                .requires(Items.SUGAR).requires(BAItems.PIE_CRUST).requires(Tags.Items.EGGS)
                .unlockedBy("has_" + resultId, hasIngredient(ingredient.full()))
                .save(output, ModUtil.BA.asResource(resultId));
    };

    private static final RecipeGenerator CAKE_RECIPE = (output, flavor, ingredient, resultFood, resultId) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, resultFood.asItemLike(), 1)
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

        FlavoredItems.RegisteredFood fullFood = FlavoredItems.REGISTRY.get(fullId);
        FlavoredItems.RegisteredFood sliceFood = FlavoredItems.REGISTRY.get(sliceId);
        if (fullFood == null || sliceFood == null) return;
        int sliceCount = (type == FlavoredItems.ItemType.CAKE ? 7 : 4);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, sliceFood.asItemLike(), sliceCount)
                .requires(fullFood.asItemLike())
                .unlockedBy("has_" + fullId, InventoryChangeTrigger.TriggerInstance.hasItems(fullFood.asItemLike()))
                .save(output, ModUtil.BA.asResource(sliceId));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, fullFood.asItemLike(), 1)
                .requires(sliceFood.asItemLike(), sliceCount)
                .unlockedBy("has_" + sliceId, InventoryChangeTrigger.TriggerInstance.hasItems(sliceFood.asItemLike()))
                .save(output, ModUtil.BA.asResource(fullId + "_from_slices"));
    }

    public static void buildRecipes(RecipeOutput output) {
        for (FlavoredItems.Flavor flavor : FlavoredItems.Flavor.values()) {
            for (Map.Entry<FlavoredItems.ItemType, FlavoredItems.Variant> entry : flavor.variants.entrySet()) {
                FlavoredItems.ItemType type = entry.getKey();
                FlavoredItems.Variant variant = entry.getValue();

                if (!variant.isAvailable() || variant.baseDisabled) continue;

                RecipeGenerator generator = GENERATORS.get(type);
                if (generator != null) {
                    String resultId = flavor.id + type.suffix;
                    FlavoredItems.RegisteredFood resultFood = FlavoredItems.REGISTRY.get(resultId);
                    if (resultFood != null) {
                        generator.generate(output, flavor, flavor.ingredient, resultFood, resultId);
                    }
                }

                if (type.hasSlice() && !variant.sliceDisabled) generateSliceRecipes(output, flavor, type);
            }
        }
    }
}