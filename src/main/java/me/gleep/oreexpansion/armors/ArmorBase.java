package me.gleep.oreexpansion.armors;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemGroup;

import javax.annotation.Nullable;

public class ArmorBase extends ArmorItem {
    private final boolean immuneToFire;
    public ArmorBase(IArmorMaterial materialIn, EquipmentSlotType slot, boolean immuneToFire) {
        super(materialIn, slot, new Properties().group(ItemGroup.COMBAT));
        this.immuneToFire = immuneToFire;
    }

    public ArmorBase(IArmorMaterial materialIn, EquipmentSlotType slot) {
        super(materialIn, slot, new Properties().group(ItemGroup.COMBAT));
        this.immuneToFire = false;
    }

    @Override
    public boolean isImmuneToFire() {
        return this.immuneToFire;
    }
}
