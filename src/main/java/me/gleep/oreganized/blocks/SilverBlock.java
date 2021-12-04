package me.gleep.oreganized.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class SilverBlock extends Block {
    public static final IntegerProperty LEVEL = BlockStateProperties.AGE_7;
    public static final float RANGE = 24.0f;
    boolean isUndeadNearby = false;

    public SilverBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL)
                .strength(5.0f, 6.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL));
        this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, 7));
    }

    /**
     * Get the {@code PathNodeType} for this block. Return {@code null} for vanilla behavior.
     *
     * @param state
     * @param world
     * @param pos
     * @param entity
     * @return the PathNodeType
     */
    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.LAVA;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(LEVEL);
    }

    @Override
    public void onPlace( BlockState pState , Level pLevel , BlockPos pPos , BlockState pOldState , boolean pIsMoving ){
        pLevel.getBlockTicks().scheduleTick( pPos, pState.getBlock(), 1);
        super.onPlace( pState , pLevel , pPos , pOldState , pIsMoving );
    }

    @Override
    public void tick( BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        int dist = 8;

        List<Entity> list = pLevel.getEntities((Entity) null,
                new AABB(pPos.getX() + RANGE, pPos.getY() + RANGE, pPos.getZ() + RANGE,
                        pPos.getX() - RANGE, pPos.getY() - RANGE, pPos.getZ() - RANGE), (Entity entity) -> entity instanceof LivingEntity
        );

        for (Entity e : list) {
            LivingEntity living = (LivingEntity) e;
            if (living.isInvertedHealAndHarm()) {
                isUndeadNearby = true;
                double distance = Math.sqrt(living.distanceToSqr(pPos.getX(), pPos.getY(), pPos.getZ()));
                if (((int) Math.ceil(distance / (RANGE / 8))) < dist) {
                    dist = (int) Math.ceil(distance / (RANGE / 8));

                    /*if (dist > 7) {
                        dist = 7;
                    }*/
                }
            }
        }

        if (!isUndeadNearby) {
            dist = 8;
        }
        pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, dist - 1));
        pLevel.getBlockTicks().scheduleTick( pPos, pState.getBlock(), 1);
    }
}
