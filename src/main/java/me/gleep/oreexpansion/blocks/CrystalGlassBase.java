package me.gleep.oreexpansion.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.Nullable;

public class CrystalGlassBase extends GlassBlock {
    public CrystalGlassBase() {
        super(Properties.create(Material.GLASS)
                .hardnessAndResistance(0.3F)
                .sound(SoundType.GLASS)
                .notSolid());
    }

    /*@Override
    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1;
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
