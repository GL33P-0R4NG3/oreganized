package me.gleep.oreganized.items;

import com.google.common.collect.ImmutableMap;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.capabilities.engravedblockscap.EngravedBlocks;
import com.google.common.collect.*;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.items.tiers.ModTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import me.gleep.oreganized.util.messages.BushHammerClickPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.Tag;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

import static me.gleep.oreganized.util.RegistryHandler.BRICKS_BLOCKTAG;
import static me.gleep.oreganized.util.RegistryHandler.ENGRAVEABLE_BLOCKTAG;
import static me.gleep.oreganized.util.SimpleNetwork.CHANNEL;

public class BushHammer extends DiggerItem{
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
        super(2.5F, -2.8F, ModTier.LEAD, Tag.fromSet(EFFECTIVE_ON.keySet()),
                new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1)
        );
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        Tag<Block> tag = BlockTags.getAllTags().getTag(new ResourceLocation(Oreganized.MOD_ID, "bush_hammer_breakable"));

        if (tag == null) return super.getDestroySpeed(pStack, pState);

        for (Block block : tag.getValues()) {
            if (block.equals(pState.getBlock())) return this.speed;
        }

        return super.getDestroySpeed(pStack, pState);
    }

    @Override
    public InteractionResult useOn( UseOnContext pContext ){
        BlockPos pPos = pContext.getClickedPos();
        Level pLevel = pContext.getLevel();
        if(BRICKS_BLOCKTAG.contains( pLevel.getBlockState( pPos ).getBlock() )){
            String name = pLevel.getBlockState( pContext.getClickedPos() ).getBlock().getRegistryName().getPath();
            pLevel.setBlockAndUpdate( pContext.getClickedPos() , ForgeRegistries.BLOCKS.getValue(
                    new ResourceLocation( Oreganized.MOD_ID , "smooth_" + name ) )
                    .defaultBlockState() );
        }
        if(ENGRAVEABLE_BLOCKTAG.contains( pLevel.getBlockState( pContext.getClickedPos() ).getBlock() )){
            EngravedBlocks.Face clickedFace = getFaceFromDirection( pContext.getClickedFace() , pContext.getHorizontalDirection() );
            if(!pLevel.isClientSide()){
                ServerPlayer player = (ServerPlayer) pContext.getPlayer();
                CHANNEL.sendTo( new BushHammerClickPacket( pPos , clickedFace , player.level.getBlockState( pPos ).getBlock() ) ,
                        player.connection.getConnection() , NetworkDirection.PLAY_TO_CLIENT );
                if (!player.gameMode.isCreative()) pContext.getItemInHand().hurt( 5 , new Random() , player );
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
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

    private EngravedBlocks.Face getFaceFromDirection(Direction pClickedFace, Direction pLookingDirection) {
        return switch(pClickedFace) {
            case DOWN -> switch(pLookingDirection) {
                case NORTH -> EngravedBlocks.Face.DOWN_N;
                case SOUTH -> EngravedBlocks.Face.DOWN_S;
                case WEST -> EngravedBlocks.Face.DOWN_W;
                case EAST -> EngravedBlocks.Face.DOWN_E;
                default -> EngravedBlocks.Face.DOWN_N;
            };
            case UP -> switch(pLookingDirection){
                case NORTH -> EngravedBlocks.Face.UP_N;
                case SOUTH -> EngravedBlocks.Face.UP_S;
                case WEST -> EngravedBlocks.Face.UP_W;
                case EAST -> EngravedBlocks.Face.UP_E;
                default -> EngravedBlocks.Face.UP_N;
            };
            case NORTH -> EngravedBlocks.Face.FRONT;
            case SOUTH -> EngravedBlocks.Face.BACK;
            case WEST -> EngravedBlocks.Face.LEFT;
            case EAST -> EngravedBlocks.Face.RIGHT;
        };
    }
}
