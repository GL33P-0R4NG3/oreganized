package me.gleep.oreexpansion.items;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;

import java.util.function.Supplier;

public class LeadBucket extends BucketItem {
    public LeadBucket(Supplier<? extends Fluid> supplier) {
        super(supplier, new Item.Properties().group(ItemGroup.MATERIALS));
    }
}
