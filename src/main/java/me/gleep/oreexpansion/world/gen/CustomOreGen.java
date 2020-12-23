package me.gleep.oreexpansion.world.gen;

import me.gleep.oreexpansion.OreExpansion;
import me.gleep.oreexpansion.util.RegistryHandler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = OreExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomOreGen {
    private static final ArrayList<ConfiguredFeature<?, ?>> overworldOres = new ArrayList<ConfiguredFeature<?, ?>>();
    //private static final ArrayList<ConfiguredFeature<?, ?>> netherOres = new ArrayList<ConfiguredFeature<?, ?>>();
    //private static final ArrayList<ConfiguredFeature<?, ?>> endOres = new ArrayList<ConfiguredFeature<?, ?>>();

    public static void registerOres() {
        //BASE_STONE_OVERWORLD is for generating in stone, granite, diorite, and andesite
        //NETHERRACK is for generating in netherrack
        //BASE_STONE_NETHER is for generating in netherrack, basalt and blackstone

        //Overworld Ore Register
        overworldOres.add(register("silver_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, RegistryHandler.SILVER_ORE.get().getDefaultState(), 3)) //Vein Size
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(30, 0, 20)))
                .square()
                .func_242731_b(4)));

        overworldOres.add(register("lead_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, RegistryHandler.LEAD_ORE.get().getDefaultState(), 12)) //Vein Size
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(50, 0, 20)))
                .square()
                .func_242731_b(1)));

        //Nether Ore Register
        /*netherOres.add(register("flame_crystal_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.NETHERRACK, RegistryHandlerBlocks.FLAME_CRYSTAL_ORE.get().getDefaultState(), 4))
                .range(48).square()
                .func_242731_b(64)));*/

        //The End Ore Register
        /*endOres.add(register("air_block", Feature.ORE.withConfiguration(new OreFeatureConfig(
                new BlockMatchRuleTest(Blocks.END_STONE), RegistryHandlerBlocks.AIR_CRYSTAL_BLOCK.get().getDefaultState(), 4)) //Vein Size
                .range(128).square() //Spawn height start
                .func_242731_b(64))); //Chunk spawn frequency*/
    }

    @SubscribeEvent
    public static void gen(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();

        if (event.getCategory().equals(Biome.Category.NETHER)) {
            /*for(ConfiguredFeature<?, ?> ore : netherOres) {
                if (ore != null) generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            }*/

        } else if (event.getCategory().equals(Biome.Category.THEEND)) {
            /*for(ConfiguredFeature<?, ?> ore : endOres) {
                if (ore != null) generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            }*/

        } else {
            for (ConfiguredFeature<?, ?> ore : overworldOres) {
                if (ore != null) generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            }
        }
    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, OreExpansion.MOD_ID + ":" + name, configuredFeature);
    }
}
