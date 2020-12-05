package me.gleep.oreexpansion.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class LeadOre extends OreBlock {
    public LeadOre() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(3.0f, 3.0f)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(2)
                .sound(SoundType.STONE));
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) { return 0; }
}