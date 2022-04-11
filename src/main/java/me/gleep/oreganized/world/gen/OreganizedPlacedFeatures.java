package me.gleep.oreganized.world.gen;

import me.gleep.oreganized.Oreganized;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static me.gleep.oreganized.world.gen.OreganizedConfiguredFeatures.*;

public class OreganizedPlacedFeatures{
    public static Holder<PlacedFeature> ORE_SILVER_DEEPSLATE_UP = register("silver_ore_deepslate_up", ORE_SILVER_DEEP_UNDERGROUND, orePlacement(1, HeightRangePlacement.uniform(VerticalAnchor.absolute(-5), VerticalAnchor.absolute(5))));
    public static Holder<PlacedFeature> ORE_SILVER_DEEPSLATE_DOWN = register("silver_ore_deepslate_down", ORE_SILVER_DEEP_UNDERGROUND, orePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-15), VerticalAnchor.absolute(-5)))); 
    public static Holder<PlacedFeature> ORE_SILVER_UP = register("silver_ore_up", ORE_SILVER, orePlacement(1, HeightRangePlacement.uniform(VerticalAnchor.absolute(160), VerticalAnchor.absolute(180))));
    public static Holder<PlacedFeature> ORE_SILVER_DOWN = register("silver_ore_down", ORE_SILVER, orePlacement(1, HeightRangePlacement.uniform(VerticalAnchor.absolute(140), VerticalAnchor.absolute(160))));
    public static Holder<PlacedFeature> ORE_LEAD_DEEPSLATE_UP = register("lead_ore_deepslate_up", ORE_LEAD_DEEP_UNDERGROUND, orePlacement(1, HeightRangePlacement.triangle(VerticalAnchor.absolute(-33), VerticalAnchor.absolute(-20))));
    public static Holder<PlacedFeature> ORE_LEAD_DEEPSLATE_DOWN = register("lead_ore_deepslate_down", ORE_LEAD_DEEP_UNDERGROUND, orePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(-33))));
    public static Holder<PlacedFeature> ORE_LEAD_SAVANNA = register("lead_ore_savanna", ORE_LEAD, orePlacement(13, HeightRangePlacement.uniform(VerticalAnchor.absolute(50), VerticalAnchor.absolute(80))));

    private static List<PlacementModifier> orePlacement(int count, PlacementModifier modifier) {
        return List.of(CountPlacement.of(count), InSquarePlacement.spread(), modifier, BiomeFilter.biome());
    }

    private static Holder<PlacedFeature> register(String key, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
        return PlacementUtils.register(new ResourceLocation(Oreganized.MOD_ID, key).toString(), feature, modifiers);
    }
}


