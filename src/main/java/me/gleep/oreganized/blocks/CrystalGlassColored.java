package me.gleep.oreganized.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;

public class CrystalGlassColored extends CrystalGlassBase implements BeaconBeamBlock {

    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 3);
    public static final int NORMAL = 0, ROTATED = 1, INNER = 2, OUTER = 3;
    public DyeColor color;

    public CrystalGlassColored(DyeColor color) {
        super();
        this.color = color;
        if (this.color != DyeColor.LIGHT_GRAY && this.color != DyeColor.WHITE && this.color != DyeColor.YELLOW) {
            this.registerDefaultState(this.defaultBlockState().setValue(TYPE, NORMAL));
        }
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        super.createBlockStateDefinition(p_49915_);
        p_49915_.add(TYPE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        /*BlockState blockAbove2 = pContext.getLevel().getBlockState(pContext.getClickedPos().above(2));
        BlockState blockAbove = pContext.getLevel().getBlockState(pContext.getClickedPos().above());
        Item placedBlock = pContext.getItemInHand().getItem();
        BlockState blockBelow = pContext.getLevel().getBlockState(pContext.getClickedPos().below());
        BlockState blockBelow2 = pContext.getLevel().getBlockState(pContext.getClickedPos().below(2));

        if(blockAbove.getBlock().asItem() == placedBlock && blockBelow.getBlock().asItem() == placedBlock){
            if(pContext.getPlayer().isCrouching() && blockAbove.getValue(TYPE) == ROTATED &&
                    blockBelow.getValue(TYPE) == NORMAL){
                return this.defaultBlockState().setValue(TYPE, OUTER);
            }
            return this.defaultBlockState().setValue(TYPE, NORMAL);
        }*/
        return this.defaultBlockState().setValue(TYPE, pContext.getPlayer().isCrouching() ? ROTATED : NORMAL);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
            if (pDirection == Direction.DOWN || pDirection == Direction.UP) {
                if (pState.getBlock() == pLevel.getBlockState(pCurrentPos.above()).getBlock()
                        && pState.getBlock() == pLevel.getBlockState(pCurrentPos.below()).getBlock()) {
                    updatePattern(pCurrentPos, pLevel);
                } else if (pState.getBlock() == pLevel.getBlockState(pCurrentPos.above(2)).getBlock()
                        && pState.getBlock() == pLevel.getBlockState(pCurrentPos.above()).getBlock()
                        && pState.getBlock() != pLevel.getBlockState(pCurrentPos.below()).getBlock()) {
                    updatePattern(pCurrentPos.above(), pLevel);
                } else if (pState.getBlock() == pLevel.getBlockState(pCurrentPos.below(2)).getBlock()
                        && pState.getBlock() == pLevel.getBlockState(pCurrentPos.below()).getBlock()
                        && pState.getBlock() != pLevel.getBlockState(pCurrentPos.above()).getBlock()) {
                    updatePattern(pCurrentPos.below(), pLevel);
                }
                if (pState.getValue(TYPE) == OUTER || pState.getValue(TYPE) == INNER) {
                    if (pState.getBlock() != pNeighborState.getBlock()) {
                        return pState.setValue(TYPE, ROTATED);
                    }
                }
            }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    private void updatePattern(BlockPos centerBlockPos, LevelAccessor pLevel) {
        BlockState aboveBlock = pLevel.getBlockState(centerBlockPos.above());
        BlockState centerBlock = pLevel.getBlockState(centerBlockPos);
        BlockState belowBlock = pLevel.getBlockState(centerBlockPos.below());
        if (centerBlock.getBlock() == aboveBlock.getBlock() && centerBlock.getBlock() == belowBlock.getBlock()) {
            if (aboveBlock.getValue(TYPE) == ROTATED && centerBlock.getValue(TYPE) == ROTATED && belowBlock.getValue(TYPE) == NORMAL) {
                pLevel.setBlock(centerBlockPos, centerBlock.setValue(TYPE, OUTER), 3);
            } else if (aboveBlock.getValue(TYPE) == NORMAL && centerBlock.getValue(TYPE) == NORMAL && belowBlock.getValue(TYPE) == ROTATED) {
                pLevel.setBlock(centerBlockPos, centerBlock.setValue(TYPE, INNER), 3);
            }
        }
    }
}
