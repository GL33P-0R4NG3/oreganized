package me.gleep.oreganized.items;


import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ItemBase extends Item {

    private final boolean immuneToFire;

    public ItemBase() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
        this.immuneToFire = false;
    }

    public ItemBase(boolean immuneToFire) {
        super(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
        this.immuneToFire = immuneToFire;
    }

    @Override
    public boolean isFireResistant() {
        return this.immuneToFire;
    }
}
