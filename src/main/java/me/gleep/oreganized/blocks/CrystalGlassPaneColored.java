package me.gleep.oreganized.blocks;

import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.item.DyeColor;
import org.jetbrains.annotations.NotNull;

public class CrystalGlassPaneColored extends CrystalGlassPaneBase implements IBeaconBeamColorProvider {
    final DyeColor color;

    public CrystalGlassPaneColored(DyeColor color) {
        this.color = color;
    }

    @NotNull
    @Override
    public DyeColor getColor() {
        return this.color;
    }
}
