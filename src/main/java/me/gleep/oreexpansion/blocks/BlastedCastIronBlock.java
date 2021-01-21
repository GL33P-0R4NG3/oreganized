package me.gleep.oreexpansion.blocks;

import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandSource;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlastedCastIronBlock extends Block {

    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");

    public BlastedCastIronBlock() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(4.0F, 5.0F)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(1)
                .sound(SoundType.METAL));
    }

    /*@Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        Direction dir = null;
        BooleanProperty PROPERTY;
        dir = Direction.getFacingFromVector(fromPos.getX() - pos.getX(), fromPos.getY() - pos.getY(), fromPos.getZ() - pos.getZ());
        switch (dir) {
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
            default:
                PROPERTY = null;
                break;
        }

        if (PROPERTY != null && canConnect(pos, worldIn, dir)) state = state.with(PROPERTY, true);
        worldIn.setBlockState(pos, state);
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }*/

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction dir = null;
        BooleanProperty PROPERTY;
        dir = Direction.getFacingFromVector(facingPos.getX() - currentPos.getX(), facingPos.getY() - currentPos.getY(), facingPos.getZ() - currentPos.getZ());
        switch (dir) {
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
            default:
                PROPERTY = null;
                break;
        }

        if (PROPERTY != null && canConnect(currentPos, worldIn, dir)) stateIn = stateIn.with(PROPERTY, true);
        //worldIn.setBlockState(pos, state);
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, WEST, SOUTH, EAST);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).with(UP, canConnect(context.getPos(), context.getWorld(), Direction.UP))
                .with(DOWN, canConnect(context.getPos(), context.getWorld(), Direction.DOWN))
                .with(NORTH, canConnect(context.getPos(), context.getWorld(), Direction.NORTH))
                .with(WEST, canConnect(context.getPos(), context.getWorld(), Direction.WEST))
                .with(SOUTH, canConnect(context.getPos(), context.getWorld(), Direction.SOUTH))
                .with(EAST, canConnect(context.getPos(), context.getWorld(), Direction.EAST));
    }

    private boolean canConnect(BlockPos pos, World world, Direction dir) {
        return this.getBlock() == world.getBlockState(pos.offset(dir)).getBlock();
    }

    private boolean canConnect(BlockPos pos, IWorld world, Direction dir) {
        return this.getBlock() == world.getBlockState(pos.offset(dir)).getBlock();
    }
}
