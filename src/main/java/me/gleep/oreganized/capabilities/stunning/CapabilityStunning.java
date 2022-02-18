package me.gleep.oreganized.capabilities.stunning;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityStunning {
    public static final Capability<IStunning> STUNNING_CAPABILITY = CapabilityManager.get(new CapabilityToken <>(){});

    public static void register(RegisterCapabilitiesEvent event)
    {
        event.register(IStunning.class);
    }

}

