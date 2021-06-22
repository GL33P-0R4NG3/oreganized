package me.gleep.oreganized.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BushHammer extends Item {
    public BushHammer() {
        super(new Properties().group(ItemGroup.TOOLS)
                .maxStackSize(1)
                .maxDamage(100));
    }

    /**
     * Called when this item is used when targetting a Block
     *
     * @param context
     */
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return super.onItemUse(context);
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     *
     * @param stack
     * @param worldIn
     * @param state
     * @param pos
     * @param entityLiving
     */
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }
}
