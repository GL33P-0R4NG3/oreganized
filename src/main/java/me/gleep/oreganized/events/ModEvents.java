package me.gleep.oreganized.events;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.armors.STABase;
import me.gleep.oreganized.blocks.Cauldron;
import me.gleep.oreganized.blocks.ExposerBlock;
import me.gleep.oreganized.tools.STSBase;
import me.gleep.oreganized.util.ModDamageSource;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.*;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    /*@SubscribeEvent
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
    }*/

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
                    world.setBlockState(pos, RegistryHandler.CAULDRON.get().getDefaultState().with(Cauldron.LEVEL, 3));
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
        if (event.getEntity().isLiving()) {
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
        if (event.getEntity().isLiving()) {
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
            }
        }
    }

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

    @SubscribeEvent
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
    }

    @SubscribeEvent
    public static void onLivingHurt(final LivingHurtEvent event) {
        /*if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getTrueSource();
            if (playerEntity.getHeldItem(playerEntity.getActiveHand()).getItem() instanceof STSBase) {
                if (playerEntity.getEntityWorld().isRemote()) {
                    double xSpeed = (playerEntity.getRNG().nextInt() % 2) > 0 ? -0.06D : 0.06D;
                    double zSpeed = (playerEntity.getRNG().nextInt() % 2) > 0 ? -0.06D : 0.06D;
                    Direction facing = playerEntity.getHorizontalFacing();
                    switch (facing) {
                        case NORTH:
                            zSpeed = -0.1D;
                            break;
                        case SOUTH:
                            zSpeed = 0.1D;
                            break;
                        case WEST:
                            xSpeed = -0.1D;
                            break;
                        case EAST:
                            xSpeed = 0.1D;
                            break;
                    }
                    playerEntity.getEntityWorld().addParticle(ParticleTypes.END_ROD, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), xSpeed, 0.05D, zSpeed);
                }
            }
        }*/
        //int parts = 0;
        if (event.getSource().getTrueSource() != null) {
            if (event.getSource().getTrueSource() instanceof MonsterEntity) {
                MonsterEntity monster = (MonsterEntity) event.getSource().getTrueSource();
                for (ItemStack stack : event.getEntityLiving().getArmorInventoryList()) {
                    if (stack.getItem() instanceof STABase) {
                        //parts++;
                        ((STABase)stack.getItem()).decreaseDurabilty(stack, event.getEntityLiving());
                    }
                }

                if (monster.isEntityUndead()) {
                    monster.setAttackTarget(null);
                    monster.setRevengeTarget(null);
                }
            }
        }
    }

    /*@SubscribeEvent
    public static void onFluidFlow(BlockEvent.CreateFluidSourceEvent event) {
        IWorldReader world = event.getWorld();
        BlockPos pos = event.getPos();
        BlockPos newPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
        BlockState blockState = event.getState();
        IChunk chunk = world.getChunk(pos);

        if (blockState.get(LeadFluid.FALLING) && chunk.getFluidState(pos).equals(RegistryHandler.LEAD_FLUID_FLOW.get().getDefaultState())) {
            chunk.setBlockState(newPos, Blocks.AIR.getDefaultState(), false);
            chunk.setBlockState(pos, RegistryHandler.LEAD_FLUID.get().getDefaultState().getBlockState(), true);
        }
    }*/

}
