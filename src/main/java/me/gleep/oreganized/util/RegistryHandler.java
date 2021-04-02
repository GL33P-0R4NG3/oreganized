package me.gleep.oreganized.util;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.armors.*;
import me.gleep.oreganized.blocks.*;
import me.gleep.oreganized.entities.LeadNuggetEntity;
import me.gleep.oreganized.entities.ShrapnelTNTEntity;
import me.gleep.oreganized.entities.tileentities.ExposerBlockTileEntity;
import me.gleep.oreganized.effects.*;
import me.gleep.oreganized.fluids.*;
import me.gleep.oreganized.items.*;
import me.gleep.oreganized.tools.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    //Mod
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Oreganized.MOD_ID);
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Oreganized.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Oreganized.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Oreganized.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Oreganized.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Oreganized.MOD_ID);
    //private static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, OreExpansion.MOD_ID);

    public static void init() {
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    /*//////////////////////////////////            FLUIDS            //////////////////////////////////*/
    //MOLTEN LEAD
    public static final RegistryObject<ForgeFlowingFluid> LEAD_FLUID = FLUIDS.register("lead_fluid", LeadFluid.Source::new);
    public static final RegistryObject<ForgeFlowingFluid> LEAD_FLUID_FLOW = FLUIDS.register("lead_fluid_flow", LeadFluid.Flowing::new);
    public static final RegistryObject<FlowingFluidBlock> LEAD_FLUID_BLOCK = BLOCKS.register("lead_fluid_block",
            () -> new LeadFluidBlock(RegistryHandler.LEAD_FLUID)
    );


    /*//////////////////////////////////            EFFECTS            //////////////////////////////////*/
    public static final RegistryObject<Effect> HEAVY_METAL_POISONING = EFFECTS.register("heavy_metal_poisoning", HeavyMetalPoisoning::new);


    /*//////////////////////////////////            ENTITIES            //////////////////////////////////*/
    public static final RegistryObject<EntityType<ShrapnelTNTEntity>> SHRAPNEL_TNT_ENTITY = ENTITIES.register("shrapnel_tnt", () ->
            EntityType.Builder.<ShrapnelTNTEntity>create(
                ShrapnelTNTEntity::new, EntityClassification.MISC
            ).immuneToFire().size(0.98F, 0.98F).trackingRange(10).func_233608_b_(10).build("shrapnel_tnt")
    );
    public static final RegistryObject<EntityType<LeadNuggetEntity>> LEAD_NUGGET_ENTITY = ENTITIES.register("lead_nugget", () ->
            EntityType.Builder.<LeadNuggetEntity>create(
                LeadNuggetEntity::new, EntityClassification.MISC
            ).immuneToFire().size(0.5F, 0.5F).trackingRange(4).func_233608_b_(20).build("lead_nugget")
    );


    /*//////////////////////////////////            ITEMS            //////////////////////////////////*/
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", ItemBase::new);
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", ItemBase::new);
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", ItemBase::new);
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", ItemBase::new);
    public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget", () -> new ItemBase(true));
    public static final RegistryObject<Item> LEAD_BUCKET = ITEMS.register("lead_bucket", () -> new LeadBucket(RegistryHandler.LEAD_FLUID));
    public static final RegistryObject<Item> SILVER_MIRROR = ITEMS.register("silver_mirror", SilverMirror::new);


    /*//////////////////////////////////            BLOCKS            //////////////////////////////////*/
    //Ores
    public static final RegistryObject<Block> SILVER_ORE = BLOCKS.register("silver_ore", SilverOre::new);
    public static final RegistryObject<Block> LEAD_ORE = BLOCKS.register("lead_ore", LeadOre::new);
    //Blocks
    public static final RegistryObject<Block> SILVER_BLOCK = BLOCKS.register("silver_block", SilverBlock::new);
    public static final RegistryObject<Block> LEAD_BLOCK = BLOCKS.register("lead_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON)
            .hardnessAndResistance(5.0F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).sound(SoundType.METAL))
    );
    public static final RegistryObject<Block> CAULDRON = BLOCKS.register("cauldron", Cauldron::new);
    public static final RegistryObject<Block> LIGHTENED_IRON_BLOCK = BLOCKS.register("lightened_iron_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON)
            .hardnessAndResistance(4.0F, 5.0F).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(1).sound(SoundType.NETHERITE))
    );
    public static final RegistryObject<Block> CAST_IRON_BLOCK = BLOCKS.register("cast_iron_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON)
            .hardnessAndResistance(5.0F, 4.0F).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(1).sound(SoundType.NETHERITE))
    );
    public static final RegistryObject<Block> CUT_CAST_IRON_BLOCK = BLOCKS.register("cut_cast_iron_block", () -> new Block(AbstractBlock.Properties.from(CAST_IRON_BLOCK.get())));
    public static final RegistryObject<Block> BLASTED_IRON_BLOCK = BLOCKS.register("blasted_iron_block", BlastedIronBlock::new);
    public static final RegistryObject<Block> CUT_BLASTED_IRON_BLOCK = BLOCKS.register("cut_blasted_iron_block", () -> new Block(AbstractBlock.Properties.from(BLASTED_IRON_BLOCK.get())));
    public static final RegistryObject<Block> TECHNICAL_NETHERITE_BLOCK = BLOCKS.register("technical_netherite_block", TechnicalNetheriteBlock::new);
    public static final RegistryObject<Block> CUT_TECHNICAL_NETHERITE_BLOCK = BLOCKS.register("cut_technical_netherite_block", TechnicalNetheriteBlock::new);
    //Stairs
    public static final RegistryObject<Block> LIGHTENED_IRON_STAIRS = BLOCKS.register("lightened_iron_stairs", () -> new StairsBlock(LIGHTENED_IRON_BLOCK.get()::getDefaultState, AbstractBlock.Properties.from(LIGHTENED_IRON_BLOCK.get())));
    public static final RegistryObject<Block> CUT_CAST_IRON_STAIRS = BLOCKS.register("cut_cast_iron_stairs", () -> new StairsBlock(CAST_IRON_BLOCK.get()::getDefaultState, AbstractBlock.Properties.from(CAST_IRON_BLOCK.get())));
    public static final RegistryObject<Block> BLASTED_IRON_STAIRS = BLOCKS.register("blasted_iron_stairs", () -> new StairsBlock(BLASTED_IRON_BLOCK.get()::getDefaultState, AbstractBlock.Properties.from(BLASTED_IRON_BLOCK.get())));
    public static final RegistryObject<Block> CUT_BLASTED_IRON_STAIRS = BLOCKS.register("cut_blasted_iron_stairs", () -> new StairsBlock(CUT_BLASTED_IRON_BLOCK.get()::getDefaultState, AbstractBlock.Properties.from(CUT_BLASTED_IRON_BLOCK.get())));
    public static final RegistryObject<Block> TECHNICAL_NETHERITE_STAIRS = BLOCKS.register("technical_netherite_stairs", () -> new StairsBlock(TECHNICAL_NETHERITE_BLOCK.get()::getDefaultState, AbstractBlock.Properties.from(TECHNICAL_NETHERITE_BLOCK.get())));
    public static final RegistryObject<Block> CUT_TECHNICAL_NETHERITE_STAIRS = BLOCKS.register("cut_technical_netherite_stairs", () -> new StairsBlock(CUT_TECHNICAL_NETHERITE_BLOCK.get()::getDefaultState, AbstractBlock.Properties.from(CUT_TECHNICAL_NETHERITE_BLOCK.get())));
    //Slabs
    public static final RegistryObject<Block> LIGHTENED_IRON_SLAB = BLOCKS.register("lightened_iron_slab", () -> new SlabBlock(AbstractBlock.Properties.from(LIGHTENED_IRON_BLOCK.get())));
    public static final RegistryObject<Block> CUT_CAST_IRON_SLAB = BLOCKS.register("cut_cast_iron_slab", () -> new SlabBlock(AbstractBlock.Properties.from(CAST_IRON_BLOCK.get())));
    public static final RegistryObject<Block> BLASTED_IRON_SLAB = BLOCKS.register("blasted_iron_slab", () -> new SlabBlock(AbstractBlock.Properties.from(BLASTED_IRON_BLOCK.get())));
    public static final RegistryObject<Block> CUT_BLASTED_IRON_SLAB = BLOCKS.register("cut_blasted_iron_slab", () -> new SlabBlock(AbstractBlock.Properties.from(CUT_BLASTED_IRON_BLOCK.get())));
    public static final RegistryObject<Block> TECHNICAL_NETHERITE_SLAB = BLOCKS.register("technical_netherite_slab", () -> new SlabBlock(AbstractBlock.Properties.from(TECHNICAL_NETHERITE_BLOCK.get())));
    public static final RegistryObject<Block> CUT_TECHNICAL_NETHERITE_SLAB = BLOCKS.register("cut_technical_netherite_slab", () -> new SlabBlock(AbstractBlock.Properties.from(CUT_TECHNICAL_NETHERITE_BLOCK.get())));
    //Glass
    public static final RegistryObject<Block> BLACK_CRYSTAL_GLASS = BLOCKS.register("black_crystal_glass", () -> new CrystalGlassColored(DyeColor.BLACK));
    public static final RegistryObject<Block> BLUE_CRYSTAL_GLASS = BLOCKS.register("blue_crystal_glass", () -> new CrystalGlassColored(DyeColor.BLUE));
    public static final RegistryObject<Block> BROWN_CRYSTAL_GLASS = BLOCKS.register("brown_crystal_glass", () -> new CrystalGlassColored(DyeColor.BROWN));
    public static final RegistryObject<Block> CRYSTAL_GLASS = BLOCKS.register("crystal_glass", CrystalGlassBase::new);
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
    public static final RegistryObject<Block> CRYSTAL_GLASS_PANE = BLOCKS.register("crystal_glass_pane", CrystalGlassPaneBase::new);
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
    //Redstone Components
    public static final RegistryObject<Block> EXPOSER = BLOCKS.register("exposer", ExposerBlock::new);
    public static final RegistryObject<Block> SHRAPNEL_TNT = BLOCKS.register("shrapnel_tnt", ShrapnelTNT::new);


    /*//////////////////////////////////            BLOCK ITEMS            //////////////////////////////////*/
    //Ores
    public static final RegistryObject<Item> SILVER_ORE_ITEM = ITEMS.register("silver_ore", () -> new BlockItemBase(SILVER_ORE.get()));
    public static final RegistryObject<Item> LEAD_ORE_ITEM = ITEMS.register("lead_ore", () -> new BlockItemBase(LEAD_ORE.get()));
    //Blocks
    public static final RegistryObject<Item> SILVER_BLOCK_ITEM = ITEMS.register("silver_block", () -> new BlockItemBase(SILVER_BLOCK.get()));
    public static final RegistryObject<Item> LEAD_BLOCK_ITEM = ITEMS.register("lead_block", () -> new BlockItemBase(LEAD_BLOCK.get()));
    public static final RegistryObject<Item> LIGHTENED_IRON_BLOCK_ITEM = ITEMS.register("lightened_iron_block", () -> new BlockItemBase(LIGHTENED_IRON_BLOCK.get()));
    public static final RegistryObject<Item> CAST_IRON_BLOCK_ITEM = ITEMS.register("cast_iron_block", () -> new BlockItemBase(CAST_IRON_BLOCK.get()));
    public static final RegistryObject<Item> CUT_CAST_IRON_BLOCK_ITEM = ITEMS.register("cut_cast_iron_block", () -> new BlockItemBase(CUT_CAST_IRON_BLOCK.get()));
    public static final RegistryObject<Item> BLASTED_IRON_BLOCK_ITEM = ITEMS.register("blasted_iron_block", () -> new BlockItemBase(BLASTED_IRON_BLOCK.get()));
    public static final RegistryObject<Item> CUT_BLASTED_IRON_BLOCK_ITEM = ITEMS.register("cut_blasted_iron_block", () -> new BlockItemBase(CUT_BLASTED_IRON_BLOCK.get()));
    public static final RegistryObject<Item> TECHNICAL_NETHERITE_BLOCK_ITEM = ITEMS.register("technical_netherite_block", () -> new TechnicalNetheriteBlockItem(TECHNICAL_NETHERITE_BLOCK.get()));
    public static final RegistryObject<Item> CUT_TECHNICAL_NETHERITE_BLOCK_ITEM = ITEMS.register("cut_technical_netherite_block", () -> new TechnicalNetheriteBlockItem(CUT_TECHNICAL_NETHERITE_BLOCK.get()));
    //Stairs
    public static final RegistryObject<Item> LIGHTENED_IRON_STAIRS_ITEM = ITEMS.register("lightened_iron_stairs", () -> new BlockItemBase(LIGHTENED_IRON_STAIRS.get()));
    public static final RegistryObject<Item> CUT_CAST_IRON_STAIRS_ITEM = ITEMS.register("cut_cast_iron_stairs", () -> new BlockItemBase(CUT_CAST_IRON_STAIRS.get()));
    public static final RegistryObject<Item> BLASTED_IRON_STAIRS_ITEM = ITEMS.register("blasted_iron_stairs", () -> new BlockItemBase(BLASTED_IRON_STAIRS.get()));
    public static final RegistryObject<Item> CUT_BLASTED_IRON_STAIRS_ITEM = ITEMS.register("cut_blasted_iron_stairs", () -> new BlockItemBase(CUT_BLASTED_IRON_STAIRS.get()));
    public static final RegistryObject<Item> TECHNICAL_NETHERITE_STAIRS_ITEM = ITEMS.register("technical_netherite_stairs", () -> new TechnicalNetheriteBlockItem(TECHNICAL_NETHERITE_STAIRS.get()));
    public static final RegistryObject<Item> CUT_TECHNICAL_NETHERITE_STAIRS_ITEM = ITEMS.register("cut_technical_netherite_stairs", () -> new TechnicalNetheriteBlockItem(CUT_TECHNICAL_NETHERITE_STAIRS.get()));
    //Slabs
    public static final RegistryObject<Item> LIGHTENED_IRON_SLAB_ITEM = ITEMS.register("lightened_iron_slab", () -> new BlockItemBase(LIGHTENED_IRON_SLAB.get()));
    public static final RegistryObject<Item> CUT_CAST_IRON_SLAB_ITEM = ITEMS.register("cut_cast_iron_slab", () -> new BlockItemBase(CUT_CAST_IRON_SLAB.get()));
    public static final RegistryObject<Item> BLASTED_IRON_SLAB_ITEM = ITEMS.register("blasted_iron_slab", () -> new BlockItemBase(BLASTED_IRON_SLAB.get()));
    public static final RegistryObject<Item> CUT_BLASTED_IRON_SLAB_ITEM = ITEMS.register("cut_blasted_iron_slab", () -> new BlockItemBase(CUT_BLASTED_IRON_SLAB.get()));
    public static final RegistryObject<Item> TECHNICAL_NETHERITE_SLAB_ITEM = ITEMS.register("technical_netherite_slab", () -> new TechnicalNetheriteBlockItem(TECHNICAL_NETHERITE_SLAB.get()));
    public static final RegistryObject<Item> CUT_TECHNICAL_NETHERITE_SLAB_ITEM = ITEMS.register("cut_technical_netherite_slab", () -> new TechnicalNetheriteBlockItem(CUT_TECHNICAL_NETHERITE_SLAB.get()));
    //Glass
    public static final RegistryObject<Item> BLACK_CRYSTAL_GLASS_ITEM = ITEMS.register("black_crystal_glass", () -> new BlockItemBase(BLACK_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> BLUE_CRYSTAL_GLASS_ITEM = ITEMS.register("blue_crystal_glass", () -> new BlockItemBase(BLUE_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> BROWN_CRYSTAL_GLASS_ITEM = ITEMS.register("brown_crystal_glass", () -> new BlockItemBase(BROWN_CRYSTAL_GLASS.get()));
    public static final RegistryObject<Item> CRYSTAL_GLASS_ITEM = ITEMS.register("crystal_glass", () -> new BlockItemBase(CRYSTAL_GLASS.get()));
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
    public static final RegistryObject<Item> CRYSTAL_GLASS_PANE_ITEM = ITEMS.register("crystal_glass_pane", () -> new BlockItemBase(CRYSTAL_GLASS_PANE.get(), 1));
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
    //Redstone Components
    public static final RegistryObject<Item> EXPOSER_ITEM = ITEMS.register("exposer", () -> new BlockItemBase(EXPOSER.get(), 2));
    public static final RegistryObject<Item> SHRAPNEL_TNT_ITEM = ITEMS.register("shrapnel_tnt", () -> new BlockItemBase(SHRAPNEL_TNT.get(), 2));


    /*//////////////////////////////////            TILE ENTITIES            //////////////////////////////////*/
    /*public static final RegistryObject<TileEntityType<SilverBlockTileEntity>> SILVER_BLOCK_TE = TILE_ENTITY_TYPES.register("silver_block", () -> TileEntityType.Builder.create(
            SilverBlockTileEntity::new, SILVER_BLOCK.get()).build(null)
    );*/
    public static final RegistryObject<TileEntityType<ExposerBlockTileEntity>> EXPOSER_TE = TILE_ENTITY_TYPES.register("exposer", () -> TileEntityType.Builder.create(
            ExposerBlockTileEntity::new, EXPOSER.get()).build(null)
    );


    /*//////////////////////////////////            TOOLS            //////////////////////////////////*/
    public static final RegistryObject<SwordItem> SILVER_TINTED_GOLDEN_SWORD = ITEMS.register("silver_tinted_golden_sword", () ->
            new STSBase(ItemTier.GOLD, 3, -2.4F)
    );
    public static final RegistryObject<SwordItem> SILVER_TINTED_DIAMOND_SWORD = ITEMS.register("silver_tinted_diamond_sword", () ->
            new STSBase(ItemTier.DIAMOND, 3, -2.4F)
    );
    public static final RegistryObject<SwordItem> SILVER_TINTED_NETHERITE_SWORD = ITEMS.register("silver_tinted_netherite_sword", () ->
            new STSBase(ItemTier.NETHERITE, 3, -2.4F)
    );


    /*//////////////////////////////////            ARMOR            //////////////////////////////////*/
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_HELMET = ITEMS.register("silver_tinted_golden_helmet", () ->
            new STABase(ArmorMaterial.GOLD, EquipmentSlotType.HEAD)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_CHESTPLATE = ITEMS.register("silver_tinted_golden_chestplate", () ->
            new STABase(ArmorMaterial.GOLD, EquipmentSlotType.CHEST)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_LEGGINGS = ITEMS.register("silver_tinted_golden_leggings", () ->
            new STABase(ArmorMaterial.GOLD, EquipmentSlotType.LEGS)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_BOOTS = ITEMS.register("silver_tinted_golden_boots", () ->
            new STABase(ArmorMaterial.GOLD, EquipmentSlotType.FEET)
    );

    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_HELMET = ITEMS.register("silver_tinted_diamond_helmet", () ->
            new STABase(ArmorMaterial.DIAMOND, EquipmentSlotType.HEAD)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_CHESTPLATE = ITEMS.register("silver_tinted_diamond_chestplate", () ->
            new STABase(ArmorMaterial.DIAMOND, EquipmentSlotType.CHEST)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_LEGGINGS = ITEMS.register("silver_tinted_diamond_leggings", () ->
            new STABase(ArmorMaterial.DIAMOND, EquipmentSlotType.LEGS)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_BOOTS = ITEMS.register("silver_tinted_diamond_boots", () ->
            new STABase(ArmorMaterial.DIAMOND, EquipmentSlotType.FEET)
    );

    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_HELMET = ITEMS.register("silver_tinted_netherite_helmet", () ->
            new STABase(ArmorMaterial.NETHERITE, EquipmentSlotType.HEAD)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_CHESTPLATE = ITEMS.register("silver_tinted_netherite_chestplate", () ->
            new STABase(ArmorMaterial.NETHERITE, EquipmentSlotType.CHEST)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_LEGGINGS = ITEMS.register("silver_tinted_netherite_leggings", () ->
            new STABase(ArmorMaterial.NETHERITE, EquipmentSlotType.LEGS)
    );
    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_BOOTS = ITEMS.register("silver_tinted_netherite_boots", () ->
            new STABase(ArmorMaterial.NETHERITE, EquipmentSlotType.FEET)
    );

}
