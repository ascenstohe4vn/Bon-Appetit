package net.ashstarcrash.bonappetit.core.common.event;

import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.common.util.FoodRegenData;
import net.ashstarcrash.bonappetit.core.content.entity.goal.BeeMoveToFruitBushGoal;
import net.ashstarcrash.bonappetit.core.content.entity.goal.BeePollinateFruitGoal;
import net.ashstarcrash.bonappetit.core.registry.BAAttachments;
import net.ashstarcrash.bonappetit.core.registry.BAEffects;
import net.ashstarcrash.bonappetit.core.registry.BAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = BonAppetit.ID)
public class GameEvents {
    private record MobEffectConfigRule(Supplier<Holder<MobEffect>> effect, ModConfigSpec.BooleanValue enabled, ModConfigSpec.ConfigValue<List<? extends String>> spawnables) {}
    private static final MobEffectConfigRule[] RULES = new MobEffectConfigRule[] {
            new MobEffectConfigRule(() -> BAEffects.TWIN_STRIKE, BAConfig.TWIN_STRIKE_MOB_SPAWNING, BAConfig.TWIN_STRIKE_MOB_SPAWNABLES),
            new MobEffectConfigRule(() -> BAEffects.FLAK, BAConfig.FLAK_MOB_SPAWNING, BAConfig.FLAK_MOB_SPAWNABLES),
            new MobEffectConfigRule(() -> BAEffects.PROLIFERATE, BAConfig.PROLIFERATE_MOB_SPAWNING, BAConfig.PROLIFERATE_MOB_SPAWNABLES)
    };

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || event.getLevel().getDifficulty() != Difficulty.HARD) return;
        if (!(event.getEntity() instanceof LivingEntity living) || !(living instanceof Enemy)) return;

        EntityType<?> type = living.getType();

        for (MobEffectConfigRule rule : RULES) {
            Holder<MobEffect> effectHolder = rule.effect().get();
            if (living.hasEffect(effectHolder)) continue;

            if (event.getLevel().getRandom().nextFloat() < 0.10f) {
                if (BAConfig.isValidEntity(type, rule.enabled(), rule.spawnables())) {
                    living.addEffect(new MobEffectInstance(effectHolder, MobEffectInstance.INFINITE_DURATION, 0));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onFinishEating(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!event.getItem().getComponents().has(DataComponents.FOOD)) return;

        String id = BuiltInRegistries.ITEM.getKey(event.getItem().getItem()).toString();
        player.getData(BAAttachments.FOOD_DISCOVERY.get()).markEaten(id);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        FoodRegenData data = player.getData(BAAttachments.FOOD_REGEN.get());
        if (!data.isActive()) return;

        int ticksRemaining = data.getTicksRemaining() - 1;
        int pulseTimer = data.getPulseTimer() + 1;

        int pulseInterval = BAConfig.FOOD_REGEN_PULSE_INTERVAL_TICKS.get();
        if (pulseTimer >= pulseInterval) {
            pulseTimer = 0;
            float healAmount = BAConfig.FOOD_REGEN_PULSE_HEAL_AMOUNT.get().floatValue();
            if (player.getHealth() < player.getMaxHealth() && healAmount > 0.0F) {
                player.heal(healAmount);
            }
        }

        data.setTicksRemaining(Math.max(0, ticksRemaining));
        data.setPulseTimer(pulseTimer);
    }

    @SubscribeEvent
    public static void onSkeletonDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Skeleton skeleton && !skeleton.level().isClientSide()) {
            DamageSource source = event.getSource();

            if (source.getDirectEntity() instanceof AbstractArrow arrow && arrow.getOwner() == skeleton) {
                //skeleton.spawnAtLocation(BAItems.MUSIC_DISC_PLACEHOLDER.get());
            }
        }
    }

    @SubscribeEvent
    public static void onCakeInteract(PlayerInteractEvent.RightClickBlock event) {
        if (!BAConfig.CAKE_REPAIRING.get()) return;

        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = event.getItemStack();
        if (state.is(Blocks.CAKE)) {
            int bites = state.getValue(CakeBlock.BITES);
            if (stack.is(BAItems.CAKE_SLICE.get()) && bites > 0) {
                if (!level.isClientSide) {
                    level.setBlock(pos, state.setValue(CakeBlock.BITES, bites - 1), 3);
                    level.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

                    if (!event.getEntity().getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }

    @SubscribeEvent
    public static void onCakeDamage(LivingIncomingDamageEvent event) {
        if (!BAConfig.CAKE_FALL_CUSHIONING.get()) return;
        if (!event.getSource().is(DamageTypes.FALL)) return;
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        BlockPos landPos = BlockPos.containing(entity.getX(), entity.getY() - 0.1D, entity.getZ());
        BlockState state = level.getBlockState(landPos);

        if (state.is(Blocks.CAKE) || state.getBlock() instanceof CakeBlock || state.is(BlockTags.CANDLE_CAKES)) {
            float newAmount = event.getAmount() * 0.2F;
            event.setAmount(newAmount);
            if (!level.isClientSide && entity.fallDistance > 3.0F) {
                if (level.random.nextFloat() < 0.20F) {
                    level.levelEvent(2001, landPos, Block.getId(state));
                    level.playSound(null, landPos, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 1.5F, 1.0F);
                    level.removeBlock(landPos, false);
                    level.gameEvent(GameEvent.BLOCK_DESTROY, landPos, GameEvent.Context.of(entity, state));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onVanillaCakeEat(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Player player = event.getEntity();

        if (BAConfig.VANILLA_CAKE_EFFECT.get() && state.is(Blocks.CAKE)) {
            if (player.canEat(false)) {
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);

                if (!level.isClientSide()) {
                    int durationToAdd = 200;
                    MobEffectInstance vigorInstance = player.getEffect(BAEffects.VIGOR);

                    int finalDuration = durationToAdd;
                    if (vigorInstance != null) {
                        finalDuration = Math.min(vigorInstance.getDuration() + durationToAdd, 12000);
                    }

                    player.addEffect(new MobEffectInstance(BAEffects.VIGOR, finalDuration, 0));
                    player.getFoodData().eat(2, 0.1F);
                    player.awardStat(Stats.EAT_CAKE_SLICE);

                    int bites = state.getValue(CakeBlock.BITES);
                    level.gameEvent(player, GameEvent.EAT, pos);

                    if (bites < 6) {
                        level.setBlock(pos, state.setValue(CakeBlock.BITES, bites + 1), 3);
                    } else {
                        level.removeBlock(pos, false);
                        level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, BAItems.GOLDEN_STRAWBERRIES.get(), Potions.REGENERATION);
    }

    @SubscribeEvent
    public static void vanillaFoodEffects(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntity();
        Item food = event.getItem().getItem();
        FoodProperties vanillaFoodChanges = BAItems.VANILLA_EFFECTS.get(food);
        if (vanillaFoodChanges != null) {
            for (FoodProperties.PossibleEffect effect : vanillaFoodChanges.effects()) {
                entity.addEffect(effect.effect());
            }
        }
    }

    @SubscribeEvent
    public static void addTooltipToVanillaFoods(ItemTooltipEvent event) {
        Item food = event.getItemStack().getItem();
        FoodProperties vanillaFoodChanges = BAItems.VANILLA_EFFECTS.get(food);
        if (vanillaFoodChanges != null) {
            List<Component> tooltip = event.getToolTip();
            for (FoodProperties.PossibleEffect effect : vanillaFoodChanges.effects()) {
                MobEffectInstance effectInstance = effect.effect();
                MutableComponent effectText = Component.translatable(effectInstance.getDescriptionId());
                Player player = event.getEntity();
                if (effectInstance.getDuration() > 20) {
                    effectText = Component.translatable("potion.withDuration", effectText, MobEffectUtil.formatDuration(effectInstance, 1, player == null ? 20 : player.level().tickRateManager().tickrate()));
                }
                tooltip.add(effectText.withStyle(effectInstance.getEffect().value().getCategory().getTooltipFormatting()));
            }
        }
    }

    @SubscribeEvent
    public static void addFoodTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        FoodProperties food = stack.getFoodProperties(event.getEntity());
        if (food == null) return;

        List<Component> tooltip = event.getToolTip();
        if (!(food.nutrition() <= 0)) {
            tooltip.add(1, Component.empty());
            tooltip.add(2, Component.empty());
        }
    }

    public static float getBoatFriction(Boat boat, float v) {
        for (var passenger : boat.getPassengers()) {
            if (passenger instanceof LivingEntity entity && entity.hasEffect(BAEffects.AGILITY)) {
                return Math.max(0.98f, v);
            }
        }
        return v;
    }

    @SubscribeEvent
    public static void onBeeJoin(EntityJoinLevelEvent join) {
        if (join.getEntity() instanceof Bee bee) {
            bee.getGoalSelector().addGoal(3, new BeePollinateFruitGoal(bee));
            bee.getGoalSelector().addGoal(4, new BeeMoveToFruitBushGoal(bee));
        }
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((p_329705_, p_329706_) -> {
            event.register((stack, index) -> index == 0 ? DyedItemColor.getOrDefault(stack, -1) : -1, BAItems.MACARON);
            return 0xFFFFFFFF;
        }, BAItems.MACARON.value());
    }
}