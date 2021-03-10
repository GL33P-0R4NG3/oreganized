package me.gleep.oreganized.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class ShrapnelTNT extends Block {
    public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;

    public ShrapnelTNT() {
        super(AbstractBlock.Properties.create(Material.TNT)
                .zeroHardnessAndResistance()
                .sound(SoundType.PLANT)
        );

        this.setDefaultState(this.getDefaultState().with(UNSTABLE, Boolean.valueOf(false)));
    }


}
