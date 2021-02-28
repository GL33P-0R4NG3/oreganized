package me.gleep.oreganized.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class TechnicalNetheriteBlock extends Block {
    public TechnicalNetheriteBlock() {
        super(AbstractBlock.Properties.create(Material.IRON)
                .hardnessAndResistance(8.0F, 1200.0F)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(2)
                .sound(SoundType.NETHERITE));
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 0;
    }
}
