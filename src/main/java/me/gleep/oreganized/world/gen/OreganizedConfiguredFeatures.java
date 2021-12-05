package me.gleep.oreganized.world.gen;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.List;

public class OreganizedConfiguredFeatures{

    public static List <OreConfiguration.TargetBlockState> ORE_SILVER_TARGET_LIST;
    public static List <OreConfiguration.TargetBlockState> ORE_LEAD_TARGET_LIST;


    public static ConfiguredFeature <?, ?> ORE_SILVER;
    public static ConfiguredFeature <?, ?> ORE_LEAD;

    public static void registerConfiguredFeatures(){
        ORE_SILVER_TARGET_LIST = List.of( OreConfiguration.target( OreFeatures.STONE_ORE_REPLACEABLES , RegistryHandler.SILVER_ORE.get().defaultBlockState() ) ,
                OreConfiguration.target( OreFeatures.DEEPSLATE_ORE_REPLACEABLES , RegistryHandler.DEEPSLATE_SILVER_ORE.get().defaultBlockState() ) );
        ORE_LEAD_TARGET_LIST = List.of( OreConfiguration.target( OreFeatures.STONE_ORE_REPLACEABLES , RegistryHandler.LEAD_ORE.get().defaultBlockState() ) ,
                OreConfiguration.target( OreFeatures.DEEPSLATE_ORE_REPLACEABLES , RegistryHandler.DEEPSLATE_LEAD_ORE.get().defaultBlockState() ) );

        ORE_SILVER = Feature.ORE.configured( new OreConfiguration( ORE_SILVER_TARGET_LIST , 9 ) );
        ORE_LEAD = Feature.ORE.configured( new OreConfiguration( ORE_LEAD_TARGET_LIST , 16 ) );

        Registry.register( BuiltinRegistries.CONFIGURED_FEATURE , new ResourceLocation( Oreganized.MOD_ID , "silver_ore" ) , ORE_SILVER );
        Registry.register( BuiltinRegistries.CONFIGURED_FEATURE , new ResourceLocation( Oreganized.MOD_ID , "lead_ore" ) , ORE_LEAD );
    }
}
