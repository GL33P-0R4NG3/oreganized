package me.gleep.oreganized.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class CrystalGlassBase extends GlassBlock {

    public CrystalGlassBase() {
        super(Properties.create(Material.GLASS)
                .hardnessAndResistance(0.3F)
                .sound(SoundType.GLASS)
                .notSolid()
                .setAllowsSpawn(CrystalGlassBase::neverAllowSpawn)
                .setOpaque(CrystalGlassBase::isntSolid)
                .setSuffocates(CrystalGlassBase::isntSolid)
                .setBlocksVision(CrystalGlassBase::isntSolid));
    }

    private static boolean isntSolid(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return false;
    }

    private static boolean neverAllowSpawn(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, EntityType<?> entityType) {
        return (boolean)false;
    }



}
