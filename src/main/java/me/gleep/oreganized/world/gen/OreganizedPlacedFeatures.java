package me.gleep.oreganized.world.gen;

import me.gleep.oreganized.Oreganized;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static me.gleep.oreganized.world.gen.OreganizedConfiguredFeatures.*;

@Mod.EventBusSubscriber
public class OreganizedPlacedFeatures{
    public static PlacedFeature ORE_SILVER_DEEPSLATE_UP;
    public static PlacedFeature ORE_SILVER_DEEPSLATE_DOWN;
    public static PlacedFeature ORE_SILVER_UP;
    public static PlacedFeature ORE_SILVER_DOWN;
    public static PlacedFeature ORE_LEAD_DEEPSLATE_UP;
    public static PlacedFeature ORE_LEAD_DEEPSLATE_DOWN;
    public static PlacedFeature ORE_LEAD_SAVANNA;

    private static final ArrayList <PlacedFeature> overworldOres = new ArrayList <>();
    //private static final ArrayList<ConfiguredFeature<?, ?>> netherOres = new ArrayList<ConfiguredFeature<?, ?>>();
    //private static final ArrayList<ConfiguredFeature<?, ?>> endOres = new ArrayList<ConfiguredFeature<?, ?>>();

    public static void registerPlacedFeatures(){
        //BASE_STONE_OVERWORLD is for generating in stone, granite, diorite, and andesite
        //NETHERRACK is for generating in netherrack
        //BASE_STONE_NETHER is for generating in netherrack, basalt and blackstone

        //Overworld Ore Register
        /*overworldOres.add(register("silver_ore", Feature.ORE.configured(new OreConfiguration(
                ORE_SILVER_TARGET_LIST, RegistryHandler.SILVER_ORE.get().defaultBlockState(), 3)) //Vein Size
                .range(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(30), VerticalAnchor.absolute(50)))).config())
                .squared()
                .rarity(4))
        );*/

        /*overworldOres.add(register("lead_ore", Feature.ORE.configured(new OreConfiguration(
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
        ORE_SILVER_DEEPSLATE_UP = ORE_SILVER_DEEP_UNDERGROUND.placed( List.of( CountPlacement.of( 2 ) , InSquarePlacement.spread() ,
                HeightRangePlacement.uniform( VerticalAnchor.absolute( -5 ) , VerticalAnchor.absolute( 5 ) ) ,
                BiomeFilter.biome() ) );

        ORE_SILVER_DEEPSLATE_DOWN = ORE_SILVER_DEEP_UNDERGROUND.placed( List.of( CountPlacement.of( 2 ) , InSquarePlacement.spread() ,
                HeightRangePlacement.uniform( VerticalAnchor.absolute( -15 ) , VerticalAnchor.absolute( -5 ) ) ,
                BiomeFilter.biome() ) );

        ORE_SILVER_UP = ORE_SILVER.placed( List.of( CountPlacement.of( 1 ) , InSquarePlacement.spread() ,
                HeightRangePlacement.uniform( VerticalAnchor.absolute( 160 ) , VerticalAnchor.absolute( 180 ) ) ,
                BiomeFilter.biome() ) );

        ORE_SILVER_DOWN = ORE_SILVER.placed( List.of( CountPlacement.of( 1 ) , InSquarePlacement.spread() ,
                HeightRangePlacement.uniform( VerticalAnchor.absolute( 140 ) , VerticalAnchor.absolute( 160 ) ) ,
                BiomeFilter.biome() ) );

        ORE_LEAD_DEEPSLATE_UP = ORE_LEAD_DEEP_UNDERGROUND.placed( List.of( CountPlacement.of( 1 ) , InSquarePlacement.spread() ,
                HeightRangePlacement.triangle( VerticalAnchor.absolute( -33 ) , VerticalAnchor.absolute( -20 ) ) ,
                BiomeFilter.biome() ) );

        ORE_LEAD_DEEPSLATE_DOWN = ORE_LEAD_DEEP_UNDERGROUND.placed( List.of( CountPlacement.of( 2 ) , InSquarePlacement.spread() ,
                HeightRangePlacement.triangle( VerticalAnchor.absolute( -40 ) , VerticalAnchor.absolute( -33 ) ) ,
                BiomeFilter.biome() ) );

        ORE_LEAD_SAVANNA = ORE_LEAD.placed( List.of( CountPlacement.of( 13 ) , InSquarePlacement.spread() ,
                HeightRangePlacement.uniform( VerticalAnchor.absolute( 50 ) , VerticalAnchor.absolute( 80 ) ) ,
                BiomeFilter.biome() ) );

        Registry.register( BuiltinRegistries.PLACED_FEATURE , new ResourceLocation( Oreganized.MOD_ID , "silver_ore_deepslate_up" ) , ORE_SILVER_DEEPSLATE_UP );
        Registry.register( BuiltinRegistries.PLACED_FEATURE , new ResourceLocation( Oreganized.MOD_ID , "silver_ore_deepslate_down" ) , ORE_SILVER_DEEPSLATE_DOWN );
        Registry.register( BuiltinRegistries.PLACED_FEATURE , new ResourceLocation( Oreganized.MOD_ID , "silver_ore_up" ) , ORE_SILVER_UP );
        Registry.register( BuiltinRegistries.PLACED_FEATURE , new ResourceLocation( Oreganized.MOD_ID , "silver_ore_down" ) , ORE_SILVER_DOWN );
        Registry.register( BuiltinRegistries.PLACED_FEATURE , new ResourceLocation( Oreganized.MOD_ID , "lead_ore_deepslate_up" ) , ORE_LEAD_DEEPSLATE_UP );
        Registry.register( BuiltinRegistries.PLACED_FEATURE , new ResourceLocation( Oreganized.MOD_ID , "lead_ore_deepslate_down" ) , ORE_LEAD_DEEPSLATE_DOWN );
        Registry.register( BuiltinRegistries.PLACED_FEATURE , new ResourceLocation( Oreganized.MOD_ID , "lead_ore_savanna" ) , ORE_LEAD_SAVANNA );
    }

    @SubscribeEvent
    public static void registerBiomeModification( final BiomeLoadingEvent event ){
        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        if(event.getCategory().equals( Biome.BiomeCategory.NETHER )){
            /*for(ConfiguredFeature<?, ?> ore : netherOres) {
                if (ore != null) generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            }*/

        }else if(event.getCategory().equals( Biome.BiomeCategory.THEEND )){
            /*for(ConfiguredFeature<?, ?> ore : endOres) {
                if (ore != null) generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            }*/
        }else if(event.getCategory().equals( Biome.BiomeCategory.SAVANNA )){
            generation.getFeatures( GenerationStep.Decoration.UNDERGROUND_ORES ).add( () -> ORE_LEAD_SAVANNA );
        }else{
            //generation.getFeatures( GenerationStep.Decoration.UNDERGROUND_ORES ).add( () -> ORE_SILVER_OVERWORLD );
            generation.getFeatures( GenerationStep.Decoration.UNDERGROUND_ORES ).add( () -> ORE_SILVER_DEEPSLATE_UP );
            generation.getFeatures( GenerationStep.Decoration.UNDERGROUND_ORES ).add( () -> ORE_SILVER_DEEPSLATE_DOWN );
            generation.getFeatures( GenerationStep.Decoration.UNDERGROUND_ORES ).add( () -> ORE_SILVER_UP );
            generation.getFeatures( GenerationStep.Decoration.UNDERGROUND_ORES ).add( () -> ORE_SILVER_DOWN );
            generation.getFeatures( GenerationStep.Decoration.UNDERGROUND_ORES ).add( () -> ORE_LEAD_DEEPSLATE_UP );
            generation.getFeatures( GenerationStep.Decoration.UNDERGROUND_ORES ).add( () -> ORE_LEAD_DEEPSLATE_DOWN );
        }
    }
}


