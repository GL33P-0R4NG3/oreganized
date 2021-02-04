package me.gleep.oreganized.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.Nullable;

public class CrystalGlassBase extends GlassBlock {

    public static final IntegerProperty ROTATED_0_2 = IntegerProperty.create("rotated", 0, 2);

    public CrystalGlassBase() {
        super(Properties.create(Material.GLASS)
                .hardnessAndResistance(0.3F)
                .sound(SoundType.GLASS)
                .notSolid()
                .setAllowsSpawn(CrystalGlassBase::neverAllowSpawn)
                .setOpaque(CrystalGlassBase::isntSolid)
                .setSuffocates(CrystalGlassBase::isntSolid)
                .setBlocksVision(CrystalGlassBase::isntSolid));
    }

    private static boolean isntSolid(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return false;
    }

    private static boolean neverAllowSpawn(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, EntityType<?> entityType) {
        return (boolean)false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(ROTATED_0_2);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        int axis = 0;
        if (context.getPlayer() != null) {
            if (context.getPlayer().isSneaking()) {
                switch (context.getPlacementHorizontalFacing()) {
                    case WEST:
                    case EAST:
                        axis = 1;
                        break;
                    case NORTH:
                    case SOUTH:
                        axis = 2;
                        break;
                }
            }
        }
        return this.getDefaultState().with(ROTATED_0_2, axis);
    }

}
