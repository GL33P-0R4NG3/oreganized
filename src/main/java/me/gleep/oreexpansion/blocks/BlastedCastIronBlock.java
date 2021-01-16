package me.gleep.oreexpansion.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BlastedCastIronBlock extends Block {
    public BlastedCastIronBlock() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(4.0F, 5.0F)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(1)
                .sound(SoundType.METAL));
    }
}
