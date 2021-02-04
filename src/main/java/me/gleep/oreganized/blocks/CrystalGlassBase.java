package me.gleep.oreganized.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.Nullable;

public class CrystalGlassBase extends GlassBlock {

    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");

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
        builder.add(ROTATED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean axis = false;
        if (context.getPlayer() != null) {
            if (context.getPlayer().isSneaking()) {
                axis = true;
            }
        }
        return this.getDefaultState().with(ROTATED, axis);
    }

}
