package me.gleep.oreganized.util;

import me.gleep.oreganized.blocks.ChiseledBlock;
import me.gleep.oreganized.capabilities.engravedblockscap.CapabilityEngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.EngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.IEngravedBlocks;
import me.gleep.oreganized.events.ModEvents;
import me.gleep.oreganized.screens.EngraveScreen;
import me.gleep.oreganized.util.messages.BushHammerClickPacket;
import me.gleep.oreganized.util.messages.UpdateClientEngravedBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.lwjgl.system.CallbackI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

import static me.gleep.oreganized.blocks.ChiseledBlock.ISZAXISDOWN;
import static me.gleep.oreganized.blocks.ChiseledBlock.ISZAXISUP;

public class GeneralUtility{
    public static void openEngraveScreen( BushHammerClickPacket message ){
        Minecraft.getInstance().setScreen( new EngraveScreen(  Minecraft.getInstance().player.level.
                getCapability( CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY ).orElse( null ),
                message.pos, message.face, message.block ) );
    }

    public static void handleClientEngravedBlocksSync( UpdateClientEngravedBlocks message ) {
        Level pLevel = Minecraft.getInstance().level;
        IEngravedBlocks pCap = pLevel.getCapability( CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY ).orElse( null );
        pCap.setEngravedBlocks( message.engravedBlocks );
        pCap.setEngravedFaces(message.engravedFaces);
        pCap.setEngravedColors(message.engravedColors);
    }

    public static int getBrightestColorFromBlock( Block block, BlockPos blockPos ){
        InputStream is;
        BufferedImage image;
        try {
            String id = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getTexture(block.defaultBlockState(), Minecraft.getInstance().level, blockPos).getName().getNamespace();
            is = Minecraft.getInstance().getResourceManager().getResource((new ResourceLocation(id, "textures/block/" + block.getRegistryName().getPath() + ".png" ))).getInputStream();
            image = ImageIO.read(is);
        }catch(IOException e){
            e.printStackTrace();
            return 0;
        }
        int maxBrightness = 0;
        for (int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++){
                int[] colors = image.getRaster().getPixel( x, y, new int[4] );
                int colorBrightness = colors[0] + colors[1] + colors[2];
                if(colorBrightness > maxBrightness){
                    maxBrightness = colorBrightness;
                }
            }
        }
        int[] brightestColorIntArr = new int[3];
        for (int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++){
                int[] colors = image.getRaster().getPixel( x, y, new int[4] );
                int colorBrightness = colors[0] + colors[1] + colors[2];
                if(colorBrightness == maxBrightness){
                    brightestColorIntArr = colors;
                }
            }
        }
        return (brightestColorIntArr[0] << 16) + (brightestColorIntArr[1] << 8) + (brightestColorIntArr[2]);
    }

    public static int modifyColorBrightness(int color, float brightness){
        int R = (color >> 16) & 0xff;
        int G = (color >> 8) & 0xff;
        int B = color & 0xff;
        float[] HSB = Color.RGBtoHSB( R, G, B, new float[4]);
        return Color.HSBtoRGB( HSB[0], HSB[1], brightness );
    }
}
