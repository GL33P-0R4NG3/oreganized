package me.gleep.oreganized.capabilities.engravedblockscap;

import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.items.IItemHandler;

public class CapabilityEngravedBlocks{
    public static final Capability<IEngravedBlocks> ENGRAVED_BLOCKS_CAPABILITY = CapabilityManager.get( new CapabilityToken <>(){});

    public static void register(RegisterCapabilitiesEvent event)
    {
        event.register(IEngravedBlocks.class);
    }

}

