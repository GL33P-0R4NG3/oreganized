package me.gleep.oreganized.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MoltenLeadBlock extends Block {
    public MoltenLeadBlock() {
        super(Block.Properties.create(Material.LAVA)
                .hardnessAndResistance(100.0F)
                .noDrops());
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return PathNodeType.WALKABLE;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 8;
    }

    @NotNull
    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (!(context.getEntity() instanceof LivingEntity)) return VoxelShapes.empty();
        LivingEntity entity = (LivingEntity) context.getEntity();
        for (ItemStack item : entity.getArmorInventoryList()) {
            if (item.getItem().equals(Items.IRON_BOOTS)) return VoxelShapes.fullCube();
        }
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    /*@Override
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        return state.getBlock().equals(adjacentBlockState.getBlock());
    }*/
}
