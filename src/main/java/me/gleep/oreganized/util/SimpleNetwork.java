package me.gleep.oreganized.util;

import me.gleep.oreganized.util.messages.StoneSignServerHandler;
import me.gleep.oreganized.util.messages.StoneSignToServerMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

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
        CHANNEL.registerMessage(id++,
                StoneSignToServerMessage.class,
                StoneSignToServerMessage::encode,
                StoneSignToServerMessage::decode,
                StoneSignToServerMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }
}
