package me.gleep.oreganized.items;

import net.minecraft.world.level.block.Block;

public class TechnicalNetheriteBlockItem extends BlockItemBase {
    public TechnicalNetheriteBlockItem (Block block) {
        super(block);
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }
}
