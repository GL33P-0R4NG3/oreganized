package me.gleep.oreganized.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrystalGlassColored extends CrystalGlassBase implements IBeaconBeamColorProvider {

    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");
    public DyeColor color;

    public CrystalGlassColored(DyeColor color) {
        super();
        this.color = color;
    }

    @Override
    public @NotNull DyeColor getColor() {
        return this.color;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        if (this.color != DyeColor.LIGHT_GRAY && this.color != DyeColor.WHITE && this.color != DyeColor.YELLOW) builder.add(ROTATED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (this.color != DyeColor.LIGHT_GRAY && this.color != DyeColor.WHITE && this.color != DyeColor.YELLOW) {
            boolean axis = false;
            if (context.getPlayer() != null) {
                if (context.getPlayer().isSneaking()) {
                    axis = true;
                }
            }
            return this.getDefaultState().with(ROTATED, axis);
        }
        return this.getDefaultState();
    }
}
