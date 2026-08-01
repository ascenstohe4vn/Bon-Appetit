package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@Mod(value = BonAppetit.ID) @EventBusSubscriber(modid = BonAppetit.ID)
public class VigorEvent {
    private static final ResourceLocation VIGOR_SPEED_ID = ModUtil.BA.asResource("vigor_speed");

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            MobEffectInstance effect = entity.getEffect(BAEffects.VIGOR);
            AttributeInstance speedInst = entity.getAttribute(Attributes.MOVEMENT_SPEED);

            if (effect != null && entity.isSprinting()) {
                int amp = effect.getAmplifier();
                double speedBonus = 0.2 + (amp * 0.1);

                updateModifier(speedInst, VIGOR_SPEED_ID, speedBonus);
            } else {
                removeModifier(speedInst, VIGOR_SPEED_ID);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effect = entity.getEffect(BAEffects.VIGOR);

        if (effect != null && entity.isSprinting()) {
            int amp = effect.getAmplifier();
            double jumpMult = 0.05 + (amp * 0.05);

            Vec3 delta = entity.getDeltaMovement();
            entity.setDeltaMovement(delta.x, delta.y + jumpMult, delta.z);
        }
    }

    private static void updateModifier(AttributeInstance inst, ResourceLocation id, double amount) {
        if (inst == null) return;
        AttributeModifier existing = inst.getModifier(id);

        if (existing == null || existing.amount() != amount) {
            if (existing != null) inst.removeModifier(id);
            inst.addOrUpdateTransientModifier(new AttributeModifier(id, amount, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
    }

    private static void removeModifier(AttributeInstance inst, ResourceLocation id) {
        if (inst != null && inst.hasModifier(id)) inst.removeModifier(id);
    }
}