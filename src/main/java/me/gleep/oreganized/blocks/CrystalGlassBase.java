package me.gleep.oreganized.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class CrystalGlassBase extends GlassBlock {

    public CrystalGlassBase() {
        super(Properties.of(Material.GLASS)
                .strength(0.3F)
                .sound(SoundType.GLASS)
                .noOcclusion()
                .isValidSpawn(CrystalGlassBase::never)
                .isRedstoneConductor(CrystalGlassBase::never)
                .isSuffocating(CrystalGlassBase::never)
                .isViewBlocking(CrystalGlassBase::never)
        );
    }

    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    private static boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return (boolean)false;
    }

}
