package me.gleep.oreganized.capabilities.engravedblockscap;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EngravedBlocksProvider implements ICapabilitySerializable<CompoundTag>
{
    private final Capability<IEngravedBlocks> capability = CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY;
    private final EngravedBlocks instance = new EngravedBlocks();
    private final LazyOptional <EngravedBlocks> implementation = LazyOptional.of( () -> instance );

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

