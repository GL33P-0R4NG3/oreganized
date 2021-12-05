package me.gleep.oreganized.util.messages;

import me.gleep.oreganized.blocks.ChiseledBlock;
import me.gleep.oreganized.capabilities.engravedblockscap.CapabilityEngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.EngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.IEngravedBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Supplier;

import static me.gleep.oreganized.blocks.ChiseledBlock.ISZAXISDOWN;
import static me.gleep.oreganized.blocks.ChiseledBlock.ISZAXISUP;
import static me.gleep.oreganized.util.SimpleNetwork.CHANNEL;

public class UpdateServerEngravedBlocks{

    private final HashSet <BlockPos> engravedBlocks;
    private final HashMap <BlockPos, HashMap <EngravedBlocks.Face, String>> engravedFaces;
    private final HashMap <BlockPos, Integer> engravedColors;

    public UpdateServerEngravedBlocks( HashSet <BlockPos> engravedBlocks , HashMap <BlockPos, HashMap <EngravedBlocks.Face, String>> engravedFaces , HashMap <BlockPos, Integer> engravedColors ) {
        this.engravedBlocks = engravedBlocks;
        this.engravedFaces = engravedFaces;
        this.engravedColors = engravedColors;
    }

    public void encode( FriendlyByteBuf buffer ) {
        CompoundTag tag = new CompoundTag();
        int i = 0;
        for(BlockPos pos: engravedBlocks){
            CompoundTag blockpos = new CompoundTag();
            CompoundTag coords = new CompoundTag();
            CompoundTag faces = new CompoundTag();
            coords.putFloat( "X", pos.getX() );
            coords.putFloat( "Y", pos.getY() );
            coords.putFloat( "Z", pos.getZ() );
            blockpos.put( "POS", coords);
            faces.putString( "UP_N", engravedFaces.get(pos).get( EngravedBlocks.Face.UP_N ) == null ? "" : engravedFaces.get(pos).get( EngravedBlocks.Face.UP_N ));
            faces.putString( "DOWN_N", engravedFaces.get(pos).get( EngravedBlocks.Face.DOWN_N ) == null ? "" : engravedFaces.get(pos).get( EngravedBlocks.Face.DOWN_N ) );
            faces.putString( "UP_S", engravedFaces.get(pos).get( EngravedBlocks.Face.UP_S ) == null ? "" : engravedFaces.get(pos).get( EngravedBlocks.Face.UP_S ));
            faces.putString( "DOWN_S", engravedFaces.get(pos).get( EngravedBlocks.Face.DOWN_S ) == null ? "" : engravedFaces.get(pos).get( EngravedBlocks.Face.DOWN_S ) );
            faces.putString( "UP_E", engravedFaces.get(pos).get( EngravedBlocks.Face.UP_E ) == null ? "" : engravedFaces.get(pos).get( EngravedBlocks.Face.UP_E ));
            faces.putString( "DOWN_E", engravedFaces.get(pos).get( EngravedBlocks.Face.DOWN_E ) == null ? "" : engravedFaces.get(pos).get( EngravedBlocks.Face.DOWN_E ) );
            faces.putString( "UP_W", engravedFaces.get(pos).get( EngravedBlocks.Face.UP_W ) == null ? "" : engravedFaces.get(pos).get( EngravedBlocks.Face.UP_W ));
            faces.putString( "DOWN_W", engravedFaces.get(pos).get( EngravedBlocks.Face.DOWN_W ) == null ? "" : engravedFaces.get(pos).get( EngravedBlocks.Face.DOWN_W ) );
            faces.putString( "LEFT", engravedFaces.get(pos).get( EngravedBlocks.Face.LEFT ) == null ? "" : engravedFaces.get(pos).get( EngravedBlocks.Face.LEFT ));
            faces.putString("RIGHT", engravedFaces.get(pos).get( EngravedBlocks.Face.RIGHT ) == null ? "" : engravedFaces.get(pos).get( EngravedBlocks.Face.RIGHT ));
            faces.putString( "FRONT", engravedFaces.get(pos).get( EngravedBlocks.Face.FRONT ) == null ? "" : engravedFaces.get(pos).get( EngravedBlocks.Face.FRONT ));
            faces.putString( "BACK", engravedFaces.get(pos).get( EngravedBlocks.Face.BACK ) == null ? "" : engravedFaces.get(pos).get( EngravedBlocks.Face.BACK ));blockpos.put( "FACES", faces );
            blockpos.putInt( "COLOR", engravedColors.get( pos ) == null ? 0 : engravedColors.get( pos ));
            tag.put( String.valueOf( i ) , blockpos );
            i++;
        }
        tag.putInt("numberOfBlocks", i);
        buffer.writeNbt( tag );
    }

    public static UpdateServerEngravedBlocks decode( FriendlyByteBuf buffer ) {
        HashSet <BlockPos> engravedBlocks = new HashSet <>();
        HashMap <BlockPos, HashMap <EngravedBlocks.Face, String>> engravedFaces = new HashMap <>( 12 );
        HashMap <BlockPos, Integer> engravedColors = new HashMap <>();
        CompoundTag tag = buffer.readAnySizeNbt();
        for( int i = 0; i < tag.getInt( "numberOfBlocks" ); i++){
            CompoundTag block = (CompoundTag) tag.get( String.valueOf( i ) );
            CompoundTag coords = block.getCompound( "POS" );
            CompoundTag faces = block.getCompound( "FACES" );
            BlockPos blockPos = new BlockPos( coords.getFloat( "X" ), coords.getFloat( "Y" ), coords.getFloat( "Z" ) );
            engravedBlocks.add( blockPos );
            HashMap <EngravedBlocks.Face, String> facesmap = new HashMap <EngravedBlocks.Face, String>(12);
            facesmap.put( EngravedBlocks.Face.UP_N, faces.getString( "UP_N" ) );
            facesmap.put( EngravedBlocks.Face.DOWN_N, faces.getString( "DOWN_N" ) );
            facesmap.put( EngravedBlocks.Face.UP_S, faces.getString( "UP_S" ) );
            facesmap.put( EngravedBlocks.Face.DOWN_S, faces.getString( "DOWN_S" ) );
            facesmap.put( EngravedBlocks.Face.UP_E, faces.getString( "UP_E" ) );
            facesmap.put( EngravedBlocks.Face.DOWN_E, faces.getString( "DOWN_E" ) );
            facesmap.put( EngravedBlocks.Face.UP_W, faces.getString( "UP_W" ) );
            facesmap.put( EngravedBlocks.Face.DOWN_W, faces.getString( "DOWN_W" ) );
            facesmap.put( EngravedBlocks.Face.LEFT, faces.getString( "LEFT" ) );
            facesmap.put( EngravedBlocks.Face.RIGHT, faces.getString( "RIGHT" ) );
            facesmap.put( EngravedBlocks.Face.FRONT, faces.getString( "FRONT" ) );
            facesmap.put( EngravedBlocks.Face.BACK, faces.getString( "BACK" ) );
            engravedFaces.put( blockPos, facesmap );
            engravedColors.put( blockPos, block.getInt("COLOR") );
        }
        return new UpdateServerEngravedBlocks(engravedBlocks, engravedFaces , engravedColors );
    }

    public static void handle(UpdateServerEngravedBlocks message, Supplier <NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Level pLevel = context.getSender().getLevel();
            IEngravedBlocks pCap = pLevel.getCapability( CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY ).orElse( null );
            for(BlockPos pos : message.engravedBlocks){
                boolean blockAdded = pCap.getEngravedBlocks().add( pos );
                if(!blockAdded){
                    pCap.getEngravedBlocks().remove( pos );
                    pCap.getEngravedBlocks().add( pos );
                }
                pCap.getEngravedFaces().put( pos , message.engravedFaces.get( pos ) );
                pCap.getEngravedColors().put( pos , message.engravedColors.get( pos ) );
                if(pLevel.getBlockState( pos ).getBlock() instanceof ChiseledBlock){
                    pLevel.setBlockAndUpdate( pos , pLevel.getBlockState( pos ).setValue( ISZAXISUP , !Objects.equals( pCap.getString( pos , EngravedBlocks.Face.UP_N ) , pCap.getString( pos , EngravedBlocks.Face.UP_S ) ) ) );
                    pLevel.setBlockAndUpdate( pos , pLevel.getBlockState( pos ).setValue( ISZAXISDOWN , !Objects.equals( pCap.getString( pos , EngravedBlocks.Face.DOWN_N ) , pCap.getString( pos , EngravedBlocks.Face.DOWN_S ) ) ) );
                }
            }
            CHANNEL.send( PacketDistributor.ALL.noArg(), new UpdateClientEngravedBlocks( pCap.getEngravedBlocks(), pCap.getEngravedFaces(), pCap.getEngravedColors() ) );
        });
        context.setPacketHandled(true);
    }
}


