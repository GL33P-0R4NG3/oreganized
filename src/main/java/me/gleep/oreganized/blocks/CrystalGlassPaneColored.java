package me.gleep.oreganized.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.StateContainer;
import org.jetbrains.annotations.NotNull;

public class CrystalGlassPaneColored extends CrystalGlassPaneBase implements IBeaconBeamColorProvider {

    //public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");
    final DyeColor color;

    public CrystalGlassPaneColored(DyeColor color) {
        this.color = color;
    }

    @NotNull
    @Override
    public DyeColor getColor() {
        return this.color;
    }

    /*@Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(ROTATED);
    }*/

    /*@Override
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
        return super.getStateForPlacement(context).with(ROTATED, axis);
    }*/
}
