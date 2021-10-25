package me.gleep.oreganized.items;

import me.gleep.oreganized.blocks.SilverBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SilverMirror extends Item {

    boolean isUndeadNearby = false;

    public SilverMirror() {
        super(new Properties().tab(CreativeModeTab.TAB_TOOLS)
                .stacksTo(1));
    }

    @Override
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        int dist = 4;

        if (!(p_41406_ instanceof Player)) return;
        CompoundTag nbt = new CompoundTag();
        Player player = (Player) p_41406_;
        BlockPos pos = player.getOnPos();
        List<Entity> list = p_41405_.getEntities(player,
                new AABB(pos.getX() + SilverBlock.RANGE, pos.getY() + SilverBlock.RANGE, pos.getZ() + SilverBlock.RANGE,
                        pos.getX() - SilverBlock.RANGE, pos.getY() - SilverBlock.RANGE, pos.getZ() - SilverBlock.RANGE), (Entity entity) -> entity instanceof LivingEntity
        );

        isUndeadNearby = false;
        for (Entity e : list) {
            LivingEntity living = (LivingEntity) e;
            if (living.isInvertedHealAndHarm()) {
                isUndeadNearby = true;
                double distance = living.distanceTo(player);
                if (distance < SilverBlock.RANGE && ((int) Math.ceil(distance / (SilverBlock.RANGE / 4))) < dist) {
                    if (distance <= 6) {
                        dist = 1;
                    } else dist = Math.max((int) Math.ceil(distance / (SilverBlock.RANGE / 4)), 2);

                    if (dist > 3) {
                        dist = 3;
                    }
                }
            }
        }

        if (!isUndeadNearby) {
            dist = 4;
        }

        nbt.putInt("Dist", dist);
        p_41404_.setTag(nbt);
    }
}
