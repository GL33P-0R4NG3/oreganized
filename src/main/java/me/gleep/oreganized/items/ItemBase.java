package me.gleep.oreganized.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemBase extends Item {

    private final boolean immuneToFire;

    public ItemBase() {
        super(new Item.Properties().group(ItemGroup.MATERIALS));
        this.immuneToFire = false;
    }

    public ItemBase(boolean immuneToFire) {
        super(new Item.Properties().group(ItemGroup.MATERIALS));
        this.immuneToFire = immuneToFire;
    }

    @Override
    public boolean isImmuneToFire() {
        return this.immuneToFire;
    }
}
