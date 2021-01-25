package me.gleep.oreganized.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SilverMirror extends Item {
    boolean isUndeadNearby;

    public SilverMirror() {
        super(new Properties().group(ItemGroup.TOOLS)
                .maxStackSize(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!(entityIn instanceof PlayerEntity)) return;
        CompoundNBT nbt = new CompoundNBT();
        //PlayerEntity player = (PlayerEntity) entityIn;
        BlockPos pos = entityIn.getPosition();
        List<Entity> list = worldIn.getEntitiesInAABBexcluding(null,
                new AxisAlignedBB(pos.getX() + 8, pos.getY() + 8, pos.getZ() + 8,
                        pos.getX() - 8, pos.getY() - 8, pos.getZ() - 8),
                null);

        for (Entity e : list) {
            if (e.isLiving()) {
                LivingEntity living = (LivingEntity)e;
                if (living.isEntityUndead()) {
                    isUndeadNearby = true;
                    if (stack.getTag() != null) nbt.merge(stack.getTag());
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

                    stack.setTag(nbt);
                }
            } else {
                isUndeadNearby = false;
            }
        }

        if (!isUndeadNearby) {
            nbt.putInt("dist", 4);
        }
        nbt.putBoolean("isUndeadNearby", isUndeadNearby);
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        isUndeadNearby = false;
        if (nbt != null) {
            if (nbt.contains("isUndeadNearby")) {
                isUndeadNearby = nbt.getBoolean("isUndeadNearby");
            }
        }
        super.readShareTag(stack, nbt);
    }
}
