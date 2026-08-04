package net.ashstarcrash.bonappetit.core.registry;

import com.mojang.serialization.Codec;
import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.common.util.FoodDiscoveryData;
import net.ashstarcrash.bonappetit.core.common.util.FoodRegenData;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class BAAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, BonAppetit.ID);

    private static final Codec<FoodDiscoveryData> FOOD_DISCOVERY_CODEC =
            Codec.STRING.listOf().xmap(
                    list -> { var data = new FoodDiscoveryData(); list.forEach(data::markEaten); return data; },
                    data -> new java.util.ArrayList<>(data.getEatenFoodIds())
            );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<FoodDiscoveryData>> FOOD_DISCOVERY =
            ATTACHMENTS.register("food_discovery",
                    () -> AttachmentType.builder(FoodDiscoveryData::new)
                            .serialize(FOOD_DISCOVERY_CODEC)
                            .sync(ByteBufCodecs.fromCodec(FOOD_DISCOVERY_CODEC))
                            .build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<FoodRegenData>> FOOD_REGEN =
            ATTACHMENTS.register("food_regen",
                    () -> AttachmentType.builder(FoodRegenData::new).build());
}