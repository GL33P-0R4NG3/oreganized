package me.gleep.oreganized.blocks;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SpottedGlance extends Block {
    public SpottedGlance() {
        super(BlockBehaviour.Properties.copy(RegistryHandler.GLANCE.get()));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction p_60542_, BlockState neighbour, LevelAccessor level, BlockPos pos, BlockPos neighbourPos) {
        if (level.isWaterAt(neighbourPos)) {
            ItemStack stack = new ItemStack(RegistryHandler.LEAD_NUGGET.get(), level.getRandom().nextInt(2) + 1);
            Containers.dropItemStack((Level) level, pos.getX(), pos.getY(), pos.getZ(), stack);

            return RegistryHandler.GLANCE.get().defaultBlockState();
        }

        return super.updateShape(state, p_60542_, neighbour, level, pos, neighbourPos);
    }

    @Override
    public void neighborChanged(BlockState p_60509_, Level level, BlockPos pos, Block p_60512_, BlockPos neighbourPos, boolean p_60514_) {
        if (level.isWaterAt(neighbourPos)) {
            level.setBlockAndUpdate(pos, RegistryHandler.GLANCE.get().defaultBlockState());

            ItemStack stack = new ItemStack(RegistryHandler.LEAD_NUGGET.get(), level.getRandom().nextInt(2) + 1);
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
        }

        super.neighborChanged(p_60509_, level, pos, p_60512_, neighbourPos, p_60514_);
    }
}
