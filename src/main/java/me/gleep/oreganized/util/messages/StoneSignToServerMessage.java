package me.gleep.oreganized.util.messages;

import net.minecraft.core.BlockPos;

import java.util.Arrays;
import java.util.function.Supplier;

import static me.gleep.oreganized.Oreganized.LOGGER;

public class StoneSignToServerMessage {
    //StoneSign properties
    private BlockPos pos;
    private String[] lines;
    //Message properties
    private boolean isValid;

    public BlockPos getPos() {
        return this.pos;
    }

    public String[] getText() {
        return this.lines;
    }

    public StoneSignToServerMessage() {
        this.isValid = false;
    }

    public StoneSignToServerMessage(BlockPos pos, String str1, String str2, String str3, String str4, String str5, String str6) {
        this.pos = pos;
        this.lines = new String[]{str1, str2, str3, str4, str5, str6};
    }

    /*public static StoneSignToServerMessage decode(PacketBuffer buf) {
        StoneSignToServerMessage msg = new StoneSignToServerMessage();
        try {
            msg.pos = buf.readBlockPos();
            for(int i = 0; i < 6; ++i) {
                msg.lines[i] = buf.readString(384);
            }

            msg.isValid = true;
        } catch (Exception e) {
            LOGGER.warn("Exception while reading StoneSignToServerMessage: " + e);
        }

        return msg;
    }

    public static void encode(StoneSignToServerMessage msg, PacketBuffer buf) {
        if (!msg.isValid) return;
        buf.writeBlockPos(msg.pos);

        for(int i = 0; i < 6; ++i) {
            buf.writeString(msg.lines[i]);
        }
    }

    public static void handle(StoneSignToServerMessage msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> StoneSignServerHandler.handle(msg));

        context.get().setPacketHandled(true);
    }*/

    @Override
    public String toString() {
        return "StoneSignToClientMessage[" + pos.toString() + ", Text=" + Arrays.toString(this.lines) + "]";
    }
}
