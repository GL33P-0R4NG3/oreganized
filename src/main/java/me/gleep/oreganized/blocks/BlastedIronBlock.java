package me.gleep.oreganized.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;

public class BlastedIronBlock extends Block {

    //Magic properties
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty UPDATE = BooleanProperty.create("update");

    public BlastedIronBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL)
                .strength(6.0F, 3.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.NETHERITE_BLOCK));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(UP, false).setValue(DOWN, false)
                .setValue(NORTH, false).setValue(WEST, false)
                .setValue(SOUTH, false).setValue(EAST, false)
                .setValue(UPDATE, false)
        );
    }

    @Override
    public BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_) {
        BooleanProperty PROPERTY = null;
        switch (p_60542_) {
            case UP:
                PROPERTY = UP;
                break;
            case DOWN:
                PROPERTY = DOWN;
                break;
            case EAST:
                PROPERTY = EAST;
                break;
            case WEST:
                PROPERTY = WEST;
                break;
            case NORTH:
                PROPERTY = NORTH;
                break;
            case SOUTH:
                PROPERTY = SOUTH;
                break;
        }

        if (PROPERTY != null) {
            if (canConnect(p_60545_, p_60544_, p_60542_))
                p_60541_ = p_60541_.setValue(PROPERTY, true);
            else if (p_60541_.getValue(PROPERTY))
                p_60541_ = p_60541_.setValue(PROPERTY, false);
        }
        return p_60541_;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(UP, DOWN, NORTH, WEST, SOUTH, EAST, UPDATE);
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        if (p_49820_.getPlayer() != null) {
            if (p_49820_.getPlayer().isCrouching()) {
                return this.defaultBlockState().setValue(UP, canConnect(p_49820_.getClickedPos(), p_49820_.getLevel(), Direction.UP))
                        .setValue(DOWN, canConnect(p_49820_.getClickedPos(), p_49820_.getLevel(), Direction.DOWN))
                        .setValue(NORTH, canConnect(p_49820_.getClickedPos(), p_49820_.getLevel(), Direction.NORTH))
                        .setValue(WEST, canConnect(p_49820_.getClickedPos(), p_49820_.getLevel(), Direction.WEST))
                        .setValue(SOUTH, canConnect(p_49820_.getClickedPos(), p_49820_.getLevel(), Direction.SOUTH))
                        .setValue(EAST, canConnect(p_49820_.getClickedPos(), p_49820_.getLevel(), Direction.EAST))
                        .setValue(UPDATE, true);
            }
        }
        return this.defaultBlockState();
    }

    private boolean canConnect(BlockPos pos, Level world, Direction dir) {
        if (world.getBlockState(pos.offset(dir.getNormal())).getBlock() instanceof BlastedIronBlock) {
            return world.getBlockState(pos.offset(dir.getNormal())).getValue(UPDATE);
        }
        return false;
    }

    private boolean canConnect(BlockPos pos, LevelAccessor world, Direction dir) {
        if (world.getBlockState(pos.offset(dir.getNormal())).getBlock() instanceof BlastedIronBlock) {
            return world.getBlockState(pos.offset(dir.getNormal())).getValue(UPDATE) && world.getBlockState(pos).getValue(UPDATE);
        }
        return false;
    }


}
