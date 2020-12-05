package me.gleep.oreexpansion.util;

import me.gleep.oreexpansion.blocks.tileentities.SilverBlockTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class NBTHelper {
    public static CompoundNBT toNBT(Object o) {
        if (o instanceof ItemStack) {
            return writeItemStack((ItemStack)o);
        }

        if (o instanceof SilverBlockTileEntity) {
            return writeSilverBlock((SilverBlockTileEntity)o);
        }

        return null;
    }

    private static CompoundNBT writeSilverBlock(SilverBlockTileEntity o){
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("x", o.x);
        compound.putInt("y", o.y);
        compound.putInt("z", o.z);
        compound.putString("interrupt", o.entity.toString());
        return compound;
    }

    private static CompoundNBT writeItemStack(ItemStack i){
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("count", i.getCount());
        nbt.putString("item", i.getItem().getRegistryName().toString());
        return nbt;
    }
}
