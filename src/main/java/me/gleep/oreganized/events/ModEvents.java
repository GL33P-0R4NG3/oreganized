package me.gleep.oreganized.events;

import com.mojang.blaze3d.systems.RenderSystem;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.armors.STABase;
import me.gleep.oreganized.blocks.ModCauldron;
import me.gleep.oreganized.tools.STSBase;
import me.gleep.oreganized.util.ModDamageSource;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    @SubscribeEvent
    public static void onToolBreakEvent(final PlayerDestroyItemEvent event) {
        ItemStack stack = event.getOriginal();
        PlayerEntity pl = event.getPlayer();
        ItemStack item = ItemStack.EMPTY;

        if (stack.getItem() instanceof STSBase) {
            int count = (int) Math.round(((double) stack.getOrCreateTag().getInt("TintedDamage") / (double) STSBase.MAX_TINT_DURABILITY) * 9D);
            item = new ItemStack(RegistryHandler.SILVER_NUGGET.get(), count);
        } else if (stack.getItem() instanceof STABase) {
            int count = (int) Math.round(((double) stack.getOrCreateTag().getInt("TintedDamage") / (double) STABase.MAX_TINT_DURABILITY) * 9D);
            item = new ItemStack(RegistryHandler.SILVER_NUGGET.get(), count);
        }

        pl.dropItem(item, true);
    }

    @SubscribeEvent
    public static void onPlayerRightClick(final PlayerInteractEvent.RightClickBlock event) {
        ItemStack item = event.getItemStack();
        BlockPos pos = event.getPos();
        World world = event.getWorld();

        if (world.getBlockState(pos).equals(Blocks.CAULDRON.getDefaultState())) {
            if (item.getItem().equals(RegistryHandler.LEAD_BLOCK_ITEM.get())) {
                if (!world.isRemote()) {
                    world.removeBlock(pos, false);
                    world.setBlockState(pos, RegistryHandler.CAULDRON.get().getDefaultState());
                    if (!event.getPlayer().isCreative()) item.shrink(1);
                    world.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    event.getPlayer().addStat(Stats.USE_CAULDRON);
                }

                if (event.isCancelable()) {
                    event.setCancellationResult(ActionResultType.func_233537_a_(world.isRemote()));
                    event.setCanceled(true);
                }
            } else if (item.getItem().equals(RegistryHandler.LEAD_BUCKET.get())) {
                if (!world.isRemote()) {
                    world.removeBlock(pos, false);
                    world.setBlockState(pos, RegistryHandler.CAULDRON.get().getDefaultState().with(ModCauldron.LEVEL, 3));
                    if (!event.getPlayer().isCreative()) event.getPlayer().setHeldItem(Hand.MAIN_HAND, new ItemStack(Items.BUCKET, 1));
                    world.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    event.getPlayer().addStat(Stats.USE_CAULDRON);
                }

                if (event.isCancelable()) {
                    event.setCancellationResult(ActionResultType.func_233537_a_(world.isRemote()));
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

    @SubscribeEvent
    public static void onEntityUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = event.getEntityLiving();
            ITag<Fluid> tag = FluidTags.getCollection().getTagByID(new ResourceLocation(Oreganized.MOD_ID + ":lead"));

            if (entity.handleFluidAcceleration(tag, 0.009D)) {
                entity.setFire(10);
                entity.attackEntityFrom(ModDamageSource.MOLTEN_LEAD, 3.0F);

                Vector3d travelVector = new Vector3d((double)entity.moveStrafing, (double)entity.moveVertical, (double)entity.moveForward);
                double d0 = 0.08D;
                ModifiableAttributeInstance gravity = entity.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
                boolean flag = entity.getMotion().y <= 0.0D;
                d0 = gravity.getValue();

                double d7 = entity.getPosY();
                entity.moveRelative(0.01F, travelVector);
                entity.move(MoverType.SELF, entity.getMotion());
                if (entity.func_233571_b_(tag) <= entity.func_233579_cu_()) {
                    entity.setMotion(entity.getMotion().mul(0.2D, (double)0.4F, 0.2D));
                    Vector3d vector3d3 = entity.func_233626_a_(d0, flag, entity.getMotion());
                    entity.setMotion(vector3d3);
                } else {
                    entity.setMotion(entity.getMotion().scale(0.2D));
                }

                if (!entity.hasNoGravity()) {
                    entity.setMotion(entity.getMotion().add(0.0D, -d0 / 4.0D, 0.0D));
                }

                Vector3d vector3d4 = entity.getMotion();
                if (entity.collidedHorizontally && entity.isOffsetPositionInLiquid(vector3d4.x, vector3d4.y + (double)0.2F - entity.getPosY() + d7, vector3d4.z)) {
                    entity.setMotion(vector3d4.x, (double)0.3F, vector3d4.z);
                }
            }/* else if (entity.isPotionActive(RegistryHandler.DAWN_SHINE.get())) {
                Minecraft.getInstance().particles.emitParticleAtEntity(entity, RegistryHandler.DAWN_SHINE_PARTICLE.get(), 10);
            }*/
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onDawnShineEffect(PotionEvent.PotionAddedEvent event) {
        if (event.getPotionEffect().getPotion().equals(RegistryHandler.DAWN_SHINE.get())) {
            Minecraft.getInstance().particles.addParticleEmitter(event.getEntityLiving(), RegistryHandler.DAWN_SHINE_PARTICLE.get());
        }
    }

    /*@SubscribeEvent
    public static void onLeadNuggetImpact(ProjectileImpactEvent event) {
        if (event.getEntity() instanceof LeadNuggetEntity) {
            event.setCanceled(true);
        }
    }*/

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void getFogDensity(EntityViewRenderEvent.FogDensity event) {
        ActiveRenderInfo info = event.getInfo();
        FluidState fluidState = info.getFluidState();
        if (fluidState.isEmpty()) return;
        Fluid fluid = fluidState.getFluid();

        if (fluid.isEquivalentTo(RegistryHandler.LEAD_FLUID.get())) {
            RenderSystem.disableCull();
            event.setDensity(1.4F);
            event.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void getFogColor(EntityViewRenderEvent.FogColors event) {
        ActiveRenderInfo info = event.getInfo();
        FluidState fluidState = info.getFluidState();
        if (fluidState.isEmpty())
            return;
        Fluid fluid = fluidState.getFluid();

        if (fluid.isEquivalentTo(RegistryHandler.LEAD_FLUID.get())) {
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

    @SubscribeEvent
    public static void onLivingHurt(final LivingHurtEvent event) {
        int parts = 0;
        if (event.getSource().getTrueSource() instanceof LivingEntity) {
            for (ItemStack stack : event.getEntityLiving().getArmorInventoryList()) {
                if (stack.getItem() instanceof STABase) {
                    parts++;
                    ((STABase)stack.getItem()).decreaseDurabilty(stack, event.getEntityLiving());
                }
            }
            long random = Math.round(Math.random() * 100.0F);

            if (random <= (16 * parts)) {
                ((LivingEntity) event.getSource().getTrueSource()).addPotionEffect(getSilverShine());
            }

            if (event.getSource().getTrueSource() instanceof MonsterEntity) {
                MonsterEntity monster = (MonsterEntity) event.getSource().getTrueSource();

                if (monster.isEntityUndead()) {
                    monster.setAttackTarget(null);
                    monster.setRevengeTarget(null);
                }
            }
        }
    }

    public static EffectInstance getSilverShine() {
        return new EffectInstance(RegistryHandler.DAWN_SHINE.get(), 400, 1);
    }

}
