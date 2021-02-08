package me.gleep.oreganized.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.List;
import java.util.Random;

public class SilverBlock extends Block {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_3;
    public static final float RANGE = 16.0f;
    boolean isUndeadNearby = false;

    public SilverBlock() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(2)
                .sound(SoundType.METAL));
        this.setDefaultState(this.getDefaultState().with(LEVEL, 3));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @org.jetbrains.annotations.Nullable MobEntity entity) {
        return PathNodeType.LAVA;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(LEVEL);
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        int dist = 4;

        List<Entity> list = worldIn.getEntitiesInAABBexcluding(null,
                new AxisAlignedBB(pos.getX() + RANGE, pos.getY() + RANGE, pos.getZ() + RANGE,
                        pos.getX() - RANGE, pos.getY() - RANGE, pos.getZ() - RANGE),
                        null);

        for (Entity e : list) {
            if (e.isLiving()) {
                LivingEntity living = (LivingEntity) e;
                if (living.isEntityUndead()) {
                    isUndeadNearby = true;
                    double distance = MathHelper.sqrt(living.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()));
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
        }

        if (!isUndeadNearby) {
            dist = 4;
        }
        worldIn.setBlockState(pos, stateIn.with(LEVEL, dist - 1));
    }
}
