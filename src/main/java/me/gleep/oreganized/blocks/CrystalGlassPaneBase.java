package me.gleep.oreganized.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import org.jetbrains.annotations.Nullable;

public class CrystalGlassPaneBase extends PaneBlock {

    public CrystalGlassPaneBase() {
        super(Properties.create(Material.GLASS)
                .hardnessAndResistance(0.3F)
                .sound(SoundType.GLASS)
                .notSolid());
    }

}
