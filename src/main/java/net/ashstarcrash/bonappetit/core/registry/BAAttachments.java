package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.common.util.FoodRegenData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class BAAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, BonAppetit.ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<FoodRegenData>> FOOD_REGEN =
            ATTACHMENTS.register("food_regen",
                    () -> AttachmentType.builder(FoodRegenData::new).build());
}