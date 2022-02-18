package me.gleep.oreganized.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class CopperEngravedBlock extends EngravedBlock{


    public CopperEngravedBlock( Properties p_49795_ ){
        super( p_49795_ );
    }

    @Override
    public InteractionResult use( BlockState pState , Level pLevel , BlockPos pPos , Player pPlayer , InteractionHand pHand , BlockHitResult pHit ){
        return pPlayer.getItemInHand( pHand ).getItem() instanceof AxeItem ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }
}

