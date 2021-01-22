package me.gleep.oreexpansion.util;

import me.gleep.oreexpansion.OreExpansion;
import me.gleep.oreexpansion.armors.STAMaterial;
import me.gleep.oreexpansion.armors.STABase;
import me.gleep.oreexpansion.blocks.*;
import me.gleep.oreexpansion.blocks.tileentities.SilverBlockTileEntity;
import me.gleep.oreexpansion.fluids.LeadFluid;
import me.gleep.oreexpansion.fluids.LeadFluidBlock;
import me.gleep.oreexpansion.fluids.LeadFluidFlow;
import me.gleep.oreexpansion.items.ItemBase;
import me.gleep.oreexpansion.items.LeadBucket;
import me.gleep.oreexpansion.items.SilverMirror;
import me.gleep.oreexpansion.tools.STSMaterial;
import me.gleep.oreexpansion.tools.STSBase;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    //Mod
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OreExpansion.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OreExpansion.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, OreExpansion.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, OreExpansion.MOD_ID);
    //private static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, OreExpansion.MOD_ID);

    public static void init() {
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    //Fluids
    public static final RegistryObject<ForgeFlowingFluid> LEAD_FLUID = FLUIDS.register("lead_fluid", LeadFluid::new);
    public static final RegistryObject<ForgeFlowingFluid> LEAD_FLUID_FLOW = FLUIDS.register("lead_fluid_flow", LeadFluidFlow::new);
    public static final RegistryObject<FlowingFluidBlock> LEAD_FLUID_BLOCK = BLOCKS.register("lead_fluid_block",
            () -> new LeadFluidBlock(RegistryHandler.LEAD_FLUID));


    //Items
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", ItemBase::new);
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", ItemBase::new);
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", ItemBase::new);
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", ItemBase::new);
    public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget", () -> new ItemBase(true));
    public static final RegistryObject<Item> LEAD_BUCKET = ITEMS.register("lead_bucket", () -> new LeadBucket(RegistryHandler.LEAD_FLUID));
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", ItemBase::new);
    public static final RegistryObject<Item> SILVER_MIRROR = ITEMS.register("silver_mirror", SilverMirror::new);


    //Blocks
    public static final RegistryObject<Block> SILVER_BLOCK = BLOCKS.register("silver_block", SilverBlock::new);
    public static final RegistryObject<Block> LEAD_BLOCK = BLOCKS.register("lead_block", LeadBlock::new);
    public static final RegistryObject<Block> SILVER_ORE = BLOCKS.register("silver_ore", SilverOre::new);
    public static final RegistryObject<Block> LEAD_ORE = BLOCKS.register("lead_ore", LeadOre::new);
    public static final RegistryObject<Block> CAULDRON = BLOCKS.register("cauldron", Cauldron::new);
    public static final RegistryObject<Block> CAST_IRON_BLOCK = BLOCKS.register("cast_iron_block", CastIronBlock::new);
    public static final RegistryObject<Block> BLASTED_IRON_BLOCK = BLOCKS.register("blasted_iron_block", BlastedCastIronBlock::new);

    public static final RegistryObject<Block> BLACK_CRYSTAL_GLASS = BLOCKS.register("black_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> BLUE_CRYSTAL_GLASS = BLOCKS.register("blue_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> BROWN_CRYSTAL_GLASS = BLOCKS.register("brown_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> CRYSTAL_GLASS = BLOCKS.register("crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> CYAN_CRYSTAL_GLASS = BLOCKS.register("cyan_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> GRAY_CRYSTAL_GLASS = BLOCKS.register("gray_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> GREEN_CRYSTAL_GLASS = BLOCKS.register("green_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> LIGHT_BLUE_CRYSTAL_GLASS = BLOCKS.register("light_blue_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> LIGHT_GRAY_CRYSTAL_GLASS = BLOCKS.register("light_gray_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> LIME_CRYSTAL_GLASS = BLOCKS.register("lime_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> MAGENTA_CRYSTAL_GLASS = BLOCKS.register("magenta_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> ORANGE_CRYSTAL_GLASS = BLOCKS.register("orange_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> PINK_CRYSTAL_GLASS = BLOCKS.register("pink_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> PURPLE_CRYSTAL_GLASS = BLOCKS.register("purple_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> RED_CRYSTAL_GLASS = BLOCKS.register("red_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> WHITE_CRYSTAL_GLASS = BLOCKS.register("white_crystal_glass", CrystalGlassBase::new);
    public static final RegistryObject<Block> YELLOW_CRYSTAL_GLASS = BLOCKS.register("yellow_crystal_glass", CrystalGlassBase::new);


    //Block Item
    public static final RegistryObject<Item> SILVER_BLOCK_ITEM = ITEMS.register("silver_block", () -> new BlockItemBase(SILVER_BLOCK.get()));
    public static final RegistryObject<Item> LEAD_BLOCK_ITEM = ITEMS.register("lead_block", () -> new BlockItemBase(LEAD_BLOCK.get()));
    public static final RegistryObject<Item> SILVER_ORE_ITEM = ITEMS.register("silver_ore", () -> new BlockItemBase(SILVER_ORE.get()));
    public static final RegistryObject<Item> LEAD_ORE_ITEM = ITEMS.register("lead_ore", () -> new BlockItemBase(LEAD_ORE.get()));
    public static final RegistryObject<Item> CAST_IRON_BLOCK_ITEM = ITEMS.register("cast_iron_block", () -> new BlockItemBase(CAST_IRON_BLOCK.get()));
    public static final RegistryObject<Item> BLASTED_IRON_BLOCK_ITEM = ITEMS.register("blasted_iron_block", () -> new BlockItemBase(BLASTED_IRON_BLOCK.get()));

    public static final RegistryObject<Item> BLACK_CRYSTAL_GLASS_ITEM = ITEMS.register("black_crystal_glass", () -> new BlockItemBase(BLACK_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> BLUE_CRYSTAL_GLASS_ITEM = ITEMS.register("blue_crystal_glass", () -> new BlockItemBase(BLUE_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> BROWN_CRYSTAL_GLASS_ITEM = ITEMS.register("brown_crystal_glass", () -> new BlockItemBase(BROWN_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> CRYSTAL_GLASS_ITEM = ITEMS.register("crystal_glass", () -> new BlockItemBase(CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> CYAN_CRYSTAL_GLASS_ITEM = ITEMS.register("cyan_crystal_glass", () -> new BlockItemBase(CYAN_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> GRAY_CRYSTAL_GLASS_ITEM = ITEMS.register("gray_crystal_glass", () -> new BlockItemBase(GRAY_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> GREEN_CRYSTAL_GLASS_ITEM = ITEMS.register("green_crystal_glass", () -> new BlockItemBase(GREEN_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> LIGHT_BLUE_CRYSTAL_GLASS_ITEM = ITEMS.register("light_blue_crystal_glass", () -> new BlockItemBase(LIGHT_BLUE_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> LIGHT_GRAY_CRYSTAL_GLASS_ITEM = ITEMS.register("light_gray_crystal_glass", () -> new BlockItemBase(LIGHT_GRAY_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> LIME_CRYSTAL_GLASS_ITEM = ITEMS.register("lime_crystal_glass", () -> new BlockItemBase(LIME_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> MAGENTA_CRYSTAL_GLASS_ITEM = ITEMS.register("magenta_crystal_glass", () -> new BlockItemBase(MAGENTA_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> ORANGE_CRYSTAL_GLASS_ITEM = ITEMS.register("orange_crystal_glass", () -> new BlockItemBase(ORANGE_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> PINK_CRYSTAL_GLASS_ITEM = ITEMS.register("pink_crystal_glass", () -> new BlockItemBase(PINK_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> PURPLE_CRYSTAL_GLASS_ITEM = ITEMS.register("purple_crystal_glass", () -> new BlockItemBase(PURPLE_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> RED_CRYSTAL_GLASS_ITEM = ITEMS.register("red_crystal_glass", () -> new BlockItemBase(RED_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> WHITE_CRYSTAL_GLASS_ITEM = ITEMS.register("white_crystal_glass", () -> new BlockItemBase(WHITE_CRYSTAL_GLASS.get(), true));
    public static final RegistryObject<Item> YELLOW_CRYSTAL_GLASS_ITEM = ITEMS.register("yellow_crystal_glass", () -> new BlockItemBase(YELLOW_CRYSTAL_GLASS.get(), true));


    //Tile Entities
    public static final RegistryObject<TileEntityType<SilverBlockTileEntity>> SILVER_BLOCK_TE = TILE_ENTITY_TYPES.register("silver_block", () -> TileEntityType.Builder.create(
            SilverBlockTileEntity::new, SILVER_BLOCK.get()).build(null));
    /*public static final RegistryObject<TileEntityType<BlastedIronBlockTileEntity>> BLASTED_IRON_BLOCK_TE = TILE_ENTITY_TYPES.register("blasted_iron_block", () -> TileEntityType.Builder.create(
            BlastedIronBlockTileEntity::new, BLASTED_IRON_BLOCK.get()).build(null));*/


    //Tools
    public static final RegistryObject<SwordItem> SILVER_TINTED_GOLDEN_SWORD = ITEMS.register("silver_tinted_golden_sword", () ->
            new STSBase(STSMaterial.STGS, 3, -2.4F));
    public static final RegistryObject<SwordItem> SILVER_TINTED_DIAMOND_SWORD = ITEMS.register("silver_tinted_diamond_sword", () ->
            new STSBase(STSMaterial.STDS, 3, -2.4F));
    public static final RegistryObject<SwordItem> SILVER_TINTED_NETHERITE_SWORD = ITEMS.register("silver_tinted_netherite_sword", () ->
            new STSBase(STSMaterial.STNS, 3, -2.4F, true));


    //Armors
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_HELMET = ITEMS.register("silver_tinted_golden_helmet", () ->
            new STABase(STAMaterial.STGA, EquipmentSlotType.HEAD));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_CHESTPLATE = ITEMS.register("silver_tinted_golden_chestplate", () ->
            new STABase(STAMaterial.STGA, EquipmentSlotType.CHEST));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_LEGGINGS = ITEMS.register("silver_tinted_golden_leggings", () ->
            new STABase(STAMaterial.STGA, EquipmentSlotType.LEGS));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_BOOTS = ITEMS.register("silver_tinted_golden_boots", () ->
            new STABase(STAMaterial.STGA, EquipmentSlotType.FEET));

    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_HELMET = ITEMS.register("silver_tinted_diamond_helmet", () ->
            new STABase(STAMaterial.STDA, EquipmentSlotType.HEAD));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_CHESTPLATE = ITEMS.register("silver_tinted_diamond_chestplate", () ->
            new STABase(STAMaterial.STDA, EquipmentSlotType.CHEST));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_LEGGINGS = ITEMS.register("silver_tinted_diamond_leggings", () ->
            new STABase(STAMaterial.STDA, EquipmentSlotType.LEGS));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_BOOTS = ITEMS.register("silver_tinted_diamond_boots", () ->
            new STABase(STAMaterial.STDA, EquipmentSlotType.FEET));

    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_HELMET = ITEMS.register("silver_tinted_netherite_helmet", () ->
            new STABase(STAMaterial.STNA, EquipmentSlotType.HEAD, true));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_CHESTPLATE = ITEMS.register("silver_tinted_netherite_chestplate", () ->
            new STABase(STAMaterial.STNA, EquipmentSlotType.CHEST, true));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_LEGGINGS = ITEMS.register("silver_tinted_netherite_leggings", () ->
            new STABase(STAMaterial.STNA, EquipmentSlotType.LEGS, true));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_BOOTS = ITEMS.register("silver_tinted_netherite_boots", () ->
            new STABase(STAMaterial.STNA, EquipmentSlotType.FEET, true));

}
