package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.ashstarcrash.bonappetit.core.registry.BABlocks;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.ashstarcrash.bonappetit.core.common.template.BAFlavorCandleCakeBlock;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Map;
import java.util.Set;

public class BABlockLootTableProvider extends BlockLootSubProvider {
    private final Set<Block> knownBlocks = new java.util.HashSet<>();

    protected BABlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(BABlocks.COPPER_TANK.get());
        dropCake(Blocks.CAKE, BAItems.CAKE_SLICE.get());
        dropCake(BABlocks.LEMON_CAKE.get(), BAItems.LEMON_CAKE_SLICE.get());
        dropCake(BABlocks.LIME_CAKE.get(), BAItems.LIME_CAKE_SLICE.get());
    }

    protected void dropCake(Block cakeBlock, ItemLike sliceItem) {
        for (BAFlavorCandleCakeBlock candleCake : BAFlavorCandleCakeBlock.getCandleCakes()) {
            if (candleCake.getCake() == cakeBlock) {
                this.addCandleCakeLoot(candleCake, candleCake.getCandle(), cakeBlock, sliceItem);
            }
        }
        if (cakeBlock == Blocks.CAKE) {
            getVanillaCandleCakes().forEach((candle, candleCake) ->
                    this.addCandleCakeLoot(candleCake, candle, cakeBlock, sliceItem)
            );
        }
        this.add(cakeBlock, LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(cakeBlock)
                                .when(this.hasSilkTouch())
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(cakeBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CakeBlock.BITES, 0)))
                                .otherwise(
                                        LootItem.lootTableItem(sliceItem)
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(7)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(cakeBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CakeBlock.BITES, 0))))
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(6)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(cakeBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CakeBlock.BITES, 1))))
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(5)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(cakeBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CakeBlock.BITES, 2))))
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(cakeBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CakeBlock.BITES, 3))))
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(cakeBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CakeBlock.BITES, 4))))
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(cakeBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CakeBlock.BITES, 5))))
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(cakeBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CakeBlock.BITES, 6))))
                                )
                        )
        ));
    }
    protected void addCandleCakeLoot(Block candleCakeBlock, ItemLike candleItem, Block baseCake, ItemLike sliceItem) {
        this.add(candleCakeBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(candleItem)))

                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity()))
                        .add(LootItem.lootTableItem(baseCake).when(this.hasSilkTouch())))

                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity()))
                        .when(this.hasSilkTouch().invert())
                        .add(LootItem.lootTableItem(sliceItem)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(7.0F)))))
        );
    }
    private static Map<Block, Block> getVanillaCandleCakes() {
        return Map.ofEntries(
                Map.entry(Blocks.CANDLE, Blocks.CANDLE_CAKE),
                Map.entry(Blocks.WHITE_CANDLE, Blocks.WHITE_CANDLE_CAKE),
                Map.entry(Blocks.ORANGE_CANDLE, Blocks.ORANGE_CANDLE_CAKE),
                Map.entry(Blocks.MAGENTA_CANDLE, Blocks.MAGENTA_CANDLE_CAKE),
                Map.entry(Blocks.LIGHT_BLUE_CANDLE, Blocks.LIGHT_BLUE_CANDLE_CAKE),
                Map.entry(Blocks.YELLOW_CANDLE, Blocks.YELLOW_CANDLE_CAKE),
                Map.entry(Blocks.LIME_CANDLE, Blocks.LIME_CANDLE_CAKE),
                Map.entry(Blocks.PINK_CANDLE, Blocks.PINK_CANDLE_CAKE),
                Map.entry(Blocks.GRAY_CANDLE, Blocks.GRAY_CANDLE_CAKE),
                Map.entry(Blocks.LIGHT_GRAY_CANDLE, Blocks.LIGHT_GRAY_CANDLE_CAKE),
                Map.entry(Blocks.CYAN_CANDLE, Blocks.CYAN_CANDLE_CAKE),
                Map.entry(Blocks.PURPLE_CANDLE, Blocks.PURPLE_CANDLE_CAKE),
                Map.entry(Blocks.BLUE_CANDLE, Blocks.BLUE_CANDLE_CAKE),
                Map.entry(Blocks.BROWN_CANDLE, Blocks.BROWN_CANDLE_CAKE),
                Map.entry(Blocks.GREEN_CANDLE, Blocks.GREEN_CANDLE_CAKE),
                Map.entry(Blocks.RED_CANDLE, Blocks.RED_CANDLE_CAKE),
                Map.entry(Blocks.BLACK_CANDLE, Blocks.BLACK_CANDLE_CAKE)
        );
    }

    @Override
    protected void dropSelf(Block block) {
        this.knownBlocks.add(block);
        super.dropSelf(block);
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        this.knownBlocks.add(block);
        super.add(block, builder);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return this.knownBlocks;
    }
}