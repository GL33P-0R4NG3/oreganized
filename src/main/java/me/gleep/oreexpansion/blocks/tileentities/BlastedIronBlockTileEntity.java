package me.gleep.oreexpansion.blocks.tileentities;

import me.gleep.oreexpansion.util.NBTHelper;
import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BlastedIronBlockTileEntity extends TileEntity {

    /*public BlastedIronBlockTileEntity() {
        this(RegistryHandler.BLASTED_IRON_BLOCK_TE.get());
    }*/

    public BlastedIronBlockTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        CompoundNBT initValues = nbt.getCompound("initvalues");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("initvalues", NBTHelper.toNBT(this));
        return super.write(compound);
    }



}
