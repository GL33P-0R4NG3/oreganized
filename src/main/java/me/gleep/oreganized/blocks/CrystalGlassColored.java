package me.gleep.oreganized.blocks;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;

public class CrystalGlassColored extends CrystalGlassBase implements BeaconBeamBlock {

    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");
    public DyeColor color;

    public CrystalGlassColored(DyeColor color) {
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        super.createBlockStateDefinition(p_49915_);
        if (this.color != DyeColor.LIGHT_GRAY && this.color != DyeColor.WHITE && this.color != DyeColor.YELLOW) p_49915_.add(ROTATED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        if (this.color != DyeColor.LIGHT_GRAY && this.color != DyeColor.WHITE && this.color != DyeColor.YELLOW) {
            boolean axis = false;
            if (p_49820_.getPlayer() != null) {
                if (p_49820_.getPlayer().isCrouching()) {
                    axis = true;
                }
            }
            return this.defaultBlockState().setValue(ROTATED, axis);
        }
        return this.defaultBlockState();
    }
}
