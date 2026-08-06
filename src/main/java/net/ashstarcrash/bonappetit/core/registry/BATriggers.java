package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.common.data.advancement.ReflectedProjectileTrigger;
import net.ashstarcrash.bonappetit.core.common.data.advancement.SeededRuptureTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BATriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, BonAppetit.ID);

    public static final Supplier<ReflectedProjectileTrigger> REFLECTED_PROJECTILE =
            TRIGGERS.register("reflected_projectile", ReflectedProjectileTrigger::new);
    public static final Supplier<SeededRuptureTrigger> SEEDED_RUPTURE =
            TRIGGERS.register("seeded_rupture", SeededRuptureTrigger::new);

    public static void register(IEventBus eventBus) {
        TRIGGERS.register(eventBus);
    }
}