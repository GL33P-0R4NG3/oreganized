package me.gleep.oreganized;

import me.gleep.oreganized.util.RegistryHandler;
import me.gleep.oreganized.world.gen.CustomOreGen;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

@Mod("oreganized")
public class Oreganized {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "oreganized";

    public Oreganized() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        RegistryHandler.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        CustomOreGen.registerOres();
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);

        RenderTypeLookup.setRenderLayer(RegistryHandler.BLACK_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.BLUE_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.BROWN_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.CYAN_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.GREEN_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.GRAY_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.LIGHT_BLUE_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.LIGHT_GRAY_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.LIME_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.MAGENTA_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.ORANGE_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.PINK_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.PURPLE_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.RED_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.WHITE_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.YELLOW_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.BLACK_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.BLUE_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.BROWN_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.CYAN_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.GREEN_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.GRAY_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.LIGHT_BLUE_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.LIGHT_GRAY_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.LIME_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.MAGENTA_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.ORANGE_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.PINK_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.PURPLE_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.RED_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.WHITE_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.YELLOW_CRYSTAL_GLASS_PANE.get(), RenderType.getTranslucent());

        //RenderTypeLookup.setRenderLayer(RegistryHandler.LEAD_FLUID.get(), RenderType.getWaterMask());
        //RenderTypeLookup.setRenderLayer(RegistryHandler.LEAD_FLUID_BLOCK.get(), RenderType.getSolid());

        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_MIRROR.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":dist"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("Dist") : 4));

        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_DIAMOND_BOOTS.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_DIAMOND_CHESTPLATE.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_DIAMOND_HELMET.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_DIAMOND_LEGGINGS.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_DIAMOND_SWORD.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_GOLDEN_BOOTS.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_GOLDEN_CHESTPLATE.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_GOLDEN_HELMET.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_GOLDEN_LEGGINGS.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_GOLDEN_SWORD.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_NETHERITE_BOOTS.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_NETHERITE_CHESTPLATE.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_NETHERITE_HELMET.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_NETHERITE_LEGGINGS.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
        event.enqueueWork(() -> ItemModelsProperties.registerProperty(RegistryHandler.SILVER_TINTED_NETHERITE_SWORD.get(),
                new ResourceLocation(Oreganized.MOD_ID + ":tinted_damage"),
                (p_call_1_, p_call_2_, p_call_3_) -> p_call_1_.getTag() != null ? p_call_1_.getTag().getInt("TintedDamage") : 150));
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
