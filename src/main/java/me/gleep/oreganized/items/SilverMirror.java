package me.gleep.oreganized.items;

import me.gleep.oreganized.blocks.SilverBlock;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SilverMirror extends Item {

    boolean isUndeadNearby = false;

    public SilverMirror() {
        super(new Properties().group(ItemGroup.TOOLS)
                .maxStackSize(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        int dist = 4;

        if (!(entityIn instanceof PlayerEntity)) return;
        CompoundNBT nbt = new CompoundNBT();
        PlayerEntity player = (PlayerEntity) entityIn;
        BlockPos pos = player.getPosition();
        List<ChickenEntity> list = worldIn.getEntitiesWithinAABB(EntityType.CHICKEN,
                new AxisAlignedBB(pos.getX() + SilverBlock.RANGE, pos.getY() + SilverBlock.RANGE, pos.getZ() + SilverBlock.RANGE,
                        pos.getX() - SilverBlock.RANGE, pos.getY() - SilverBlock.RANGE, pos.getZ() - SilverBlock.RANGE), (x) -> true
        );

        isUndeadNearby = false;
        for (Entity e : list) {
            isUndeadNearby = true;
            double distance = MathHelper.sqrt(e.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()));
            if (distance < SilverBlock.RANGE && ((int) Math.ceil(distance / (SilverBlock.RANGE / 4))) < dist) {
                if (distance <= 6) {
                    dist = 1;
                } else dist = Math.max((int) Math.ceil(distance / (SilverBlock.RANGE / 4)), 2);

                if (dist > 3) {
                    dist = 3;
                }
            }
        }

        if (!isUndeadNearby) {
            dist = 4;
        }

        nbt.putInt("Dist", dist);
        stack.setTag(nbt);
    }

}
