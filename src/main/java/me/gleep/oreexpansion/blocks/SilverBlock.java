package me.gleep.oreexpansion.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class SilverBlock extends Block {
    public SilverBlock() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(2)
                .sound(SoundType.METAL));
    }
}
