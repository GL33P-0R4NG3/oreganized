package me.gleep.oreexpansion.fluids;

import me.gleep.oreexpansion.items.LeadBucket;
import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.lwjgl.system.CallbackI;

import java.util.function.Supplier;

public class LeadFluid extends Fluid {

    public LeadFluid() {
        super();
    }

    @Override
    public Item getFilledBucket() {
        return RegistryHandler.LEAD_BUCKET.get();
    }

    @Override
    protected boolean canDisplace(FluidState fluidState, IBlockReader blockReader, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    protected Vector3d getFlow(IBlockReader blockReader, BlockPos pos, FluidState fluidState) {
        return null;
    }

    @Override
    public int getTickRate(IWorldReader p_205569_1_) {
        return 0;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public float getActualHeight(FluidState p_215662_1_, IBlockReader p_215662_2_, BlockPos p_215662_3_) {
        return 8.0F;
    }

    @Override
    public float getHeight(FluidState p_223407_1_) {
        return 8.0F;
    }

    @Override
    protected BlockState getBlockState(FluidState state) {
        return null;
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
    public VoxelShape func_215664_b(FluidState p_215664_1_, IBlockReader p_215664_2_, BlockPos p_215664_3_) {
        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }

    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(new ResourceLocation("oreexpansion:block/lead_fluid"), null)
                .density(5).temperature(50).luminosity(2).build(this);
    }
}
