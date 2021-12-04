package me.gleep.oreganized.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ChiseledBlock extends Block{
    public static final BooleanProperty ISZAXISUP = BooleanProperty.create( "iszaxisup" );
    public static final BooleanProperty ISZAXISDOWN = BooleanProperty.create( "iszaxisdown" );

    public ChiseledBlock( Properties p_49795_ ){
        super( p_49795_ );
    }

    @Override
    protected void createBlockStateDefinition( StateDefinition.Builder <Block, BlockState> pBuilder ){
        pBuilder.add( ISZAXISUP );
        pBuilder.add( ISZAXISDOWN );
    }

}

