package me.gleep.oreganized.blocks.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.capabilities.engravedblockscap.CapabilityEngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.EngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.IEngravedBlocks;
import me.gleep.oreganized.util.GeneralUtility;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
    public static void renderEngravedBlocksOverlay( RenderLevelLastEvent ev ){
        Minecraft mc = Minecraft.getInstance();
        Level level = mc.player.level;
        PoseStack matrix = ev.getPoseStack();
        Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
        IEngravedBlocks capability = level.getCapability( CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY,
                null ).orElse( null );

        if(capability.getEngravedBlocks().isEmpty()) return;

        for(BlockPos pos : capability.getEngravedBlocks()){
            if(mc.gameRenderer.getRenderDistance() * mc.gameRenderer.getRenderDistance() < mc.player.distanceToSqr( new Vec3(pos.getX(), pos.getY(), pos.getZ()) )) continue;
            for(EngravedBlocks.Face face : faces){
                if(level.getBlockState(pos.relative( face.direction )).isSolidRender(level, pos.relative( face.direction ))) continue;
                matrix.pushPose();
                RenderSystem.enableDepthTest();
                matrix.translate(-cameraPos.x(), -cameraPos.y(), -cameraPos.z());
                matrix.translate(pos.getX() + face.translation.x(),pos.getY() + face.translation.y(),pos.getZ() + face.translation.z());
                matrix.scale( 0.01f, 0.01f, 0.01f );
                matrix.scale( 1.0f, 1.0f, 1.0f );
                matrix.translate(0.0f, 1.0f, 0.0f);
                matrix.mulPose( Vector3f.XP.rotationDegrees( face.rotation.x() ) );
                matrix.mulPose( Vector3f.YP.rotationDegrees( face.rotation.y() ) );
                matrix.mulPose( Vector3f.ZP.rotationDegrees( face.rotation.z() ) );
                if(capability.getStringArray( pos, face ) != null){
                    int pPackedLight = ev.getLevelRenderer().getLightColor(level, level.getBlockState( pos ), pos.relative( face.direction ));
                    MultiBufferSource.BufferSource multibuffersource$buffersource = mc.renderBuffers().bufferSource();
                    int i = 0;
                    for(String s : capability.getStringArray( pos , face )){
                        matrix.translate(0.01,0.01,0.01);
                        mc.font.drawInBatch( s , 45 - mc.font.width( s )/2 + 1.0f , i * 9 + 0.4f, level.getBlockState( pos ).getBlock() != RegistryHandler.ENGRAVED_NETHER_BRICKS.get() ? GeneralUtility.modifyColorBrightness(capability.getColor( pos ), -0.125f) : 4991023, false, matrix.last().pose(), multibuffersource$buffersource,false, 0, pPackedLight,false );
                        matrix.translate(-0.01,-0.01,-0.01);
                        mc.font.drawInBatch( s , 45 - mc.font.width( s )/2 , i * 9 ,level.getBlockState( pos ).getBlock() != RegistryHandler.ENGRAVED_NETHER_BRICKS.get() ? GeneralUtility.modifyColorBrightness( capability.getColor( pos ), -0.225f) : 787976, false, matrix.last().pose(), multibuffersource$buffersource,false, 0, pPackedLight,false );
                        i++;
                    }
                    multibuffersource$buffersource.endBatch();
                }
                RenderSystem.disableDepthTest();
                matrix.popPose();
            }
        }
    }
}
