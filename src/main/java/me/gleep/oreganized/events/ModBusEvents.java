package me.gleep.oreganized.events;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.capabilities.engravedblockscap.CapabilityEngravedBlocks;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents{
    @SubscribeEvent
    public static void registerCapabilities( RegisterCapabilitiesEvent event ){
        CapabilityEngravedBlocks.register( event );
    }
}
