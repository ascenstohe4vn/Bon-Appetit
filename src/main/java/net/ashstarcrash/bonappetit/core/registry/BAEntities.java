package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.core.content.entity.DragonShardEntity;
import net.ashstarcrash.bonappetit.core.content.entity.ThrownPitchfork;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BAEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, "bonappetit");

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownPitchfork>> PITCHFORK =
            ENTITIES.register("pitchfork", () -> EntityType.Builder.<ThrownPitchfork>of(ThrownPitchfork::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("pitchfork"));
    public static final DeferredHolder<EntityType<?>, EntityType<DragonShardEntity>> DRAGON_SHARD =
            ENTITIES.register("dragon_shard", () -> EntityType.Builder.<DragonShardEntity>of(DragonShardEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("dragon_shard"));
}