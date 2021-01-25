package me.gleep.oreganized.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class CastIronBlock extends Block {
    public CastIronBlock() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(4.5F, 5.5F)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(1)
                .sound(SoundType.METAL));
    }

}
