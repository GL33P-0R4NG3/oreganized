package me.gleep.oreganized.events;

import com.mojang.blaze3d.systems.RenderSystem;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.armors.STABase;
import me.gleep.oreganized.blocks.ModCauldron;
import me.gleep.oreganized.items.BushHammer;
import me.gleep.oreganized.tools.STSBase;
import me.gleep.oreganized.util.ModDamageSource;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    /**
     * Event to handle Silver Tintend Swords break
     */
    @SubscribeEvent
    public static void onToolBreakEvent(final PlayerDestroyItemEvent event) {
        ItemStack stack = event.getOriginal();
        Player pl = event.getPlayer();
        ItemStack item = ItemStack.EMPTY;

        if (stack.getItem() instanceof STSBase) {
            int count = (int) Math.round(((double) stack.getOrCreateTag().getInt("TintedDamage") / (double) STSBase.MAX_TINT_DURABILITY) * 9D);
            item = new ItemStack(RegistryHandler.SILVER_NUGGET.get(), count);
        } else if (stack.getItem() instanceof STABase) {
            int count = (int) Math.round(((double) stack.getOrCreateTag().getInt("TintedDamage") / (double) STABase.MAX_TINT_DURABILITY) * 9D);
            item = new ItemStack(RegistryHandler.SILVER_NUGGET.get(), count);
        }

        pl.drop(item, true);
    }

    /**
     * Event to handle Cauldron replacement
     */
    @SubscribeEvent
    public static void onPlayerRightClick(final PlayerInteractEvent.RightClickBlock event) {
        ItemStack item = event.getItemStack();
        BlockPos pos = event.getPos();
        Level level = event.getWorld();

        if (level.getBlockState(pos).equals(Blocks.CAULDRON.defaultBlockState())) {
            if (item.getItem().equals(RegistryHandler.LEAD_BLOCK_ITEM.get())) {
                if (!level.isClientSide()) {
                    level.removeBlock(pos, false);
                    level.setBlockAndUpdate(pos, RegistryHandler.CAULDRON.get().defaultBlockState());
                    if (!event.getPlayer().isCreative()) item.shrink(1);
                    level.playSound((Player) null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    event.getPlayer().awardStat(Stats.USE_CAULDRON);
                }

                if (event.isCancelable()) {
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                    event.setCanceled(true);
                }
            } else if (item.getItem().equals(RegistryHandler.LEAD_BUCKET.get())) {
                if (!level.isClientSide()) {
                    level.removeBlock(pos, false);
                    level.setBlockAndUpdate(pos, RegistryHandler.CAULDRON.get().defaultBlockState().setValue(ModCauldron.LEVEL, 3));
                    if (!event.getPlayer().isCreative()) event.getPlayer().setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET, 1));
                    level.playSound((Player) null, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
                    event.getPlayer().awardStat(Stats.USE_CAULDRON);
                }

                if (event.isCancelable()) {
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                    event.setCanceled(true);
                }
            }
        }
    }

    /*@SubscribeEvent
    public static void onLivingJump(final LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = event.getEntityLiving();
            ITag<Fluid> tag = FluidTags.getCollection().getTagByID(new ResourceLocation(Oreganized.MOD_ID + ":lead"));

            double d7 = entity.func_233571_b_(tag);
            double d8 = entity.func_233579_cu_();

            if (d7 > d8 && entity.handleFluidAcceleration(tag, 0.009D)) {
                entity.setMotion(entity.getMotion().add(0.0D, (double)0.04F * entity.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue(), 0.0D));
            }

        }
    }*/

    /**
     * Event to handle entities in mod fluid
     */
    @SubscribeEvent
    public static void onEntityUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = event.getEntityLiving();
            Tag<Fluid> tag = FluidTags.getAllTags().getTag(new ResourceLocation(Oreganized.MOD_ID + ":lead"));

            if (entity.updateFluidHeightAndDoFluidPushing(tag, 0.009D)) {
                entity.setSecondsOnFire(10);
                entity.hurt(ModDamageSource.MOLTEN_LEAD, 3.0F);

                Vec3 travelVector = new Vec3((double)entity.xxa, (double)entity.yya, (double)entity.zza);
                double d0 = 0.08D;
                AttributeInstance gravity = entity.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
                boolean flag = entity.getDeltaMovement().y <= 0.0D;
                d0 = gravity.getValue();

                double d7 = entity.getY();
                entity.moveRelative(0.01F, travelVector);
                entity.move(MoverType.SELF, entity.getDeltaMovement());
                if (entity.getFluidHeight(tag) <= entity.getFluidJumpThreshold()) {
                    entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.2D, (double)0.4F, 0.2D));
                    Vec3 vector3d3 = entity.getFluidFallingAdjustedMovement(d0, flag, entity.getDeltaMovement());
                    entity.setDeltaMovement(vector3d3);
                } else {
                    entity.setDeltaMovement(entity.getDeltaMovement().scale(0.2D));
                }

                if (!entity.isNoGravity()) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, -d0 / 4.0D, 0.0D));
                }

                Vec3 vector3d4 = entity.getDeltaMovement();
                if (entity.horizontalCollision && entity.isFree(vector3d4.x, vector3d4.y + (double)0.2F - entity.getY() + d7, vector3d4.z)) {
                    entity.setDeltaMovement(vector3d4.x, (double)0.3F, vector3d4.z);
                }
            }/* else if (entity.isPotionActive(RegistryHandler.DAWN_SHINE.get())) {
                Minecraft.getInstance().particles.emitParticleAtEntity(entity, RegistryHandler.DAWN_SHINE_PARTICLE.get(), 10);
            }*/
        }
    }

    /**
     * Event to handle block cracking and damaging BushHammer item
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onBlockDestroyed(BlockEvent.BreakEvent event) {
        LevelAccessor world = event.getWorld();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);
        ItemStack currentitem = event.getPlayer().getItemInHand(event.getPlayer().getUsedItemHand());

        if (currentitem.getItem().equals(RegistryHandler.BUSH_HAMMER.get())) {
            for (Block b : BushHammer.EFFECTIVE_ON.keySet()) {
                if (state.getBlock().equals(b) && !event.getPlayer().isCreative()) {
                    world.setBlock(pos, BushHammer.EFFECTIVE_ON.get(b).defaultBlockState(), 2);
                    currentitem.hurtAndBreak(1, event.getPlayer(), (player) -> {
                        player.broadcastBreakEvent(event.getPlayer().getUsedItemHand());
                    });
                    event.setCanceled(true);
                }
            }
        }

    }

    /*
    /**
     * Event to emit particles when entity gets DawnShine effect
    //
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onDawnShineEffect(PotionEvent.PotionAddedEvent event) {
        if (event.getPotionEffect().getPotion().equals(RegistryHandler.DAWN_SHINE.get())) {
            Minecraft.getInstance().particles.addParticleEmitter(event.getEntityLiving(), RegistryHandler.DAWN_SHINE_PARTICLE.get());
        }
    }
    */

    /*@SubscribeEvent
    public static void onLeadNuggetImpact(ProjectileImpactEvent event) {
        if (event.getEntity() instanceof LeadNuggetEntity) {
            event.setCanceled(true);
        }
    }*/

    /**
     * Event to change fluid fog density for rendering
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void getFogDensity(EntityViewRenderEvent.FogDensity event) {
        Camera info = event.getInfo();
        FluidState fluidState = info.getBlockAtCamera().getFluidState();
        if (fluidState.isEmpty()) return;
        Fluid fluid = fluidState.getType();

        if (fluid.isSame(RegistryHandler.LEAD_FLUID.get())) {
            RenderSystem.disableCull();
            event.setDensity(1.4F);
            event.setCanceled(true);
        }
    }

    /**
     * Event to change the fluid fog color for rendering
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void getFogColor(EntityViewRenderEvent.FogColors event) {
        Camera info = event.getInfo();
        FluidState fluidState = info.getBlockAtCamera().getFluidState();
        if (fluidState.isEmpty())
            return;
        Fluid fluid = fluidState.getType();

        if (fluid.isSame(RegistryHandler.LEAD_FLUID.get())) {
            event.setRed(57F / 256F);
            event.setGreen(57F / 256F);
            event.setBlue(95F / 256F);
        }
    }

    /*@SubscribeEvent
    public static void onEntityJoin(final EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof MonsterEntity) {
            MonsterEntity monster = (MonsterEntity) event.getEntity();
            if (monster.isEntityUndead()) {
                try {
                    final Set<PrioritizedGoal> goals = ObfuscationReflectionHelper.getPrivateValue(
                            GoalSelector.class, monster.targetSelector, "field_220892_d");

                    for (PrioritizedGoal g : goals) {
                        if (g.getGoal() instanceof NearestAttackableTargetGoal<?>) {
                            NearestAttackableTargetGoal<?> goal = (NearestAttackableTargetGoal<?>) g.getGoal();

                            final Class<?> targetClass = ObfuscationReflectionHelper.getPrivateValue(
                                    NearestAttackableTargetGoal.class, goal, "field_75307_b");
                            if (targetClass == PlayerEntity.class || targetClass == ServerPlayerEntity.class) {
                                final EntityPredicate targetEntitySelector = ObfuscationReflectionHelper.getPrivateValue(
                                        NearestAttackableTargetGoal.class, goal, "field_220779_d");

                                final Predicate<LivingEntity> customPredicate = ObfuscationReflectionHelper.getPrivateValue(
                                        EntityPredicate.class, targetEntitySelector, "field_221023_h");

                                Predicate<LivingEntity> entityPredicate =  new Predicate<LivingEntity>() {
                                    final Predicate<LivingEntity> predicate = customPredicate;
                                    @Override
                                    public boolean test(LivingEntity target) {
                                        for (ItemStack itemStack : target.getArmorInventoryList()) {
                                            if (ItemTags.getCollection().getTagByID(
                                                    new ResourceLocation("oreganized:silver_tinted_items"))
                                                    .contains(itemStack.getItem())) return false;
                                        }
                                        return predicate == null || predicate.test(target);
                                    }
                                };

                                targetEntitySelector.setCustomPredicate(entityPredicate);
                            }
                        }
                    }
                } catch (NullPointerException nullPointerException) {
                    if (event.getWorld().getServer() != null) {
                        event.getWorld().getServer().sendMessage(ITextComponent.getTextComponentOrEmpty("An error occurred during loading living entity\n" + nullPointerException.getMessage()), UUID.randomUUID());
                    }
                    Oreganized.LOGGER.error("An error occurred during living entity loading\n" + nullPointerException.getMessage());
                } catch (ObfuscationReflectionHelper.UnableToAccessFieldException fieldException) {
                    if (event.getWorld().getServer() != null) {
                        event.getWorld().getServer().sendMessage(ITextComponent.getTextComponentOrEmpty("Cannot find the field\n" + fieldException.getMessage()), UUID.randomUUID());
                    }
                    Oreganized.LOGGER.error("Cannot find the field\n" + fieldException.getMessage());
                }
            }
        }
    }*/

    /*@SubscribeEvent
    public static void onLivingHurt(final LivingHurtEvent event) {
        int parts = 0;
        if (event.getSource().getDirectEntity() instanceof LivingEntity) {
            for (ItemStack stack : event.getEntityLiving().getArmorSlots()) {
                if (stack.getItem() instanceof STABase) {
                    parts++;
                    ((STABase)stack.getItem()).decreaseDurabilty(stack, event.getEntityLiving());
                }
            }

            if (parts == 0) return;

            long random = Math.round(Math.random() * 100.0F);

            if (random <= (16L * parts)) {
                ((LivingEntity) event.getSource().getDirectEntity()).addEffect(getSilverShine());
            }

            if (event.getSource().getTrueSource() instanceof MonsterEntity) {
                MonsterEntity monster = (MonsterEntity) event.getSource().getTrueSource();

                if (monster.isEntityUndead()) {
                    monster.setAttackTarget(null);
                    monster.setRevengeTarget(null);
                }
            }
        }
    }*/

    /*public static MobEffectInstance getSilverShine() {
        return new MobEffectInstance(RegistryHandler.DAWN_SHINE.get(), 400, 1);
    }*/

}
