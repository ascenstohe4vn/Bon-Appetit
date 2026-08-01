package net.ashstarcrash.bonappetit.core.common.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DungeonLootModifier extends LootModifier {
    public static final MapCodec<DungeonLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(
                    inst.group(
                            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(m -> m.item),
                            LootItemFunctions.ROOT_CODEC.listOf().optionalFieldOf("functions", List.of()).forGetter(m -> m.functions)
                    )
            ).apply(inst, DungeonLootModifier::new)
    );

    private final Item item;
    private final List<LootItemFunction> functions;

    protected DungeonLootModifier(LootItemCondition[] conditionsIn, Item item, List<LootItemFunction> functions) {
        super(conditionsIn);
        this.item = item;
        this.functions = functions;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ItemStack stack = new ItemStack(item);
        for (LootItemFunction function : this.functions) {
            stack = function.apply(stack, context);
        }
        generatedLoot.add(stack);
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}