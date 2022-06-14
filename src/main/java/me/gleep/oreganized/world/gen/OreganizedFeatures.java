package me.gleep.oreganized.world.gen;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;

public class OreganizedFeatures {

    public static Holder<PlacedFeature> SILVER_ORE_LOW;
    public static Holder<PlacedFeature> SILVER_ORE_HIGH;
    public static Holder<PlacedFeature> LEAD_ORE;
    public static Holder<PlacedFeature> LEAD_ORE_SAVANNAH;


    public static void registerOreFeatures() {
        List<OreConfiguration.TargetBlockState> silverOre = List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, RegistryHandler.SILVER_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, RegistryHandler.DEEPSLATE_SILVER_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> leadOre = List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, RegistryHandler.LEAD_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, RegistryHandler.DEEPSLATE_LEAD_ORE.get().defaultBlockState()));

        SILVER_ORE_LOW = registerPlacedOreFeature("silver_ore", new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(silverOre, 9)), // Vein size of 9
                CountPlacement.of(2), // How rare the ore is, closer to 0, the rarer it is.
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(-15), VerticalAnchor.absolute(5))); // Triangle distribution between y values, uniform would be a flat chance between the values while triangle has a 'peak' of chance.
        SILVER_ORE_HIGH = registerPlacedOreFeature("silver_ore_high", new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(silverOre, 9)), // Vein size of 9
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(140), VerticalAnchor.absolute(160)));
        LEAD_ORE = registerPlacedOreFeature("lead_ore", new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(leadOre, 8)), // Vein size of 8
                CountPlacement.of(10),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(-20)));
        LEAD_ORE_SAVANNAH = registerPlacedOreFeature("lead_ore_savannah", new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(leadOre, 13)), // Vein size of 13
                CountPlacement.of(15),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(50), VerticalAnchor.absolute(80)));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> registerPlacedOreFeature(String regName, ConfiguredFeature<C, F> feature, PlacementModifier ... placementModifiers) {
        return PlacementUtils.register(regName, Holder.direct(feature), placementModifiers);
    }

    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        // Check that we're not in the nether or end to save on resources.
        if (event.getCategory() == Biome.BiomeCategory.NETHER || event.getCategory() == Biome.BiomeCategory.THEEND) return;

        // Check if we're in a savannah to add extra lead generation. Notice that there is no else statement meaning other ore placements
        // will still generate in the biome.
        if (event.getCategory() == Biome.BiomeCategory.SAVANNA) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, LEAD_ORE_SAVANNAH);
        }
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, SILVER_ORE_LOW);
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, SILVER_ORE_HIGH);
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, LEAD_ORE);
    }
}
