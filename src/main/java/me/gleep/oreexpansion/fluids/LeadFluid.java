package me.gleep.oreexpansion.fluids;

import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.fluid.FluidState;
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

public class LeadFluid extends ForgeFlowingFluid {
    public LeadFluid() {
        super(new Properties(RegistryHandler.LEAD_FLUID, RegistryHandler.LEAD_FLUID_FLOW, FluidAttributes
                    .builder(new ResourceLocation("oreexpansion:block/lead_fluid"), new ResourceLocation("oreexpansion:block/lead_fluid"))
                    .overlay(new ResourceLocation("oreexpansion:block/lead_fluid_overlay"))
                    .luminosity(8)
                    .viscosity(10000)
                    .density(5000)
                    .sound(SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundEvents.ITEM_BUCKET_EMPTY_LAVA))
                .bucket(RegistryHandler.LEAD_BUCKET)
                .block(RegistryHandler.LEAD_FLUID_BLOCK)
                .tickRate(8));
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
    public Vector3d getFlow(IBlockReader blockReader, BlockPos pos, FluidState fluidState) {
        double d0 = 0.0D;
        double d1 = 0.0D;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        Vector3d vector3d = new Vector3d(d0, 0.0D, d1);
        /*if (fluidState.get(FALLING)) {
            for (Direction direction1 : Direction.Plane.HORIZONTAL) {
                blockpos$mutable.setAndMove(pos, direction1);
                if (this.causesDownwardCurrent(blockReader, blockpos$mutable, direction1) || this.causesDownwardCurrent(blockReader, blockpos$mutable.up(), direction1)) {
                    vector3d = vector3d.normalize().add(0.0D, -6.0D, 0.0D);
                    break;
                }
            }
        }*/

        return vector3d.normalize();
    }

    @Override
    protected int getLevelDecreasePerBlock(IWorldReader worldIn) {
        return 8;
    }

    @Override
    public VoxelShape func_215664_b(FluidState p_215664_1_, IBlockReader p_215664_2_, BlockPos p_215664_3_) {
        return VoxelShapes.fullCube();
    }



}
