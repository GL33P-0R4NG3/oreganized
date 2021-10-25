package me.gleep.oreganized.blocks;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;
import java.util.Random;

public class LeadCoating extends Block {

    public static final int RANGE = 4;
    public static final IntegerProperty LEVEL = IntegerProperty.create("water_level", 0, 3);
    public static final IntegerProperty HEIGHT =  IntegerProperty.create("height", 0, 255);
    public static final BooleanProperty IS_BASE = BooleanProperty.create("is_base");

    public LeadCoating() {
        super(BlockBehaviour.Properties.of(Material.METAL)
                .strength(4.0F, 5.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL));

        this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, Integer.valueOf(0)).setValue(IS_BASE, false).setValue(HEIGHT, Integer.valueOf(0)));
    }


    @Override
    public void setPlacedBy(Level p_49847_, BlockPos p_49848_, BlockState p_49849_, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
        BlockPos basePos = p_49848_.below();
        while (p_49847_.getBlockState(basePos).getBlock().equals(RegistryHandler.LEAD_COATING.get())) basePos = basePos.below();
        basePos = basePos.above();

        BlockPos topPos = basePos.above();
        while (p_49847_.getBlockState(topPos).getBlock().equals(RegistryHandler.LEAD_COATING.get())) {
            p_49847_.setBlockAndUpdate(topPos, p_49847_.getBlockState(topPos).setValue(IS_BASE, false).setValue(HEIGHT, basePos.getY()));
            topPos = topPos.above();
        }
        topPos = topPos.below();

        BlockState baseState = p_49847_.getBlockState(basePos).setValue(IS_BASE, true).setValue(HEIGHT, topPos.getY());
        p_49847_.setBlockAndUpdate(basePos, baseState);
    }


    @Override
    public void destroy(LevelAccessor p_49860_, BlockPos p_49861_, BlockState p_49862_) {
        if (p_49860_.getBlockState(p_49861_.above()).getBlock().equals(RegistryHandler.LEAD_COATING.get())) {
            p_49860_.getBlockState(p_49861_.above()).getBlock().setPlacedBy((Level)p_49860_, p_49861_.above(), p_49860_.getBlockState(p_49861_.above()), null, ItemStack.EMPTY);
        }

        if (p_49860_.getBlockState(p_49861_.below()).getBlock().equals(RegistryHandler.LEAD_COATING.get())) {
            p_49860_.getBlockState(p_49861_.below()).getBlock().setPlacedBy((Level)p_49860_, p_49861_.below(), p_49860_.getBlockState(p_49861_.below()), null, ItemStack.EMPTY);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(LEVEL).add(IS_BASE).add(HEIGHT);
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return true;
    }

    @Override
    public void randomTick(BlockState p_60551_, ServerLevel p_60552_, BlockPos p_60553_, Random p_60554_) {
        this.tick(p_60551_, p_60552_, p_60553_, p_60554_);
    }

    @Override
    public void tick(BlockState p_60462_, ServerLevel p_60463_, BlockPos p_60464_, Random p_60465_) {
        if (p_60462_.getValue(IS_BASE) && p_60462_.getValue(LEVEL) > 0) {
            fertilize((Level) p_60463_, p_60464_, p_60462_);
        }
    }



    @Override
    public void handlePrecipitation(BlockState p_152450_, Level p_152451_, BlockPos p_152452_, Biome.Precipitation p_152453_) {
        float f = p_152451_.getBiome(p_152452_).getTemperature(p_152452_);
        if (!(f < 0.15F)) {
            BlockState state = p_152451_.getBlockState(p_152452_);

            BlockPos basePos = new BlockPos(p_152452_.getX(), state.getValue(HEIGHT), p_152452_.getZ());
            BlockState baseState = p_152451_.getBlockState(basePos);
            if (!flow(p_152451_, basePos, baseState)) {
                p_152451_.setBlockAndUpdate(p_152452_, state.setValue(LEVEL, Integer.valueOf(state.getValue(LEVEL) + 1)));
            }
        }
    }

    public void fertilize(Level world, BlockPos pos, BlockState blockState) {
        int minY = pos.getY();
        int maxY = blockState.getValue(HEIGHT);
        int maxwater = 0;
        int usedwater = 0;

        for (int i = maxY; i >= minY; i--) {
            BlockPos pos0 = new BlockPos(pos.getX(), i, pos.getZ());
            BlockState state0 = world.getBlockState(pos0);
            maxwater += state0.getValue(LEVEL);
        }

        for (int i = maxwater; i > 0; i--) {
            int rndX = world.getRandom().nextInt(RANGE * 2 + 1) - 4;
            int rndZ = world.getRandom().nextInt(RANGE * 2 + 1) - 4;
            BlockPos pos1 = new BlockPos(pos.getX() + rndX, minY - 1, pos.getZ() + rndZ);
            BlockState state1 = world.getBlockState(pos1);

            if (state1.getBlock() instanceof BonemealableBlock) {
                BonemealableBlock bonemealable = (BonemealableBlock) state1.getBlock();
                if (bonemealable.isValidBonemealTarget(world, pos1, state1, world.isClientSide())) {
                    if (world instanceof ServerLevel) {
                        if (bonemealable.isBonemealSuccess(world, world.random, pos1, state1)) {
                            bonemealable.performBonemeal((ServerLevel) world, world.random, pos1, state1);
                            usedwater++;
                        }
                    }
                }
            }
        }

        for (int i = maxY; i >= minY; i--) {
            BlockPos pos0 = new BlockPos(pos.getX(), i, pos.getZ());
            BlockState state0 = world.getBlockState(pos0);
            int storedwater = state0.getValue(LEVEL);

            if (storedwater < 0) break;

            if (storedwater > usedwater) {
                world.setBlockAndUpdate(pos0, state0.setValue(LEVEL, Integer.valueOf(storedwater - usedwater)));
            } else {
                usedwater -= storedwater;
                world.setBlockAndUpdate(pos0, state0.setValue(LEVEL, Integer.valueOf(0)));
            }
        }
    }

    public boolean flow(Level world, BlockPos pos, BlockState blockState) {
        if (blockState.getValue(IS_BASE)) {
            if (pos.getY() > blockState.getValue(HEIGHT)) return false;

            if (world.getBlockState(pos).getValue(LEVEL) == 3) {
                return flow(world, pos.above(), blockState);
            } else {
                BlockState prevState = world.getBlockState(pos);
                world.setBlockAndUpdate(pos, prevState.setValue(LEVEL, Integer.valueOf(prevState.getValue(LEVEL) + 1)));
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState p_60457_) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState p_60487_, Level p_60488_, BlockPos p_60489_) {
        return p_60487_.getValue(LEVEL);
    }

}
