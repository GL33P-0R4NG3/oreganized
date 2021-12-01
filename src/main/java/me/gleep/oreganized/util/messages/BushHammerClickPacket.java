package me.gleep.oreganized.util.messages;

import me.gleep.oreganized.capabilities.engravedblockscap.EngravedBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static me.gleep.oreganized.util.GeneralUtility.openEngraveScreen;

public class BushHammerClickPacket{

    public final BlockPos pos;
    public final EngravedBlocks.Face face;
    public Block block;

    public BushHammerClickPacket( BlockPos pos, EngravedBlocks.Face face, Block block) {
        this.pos = pos;
        this.face = face;
        this.block = block;
    }

    public void encode( FriendlyByteBuf buffer ) {
        CompoundTag tag = new CompoundTag();
        tag.putString( "block", block.getRegistryName().toString() );
        tag.putIntArray( "BlockPos", new int[] {pos.getX(), pos.getY(), pos.getZ()} );
        buffer.writeNbt( tag );
        buffer.writeEnum( face );

    }

    public static BushHammerClickPacket decode( FriendlyByteBuf buffer) {
        CompoundTag tag = buffer.readNbt();
        int[] coords = tag.getIntArray( "BlockPos" );
        BlockPos pos = new BlockPos(coords[0], coords[1], coords[2]);
        return new BushHammerClickPacket( pos, buffer.readEnum( EngravedBlocks.Face.class ),
                ForgeRegistries.BLOCKS.getValue( new ResourceLocation(tag.getString( "block" ))));
    }

    public static void handle(BushHammerClickPacket message, Supplier <NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> openEngraveScreen(message) );
        context.setPacketHandled(true);
    }
}
