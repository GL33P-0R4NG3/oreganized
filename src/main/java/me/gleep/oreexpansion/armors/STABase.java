package me.gleep.oreexpansion.armors;

import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

public class STABase extends ArmorItem {
    private final boolean immuneToFire;
    public STABase(IArmorMaterial materialIn, EquipmentSlotType slot, boolean immuneToFire) {
        super(materialIn, slot, new Properties().group(ItemGroup.COMBAT).setNoRepair());
        this.immuneToFire = immuneToFire;
    }

    public STABase(IArmorMaterial materialIn, EquipmentSlotType slot) {
        super(materialIn, slot, new Properties().group(ItemGroup.COMBAT).setNoRepair());
        this.immuneToFire = false;
    }

    @Override
    public boolean isImmuneToFire() {
        return this.immuneToFire;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        if (stack.getItemEnchantability() == ArmorMaterial.GOLD.getEnchantability()) {
            return true;
        } else return false;
    }
}
