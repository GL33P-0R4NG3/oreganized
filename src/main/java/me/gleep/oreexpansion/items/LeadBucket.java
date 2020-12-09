package me.gleep.oreexpansion.items;

import me.gleep.oreexpansion.fluids.LeadFluid;
import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LeadBucket extends BucketItem {
    public LeadBucket() {
        super(RegistryHandler.LEAD_FLUID, new Properties().group(ItemGroup.MATERIALS));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (world.isRemote()) {
            BlockPos pos = context.getPos();
            BlockPos blockPlace = pos.offset(context.getFace());
            world.setBlockState(blockPlace, this.getFluid().getDefaultState().getBlockState());
        }
        return ActionResultType.func_233537_a_(world.isRemote());
    }
}
