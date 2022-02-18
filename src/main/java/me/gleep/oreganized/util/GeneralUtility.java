package me.gleep.oreganized.util;

import me.gleep.oreganized.capabilities.engravedblockscap.CapabilityEngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.IEngravedBlocks;
import me.gleep.oreganized.screens.EngraveScreen;
import me.gleep.oreganized.util.messages.BushHammerClickPacket;
import me.gleep.oreganized.util.messages.UpdateClientEngravedBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GeneralUtility{
    public static void openEngraveScreen( BushHammerClickPacket message ){
        IEngravedBlocks cap = Minecraft.getInstance().player.level.getCapability( CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY ).orElse( null );
            Minecraft.getInstance().setScreen(new EngraveScreen(cap,
                    message.pos, message.face, message.block));
    }

    public static void handleClientEngravedBlocksSync( UpdateClientEngravedBlocks message ) {
        Level pLevel = Minecraft.getInstance().level;
        IEngravedBlocks pCap = pLevel.getCapability( CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY ).orElse( null );
        pCap.setEngravedBlocks(message.engravedBlocks );
        pCap.setEngravedFaces(message.engravedFaces);
        pCap.setEngravedColors(message.engravedColors);
    }

    public static int getBrightestColorFromBlock( Block block, BlockPos blockPos ){
        InputStream is;
        BufferedImage image;
        Block localBlock = block;
        if( block.getRegistryName().getPath().contains( "copper" ) ){
            localBlock = Blocks.COPPER_BLOCK;
        }
        try {
            String id = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getTexture(localBlock.defaultBlockState(), Minecraft.getInstance().level, blockPos).getName().getNamespace();
            is = Minecraft.getInstance().getResourceManager().getResource((new ResourceLocation(id, "textures/block/" + localBlock.getRegistryName().getPath() + ".png" ))).getInputStream();
            image = ImageIO.read(is);
        }catch(IOException e){
            e.printStackTrace();
            return 0;
        }
        int color = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++){
                int c = image.getRGB( x, y);
                int color1 = (((c >> 16) & 0xFF) << 16) + (((c >> 8) & 0xFF) << 8) + ((c) & 0xFF);
                if(color1 > color){
                    color = color1;
                }
            }
        }
        return modifyColorBrightness(color, -0.068F);
    }

    public static int modifyColorBrightness(int color, float brightness){
        int R = (color >> 16) & 0xff;
        int G = (color >> 8) & 0xff;
        int B = color & 0xff;
        float[] HSB = Color.RGBtoHSB( R, G, B, new float[4]);
        return Color.HSBtoRGB( HSB[0], HSB[1], HSB[2] + brightness );
    }
}
