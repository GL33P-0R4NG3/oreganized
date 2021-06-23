package me.gleep.oreganized.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CrystalGlassPaneColored extends CrystalGlassPaneBase implements IBeaconBeamColorProvider {

    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");
    final DyeColor color;

    public CrystalGlassPaneColored(DyeColor color) {
        super();
        this.color = color;
        if (this.color != DyeColor.LIGHT_GRAY && this.color != DyeColor.WHITE && this.color != DyeColor.YELLOW) {
            this.setDefaultState(this.getDefaultState().with(ROTATED, false));
        }
    }

    @NotNull
    @Override
    public DyeColor getColor() {
        return this.color;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(ROTATED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (this.color != DyeColor.LIGHT_GRAY && this.color != DyeColor.WHITE && this.color != DyeColor.YELLOW) {
            boolean axis = false;
            if (context.getPlayer() != null) {
                if (context.getPlayer().isSneaking()) {
                    axis = true;
                }
            }

            if (state != null) {
                return state.with(ROTATED, axis);
            }
        }
        return state;
    }
}
