package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BABlocks;
import net.ashstarcrash.bonappetit.core.common.template.BAFlavorCandleCakeBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;

public class BABlockStateProvider extends BlockStateProvider {
    public BABlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {super(output, BonAppetit.ID, exFileHelper);}

    @Override
    protected void registerStatesAndModels() {
        cakeBlock(BABlocks.LEMON_CAKE.get());
        cakeBlock(BABlocks.LIME_CAKE.get());
    }

    private String name(Block block) {
        return key(block).getPath();
    }
    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
    public void makeBush(SweetBerryBushBlock block, String modelName, String textureName) {Function<BlockState, ConfiguredModel[]> function = state -> states(state, modelName, textureName);getVariantBuilder(block).forAllStates(function);}
    private ConfiguredModel[] states(BlockState state, String modelName, String textureName) {ConfiguredModel[] models = new ConfiguredModel[1];models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(SweetBerryBushBlock.AGE), ResourceLocation.fromNamespaceAndPath(BonAppetit.ID, "block/" + textureName + state.getValue(SweetBerryBushBlock.AGE))).renderType("cutout"));return models;}
    private void saplingBlock(DeferredBlock<SaplingBlock> blockRegistryObject) {simpleBlock(blockRegistryObject.get(), models().cross(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));}
    private void blockWithItem(DeferredBlock<?> deferredBlock) {simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));}
    private void blockItem(DeferredBlock<?> deferredBlock) {simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("bonappetit:block/" + deferredBlock.getId().getPath()));}
    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("bonappetit:block/" + deferredBlock.getId().getPath() + appendix));}
    public void cakeBlock(Block block) {
        ModelFile base = cakeModel(block, "", "block/cake");
        this.getVariantBuilder(block).forAllStates(state -> {
            int bites = state.getValue(CakeBlock.BITES);
            return ConfiguredModel.builder()
                    .modelFile(bites == 0 ? base : cakeModel(block, "_slice" + bites, "block/cake_slice" + bites))
                    .build();
        });
        for (BAFlavorCandleCakeBlock candleCake : BAFlavorCandleCakeBlock.getCandleCakes()) {
            if (candleCake.getCake() == block) {
                this.generateCandleCakeModels(candleCake, block, candleCake.getCandle());
            }
        }
    }
    public ModelFile cakeModel(Block block, String suffix, String parent) {
        return models().withExistingParent(name(block) + suffix, mcLoc(parent))
                .texture("bottom", blockTexture(block).withSuffix("_bottom"))
                .texture("side", blockTexture(block).withSuffix("_side"))
                .texture("top", blockTexture(block).withSuffix("_top"))
                .texture("inside", blockTexture(block).withSuffix("_inner"))
                .texture("particle", blockTexture(block).withSuffix("_side"));
    }

    private void generateCandleCakeModels(Block candleCake, Block baseCake, Block candle) {
        ModelFile unlit = models().withExistingParent(name(candleCake), mcLoc("template_cake_with_candle"))
                .texture("candle", blockTexture(candle))
                .texture("bottom", blockTexture(baseCake).withSuffix("_bottom"))
                .texture("side", blockTexture(baseCake).withSuffix("_side"))
                .texture("top", blockTexture(baseCake).withSuffix("_top"))
                .texture("particle", blockTexture(baseCake).withSuffix("_side"));

        ModelFile lit = models().withExistingParent(name(candleCake) + "_lit", mcLoc("template_cake_with_candle"))
                .texture("candle", blockTexture(candle).withSuffix("_lit"))
                .texture("bottom", blockTexture(baseCake).withSuffix("_bottom"))
                .texture("side", blockTexture(baseCake).withSuffix("_side"))
                .texture("top", blockTexture(baseCake).withSuffix("_top"))
                .texture("particle", blockTexture(baseCake).withSuffix("_side"));

        this.getVariantBuilder(candleCake).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(state.getValue(BlockStateProperties.LIT) ? lit : unlit)
                        .build()
        );
    }
}