package me.gleep.oreexpansion.util;

import me.gleep.oreexpansion.OreExpansion;
import me.gleep.oreexpansion.armors.STAMaterial;
import me.gleep.oreexpansion.armors.ArmorBase;
import me.gleep.oreexpansion.blocks.*;
import me.gleep.oreexpansion.blocks.tileentities.SilverBlockTileEntity;
import me.gleep.oreexpansion.blocks.LeadFluid;
import me.gleep.oreexpansion.items.ItemBase;
import me.gleep.oreexpansion.items.LeadBucket;
import me.gleep.oreexpansion.tools.STSMaterial;
import me.gleep.oreexpansion.tools.STSBase;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    //Mod
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OreExpansion.MOD_ID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OreExpansion.MOD_ID);
    private static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, OreExpansion.MOD_ID);
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, OreExpansion.MOD_ID);
    //private static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, OreExpansion.MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    //Items
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", ItemBase::new);
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", ItemBase::new);
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", ItemBase::new);
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", ItemBase::new);
    public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget", () -> new ItemBase(true));
    public static final RegistryObject<Item> LEAD_BUCKET = ITEMS.register("lead_bucket", LeadBucket::new);


    //Blocks
    public static final RegistryObject<Block> SILVER_BLOCK = BLOCKS.register("silver_block", SilverBlock::new);
    public static final RegistryObject<Block> LEAD_BLOCK = BLOCKS.register("lead_block", LeadBlock::new);
    public static final RegistryObject<Block> SILVER_ORE = BLOCKS.register("silver_ore", SilverOre::new);
    public static final RegistryObject<Block> LEAD_ORE = BLOCKS.register("lead_ore", LeadOre::new);
    public static final RegistryObject<Block> CAULDRON = BLOCKS.register("cauldron", Cauldron::new);

    //Block Item
    public static final RegistryObject<Item> SILVER_BLOCK_ITEM = ITEMS.register("silver_block", () -> new BlockItemBase(SILVER_BLOCK.get()));
    public static final RegistryObject<Item> LEAD_BLOCK_ITEM = ITEMS.register("lead_block", () -> new BlockItemBase(LEAD_BLOCK.get()));
    public static final RegistryObject<Item> SILVER_ORE_ITEM = ITEMS.register("silver_ore", () -> new BlockItemBase(SILVER_ORE.get()));
    public static final RegistryObject<Item> LEAD_ORE_ITEM = ITEMS.register("lead_ore", () -> new BlockItemBase(LEAD_ORE.get()));


    //Fluids
    public static final RegistryObject<Block> LEAD_FLUID = BLOCKS.register("lead_fluid", LeadFluid::new);


    //Tile Entities
    public static final RegistryObject<TileEntityType<SilverBlockTileEntity>> SILVER_BLOCK_TE = TILE_ENTITY_TYPES.register("silver_block", () -> TileEntityType.Builder.create(
            SilverBlockTileEntity::new, SILVER_BLOCK.get()).build(null));


    //Tools
    public static final RegistryObject<SwordItem> SILVER_TINTED_GOLDEN_SWORD = ITEMS.register("silver_tinted_golden_sword", () ->
            new STSBase(STSMaterial.STGS, 3, -2.4F));
    public static final RegistryObject<SwordItem> SILVER_TINTED_DIAMOND_SWORD = ITEMS.register("silver_tinted_diamond_sword", () ->
            new STSBase(STSMaterial.STDS, 3, -2.4F));
    public static final RegistryObject<SwordItem> SILVER_TINTED_NETHERITE_SWORD = ITEMS.register("silver_tinted_netherite_sword", () ->
            new STSBase(STSMaterial.STNS, 3, -2.4F, true));


    //Armors
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_HELMET = ITEMS.register("silver_tinted_golden_helmet", () ->
            new ArmorBase(STAMaterial.STGA, EquipmentSlotType.HEAD));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_CHESTPLATE = ITEMS.register("silver_tinted_golden_chestplate", () ->
            new ArmorBase(STAMaterial.STGA, EquipmentSlotType.CHEST));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_LEGGINGS = ITEMS.register("silver_tinted_golden_leggings", () ->
            new ArmorBase(STAMaterial.STGA, EquipmentSlotType.LEGS));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_GOLDEN_BOOTS = ITEMS.register("silver_tinted_golden_boots", () ->
            new ArmorBase(STAMaterial.STGA, EquipmentSlotType.FEET));

    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_HELMET = ITEMS.register("silver_tinted_diamond_helmet", () ->
            new ArmorBase(STAMaterial.STDA, EquipmentSlotType.HEAD));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_CHESTPLATE = ITEMS.register("silver_tinted_diamond_chestplate", () ->
            new ArmorBase(STAMaterial.STDA, EquipmentSlotType.CHEST));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_LEGGINGS = ITEMS.register("silver_tinted_diamond_leggings", () ->
            new ArmorBase(STAMaterial.STDA, EquipmentSlotType.LEGS));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_DIAMOND_BOOTS = ITEMS.register("silver_tinted_diamond_boots", () ->
            new ArmorBase(STAMaterial.STDA, EquipmentSlotType.FEET));

    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_HELMET = ITEMS.register("silver_tinted_netherite_helmet", () ->
            new ArmorBase(STAMaterial.STNA, EquipmentSlotType.HEAD, true));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_CHESTPLATE = ITEMS.register("silver_tinted_netherite_chestplate", () ->
            new ArmorBase(STAMaterial.STNA, EquipmentSlotType.CHEST, true));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_LEGGINGS = ITEMS.register("silver_tinted_netherite_leggings", () ->
            new ArmorBase(STAMaterial.STNA, EquipmentSlotType.LEGS, true));
    public static final RegistryObject<ArmorItem> SILVER_TINTED_NETHERITE_BOOTS = ITEMS.register("silver_tinted_netherite_boots", () ->
            new ArmorBase(STAMaterial.STNA, EquipmentSlotType.FEET, true));

}
