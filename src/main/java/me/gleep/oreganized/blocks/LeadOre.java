package me.gleep.oreganized.blocks;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class LeadOre extends OreBlock {
    public LeadOre() {
        super(BlockBehaviour.Properties.of(Material.METAL)
                .strength(3.0F, 3.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE), UniformInt.of(0, 3));
    }

}