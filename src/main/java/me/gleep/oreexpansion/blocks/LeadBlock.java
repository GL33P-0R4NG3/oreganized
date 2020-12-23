package me.gleep.oreexpansion.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LeadBlock extends Block {
    public LeadBlock() {
        super(Block.Properties.create(Material.IRON)
                .zeroHardnessAndResistance()
                .notSolid()
                /*.setLightLevel((light) -> { return 2; })*/);
    }

}
