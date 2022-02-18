package me.gleep.oreganized.capabilities.stunning;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StunningProvider implements ICapabilitySerializable<CompoundTag>
{
    private final Capability<IStunning> capability = CapabilityStunning.STUNNING_CAPABILITY;
    private final Stunning instance = new Stunning();
    private final LazyOptional <Stunning> implementation = LazyOptional.of( () -> instance );

    public void invalidate() {
        implementation.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if(cap == capability) return implementation.cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() { return instance.serializeNBT(); }

    @Override
    public void deserializeNBT(CompoundTag nbt) { instance.deserializeNBT(nbt); }
}

