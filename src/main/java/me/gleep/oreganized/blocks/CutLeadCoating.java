package me.gleep.oreganized.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class CutLeadCoating extends Block {
    public CutLeadCoating() {
        super(Properties.of(Material.METAL)
                .strength(4.0F, 5.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL)
        );
    }


}
