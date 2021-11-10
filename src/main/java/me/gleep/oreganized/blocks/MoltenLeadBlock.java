package me.gleep.oreganized.blocks;

import me.gleep.oreganized.util.ModDamageSource;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;

public class MoltenLeadBlock extends Block implements BucketPickup {
    public MoltenLeadBlock() {
        super(BlockBehaviour.Properties.of(Material.LAVA)
                .strength(-1.0F, 3600000.0F)
                .noCollission()
                .noDrops()
                .requiresCorrectToolForDrops());
    }


    /**
     * Get the {@code PathNodeType} for this block. Return {@code null} for vanilla behavior.
     *
     * @param state
     * @param world
     * @param pos
     * @param entity
     * @return the PathNodeType
     */
    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.WALKABLE;
    }

    /**
     * Determines if this block is can be destroyed by the specified entities normal behavior.
     *
     * @param state  The current state
     * @param world  The current world
     * @param pos    Block position in world
     * @param entity
     * @return True to allow the ender dragon to destroy this block
     */
    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
        return false;
    }

    @Override
    public void playerWillDestroy(Level p_49852_, BlockPos p_49853_, BlockState p_49854_, Player p_49855_) { }

    /**
     * Called when a player removes a block.  This is responsible for
     * actually destroying the block, and the block is intact at time of call.
     * This is called regardless of whether the player can harvest the block or
     * not.
     * <p>
     * Return true if the block is actually destroyed.
     * <p>
     * Note: When used in multiplayer, this is called on both client and
     * server sides!
     *
     * @param state       The current state.
     * @param world       The current world
     * @param pos         Block position in world
     * @param player      The player damaging the block, may be null
     * @param willHarvest True if Block.harvestBlock will be called after this, if the return in true.
     *                    Can be useful to delay the destruction of tile entities till after harvestBlock
     * @param fluid       The current fluid state at current position
     * @return True if the block is actually destroyed.
     */
    @Override
    public boolean removedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return false;
    }

    @Override
    public int getLightBlock(BlockState p_60585_, BlockGetter p_60586_, BlockPos p_60587_) {
        return 8;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        if (((EntityCollisionContext)p_60575_).getEntity().isPresent()) {
            if (!(((EntityCollisionContext)p_60575_).getEntity().get() instanceof LivingEntity)) return Shapes.empty();
            LivingEntity entity = (LivingEntity) ((EntityCollisionContext)p_60575_).getEntity().get();
            for (ItemStack item : entity.getArmorSlots()) {
                if (item.getItem().equals(Items.IRON_BOOTS)) return Shapes.block();
            }
        }
        //if (!p_60575_.isDescending()) return Shapes.block();
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return p_60558_.isHoldingItem(Items.BUCKET)
                || p_60558_.isHoldingItem(RegistryHandler.MOLTEN_LEAD_BUCKET.get())
                || !p_60558_.isDescending() ? Shapes.block() : Shapes.empty();
        //return Shapes.block();
    }

    /*@Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (p_60506_.getItemInHand(p_60507_).getItem().equals(Items.BUCKET) || p_60506_.getItemInHand(p_60507_).getItem().equals(RegistryHandler.MOLTEN_LEAD_BUCKET.get())){
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }*/

    /**
     * Determines if the player can harvest this block, obtaining it's drops when the block is destroyed.
     *
     * @param state
     * @param world  The current world
     * @param pos    The block's current position
     * @param player The player damaging the block
     * @return True to spawn the drops
     */
    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public void entityInside(BlockState p_60495_, Level p_60496_, BlockPos p_60497_, Entity p_60498_) {
        if (p_60498_ instanceof LivingEntity) {
            p_60498_.setSecondsOnFire(10);
            p_60498_.hurt(ModDamageSource.MOLTEN_LEAD, 3.0F);
            p_60498_.makeStuckInBlock(p_60495_, new Vec3((double)0.7F, 1.0D, (double)0.7F));
        }

        super.entityInside(p_60495_, p_60496_, p_60497_, p_60498_);
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor p_152719_, BlockPos p_152720_, BlockState p_152721_) {
        p_152719_.setBlock(p_152720_, Blocks.AIR.defaultBlockState(), 11);
        if (!p_152719_.isClientSide()) {
            p_152719_.levelEvent(2001, p_152720_, Block.getId(p_152721_));
        }

        return new ItemStack(RegistryHandler.MOLTEN_LEAD_BUCKET.get(), 1);
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
    }

    /*@Override
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        return state.getBlock().equals(adjacentBlockState.getBlock());
    }*/
}
