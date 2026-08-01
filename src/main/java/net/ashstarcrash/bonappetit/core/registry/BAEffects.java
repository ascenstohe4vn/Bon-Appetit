package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.content.effect.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BAEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, BonAppetit.ID);

    public static final DeferredHolder<MobEffect, TwinStrikeEffect> TWIN_STRIKE = EFFECTS.register("twin_strike", TwinStrikeEffect::new);
    public static final DeferredHolder<MobEffect, InsatiableEffect> INSATIABLE = EFFECTS.register("insatiable", InsatiableEffect::new);
    public static final DeferredHolder<MobEffect, MellowEffect> MELLOW = EFFECTS.register("mellow", MellowEffect::new);
    public static final DeferredHolder<MobEffect, ProliferateEffect> PROLIFERATE = EFFECTS.register("proliferate", ProliferateEffect::new);
    public static final DeferredHolder<MobEffect, FervorEffect> FERVOR = EFFECTS.register("fervor", FervorEffect::new);
    public static final DeferredHolder<MobEffect, DischargeEffect> DISCHARGE = EFFECTS.register("discharge", DischargeEffect::new);

    public static final DeferredHolder<MobEffect, ReflectionEffect> REFLECTION = EFFECTS.register("reflection", ReflectionEffect::new);
    public static final DeferredHolder<MobEffect, ConcentrationEffect> CONCENTRATION = EFFECTS.register("concentration", ConcentrationEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> AGILITY = EFFECTS.register("agility", () -> new AgilityEffect(MobEffectCategory.NEUTRAL, 0xFCF5CA)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, BonAppetit.asResource("effect.agility.movement_speed"), 0.025F, AttributeModifier.Operation.ADD_VALUE));
    public static final DeferredHolder<MobEffect, MobEffect> RESONANCE = EFFECTS.register("resonance", () -> new ResonanceEffect(MobEffectCategory.NEUTRAL, 0xFCF5CA)
            .addAttributeModifier(Attributes.ARMOR, BonAppetit.asResource("effect.resonance.armor"), 1.0F, AttributeModifier.Operation.ADD_VALUE));
    public static final DeferredHolder<MobEffect, MobEffect> DISSONANCE = EFFECTS.register("dissonance", () -> new DissonanceEffect(MobEffectCategory.NEUTRAL, 0xE8FED8)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, BonAppetit.asResource("effect.dissonance.attack_damage"), 1.0F, AttributeModifier.Operation.ADD_VALUE));
    public static final DeferredHolder<MobEffect, MobEffect> OBSCURITY = EFFECTS.register("obscurity", () -> new ObscurityEffect(MobEffectCategory.BENEFICIAL, 0xCDE262)
            .addAttributeModifier(Attributes.SNEAKING_SPEED, BonAppetit.asResource("effect.obscurity.sneaking_speed"), 50.0F, AttributeModifier.Operation.ADD_VALUE));
    public static final DeferredHolder<MobEffect, FlakEffect> FLAK = EFFECTS.register("flak", FlakEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> ROOTED = EFFECTS.register("rooted", () -> new RootedEffect(MobEffectCategory.BENEFICIAL, 0xA4272C)
            .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, BonAppetit.asResource("effect.rooted.knockback_resistance"), 2F, AttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(Attributes.ARMOR, BonAppetit.asResource("effect.rooted.armor"), 1.5F, AttributeModifier.Operation.ADD_VALUE));

    public static final DeferredHolder<MobEffect, VigorEffect> VIGOR = EFFECTS.register("vigor", VigorEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> CAFFEINATED = EFFECTS.register("caffeinated", () -> new CaffeinatedEffect(MobEffectCategory.BENEFICIAL, 0x270D0A)
            .addAttributeModifier(Attributes.ATTACK_SPEED, BonAppetit.asResource("effect.caffeinated.attack_speed"), 0.2F, AttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(Attributes.MOVEMENT_EFFICIENCY, BonAppetit.asResource("effect.caffeinated.movement_speed"), 0.65F, AttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, BonAppetit.asResource("effect.caffeinated.block_break_speed"), 0.2F, AttributeModifier.Operation.ADD_VALUE));



    public static final DeferredHolder<MobEffect, SeededEffect> SEEDED = EFFECTS.register("seeded", SeededEffect::new);
}