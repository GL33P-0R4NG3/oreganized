package me.gleep.oreganized.capabilities.stunning;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class Stunning implements IStunning, INBTSerializable<CompoundTag>{
    private BlockPos previousPos = new BlockPos(0,0,0);
    private int stunTime = 0;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        CompoundTag previousPos = new CompoundTag();
        previousPos.putLong("X", this.previousPos.getX());
        previousPos.putLong("Y", this.previousPos.getY());
        previousPos.putLong("Z", this.previousPos.getZ());
        tag.put("PreviousPos", previousPos );
        tag.putInt( "StunTime", this.stunTime);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        CompoundTag previousPos = nbt.getCompound("PreviousPos");
        this.previousPos = new BlockPos(previousPos.getLong("X"), previousPos.getLong("Y"), previousPos.getLong("Z"));
        this.stunTime = nbt.getInt("StunTime");
    }

    @Override
    public void setPreviousPos(BlockPos pos) {
        this.previousPos = pos;
    }

    @Override
    public BlockPos getPreviousPos() {
        return this.previousPos;
    }

    @Override
    public void setRemainingStunTime(int time) {
        this.stunTime = time;
    }

    @Override
    public int getRemainingStunTime() {
        return this.stunTime;
    }
}
