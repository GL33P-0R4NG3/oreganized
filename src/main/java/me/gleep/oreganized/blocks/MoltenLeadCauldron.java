package me.gleep.oreganized.blocks;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.util.ModDamageSource;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
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

public class MoltenLeadCauldron extends AbstractCauldronBlock {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_CAULDRON;

    public MoltenLeadCauldron() {
        super(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.STONE)
                .requiresCorrectToolForDrops()
                .strength(2.0F)
                .noOcclusion(),
                CauldronInteraction.LAVA
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(1)));
    }

    @Override
    protected double getContentHeight(BlockState pState) { return 0.9375D; }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!(pEntity instanceof LivingEntity)) return;

        if (this.isEntityInsideContent(pState, pPos, pEntity)) {
            pEntity.setSecondsOnFire(10);
            pEntity.hurt(ModDamageSource.MOLTEN_LEAD, 3.0F);
        }

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

    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @deprecated call via {@link
     * net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#getAnalogOutputSignal(Level, BlockPos)} whenever possible.
     * Implementing/overriding is fine.
     */
    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return pState.getValue(LEVEL);
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

                if (BlockTags.getAllTags().getTag(new ResourceLocation(Oreganized.MOD_ID, "fire_source")).contains(block.getBlock())) {
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

    @Override
    public boolean isFull(BlockState pState) { return true; }
}
