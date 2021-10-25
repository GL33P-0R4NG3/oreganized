package me.gleep.oreganized.util;

import me.gleep.oreganized.items.SilverMirror;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class NBTHelper {
    public static CompoundTag toNBT(Object o) {
        if (o instanceof ItemStack) {
            return writeItemStack((ItemStack)o);
        }

        /*if (o instanceof SilverBlockTileEntity) {
            return writeSilverBlock((SilverBlockTileEntity)o);
        }

        if (o instanceof SilverMirror) {
            return writeSilverMirror((SilverMirror)o);
        }*/

        return null;
    }

    /*private static CompoundTag writeSilverMirror(SilverMirror o) {
        CompoundTag compound = new CompoundTag();
        return compound;
    }

    private static CompoundTag writeSilverBlock(SilverBlockTileEntity o){
        CompoundTag compound = new CompoundTag();
        compound.putInt("x", o.x);
        compound.putInt("y", o.y);
        compound.putInt("z", o.z);
        compound.putString("interrupt", o.entity.toString());
        compound.putInt("distance", o.distance);
        return compound;
    }*/

    private static CompoundTag writeItemStack(ItemStack i){
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("count", i.getCount());
        nbt.putString("item", i.getItem().getRegistryName().toString());
        return nbt;
    }
}
