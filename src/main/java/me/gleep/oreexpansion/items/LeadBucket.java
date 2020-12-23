package me.gleep.oreexpansion.items;

import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LeadBucket extends Item {

    public LeadBucket() {
        super(new Item.Properties().group(ItemGroup.MATERIALS));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (world.isRemote()) {
            if (!context.getPlayer().isCreative()) {
                context.getItem().shrink(1);
                context.getPlayer().setHeldItem(context.getHand(), Items.BUCKET.getDefaultInstance());
            }
            BlockPos pos = context.getPos();
            BlockPos blockPlace = pos.offset(context.getFace());
            world.setBlockState(blockPlace, RegistryHandler.LEAD_FLUID.get().getDefaultState());
        }
        return ActionResultType.func_233537_a_(world.isRemote());
    }
}
