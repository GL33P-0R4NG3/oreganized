package me.gleep.oreexpansion.blocks;

import me.gleep.oreexpansion.util.RegistryHandler;
import me.gleep.oreexpansion.util.CustomBlockStateProperties;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Cauldron extends CauldronBlock {

    public static final IntegerProperty CONTENT = CustomBlockStateProperties.CONTENTS_0_2;

    public Cauldron() {
        super(AbstractBlock.Properties.create(Material.IRON, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(2.0F).notSolid());
        this.setDefaultState(this.getStateContainer().getBaseState().with(LEVEL, Integer.valueOf(0)).with(CONTENT, Integer.valueOf(0)));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        if (itemstack.isEmpty()) {
            return ActionResultType.PASS;
        } else {
            int i = state.get(LEVEL);
            int j = state.get(CONTENT);
            Item item = itemstack.getItem();
            if (item == RegistryHandler.LEAD_BLOCK_ITEM.get()) {
                if (!worldIn.isRemote && j == 0) {
                    /*if (!player.abilities.isCreativeMode) {
                        player.setHeldItem(handIn, new ItemStack(Items.BUCKET));
                    }*/

                    player.addStat(Stats.FILL_CAULDRON);
                    this.setLeadLevel(worldIn, pos, state, 1);
                    worldIn.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResultType.func_233537_a_(worldIn.isRemote);
            } /*else if (item == RegistryHandler.LEAD_BUCKET.get()) {
                if (j == 0 && i < 3 && !worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        player.setHeldItem(handIn, new ItemStack(Items.BUCKET));
                    }

                    player.addStat(Stats.FILL_CAULDRON);
                    this.setLeadLevel(worldIn, pos, state, 0);
                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResultType.func_233537_a_(worldIn.isRemote);
            }*/
            else if (item == Items.WATER_BUCKET) {
                if (j < 2 && i < 3 && !worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        player.setHeldItem(handIn, new ItemStack(Items.BUCKET));
                    }

                    player.addStat(Stats.FILL_CAULDRON);
                    this.setWaterLevel(worldIn, pos, state, 3);
                    worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResultType.func_233537_a_(worldIn.isRemote);

            } else if (item == Items.BUCKET) {
                if (j > 0 && i == 3 && !worldIn.isRemote) {
                    if (j == 1) {
                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                player.setHeldItem(handIn, new ItemStack(Items.WATER_BUCKET));
                            } else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))) {
                                player.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                            }
                        }

                        player.addStat(Stats.USE_CAULDRON);
                        this.setWaterLevel(worldIn, pos, state, 0);
                        worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    } else {
                        return ActionResultType.PASS;
                        /*if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                player.setHeldItem(handIn, new ItemStack(Items.WATER_BUCKET));
                            } else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))) {
                                player.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                            }
                        }
                        player.addStat(Stats.USE_CAULDRON);
                        this.setInside(worldIn, pos, state, 0);
                        this.setWaterLevel(worldIn, pos, state, 0);
                        worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 1.0F, 1.0F);*/
                    }
                }
                return ActionResultType.func_233537_a_(worldIn.isRemote);

            } else if (item == Items.GLASS_BOTTLE) {
                if (j == 1 && i > 0 && !worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        ItemStack itemstack4 = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);
                        player.addStat(Stats.USE_CAULDRON);
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.setHeldItem(handIn, itemstack4);
                        } else if (!player.inventory.addItemStackToInventory(itemstack4)) {
                            player.dropItem(itemstack4, false);
                        } else if (player instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity) player).sendContainerToPlayer(player.container);
                        }
                    }

                    worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    this.setWaterLevel(worldIn, pos, state, i - 1);
                }

                return ActionResultType.func_233537_a_(worldIn.isRemote);
            } else if (item == Items.POTION && PotionUtils.getPotionFromItem(itemstack) == Potions.WATER) {
                if (j < 2 && i < 3 && !worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        ItemStack itemstack3 = new ItemStack(Items.GLASS_BOTTLE);
                        player.addStat(Stats.USE_CAULDRON);
                        player.setHeldItem(handIn, itemstack3);
                        if (player instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity) player).sendContainerToPlayer(player.container);
                        }
                    }

                    worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    this.setWaterLevel(worldIn, pos, state, i + 1);
                }

                return ActionResultType.func_233537_a_(worldIn.isRemote);
            } else {
                if (j == 1) {
                    if (i > 0 && item instanceof IDyeableArmorItem) {
                        IDyeableArmorItem idyeablearmoritem = (IDyeableArmorItem) item;
                        if (idyeablearmoritem.hasColor(itemstack) && !worldIn.isRemote) {
                            idyeablearmoritem.removeColor(itemstack);
                            this.setWaterLevel(worldIn, pos, state, i - 1);
                            player.addStat(Stats.CLEAN_ARMOR);
                            return ActionResultType.SUCCESS;
                        }
                    } else if (i > 0 && item instanceof BannerItem) {
                        if (BannerTileEntity.getPatterns(itemstack) > 0 && !worldIn.isRemote) {
                            ItemStack itemstack2 = itemstack.copy();
                            itemstack2.setCount(1);
                            BannerTileEntity.removeBannerData(itemstack2);
                            player.addStat(Stats.CLEAN_BANNER);
                            if (!player.abilities.isCreativeMode) {
                                itemstack.shrink(1);
                                this.setWaterLevel(worldIn, pos, state, i - 1);
                            }

                            if (itemstack.isEmpty()) {
                                player.setHeldItem(handIn, itemstack2);
                            } else if (!player.inventory.addItemStackToInventory(itemstack2)) {
                                player.dropItem(itemstack2, false);
                            } else if (player instanceof ServerPlayerEntity) {
                                ((ServerPlayerEntity) player).sendContainerToPlayer(player.container);
                            }
                        }

                        return ActionResultType.func_233537_a_(worldIn.isRemote);
                    } else if (i > 0 && item instanceof BlockItem) {
                        Block block = ((BlockItem) item).getBlock();
                        if (block instanceof ShulkerBoxBlock && !worldIn.isRemote()) {
                            ItemStack itemstack1 = new ItemStack(Blocks.SHULKER_BOX, 1);
                            if (itemstack.hasTag()) {
                                itemstack1.setTag(itemstack.getTag().copy());
                            }

                            player.setHeldItem(handIn, itemstack1);
                            this.setWaterLevel(worldIn, pos, state, i - 1);
                            player.addStat(Stats.CLEAN_SHULKER_BOX);
                            return ActionResultType.SUCCESS;
                        } else {
                            return ActionResultType.CONSUME;
                        }
                    } else return ActionResultType.PASS;
                }
            }
        }
        return ActionResultType.PASS;
    }

    public void setLeadLevel(World worldIn, BlockPos pos, BlockState state, int level) {
        worldIn.setBlockState(pos, state.with(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 3))).with(CONTENT, level == 0 ? 0 : 2), 2);
    }

    @Override
    public void setWaterLevel(World worldIn, BlockPos pos, BlockState state, int level) {
        worldIn.setBlockState(pos, state.with(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 3))).with(CONTENT, level == 0 ? 0 : 1), 2);
        worldIn.updateComparatorOutputLevel(pos, this);
    }

    @Override
    public void fillWithRain(World worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos).get(CONTENT) < 2) {
            super.fillWithRain(worldIn, pos);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CONTENT);
        super.fillStateContainer(builder);
    }
}
