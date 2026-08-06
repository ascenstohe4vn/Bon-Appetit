package net.ashstarcrash.bonappetit.core.common.loot;

import com.mojang.serialization.Codec;
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
import java.util.Optional;

public class AbandonedMineshaftLootModifier extends LootModifier {
    public static final MapCodec<AbandonedMineshaftLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(
                    inst.group(
                            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(m -> m.item),
                            LootItemFunctions.ROOT_CODEC.listOf().optionalFieldOf("functions", List.of()).forGetter(m -> m.functions),
                            BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("target_item").forGetter(m -> m.targetItem),
                            Codec.FLOAT.optionalFieldOf("replace_chance", 1.0f).forGetter(m -> m.replaceChance)
                    )
            ).apply(inst, AbandonedMineshaftLootModifier::new)
    );

    private final Item item;
    private final List<LootItemFunction> functions;
    private final Optional<Item> targetItem;
    private final float replaceChance;

    protected AbandonedMineshaftLootModifier(LootItemCondition[] conditionsIn, Item item, List<LootItemFunction> functions, Optional<Item> targetItem, float replaceChance) {
        super(conditionsIn);
        this.item = item;
        this.functions = functions;
        this.targetItem = targetItem;
        this.replaceChance = replaceChance;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (this.targetItem.isPresent()) {
            Item target = this.targetItem.get();
            for (int i = 0; i < generatedLoot.size(); i++) {
                ItemStack stack = generatedLoot.get(i);
                if (stack.is(target)) {
                    if (context.getRandom().nextFloat() < this.replaceChance) {
                        ItemStack replacedStack = new ItemStack(this.item, stack.getCount());
                        for (LootItemFunction function : this.functions) {
                            replacedStack = function.apply(replacedStack, context);
                        }
                        generatedLoot.set(i, replacedStack);
                    }
                }
            }
        } else {
            ItemStack stack = new ItemStack(this.item);
            for (LootItemFunction function : this.functions) {
                stack = function.apply(stack, context);
            }
            generatedLoot.add(stack);
        }
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}