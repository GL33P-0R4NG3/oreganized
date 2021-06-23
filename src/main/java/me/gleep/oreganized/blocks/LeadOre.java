package me.gleep.oreganized.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class LeadOre extends OreBlock {
    public LeadOre() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(3.0F, 3.0F)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(2)
                .sound(SoundType.STONE));
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        if (silktouch > 0) return 0;
        if (reader.getChunk(pos).getWorldForge() != null) {
            return MathHelper.nextInt(reader.getChunk(pos).getWorldForge().getRandom(), 0, 2);
        }
        return fortune + 1;
    }
}