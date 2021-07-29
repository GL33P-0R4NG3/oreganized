package me.gleep.oreganized.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(6.0F, 3.0F)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(1)
                .sound(SoundType.NETHERITE));
        this.setDefaultState(this.getDefaultState().with(UP, false).with(DOWN, false).with(NORTH, false)
                .with(WEST, false).with(SOUTH, false).with(EAST, false).with(UPDATE, false)
        );
    }

    @NotNull
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        BooleanProperty PROPERTY = null;
        switch (facing) {
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
            if (canConnect(currentPos, worldIn, facing))
                stateIn = stateIn.with(PROPERTY, true);
            else if (stateIn.get(PROPERTY))
                stateIn = stateIn.with(PROPERTY, false);
        }
        return stateIn;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, WEST, SOUTH, EAST, UPDATE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (context.getPlayer() != null) {
            if (context.getPlayer().isCrouching()) {
                return this.getDefaultState().with(UP, canConnect(context.getPos(), context.getWorld(), Direction.UP))
                        .with(DOWN, canConnect(context.getPos(), context.getWorld(), Direction.DOWN))
                        .with(NORTH, canConnect(context.getPos(), context.getWorld(), Direction.NORTH))
                        .with(WEST, canConnect(context.getPos(), context.getWorld(), Direction.WEST))
                        .with(SOUTH, canConnect(context.getPos(), context.getWorld(), Direction.SOUTH))
                        .with(EAST, canConnect(context.getPos(), context.getWorld(), Direction.EAST))
                        .with(UPDATE, true);
            }
        }
        return this.getDefaultState();
    }

    private boolean canConnect(BlockPos pos, World world, Direction dir) {
        if (world.getBlockState(pos.offset(dir)).getBlock() instanceof BlastedIronBlock) {
            return world.getBlockState(pos.offset(dir)).get(UPDATE);
        }
        return false;
    }

    private boolean canConnect(BlockPos pos, IWorld world, Direction dir) {
        if (world.getBlockState(pos.offset(dir)).getBlock() instanceof BlastedIronBlock) {
            return world.getBlockState(pos.offset(dir)).get(UPDATE) && world.getBlockState(pos).get(UPDATE);
        }
        return false;
    }


}
