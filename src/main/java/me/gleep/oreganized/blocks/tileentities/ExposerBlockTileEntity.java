package me.gleep.oreganized.blocks.tileentities;

import me.gleep.oreganized.blocks.ExposerBlock;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class ExposerBlockTileEntity extends TileEntity implements ITickableTileEntity {
    public ExposerBlockTileEntity() {
        super(RegistryHandler.EXPOSER_TE.get());
    }

    @Override
    public void tick() {
        if (this.getWorld() == null) return;
        ((ExposerBlock) this.getBlockState().getBlock()).tick(this.getBlockState(), this.getWorld(), this.getPos(), this.getWorld().getRandom());
    }
}
