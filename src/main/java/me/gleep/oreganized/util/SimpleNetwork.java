package me.gleep.oreganized.util;

import me.gleep.oreganized.util.messages.BushHammerClickPacket;
import me.gleep.oreganized.util.messages.UpdateClientEngravedBlocks;
import me.gleep.oreganized.util.messages.UpdateServerEngravedBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

import java.util.Optional;

import static me.gleep.oreganized.Oreganized.MOD_ID;

public class SimpleNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () ->PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage( id++,
                BushHammerClickPacket.class,
                BushHammerClickPacket::encode,
                BushHammerClickPacket::decode,
                BushHammerClickPacket::handle,
                Optional.of( NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage( id++,
                UpdateServerEngravedBlocks.class,
                UpdateServerEngravedBlocks::encode,
                UpdateServerEngravedBlocks::decode,
                UpdateServerEngravedBlocks::handle,
                Optional.of( NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage( id++,
                UpdateClientEngravedBlocks.class,
                UpdateClientEngravedBlocks::encode,
                UpdateClientEngravedBlocks::decode,
                UpdateClientEngravedBlocks::handle,
                Optional.of( NetworkDirection.PLAY_TO_CLIENT));
    }

   /* public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++,
                StoneSignToServerMessage.class,
                StoneSignToServerMessage::encode,
                StoneSignToServerMessage::decode,
                StoneSignToServerMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }*/
}
