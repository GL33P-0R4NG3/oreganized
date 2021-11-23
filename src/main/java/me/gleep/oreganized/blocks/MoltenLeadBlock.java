package me.gleep.oreganized.blocks;

import me.gleep.oreganized.util.ModDamageSource;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.IBlockRenderProperties;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

public class MoltenLeadBlock extends Block implements BucketPickup {
    public MoltenLeadBlock() {
        super(BlockBehaviour.Properties.of((new Material.Builder(MaterialColor.COLOR_PURPLE)).noCollider().notSolidBlocking().nonSolid().liquid().build())
                .strength(-1.0F, 3600000.0F)
                .noDrops()
                .requiresCorrectToolForDrops());
    }

    @Override
    public boolean skipRendering(BlockState p_60532_, BlockState p_60533_, Direction p_60534_) {
        return super.skipRendering(p_60532_, p_60533_, p_60534_);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction p_60542_, BlockState neighbour, LevelAccessor level, BlockPos pos, BlockPos neighbourPos) {
        if (level.isWaterAt(neighbourPos)) {
            level.levelEvent(1501, pos, 0);

            return RegistryHandler.LEAD_BLOCK.get().defaultBlockState();
        }

        return super.updateShape(state, p_60542_, neighbour, level, pos, neighbourPos);
    }

    @Override
    public void neighborChanged(BlockState p_60509_, Level level, BlockPos pos, Block p_60512_, BlockPos neighbourPos, boolean p_60514_) {
        if (level.getFluidState(neighbourPos).is(FluidTags.WATER)) {
            level.setBlockAndUpdate(pos, RegistryHandler.LEAD_BLOCK.get().defaultBlockState());

            level.levelEvent(1501, pos, 0);
        } else if (level.getFluidState(neighbourPos).is(FluidTags.LAVA)) {

        }


        super.neighborChanged(p_60509_, level, pos, p_60512_, neighbourPos, p_60514_);
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
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if (((EntityCollisionContext)p_60558_).getEntity().isPresent()) {
            return p_60558_.isHoldingItem(Items.BUCKET)
                    || p_60558_.isHoldingItem(RegistryHandler.MOLTEN_LEAD_BUCKET.get()) ? Shapes.block() : Shapes.empty();
        }

        return Shapes.block();
    }

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

}
