package me.gleep.oreganized.items;


import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class LeadBucket extends BucketItem {

    public LeadBucket(Supplier<? extends Fluid> supplier) {
        super(supplier, new Properties().tab(CreativeModeTab.TAB_MATERIALS).stacksTo(1));
    }

}
