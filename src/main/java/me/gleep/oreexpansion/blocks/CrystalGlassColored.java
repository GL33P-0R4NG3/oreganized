package me.gleep.oreexpansion.blocks;

import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.item.DyeColor;
import org.jetbrains.annotations.NotNull;

public class CrystalGlassColored extends CrystalGlassBase implements IBeaconBeamColorProvider {
    public DyeColor color;

    public CrystalGlassColored(DyeColor color) {
        super();
        this.color = color;
    }

    @Override
    public @NotNull DyeColor getColor() {
        return this.color;
    }
}
