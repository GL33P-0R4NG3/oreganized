package me.gleep.oreganized.blocks;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class EngravedWeatheringCopperBlock extends EngravedBlock implements CustomWeatheringCopper {

    private final CustomWeatheringCopper.WeatherState weatherState;
    private final Block waxedBlock;

    public EngravedWeatheringCopperBlock( WeatherState pWeatherState , Properties pProperties , Block waxedBlock ) {
        super(pProperties);
        this.weatherState = pWeatherState;
        this.waxedBlock = waxedBlock;
    }

    /**
     * Performs a random tick on a block.
     */
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        this.onRandomTick(pState, pLevel, pPos, pRandom);
    }

    /**
     * @return whether this block needs random ticking.
     */
    public boolean isRandomlyTicking(BlockState pState) {
        return CustomWeatheringCopper.getNext(pState.getBlock()).isPresent();
    }

    public CustomWeatheringCopper.WeatherState getAge() {
        return this.weatherState;
    }

    public Block getWaxedBlock() {
        return this.waxedBlock;
    }

    @Override
    public InteractionResult use( BlockState pState , Level pLevel , BlockPos pPos , Player pPlayer , InteractionHand pHand , BlockHitResult pHit ){
        return pPlayer.getItemInHand( pHand ).getItem() == Items.HONEYCOMB ||
                (this.waxedBlock != RegistryHandler.ENGRAVED_WAXED_CUT_COPPER.get() &&
                        pPlayer.getItemInHand( pHand ).getItem() instanceof AxeItem) ?
                InteractionResult.SUCCESS : InteractionResult.PASS;
    }
}