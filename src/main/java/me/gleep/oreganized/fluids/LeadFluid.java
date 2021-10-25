package me.gleep.oreganized.fluids;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class LeadFluid extends ForgeFlowingFluid {

    public LeadFluid() {
        super(new Properties(RegistryHandler.LEAD_FLUID, RegistryHandler.LEAD_FLUID_FLOW, FluidAttributes
                    .builder(new ResourceLocation("oreganized:block/lead_fluid"), new ResourceLocation("oreganized:block/lead_fluid"))
                    //.overlay(new ResourceLocation("oreganized:block/lead_fluid_overlay"))
                    .luminosity(8)
                    .viscosity(5000)
                    .density(11300)
                    .temperature(570)
                    .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA))
                .bucket(RegistryHandler.LEAD_BUCKET)
                .block(RegistryHandler.LEAD_FLUID_BLOCK)
                .tickRate(8));
    }

    @Override
    public Vec3 getFlow(BlockGetter p_75987_, BlockPos p_75988_, FluidState p_75989_) {
        Vec3 vector3d = new Vec3(0.0D, 0.0D, 0.0D);

        return vector3d.normalize();
    }


    @Override
    protected int getSlopeFindDistance(LevelReader worldIn) {
        return 8;
    }

    @Override
    protected boolean canSpreadTo(BlockGetter world, BlockPos fromPos, BlockState fromBlockState, Direction dir, BlockPos toPos, BlockState toBlockState, FluidState toFluidState, Fluid fluid) {
        return false;
    }

    public static class Flowing extends LeadFluid {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> p_76046_) {
            super.createFluidStateDefinition(p_76046_);
            p_76046_.add(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }
    }

    public static class Source extends LeadFluid {
        @Override
        public boolean isSource(FluidState state) {
            return true;
        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }
    }

}
