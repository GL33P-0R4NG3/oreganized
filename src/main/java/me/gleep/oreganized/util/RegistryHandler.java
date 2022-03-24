package me.gleep.oreganized.util;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.blocks.*;
import me.gleep.oreganized.entities.PrimedShrapnelBomb;
import me.gleep.oreganized.entities.tileentities.ExposerBlockEntity;
import me.gleep.oreganized.items.*;
import me.gleep.oreganized.world.gen.BoulderStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static me.gleep.oreganized.Oreganized.MOD_ID;

public class RegistryHandler {
    //Mod
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);
    //private static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, OreExpansion.MOD_ID);

    public static void init() {
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        STRUCTURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        PARTICLES.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCK_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    /*//////////////////////////////////            FLUIDS            //////////////////////////////////*/
    //MOLTEN LEAD
    /*public static final RegistryObject<ForgeFlowingFluid> LEAD_FLUID = FLUIDS.register("lead_fluid", LeadFluid.Source::new);
    public static final RegistryObject<ForgeFlowingFluid> LEAD_FLUID_FLOW = FLUIDS.register("lead_fluid_flow", LeadFluid.Flowing::new);
    public static final RegistryObject<LiquidBlock> LEAD_FLUID_BLOCK = BLOCKS.register("lead_fluid_block",
            () -> new LeadFluidBlock(RegistryHandler.LEAD_FLUID)
    );*/


    /*//////////////////////////////////            PARTICLES            //////////////////////////////////*/
    //public static final RegistryObject<ParticleType<SimpleParticleType>> DAWN_SHINE_PARTICLE = PARTICLES.register("dawn_shine", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DRIPPING_LEAD = PARTICLES.register("dripping_lead", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FALLING_LEAD = PARTICLES.register("falling_lead", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LANDING_LEAD = PARTICLES.register("landing_lead", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LEAD_SHRAPNEL = PARTICLES.register( "lead_shrapnel", () -> new SimpleParticleType(true));


    /*//////////////////////////////////            SOUND EVENTS            //////////////////////////////////*/
    public static final RegistryObject<SoundEvent> MUSIC_DISC_PILLAGED = SOUND_EVENTS.register("music_disc.pillaged", () -> new SoundEvent(new ResourceLocation(MOD_ID, "music_disc.pillaged")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_18 = SOUND_EVENTS.register("music_disc.18", () -> new SoundEvent(new ResourceLocation(MOD_ID, "music_disc.18")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_SHULK = SOUND_EVENTS.register("music_disc.shulk", () -> new SoundEvent(new ResourceLocation(MOD_ID, "music_disc.shulk")));


    /*//////////////////////////////////            EFFECTS            //////////////////////////////////*/
    //public static final RegistryObject<MobEffect> HEAVY_METAL_POISONING = EFFECTS.register("heavy_metal_poisoning", HeavyMetalPoisoning::new);
    //public static final RegistryObject<MobEffect> DAWN_SHINE = EFFECTS.register("dawn_shine", DawnShine::new);
    //public static final RegistryObject<MobEffect> NON_DAWN_SHINE = EFFECTS.register("non_dawn_shine", NonDawnShine::new);


    /*//////////////////////////////////            ENTITIES            //////////////////////////////////*/
    public static final RegistryObject<EntityType<PrimedShrapnelBomb>> SHRAPNEL_BOMB_ENTITY = ENTITIES.register("shrapnel_bomb", () ->
            EntityType.Builder.<PrimedShrapnelBomb>of(
                    PrimedShrapnelBomb::new, MobCategory.MISC
            ).fireImmune().sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(10).build("shrapnel_bomb")
    );

    /*public static final RegistryObject<EntityType<LeadNuggetEntity>> LEAD_NUGGET_ENTITY = ENTITIES.register("lead_nugget", () ->
            EntityType.Builder.<LeadNuggetEntity>of(
                    LeadNuggetEntity::new, MobCategory.MISC
            ).fireImmune().sized(1.0F, 1.0F).clientTrackingRange(4).updateInterval(20).build("lead_nugget")
    );*/


    /*//////////////////////////////////            STRUCTURES            //////////////////////////////////*/
    public static final RegistryObject<StructureFeature<?>> ROCKS = STRUCTURES.register("rocks", BoulderStructures::new);


    /*//////////////////////////////////            ITEMS            //////////////////////////////////*/
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", SilverIngot::new);
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", ItemBase::new);
    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", ItemBase::new);
    public static final RegistryObject<Item> RAW_LEAD = ITEMS.register("raw_lead", ItemBase::new);
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", ItemBase::new);
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", ItemBase::new);
    public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget", () -> new ItemBase(true));
    public static final RegistryObject<Item> MOLTEN_LEAD_BUCKET = ITEMS.register("molten_lead_bucket", () -> new SolidBucketItem(RegistryHandler.MOLTEN_LEAD_BLOCK.get(),
            SoundEvents.BUCKET_EMPTY_LAVA, new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).stacksTo(1))
    );
    public static final RegistryObject<Item> SILVER_MIRROR = ITEMS.register("silver_mirror", SilverMirror::new);
    //Music Discs
    public static final RegistryObject<Item> MUSIC_DISC_PILLAGED_ITEM = ITEMS.register("music_disc_pillaged", () -> new ModMusicDisc(13, MUSIC_DISC_PILLAGED));
    public static final RegistryObject<Item> MUSIC_DISC_18_ITEM = ITEMS.register("music_disc_18", () -> new ModMusicDisc(14, MUSIC_DISC_18));
    public static final RegistryObject<Item> MUSIC_DISC_SHULK_ITEM = ITEMS.register("music_disc_shulk", () -> new ModMusicDisc(15, MUSIC_DISC_SHULK));

    public static final RegistryObject<Item> ELECTURM_HELMET = ITEMS.register("electrum_helmet", () -> new ElectrumArmorItem(EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> ELECTURM_CHESTPLATE = ITEMS.register("electrum_chestplate", () -> new ElectrumArmorItem(EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> ELECTURM_LEGGINGS = ITEMS.register("electrum_leggings", () -> new ElectrumArmorItem(EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> ELECTURM_BOOTS = ITEMS.register("electrum_boots", () -> new ElectrumArmorItem(EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    /*//////////////////////////////////            BLOCKS            //////////////////////////////////*/
    //Ores
    public static final RegistryObject<Block> SILVER_ORE = BLOCKS.register("silver_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE)));
    public static final RegistryObject<Block> LEAD_ORE = BLOCKS.register("lead_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.METAL)
            .strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.STONE), UniformInt.of(0, 3))
    );
    public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE = BLOCKS.register("deepslate_silver_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_LEAD_ORE = BLOCKS.register("deepslate_lead_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE)));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = BLOCKS.register("raw_silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
    public static final RegistryObject<Block> RAW_LEAD_BLOCK = BLOCKS.register("raw_lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
    //Blocks
    public static final RegistryObject<Block> SILVER_BLOCK = BLOCKS.register("silver_block", SilverBlock::new);
    public static final RegistryObject<Block> LEAD_BLOCK = BLOCKS.register("lead_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL))
    );
    public static final RegistryObject<Block> GLANCE = BLOCKS.register("glance", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIORITE)));
    public static final RegistryObject<SlabBlock> GLANCE_SLAB = BLOCKS.register("glance_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(GLANCE.get())));
    public static final RegistryObject<SlabBlock> GLANCE_BRICKS_SLAB = BLOCKS.register("glance_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(GLANCE.get())));
    public static final RegistryObject<StairBlock> GLANCE_STAIRS = BLOCKS.register("glance_stairs", () -> new StairBlock(GLANCE.get()::defaultBlockState,
            BlockBehaviour.Properties.copy(GLANCE.get())));
    public static final RegistryObject<StairBlock> GLANCE_BRICKS_STAIRS = BLOCKS.register("glance_bricks_stairs", () -> new StairBlock(GLANCE.get()::defaultBlockState,
            BlockBehaviour.Properties.copy(GLANCE.get())));
    public static final RegistryObject<WallBlock> GLANCE_WALL = BLOCKS.register("glance_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(GLANCE.get())));
    public static final RegistryObject<WallBlock> GLANCE_BRICKS_WALL = BLOCKS.register("glance_bricks_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(GLANCE.get())));
    public static final RegistryObject<Block> POLISHED_GLANCE = BLOCKS.register("polished_glance", () -> new Block(BlockBehaviour.Properties.copy(GLANCE.get())));
    public static final RegistryObject<Block> GLANCE_BRICKS = BLOCKS.register("glance_bricks", () -> new Block(BlockBehaviour.Properties.copy(GLANCE.get())));
    public static final RegistryObject<Block> CHISELED_GLANCE = BLOCKS.register("chiseled_glance", () -> new Block(BlockBehaviour.Properties.copy(GLANCE.get())));
    public static final RegistryObject<Block> SPOTTED_GLANCE = BLOCKS.register("spotted_glance", SpottedGlance::new);
    public static final RegistryObject<Block> WAXED_SPOTTED_GLANCE = BLOCKS.register("waxed_spotted_glance", () -> new Block(BlockBehaviour.Properties.copy(SPOTTED_GLANCE.get())));
    public static final RegistryObject<Block> MOLTEN_LEAD_BLOCK = BLOCKS.register("molten_lead_block", MoltenLeadBlock::new);
    //public static final RegistryObject<Block> LEAD_COATING = BLOCKS.register("lead_coating", LeadCoating::new);
    //public static final RegistryObject<Block> CUT_LEAD_COATING = BLOCKS.register("cut_lead_coating", CutLeadCoating::new);
    public static final RegistryObject<Block> LEAD_CAULDRON = BLOCKS.register("cauldron", ModCauldron::new);
    //public static final RegistryObject<Block> LIGHTENED_IRON_BLOCK = BLOCKS.register("lightened_iron_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
    //        .strength(4.0F, 5.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
    //);
    //public static final RegistryObject<Block> CAST_IRON_BLOCK = BLOCKS.register("cast_iron_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
    //        .strength(5.0F, 4.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK))
    //);
    //public static final RegistryObject<Block> CUT_CAST_IRON_BLOCK = BLOCKS.register("cut_cast_iron_block", () -> new Block(BlockBehaviour.Properties.copy(CAST_IRON_BLOCK.get())));
    //public static final RegistryObject<Block> BLASTED_IRON_BLOCK = BLOCKS.register("blasted_iron_block", BlastedIronBlock::new);
    //public static final RegistryObject<Block> CUT_BLASTED_IRON_BLOCK = BLOCKS.register("cut_blasted_iron_block", () -> new Block(BlockBehaviour.Properties.copy(BLASTED_IRON_BLOCK.get())));
    //public static final RegistryObject<Block> TECHNICAL_NETHERITE_BLOCK = BLOCKS.register("technical_netherite_block", TechnicalNetheriteBlock::new);
    //public static final RegistryObject<Block> CUT_TECHNICAL_NETHERITE_BLOCK = BLOCKS.register("cut_technical_netherite_block", TechnicalNetheriteBlock::new);

    //Engraved Bricks
    public static final RegistryObject<EngravedBlock> ENGRAVED_NETHER_BRICKS = BLOCKS.register("engraved_nether_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS).requiresCorrectToolForDrops()
                    .strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS)));

    public static final RegistryObject<EngravedBlock> ENGRAVED_RED_NETHER_BRICKS = BLOCKS.register("engraved_red_nether_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS).requiresCorrectToolForDrops()
                    .strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS)));

    public static final RegistryObject<EngravedBlock> ENGRAVED_POLISHED_BLACKSTONE_BRICKS = BLOCKS.register("engraved_polished_blackstone_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final RegistryObject<EngravedBlock> ENGRAVED_BRICKS = BLOCKS.register("engraved_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)));

    public static final RegistryObject<CopperEngravedBlock> ENGRAVED_WAXED_OXIDIZED_CUT_COPPER = BLOCKS.register("engraved_waxed_oxidized_cut_copper",
            () -> new CopperEngravedBlock(BlockBehaviour.Properties.copy(Blocks.OXIDIZED_CUT_COPPER)));

    public static final RegistryObject<CopperEngravedBlock> ENGRAVED_WAXED_WEATHERED_CUT_COPPER = BLOCKS.register("engraved_waxed_weathered_cut_copper",
            () -> new CopperEngravedBlock(BlockBehaviour.Properties.copy(Blocks.WEATHERED_CUT_COPPER)));

    public static final RegistryObject<CopperEngravedBlock> ENGRAVED_WAXED_EXPOSED_CUT_COPPER = BLOCKS.register("engraved_waxed_exposed_cut_copper",
            () -> new CopperEngravedBlock(BlockBehaviour.Properties.copy(Blocks.EXPOSED_CUT_COPPER)));

    public static final RegistryObject<CopperEngravedBlock> ENGRAVED_WAXED_CUT_COPPER = BLOCKS.register("engraved_waxed_cut_copper",
            () -> new CopperEngravedBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));

    public static final RegistryObject<EngravedWeatheringCopperBlock> ENGRAVED_CUT_COPPER = BLOCKS.register("engraved_cut_copper",
            () -> new EngravedWeatheringCopperBlock(CustomWeatheringCopper.WeatherState.UNAFFECTED, BlockBehaviour.Properties.copy(Blocks.CUT_COPPER), ENGRAVED_WAXED_CUT_COPPER.get()));

    public static final RegistryObject<EngravedWeatheringCopperBlock> ENGRAVED_EXPOSED_CUT_COPPER = BLOCKS.register("engraved_exposed_cut_copper",
            () -> new EngravedWeatheringCopperBlock(CustomWeatheringCopper.WeatherState.EXPOSED, BlockBehaviour.Properties.copy(Blocks.EXPOSED_CUT_COPPER), ENGRAVED_WAXED_EXPOSED_CUT_COPPER.get()));

    public static final RegistryObject<EngravedWeatheringCopperBlock> ENGRAVED_WEATHERED_CUT_COPPER = BLOCKS.register("engraved_weathered_cut_copper",
            () -> new EngravedWeatheringCopperBlock(CustomWeatheringCopper.WeatherState.WEATHERED, BlockBehaviour.Properties.copy(Blocks.WEATHERED_CUT_COPPER), ENGRAVED_WAXED_WEATHERED_CUT_COPPER.get()));

    public static final RegistryObject<EngravedWeatheringCopperBlock> ENGRAVED_OXIDIZED_CUT_COPPER = BLOCKS.register("engraved_oxidized_cut_copper",
            () -> new EngravedWeatheringCopperBlock(CustomWeatheringCopper.WeatherState.OXIDIZED, BlockBehaviour.Properties.copy(Blocks.OXIDIZED_CUT_COPPER), ENGRAVED_WAXED_OXIDIZED_CUT_COPPER.get()));

    public static final RegistryObject<EngravedBlock> ENGRAVED_DEEPSLATE_BRICKS = BLOCKS.register("engraved_deepslate_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICKS)));

    public static final RegistryObject<EngravedBlock> ENGRAVED_END_STONE_BRICKS = BLOCKS.register("engraved_end_stone_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS)));

    public static final RegistryObject<EngravedBlock> ENGRAVED_PRISMARINE_BRICKS = BLOCKS.register("engraved_prismarine_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICKS)));

    public static final RegistryObject<EngravedBlock> ENGRAVED_QUARTZ_BRICKS = BLOCKS.register("engraved_quartz_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS)));

    public static final RegistryObject<EngravedBlock> ENGRAVED_STONE_BRICKS = BLOCKS.register("engraved_stone_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    public static final RegistryObject<EngravedBlock> ENGRAVED_GLANCE_BRICKS = BLOCKS.register("engraved_glance_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(GLANCE_BRICKS.get())));

    //public static final RegistryObject<Block> STONE = BLOCKS.register("stone", () -> new StoneSign(BlockBehaviour.Properties.copy(Blocks.STONE)));
    //public static final RegistryObject<Block> STONE_BRICKS = BLOCKS.register("stone_bricks", () -> new StoneSign(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    //public static final RegistryObject<Block> POLISHED_BLACKSTONE_BRICKS = BLOCKS.register("polished_blackstone_bricks", () -> new StoneSign(AbstractBlock.Properties.from(Blocks.POLISHED_BLACKSTONE_BRICKS)));
    //public static final RegistryObject<Block> NETHER_BRICKS = BLOCKS.register("nether_bricks", () -> new StoneSign(AbstractBlock.Properties.from(Blocks.NETHER_BRICKS)));
    //Stairs
    //public static final RegistryObject<Block> LIGHTENED_IRON_STAIRS = BLOCKS.register("lightened_iron_stairs", () -> new StairBlock(LIGHTENED_IRON_BLOCK.get()::defaultBlockState, BlockBehaviour.Properties.copy(LIGHTENED_IRON_BLOCK.get())));
    //public static final RegistryObject<Block> CUT_CAST_IRON_STAIRS = BLOCKS.register("cut_cast_iron_stairs", () -> new StairBlock(CAST_IRON_BLOCK.get()::defaultBlockState, BlockBehaviour.Properties.copy(CAST_IRON_BLOCK.get())));
    //public static final RegistryObject<Block> BLASTED_IRON_STAIRS = BLOCKS.register("blasted_iron_stairs", () -> new StairBlock(BLASTED_IRON_BLOCK.get()::defaultBlockState, BlockBehaviour.Properties.copy(BLASTED_IRON_BLOCK.get())));
    //public static final RegistryObject<Block> CUT_BLASTED_IRON_STAIRS = BLOCKS.register("cut_blasted_iron_stairs", () -> new StairBlock(CUT_BLASTED_IRON_BLOCK.get()::defaultBlockState, BlockBehaviour.Properties.copy(CUT_BLASTED_IRON_BLOCK.get())));
    //public static final RegistryObject<Block> TECHNICAL_NETHERITE_STAIRS = BLOCKS.register("technical_netherite_stairs", () -> new StairBlock(TECHNICAL_NETHERITE_BLOCK.get()::defaultBlockState, BlockBehaviour.Properties.copy(TECHNICAL_NETHERITE_BLOCK.get())));
    //public static final RegistryObject<Block> CUT_TECHNICAL_NETHERITE_STAIRS = BLOCKS.register("cut_technical_netherite_stairs", () -> new StairBlock(CUT_TECHNICAL_NETHERITE_BLOCK.get()::defaultBlockState, BlockBehaviour.Properties.copy(CUT_TECHNICAL_NETHERITE_BLOCK.get())));
    //Slabs
    //public static final RegistryObject<Block> LIGHTENED_IRON_SLAB = BLOCKS.register("lightened_iron_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(LIGHTENED_IRON_BLOCK.get())));
    //public static final RegistryObject<Block> CUT_CAST_IRON_SLAB = BLOCKS.register("cut_cast_iron_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CAST_IRON_BLOCK.get())));
    //public static final RegistryObject<Block> BLASTED_IRON_SLAB = BLOCKS.register("blasted_iron_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(BLASTED_IRON_BLOCK.get())));
    //public static final RegistryObject<Block> CUT_BLASTED_IRON_SLAB = BLOCKS.register("cut_blasted_iron_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CUT_BLASTED_IRON_BLOCK.get())));
    //public static final RegistryObject<Block> TECHNICAL_NETHERITE_SLAB = BLOCKS.register("technical_netherite_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(TECHNICAL_NETHERITE_BLOCK.get())));
    //public static final RegistryObject<Block> CUT_TECHNICAL_NETHERITE_SLAB = BLOCKS.register("cut_technical_netherite_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CUT_TECHNICAL_NETHERITE_BLOCK.get())));
    //Glass
    public static final RegistryObject<Block> BLACK_CRYSTAL_GLASS = BLOCKS.register("black_crystal_glass", () -> new CrystalGlassColored(DyeColor.BLACK));
    public static final RegistryObject<Block> BLUE_CRYSTAL_GLASS = BLOCKS.register("blue_crystal_glass", () -> new CrystalGlassColored(DyeColor.BLUE));
    public static final RegistryObject<Block> BROWN_CRYSTAL_GLASS = BLOCKS.register("brown_crystal_glass", () -> new CrystalGlassColored(DyeColor.BROWN));
    //public static final RegistryObject<Block> CRYSTAL_GLASS = BLOCKS.register("crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> CYAN_CRYSTAL_GLASS = BLOCKS.register("cyan_crystal_glass", () -> new CrystalGlassColored(DyeColor.CYAN));
    public static final RegistryObject<Block> GRAY_CRYSTAL_GLASS = BLOCKS.register("gray_crystal_glass", () -> new CrystalGlassColored(DyeColor.GRAY));
    public static final RegistryObject<Block> GREEN_CRYSTAL_GLASS = BLOCKS.register("green_crystal_glass", () -> new CrystalGlassColored(DyeColor.GREEN));
    public static final RegistryObject<Block> LIGHT_BLUE_CRYSTAL_GLASS = BLOCKS.register("light_blue_crystal_glass", () -> new CrystalGlassColored(DyeColor.LIGHT_BLUE));
    public static final RegistryObject<Block> LIGHT_GRAY_CRYSTAL_GLASS = BLOCKS.register("light_gray_crystal_glass", () -> new CrystalGlassColored(DyeColor.LIGHT_GRAY));
    public static final RegistryObject<Block> LIME_CRYSTAL_GLASS = BLOCKS.register("lime_crystal_glass", () -> new CrystalGlassColored(DyeColor.LIME));
    public static final RegistryObject<Block> MAGENTA_CRYSTAL_GLASS = BLOCKS.register("magenta_crystal_glass", () -> new CrystalGlassColored(DyeColor.MAGENTA));
    public static final RegistryObject<Block> ORANGE_CRYSTAL_GLASS = BLOCKS.register("orange_crystal_glass", () -> new CrystalGlassColored(DyeColor.ORANGE));
    public static final RegistryObject<Block> PINK_CRYSTAL_GLASS = BLOCKS.register("pink_crystal_glass", () -> new CrystalGlassColored(DyeColor.PINK));
    public static final RegistryObject<Block> PURPLE_CRYSTAL_GLASS = BLOCKS.register("purple_crystal_glass", () -> new CrystalGlassColored(DyeColor.PURPLE));
    public static final RegistryObject<Block> RED_CRYSTAL_GLASS = BLOCKS.register("red_crystal_glass", () -> new CrystalGlassColored(DyeColor.RED));
    public static final RegistryObject<Block> WHITE_CRYSTAL_GLASS = BLOCKS.register("white_crystal_glass", () -> new CrystalGlassColored(DyeColor.WHITE));
    public static final RegistryObject<Block> YELLOW_CRYSTAL_GLASS = BLOCKS.register("yellow_crystal_glass", () -> new CrystalGlassColored(DyeColor.YELLOW));
    //Glass panes
    public static final RegistryObject<Block> BLACK_CRYSTAL_GLASS_PANE = BLOCKS.register("black_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.BLACK));
    public static final RegistryObject<Block> BLUE_CRYSTAL_GLASS_PANE = BLOCKS.register("blue_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.BLUE));
    public static final RegistryObject<Block> BROWN_CRYSTAL_GLASS_PANE = BLOCKS.register("brown_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.BROWN));
    //public static final RegistryObject<Block> CRYSTAL_GLASS_PANE = BLOCKS.register("crystal_glass_pane", CrystalGlassPaneBase::new);
    public static final RegistryObject<Block> CYAN_CRYSTAL_GLASS_PANE = BLOCKS.register("cyan_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.CYAN));
    public static final RegistryObject<Block> GRAY_CRYSTAL_GLASS_PANE = BLOCKS.register("gray_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.GRAY));
    public static final RegistryObject<Block> GREEN_CRYSTAL_GLASS_PANE = BLOCKS.register("green_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.GREEN));
    public static final RegistryObject<Block> LIGHT_BLUE_CRYSTAL_GLASS_PANE = BLOCKS.register("light_blue_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.LIGHT_BLUE));
    public static final RegistryObject<Block> LIGHT_GRAY_CRYSTAL_GLASS_PANE = BLOCKS.register("light_gray_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.LIGHT_GRAY));
    public static final RegistryObject<Block> LIME_CRYSTAL_GLASS_PANE = BLOCKS.register("lime_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.LIME));
    public static final RegistryObject<Block> MAGENTA_CRYSTAL_GLASS_PANE = BLOCKS.register("magenta_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.MAGENTA));
    public static final RegistryObject<Block> ORANGE_CRYSTAL_GLASS_PANE = BLOCKS.register("orange_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.ORANGE));
    public static final RegistryObject<Block> PINK_CRYSTAL_GLASS_PANE = BLOCKS.register("pink_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.PINK));
    public static final RegistryObject<Block> PURPLE_CRYSTAL_GLASS_PANE = BLOCKS.register("purple_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.PURPLE));
    public static final RegistryObject<Block> RED_CRYSTAL_GLASS_PANE = BLOCKS.register("red_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.RED));
    public static final RegistryObject<Block> WHITE_CRYSTAL_GLASS_PANE = BLOCKS.register("white_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.WHITE));
    public static final RegistryObject<Block> YELLOW_CRYSTAL_GLASS_PANE = BLOCKS.register("yellow_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.YELLOW));
    //Waxed Concrete Powder
    public static final RegistryObject<FallingBlock> WAXED_WHITE_CONCRETE_POWDER = BLOCKS.register("waxed_white_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_ORANGE_CONCRETE_POWDER = BLOCKS.register("waxed_orange_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.ORANGE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_MAGENTA_CONCRETE_POWDER = BLOCKS.register("waxed_magenta_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.MAGENTA).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_LIGHT_BLUE_CONCRETE_POWDER = BLOCKS.register("waxed_light_blue_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.LIGHT_BLUE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_YELLOW_CONCRETE_POWDER = BLOCKS.register("waxed_yellow_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.YELLOW).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_LIME_CONCRETE_POWDER = BLOCKS.register("waxed_lime_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.LIME).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_PINK_CONCRETE_POWDER = BLOCKS.register("waxed_pink_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.PINK).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_GRAY_CONCRETE_POWDER = BLOCKS.register("waxed_gray_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.GRAY).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_LIGHT_GRAY_CONCRETE_POWDER = BLOCKS.register("waxed_light_gray_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.LIGHT_GRAY).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_CYAN_CONCRETE_POWDER = BLOCKS.register("waxed_cyan_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.CYAN).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_PURPLE_CONCRETE_POWDER = BLOCKS.register("waxed_purple_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.PURPLE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_BLUE_CONCRETE_POWDER = BLOCKS.register("waxed_blue_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.BLUE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_BROWN_CONCRETE_POWDER = BLOCKS.register("waxed_brown_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.BROWN).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_GREEN_CONCRETE_POWDER = BLOCKS.register("waxed_green_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.GREEN).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_RED_CONCRETE_POWDER = BLOCKS.register("waxed_red_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.RED).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<FallingBlock> WAXED_BLACK_CONCRETE_POWDER = BLOCKS.register("waxed_black_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.BLACK).strength(0.5F).sound(SoundType.SAND)));
    //Redstone Components
    public static final RegistryObject<Block> EXPOSER = BLOCKS.register("exposer", ExposerBlock::new);
    public static final RegistryObject<Block> SHRAPNEL_BOMB = BLOCKS.register("shrapnel_bomb", () -> new ShrapnelBombBlock(BlockBehaviour.Properties.copy(Blocks.TNT)));


    /*//////////////////////////////////            BLOCK ITEMS            //////////////////////////////////*/
    //Ores
    public static final RegistryObject<Item> SILVER_ORE_ITEM = ITEMS.register("silver_ore", () -> new BlockItemBase(SILVER_ORE.get()));
    public static final RegistryObject<Item> LEAD_ORE_ITEM = ITEMS.register("lead_ore", () -> new BlockItemBase(LEAD_ORE.get()));
    public static final RegistryObject<Item> DEEPSLATE_SILVER_ORE_ITEM = ITEMS.register("deepslate_silver_ore", () -> new BlockItemBase(DEEPSLATE_SILVER_ORE.get()));
    public static final RegistryObject<Item> DEEPSLATE_LEAD_ORE_ITEM = ITEMS.register("deepslate_lead_ore", () -> new BlockItemBase(DEEPSLATE_LEAD_ORE.get()));
    public static final RegistryObject<Item> RAW_SILVER_BLOCK_ITEM = ITEMS.register("raw_silver_block", () -> new BlockItemBase(RAW_SILVER_BLOCK.get()));
    public static final RegistryObject<Item> RAW_LEAD_BLOCK_ITEM = ITEMS.register("raw_lead_block", () -> new BlockItemBase(RAW_LEAD_BLOCK.get()));
    //Blocks
    public static final RegistryObject<Item> SILVER_BLOCK_ITEM = ITEMS.register("silver_block", () -> new BlockItemBase(SILVER_BLOCK.get()));
    public static final RegistryObject<Item> LEAD_BLOCK_ITEM = ITEMS.register("lead_block", () -> new BlockItemBase(LEAD_BLOCK.get()));
    //public static final RegistryObject <Item> LEAD_COATING_ITEM = ITEMS.register("lead_coating", () -> new BlockItemBase(LEAD_COATING.get(), 1));
    //public static final RegistryObject<Item> CUT_LEAD_COATING_ITEM = ITEMS.register("cut_lead_coating", () -> new BlockItemBase(CUT_LEAD_COATING.get(), 1));
    //public static final RegistryObject<Item> LIGHTENED_IRON_BLOCK_ITEM = ITEMS.register("lightened_iron_block", () -> new BlockItemBase(LIGHTENED_IRON_BLOCK.get()));
    //public static final RegistryObject<Item> CAST_IRON_BLOCK_ITEM = ITEMS.register("cast_iron_block", () -> new BlockItemBase(CAST_IRON_BLOCK.get()));
    //public static final RegistryObject<Item> CUT_CAST_IRON_BLOCK_ITEM = ITEMS.register("cut_cast_iron_block", () -> new BlockItemBase(CUT_CAST_IRON_BLOCK.get()));
    //public static final RegistryObject<Item> BLASTED_IRON_BLOCK_ITEM = ITEMS.register("blasted_iron_block", () -> new BlockItemBase(BLASTED_IRON_BLOCK.get()));
    //public static final RegistryObject<Item> CUT_BLASTED_IRON_BLOCK_ITEM = ITEMS.register("cut_blasted_iron_block", () -> new BlockItemBase(CUT_BLASTED_IRON_BLOCK.get()));
    //public static final RegistryObject<Item> TECHNICAL_NETHERITE_BLOCK_ITEM = ITEMS.register("technical_netherite_block", () -> new TechnicalNetheriteBlockItem(TECHNICAL_NETHERITE_BLOCK.get()));
    //public static final RegistryObject<Item> CUT_TECHNICAL_NETHERITE_BLOCK_ITEM = ITEMS.register("cut_technical_netherite_block", () -> new TechnicalNetheriteBlockItem(CUT_TECHNICAL_NETHERITE_BLOCK.get()));
    public static final RegistryObject<Item> GLANCE_ITEM = ITEMS.register("glance", () -> new BlockItemBase(GLANCE.get()));
    public static final RegistryObject<Item> POLISHED_GLANCE_ITEM = ITEMS.register("polished_glance", () -> new BlockItemBase(POLISHED_GLANCE.get()));
    public static final RegistryObject<Item> GLANCE_BRICKS_ITEM = ITEMS.register("glance_bricks", () -> new BlockItemBase(GLANCE_BRICKS.get()));
    public static final RegistryObject<Item> CHISELED_GLANCE_ITEM = ITEMS.register("chiseled_glance", () -> new BlockItemBase(CHISELED_GLANCE.get()));
    public static final RegistryObject<Item> SPOTTED_GLANCE_ITEM = ITEMS.register("spotted_glance", () -> new BlockItemBase(SPOTTED_GLANCE.get()));
    public static final RegistryObject<Item> WAXED_SPOTTED_GLANCE_ITEM = ITEMS.register("waxed_spotted_glance", () -> new BlockItemBase(WAXED_SPOTTED_GLANCE.get()));
    //Stairs
    //public static final RegistryObject<Item> LIGHTENED_IRON_STAIRS_ITEM = ITEMS.register("lightened_iron_stairs", () -> new BlockItemBase(LIGHTENED_IRON_STAIRS.get()));
    //public static final RegistryObject<Item> CUT_CAST_IRON_STAIRS_ITEM = ITEMS.register("cut_cast_iron_stairs", () -> new BlockItemBase(CUT_CAST_IRON_STAIRS.get()));
    //public static final RegistryObject<Item> BLASTED_IRON_STAIRS_ITEM = ITEMS.register("blasted_iron_stairs", () -> new BlockItemBase(BLASTED_IRON_STAIRS.get()));
    //public static final RegistryObject<Item> CUT_BLASTED_IRON_STAIRS_ITEM = ITEMS.register("cut_blasted_iron_stairs", () -> new BlockItemBase(CUT_BLASTED_IRON_STAIRS.get()));
    public static final RegistryObject<Item> GLANCE_STAIRS_ITEM = ITEMS.register("glance_stairs", () -> new BlockItemBase(GLANCE_STAIRS.get()));
    public static final RegistryObject<Item> GLANCE_BRICKS_STAIRS_ITEM = ITEMS.register("glance_bricks_stairs", () -> new BlockItemBase(GLANCE_BRICKS_STAIRS.get()));
    //public static final RegistryObject<Item> TECHNICAL_NETHERITE_STAIRS_ITEM = ITEMS.register("technical_netherite_stairs", () -> new TechnicalNetheriteBlockItem(TECHNICAL_NETHERITE_STAIRS.get()));
    //public static final RegistryObject<Item> CUT_TECHNICAL_NETHERITE_STAIRS_ITEM = ITEMS.register("cut_technical_netherite_stairs", () -> new TechnicalNetheriteBlockItem(CUT_TECHNICAL_NETHERITE_STAIRS.get()));
    //Slabs
    //public static final RegistryObject<Item> LIGHTENED_IRON_SLAB_ITEM = ITEMS.register("lightened_iron_slab", () -> new BlockItemBase(LIGHTENED_IRON_SLAB.get()));
    //public static final RegistryObject<Item> CUT_CAST_IRON_SLAB_ITEM = ITEMS.register("cut_cast_iron_slab", () -> new BlockItemBase(CUT_CAST_IRON_SLAB.get()));
    //public static final RegistryObject<Item> BLASTED_IRON_SLAB_ITEM = ITEMS.register("blasted_iron_slab", () -> new BlockItemBase(BLASTED_IRON_SLAB.get()));
    //public static final RegistryObject<Item> CUT_BLASTED_IRON_SLAB_ITEM = ITEMS.register("cut_blasted_iron_slab", () -> new BlockItemBase(CUT_BLASTED_IRON_SLAB.get()));
    public static final RegistryObject<Item> GLANCE_SLAB_ITEM = ITEMS.register("glance_slab", () -> new BlockItemBase(GLANCE_SLAB.get()));
    public static final RegistryObject<Item> GLANCE_BRICKS_SLAB_ITEM = ITEMS.register("glance_bricks_slab", () -> new BlockItemBase(GLANCE_BRICKS_SLAB.get()));
    //public static final RegistryObject<Item> TECHNICAL_NETHERITE_SLAB_ITEM = ITEMS.register("technical_netherite_slab", () -> new TechnicalNetheriteBlockItem(TECHNICAL_NETHERITE_SLAB.get()));
    //public static final RegistryObject<Item> CUT_TECHNICAL_NETHERITE_SLAB_ITEM = ITEMS.register("cut_technical_netherite_slab", () -> new TechnicalNetheriteBlockItem(CUT_TECHNICAL_NETHERITE_SLAB.get()));
    //Walls
    public static final RegistryObject<Item> GLANCE_WALL_ITEM = ITEMS.register("glance_wall", () -> new BlockItemBase(GLANCE_WALL.get()));
    public static final RegistryObject<Item> GLANCE_BRICKS_WALL_ITEM = ITEMS.register("glance_bricks_wall", () -> new BlockItemBase(GLANCE_BRICKS_WALL.get()));
    //Glass
    public static final RegistryObject<Item> BLACK_CRYSTAL_GLASS_ITEM = ITEMS.register("black_crystal_glass", () -> new BlockItemBase(BLACK_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> BLUE_CRYSTAL_GLASS_ITEM = ITEMS.register("blue_crystal_glass", () -> new BlockItemBase(BLUE_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> BROWN_CRYSTAL_GLASS_ITEM = ITEMS.register("brown_crystal_glass", () -> new BlockItemBase(BROWN_CRYSTAL_GLASS.get()));
    //public static final RegistryObject<Item> CRYSTAL_GLASS_ITEM = ITEMS.register("crystal_glass", () -> new BlockItemBase(CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> CYAN_CRYSTAL_GLASS_ITEM = ITEMS.register("cyan_crystal_glass", () -> new BlockItemBase(CYAN_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> GRAY_CRYSTAL_GLASS_ITEM = ITEMS.register("gray_crystal_glass", () -> new BlockItemBase(GRAY_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> GREEN_CRYSTAL_GLASS_ITEM = ITEMS.register("green_crystal_glass", () -> new BlockItemBase(GREEN_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> LIGHT_BLUE_CRYSTAL_GLASS_ITEM = ITEMS.register("light_blue_crystal_glass", () -> new BlockItemBase(LIGHT_BLUE_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> LIGHT_GRAY_CRYSTAL_GLASS_ITEM = ITEMS.register("light_gray_crystal_glass", () -> new BlockItemBase(LIGHT_GRAY_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> LIME_CRYSTAL_GLASS_ITEM = ITEMS.register("lime_crystal_glass", () -> new BlockItemBase(LIME_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> MAGENTA_CRYSTAL_GLASS_ITEM = ITEMS.register("magenta_crystal_glass", () -> new BlockItemBase(MAGENTA_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> ORANGE_CRYSTAL_GLASS_ITEM = ITEMS.register("orange_crystal_glass", () -> new BlockItemBase(ORANGE_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> PINK_CRYSTAL_GLASS_ITEM = ITEMS.register("pink_crystal_glass", () -> new BlockItemBase(PINK_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> PURPLE_CRYSTAL_GLASS_ITEM = ITEMS.register("purple_crystal_glass", () -> new BlockItemBase(PURPLE_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> RED_CRYSTAL_GLASS_ITEM = ITEMS.register("red_crystal_glass", () -> new BlockItemBase(RED_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> WHITE_CRYSTAL_GLASS_ITEM = ITEMS.register("white_crystal_glass", () -> new BlockItemBase(WHITE_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> YELLOW_CRYSTAL_GLASS_ITEM = ITEMS.register("yellow_crystal_glass", () -> new BlockItemBase(YELLOW_CRYSTAL_GLASS.get()));
    //Glass panes
    public static final RegistryObject<Item> BLACK_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("black_crystal_glass_pane", () -> new BlockItemBase(BLACK_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> BLUE_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("blue_crystal_glass_pane", () -> new BlockItemBase(BLUE_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> BROWN_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("brown_crystal_glass_pane", () -> new BlockItemBase(BROWN_CRYSTAL_GLASS_PANE.get(), 1));
    //public static final RegistryObject<Item> CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("crystal_glass_pane", () -> new BlockItemBase(CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> CYAN_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("cyan_crystal_glass_pane", () -> new BlockItemBase(CYAN_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> GRAY_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("gray_crystal_glass_pane", () -> new BlockItemBase(GRAY_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> GREEN_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("green_crystal_glass_pane", () -> new BlockItemBase(GREEN_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> LIGHT_BLUE_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("light_blue_crystal_glass_pane", () -> new BlockItemBase(LIGHT_BLUE_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> LIGHT_GRAY_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("light_gray_crystal_glass_pane", () -> new BlockItemBase(LIGHT_GRAY_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> LIME_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("lime_crystal_glass_pane", () -> new BlockItemBase(LIME_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> MAGENTA_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("magenta_crystal_glass_pane", () -> new BlockItemBase(MAGENTA_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> ORANGE_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("orange_crystal_glass_pane", () -> new BlockItemBase(ORANGE_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> PINK_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("pink_crystal_glass_pane", () -> new BlockItemBase(PINK_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> PURPLE_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("purple_crystal_glass_pane", () -> new BlockItemBase(PURPLE_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> RED_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("red_crystal_glass_pane", () -> new BlockItemBase(RED_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> WHITE_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("white_crystal_glass_pane", () -> new BlockItemBase(WHITE_CRYSTAL_GLASS_PANE.get(), 1));
    public static final RegistryObject<Item> YELLOW_CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("yellow_crystal_glass_pane", () -> new BlockItemBase(YELLOW_CRYSTAL_GLASS_PANE.get(), 1));
    //SugarCoated Powder Concrete
    public static final RegistryObject<Item> WAXED_WHITE_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_white_concrete_powder", () -> new BlockItemBase(WAXED_WHITE_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_ORANGE_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_orange_concrete_powder", () -> new BlockItemBase(WAXED_ORANGE_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_MAGENTA_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_magenta_concrete_powder", () -> new BlockItemBase(WAXED_MAGENTA_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_LIGHT_BLUE_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_light_blue_concrete_powder", () -> new BlockItemBase(WAXED_LIGHT_BLUE_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_YELLOW_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_yellow_concrete_powder", () -> new BlockItemBase(WAXED_YELLOW_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_LIME_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_lime_concrete_powder", () -> new BlockItemBase(WAXED_LIME_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_PINK_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_pink_concrete_powder", () -> new BlockItemBase(WAXED_PINK_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_GRAY_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_gray_concrete_powder", () -> new BlockItemBase(WAXED_GRAY_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_LIGHT_GRAY_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_light_gray_concrete_powder", () -> new BlockItemBase(WAXED_LIGHT_GRAY_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_CYAN_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_cyan_concrete_powder", () -> new BlockItemBase(WAXED_CYAN_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_PURPLE_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_purple_concrete_powder", () -> new BlockItemBase(WAXED_PURPLE_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_BLUE_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_blue_concrete_powder", () -> new BlockItemBase(WAXED_BLUE_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_BROWN_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_brown_concrete_powder", () -> new BlockItemBase(WAXED_BROWN_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_GREEN_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_green_concrete_powder", () -> new BlockItemBase(WAXED_GREEN_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_RED_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_red_concrete_powder", () -> new BlockItemBase(WAXED_RED_CONCRETE_POWDER.get(), 1));
    public static final RegistryObject<Item> WAXED_BLACK_CONCRETE_POWDER_ITEM = ITEMS.register("waxed_black_concrete_powder", () -> new BlockItemBase(WAXED_BLACK_CONCRETE_POWDER.get(), 1));
    //Redstone Components
    public static final RegistryObject<Item> EXPOSER_ITEM = ITEMS.register("exposer", () -> new BlockItemBase(EXPOSER.get(), 2));
    public static final RegistryObject<Item> SHRAPNEL_BOMB_ITEM = ITEMS.register("shrapnel_bomb", () -> new BlockItemBase(SHRAPNEL_BOMB.get(), 2));


    /*//////////////////////////////////            TILE ENTITIES            //////////////////////////////////*/
    /*public static final RegistryObject<TileEntityType<SilverBlockTileEntity>> SILVER_BLOCK_TE = TILE_ENTITY_TYPES.register("silver_block", () -> TileEntityType.Builder.create(
            SilverBlockTileEntity::new, SILVER_BLOCK.get()).build(null)
    );*/
    public static final RegistryObject<BlockEntityType<ExposerBlockEntity>> EXPOSER_TE = BLOCK_ENTITY_TYPES.register("exposer", () -> BlockEntityType.Builder.of(
            ExposerBlockEntity::new, EXPOSER.get()).build(null)
    );
    /*public static final RegistryObject<BlockEntityType<StoneSignTileEntity>> STONE_SIGN_TE = TILE_ENTITY_TYPES.register("stone_sign", () -> BlockEntityType.Builder.of(
            StoneSignTileEntity::new, STONE.get(), STONE_BRICKS.get()).build(null)
    );*/

    /*//////////////////////////////////            TOOLS            //////////////////////////////////*/
    //public static final RegistryObject<SwordItem> SILVER_TINTED_GOLDEN_SWORD = ITEMS.register("silver_tinted_golden_sword", () ->
    //        new STSBase(Tiers.GOLD, 3, -2.4F)
    //);
    //public static final RegistryObject<SwordItem> SILVER_TINTED_DIAMOND_SWORD = ITEMS.register("silver_tinted_diamond_sword", () ->
    //        new STSBase(Tiers.DIAMOND, 3, -2.4F)
    //);
    //public static final RegistryObject<SwordItem> SILVER_TINTED_NETHERITE_SWORD = ITEMS.register("silver_tinted_netherite_sword", () ->
    //        new STSBase(Tiers.NETHERITE, 3, -2.4F)
    //);
    public static final RegistryObject<Item> BUSH_HAMMER = ITEMS.register("bush_hammer", BushHammer::new);


    /*//////////////////////////////////            ARMOR            //////////////////////////////////*/
    /*public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_HELMET = ITEMS.register("silver_tinted_golden_helmet", () ->
            new STABase(ArmorMaterials.GOLD, EquipmentSlot.HEAD)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_CHESTPLATE = ITEMS.register("silver_tinted_golden_chestplate", () ->
            new STABase(ArmorMaterials.GOLD, EquipmentSlot.CHEST)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_LEGGINGS = ITEMS.register("silver_tinted_golden_leggings", () ->
            new STABase(ArmorMaterials.GOLD, EquipmentSlot.LEGS)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_BOOTS = ITEMS.register("silver_tinted_golden_boots", () ->
            new STABase(ArmorMaterials.GOLD, EquipmentSlot.FEET)
    );

    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_HELMET = ITEMS.register("silver_tinted_diamond_helmet", () ->
            new STABase(ArmorMaterials.DIAMOND, EquipmentSlot.HEAD)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_CHESTPLATE = ITEMS.register("silver_tinted_diamond_chestplate", () ->
            new STABase(ArmorMaterials.DIAMOND, EquipmentSlot.CHEST)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_LEGGINGS = ITEMS.register("silver_tinted_diamond_leggings", () ->
            new STABase(ArmorMaterials.DIAMOND, EquipmentSlot.LEGS)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_BOOTS = ITEMS.register("silver_tinted_diamond_boots", () ->
            new STABase(ArmorMaterials.DIAMOND, EquipmentSlot.FEET)
    );

    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_HELMET = ITEMS.register("silver_tinted_netherite_helmet", () ->
            new STABase(ArmorMaterials.NETHERITE, EquipmentSlot.HEAD)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_CHESTPLATE = ITEMS.register("silver_tinted_netherite_chestplate", () ->
            new STABase(ArmorMaterials.NETHERITE, EquipmentSlot.CHEST)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_LEGGINGS = ITEMS.register("silver_tinted_netherite_leggings", () ->
            new STABase(ArmorMaterials.NETHERITE, EquipmentSlot.LEGS)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_BOOTS = ITEMS.register("silver_tinted_netherite_boots", () ->
            new STABase(ArmorMaterials.NETHERITE, EquipmentSlot.FEET)
    );*/

    /*//////////////////////////////////            BLOCKTAGS            //////////////////////////////////*/
    //Oreganized
    public static final TagKey<Block> BUSH_HAMMER_BREAKABLE_BLOCKTAG = BlockTags.create(new ResourceLocation(MOD_ID, "bush_hammer_breakable"));
    public static final TagKey<Block> ENGRAVEABLE_BLOCKTAG = BlockTags.create(new ResourceLocation(MOD_ID, "engraveable"));
    public static final TagKey<Block> ENGRAVED_TEXTURED_BLOCKS_BLOCKTAG = BlockTags.create(new ResourceLocation(MOD_ID, "engraved_textured_blocks"));
    public static final TagKey<Block> FIRE_SOURCE_BLOCKTAG = BlockTags.create(new ResourceLocation(MOD_ID, "fire_source"));

    //Forge
    public static final TagKey<Item> LEAD_INGOTS_ITEMTAG = ItemTags.create(new ResourceLocation("forge", "ingots/lead"));

    public static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return (boolean)false;
    }

    public static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }
}
