package me.gleep.oreganized.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class LeadCoating extends Block {

    private int ranNum;
    private int tick;

    public LeadCoating() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(4.0F, 5.0F)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(1)
                .sound(SoundType.METAL));
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return false;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (ranNum == 0) {

        }
    }
}
