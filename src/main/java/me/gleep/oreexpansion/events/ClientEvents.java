package me.gleep.oreexpansion.events;

import me.gleep.oreexpansion.OreExpansion;
import me.gleep.oreexpansion.items.ItemBase;
import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockStateProvider;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.fixes.MinecartEntityTypes;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

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

        /*ListNBT newEnch = item.getEnchantmentTagList();
        newEnch.addAll(ench);*/
        //item.setTag(ench.getCompound());

        item.setTag(sword.getTag());
        item.setDamage(sword.getMaxDamage());
        pl.dropItem(item, false);
    }

    /*@SubscribeEvent
    public static void onPlayeRightClick (BlockEvent.EntityPlaceEvent event) {
        BlockState block = event.getPlacedBlock();
        if (block.equals(Blocks.CAULDRON.getDefaultState())) {
            if (!event.getWorld().isRemote()) {
                event.getWorld().removeBlock(event.getPos(), false);
                event.getWorld().setBlockState(event.getPos(), RegistryHandler.CAULDRON.get().getDefaultState(), 2);
            }
        }
    }*/

}
