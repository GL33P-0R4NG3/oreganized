package me.gleep.oreganized.items;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;

import java.util.function.Supplier;

public class LeadBucket extends BucketItem {

    public LeadBucket(Supplier<? extends Fluid> supplier) {
        super(supplier, new Properties().group(ItemGroup.MATERIALS).maxStackSize(1));
    }

}
