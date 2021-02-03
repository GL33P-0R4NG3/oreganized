package me.gleep.oreganized.events;

import com.mojang.util.QueueLogAppender;
import io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.blocks.Cauldron;
import me.gleep.oreganized.util.ModDamageSource;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.Blocks;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    static final HashMap<String, ?> FIELDS = new HashMap<>();
    //@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onToolBreakEvent(final PlayerDestroyItemEvent event) {
        ItemStack sword = event.getOriginal();
        PlayerEntity pl = event.getPlayer();
        ItemStack item;

        if (sword.getItem().equals(RegistryHandler.SILVER_TINTED_DIAMOND_SWORD.get())) {
            item = new ItemStack(Items.DIAMOND_SWORD);
        } else if (sword.getItem().equals(RegistryHandler.SILVER_TINTED_GOLDEN_SWORD.get())) {
            item = new ItemStack(Items.GOLDEN_SWORD);
        } else if (sword.getItem().equals(RegistryHandler.SILVER_TINTED_NETHERITE_SWORD.get())) {
            item = new ItemStack(Items.NETHERITE_SWORD);
        } else {
            return;
        }

        item.setTag(sword.getTag());
        //item.getOrCreateTag().putBoolean("Tossed", true);
        item.setDamage(sword.getMaxDamage());
        for (int i = 0; i <= 36; ++i) {
            ItemStack stack = pl.inventory.mainInventory.get(i);
            if (stack == ItemStack.EMPTY && i != pl.inventory.getSlotFor(sword)) {
                pl.inventory.setInventorySlotContents(i, item);
                return;
            }
        }
        
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
            ITag<Fluid> tag = FluidTags.getCollection().getTagByID(new ResourceLocation("oreganized:lead"));
            if (entity.isEntityInsideOpaqueBlock()) {

                entity.handleFluidAcceleration(tag, 1.0d);
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
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof MonsterEntity) {
            MonsterEntity monster = (MonsterEntity) event.getEntity();
            if (monster.isEntityUndead()) {
                try {
                    final Set<PrioritizedGoal> set = ObfuscationReflectionHelper.getPrivateValue(
                            GoalSelector.class, monster.targetSelector, "goals");

                    for (PrioritizedGoal g : set) {
                        if (g.getGoal() instanceof NearestAttackableTargetGoal<?>) {
                            NearestAttackableTargetGoal<?> goal = (NearestAttackableTargetGoal<?>) g.getGoal();

                            final Class<?> targetClass = ObfuscationReflectionHelper.getPrivateValue(
                                    NearestAttackableTargetGoal.class, goal, "targetClass");
                            if (targetClass == PlayerEntity.class || targetClass == ServerPlayerEntity.class) {
                                final EntityPredicate targetEntitySelector = ObfuscationReflectionHelper.getPrivateValue(
                                        NearestAttackableTargetGoal.class, goal, "targetEntitySelector");

                                final Predicate<LivingEntity> customPredicate = ObfuscationReflectionHelper.getPrivateValue(
                                        EntityPredicate.class, targetEntitySelector, "customPredicate");

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
                } catch (NullPointerException ignored) {
                    if (event.getWorld().getServer() != null) {
                        event.getWorld().getServer().sendMessage(ITextComponent.getTextComponentOrEmpty("An error occurred during entity loading"), UUID.randomUUID());
                    }
                }
            }
        }
    }

    /*@SubscribeEvent
    public static void onItemDrop(ItemTossEvent event) {
        ItemStack stack = event.getEntityItem().getItem();
        if (stack.getTag() == null) return;
        if (stack.getTag().getBoolean("Tossed")) {
            PlayerEntity player = event.getPlayer();
            boolean notExist = player.inventory.getSlotFor(stack) == -1;
            stack.getTag().remove("Tossed");
            if (notExist) {
                if (event.isCancelable()) {
                    event.setResult(Event.Result.ALLOW);
                    event.setCanceled(true);
                }
                player.setHeldItem(Hand.MAIN_HAND, stack);
            } else {
                player.sendMessage(ITextComponent.getTextComponentOrEmpty("\""+ stack.toString() + "\" already exists :/"), UUID.randomUUID());
            }
        }
    }*/

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
