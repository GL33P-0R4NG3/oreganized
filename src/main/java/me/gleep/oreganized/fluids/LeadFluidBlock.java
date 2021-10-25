package me.gleep.oreganized.fluids;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class LeadFluidBlock extends LiquidBlock {
    public LeadFluidBlock(Supplier<? extends FlowingFluid> supplier) {
        super(supplier, Block.Properties.of(Material.LAVA)
                .noOcclusion()
                .strength(100.0F)
                .speedFactor(0.10F)
                //.jumpFactor(0.0F)
                .noDrops());
    }

    @Override
    public RenderShape getRenderShape(BlockState p_54738_) {
        return RenderShape.MODEL;
    }

    /**
     * Get the {@code PathNodeType} for this block. Return {@code null} for vanilla behavior.
     *
     * @return the PathNodeType
     */
    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.WALKABLE;
    }

    /**
     * Get a light value for this block, taking into account the given state and coordinates, normal ranges are between 0 and 15
     *
     * @param state
     * @param world
     * @param pos
     * @return The light value
     */
    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return 8;
    }

    /**
     * Determines if this block should set fire and deal fire damage
     * to entities coming into contact with it.
     *
     * @param state
     * @param world The current world
     * @param pos   Block position in world
     * @return True if the block should deal damage
     */
    @Override
    public boolean isBurning(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    /*@Override
    public boolean isVariableOpacity() {
        return true;
    }*/

    @Override
    public VoxelShape getCollisionShape(BlockState p_54760_, BlockGetter p_54761_, BlockPos p_54762_, CollisionContext p_54763_) {
        if (!(p_54763_ instanceof LivingEntity)) return Shapes.empty();
        LivingEntity entity = (LivingEntity) p_54763_;
        for (ItemStack item : entity.getArmorSlots()) {
            if (item.getItem().equals(Items.IRON_BOOTS)) return Shapes.block();
        }
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState p_54749_, BlockGetter p_54750_, BlockPos p_54751_, CollisionContext p_54752_) {
        return super.getShape(p_54749_, p_54750_, p_54751_, p_54752_);
    }
}
