package me.gleep.oreganized.entities.tileentities;

import me.gleep.oreganized.util.NBTHelper;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class LeadCoatingTileEntity extends TileEntity {

    //Max range of base block
    public static final int RANGE = 4;
    //Max height from base block
    public static final int RANGE_Y = 9;

    private boolean init = false;
    //Whether the block is base block
    private boolean isBase;
    //Coordinates of the base block
    private int baseX;
    private int baseY;
    private int baseZ;

    public LeadCoatingTileEntity() {
        super(RegistryHandler.LEAD_COATING_TE.get());
        init();
    }


    private void init() {
        init = true;
        findBase();
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        CompoundNBT leadProperties = nbt.getCompound("leadProperties");
        if (leadProperties != null) {
            this.isBase = leadProperties.getBoolean("isBase");
            this.baseX = leadProperties.getInt("x");
            this.baseY = leadProperties.getInt("y");
            this.baseZ = leadProperties.getInt("z");
            init = true;
        } else init();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("leadProperties", NBTHelper.toNBT(this));
        return super.write(compound);
    }

    private void findBase() {
        for (int x = -4; x < RANGE - 4; x++) {
            if (x == 0) continue;
            for (int z = -4; z <  RANGE - 4; z++) {
                if (z == 0) continue;
                for (int y = 0; y > -5; y--) {
                    if (y == 0) continue;
                    BlockState bS = this.world.getBlockState(new BlockPos(x, y, z));
                    if (bS.getBlock().equals(RegistryHandler.LEAD_COATING.get())) {

                    }
                }
            }
        }
    }

    public int getBaseX() {
        return this.baseX;
    }

    public int getBaseY() {
        return this.baseY;
    }

    public int getBaseZ() {
        return this.baseZ;
    }

    public boolean isBase() {
        return this.isBase;
    }
}
