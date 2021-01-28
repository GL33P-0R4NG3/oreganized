package me.gleep.oreganized.events;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.blocks.Cauldron;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {
    //@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onToolBreakEvent(final PlayerDestroyItemEvent event) {
        ItemStack sword = event.getOriginal();
        PlayerEntity pl = event.getPlayer();
        ItemStack item;

        //ListNBT ench = sword.getEnchantmentTagList();

        //enchanments.put(sword.getEnchantmentTagList(), "");

        if (sword.getItem().equals(RegistryHandler.SILVER_TINTED_DIAMOND_SWORD.get())) {
            item = new ItemStack(Items.DIAMOND_SWORD/*.getItem()*/);
        } else if (sword.getItem().equals(RegistryHandler.SILVER_TINTED_GOLDEN_SWORD.get())) {
            item = new ItemStack(Items.GOLDEN_SWORD/*.getItem()*/);
        } else if (sword.getItem().equals(RegistryHandler.SILVER_TINTED_NETHERITE_SWORD.get())) {
            item = new ItemStack(Items.NETHERITE_SWORD/*.getItem()*/);
        } else {
            return;
        }

        item.setTag(sword.getTag());
        item.setDamage(sword.getMaxDamage());
        if (!pl.addItemStackToInventory(item)) {
            pl.dropItem(item, false);
        }
    }

    @SubscribeEvent
    public static void onTargetSet(LivingSetAttackTargetEvent event) {
        if (event.getTarget() instanceof PlayerEntity) {
            if (event.getEntityLiving().isEntityUndead()) {
                PlayerEntity player = (PlayerEntity) event.getTarget();
                for (ItemStack stack : player.inventory.armorInventory) {
                    if (ItemTags.getCollection().getTagByID(new ResourceLocation(Oreganized.MOD_ID + ":silver_tinted_items")).contains(stack.getItem())) {
                        event.getEntityLiving().setLastAttackedEntity(null);
                    }
                }
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
            if (entity.getBlockState().getBlock().equals(RegistryHandler.LEAD_FLUID_BLOCK.get())) {
                if (entity.attackable()) {
                    entity.attackEntityFrom(DamageSource.MAGIC, 3F);
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
