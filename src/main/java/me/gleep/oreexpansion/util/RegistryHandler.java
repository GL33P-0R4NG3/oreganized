package me.gleep.oreexpansion.util;

import me.gleep.oreexpansion.OreExpansion;
import me.gleep.oreexpansion.blocks.BlockItemBase;
import me.gleep.oreexpansion.blocks.SilverBlock;
import me.gleep.oreexpansion.blocks.SilverOre;
import me.gleep.oreexpansion.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OreExpansion.MOD_ID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OreExpansion.MOD_ID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Blocks
    public static final RegistryObject<Block> SILVER_BLOCK = BLOCKS.register("silver_block", SilverBlock::new);
    public static final RegistryObject<Block> SILVER_ORE = BLOCKS.register("silver_ore", SilverOre::new);

    //Block Item
    public static final RegistryObject<Item> SILVER_BLOCK_ITEM = ITEMS.register("silver_block", () -> new BlockItemBase(SILVER_BLOCK.get()));
    public static final RegistryObject<Item> SILVER_ORE_ITEM = ITEMS.register("silver_ore", () -> new BlockItemBase(SILVER_ORE.get()));

    //Items
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", ItemBase::new);
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", ItemBase::new);
}
