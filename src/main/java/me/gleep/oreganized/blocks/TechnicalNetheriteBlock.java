package me.gleep.oreganized.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class TechnicalNetheriteBlock extends Block {
    public TechnicalNetheriteBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL)
                .strength(8.0F, 1200.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.NETHERITE_BLOCK));
    }

    /**
     * Chance that fire will spread and consume this block.
     * 300 being a 100% chance, 0, being a 0% chance.
     *
     * @param state The current state
     * @param world The current world
     * @param pos   Block position in world
     * @param face  The face that the fire is coming from
     * @return A number ranging from 0 to 300 relating used to determine if the block will be consumed by fire
     */
    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 0;
    }
}
