package me.gleep.oreganized.blocks;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class SilverBlock extends Block {
    public SilverBlock() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .harvestLevel(2)
                .sound(SoundType.METAL));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @org.jetbrains.annotations.Nullable MobEntity entity) {
        return PathNodeType.DAMAGE_FIRE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return RegistryHandler.SILVER_BLOCK_TE.get().create();
    }


}
