package me.gleep.oreexpansion.fluids;

import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class LeadFluid extends ForgeFlowingFluid {

    public LeadFluid() {
        super(new ForgeFlowingFluid.Properties(RegistryHandler.LEAD_FLUID, null, FluidAttributes
                .builder(null/*new ResourceLocation("oreexpansion:block/lead_fluid")*/, null)
                .density(5)
                .viscosity(5)
                .luminosity(6)
                .temperature(40)
                .sound(SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundEvents.ITEM_BUCKET_EMPTY_LAVA))
            .block(RegistryHandler.LEAD_FLUID_BLOCK));
    }

    @Override
    public boolean isSource(FluidState state) {
        return true;
    }

    @Override
    public int getLevel(FluidState state) {
        return 8;
    }

    @Override
    public Fluid getFlowingFluid() {
        return (Fluid)null;
    }

    @Override
    public Fluid getStillFluid() {
        return super.getStillFluid();
    }

    @Override
    public Item getFilledBucket() {
        return RegistryHandler.LEAD_BUCKET.get();
    }

    public static class Source extends LeadFluid {

    }
}
