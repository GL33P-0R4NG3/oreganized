package me.gleep.oreganized.blocks;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.Nullable;

public class StoneSign extends Block {
    private static final IntegerProperty PROGRESS = IntegerProperty.create("progress", 0, 8);;

    public StoneSign(Properties properties) {
        super(properties);
        setDefaultState(this.getDefaultState().with(PROGRESS, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PROGRESS);
    }

    /**
     * Called throughout the code as a replacement for block instanceof BlockContainer
     * Moving this to the Block base class allows for mods that wish to extend vanilla
     * blocks, and also want to have a tile entity on that block, may.
     * <p>
     * Return true from this function to specify this block has a tile entity.
     *
     * @param state State of the current block
     * @return True if block has a tile entity, false otherwise
     */
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    /**
     * Called throughout the code as a replacement for ITileEntityProvider.createNewTileEntity
     * Return the same thing you would from that function.
     * This will fall back to ITileEntityProvider.createNewTileEntity(World) if this block is a ITileEntityProvider
     *
     * @param state The state of the current block
     * @param world The world to create the TE in
     * @return A instance of a class extending TileEntity
     */
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return RegistryHandler.STONE_SIGN_TE.get().create();
    }
}
