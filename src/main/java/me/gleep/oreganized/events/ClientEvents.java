package me.gleep.oreganized.events;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.client.model.ElectrumArmorModel;
import me.gleep.oreganized.items.ElectrumArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ElectrumArmorModel.ELECTRUM_ARMOR, ElectrumArmorModel::createBodyLayer);
    }

}
