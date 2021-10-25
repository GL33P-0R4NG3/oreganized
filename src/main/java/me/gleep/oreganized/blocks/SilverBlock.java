package me.gleep.oreganized.blocks;

import net.minecraft.core.BlockPos;
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
    public static final IntegerProperty LEVEL = BlockStateProperties.AGE_3;
    public static final float RANGE = 16.0f;
    boolean isUndeadNearby = false;

    public SilverBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL)
                .strength(5.0f, 6.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL));
        this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, 3));
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
    public void animateTick(BlockState p_49888_, Level p_49889_, BlockPos p_49890_, Random p_49891_) {
        int dist = 4;

        List<Entity> list = p_49889_.getEntities((Entity) null,
                new AABB(p_49890_.getX() + RANGE, p_49890_.getY() + RANGE, p_49890_.getZ() + RANGE,
                        p_49890_.getX() - RANGE, p_49890_.getY() - RANGE, p_49890_.getZ() - RANGE), (Entity entity) -> entity instanceof LivingEntity
        );

        for (Entity e : list) {
            LivingEntity living = (LivingEntity) e;
            if (living.isInvertedHealAndHarm()) {
                isUndeadNearby = true;
                double distance = Math.sqrt(living.distanceToSqr(p_49890_.getX(), p_49890_.getY(), p_49890_.getZ()));
                if (distance < RANGE && ((int) Math.ceil(distance / (RANGE / 4))) < dist) {
                    if (distance <= 6) {
                        dist = 1;
                    } else dist = Math.max((int) Math.ceil(distance / (RANGE / 4)), 2);

                    if (dist > 3) {
                        dist = 3;
                    }
                }
            }
        }

        if (!isUndeadNearby) {
            dist = 4;
        }
        p_49889_.setBlockAndUpdate(p_49890_, p_49888_.setValue(LEVEL, dist - 1));
    }
}
