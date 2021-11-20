package me.gleep.oreganized.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SilverIngot extends ItemBase {

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (!(entity instanceof Player)) return;
        CompoundTag nbt = new CompoundTag();
        Player player = (Player) entity;
        if (stack.getOrCreateTag().getBoolean("Shine")) {
            long tick = stack.getOrCreateTag().getLong("t");

            if (entity.level.getGameTime() >= tick + 40) {
                nbt.putLong("t", 0);
                nbt.putBoolean("Shine", false);
                stack.setTag(nbt);
            }
        }
    }


}
