package me.gleep.oreganized.entities.tileentities;

import me.gleep.oreganized.blocks.ExposerBlock;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExposerBlockEntity extends BlockEntity {
    public ExposerBlockEntity(BlockPos p_155134_, BlockState p_155135_) {
        super(RegistryHandler.EXPOSER_TE.get(), p_155134_, p_155135_);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if (t.getLevel() == null) return;
        if (t.getLevel().isClientSide()) return;
        ((ExposerBlock) t.getBlockState().getBlock()).tick(t.getBlockState(), (ServerLevel) t.getLevel(), t.getBlockPos(), t.getLevel().getRandom());
    }
}
