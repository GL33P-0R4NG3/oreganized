package me.gleep.oreganized.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class MoltenLeadBlock extends Block {
    public MoltenLeadBlock() {
        super(BlockBehaviour.Properties.of(Material.LAVA)
                .strength(100.0F)
                .noDrops());
    }


    /**
     * Get the {@code PathNodeType} for this block. Return {@code null} for vanilla behavior.
     *
     * @param state
     * @param world
     * @param pos
     * @param entity
     * @return the PathNodeType
     */
    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.WALKABLE;
    }

    @Override
    public int getLightBlock(BlockState p_60585_, BlockGetter p_60586_, BlockPos p_60587_) {
        return 8;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        if (!(p_60575_ instanceof LivingEntity)) return Shapes.empty();
        LivingEntity entity = (LivingEntity) p_60575_;
        for (ItemStack item : entity.getArmorSlots()) {
            if (item.getItem().equals(Items.IRON_BOOTS)) return Shapes.block();
        }
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.empty();
    }

    /*@Override
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        return state.getBlock().equals(adjacentBlockState.getBlock());
    }*/
}
