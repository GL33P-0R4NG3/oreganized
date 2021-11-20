package me.gleep.oreganized;

import me.gleep.oreganized.armors.STABase;
import me.gleep.oreganized.tools.STSBase;
import me.gleep.oreganized.util.RegistryHandler;
import me.gleep.oreganized.world.gen.CustomOreGen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

@Mod("oreganized")
public class Oreganized {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "oreganized";

    public Oreganized() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::particleFactoryRegistrationEvent);

        RegistryHandler.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CustomOreGen.registerOres();
        });
        /*event.enqueueWork(() -> {
            SimpleNetwork.register();
        });*/
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        //BlockEntityRenderers.register(RegistryHandler.STONE_SIGN_TE.get(), BlockEntityRendererProvider::new);

        //RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.SHRAPNEL_TNT_ENTITY.get(), ShrapnelTNTRenderer::new);
        //RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.LEAD_NUGGET_ENTITY.get(), LeadNuggetRenderer::new);

        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.BLACK_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.BLUE_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.BROWN_CRYSTAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.CRYSTAL_GLASS.get(), RenderType.translucent());
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
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.CRYSTAL_GLASS_PANE.get(), RenderType.translucent());
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
                        p_174676_.getTag() != null ? p_174676_.getTag().getInt("Dist") : 4)
        );
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.SILVER_TINTED_DIAMOND_BOOTS.get(),
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
        );

    }

    /*public void particleFactoryRegistrationEvent(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(RegistryHandler.DAWN_SHINE_PARTICLE.get(), DawnShineParticle.Factory::new);
    }*/

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
