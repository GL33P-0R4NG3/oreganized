package me.gleep.oreganized.blocks;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

import static net.minecraft.world.phys.shapes.BooleanOp.ONLY_FIRST;

public class ModCauldron extends Block {

    public static final IntegerProperty LEVEL = BlockStateProperties.AGE_3;
    private static final VoxelShape INSIDE = box(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE = Shapes.join(Shapes.block(),
            Shapes.or(box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D),
                      box(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D),
                      box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D),
                      INSIDE), ONLY_FIRST);

    public ModCauldron() {
        super(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.STONE)
                .requiresCorrectToolForDrops()
                .strength(2.0F)
                .noOcclusion());
        registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, Integer.valueOf(1)));
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
        return INSIDE;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_60508_) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.isEmpty()) {
            if (blockState.getValue(LEVEL) == 1) {
                level.removeBlock(pos, false);
                level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY() + 0.5D, pos.getZ(), RegistryHandler.LEAD_BLOCK_ITEM.get().getDefaultInstance()));
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else return InteractionResult.PASS;
        } else {
            int i = blockState.getValue(LEVEL);
            Item item = itemstack.getItem();
            /*if (item == RegistryHandler.LEAD_BLOCK_ITEM.get()) {
                if (!worldIn.isRemote) {
                    player.addStat(Stats.FILL_CAULDRON);
                    this.setLeadLevel(worldIn, pos, state, 1);
                    worldIn.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResultType.func_233537_a_(worldIn.isRemote);
            } else */if (item == Items.BUCKET) {
                if (i == 3 && !level.isClientSide) {
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.setItemInHand(hand, new ItemStack(RegistryHandler.MOLTEN_LEAD_BUCKET.get()));
                        } else if (!player.getInventory().add(new ItemStack(RegistryHandler.MOLTEN_LEAD_BUCKET.get()))) {
                            player.drop(new ItemStack(RegistryHandler.MOLTEN_LEAD_BUCKET.get()), false);
                        }
                    }
                    player.awardStat(Stats.USE_CAULDRON);
                    level.removeBlock(pos, false);
                    level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                    level.playSound((Player) null, pos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);

            } else return InteractionResult.PASS;
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        this.tick(state, worldIn, pos, random);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (state.getValue(LEVEL) > 0 && state.getValue(LEVEL) != 3) {
            if (!worldIn.isClientSide) {
                BlockPos newPos = new BlockPos(pos.getX(), pos.getY() - 1.0D, pos.getZ());
                BlockState block = worldIn.getBlockState(newPos);
                ResourceLocation loc = new ResourceLocation("oreganized", "fire_source");
                if (BlockTags.getAllTags().getTag(loc).contains(block.getBlock())) {
                    this.setLeadLevel(worldIn, pos, state, state.getValue(LEVEL) + 1);
                } else {
                    this.setLeadLevel(worldIn, pos, state, 1);
                }
            }
        }
    }

    public void setLeadLevel(Level worldIn, BlockPos pos, BlockState state, int level) {
        worldIn.setBlock(pos, state.setValue(LEVEL, Mth.clamp(level, 0, 3)), 2);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(LEVEL);
    }

    @Override
    public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
        return false;
    }
}
