package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.ashstarcrash.bonappetit.core.common.template.BAFlavorCakeBlock;
import net.ashstarcrash.bonappetit.core.common.template.BAFlavorCandleCakeBlock;
import net.ashstarcrash.bonappetit.core.content.block.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static net.minecraft.world.level.block.Blocks.*;

public class BABlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BonAppetit.ID);

    public static final DeferredBlock<CookingPotBlock> COOKING_POT = registerBlockNoItem("cooking_pot",
            () -> new CookingPotBlock(BlockBehaviour.Properties.of().dynamicShape().sound(SoundType.LANTERN).noOcclusion()));
    public static final DeferredBlock<DryingRackBlock> DRYING_RACK = registerBlock("drying_rack",
            () -> new DryingRackBlock(BlockBehaviour.Properties.ofFullCopy(GLASS).dynamicShape().sound(SoundType.WOOD).noOcclusion()));
    public static final DeferredBlock<CopperTankBlock> COPPER_TANK = registerBlock("copper_tank",
            () -> new CopperTankBlock(BlockBehaviour.Properties.ofFullCopy(WAXED_COPPER_BLOCK).sound(SoundType.COPPER).noOcclusion()));

    public static final DeferredBlock<Block> CORN_BASE = registerBlockNoItem("corn_base", () -> new CornCropBlock(BlockBehaviour.Properties.ofFullCopy(WHEAT).randomTicks().offsetType(BlockBehaviour.OffsetType.NONE).instabreak().sound(SoundType.CROP)));
    public static final DeferredBlock<Block> CORN_TOP = registerBlockNoItem("corn_top", () -> new CornCropBlockTop(BlockBehaviour.Properties.ofFullCopy(WHEAT)));
    public static final DeferredBlock<Block> GRAPEFRUIT_VINE = registerBlockNoItem("grapefruit_vine", () -> new GrapefruitVineBlock(BlockBehaviour.Properties.ofFullCopy(WHEAT).randomTicks().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().noCollission().sound(SoundType.WEEPING_VINES)));
    public static final DeferredBlock<Block> POMEGRANATE_BLOCK = registerBlockNoItem("pomegranate_block", () -> new PomegranateBlock(BlockBehaviour.Properties.ofFullCopy(COCOA).randomTicks().sound(SoundType.NETHER_WART)));

    public static final DeferredBlock<Block> PANETTONE = BLOCKS.register("panettone",
            () -> new BAFlavorCakeBlock(BAFoodProperties.PANETTONE, BlockBehaviour.Properties.ofFullCopy(CAKE)));
    public static final DeferredBlock<Block> STOLLEN = BLOCKS.register("stollen",
            () -> new BAFlavorCakeBlock(BAFoodProperties.STOLLEN, BlockBehaviour.Properties.ofFullCopy(CAKE)));

    public static final DeferredBlock<Block> COCHINEAL_SPONGECAKE = registerBlockNoItem("cochineal_spongecake",
            () -> new SpongecakeBlock(BAFoodProperties.Compat.COCHINEAL_SPONGECAKE, BlockBehaviour.Properties.ofFullCopy(CAKE)));



    private static ToIntFunction<BlockState> litBlockEmission(int level) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? level : 0;
    }
    private static void registerCandleCakes(String name, DeferredBlock<BAFlavorCakeBlock> baseCake) {
        BLOCKS.register(name + "_candle_cake",
                () -> new BAFlavorCandleCakeBlock(baseCake, CANDLE, BlockBehaviour.Properties.ofFullCopy(CANDLE_CAKE)));
        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getName();
            Block vanillaCandle = BuiltInRegistries.BLOCK.get(ModUtil.BA.asResource(colorName + "_candle"));
            BLOCKS.register(colorName + "_candle_" + name + "_cake",
                    () -> new BAFlavorCandleCakeBlock(baseCake, vanillaCandle, BlockBehaviour.Properties.ofFullCopy(CANDLE_CAKE)));
        }
    }
    public static DeferredBlock<BAFlavorCakeBlock> registerFlavorCakes(String name, FoodProperties food) {
        DeferredBlock<BAFlavorCakeBlock> cake = BLOCKS.register(name, () -> new BAFlavorCakeBlock(food, BlockBehaviour.Properties.ofFullCopy(CAKE)));
        registerBlockItem(name, cake);
        registerCandleCakes(name, cake);
        return cake;
    }
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {DeferredBlock<T> toReturn = BLOCKS.register(name, block); registerBlockItem(name, toReturn); return toReturn;}
    private static <T extends Block> DeferredBlock<T> registerBlockNoItem(String name, Supplier<T> block) {return BLOCKS.register(name, block);}
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {BAItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties())); }
    public static void register(IEventBus eventBus) {BLOCKS.register(eventBus);}
}
