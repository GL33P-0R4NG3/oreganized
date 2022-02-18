package me.gleep.oreganized.particles;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static me.gleep.oreganized.Oreganized.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRegisterer{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void particleFactoryRegistrationEvent(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(RegistryHandler.DRIPPING_LEAD.get(), CustomDrippingParticle.LeadHangProvider::new);
        Minecraft.getInstance().particleEngine.register(RegistryHandler.FALLING_LEAD.get(), CustomDrippingParticle.LeadFallProvider::new);
        Minecraft.getInstance().particleEngine.register(RegistryHandler.LANDING_LEAD.get(), CustomDrippingParticle.LeadLandProvider::new);
        Minecraft.getInstance().particleEngine.register(RegistryHandler.LEAD_SHRAPNEL.get(), LeadShrapnelParticle.Provider::new);
    }
}
