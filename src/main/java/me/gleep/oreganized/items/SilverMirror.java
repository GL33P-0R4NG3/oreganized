package me.gleep.oreganized.items;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SilverMirror extends Item {

    public SilverMirror() {
        super(new Properties().group(ItemGroup.TOOLS)
                .maxStackSize(1));
    }

    /*@Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!(entityIn instanceof PlayerEntity)) return;
        CompoundNBT nbt = new CompoundNBT();
        PlayerEntity player = (PlayerEntity) entityIn;
        BlockPos pos = player.getPosition();
        List<Entity> list = worldIn.getEntitiesInAABBexcluding(player,
                new AxisAlignedBB(pos.getX() + RANGE, pos.getY() + RANGE, pos.getZ() + RANGE,
                        pos.getX() - RANGE, pos.getY() - RANGE, pos.getZ() - RANGE),
                        null);

        for (Entity e : list) {
            if (e.isLiving()) {
                LivingEntity living = (LivingEntity)e;
                if (living.isEntityUndead()) {
                    isUndeadNearby = true;
                    if (stack.getTag() != null) nbt.merge(stack.getTag());
                    double distance = living.getDistance(player);
                    if (distance < RANGE) {
                        int dist = (int) Math.floor(distance / (RANGE / 4));
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
    }*/

}
