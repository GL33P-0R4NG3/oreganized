package me.gleep.oreganized.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import org.jetbrains.annotations.Nullable;

public class CrystalGlassPaneBase extends PaneBlock {

    public CrystalGlassPaneBase() {
        super(Properties.create(Material.GLASS)
                .hardnessAndResistance(0.3F)
                .sound(SoundType.GLASS)
                .notSolid());
    }

    /*@Override
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
    }*/

}
