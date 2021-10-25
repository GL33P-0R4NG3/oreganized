package me.gleep.oreganized.world.gen;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomOreGen {
    private static final ArrayList<ConfiguredFeature<?, ?>> overworldOres = new ArrayList<ConfiguredFeature<?, ?>>();
    //private static final ArrayList<ConfiguredFeature<?, ?>> netherOres = new ArrayList<ConfiguredFeature<?, ?>>();
    //private static final ArrayList<ConfiguredFeature<?, ?>> endOres = new ArrayList<ConfiguredFeature<?, ?>>();

    public static void registerOres() {
        //BASE_STONE_OVERWORLD is for generating in stone, granite, diorite, and andesite
        //NETHERRACK is for generating in netherrack
        //BASE_STONE_NETHER is for generating in netherrack, basalt and blackstone

        //Overworld Ore Register
        overworldOres.add(register("silver_ore", Feature.ORE.configured(new OreConfiguration(
                OreConfiguration.Predicates.NATURAL_STONE, RegistryHandler.SILVER_ORE.get().defaultBlockState(), 3)) //Vein Size
                .range(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(30), VerticalAnchor.absolute(50)))).config())
                .squared()
                .rarity(4))
        );

        overworldOres.add(register("lead_ore", Feature.ORE.configured(new OreConfiguration(
                OreConfiguration.Predicates.NATURAL_STONE, RegistryHandler.LEAD_ORE.get().defaultBlockState(), 16)) //Vein Size
                .range(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(30), VerticalAnchor.absolute(50)))).config())
                .squared()
                .rarity(1))
        );

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

        if (event.getCategory().equals(Biome.BiomeCategory.NETHER)) {
            /*for(ConfiguredFeature<?, ?> ore : netherOres) {
                if (ore != null) generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            }*/

        } else if (event.getCategory().equals(Biome.BiomeCategory.THEEND)) {
            /*for(ConfiguredFeature<?, ?> ore : endOres) {
                if (ore != null) generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            }*/

        } else {
            for (ConfiguredFeature<?, ?> ore : overworldOres) {
                if (ore != null) generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ore);
            }
        }
    }

    private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Oreganized.MOD_ID + ":" + name, configuredFeature);
    }
}
