package me.gleep.oreganized.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ExposerBlock extends DirectionalBlock {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_3;
    boolean isUndeadNearby = false;

    public ExposerBlock() {
        super(AbstractBlock.Properties.from(Blocks.OBSERVER));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.SOUTH).with(LEVEL, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, LEVEL);
    }

    @NotNull
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @NotNull
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.getWeakPower(blockAccess, pos, side);
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        switch (blockState.get(LEVEL)) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
        }

        return blockState.get(LEVEL);
    }

    @NotNull
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(FACING) == facing && stateIn.get(LEVEL) == 0) {
            this.startSignal(worldIn, currentPos);
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    private void startSignal(IWorld worldIn, BlockPos pos) {
        if (!worldIn.isRemote() && !worldIn.getPendingBlockTicks().isTickScheduled(pos, this)) {
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, 2);
        }
    }

    public void tick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        int dist = 4;

        List<Entity> list = worldIn.getEntitiesInAABBexcluding(null,
                new AxisAlignedBB(pos.getX() + SilverBlock.RANGE, pos.getY() + SilverBlock.RANGE, pos.getZ() + SilverBlock.RANGE,
                        pos.getX() - SilverBlock.RANGE, pos.getY() - SilverBlock.RANGE, pos.getZ() - SilverBlock.RANGE),
                null);

        for (Entity e : list) {
            if (e.isLiving()) {
                LivingEntity living = (LivingEntity) e;
                if (living.isEntityUndead()) {
                    isUndeadNearby = true;
                    double distance = MathHelper.sqrt(living.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()));
                    if (distance < SilverBlock.RANGE && ((int) Math.ceil(distance / (SilverBlock.RANGE / 4))) < dist) {
                        if (distance <= 6) {
                            dist = 1;
                        } else dist = Math.max((int) Math.ceil(distance / (SilverBlock.RANGE / 4)), 2);

                        if (dist > 3) {
                            dist = 3;
                        }
                    }
                }
            }
        }

        if (!isUndeadNearby) {
            dist = 4;
        }
        worldIn.setBlockState(pos, state.with(LEVEL, 3 - (dist - 1)));

        this.updateNeighborsInFront(worldIn, pos, state);
    }

    protected void updateNeighborsInFront(World worldIn, BlockPos pos, BlockState state) {
        Direction direction = state.get(FACING);
        BlockPos blockpos = pos.offset(direction.getOpposite());
        worldIn.neighborChanged(blockpos, this, pos);
        worldIn.notifyNeighborsOfStateExcept(blockpos, this, direction);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.isIn(newState.getBlock())) {
            if (!worldIn.isRemote && state.get(LEVEL) > 0 && worldIn.getPendingBlockTicks().isTickScheduled(pos, this)) {
                this.updateNeighborsInFront(worldIn, pos, state.with(LEVEL, 0));
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite().getOpposite());
    }
}
