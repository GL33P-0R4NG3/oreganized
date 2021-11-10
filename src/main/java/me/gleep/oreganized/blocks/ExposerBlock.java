package me.gleep.oreganized.blocks;

import me.gleep.oreganized.entities.tileentities.ExposerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ExposerBlock extends DirectionalBlock implements EntityBlock {
    public static final IntegerProperty LEVEL = BlockStateProperties.AGE_3;
    public static final int[] POWER_STATES = new int[] {0, 1, 2, 3};
    boolean isUndeadNearby = false;

    public ExposerBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.OBSERVER));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.SOUTH).setValue(LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING, LEVEL);
    }

    @Override
    public BlockState rotate(BlockState p_60530_, Rotation p_60531_) {
        return p_60530_.setValue(FACING, p_60531_.rotate(p_60530_.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState p_60528_, Mirror p_60529_) {
        return p_60528_.rotate(p_60529_.getRotation(p_60528_.getValue(FACING)));
    }


    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new ExposerBlockEntity(p_153215_, p_153216_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ExposerBlockEntity::tick;
    }

    /*@Nullable
    public BlockEntityTicker<ExposerBlockEntity> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : ExposerBlockEntity::tick;
    }*/

    @Override
    public boolean isSignalSource(BlockState p_60571_) {
        return true;
    }

    @Override
    public int getSignal(BlockState p_60483_, BlockGetter p_60484_, BlockPos p_60485_, Direction p_60486_) {
        return p_60483_.getSignal(p_60484_, p_60485_, p_60486_);
    }

    @Override
    public int getDirectSignal(BlockState p_60559_, BlockGetter p_60560_, BlockPos p_60561_, Direction p_60562_) {
        return POWER_STATES[p_60559_.getValue(LEVEL)];
    }

    @Override
    public BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_) {
        if (p_60541_.getValue(FACING) == p_60542_ && p_60541_.getValue(LEVEL) == 0) {
            this.startSignal(p_60544_, p_60545_);
        }

        return super.updateShape(p_60541_, p_60542_, p_60543_, p_60544_, p_60545_, p_60546_);
    }

    private void startSignal(LevelAccessor worldIn, BlockPos pos) {
        if (!worldIn.isClientSide() && !worldIn.getBlockTicks().willTickThisTick(pos, this)) {
            worldIn.getBlockTicks().scheduleTick(pos, this, 2);
        }
    }

    @Override
    public void tick(BlockState p_60462_, ServerLevel p_60463_, BlockPos p_60464_, Random p_60465_) {
        int dist = 4;

        List<Entity> list = p_60463_.getEntities((Entity) null,
                new AABB(p_60464_.getX() + SilverBlock.RANGE, p_60464_.getY() + SilverBlock.RANGE, p_60464_.getZ() + SilverBlock.RANGE,
                        p_60464_.getX() - SilverBlock.RANGE, p_60464_.getY() - SilverBlock.RANGE, p_60464_.getZ() - SilverBlock.RANGE),
                (Entity entity) -> entity instanceof LivingEntity
        );

        for (Entity e : list) {
            LivingEntity living = (LivingEntity) e;
            if (living.isInvertedHealAndHarm()) {
                isUndeadNearby = true;
                double distance = Mth.sqrt((float) living.distanceToSqr(p_60464_.getX(), p_60464_.getY(), p_60464_.getZ()));
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

        if (!isUndeadNearby) {
            dist = 4;
        }
        p_60463_.setBlockAndUpdate(p_60464_, p_60462_.setValue(LEVEL, 3 - (dist - 1)));

        this.updateNeighborsInFront(p_60463_, p_60464_, p_60462_);
    }

    protected void updateNeighborsInFront(Level worldIn, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.offset(direction.getOpposite().getNormal());
        worldIn.neighborChanged(blockpos, this, pos);
        worldIn.updateNeighborsAtExceptFromFacing(blockpos, this, direction);
    }

    @Override
    public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        if (!p_60515_.is(p_60518_.getBlock())) {
            if (!p_60516_.isClientSide && p_60515_.getValue(LEVEL) > 0 && p_60516_.getBlockTicks().willTickThisTick(p_60517_, this)) {
                this.updateNeighborsInFront(p_60516_, p_60517_, p_60515_.setValue(LEVEL, 0));
            }
        }

        super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        return this.defaultBlockState().setValue(FACING, p_49820_.getNearestLookingDirection().getOpposite().getOpposite());
    }
}
