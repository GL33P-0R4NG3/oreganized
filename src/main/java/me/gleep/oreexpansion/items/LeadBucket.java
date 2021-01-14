package me.gleep.oreexpansion.items;

import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeFluid;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.function.Supplier;

public class LeadBucket extends BucketItem {

    public LeadBucket(Supplier<? extends Fluid> supplier) {
        super(supplier, new Properties().group(ItemGroup.MATERIALS).maxStackSize(1));
    }

}
