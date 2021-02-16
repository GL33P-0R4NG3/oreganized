package me.gleep.oreganized.events;

import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.armors.STABase;
import me.gleep.oreganized.blocks.Cauldron;
import me.gleep.oreganized.tools.STSBase;
import me.gleep.oreganized.util.ModDamageSource;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.Blocks;
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
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.*;
import java.util.function.Predicate;

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

    @SubscribeEvent
    public static void onEntityUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity().isLiving()) {
            LivingEntity entity = event.getEntityLiving();
            ITag<Fluid> tag = FluidTags.getCollection().getTagByID(new ResourceLocation(Oreganized.MOD_ID + ":lead"));

            if (entity.handleFluidAcceleration(tag, 0.009D)) {
                entity.setSwimming(true);
                entity.setFire(10);
                entity.attackEntityFrom(ModDamageSource.MOLTEN_LEAD, 3.0F);

                if (entity.areEyesInFluid(tag) && entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoin(final EntityJoinWorldEvent event) {
        /*if (event.getEntity() instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) event.getEntity();
            try {
                final Object2DoubleMap<ITag<Fluid>> eyesFluidLevel = ObfuscationReflectionHelper.getPrivateValue(
                        LivingEntity.class, living, "field_220892_d");
                Object2DoubleMap<ITag<Fluid>> fluid = new Object2DoubleArrayMap<>(eyesFluidLevel.size() + 1);

                for (ITag<Fluid> tag : eyesFluidLevel.keySet()) {
                    fluid.put(tag, eyesFluidLevel.getDouble(tag));
                }

                //fluid.put();

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
        }*/

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
        //int parts = 0;
        if (event.getSource().getTrueSource() != null) {
            if (event.getSource().getTrueSource().isLiving()) {
                LivingEntity livingEntity = (LivingEntity) event.getSource().getTrueSource();
                for (ItemStack stack : event.getEntityLiving().getArmorInventoryList()) {
                    if (stack.getItem() instanceof STABase) {
                        //parts++;
                        ((STABase)stack.getItem()).decreaseDurabilty(stack, event.getEntityLiving());
                    }
                }

                if (livingEntity.isEntityUndead()) {
                    livingEntity.setRevengeTarget(null);
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
