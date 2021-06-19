package me.gleep.oreganized.blocks;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class LeadCoating extends Block {

    public static final int RANGE = 4;
    public static final IntegerProperty LEVEL = IntegerProperty.create("water_level", 0, 3);
    public static final IntegerProperty HEIGHT =  IntegerProperty.create("height", 0, 255);
    public static final BooleanProperty IS_BASE = BooleanProperty.create("is_base");

    public LeadCoating() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(4.0F, 5.0F)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(1)
                .sound(SoundType.METAL));

        this.setDefaultState(this.stateContainer.getBaseState().with(LEVEL, Integer.valueOf(0)).with(IS_BASE, false).with(HEIGHT, Integer.valueOf(0)));
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockPos basePos = pos.down();
        while (worldIn.getBlockState(basePos).getBlock().equals(RegistryHandler.LEAD_COATING.get())) basePos = basePos.down();
        basePos = basePos.up();

        BlockPos topPos = basePos.up();
        while (worldIn.getBlockState(topPos).getBlock().equals(RegistryHandler.LEAD_COATING.get())) {
            worldIn.setBlockState(topPos, worldIn.getBlockState(topPos).with(IS_BASE, false).with(HEIGHT, basePos.getY()), 2);
            topPos = topPos.up();
        }
        topPos = topPos.down();

        BlockState baseState = worldIn.getBlockState(basePos).with(IS_BASE, true).with(HEIGHT, topPos.getY());
        worldIn.setBlockState(basePos, baseState, 2);

    }

    /**
     * Called after a player destroys this Block - the position pos may no longer hold the state indicated.
     */
    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        if (worldIn.getBlockState(pos.up()).getBlock().equals(RegistryHandler.LEAD_COATING.get())) {
            worldIn.getBlockState(pos.up()).getBlock().onBlockPlacedBy((World)worldIn, pos.up(), worldIn.getBlockState(pos.up()), null, ItemStack.EMPTY);
        }

        if (worldIn.getBlockState(pos.down()).getBlock().equals(RegistryHandler.LEAD_COATING.get())) {
            worldIn.getBlockState(pos.down()).getBlock().onBlockPlacedBy((World)worldIn, pos.down(), worldIn.getBlockState(pos.down()), null, ItemStack.EMPTY);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LEVEL).add(IS_BASE).add(HEIGHT);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    /**
     * Performs a random tick on a block.
     */
    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        this.tick(state, worldIn, pos, random);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.get(IS_BASE) && state.get(LEVEL) > 0) {
            fertilize((World) worldIn, pos, state);
        }
    }

    @Override
    public void fillWithRain(World worldIn, BlockPos pos) {
        float f = worldIn.getBiome(pos).getTemperature(pos);
        if (!(f < 0.15F)) {
            BlockState state = worldIn.getBlockState(pos);

            BlockPos basePos = new BlockPos(pos.getX(), state.get(HEIGHT), pos.getZ());
            BlockState baseState = worldIn.getBlockState(basePos);
            if (!flow(worldIn, basePos, baseState)) {
                worldIn.setBlockState(pos, state.with(LEVEL, Integer.valueOf(state.get(LEVEL) + 1)));
            }
        }
    }

    public void fertilize(World world, BlockPos pos, BlockState blockState) {
        int minY = pos.getY();
        int maxY = blockState.get(HEIGHT);
        int maxwater = 0;
        int usedwater = 0;

        for (int i = maxY; i >= minY; i--) {
            BlockPos pos0 = new BlockPos(pos.getX(), i, pos.getZ());
            BlockState state0 = world.getBlockState(pos0);
            maxwater += state0.get(LEVEL);
        }

        for (int i = maxwater; i > 0; i--) {
            int rndX = world.getRandom().nextInt(RANGE * 2 + 1) - 4;
            int rndZ = world.getRandom().nextInt(RANGE * 2 + 1) - 4;
            BlockPos pos1 = new BlockPos(pos.getX() + rndX, minY - 1, pos.getZ() + rndZ);
            BlockState state1 = world.getBlockState(pos1);

            if (state1.getBlock() instanceof IGrowable) {
                IGrowable igrowable = (IGrowable) state1.getBlock();
                if (igrowable.canGrow(world, pos1, state1, world.isRemote)) {
                    if (world instanceof ServerWorld) {
                        if (igrowable.canUseBonemeal(world, world.rand, pos1, state1)) {
                            igrowable.grow((ServerWorld) world, world.rand, pos1, state1);
                            usedwater++;
                        }
                    }
                }
            }
        }

        for (int i = maxY; i >= minY; i--) {
            BlockPos pos0 = new BlockPos(pos.getX(), i, pos.getZ());
            BlockState state0 = world.getBlockState(pos0);
            int storedwater = state0.get(LEVEL);

            if (storedwater < 0) break;

            if (storedwater > usedwater) {
                world.setBlockState(pos0, state0.with(LEVEL, Integer.valueOf(storedwater - usedwater)), 2);
            } else {
                usedwater -= storedwater;
                world.setBlockState(pos0, state0.with(LEVEL, Integer.valueOf(0)), 2);
            }
        }
    }

    public boolean flow(World world, BlockPos pos, BlockState blockState) {
        if (blockState.get(IS_BASE)) {
            if (pos.getY() > blockState.get(HEIGHT)) return false;

            if (world.getBlockState(pos).get(LEVEL) == 3) {
                return flow(world, pos.up(), blockState);
            } else {
                BlockState prevState = world.getBlockState(pos);
                world.setBlockState(pos, prevState.with(LEVEL, Integer.valueOf(prevState.get(LEVEL) + 1)), 2);
                return true;
            }
        }

        return false;
    }

    /**
     * @deprecated call via {@link BlockState#hasComparatorInputOverride()} whenever possible. Implementing/overriding
     * is fine.
     */
    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    /**
     * @deprecated call via {@link IBlockState#getComparatorInputOverride(World, BlockPos)} whenever possible.
     * Implementing/overriding is fine.
     */
    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return blockState.get(LEVEL);
    }
}
