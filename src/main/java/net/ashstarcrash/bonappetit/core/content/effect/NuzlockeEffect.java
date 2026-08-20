package net.ashstarcrash.bonappetit.core.content.effect;

import net.ashstarcrash.bonappetit.compat.ModUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class NuzlockeEffect extends MobEffect {
    private static final ResourceLocation MODIFIER_ID = ModUtil.BA.asResource("effect.nuzlocke.max_health");

    public NuzlockeEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void addAttributeModifiers(@NotNull AttributeMap attributeMap, int amplifier) {
        AttributeInstance maxHealthAttr = attributeMap.getInstance(Attributes.MAX_HEALTH);
        if (maxHealthAttr != null) {
            maxHealthAttr.removeModifier(MODIFIER_ID);

            double currentMax = maxHealthAttr.getValue();
            double requiredSubtraction = 2.0D - currentMax;

            maxHealthAttr.addPermanentModifier(new AttributeModifier(MODIFIER_ID, requiredSubtraction, AttributeModifier.Operation.ADD_VALUE));
        }
        super.addAttributeModifiers(attributeMap, amplifier);
    }

    @Override
    public void removeAttributeModifiers(@NotNull AttributeMap attributeMap) {
        AttributeInstance maxHealthAttr = attributeMap.getInstance(Attributes.MAX_HEALTH);
        if (maxHealthAttr != null) maxHealthAttr.removeModifier(MODIFIER_ID);
        super.removeAttributeModifiers(attributeMap);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            if (player.getHealth() > 2.0F) player.setHealth(2.0F);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}