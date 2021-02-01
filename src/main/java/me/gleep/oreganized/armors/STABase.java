package me.gleep.oreganized.armors;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

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
