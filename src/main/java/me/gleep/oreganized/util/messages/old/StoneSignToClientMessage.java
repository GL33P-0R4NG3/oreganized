package me.gleep.oreganized.util.messages.old;

import net.minecraft.core.BlockPos;
import net.minecraftforge.fmllegacy.network.ICustomPacket;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.Arrays;
import java.util.function.Supplier;

public class StoneSignToClientMessage {
    //StoneSign properties
    private final BlockPos pos;
    private final String[] lines;
    //Message properties
    private boolean isValid;

    public BlockPos getPos() {
        return this.pos;
    }

    public String[] getText() {
        return this.lines;
    }

    private StoneSignToClientMessage() {
        this.isValid = false;
        this.pos = null;
        this.lines = null;
    }

    public StoneSignToClientMessage(BlockPos pos, String str1, String str2, String str3, String str4, String str5, String str6) {
        this.pos = pos;
        this.lines = new String[]{str1, str2, str3, str4, str5, str6};
    }

    /*public static StoneSignToClientMessage decode(PacketBuffer buf) {
        return new StoneSignToClientMessage(buf.readBlockPos(), buf.readString(384), buf.readString(384), buf.readString(384), buf.readString(384), buf.readString(384), buf.readString(384));
    }*/

    public static void encode(StoneSignToClientMessage msg, ICustomPacket<?> buf) {
        /*buf.setData(msg.pos);

        for(int i = 0; i < 6; ++i) {
            buf.writeString(msg.lines[i]);
        }*/
    }

    public static void handle(StoneSignToClientMessage msg, Supplier<NetworkEvent.Context> context) {
        /*context.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> StoneSignClientHandler.handle(msg));
        });

        context.get().setPacketHandled(true);*/
    }

    @Override
    public String toString() {
        return "StoneSignToClientMessage[" + pos.toString() + ", Text=" + Arrays.toString(this.lines) + "]";
    }
}
