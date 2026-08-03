package net.ashstarcrash.bonappetit.core.common.data.gen;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Set;

public class BAItemModelProvider extends ItemModelProvider {
    private static final Set<String> BLACKLIST = Set.of(
            "pitchfork"
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
                handheldItem(item.get());
            } else if (BLOCK_ITEMS.contains(path)) {
                withExistingParent(path, modLoc("block/" + path));
            } else {
                basicItem(item.get());
            }
        });
    }
}