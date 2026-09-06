package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Set;

public class BAItemModelProvider extends ItemModelProvider {
    private static final Set<String> BLACKLIST = Set.of(
            "pitchfork",
            "sugar_cookie"
    );

    private static final Set<String> BLOCK_ITEMS = Set.of(
            "drying_rack",
            "copper_tank"
    );

    private static final Set<String> HANDHELD = Set.of(
            "cinnamon_sticks"
    );

    public BAItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BonAppetit.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        BAItems.ITEMS.getEntries().forEach(item -> {
            if (item.getId() == null) return;
            String path = item.getId().getPath();

            if (BLACKLIST.contains(path)) return;
            if (HANDHELD.contains(path)) {
                safeHandheldItem(item.get());
            } else if (BLOCK_ITEMS.contains(path)) {
                withExistingParent(path, modLoc("block/" + path));
            } else {
                safeBasicItem(item.get());
            }
        });
    }

    private ItemModelBuilder safeBasicItem(Item item) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        ResourceLocation texture = getItemTexture(id);
        return withExistingParent(id.getPath(), "item/generated").texture("layer0", texture);
    }

    private ItemModelBuilder safeHandheldItem(Item item) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        ResourceLocation texture = getItemTexture(id);
        return withExistingParent(id.getPath(), "item/handheld").texture("layer0", texture);
    }

    private ResourceLocation getItemTexture(ResourceLocation itemId) {
        ResourceLocation texture = modLoc("item/" + itemId.getPath());
        if (existingFileHelper.exists(texture, PackType.CLIENT_RESOURCES, ".png", "textures")) {
            return texture;
        }
        return ModUtil.BA.asResource("item/placeholder");
    }
}