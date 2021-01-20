package me.gleep.oreexpansion;

import me.gleep.oreexpansion.util.RegistryHandler;
import me.gleep.oreexpansion.world.gen.CustomOreGen;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("oreexpansion")
public class OreExpansion {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "oreexpansion";

    public OreExpansion() {
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
        RenderTypeLookup.setRenderLayer(RegistryHandler.GREY_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.LIGHT_BLUE_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.LIGHT_GREY_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.LIME_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.MAGENTA_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.ORANGE_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.PINK_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.PURPLE_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.RED_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.WHITE_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.YELLOW_CRYSTAL_GLASS.get(), RenderType.getTranslucent());
    }

    /*@SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event){
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
