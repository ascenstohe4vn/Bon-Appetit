package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.ashstarcrash.bonappetit.core.registry.BABlocks;
import net.ashstarcrash.bonappetit.core.common.template.BAFlavorCandleCakeBlock;
import net.ashstarcrash.bonappetit.core.registry.FlavoredItems;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
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

import static net.ashstarcrash.bonappetit.core.registry.BABlocks.*;

public class BABlockStateProvider extends BlockStateProvider {
    private final ExistingFileHelper exFileHelper;
    public BABlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BonAppetit.ID, exFileHelper);
        this.exFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        pomegranateBlock(POMEGRANATE_BLOCK.get());
        simpleBlock(PANETTONE.get());
        simpleBlock(STOLLEN.get());
        horizontalBlock(COCHINEAL_SPONGECAKE.get(), models().getExistingFile(modLoc("block/cochineal_spongecake")));
        for (var entry : FlavoredItems.CAKE_BLOCKS_BY_ID.entrySet()) cakeBlock(entry.getValue().get());
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
                .texture("bottom", safeBlockTexture(block, "_bottom"))
                .texture("side", safeBlockTexture(block, "_side"))
                .texture("top", safeBlockTexture(block, "_top"))
                .texture("inside", safeBlockTexture(block, "_inner"))
                .texture("particle", safeBlockTexture(block, "_side"));
    }

    private void generateCandleCakeModels(Block candleCake, Block baseCake, Block candle) {
        ModelFile unlit = models().withExistingParent(name(candleCake), mcLoc("template_cake_with_candle"))
                .texture("candle", safeBlockTexture(candle, ""))
                .texture("bottom", safeBlockTexture(baseCake, "_bottom"))
                .texture("side", safeBlockTexture(baseCake, "_side"))
                .texture("top", safeBlockTexture(baseCake, "_top"))
                .texture("particle", safeBlockTexture(baseCake, "_side"));

        ModelFile lit = models().withExistingParent(name(candleCake) + "_lit", mcLoc("template_cake_with_candle"))
                .texture("candle", safeBlockTexture(candle, "_lit"))
                .texture("bottom", safeBlockTexture(baseCake, "_bottom"))
                .texture("side", safeBlockTexture(baseCake, "_side"))
                .texture("top", safeBlockTexture(baseCake, "_top"))
                .texture("particle", safeBlockTexture(baseCake, "_side"));

        this.getVariantBuilder(candleCake).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(state.getValue(BlockStateProperties.LIT) ? lit : unlit)
                        .build()
        );
    }
    public void pomegranateBlock(Block block) {
        this.getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int age = state.getValue(BlockStateProperties.AGE_3);
            int yRot = ((int) facing.toYRot() + 180) % 360;

            String parentModel = "block/cocoa_stage" + Math.min(age, 2); //temp
            ModelFile model = models().withExistingParent(name(block) + "_stage" + age, mcLoc(parentModel))
                    .texture("particle", blockTexture(block).withSuffix("_stage" + age))
                    .texture("selection", blockTexture(block).withSuffix("_stage" + age))
                    .renderType("cutout");

            return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
        });
    }
    private ResourceLocation safeBlockTexture(Block block, String suffix) {
        ResourceLocation texture = blockTexture(block).withSuffix(suffix);
        if (exFileHelper.exists(texture, PackType.CLIENT_RESOURCES, ".png", "textures")) {
            return texture;
        }
        return ModUtil.BA.asResource("block/placeholder");
    }
}