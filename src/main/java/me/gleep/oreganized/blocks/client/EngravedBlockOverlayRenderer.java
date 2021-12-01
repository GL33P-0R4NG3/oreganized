package me.gleep.oreganized.blocks.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.capabilities.engravedblockscap.CapabilityEngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.EngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.IEngravedBlocks;
import me.gleep.oreganized.events.ModEvents;
import me.gleep.oreganized.util.GeneralUtility;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.sqrt;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, value = Dist.CLIENT)
public class EngravedBlockOverlayRenderer{

    public static EngravedBlocks.Face[] faces = {
            EngravedBlocks.Face.DOWN_N,
            EngravedBlocks.Face.DOWN_S,
            EngravedBlocks.Face.DOWN_E,
            EngravedBlocks.Face.DOWN_W,
            EngravedBlocks.Face.UP_N,
            EngravedBlocks.Face.UP_S,
            EngravedBlocks.Face.UP_E,
            EngravedBlocks.Face.UP_W,
            EngravedBlocks.Face.BACK,
            EngravedBlocks.Face.FRONT,
            EngravedBlocks.Face.LEFT,
            EngravedBlocks.Face.RIGHT
    };
    @SubscribeEvent
    public static void renderEngravedBlocksOverlay( RenderWorldLastEvent ev )
    {
        Minecraft mc = Minecraft.getInstance();
        Level level = mc.player.level;
        PoseStack matrix = ev.getMatrixStack();
        Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
        IEngravedBlocks capability = level.getCapability( CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY,
                null ).orElse( null );

        if(capability.getEngravedBlocks().isEmpty() || ModEvents.recentlyActivatedPiston) return;

        for(BlockPos pos : capability.getEngravedBlocks())
        {
            if(mc.gameRenderer.getRenderDistance() < sqrt(mc.player.distanceToSqr( new Vec3(pos.getX(), pos.getY(), pos.getZ()) ))) break;
            for(EngravedBlocks.Face face : faces){
                matrix.pushPose();
                RenderSystem.enableDepthTest();
                matrix.translate(-cameraPos.x(), -cameraPos.y(), -cameraPos.z());
                matrix.translate(pos.getX() + face.translation.x(),pos.getY() + face.translation.y(),pos.getZ() + face.translation.z());
                //matrix.translate(pos.getX() + face.translation.x(),pos.getY() + face.translation.y(),pos.getZ() + 0.95f);
                matrix.scale( 0.01f, 0.01f, 0.01f );
                matrix.mulPose( Vector3f.XP.rotationDegrees( face.rotation.x() ) );
                matrix.mulPose( Vector3f.YP.rotationDegrees( face.rotation.y() ) );
                matrix.mulPose( Vector3f.ZP.rotationDegrees( face.rotation.z() ) );
                /*matrix.mulPose( Vector3f.XP.rotationDegrees( face.rotation.x() ) );
                matrix.mulPose( Vector3f.YP.rotationDegrees( face.rotation.y() ) );
                matrix.mulPose( Vector3f.ZP.rotationDegrees( -90 ) );*/
                if(capability.getStringArray( pos, face ) != null){
                    int i = 0;
                    for(String s : capability.getStringArray( pos , face )){
                        matrix.translate(0.01,0.01,0.01);
                        mc.font.draw( matrix , s , 45 - mc.font.width( s )/2 + 1.0f , i * 9 + 0.4f,level.getBlockState( pos ).getBlock() != RegistryHandler.SMOOTH_NETHER_BRICKS.get() ? capability.getColor( pos ) : 4991023);
                        matrix.translate(-0.01,-0.01,-0.01);
                        mc.font.draw( matrix , s , 45 - mc.font.width( s )/2 , i * 9 ,level.getBlockState( pos ).getBlock() != RegistryHandler.SMOOTH_NETHER_BRICKS.get() ? GeneralUtility.modifyColorBrightness( capability.getColor( pos ), 0.2f) : 787976);
                        i++;
                    }
                }
                RenderSystem.disableDepthTest();
                matrix.popPose();
            }
        }
    }

}
