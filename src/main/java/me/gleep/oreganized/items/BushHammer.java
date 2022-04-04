package me.gleep.oreganized.items;

import com.google.common.collect.ImmutableMap;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.capabilities.engravedblockscap.CapabilityEngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.EngravedBlocks;
import me.gleep.oreganized.capabilities.engravedblockscap.IEngravedBlocks;
import me.gleep.oreganized.items.tiers.ModTier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import me.gleep.oreganized.util.messages.BushHammerClickPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

import static me.gleep.oreganized.capabilities.engravedblockscap.EngravedBlocks.Face.*;
import static me.gleep.oreganized.util.RegistryHandler.*;
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
        super(2.5F, -2.8F, ModTier.LEAD, BUSH_HAMMER_BREAKABLE_BLOCKTAG,
                new Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1)
        );
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        if (EFFECTIVE_ON.containsKey(pState.getBlock())) return this.speed;
        return super.getDestroySpeed(pStack, pState);
    }

    @Override
    public InteractionResult useOn( UseOnContext pContext ) {
        BlockPos pPos = pContext.getClickedPos();
        Level pLevel = pContext.getLevel();
        if (pLevel.getBlockState( pPos ).is(ENGRAVED_TEXTURED_BLOCKS_BLOCKTAG)) {
            String name = pLevel.getBlockState( pContext.getClickedPos() ).getBlock().getRegistryName().getPath();
            String modid = pLevel.getBlockState( pContext.getClickedPos() ).getBlock().getRegistryName().getNamespace();
            Block blockToConvert;
            if ( modid.equals( "minecraft" ) ) {
                blockToConvert = ForgeRegistries.BLOCKS.getValue(
                        new ResourceLocation( Oreganized.MOD_ID, "engraved_" + name ) );
            } else if( modid.equals( "quark" ) ){
                Block quarkBlock = ForgeRegistries.BLOCKS.getValue(
                        new ResourceLocation( Oreganized.MOD_ID, "engraved_" + name ) );
                if(quarkBlock == null) {
                   blockToConvert = ForgeRegistries.BLOCKS.getValue(
                            new ResourceLocation( modid, "engraved_" + name ) );
                } else blockToConvert = quarkBlock;
            } else {
                blockToConvert = ForgeRegistries.BLOCKS.getValue(
                        new ResourceLocation( modid, "engraved_" + name ) );
            }
            if (blockToConvert != Blocks.AIR) pLevel.setBlockAndUpdate( pContext.getClickedPos() , blockToConvert.defaultBlockState());
        }
        if (pLevel.getBlockState(pContext.getClickedPos()).is(ENGRAVEABLE_BLOCKTAG)) {
            IEngravedBlocks cap = Minecraft.getInstance().player.level.getCapability(CapabilityEngravedBlocks.ENGRAVED_BLOCKS_CAPABILITY).orElse(null);
            EngravedBlocks.Face clickedFace = getFaceFromDirection(pContext.getClickedFace(), pContext.getHorizontalDirection());
            if (cap.getEngravedFaces().get(pContext.getClickedPos()) == null ||( evaluateFace(cap, clickedFace, pContext.getClickedPos()) && (cap.getEngravedFaces().get(pContext.getClickedPos()).get(clickedFace).equals("")
                    || cap.getEngravedFaces().get(pContext.getClickedPos()).get(clickedFace).equals("\n\n\n\n\n\n\n")))) {
                if (!pLevel.isClientSide()) {
                    ServerPlayer player = (ServerPlayer) pContext.getPlayer();
                    CHANNEL.sendTo(new BushHammerClickPacket(pPos, clickedFace, player.level.getBlockState(pPos).getBlock()),
                            player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
                    if (!player.gameMode.isCreative()) pContext.getItemInHand().hurt(5, new Random(), player);
                }
                return InteractionResult.SUCCESS;
            }
        } else {
            if (pContext.getPlayer() != null) {
                TranslatableComponent mes = new TranslatableComponent("engraving.fail");
                mes.setStyle(mes.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.WHITE)).withBold(true));
                pContext.getPlayer().displayClientMessage(mes,true);
            }
        }
        return InteractionResult.PASS;
    }

    private boolean evaluateFace(IEngravedBlocks cap, EngravedBlocks.Face face, BlockPos clickedPos){
            if (face.direction == Direction.UP) {
                return (cap.getEngravedFaces().get(clickedPos).get(UP_N).equals("") ||
                        cap.getEngravedFaces().get(clickedPos).get(UP_N).equals("\n\n\n\n\n\n\n")) &&
                        (cap.getEngravedFaces().get(clickedPos).get(UP_S).equals("") ||
                                cap.getEngravedFaces().get(clickedPos).get(UP_S).equals("\n\n\n\n\n\n\n")) &&
                        (cap.getEngravedFaces().get(clickedPos).get(UP_E).equals("") ||
                                cap.getEngravedFaces().get(clickedPos).get(UP_E).equals("\n\n\n\n\n\n\n")) &&
                        (cap.getEngravedFaces().get(clickedPos).get(UP_W).equals("") ||
                                cap.getEngravedFaces().get(clickedPos).get(UP_W).equals("\n\n\n\n\n\n\n"));
            } else if (face.direction == Direction.DOWN) {
            return (cap.getEngravedFaces().get(clickedPos).get(DOWN_N).equals("") ||
                    cap.getEngravedFaces().get(clickedPos).get(DOWN_N).equals("\n\n\n\n\n\n\n")) &&
                    (cap.getEngravedFaces().get(clickedPos).get(DOWN_S).equals("") ||
                    cap.getEngravedFaces().get(clickedPos).get(DOWN_S).equals("\n\n\n\n\n\n\n")) &&
                    (cap.getEngravedFaces().get(clickedPos).get(DOWN_E).equals("") ||
                    cap.getEngravedFaces().get(clickedPos).get(DOWN_E).equals("\n\n\n\n\n\n\n")) &&
                    (cap.getEngravedFaces().get(clickedPos).get(DOWN_W).equals("") ||
                    cap.getEngravedFaces().get(clickedPos).get(DOWN_W).equals("\n\n\n\n\n\n\n"));
        } else {
                return (cap.getEngravedFaces().get(clickedPos).get(face).equals("")
                        || cap.getEngravedFaces().get(clickedPos).get(face).equals("\n\n\n\n\n\n\n"));
            }
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
                case NORTH -> UP_N;
                case SOUTH -> UP_S;
                case WEST -> EngravedBlocks.Face.UP_W;
                case EAST -> UP_E;
                default -> UP_N;
            };
            case NORTH -> EngravedBlocks.Face.FRONT;
            case SOUTH -> EngravedBlocks.Face.BACK;
            case WEST -> EngravedBlocks.Face.LEFT;
            case EAST -> EngravedBlocks.Face.RIGHT;
        };
    }
}
