package me.gleep.oreexpansion.blocks;

import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Cauldron extends Block {

    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_3;
    private static final VoxelShape INSIDE = makeCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(makeCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), makeCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), INSIDE), IBooleanFunction.ONLY_FIRST);

    public Cauldron() {
        super(AbstractBlock.Properties.create(Material.IRON, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(2.0F).notSolid());
        this.setDefaultState(this.getStateContainer().getBaseState().with(LEVEL, Integer.valueOf(1)));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return INSIDE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        if (itemstack.isEmpty()) {
            if (state.get(LEVEL) == 1) {
                worldIn.removeBlock(pos, false);
                worldIn.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY() + 0.5D, pos.getZ(), RegistryHandler.LEAD_BLOCK_ITEM.get().getDefaultInstance()));
                return ActionResultType.func_233537_a_(worldIn.isRemote);
            } else return ActionResultType.PASS;
        } else {
            int i = state.get(LEVEL);
            Item item = itemstack.getItem();
            if (item == RegistryHandler.LEAD_BLOCK_ITEM.get()) {
                if (!worldIn.isRemote) {
                    player.addStat(Stats.FILL_CAULDRON);
                    this.setLeadLevel(worldIn, pos, state, 1);
                    worldIn.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResultType.func_233537_a_(worldIn.isRemote);
            } else if (item == Items.BUCKET) {
                if (i == 3 && !worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.setHeldItem(handIn, new ItemStack(RegistryHandler.LEAD_BUCKET.get()));
                        } else if (!player.inventory.addItemStackToInventory(new ItemStack(RegistryHandler.LEAD_BUCKET.get()))) {
                            player.dropItem(new ItemStack(RegistryHandler.LEAD_BUCKET.get()), false);
                        }
                    }
                    player.addStat(Stats.USE_CAULDRON);
                    worldIn.removeBlock(pos, false);
                    worldIn.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                    worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                return ActionResultType.func_233537_a_(worldIn.isRemote);

            } else return ActionResultType.PASS;
        }
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        this.tick(state, worldIn, pos, random);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (state.get(LEVEL) > 0 && state.get(LEVEL) != 3) {
            if (!worldIn.isRemote) {
                BlockPos newPos = new BlockPos(pos.getX(), pos.getY() - 1.0D, pos.getZ());
                BlockState block = worldIn.getBlockState(newPos);
                ResourceLocation loc = new ResourceLocation("oreexpansion", "fire_source");
                if (BlockTags.getCollection().getTagByID(loc).contains(block.getBlock())) {
                    this.setLeadLevel(worldIn, pos, state, state.get(LEVEL) + 1);
                } else {
                    this.setLeadLevel(worldIn, pos, state, 1);
                }
            }
        }
    }

    public void setLeadLevel(World worldIn, BlockPos pos, BlockState state, int level) {
        worldIn.setBlockState(pos, state.with(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 3))), 2);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) { builder.add(LEVEL); }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    /*@Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return RegistryHandler.CAULDRON_TE.get().create();
    }*/
}
