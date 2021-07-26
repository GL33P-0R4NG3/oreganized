package me.gleep.oreganized.items;

import com.google.common.collect.*;
import jdk.nashorn.internal.codegen.MapCreator;
import me.gleep.oreganized.entities.tileentities.StoneSignTileEntity;
import me.gleep.oreganized.items.tiers.ModTier;
import me.gleep.oreganized.util.EditStoneSignScreen;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.lang.management.PlatformLoggingMXBean;
import java.util.*;

import static me.gleep.oreganized.Oreganized.MOD_ID;

public class BushHammer extends ToolItem {
    public static final Map<Block, Block> EFFECTIVE_ON = ImmutableMap.of(
            Blocks.STONE, Blocks.COBBLESTONE,
            Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS,
            Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,
            Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS
    );
    public static final Map<Block, Block> SIGNS = new HashMap<>();

    static {
        SIGNS.put(Blocks.STONE, RegistryHandler.STONE.get());
        SIGNS.put(Blocks.STONE_BRICKS, RegistryHandler.STONE_BRICKS.get());
    }

    public BushHammer() {
        super(2.5F, -2.9F, ModTier.LEAD, EFFECTIVE_ON.keySet(),
                new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
        );
    }

    /**
     * Called when this item is used when targetting a Block
     */
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();

        if (SIGNS.containsKey(state.getBlock())) {
            world.setBlockState(pos, SIGNS.get(state.getBlock()).getDefaultState(), 2);
            Minecraft.getInstance().displayGuiScreen(new EditStoneSignScreen((StoneSignTileEntity) world.getTileEntity(pos)));
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
