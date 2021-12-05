package me.gleep.oreganized.world.gen;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static me.gleep.oreganized.world.gen.OreganizedConfiguredFeatures.ORE_LEAD;
import static me.gleep.oreganized.world.gen.OreganizedConfiguredFeatures.ORE_SILVER;
import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;

@Mod.EventBusSubscriber
public class OreganizedPlacedFeatures{
    public static PlacedFeature ORE_SILVER_OVERWORLD;
    public static PlacedFeature ORE_LEAD_OVERWORLD;

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
        ORE_SILVER_OVERWORLD = ORE_SILVER.placed( List.of( CountPlacement.of( 16 ) , InSquarePlacement.spread() ,
                HeightRangePlacement.uniform( VerticalAnchor.absolute( 32 ), VerticalAnchor.absolute( 256 ) ) ,
                BiomeFilter.biome() ) );

        ORE_LEAD_OVERWORLD = ORE_LEAD.placed( List.of( CountPlacement.of( 4 ) , InSquarePlacement.spread() ,
                HeightRangePlacement.uniform( VerticalAnchor.absolute( 32 ), VerticalAnchor.absolute( 256 ) ) ,
                BiomeFilter.biome() ) );

        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Oreganized.MOD_ID, "silver_ore"), ORE_SILVER_OVERWORLD);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Oreganized.MOD_ID, "lead_ore"), ORE_LEAD_OVERWORLD);
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
        }else{
            generation.getFeatures( GenerationStep.Decoration.UNDERGROUND_ORES ).add( () -> ORE_SILVER_OVERWORLD );
            generation.getFeatures( GenerationStep.Decoration.UNDERGROUND_ORES ).add( () -> ORE_LEAD_OVERWORLD );
        }
    }
}

