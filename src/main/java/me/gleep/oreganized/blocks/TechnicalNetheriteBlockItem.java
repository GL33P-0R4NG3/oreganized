package me.gleep.oreganized.blocks;

import me.gleep.oreganized.util.RegistryHandler;

public class TechnicalNetheriteBlockItem extends BlockItemBase {
    public TechnicalNetheriteBlockItem () {
        super(RegistryHandler.TECHNICAL_NETHERITE_BLOCK.get());
    }

    @Override
    public boolean isImmuneToFire() {
        return true;
    }
}
