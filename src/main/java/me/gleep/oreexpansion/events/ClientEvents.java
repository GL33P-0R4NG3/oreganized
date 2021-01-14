package me.gleep.oreexpansion.events;

import me.gleep.oreexpansion.OreExpansion;
import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.datafix.fixes.MinecartEntityTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.sql.Ref;

@Mod.EventBusSubscriber(modid = OreExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {
    //@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onToolBreakEvent(PlayerDestroyItemEvent event) {
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
        if (!pl.inventory.addItemStackToInventory(item)) {
            pl.dropItem(item, false);
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event) {
        ItemStack item = event.getItemStack();
        BlockPos pos = event.getPos();
        World world = event.getWorld();

        if (world.getBlockState(pos).equals(Blocks.CAULDRON.getDefaultState()) && item.getItem().equals(RegistryHandler.LEAD_BLOCK_ITEM.get())) {
            if (!world.isRemote()) {
                world.removeBlock(pos, false);
                world.setBlockState(pos, RegistryHandler.CAULDRON.get().getDefaultState());
                if(!event.getPlayer().isCreative()) item.shrink(1);
            }
            if (event.isCancelable()) {
                event.setCancellationResult(ActionResultType.func_233537_a_(world.isRemote()));
                event.setCanceled(true);
            }
            world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity().isLiving()) {
            LivingEntity entity = event.getEntityLiving();
            if (entity.getBlockState().getBlock().equals(RegistryHandler.LEAD_FLUID_BLOCK.get())) {
                if (entity.attackable()) {
                    entity.attackEntityFrom(DamageSource.MAGIC, 3F);
                }
            }
        }
    }
}
