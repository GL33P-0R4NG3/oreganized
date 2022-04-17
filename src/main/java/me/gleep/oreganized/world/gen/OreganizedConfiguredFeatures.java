package me.gleep.oreganized.world.gen;

import com.google.common.collect.ImmutableList;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class OreganizedConfiguredFeatures {
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_SILVER_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, RegistryHandler.SILVER_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, RegistryHandler.DEEPSLATE_SILVER_ORE.get().defaultBlockState()));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_LEAD_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, RegistryHandler.LEAD_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, RegistryHandler.DEEPSLATE_LEAD_ORE.get().defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_SILVER = register("silver_ore", Feature.ORE, new OreConfiguration(ORE_SILVER_TARGET_LIST, 14));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_SILVER_DEEP_UNDERGROUND = register("silver_ore_underground", Feature.ORE, new OreConfiguration(ORE_SILVER_TARGET_LIST, 9));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_LEAD = register("lead_ore", Feature.ORE, new OreConfiguration(ORE_LEAD_TARGET_LIST, 13));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_LEAD_DEEP_UNDERGROUND = register("lead_ore_underground", Feature.ORE, new OreConfiguration(ORE_LEAD_TARGET_LIST, 8));

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String key, F feature, FC config) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Oreganized.MOD_ID, key).toString(), new ConfiguredFeature<>(feature, config));
    }

    public static void init() {

    }
}
