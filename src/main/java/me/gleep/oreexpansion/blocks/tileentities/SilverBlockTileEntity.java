package me.gleep.oreexpansion.blocks.tileentities;

import me.gleep.oreexpansion.OreExpansion;
import me.gleep.oreexpansion.util.NBTHelper;
import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SilverBlockTileEntity extends TileEntity implements ITickableTileEntity {

    public int x, y, z, distance, tick;
    public LivingEntity entity;
    boolean init = false;

    public SilverBlockTileEntity() {
        this(RegistryHandler.SILVER_BLOCK_TE.get());
    }

    private SilverBlockTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        if (!init) init();
        tick++;
        if (tick == 50) {
            tick = 0;
            execute();
        }
    }

    private void execute() {

    }

    private void init() {
        init = true;
        x = this.pos.getX() - 1;
        y = this.pos.getY() - 1;
        z = this.pos.getZ() - 1;
        tick = 0;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        CompoundNBT initValues = nbt.getCompound("initvalues");
        if (initValues != null) {
            this.x = initValues.getInt("x");
            this.y = initValues.getInt("y");
            this.z = initValues.getInt("z");
            this.tick = 0;
            init = true;
        } else init();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("initvalues", NBTHelper.toNBT(this));
        return super.write(compound);
    }
}
