package me.gleep.oreganized.events;

import com.mojang.blaze3d.systems.RenderSystem;
import me.gleep.oreganized.armors.STABase;
import me.gleep.oreganized.blocks.ModCauldron;
import me.gleep.oreganized.capabilities.engravedblockscap.CapabilityEngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.IEngravedBlocks;
import me.gleep.oreganized.items.BushHammer;
import me.gleep.oreganized.potion.ModPotions;
import me.gleep.oreganized.tools.STSBase;
import me.gleep.oreganized.util.RegistryHandler;
import me.gleep.oreganized.util.messages.UpdateClientEngravedBlocks;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.PistonEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.*;

import static me.gleep.oreganized.Oreganized.MOD_ID;
import static me.gleep.oreganized.util.RegistryHandler.ENGRAVEABLE_BLOCKTAG;
import static me.gleep.oreganized.util.SimpleNetwork.CHANNEL;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents{
    /**
     * Event to handle Silver Tintend Swords break
     */
    @SubscribeEvent
    public static void onToolBreakEvent(final PlayerDestroyItemEvent event) {
        ItemStack stack = event.getOriginal();
        Player pl = event.getPlayer();
        ItemStack item = ItemStack.EMPTY;

        if (stack.getItem() instanceof STSBase) {
            int count = (int) Math.round(((double) stack.getOrCreateTag().getInt("TintedDamage") / (double) STSBase.MAX_TINT_DURABILITY) * 9D);
            item = new ItemStack(RegistryHandler.SILVER_NUGGET.get(), count);
        } else if (stack.getItem() instanceof STABase) {
            int count = (int) Math.round(((double) stack.getOrCreateTag().getInt("TintedDamage") / (double) STABase.MAX_TINT_DURABILITY) * 9D);
            item = new ItemStack(RegistryHandler.SILVER_NUGGET.get(), count);
        }

        pl.drop(item, true);
    }

    /**
     * Event to handle Cauldron replacement
     */
    @SubscribeEvent
    public static void onPlayerRightClick(final PlayerInteractEvent.RightClickBlock event) {
        ItemStack item = event.getItemStack();
        BlockPos pos = event.getPos();
        Level level = event.getWorld();

        if (level.getBlockState(pos).equals(Blocks.CAULDRON.defaultBlockState())) {
            if (item.getItem().equals(RegistryHandler.LEAD_BLOCK_ITEM.get())) {
                if (!level.isClientSide()) {
                    level.removeBlock(pos, false);
                    level.setBlockAndUpdate(pos, RegistryHandler.LEAD_CAULDRON.get().defaultBlockState());
                    if (!event.getPlayer().isCreative()) item.shrink(1);
                    level.playSound((Player) null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    event.getPlayer().awardStat(Stats.USE_CAULDRON);
                }

                if (event.isCancelable()) {
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                    event.setCanceled(true);
                }
            } else if (item.getItem().equals(RegistryHandler.MOLTEN_LEAD_BUCKET.get())) {
                if (!level.isClientSide()) {
                    level.removeBlock(pos, false);
                    level.setBlockAndUpdate(pos, RegistryHandler.LEAD_CAULDRON.get().defaultBlockState().setValue(ModCauldron.LEVEL, 3));
                    if (!event.getPlayer().isCreative()) event.getPlayer().setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET, 1));
                    level.playSound((Player) null, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
                    event.getPlayer().awardStat(Stats.USE_CAULDRON);
                }

                if (event.isCancelable()) {
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                    event.setCanceled(true);
                }
            }
        }
    }

    /**
     * Event to handle entities in mod fluid
     */
    /*@SubscribeEvent
    public static void onEntityUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = event.getEntityLiving();
            Tag<Fluid> tag = FluidTags.getAllTags().getTag(new ResourceLocation(Oreganized.MOD_ID + ":lead"));

            if (entity.updateFluidHeightAndDoFluidPushing(tag, 0.009D)) {
                entity.setSecondsOnFire(10);
                entity.hurt(ModDamageSource.MOLTEN_LEAD, 3.0F);

                Vec3 travelVector = new Vec3((double)entity.xxa, (double)entity.yya, (double)entity.zza);
                double d0 = 0.08D;
                AttributeInstance gravity = entity.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
                boolean flag = entity.getDeltaMovement().y <= 0.0D;
                d0 = gravity.getValue();

                double d7 = entity.getY();
                entity.moveRelative(0.01F, travelVector);
                entity.move(MoverType.SELF, entity.getDeltaMovement());
                if (entity.getFluidHeight(tag) <= entity.getFluidJumpThreshold()) {
                    entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.2D, (double)0.4F, 0.2D));
                    Vec3 vector3d3 = entity.getFluidFallingAdjustedMovement(d0, flag, entity.getDeltaMovement());
                    entity.setDeltaMovement(vector3d3);
                } else {
                    entity.setDeltaMovement(entity.getDeltaMovement().scale(0.2D));
                }

                if (!entity.isNoGravity()) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, -d0 / 4.0D, 0.0D));
                }

                Vec3 vector3d4 = entity.getDeltaMovement();
                if (entity.horizontalCollision && entity.isFree(vector3d4.x, vector3d4.y + (double)0.2F - entity.getY() + d7, vector3d4.z)) {
                    entity.setDeltaMovement(vector3d4.x, (double)0.3F, vector3d4.z);
                }
            }
        }
    }*/

    /**
     * Event for BushHammer mechanics
     */
    @SubscribeEvent
    public static void onBlockDestroyed(BlockEvent.BreakEvent event) {
        LevelAccessor world = event.getWorld();
        Level level = event.getPlayer().level;
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);
        ItemStack currentItem = event.getPlayer().getItemInHand(event.getPlayer().getUsedItemHand());

        if (currentItem.getItem().equals(RegistryHandler.BUSH_HAMMER.get())) {
            if (event.getPlayer().isCrouching()) {
                for (Block b : BushHammer.EFFECTIVE_ON.keySet()) {
                    if (state.getBlock().equals(b) && !event.getPlayer().isCreative()) {
                        world.setBlock(pos, BushHammer.EFFECTIVE_ON.get(b).defaultBlockState(), 2);
                        currentItem.hurtAndBreak(1, event.getPlayer(), (player) -> {
                            player.broadcastBreakEvent(event.getPlayer().getUsedItemHand());
                        });
                        event.setCanceled(true);
                    }
                }
            } else {
                List<CraftingRecipe> l = level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING);
                Tag<Block> tag = BlockTags.getAllTags().getTag(new ResourceLocation(MOD_ID, "bush_hammer_breakable"));
                String destroyedBlockName = state.getBlock().getRegistryName().getPath();

                if (tag == null) return;

                for (CraftingRecipe recipe : l) {
                    for (Block block : tag.getValues()) {
                        String itemName = recipe.getResultItem().getItem().getRegistryName().getPath();
                        String blockName = block.getRegistryName().getPath();

                        if (itemName.equals(blockName) && blockName.equals(destroyedBlockName)) {
                            NonNullList<Ingredient> ingredients = recipe.getIngredients();
                            List<ItemStack> stacks = new ArrayList<>();
                            int resultCount = recipe.getResultItem().getCount();

                            // If the block is in minecraft:stairs tag drop only one ingredient
                            for (Block b : BlockTags.getAllTags().getTag(new ResourceLocation("minecraft", "stairs")).getValues()) {
                                if (b.getRegistryName().getPath().equals(blockName)) {
                                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), ingredients.get(0).getItems()[0]);

                                    world.setBlock(pos, Blocks.AIR.defaultBlockState(), 1);
                                    currentItem.hurtAndBreak(1, event.getPlayer(), (player) -> {
                                        player.broadcastBreakEvent(event.getPlayer().getUsedItemHand());
                                    });
                                    event.setCanceled(true);
                                    return;
                                }
                            }

                            // Adding items to List<ItemStack> for calculation purposes
                            for (Ingredient i : ingredients) {
                                boolean added = false;

                                for (ItemStack s : i.getItems()) {
                                    for (ItemStack stack : stacks) {
                                        if (stack.getItem().equals(s.getItem())) {
                                            stack.setCount(stack.getCount() + 1);
                                            added = true;
                                            break;
                                        }
                                    }

                                    if (added) break;
                                }

                                if (added || i.getItems().length <= 0) continue;

                                int randItem = level.getRandom().nextInt(i.getItems().length);
                                ItemStack stack = new ItemStack(i.getItems()[randItem].getItem());
                                stack.setCount(1);

                                stacks.add(stack);
                            }

                            // If the result item count is greater than 1, recalculate item drops
                            if (resultCount > 1) {
                                for (ItemStack stack : stacks) {
                                    float itemPerResult = (float) stack.getCount() / resultCount;
                                    int a = Mth.floor(itemPerResult);

                                    stack.setCount(a);

                                    if (a != itemPerResult) {
                                        float chance = itemPerResult - a;

                                        if (level.getRandom().nextFloat() <= chance) stack.setCount(stack.getCount() + 1);
                                    }
                                }
                            }

                            // Drop items cycle
                            for (ItemStack stack : stacks) {
                                if (stack.getCount() == 0) continue;

                                float amp = 0.3f;   // for enchant implementation, smaller value means less loss
                                if (!currentItem.getEnchantmentTags().isEmpty()) {
                                    // Enchantment cycle
                                    for (int j = 0; j < currentItem.getEnchantmentTags().size(); j++) {
                                        String enchantName = currentItem.getEnchantmentTags().getCompound(j).getString("id");
                                        int lvl = currentItem.getEnchantmentTags().getCompound(j).getInt("lvl");

                                        if (enchantName.equals("minecraft:fortune")) {
                                            amp -= lvl <= 3 ? 0.1f * lvl : 0;
                                            break;
                                        }
                                    }
                                }

                                 int range = (int) ((stack.getCount() * amp) + 0.5f);
                                if (range > 0) stack.setCount(stack.getCount() - level.getRandom().nextInt(range + 1));

                                if (stack.getCount() > 0) Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
                            }

                            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 1);
                            currentItem.hurtAndBreak(1, event.getPlayer(), (player) -> {
                                player.broadcastBreakEvent(event.getPlayer().getUsedItemHand());
                            });
                            event.setCanceled(true);
                            return;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void applyLeadEffect(LivingEntityUseItemEvent.Finish event){
        if (event.getItem().getItem().isEdible()) {
            if (event.getEntityLiving() instanceof Player player) {
                for (int i = 0; i < 9; i++) {
                    if (ItemTags.getAllTags().getTag(new ResourceLocation("forge","ingots/lead")).contains(player.getInventory().items.get(i).getItem())) {
                        player.addEffect(new MobEffectInstance(ModPotions.STUNNING,40*20));
                        return;
                    }
                }

                if (ItemTags.getAllTags().getTag(new ResourceLocation("forge","ingots/lead")).contains(player.getInventory().offhand.get(0).getItem())) {
                    player.addEffect(new MobEffectInstance(ModPotions.STUNNING,40*20));
                }
            }
        }
    }

    /**
     * Event to change fluid fog density for rendering
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void getFogDensity(EntityViewRenderEvent.FogDensity event) {
        Camera info = event.getInfo();
        BlockState blockState = info.getBlockAtCamera();

        if (blockState.getBlock().equals(RegistryHandler.MOLTEN_LEAD_BLOCK.get())) {
            RenderSystem.disableCull();
            event.setDensity(1.2F);
            event.setCanceled(true);
        }
    }

    /**
     * Event to change the fluid fog color for rendering
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void getFogColor(EntityViewRenderEvent.FogColors event) {
        Camera info = event.getInfo();
        BlockState blockState = info.getBlockAtCamera();

        if (blockState.getBlock().equals(RegistryHandler.MOLTEN_LEAD_BLOCK.get())) {
            event.setRed(57F / 256F);
            event.setGreen(57F / 256F);
            event.setBlue(95F / 256F);
        }
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event.getSource().getDirectEntity() instanceof Player) {
            Player player = (Player) event.getSource().getDirectEntity();
            LivingEntity living = event.getEntityLiving();

            if (living.isInvertedHealAndHarm()) {
                CompoundTag nbt = new CompoundTag();
                nbt.putLong("t", player.level.getGameTime());
                nbt.putBoolean("Shine", true);

                for (ItemStack stack: player.getInventory().items) {
                    if (stack.getItem().equals(RegistryHandler.SILVER_INGOT.get())) stack.setTag(nbt);
                }

                if (player.getInventory().offhand.get(0).getItem().equals(RegistryHandler.SILVER_INGOT.get())) {
                    player.getInventory().offhand.get(0).setTag(nbt);
                }
            }
        }
    }

    // ENGRAVING HANDLING STARTS HERE

    public static boolean recentlyActivatedPiston = false;
    public static byte recentlyActivatedPistonDelay = 0;

    @SubscribeEvent
    public static void updatePistonDelay( TickEvent.ServerTickEvent event ){
        if(recentlyActivatedPiston && recentlyActivatedPistonDelay == 0){
            recentlyActivatedPistonDelay = 20;
        }
        if(recentlyActivatedPistonDelay > 0){
            recentlyActivatedPistonDelay--;
        }
        if(recentlyActivatedPistonDelay == 0){
            recentlyActivatedPiston = false;
        }
    }

    @SubscribeEvent
    public static void updateEngravedBlocks( TickEvent.PlayerTickEvent event ){
        Level level = event.player.level;
        if(!level.isClientSide()){
            IEngravedBlocks capability = level.getCapability( CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY )
                    .orElse( null );
            for(BlockPos pos : capability.getEngravedBlocks()){
                if(!ENGRAVEABLE_BLOCKTAG.contains( event.player.level.getBlockState( pos ).getBlock() ) && !recentlyActivatedPiston){
                    capability.removeEngravedBlock( pos );
                    CHANNEL.send( PacketDistributor.ALL.noArg() , new UpdateClientEngravedBlocks( capability.getEngravedBlocks() ,
                            capability.getEngravedFaces() , capability.getEngravedColors() ) );
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEngravedBlockMoved( PistonEvent.Pre event ){
        if(event.getWorld() instanceof Level){
            Level level = (Level) event.getWorld();
            if(!level.isClientSide()){
                PistonStructureResolver pistonHelper = event.getStructureHelper();
                pistonHelper.resolve();
                Direction pushDirection = pistonHelper.getPushDirection();
                for(BlockPos oldPos : pistonHelper.getToPush()){
                    if(!(level.getBlockState( event.getPos() ).getBlock() == Blocks.PISTON && !event.getPistonMoveType().isExtend)){
                        BlockPos newPos = oldPos.relative( pushDirection );
                        IEngravedBlocks cap = level.getCapability( CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY ).orElse( null );
                        if(cap.isEngraved( oldPos )){
                            cap.getEngravedBlocks().add( newPos );
                            cap.getEngravedFaces().put( newPos , cap.getEngravedFaces().get( oldPos ) );
                            cap.getEngravedColors().put( newPos , cap.getEngravedColors().get( oldPos ) );
                            cap.getEngravedBlocks().remove( oldPos );
                            cap.getEngravedFaces().remove( oldPos );
                            cap.getEngravedColors().remove( oldPos );
                            CHANNEL.send( PacketDistributor.ALL.noArg() ,
                                    new UpdateClientEngravedBlocks( cap.getEngravedBlocks() , cap.getEngravedFaces() , cap.getEngravedColors() ) );
                        }
                    }
                }
                if(!pistonHelper.getToPush().isEmpty()){
                    recentlyActivatedPiston = true;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin( PlayerEvent.PlayerLoggedInEvent event ){
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        ServerLevel level = player.getLevel();
        IEngravedBlocks cap = level.getCapability( CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY ).orElse( null );
        CHANNEL.send( PacketDistributor.PLAYER.with( () -> player ) ,
                new UpdateClientEngravedBlocks( cap.getEngravedBlocks() , cap.getEngravedFaces() , cap.getEngravedColors() ) );
    }

    @SubscribeEvent
    public static void onPlayerLogin( PlayerEvent.PlayerChangedDimensionEvent event ){
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        ServerLevel level = player.getLevel();
        IEngravedBlocks cap = level.getCapability( CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY ).orElse( null );
        CHANNEL.send( PacketDistributor.PLAYER.with( () -> player ) ,
                new UpdateClientEngravedBlocks( cap.getEngravedBlocks() , cap.getEngravedFaces() , cap.getEngravedColors() ) );
    }
}
