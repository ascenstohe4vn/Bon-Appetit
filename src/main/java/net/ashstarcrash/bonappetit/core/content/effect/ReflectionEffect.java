package net.ashstarcrash.bonappetit.core.content.effect;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*\
 * Reflection reflects all projectiles that attempt to hit you.
 * Starting % is 40 at amplifier 0, and adding 30 for every amplifier up to 100% at amplifier 2
\*/
public class ReflectionEffect extends MobEffect implements Holder<MobEffect> {
    public ReflectionEffect() {super(MobEffectCategory.BENEFICIAL, 0xCD282E);}

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }

    @Override public MobEffect value() {return null;}
    @Override public boolean isBound() {return false;}
    @Override public boolean is(ResourceLocation resourceLocation) {return false;}
    @Override public boolean is(ResourceKey<MobEffect> resourceKey) {return false;}
    @Override public boolean is(Predicate<ResourceKey<MobEffect>> predicate) {return false;}
    @Override public boolean is(TagKey<MobEffect> tagKey) {return false;}
    @Override public boolean is(Holder<MobEffect> holder) {return false;}
    @Override public Stream<TagKey<MobEffect>> tags() {return Stream.empty();}
    @Override public Either<ResourceKey<MobEffect>, MobEffect> unwrap() {return null;}
    @Override public Optional<ResourceKey<MobEffect>> unwrapKey() {return Optional.empty();}
    @Override public Kind kind() {return null;}
    @Override public boolean canSerializeIn(HolderOwner<MobEffect> holderOwner) {return false;}
}