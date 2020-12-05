package me.gleep.oreexpansion.items;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;

public class LeadBucket extends BucketItem {
    public LeadBucket(java.util.function.Supplier<? extends Fluid> supplier, Item.Properties builder) {
        super(supplier, builder);
    }

}
