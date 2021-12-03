package me.gleep.oreganized.capabilities.engravedblockscap;

import com.mojang.math.Vector3f;
import me.gleep.oreganized.util.GeneralUtility;
import me.gleep.oreganized.util.messages.UpdateServerEngravedBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.HashSet;

import static me.gleep.oreganized.util.SimpleNetwork.CHANNEL;

public class EngravedBlocks implements IEngravedBlocks, INBTSerializable<CompoundTag>{

    public HashSet<BlockPos> engravedBlocks = new HashSet <>();
    public HashMap<BlockPos, HashMap <Face, String>> engravedFaces = new HashMap <>( 12 );
    public HashMap<BlockPos, Integer> engravedColors = new HashMap <>();

    @Override
    public CompoundTag serializeNBT(){
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
            faces.putString( "UP_N", engravedFaces.get(pos).get( Face.UP_N ) == null ? "" : engravedFaces.get(pos).get( Face.UP_N ));
            faces.putString( "DOWN_N", engravedFaces.get(pos).get( Face.DOWN_N ) == null ? "" : engravedFaces.get(pos).get( Face.DOWN_N ) );
            faces.putString( "UP_S", engravedFaces.get(pos).get( Face.UP_S ) == null ? "" : engravedFaces.get(pos).get( Face.UP_S ));
            faces.putString( "DOWN_S", engravedFaces.get(pos).get( Face.DOWN_S ) == null ? "" : engravedFaces.get(pos).get( Face.DOWN_S ) );
            faces.putString( "UP_E", engravedFaces.get(pos).get( Face.UP_E ) == null ? "" : engravedFaces.get(pos).get( Face.UP_E ));
            faces.putString( "DOWN_E", engravedFaces.get(pos).get( Face.DOWN_E ) == null ? "" : engravedFaces.get(pos).get( Face.DOWN_E ) );
            faces.putString( "UP_W", engravedFaces.get(pos).get( Face.UP_W ) == null ? "" : engravedFaces.get(pos).get( Face.UP_W ));
            faces.putString( "DOWN_W", engravedFaces.get(pos).get( Face.DOWN_W ) == null ? "" : engravedFaces.get(pos).get( Face.DOWN_W ) );
            faces.putString( "LEFT", engravedFaces.get(pos).get( Face.LEFT ) == null ? "" : engravedFaces.get(pos).get( Face.LEFT ));
            faces.putString("RIGHT", engravedFaces.get(pos).get( Face.RIGHT ) == null ? "" : engravedFaces.get(pos).get( Face.RIGHT ));
            faces.putString( "FRONT", engravedFaces.get(pos).get( Face.FRONT ) == null ? "" : engravedFaces.get(pos).get( Face.FRONT ));
            faces.putString( "BACK", engravedFaces.get(pos).get( Face.BACK ) == null ? "" : engravedFaces.get(pos).get( Face.BACK ));
            blockpos.put( "FACES", faces );
            blockpos.putInt( "COLOR", engravedColors.get( pos ) == null ? 0 : engravedColors.get( pos ));
            tag.put( String.valueOf( i ) , blockpos );
            i++;
        }
        tag.putInt( "COUNT", i );
        return tag;
    }

    @Override
    public void deserializeNBT( CompoundTag nbt ){
        for( int i = 0; i < nbt.getInt("COUNT"); i++){
            CompoundTag block = (CompoundTag) nbt.get( String.valueOf( i ) );
            CompoundTag coords = block.getCompound( "POS" );
            CompoundTag faces = block.getCompound( "FACES" );
            BlockPos blockPos = new BlockPos( coords.getFloat( "X" ), coords.getFloat( "Y" ), coords.getFloat( "Z" ) );
            engravedBlocks.add( blockPos );
            HashMap <Face, String> facesmap = new HashMap <Face, String>(12);
            facesmap.put( Face.UP_N, faces.getString( "UP_N" ) );
            facesmap.put( Face.DOWN_N, faces.getString( "DOWN_N" ) );
            facesmap.put( Face.UP_S, faces.getString( "UP_S" ) );
            facesmap.put( Face.DOWN_S, faces.getString( "DOWN_S" ) );
            facesmap.put( Face.UP_E, faces.getString( "UP_E" ) );
            facesmap.put( Face.DOWN_E, faces.getString( "DOWN_E" ) );
            facesmap.put( Face.UP_W, faces.getString( "UP_W" ) );
            facesmap.put( Face.DOWN_W, faces.getString( "DOWN_W" ) );
            facesmap.put( Face.LEFT, faces.getString( "LEFT" ) );
            facesmap.put( Face.RIGHT, faces.getString( "RIGHT" ) );
            facesmap.put( Face.FRONT, faces.getString( "FRONT" ) );
            facesmap.put( Face.BACK, faces.getString( "BACK" ) );
            this.engravedBlocks.add( blockPos );
            this.engravedFaces.put( blockPos, facesmap );
            this.engravedColors.put( blockPos, block.getInt("COLOR") );
        }
    }

    @Override
    public void setFaceString(BlockPos pos, Face face , String string ){
        if(isEngraved( pos )){
            if(isDown( face )){
                this.engravedFaces.get( pos ).put( Face.DOWN_N, "" );
                this.engravedFaces.get( pos ).put( Face.DOWN_S, "" );
                this.engravedFaces.get( pos ).put( Face.DOWN_E, "" );
                this.engravedFaces.get( pos ).put( Face.DOWN_W, "");
            } else if(isUp( face )) {
                this.engravedFaces.get( pos ).put( Face.UP_N, "" );
                this.engravedFaces.get( pos ).put( Face.UP_S, "" );
                this.engravedFaces.get( pos ).put( Face.UP_E, "" );
                this.engravedFaces.get( pos ).put( Face.UP_W, "");
            }
            engravedFaces.get( pos ).put( face, string );
        } else {
            engravedFaces.put( pos, new HashMap <>() );
            if(isDown( face )){
                engravedFaces.get( pos ).put( Face.DOWN_N, "" );
                engravedFaces.get( pos ).put( Face.DOWN_S, "" );
                engravedFaces.get( pos ).put( Face.DOWN_E, "" );
                engravedFaces.get( pos ).put( Face.DOWN_W, "");
            } else if(isUp( face )) {
                engravedFaces.get( pos ).put( Face.UP_N, "" );
                engravedFaces.get( pos ).put( Face.UP_S, "" );
                engravedFaces.get( pos ).put( Face.UP_E, "" );
                engravedFaces.get( pos ).put( Face.UP_W, "");
            }
            engravedFaces.get( pos ).put( face, string );
            engravedBlocks.add( pos );
            engravedColors.put( pos, GeneralUtility.getBrightestColorFromBlock( Minecraft.getInstance().level.getBlockState( pos ).getBlock(), pos ) );
        }
        CHANNEL.sendToServer( new UpdateServerEngravedBlocks(engravedBlocks, engravedFaces, engravedColors) );
    }

    @Override
    public void removeEngravedBlock(BlockPos pos){
        if(isEngraved( pos )){
            engravedFaces.remove( pos );
            engravedBlocks.remove( pos );
            engravedColors.remove( pos );
        }
    }

    @Override
    public String[] getStringArray(BlockPos pos, Face face ){
        if(this.engravedFaces.get( pos ) != null) if(this.engravedFaces.get( pos ).get( face ) != null) {
            return this.engravedFaces.get( pos ).get( face ).split( "\n" );
        }
        return new String[0];
    }

    @Override
    public String getString( BlockPos pos , Face face ){
        return engravedFaces.get( pos ).get( face ) != null ? engravedFaces.get( pos ).get( face ) : "";
    }

    @Override
    public int getColor(BlockPos pos){
        return engravedColors.get( pos ) != null ? engravedColors.get( pos ) : 0;
    }

    @Override
    public HashSet <BlockPos> getEngravedBlocks(){
        return engravedBlocks;
    }

    @Override
    public HashMap <BlockPos, HashMap <Face, String>> getEngravedFaces(){
        return engravedFaces;
    }

    @Override
    public HashMap <BlockPos, Integer> getEngravedColors(){
        return engravedColors;
    }

    @Override
    public void setEngravedBlocks( HashSet <BlockPos> engravedBlocks ){
        this.engravedBlocks = engravedBlocks;
    }

    @Override
    public void setEngravedFaces( HashMap <BlockPos, HashMap <Face, String>> engravedFaces ){
        this.engravedFaces = engravedFaces;
    }

    @Override
    public void setEngravedColors( HashMap <BlockPos, Integer> engravedColors ){
        this.engravedColors = engravedColors;
    }


    public enum Face{
        UP_N(new Vector3f(0.05f,1.0002f,0.19f), new Vector3f(90f,0f,0f), Direction.UP),
        UP_S(new Vector3f(0.95f,1.0002f,0.81f), new Vector3f(90f,0f,180f), Direction.UP),
        UP_W(new Vector3f(0.19f,1.0002f,0.95f), new Vector3f(90f,0f,-90f), Direction.UP),
        UP_E(new Vector3f(0.81f,1.0002f,0.05f), new Vector3f(90f,0f,90f), Direction.UP),
        DOWN_N(new Vector3f(0.95f,-0.0002f,0.19f), new Vector3f(-90f,0f,180f), Direction.DOWN),
        DOWN_S(new Vector3f(0.05f,-0.0002f,0.81f), new Vector3f(-90f,0f,0f), Direction.DOWN),
        DOWN_E(new Vector3f(0.81f,-0.0002f,0.95f), new Vector3f(-90f,0f,90f), Direction.DOWN),
        DOWN_W(new Vector3f(0.19f,-0.0002f,0.05f), new Vector3f(-90f,0f,-90f), Direction.DOWN),
        LEFT(new Vector3f(-0.0002f,0.81f,0.05f), new Vector3f(0f,90f,180f), Direction.WEST),
        RIGHT(new Vector3f(1.0002f,0.81f, 0.95f), new Vector3f(0f,-90f,180f), Direction.EAST),
        FRONT(new Vector3f(0.95f,0.81f,-0.0002f), new Vector3f(0f,0f,180f), Direction.NORTH),
        BACK(new Vector3f(0.05f,0.81f, 1.0002f), new Vector3f(0f,180f,180f), Direction.SOUTH);

        public Vector3f translation;
        public Vector3f rotation;
        public Direction direction;

        Face( Vector3f translation, Vector3f rotation, Direction direction ) {
            this.direction = direction;
            this.translation = translation;
            this.rotation = rotation;
        }
    }

    private boolean isUp(Face face){
       return (face == Face.UP_W) || (face == Face.UP_E) || (face == Face.UP_N) || (face == Face.UP_S);
    }

    private boolean isDown(Face face){
        return (face == Face.DOWN_W) || (face == Face.DOWN_E) || (face == Face.DOWN_N) || (face == Face.DOWN_S);
    }

    public boolean isEngraved( BlockPos pos ){
        return engravedFaces.containsKey( pos ) && engravedBlocks.contains( pos );
    }
}
