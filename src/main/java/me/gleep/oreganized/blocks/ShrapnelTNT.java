package me.gleep.oreganized.blocks;

import me.gleep.oreganized.entities.ShrapnelTNTEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class ShrapnelTNT extends Block {
    public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;

    public ShrapnelTNT() {
        super(BlockBehaviour.Properties.of(Material.EXPLOSIVE)
                .instabreak()
                .sound(SoundType.GRASS)
        );

        this.registerDefaultState(this.getStateDefinition().any().setValue(UNSTABLE, false));
    }

    /**
     * If the block is flammable, this is called when it gets lit on fire.
     *
     * @param state   The current state
     * @param world   The current world
     * @param pos     Block position in world
     * @param face    The face that the fire is coming from
     * @param igniter The entity that lit the fire
     */
    @Override
    public void catchFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        explode(world, pos, igniter);
    }

    @Override
    public void wasExploded(Level p_49844_, BlockPos p_49845_, Explosion p_49846_) {
        if (!p_49844_.isClientSide) {
            ShrapnelTNTEntity tntentity = new ShrapnelTNTEntity(p_49844_, (double)p_49845_.getX() + 0.5D, (double)p_49845_.getY(), (double)p_49845_.getZ() + 0.5D, p_49846_.getSourceMob());
            tntentity.setFuse((short)(p_49844_.random.nextInt(tntentity.getFuse() / 4) + tntentity.getFuse() / 8));
            p_49844_.addFreshEntity(tntentity);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean p_60570_) {
        if (!oldState.is(state.getBlock())) {
            if (level.hasNeighborSignal(pos)) {
                catchFire(state, level, pos, null, null);
                level.removeBlock(pos, false);
            }

        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && !player.isCreative() && state.getValue(UNSTABLE)) {
            catchFire(state, level, pos, null, null);
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (worldIn.hasNeighborSignal(pos)) {
            catchFire(state, worldIn, pos, null, null);
            worldIn.removeBlock(pos, false);
        }

    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {
        ItemStack itemstack = player.getItemInHand(p_60507_);
        Item item = itemstack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
            return super.use(state, level, pos, player, p_60507_, p_60508_);
        } else {
            catchFire(state, level, pos, p_60508_.getDirection(), player);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            if (!player.isCreative()) {
                if (item == Items.FLINT_AND_STEEL) {
                    itemstack.hurtAndBreak(1, player, (player1) -> {
                        player1.broadcastBreakEvent(p_60507_);
                    });
                } else {
                    itemstack.shrink(1);
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }



    @Override
    public void onProjectileHit(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!worldIn.isClientSide) {
            Entity entity = projectile.getOwner();
            if (projectile.isOnFire()) {
                BlockPos blockpos = hit.getBlockPos();
                catchFire(state, worldIn, blockpos, null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
                worldIn.removeBlock(blockpos, false);
            }
        }

    }

    @Override
    public boolean dropFromExplosion(Explosion explosionIn) {
        return false;
    }

    public static void explode(Level world, BlockPos worldIn) {
        explode(world, worldIn, null);
    }

    private static void explode(Level worldIn, BlockPos pos, @javax.annotation.Nullable LivingEntity entityIn) {
        if (!worldIn.isClientSide) {
            ShrapnelTNTEntity tntentity = new ShrapnelTNTEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, entityIn);
            worldIn.addFreshEntity(tntentity);
            worldIn.playSound((Player) null, tntentity.getX(), tntentity.getY(), tntentity.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(UNSTABLE);
    }
}
