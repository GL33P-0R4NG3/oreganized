package me.gleep.oreganized.screens;

import me.gleep.oreganized.Oreganized;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class StunnedOverlayRenderer{

    private float alpha = 0.0F;
    private static final ResourceLocation STUNNED_OVERLAY_LOCATION = new ResourceLocation(Oreganized.MOD_ID, "overlays/stunning_overlay.png");

    @SubscribeEvent
    public void renderOverlay( RenderGameOverlayEvent.Pre event ) {
        /**
         * Commented out as it's experimental
         * author: zKryle
         * -
        if(event.getType() == RenderGameOverlayEvent.ElementType.LAYER){
            Minecraft mc = Minecraft.getInstance();

            if(mc.level == null){
                return;
            }

            ((ForgeIngameGui)mc.gui).setupOverlayRenderState( true, false );

            if(mc.player.hasEffect( ModPotions.STUNNED )){
                if(alpha < 0.2F) alpha += 0.0001F;
            }else{
                if(alpha > 0.0F) alpha -= 0.0001F;
            }

            if(alpha > 0.0F){
                mc.gui.renderTextureOverlay( STUNNED_OVERLAY_LOCATION , alpha);
            }
        }
        */
    }
}
