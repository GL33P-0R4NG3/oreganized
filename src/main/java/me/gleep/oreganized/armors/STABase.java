package me.gleep.oreganized.armors;

import me.gleep.oreganized.blocks.SilverBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class STABase extends ArmorItem {
    private final boolean immuneToFire;
    public STABase(IArmorMaterial materialIn, EquipmentSlotType slot, boolean immuneToFire) {
        super(materialIn, slot, new Properties().group(ItemGroup.COMBAT).maxStackSize(1));
        this.immuneToFire = immuneToFire;
    }

    public STABase(IArmorMaterial materialIn, EquipmentSlotType slot) {
        super(materialIn, slot, new Properties().group(ItemGroup.COMBAT).maxStackSize(1));
        this.immuneToFire = false;
    }

    /*@Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        int range = (int) SilverBlock.RANGE;
        BlockPos pos1 = player.getPosition().add(-range / 2, -range / 2, -range / 2);
        BlockPos pos2 = pos1.add(range, range , range);
        List<Entity> list = world.getEntitiesInAABBexcluding(player, new AxisAlignedBB(pos1, pos2), null);
        for (Entity e : list) {
            if (e.isLiving()) {
                LivingEntity living = (LivingEntity) e;
                if (living.isEntityUndead()) {
                    MonsterEntity monster = (MonsterEntity) e;
                    try {
                        ServerPlayerEntity playerEntity = Objects.requireNonNull(world.getServer()).getPlayerList().getPlayerByUUID(player.getUniqueID());
                        if (playerEntity != null) monster.removeTrackingPlayer(playerEntity);
                    } catch (NullPointerException err) {

                    }
                }
            }
        }
    }*/

    @Override
    public boolean isImmuneToFire() {
        return this.immuneToFire;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return stack.getItemEnchantability() == ArmorMaterial.GOLD.getEnchantability();
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }
}
