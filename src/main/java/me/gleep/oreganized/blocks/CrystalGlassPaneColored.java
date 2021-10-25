package me.gleep.oreganized.blocks;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class CrystalGlassPaneColored extends CrystalGlassPaneBase implements BeaconBeamBlock {

    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");
    final DyeColor color;

    public CrystalGlassPaneColored(DyeColor color) {
        super();
        this.color = color;
        if (this.color != DyeColor.LIGHT_GRAY && this.color != DyeColor.WHITE && this.color != DyeColor.YELLOW) {
            this.registerDefaultState(this.defaultBlockState().setValue(ROTATED, false));
        }
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54221_) {
        super.createBlockStateDefinition(p_54221_);
        p_54221_.add(ROTATED);
    }

    @ParametersAreNonnullByDefault
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_54200_) {
        BlockState state = super.getStateForPlacement(p_54200_);
        if (this.color != DyeColor.LIGHT_GRAY && this.color != DyeColor.WHITE && this.color != DyeColor.YELLOW) {
            boolean axis = false;
            if (p_54200_.getPlayer() != null) {
                if (p_54200_.getPlayer().isCrouching()) {
                    axis = true;
                }
            }

            if (state != null) {
                return state.setValue(ROTATED, axis);
            }
        }
        return state;
    }
}
