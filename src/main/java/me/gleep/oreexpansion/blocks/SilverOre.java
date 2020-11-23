package me.gleep.oreexpansion.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class SilverOre extends Block{
    public SilverOre() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(3.0f, 3.0f)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(2)
                .sound(SoundType.STONE));
    }
}