package me.gleep.oreexpansion.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.lwjgl.system.NonnullDefault;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class SilverMirror extends Item {
    public SilverMirror() {
        super(new Properties().group(ItemGroup.TOOLS)
                .maxStackSize(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        BlockPos pos = entityIn.getPosition();
        List<Entity> list = worldIn.getEntitiesInAABBexcluding(null,
                new AxisAlignedBB(pos.getX() + 8, pos.getY() + 8, pos.getZ() + 8,
                        pos.getX() - 8, pos.getY() - 8, pos.getZ() - 8),
                null);
        for (Entity e : list) {
            if (e.isLiving()) {
                LivingEntity living = (LivingEntity)e;
                if (living.isEntityUndead()) {
                    if (entityIn instanceof PlayerEntity) {
                        PlayerEntity player = (PlayerEntity) entityIn;
                        CompoundNBT nbt = player.inventory.getStackInSlot(itemSlot).getTag();
                        if (nbt == null) return;
                        double distance = living.getDistance(entityIn);
                        if (distance < 8) {
                            int dist = (int) Math.floor(distance / 2);
                            if (!(dist > 1))
                                dist = 1;
                            else if (dist > 3)
                                dist = 3;
                            nbt.putInt("dist", dist);
                        } else {
                            nbt.putInt("dist", 4);
                        }

                        stack.setTag(stack.getTag().merge(nbt));
                    }
                }
            }
        }
    }

}
