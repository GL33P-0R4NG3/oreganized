package me.gleep.oreexpansion.fluids;

import net.minecraft.fluid.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class LeadFluid extends ForgeFlowingFluid {
    protected LeadFluid(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSource(FluidState state) {
        return true;
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }
}
