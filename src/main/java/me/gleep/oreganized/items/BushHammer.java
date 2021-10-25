package me.gleep.oreganized.items;

import com.google.common.collect.*;
import me.gleep.oreganized.items.tiers.ModTier;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import java.util.Map;
import java.util.function.Consumer;

public class BushHammer extends DiggerItem {
    /**
     * Map where first element is the effective block and second element is the cracked version
     */
    public static final Map<Block, Block> EFFECTIVE_ON = ImmutableMap.of(
            Blocks.STONE, Blocks.COBBLESTONE,
            Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS,
            Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,
            Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS
    );
    /**
     * Map containing vanilla and mod version of blocks
     */
    /*public static final Map<Block, Block> SIGNS = ImmutableMap.of(
            Blocks.STONE, RegistryHandler.STONE.get(),
            Blocks.STONE_BRICKS, RegistryHandler.STONE_BRICKS.get()
    );*/

    public BushHammer() {
        super(2.5F, -3.1F, ModTier.LEAD, Tag.fromSet(EFFECTIVE_ON.keySet()),
                new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1)
        );
    }

    /**
     * Used for stone sign placement
     */
    /*@Override
    public InteractionResult useOn(UseOnContext p_41427_) {
        Level level = p_41427_.getLevel();
        BlockPos pos = p_41427_.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = p_41427_.getPlayer();

        if (SIGNS.containsKey(state.getBlock()) && !level.isClientSide) {
            level.setBlock(pos, SIGNS.get(state.getBlock()).getDefaultState(), 2);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(p_41427_);
    }*/

    /**
     * Called each tick while using an item.
     *
     * @param stack  The Item being used
     * @param player The Player using the item
     * @param count  The amount of time in tick the item has been used for
     */
    /*@Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {

    }*/

    /**
     * Reduce the durability of this item by the amount given.
     * This can be used to e.g. consume power from NBT before durability.
     *
     * @param stack    The itemstack to damage
     * @param amount   The amount to damage
     * @param entity   The entity damaging the item
     * @param onBroken The on-broken callback from vanilla
     * @return The amount of damage to pass to the vanilla logic
     */
    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return super.damageItem(stack, amount, entity, onBroken);
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    /*@Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {

    }*/

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    /*@Override
    public boolean onBlockDestroyed(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }*/
}
