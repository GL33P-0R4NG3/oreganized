package me.gleep.oreganized.entities.tileentities;

import me.gleep.oreganized.blocks.ExposerBlock;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.server.ServerWorld;

public class ExposerBlockTileEntity extends TileEntity implements ITickableTileEntity {
    public ExposerBlockTileEntity() {
        super(RegistryHandler.EXPOSER_TE.get());
    }

    @Override
    public void tick() {
        if (this.getWorld() == null) return;
        if (this.getWorld().isRemote()) return;
        ((ExposerBlock) this.getBlockState().getBlock()).tick(this.getBlockState(), (ServerWorld) this.getWorld(), this.getPos(), this.getWorld().getRandom());
    }
}
