package me.gleep.oreganized;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import me.gleep.oreganized.blocks.client.ShrapnelBombRenderer;
import me.gleep.oreganized.capabilities.CapabilityHandler;
import me.gleep.oreganized.util.RegistryHandler;
import me.gleep.oreganized.util.SimpleNetwork;
import me.gleep.oreganized.events.*;
import me.gleep.oreganized.world.gen.OreganizedConfiguredFeatures;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;

@Mod("oreganized")
public class Oreganized {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "oreganized";

    public Oreganized() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        RegistryHandler.init();
        bus.addListener(this::setup);
        bus.addListener(this::doClientStuff);
        bus.addListener(this::registerRenderers);

        MinecraftForge.EVENT_BUS.register( new CapabilityHandler() );
        //MinecraftForge.EVENT_BUS.register( new StunnedOverlayRenderer() );
    }

    private void setup(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            OreganizedConfiguredFeatures.init();
            SimpleNetwork.register();
        });

        ModEvents.ENGRAVED_COPPER_BLOCKS = ImmutableList.of(RegistryHandler.ENGRAVED_CUT_COPPER.get(),
                RegistryHandler.ENGRAVED_EXPOSED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_WEATHERED_CUT_COPPER.get(),
                RegistryHandler.ENGRAVED_OXIDIZED_CUT_COPPER.get());

        ModEvents.ENGRAVED_WAXED_COPPER_BLOCKS = ImmutableBiMap.of(
                RegistryHandler.ENGRAVED_WAXED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_CUT_COPPER.get(),
                RegistryHandler.ENGRAVED_WAXED_EXPOSED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_EXPOSED_CUT_COPPER.get(),
                RegistryHandler.ENGRAVED_WAXED_WEATHERED_CUT_COPPER.get(),RegistryHandler.ENGRAVED_WEATHERED_CUT_COPPER.get(),
                RegistryHandler.ENGRAVED_WAXED_OXIDIZED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_OXIDIZED_CUT_COPPER.get());

        ModEvents.WAXED_BLOCKS= ImmutableBiMap.ofEntries(
                Map.entry(RegistryHandler.WAXED_WHITE_CONCRETE_POWDER.get(), Blocks.WHITE_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_ORANGE_CONCRETE_POWDER.get(), Blocks.ORANGE_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_MAGENTA_CONCRETE_POWDER.get(), Blocks.MAGENTA_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_LIGHT_BLUE_CONCRETE_POWDER.get(), Blocks.LIGHT_BLUE_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_YELLOW_CONCRETE_POWDER.get(), Blocks.YELLOW_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_LIME_CONCRETE_POWDER.get(), Blocks.LIME_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_PINK_CONCRETE_POWDER.get(), Blocks.PINK_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_GRAY_CONCRETE_POWDER.get(), Blocks.GRAY_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_LIGHT_GRAY_CONCRETE_POWDER.get(), Blocks.LIGHT_GRAY_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_CYAN_CONCRETE_POWDER.get(), Blocks.CYAN_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_PURPLE_CONCRETE_POWDER.get(), Blocks.PURPLE_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_BLUE_CONCRETE_POWDER.get(), Blocks.BLUE_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_BROWN_CONCRETE_POWDER.get(), Blocks.BROWN_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_GREEN_CONCRETE_POWDER.get(), Blocks.GREEN_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_RED_CONCRETE_POWDER.get(), Blocks.RED_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_BLACK_CONCRETE_POWDER.get(), Blocks.BLACK_CONCRETE_POWDER),
                Map.entry(RegistryHandler.WAXED_SPOTTED_GLANCE.get(), RegistryHandler.SPOTTED_GLANCE.get())
        );
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        //BlockEntityRenderers.register(RegistryHandler.STONE_SIGN_TE.get(), BlockEntityRendererProvider::new);

        //RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.SHRAPNEL_TNT_ENTITY.get(), ShrapnelTNTRenderer::new);
        //RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.LEAD_NUGGET_ENTITY.get(), LeadNuggetRenderer::new);

        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.BLACK_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.BLUE_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.BROWN_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.CYAN_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.GREEN_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.GRAY_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.LIGHT_BLUE_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.LIGHT_GRAY_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.LIME_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.MAGENTA_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.ORANGE_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.PINK_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.PURPLE_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.RED_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.WHITE_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.YELLOW_CRYSTAL_GLASS.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.BLACK_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.BLUE_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.BROWN_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.CYAN_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.GREEN_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.GRAY_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.LIGHT_BLUE_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.LIGHT_GRAY_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.LIME_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.MAGENTA_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.ORANGE_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.PINK_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.PURPLE_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.RED_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.WHITE_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.YELLOW_CRYSTAL_GLASS_PANE.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.MOLTEN_LEAD_BLOCK.get(), RenderType.translucent());

        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_INGOT.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":shine"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? (p_174676_.getTag().getBoolean("Shine") ? 1 : 0) : 0)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_MIRROR.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":dist"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("Dist") : 8)
        );
        /*event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_DIAMOND_BOOTS.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STABase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_DIAMOND_CHESTPLATE.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STABase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_DIAMOND_HELMET.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STABase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_DIAMOND_LEGGINGS.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STABase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_DIAMOND_SWORD.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STSBase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_GOLDEN_BOOTS.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STABase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_GOLDEN_CHESTPLATE.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STABase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_GOLDEN_HELMET.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STABase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_GOLDEN_LEGGINGS.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STABase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_GOLDEN_SWORD.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STSBase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_NETHERITE_BOOTS.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) -> 
        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STABase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_NETHERITE_CHESTPLATE.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) -> 
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STABase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_NETHERITE_HELMET.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) -> 
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STABase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_NETHERITE_LEGGINGS.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) ->
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STABase.MAX_TINT_DURABILITY)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_NETHERITE_SWORD.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (ItemStack p_174676_, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) -> 
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("TintedDamage") : STSBase.MAX_TINT_DURABILITY)
        );*/
    }

    private void registerRenderers( EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer( RegistryHandler.SHRAPNEL_BOMB_ENTITY.get(), ShrapnelBombRenderer::new);
    }

    /*@SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        RegistryHandler.BLOCKS.getEntries().stream().filter(block -> !(block.get() instanceof FlowingFluidBlock))
            .map(RegistryObject::get).forEach(block -> {
                final Item.Properties properties = new Item.Properties().group(ItemGroup.MATERIALS);
                final BlockItem blockItem = new BlockItem(block, properties);
                blockItem.setRegistryName(block.getRegistryName());
                registry.register(blockItem);
        });
    }*/
}
