package me.gleep.oreexpansion.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.NonNullList;
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

    /*@Override
    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public boolean isVariableOpacity() {
        return true;
    }*/

    /*@Override
    public boolean isTransparent(BlockState state) {
        return true;
    }*/

    /*@Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        player.addStat(Stats.BLOCK_MINED.get(this));
        player.addExhaustion(0.005F);
        ListNBT nbt = stack.getEnchantmentTagList();
        for (INBT s : nbt) {
            if (s.getString().equals("silk_touch")) {
                spawnDrops(state, worldIn, pos, te, player, stack);
            }
        }
    }*/

}
