package me.gleep.oreganized.blocks;

import net.minecraft.block.Block;

public class TechnicalNetheriteBlockItem extends BlockItemBase {
    public TechnicalNetheriteBlockItem (Block block) {
        super(block);
    }

    @Override
    public boolean isImmuneToFire() {
        return true;
    }
}
