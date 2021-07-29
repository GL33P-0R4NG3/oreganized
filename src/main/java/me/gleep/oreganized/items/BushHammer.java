package me.gleep.oreganized.items;

import com.google.common.collect.*;
import me.gleep.oreganized.entities.tileentities.StoneSignTileEntity;
import me.gleep.oreganized.items.tiers.ModTier;
import me.gleep.oreganized.util.EditStoneSignScreen;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class BushHammer extends ToolItem {
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
    public static final Map<Block, Block> SIGNS = ImmutableMap.of(
            Blocks.STONE, RegistryHandler.STONE.get(),
            Blocks.STONE_BRICKS, RegistryHandler.STONE_BRICKS.get()
    );

    public BushHammer() {
        super(2.5F, -3.1F, ModTier.LEAD, EFFECTIVE_ON.keySet(),
                new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
        );
    }

    /**
     * Called when this item is used when targetting a Block
     *
     * Used for stone sign placement
     */
    @NotNull
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();

        if (SIGNS.containsKey(state.getBlock()) && !world.isRemote()) {
            world.setBlockState(pos, SIGNS.get(state.getBlock()).getDefaultState(), 2);
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }

    /**
     * Called each tick while using an item.
     *
     * @param stack  The Item being used
     * @param player The Player using the item
     * @param count  The amount of time in tick the item has been used for
     */
    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {

    }

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
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {

    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }
}
