package me.gleep.oreganized;

import me.gleep.oreganized.util.RegistryHandler;
import me.gleep.oreganized.world.gen.CustomOreGen;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Mod("oreganized")
public class Oreganized {
    private static final Logger LOGGER = LogManager.getLogger();
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

        ItemModelsProperties.registerProperty(RegistryHandler.SILVER_MIRROR.get(), new ResourceLocation(Oreganized.MOD_ID + ":dist"), new IItemPropertyGetter() {
            final float RANGE = 10.0f;
            boolean isUndeadNearby = false;

            @Override
            public float call(ItemStack p_call_1_, @Nullable ClientWorld p_call_2_, @Nullable LivingEntity p_call_3_) {
                int dist = 4;
                if (!(p_call_1_.getAttachedEntity() instanceof PlayerEntity)) return dist;
                CompoundNBT nbt = new CompoundNBT();
                PlayerEntity player = (PlayerEntity) p_call_3_;
                BlockPos pos = player.getPosition();
                List<Entity> list = player.getEntityWorld().getEntitiesInAABBexcluding(null,
                        new AxisAlignedBB(pos.getX() + RANGE, pos.getY() + RANGE, pos.getZ() + RANGE,
                                pos.getX() - RANGE, pos.getY() - RANGE, pos.getZ() - RANGE),
                        null);

                isUndeadNearby = false;
                for (Entity e : list) {
                    if (e.isLiving()) {
                        LivingEntity living = (LivingEntity) e;
                        if (living.isEntityUndead()) {
                            isUndeadNearby = true;
                            if (p_call_1_.getTag() != null) nbt.merge(p_call_1_.getTag());
                            double distance = living.getDistance(player);
                            if (distance < RANGE) {
                                dist = (int) Math.floor(distance / (RANGE / 4));
                                if (!(dist > 1))
                                    dist = 1;
                                else if (dist > 3)
                                    dist = 3;
                            }
                        }
                    }
                }

                if (!isUndeadNearby) {
                    dist = 4;
                }
                return dist;
            }
        });
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
