package net.ashstarcrash.bonappetit.core.registry;

import net.ashstarcrash.bonappetit.BAConfig;
import net.ashstarcrash.bonappetit.BonAppetit;
import net.ashstarcrash.bonappetit.core.content.entity.goal.BeeMoveToFruitBushGoal;
import net.ashstarcrash.bonappetit.core.content.entity.goal.BeePollinateFruitGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
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
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.*;

@Mod(value = BonAppetit.ID) @EventBusSubscriber(modid = BonAppetit.ID)
public class BAEvents {
    private static final List<CherryEcho> ECHO_QUEUE = new ArrayList<>();
    private static final Map<UUID, Integer> MELLOW_STILL_TICKS = new HashMap<>();
    private static final Map<UUID, Integer> TWIN_STRIKE_COOLDOWN = new HashMap<>();

    private record CherryEcho(LivingEntity attacker, LivingEntity victim, float damage, int timer) {}

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        UUID uuid = player.getUUID();

        if (player.hasEffect(BAEffects.MELLOW)) {
            boolean isStill = player.getDeltaMovement().horizontalDistanceSqr() < 0.001;
            if (isStill && player.onGround()) {
                int ticks = MELLOW_STILL_TICKS.merge(uuid, 1, Integer::sum);
            } else {
                MELLOW_STILL_TICKS.put(uuid, 0);
            }
        } else {
            MELLOW_STILL_TICKS.remove(uuid);
        }

        if (TWIN_STRIKE_COOLDOWN.containsKey(uuid)) {
            int time = TWIN_STRIKE_COOLDOWN.get(uuid);
            if (time > 0) TWIN_STRIKE_COOLDOWN.put(uuid, time - 1);
        }
    }

    @SubscribeEvent
    public static void handleFruitCombat(LivingIncomingDamageEvent event) {
        if (event.getEntity().level().isClientSide) return;
        LivingEntity victim = event.getEntity();
        float damage = event.getAmount();

        MobEffectInstance mangoEffect = victim.getEffect(BAEffects.INSATIABLE);
        if (mangoEffect != null && victim instanceof Player player) {
            int mangoAmplifier = mangoEffect.getAmplifier();
            if (player.getFoodData().getFoodLevel() >= 6) {
                event.setAmount(damage * (0.7f - (mangoAmplifier * 0.1f)));
                player.getFoodData().addExhaustion(1.0f);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.5f, 1.3f);
            }
        }

        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            UUID attackerUUID = attacker.getUUID();

            MobEffectInstance cherryEffect = attacker.getEffect(BAEffects.TWIN_STRIKE);
            if (cherryEffect != null && !event.getSource().is(BADamageTypes.TWIN_HIT)) {
                int cherryAmplifier = cherryEffect.getAmplifier();

                float charge = (attacker instanceof Player p) ? p.getAttackStrengthScale(0.5f) : 1.0f;
                int cooldown = TWIN_STRIKE_COOLDOWN.getOrDefault(attackerUUID, 0);

                if (charge >= 0.9f && cooldown <= 0) {
                    ECHO_QUEUE.add(new CherryEcho(attacker, victim, (float)((damage * BAConfig.CHERRY_EFFECT_INITIAL_MULTI.get()) + (cherryAmplifier * BAConfig.CHERRY_EFFECT_ADDITIVE_MULTI.get())), 5));
                    TWIN_STRIKE_COOLDOWN.put(attackerUUID, 5);

                    attacker.level().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                            SoundEvents.CHERRY_SAPLING_PLACE, SoundSource.PLAYERS, 0.8f, 0.5f);
                }
            }

            MobEffectInstance pearEffect = attacker.getEffect(BAEffects.MELLOW);
            if (pearEffect != null && attacker instanceof Player player) {
                int pearAmplifier = pearEffect.getAmplifier();
                int stillTicks = MELLOW_STILL_TICKS.getOrDefault(attackerUUID, 0);
                if (stillTicks > 20) {
                    float bonus = Math.min((float)stillTicks / 20f, (5f + pearAmplifier * 2.5f));
                    event.setAmount(damage + bonus);
                    MELLOW_STILL_TICKS.put(attackerUUID, 0);
                }
            }

            MobEffectInstance pomegranateEffect = attacker.getEffect(BAEffects.PROLIFERATE);
            if (pomegranateEffect != null) {
                int pomegranateAmplifier = pomegranateEffect.getAmplifier();
                MobEffectInstance seeded = victim.getEffect(BAEffects.SEEDED);
                int stacks = (seeded == null) ? 0 : seeded.getAmplifier() + 1;

                if (stacks >= 3) {
                    victim.removeEffect(BAEffects.SEEDED);
                    victim.hurt(victim.level().damageSources().magic(), 4.0f + (pomegranateAmplifier * 1.5f));
                    victim.level().playSound(null, victim.getX(), victim.getY(), victim.getZ(),
                            SoundEvents.CHERRY_WOOD_BREAK, SoundSource.HOSTILE, 1.5f, 0.8f);
                    if (victim.level() instanceof ServerLevel sl) {
                        sl.sendParticles(ParticleTypes.CRIMSON_SPORE, victim.getX(), victim.getY() + 1, victim.getZ(), 30, 0.3, 0.3, 0.3, 0.1);
                        sl.sendParticles(ParticleTypes.EXPLOSION, victim.getX(), victim.getY() + 1, victim.getZ(), 1, 0, 0, 0, 0);
                    }
                } else {
                    victim.addEffect(new MobEffectInstance(BAEffects.SEEDED, 100, stacks));
                    victim.level().playSound(null, victim.getX(), victim.getY(), victim.getZ(),
                            SoundEvents.CHERRY_SAPLING_STEP, SoundSource.PLAYERS, 0.5f, 1.2f);
                }
            }
        }
    }

    private static final WeakHashMap<Player, Long> EXPOSURE_COOLDOWN = new WeakHashMap<>();
    private static final WeakHashMap<Player, Long> SNEAK_START_TIME = new WeakHashMap<>();

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            if (!player.isCrouching() || player.isSprinting()) {
                SNEAK_START_TIME.remove(player);
            } else {
                SNEAK_START_TIME.putIfAbsent(player, player.level().getGameTime());
            }

            if (!player.level().isClientSide && isHidden(player) && player.level().getGameTime() % 20 == 0) {
                var enemies = player.level().getEntitiesOfClass(Mob.class,
                        player.getBoundingBox().inflate(4.0D, 2.0D, 4.0D),
                        mob -> (mob instanceof net.minecraft.world.entity.monster.Monster || mob instanceof net.minecraft.world.entity.monster.Slime));
                if (enemies.size() >= 5) {
                    grantAdvancement(player, "slip_under_the_door");
                }
            }
        }

        if (event.getEntity() instanceof Mob mob && mob.getTarget() instanceof Player player) {
            if (isHidden(player)) {
                mob.setTarget(null);
                mob.setLastHurtByMob(null);
                if (mob.getBrain() != null) {
                    mob.getBrain().eraseMemory(net.minecraft.world.entity.ai.memory.MemoryModuleType.ATTACK_TARGET);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMobTarget(LivingChangeTargetEvent event) {
        if (event.getNewAboutToBeSetTarget() instanceof Player player && isHidden(player)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player && isHidden(player)) {
            if (event.getSource().getEntity() instanceof Mob) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onShadowStrike(LivingDamageEvent.Pre event) {
        if (event.getSource().getDirectEntity() instanceof Player attacker) {
            MobEffectInstance effect = null;
            for (var instance : attacker.getActiveEffects()) {
                if (instance.getEffect().getRegisteredName().equals("bonappetit:obscurity")) {
                    effect = instance;
                    break;
                }
            }

            if (effect != null && isHidden(attacker)) {
                float multiplier = 1.5f + (effect.getAmplifier() * 0.5f);
                event.setNewDamage(event.getNewDamage() * multiplier);

                if (attacker.level() instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.SMOKE, event.getEntity().getX(), event.getEntity().getY(0.5), event.getEntity().getZ(), 10, 0.2, 0.2, 0.2, 0.05);
                    server.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0f, 0.5f);
                }

                int cooldownTicks = Math.max(20, 200 - (effect.getAmplifier() * 40));
                grantAdvancement(attacker, "shadow_strike");
                EXPOSURE_COOLDOWN.put(attacker, attacker.level().getGameTime() - (200 - cooldownTicks));
            } else if (effect != null) {
                EXPOSURE_COOLDOWN.put(attacker, attacker.level().getGameTime());
            }
        }
    }

    public static boolean isHidden(Player player) {
        boolean hasObscurity = false;
        for (var effectInstance : player.getActiveEffects()) {
            if (effectInstance.getEffect().getRegisteredName().equals("bonappetit:obscurity")) {
                hasObscurity = true;
                break;
            }
        }

        long currentTime = player.level().getGameTime();
        long lastAttack = EXPOSURE_COOLDOWN.getOrDefault(player, 0L);
        long sneakStart = SNEAK_START_TIME.getOrDefault(player, -1L);

        boolean cooledDown = (currentTime - lastAttack >= 200);
        boolean sneakPrepped = sneakStart != -1L && (currentTime - sneakStart >= 100);

        return hasObscurity && cooledDown && player.isCrouching() && !player.isSprinting() && sneakPrepped;
    }

    private static void grantAdvancement(Player player, String id) {
        if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
            var advancementHolder = serverPlayer.getServer().getAdvancements().get(
                    net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("bonappetit", "adventure/" + id)
            );
            if (advancementHolder != null) {
                serverPlayer.getAdvancements().award(advancementHolder, "code_trigger");
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (ECHO_QUEUE.isEmpty()) return;
        for (int i = ECHO_QUEUE.size() - 1; i >= 0; i--) {
            CherryEcho echo = ECHO_QUEUE.get(i);
            if (echo.timer <= 0) {
                if (echo.victim.isAlive() && !echo.victim.isRemoved()) {
                    echo.victim.invulnerableTime = 0;
                    echo.victim.hurtDuration = 0;
                    echo.victim.hurt(echo.victim.level().damageSources().source(BADamageTypes.TWIN_HIT, echo.attacker), echo.damage);
                    echo.victim.level().playSound(null, echo.victim.getX(), echo.victim.getY(), echo.victim.getZ(),
                            SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.6f, 1.3f);
                    if (echo.victim.level() instanceof ServerLevel sl) {
                        sl.sendParticles(ParticleTypes.SWEEP_ATTACK, echo.victim.getX(), echo.victim.getY() + 1, echo.victim.getZ(), 1, 0, 0, 0, 0);
                    }
                }
                ECHO_QUEUE.remove(i);
            } else {
                ECHO_QUEUE.set(i, new CherryEcho(echo.attacker, echo.victim, echo.damage, echo.timer - 1));
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
    public static void vanillaFoodEffects(net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish event) {
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