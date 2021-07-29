package me.gleep.oreganized.fluids;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;

public abstract class LeadFluid extends ForgeFlowingFluid {

    public LeadFluid() {
        super(new Properties(RegistryHandler.LEAD_FLUID, RegistryHandler.LEAD_FLUID_FLOW, FluidAttributes
                    .builder(new ResourceLocation("oreganized:block/lead_fluid"), new ResourceLocation("oreganized:block/lead_fluid"))
                    //.overlay(new ResourceLocation("oreganized:block/lead_fluid_overlay"))
                    .luminosity(8)
                    .viscosity(5000)
                    .density(11300)
                    .temperature(570)
                    .sound(SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundEvents.ITEM_BUCKET_EMPTY_LAVA))
                .bucket(RegistryHandler.LEAD_BUCKET)
                .block(RegistryHandler.LEAD_FLUID_BLOCK)
                .tickRate(8));
    }

    @Override
    public Vector3d getFlow(IBlockReader blockReader, BlockPos pos, FluidState fluidState) {
        Vector3d vector3d = new Vector3d(0.0D, 0.0D, 0.0D);

        return vector3d.normalize();
    }

    @Override
    protected int getLevelDecreasePerBlock(IWorldReader worldIn) {
        return 8;
    }

    @Override
    protected boolean canFlow(IBlockReader worldIn, BlockPos fromPos, BlockState fromBlockState, Direction direction, BlockPos toPos, BlockState toBlockState, FluidState toFluidState, Fluid fluidIn) {
        return false;
    }

    public static class Flowing extends LeadFluid {
        @Override
        protected void fillStateContainer(@NotNull StateContainer.Builder<Fluid, FluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }

    public static class Source extends LeadFluid {
        @Override
        public boolean isSource(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }

}
