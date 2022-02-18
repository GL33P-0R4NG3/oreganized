package me.gleep.oreganized.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.blocks.EngravedBlock;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Datagen {

    @SubscribeEvent
    public static void genData(GatherDataEvent event) {
        if (event.includeServer()) {
            event.getGenerator().addProvider(new LootTableProvider(event.getGenerator()) {

                @Override
                protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>,
                        LootContextParamSet>> getTables() {
                    return ImmutableList.of(Pair.of(ModBlockLoot::new, LootContextParamSets.BLOCK));
                }

                @Override
                protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
                    map.forEach((p_218436_2_, p_218436_3_) -> {
                        LootTables.validate(validationtracker, p_218436_2_, p_218436_3_);
                    });
                }
            });
            //tags
            BlockTagsProvider blockTagsProvider = new BlockTagsProvider(event.getGenerator(), Oreganized.MOD_ID,
                    event.getExistingFileHelper()) {
                @Override
                protected void addTags() {
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(RegistryHandler.RAW_LEAD_BLOCK.get(),
                            RegistryHandler.DEEPSLATE_LEAD_ORE.get(),
                            RegistryHandler.RAW_SILVER_BLOCK.get(),
                            RegistryHandler.DEEPSLATE_SILVER_ORE.get(),
                            RegistryHandler.GLANCE.get(),
                            RegistryHandler.GLANCE_STAIRS.get(),
                            RegistryHandler.GLANCE_SLAB.get(),
                            RegistryHandler.POLISHED_GLANCE.get(),
                            RegistryHandler.CHISELED_GLANCE.get(),
                            RegistryHandler.GLANCE_WALL.get(),
                            RegistryHandler.GLANCE_BRICKS.get(),
                            RegistryHandler.GLANCE_BRICKS_STAIRS.get(),
                            RegistryHandler.GLANCE_BRICKS_SLAB.get(),
                            RegistryHandler.GLANCE_BRICKS_WALL.get(),
                            RegistryHandler.SPOTTED_GLANCE.get(),
                            RegistryHandler.WAXED_SPOTTED_GLANCE.get(),
                            RegistryHandler.SILVER_ORE.get(),
                            RegistryHandler.LEAD_ORE.get(),
                            RegistryHandler.LEAD_BLOCK.get(),
                            RegistryHandler.SILVER_BLOCK.get(),
                            RegistryHandler.TECHNICAL_NETHERITE_BLOCK.get()
                            , RegistryHandler.ENGRAVED_STONE_BRICKS.get(),
                            RegistryHandler.ENGRAVED_POLISHED_BLACKSTONE_BRICKS.get(), RegistryHandler.ENGRAVED_NETHER_BRICKS.get(), RegistryHandler.ENGRAVED_RED_NETHER_BRICKS.get(), RegistryHandler.ENGRAVED_BRICKS.get(), RegistryHandler.ENGRAVED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_EXPOSED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_WEATHERED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_OXIDIZED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_WAXED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_WAXED_EXPOSED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_WAXED_WEATHERED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_WAXED_OXIDIZED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_DEEPSLATE_BRICKS.get(), RegistryHandler.ENGRAVED_END_STONE_BRICKS.get(), RegistryHandler.ENGRAVED_QUARTZ_BRICKS.get(), RegistryHandler.ENGRAVED_PRISMARINE_BRICKS.get(), RegistryHandler.ENGRAVED_GLANCE_BRICKS.get());
                    //	this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                    //			RegistryHandler.RAW_SILVER_BLOCK.get(),
                    //			RegistryHandler.DEEPSLATE_SILVER_ORE.get(),
                    //			RegistryHandler.SILVER_ORE.get(),
                    //			RegistryHandler.SILVER_BLOCK.get(),
                    //			RegistryHandler.TECHNICAL_NETHERITE_BLOCK.get()
                    //	);
                    //	this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                    //			RegistryHandler.RAW_LEAD_BLOCK.get(),
                    //			RegistryHandler.DEEPSLATE_LEAD_ORE.get(),
                    //			RegistryHandler.BLASTED_IRON_BLOCK.get(),
                    //			RegistryHandler.CAST_IRON_BLOCK.get(),
                    //			RegistryHandler.CUT_LEAD_COATING.get(),
                    //			RegistryHandler.LEAD_COATING.get(),
                    //			RegistryHandler.LIGHTENED_IRON_BLOCK.get(),
                    //			RegistryHandler.LEAD_BLOCK.get(),
                    //			RegistryHandler.LEAD_ORE.get()
                    //	);
                    this.tag(BlockTags.SLABS).add(RegistryHandler.GLANCE_SLAB.get(),
                            RegistryHandler.TECHNICAL_NETHERITE_SLAB.get(),
                            RegistryHandler.CUT_TECHNICAL_NETHERITE_SLAB.get(),
                            RegistryHandler.GLANCE_BRICKS_SLAB.get());
                    this.tag(BlockTags.STAIRS).add(RegistryHandler.GLANCE_STAIRS.get(),
                            RegistryHandler.TECHNICAL_NETHERITE_STAIRS.get(),
                            RegistryHandler.CUT_TECHNICAL_NETHERITE_STAIRS.get(),
                            RegistryHandler.GLANCE_BRICKS_STAIRS.get());
                    this.tag(BlockTags.WALLS).add(RegistryHandler.GLANCE_WALL.get(),
                            RegistryHandler.GLANCE_BRICKS_WALL.get());
                    this.tag(RegistryHandler.ENGRAVED_TEXTURED_BLOCKS_BLOCKTAG).add(Blocks.STONE_BRICKS,
                            Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.NETHER_BRICKS, Blocks.RED_NETHER_BRICKS, Blocks.BRICKS, Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER, Blocks.WAXED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER, Blocks.DEEPSLATE_BRICKS, Blocks.END_STONE_BRICKS, Blocks.QUARTZ_BRICKS, Blocks.PRISMARINE_BRICKS, RegistryHandler.GLANCE_BRICKS.get());
                    this.tag(RegistryHandler.ENGRAVEABLE_BLOCKTAG).add(Blocks.STONE, Blocks.STONE_BRICKS,
                            RegistryHandler.ENGRAVED_STONE_BRICKS.get(), Blocks.POLISHED_BLACKSTONE_BRICKS, RegistryHandler.ENGRAVED_POLISHED_BLACKSTONE_BRICKS.get(), Blocks.NETHER_BRICKS, RegistryHandler.ENGRAVED_NETHER_BRICKS.get(), Blocks.RED_NETHER_BRICKS, RegistryHandler.ENGRAVED_RED_NETHER_BRICKS.get(), Blocks.BRICKS, RegistryHandler.ENGRAVED_BRICKS.get(), Blocks.CUT_COPPER, RegistryHandler.ENGRAVED_CUT_COPPER.get(), Blocks.EXPOSED_CUT_COPPER, RegistryHandler.ENGRAVED_EXPOSED_CUT_COPPER.get(), Blocks.WEATHERED_CUT_COPPER, RegistryHandler.ENGRAVED_WEATHERED_CUT_COPPER.get(), Blocks.OXIDIZED_CUT_COPPER, RegistryHandler.ENGRAVED_OXIDIZED_CUT_COPPER.get(), Blocks.WAXED_CUT_COPPER, RegistryHandler.ENGRAVED_WAXED_CUT_COPPER.get(), Blocks.WAXED_EXPOSED_CUT_COPPER, RegistryHandler.ENGRAVED_WAXED_EXPOSED_CUT_COPPER.get(), Blocks.WAXED_WEATHERED_CUT_COPPER, RegistryHandler.ENGRAVED_WAXED_WEATHERED_CUT_COPPER.get(), Blocks.WAXED_OXIDIZED_CUT_COPPER, RegistryHandler.ENGRAVED_WAXED_OXIDIZED_CUT_COPPER.get(), Blocks.DEEPSLATE_BRICKS, RegistryHandler.ENGRAVED_DEEPSLATE_BRICKS.get(), Blocks.END_STONE_BRICKS, RegistryHandler.ENGRAVED_END_STONE_BRICKS.get(), Blocks.QUARTZ_BRICKS, RegistryHandler.ENGRAVED_QUARTZ_BRICKS.get(), Blocks.PRISMARINE_BRICKS, RegistryHandler.ENGRAVED_PRISMARINE_BRICKS.get(), RegistryHandler.GLANCE_BRICKS.get(), RegistryHandler.ENGRAVED_GLANCE_BRICKS.get());
                }
            };
            event.getGenerator().addProvider(blockTagsProvider);
            // Item Tags
            event.getGenerator().addProvider(new ItemTagsProvider(event.getGenerator(), blockTagsProvider,
                    Oreganized.MOD_ID, event.getExistingFileHelper()) {
                @Override
                protected void addTags() {
                    this.tag(ItemTags.SLABS).add(RegistryHandler.GLANCE_SLAB_ITEM.get(),
                            RegistryHandler.TECHNICAL_NETHERITE_SLAB_ITEM.get(),
                            RegistryHandler.CUT_TECHNICAL_NETHERITE_SLAB_ITEM.get(),
                            RegistryHandler.GLANCE_BRICKS_SLAB_ITEM.get());
                    this.tag(ItemTags.STAIRS).add(RegistryHandler.GLANCE_STAIRS_ITEM.get(),
                            RegistryHandler.TECHNICAL_NETHERITE_STAIRS_ITEM.get(),
                            RegistryHandler.CUT_TECHNICAL_NETHERITE_STAIRS_ITEM.get(),
                            RegistryHandler.GLANCE_BRICKS_STAIRS_ITEM.get());
                    this.tag(ItemTags.WALLS).add(RegistryHandler.GLANCE_WALL_ITEM.get(),
                            RegistryHandler.GLANCE_BRICKS_WALL_ITEM.get());
                }
            });

            //blockstate
            event.getGenerator().addProvider(new BlockStateProvider(event.getGenerator(), Oreganized.MOD_ID,
                    event.getExistingFileHelper()) {
                @Override
                protected void registerStatesAndModels() {
                    //simpleBlock(RegistryHandler.LEAD_RAW_BLOCK.get());
                    //simpleBlock(RegistryHandler.SILVER_RAW_BLOCK.get());
                    //simpleBlock(RegistryHandler.DEEPSLATE_LEAD_ORE.get());
                    //simpleBlock(RegistryHandler.DEEPSLATE_SILVER_ORE.get());
                    //simpleBlock(RegistryHandler.GLANCE.get());
                    //simpleBlock(RegistryHandler.SPOTTED_GLANCE.get());
                    simpleBlock(RegistryHandler.POLISHED_GLANCE.get());
                    simpleBlock(RegistryHandler.GLANCE_BRICKS.get());
                    simpleBlock(RegistryHandler.CHISELED_GLANCE.get());
                    simpleBlock(RegistryHandler.SHRAPNEL_BOMB.get(), cubeBottomTop(RegistryHandler.SHRAPNEL_BOMB.get()));
                    simpleBlock(RegistryHandler.WAXED_BLACK_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.BLACK_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_BLUE_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.BLUE_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_BROWN_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.BROWN_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_CYAN_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.CYAN_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_GRAY_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.GRAY_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_GREEN_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.GREEN_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_LIGHT_BLUE_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.LIGHT_BLUE_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_LIGHT_GRAY_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.LIGHT_GRAY_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_LIME_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.LIME_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_MAGENTA_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.MAGENTA_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_ORANGE_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.ORANGE_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_PINK_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.PINK_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_PURPLE_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.PURPLE_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_RED_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.RED_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_WHITE_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.WHITE_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_YELLOW_CONCRETE_POWDER.get(),
                            cubeAll(Blocks.YELLOW_CONCRETE_POWDER));
                    simpleBlock(RegistryHandler.WAXED_SPOTTED_GLANCE.get(),
                            cubeAll(RegistryHandler.SPOTTED_GLANCE.get()));
                    slabBlock(RegistryHandler.GLANCE_SLAB.get(), new ResourceLocation(Oreganized.MOD_ID,
                            "block/" + RegistryHandler.GLANCE.get().getRegistryName().getPath()), new ResourceLocation(Oreganized.MOD_ID, "block/" + RegistryHandler.GLANCE.get().getRegistryName().getPath()));
                    stairsBlock(RegistryHandler.GLANCE_STAIRS.get(), new ResourceLocation(Oreganized.MOD_ID,
                            "block/" + RegistryHandler.GLANCE.get().getRegistryName().getPath()));
                    slabBlock(RegistryHandler.GLANCE_BRICKS_SLAB.get(), new ResourceLocation(Oreganized.MOD_ID,
                            "block/" + RegistryHandler.GLANCE_BRICKS.get().getRegistryName().getPath()), new ResourceLocation(Oreganized.MOD_ID, "block/" + RegistryHandler.GLANCE_BRICKS.get().getRegistryName().getPath()));
                    stairsBlock(RegistryHandler.GLANCE_BRICKS_STAIRS.get(),
                            new ResourceLocation(Oreganized.MOD_ID,
                                    "block/" + RegistryHandler.GLANCE_BRICKS.get().getRegistryName().getPath()));
                    wallBlock(RegistryHandler.GLANCE_WALL.get(), new ResourceLocation(Oreganized.MOD_ID,
                            "block/" + RegistryHandler.GLANCE.get().getRegistryName().getPath()));
                    wallBlock(RegistryHandler.GLANCE_BRICKS_WALL.get(), new ResourceLocation(Oreganized.MOD_ID,
                            "block/" + RegistryHandler.GLANCE_BRICKS.get().getRegistryName().getPath()));
                    engravedBlock(RegistryHandler.ENGRAVED_NETHER_BRICKS.get(),
                            cubeAll(RegistryHandler.ENGRAVED_NETHER_BRICKS.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_NETHER_BRICKS.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_RED_NETHER_BRICKS.get(),
                            cubeAll(RegistryHandler.ENGRAVED_RED_NETHER_BRICKS.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_RED_NETHER_BRICKS.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_STONE_BRICKS.get(),
                            cubeAll(RegistryHandler.ENGRAVED_STONE_BRICKS.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_STONE_BRICKS.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_DEEPSLATE_BRICKS.get(),
                            cubeAll(RegistryHandler.ENGRAVED_DEEPSLATE_BRICKS.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_DEEPSLATE_BRICKS.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_END_STONE_BRICKS.get(),
                            cubeAll(RegistryHandler.ENGRAVED_END_STONE_BRICKS.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_END_STONE_BRICKS.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_POLISHED_BLACKSTONE_BRICKS.get(),
                            cubeAll(RegistryHandler.ENGRAVED_POLISHED_BLACKSTONE_BRICKS.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_POLISHED_BLACKSTONE_BRICKS.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_PRISMARINE_BRICKS.get(),
                            cubeAll(RegistryHandler.ENGRAVED_PRISMARINE_BRICKS.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_PRISMARINE_BRICKS.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_QUARTZ_BRICKS.get(),
                            cubeAll(RegistryHandler.ENGRAVED_QUARTZ_BRICKS.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_DEEPSLATE_BRICKS.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_CUT_COPPER.get(),
                            cubeAll(RegistryHandler.ENGRAVED_CUT_COPPER.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_CUT_COPPER.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_EXPOSED_CUT_COPPER.get(),
                            cubeAll(RegistryHandler.ENGRAVED_EXPOSED_CUT_COPPER.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_EXPOSED_CUT_COPPER.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_WEATHERED_CUT_COPPER.get(),
                            cubeAll(RegistryHandler.ENGRAVED_WEATHERED_CUT_COPPER.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_WEATHERED_CUT_COPPER.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_OXIDIZED_CUT_COPPER.get(),
                            cubeAll(RegistryHandler.ENGRAVED_OXIDIZED_CUT_COPPER.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_OXIDIZED_CUT_COPPER.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_WAXED_CUT_COPPER.get(),
                            cubeAll(RegistryHandler.ENGRAVED_CUT_COPPER.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_CUT_COPPER.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_WAXED_EXPOSED_CUT_COPPER.get(),
                            cubeAll(RegistryHandler.ENGRAVED_EXPOSED_CUT_COPPER.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_EXPOSED_CUT_COPPER.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_WAXED_WEATHERED_CUT_COPPER.get(),
                            cubeAll(RegistryHandler.ENGRAVED_WEATHERED_CUT_COPPER.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_WEATHERED_CUT_COPPER.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_WAXED_OXIDIZED_CUT_COPPER.get(),
                            cubeAll(RegistryHandler.ENGRAVED_OXIDIZED_CUT_COPPER.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_OXIDIZED_CUT_COPPER.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_BRICKS.get(),
                            cubeAll(RegistryHandler.ENGRAVED_BRICKS.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_BRICKS.get()));
                    engravedBlock(RegistryHandler.ENGRAVED_GLANCE_BRICKS.get(),
                            cubeAll(RegistryHandler.ENGRAVED_GLANCE_BRICKS.get()),
                            cubeRotatedBottomTop(RegistryHandler.ENGRAVED_GLANCE_BRICKS.get()));
                }

                private ModelFile cubeRotatedBottomTop(Block block) {
                    BlockModelBuilder model = models().getBuilder(block.getRegistryName().getPath() +
                            "_rotated_bottom");
                    model.parent(models().getExistingFile(new ResourceLocation(Oreganized.MOD_ID, "block" +
                            "/cube_rotated_bottom_top")));
                    model.texture("top", new ResourceLocation(Oreganized.MOD_ID,
                            "block/" + block.getRegistryName().getPath()));
                    model.texture("bottom", new ResourceLocation(Oreganized.MOD_ID,
                            "block/" + block.getRegistryName().getPath()));
                    model.texture("side", new ResourceLocation(Oreganized.MOD_ID,
                            "block/" + block.getRegistryName().getPath()));
                    return model;
                }

                private ModelFile cubeBottomTop(Block block) {
                    BlockModelBuilder model = models().getBuilder(block.getRegistryName().getPath() );
                    model.parent(models().getExistingFile(new ResourceLocation("minecraft", "block" +
                            "/cube_bottom_top")));
                    model.texture("top", new ResourceLocation(Oreganized.MOD_ID,
                            "block/" + block.getRegistryName().getPath() + "_top"));
                    model.texture("bottom", new ResourceLocation(Oreganized.MOD_ID,
                            "block/" + block.getRegistryName().getPath() + "_bottom"));
                    model.texture("side", new ResourceLocation(Oreganized.MOD_ID,
                            "block/" + block.getRegistryName().getPath() + "_side"));
                    return model;
                }

                private void engravedBlock(Block block, ModelFile mainModel, ModelFile rotatedBottomModel) {
                    getVariantBuilder(block).partialState().with(EngravedBlock.ISZAXISUP, true)
                            .with(EngravedBlock.ISZAXISDOWN, true).modelForState().modelFile(mainModel)
                            .addModel().partialState().with(EngravedBlock.ISZAXISUP, false)
                            .with(EngravedBlock.ISZAXISDOWN, false).modelForState().modelFile(mainModel)
                            .rotationY(90).addModel().partialState().with(EngravedBlock.ISZAXISUP, true)
                            .with(EngravedBlock.ISZAXISDOWN, false).modelForState().modelFile(rotatedBottomModel)
                            .addModel().partialState().with(EngravedBlock.ISZAXISUP, false).with(EngravedBlock.ISZAXISDOWN, true)
                            .modelForState().modelFile(rotatedBottomModel).rotationY(90).addModel();
                }

            });
            //items
            event.getGenerator().addProvider(new ItemModelProvider(event.getGenerator(), Oreganized.MOD_ID,
                    event.getExistingFileHelper()) {
                @Override
                protected void registerModels() {
                    //withExistingParent(RegistryHandler.DEEPSLATE_LEAD_ORE_ITEM.get().getRegistryName().getPath(),
                    // new ResourceLocation(Oreganized.MOD_ID,"block/deepslate_lead_ore"));
                    //withExistingParent(RegistryHandler.DEEPSLATE_SILVER_ORE_ITEM.get().getRegistryName().getPath(),
                    // new ResourceLocation(Oreganized.MOD_ID,"block/deepslate_silver_ore"));
                    //withExistingParent(RegistryHandler.GLANCE_ITEM.get().getRegistryName().getPath(), new
                    // ResourceLocation(Oreganized.MOD_ID,"block/glance"));
                    //withExistingParent(RegistryHandler.SPOTTED_GLANCE_ITEM.get().getRegistryName().getPath(), new
                    // ResourceLocation(Oreganized.MOD_ID,"block/spotted_glance"));
                    withExistingParent(RegistryHandler.SHRAPNEL_BOMB_ITEM.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/shrapnel_bomb"));
                    withExistingParent(RegistryHandler.POLISHED_GLANCE_ITEM.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/polished_glance"));
                    withExistingParent(RegistryHandler.GLANCE_BRICKS_ITEM.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/glance_bricks"));
                    withExistingParent(RegistryHandler.CHISELED_GLANCE_ITEM.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/chiseled_glance"));
                    withExistingParent(RegistryHandler.GLANCE_SLAB_ITEM.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/glance_slab"));
                    withExistingParent(RegistryHandler.GLANCE_STAIRS_ITEM.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/glance_stairs"));
                    withExistingParent(RegistryHandler.GLANCE_BRICKS_SLAB_ITEM.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/glance_bricks_slab"));
                    withExistingParent(RegistryHandler.GLANCE_BRICKS_STAIRS_ITEM.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/glance_bricks_stairs"));
                    wallInventory(RegistryHandler.GLANCE_WALL_ITEM.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/glance"));
                    wallInventory(RegistryHandler.GLANCE_BRICKS_WALL_ITEM.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/glance_bricks"));
                    withExistingParent(RegistryHandler.WAXED_WHITE_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/white_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_BLACK_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/black_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_BLUE_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/blue_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_BROWN_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/brown_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_CYAN_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/cyan_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_GRAY_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/gray_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_GREEN_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/green_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_LIGHT_BLUE_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/light_blue_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_LIGHT_GRAY_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/light_gray_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_LIME_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/lime_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_MAGENTA_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/magenta_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_ORANGE_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/orange_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_PINK_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/pink_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_PURPLE_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/purple_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_RED_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/red_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_YELLOW_CONCRETE_POWDER.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/yellow_concrete_powder"));
                    withExistingParent(RegistryHandler.WAXED_SPOTTED_GLANCE.get().getRegistryName().getPath(),
                            new ResourceLocation(Oreganized.MOD_ID, "block/spotted_glance"));
                }
            });
            //recipes
            //event.getGenerator().addProvider(new ModRecipeProvider(event.getGenerator()){
            //	@Override
            //	protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
            //		nineBlockStorageRecipes(pFinishedRecipeConsumer, RegistryHandler.RAW_LEAD.get(), RegistryHandler
            //		.RAW_LEAD_BLOCK.get());
            //		nineBlockStorageRecipes(pFinishedRecipeConsumer, RegistryHandler.RAW_SILVER.get(), RegistryHandler
            //		.RAW_SILVER_BLOCK.get());
            //
            //		nineBlockStorageRecipes(pFinishedRecipeConsumer, RegistryHandler.LEAD_INGOT.get(), RegistryHandler
            //		.LEAD_BLOCK.get());
            //
            //		oreSmelting(pFinishedRecipeConsumer, ImmutableList.of(RegistryHandler.RAW_LEAD.get(),
            //		RegistryHandler.LEAD_ORE.get(),RegistryHandler.DEEPSLATE_LEAD_ORE.get()),RegistryHandler
            //		.LEAD_INGOT.get(),1F,200,"lead_ingot");
            //		oreBlasting(pFinishedRecipeConsumer, ImmutableList.of(RegistryHandler.RAW_LEAD.get(),
            //		RegistryHandler.LEAD_ORE.get(),RegistryHandler.DEEPSLATE_LEAD_ORE.get()),RegistryHandler
            //		.LEAD_INGOT.get(),1F,100,"lead_ingot");
            //		oreSmelting(pFinishedRecipeConsumer, ImmutableList.of(RegistryHandler.RAW_SILVER.get(),
            //		RegistryHandler.SILVER_ORE.get(),RegistryHandler.DEEPSLATE_SILVER_ORE.get()),RegistryHandler
            //		.SILVER_INGOT.get(),1F,200,"silver_ingot");
            //		oreBlasting(pFinishedRecipeConsumer, ImmutableList.of(RegistryHandler.RAW_SILVER.get(),
            //		RegistryHandler.SILVER_ORE.get(),RegistryHandler.DEEPSLATE_SILVER_ORE.get()),RegistryHandler
            //		.SILVER_INGOT.get(),1F,100,"silver_ingot");
            //
            //
            //	}
            //});

        }
    }
	
	/*public static class ModBlockLoot extends BlockLoot {
		@Override
		protected void addTables() {
			this.add(RegistryHandler.DEEPSLATE_LEAD_ORE.get(), (i)->{
				return createOreDrop(i, RegistryHandler.RAW_LEAD.get());
			});
			this.add(RegistryHandler.DEEPSLATE_SILVER_ORE.get(), (i)->{
				return createOreDrop(i, RegistryHandler.RAW_SILVER.get());
			});
			this.add(RegistryHandler.LEAD_ORE.get(), (i)->{
				return createOreDrop(i, RegistryHandler.RAW_LEAD.get());
			});
			this.add(RegistryHandler.SILVER_ORE.get(), (i)->{
				return createOreDrop(i, RegistryHandler.RAW_SILVER.get());
			});
			this.dropSelf(RegistryHandler.GLANCE.get());
			this.dropSelf(RegistryHandler.SPOTTED_GLANCE.get());
			this.dropSelf(RegistryHandler.RAW_LEAD_BLOCK.get());
			this.dropSelf(RegistryHandler.RAW_SILVER_BLOCK.get());
		}
		
		@Override
		protected Iterable<Block> getKnownBlocks() {
			return List.of(RegistryHandler.GLANCE.get(),RegistryHandler.SPOTTED_GLANCE.get(),RegistryHandler
			.DEEPSLATE_LEAD_ORE.get(),RegistryHandler.DEEPSLATE_SILVER_ORE.get(),
					RegistryHandler.RAW_LEAD_BLOCK.get(),RegistryHandler.RAW_SILVER_BLOCK.get(),RegistryHandler
					.LEAD_ORE.get(),RegistryHandler.SILVER_ORE.get());
		}
	}*/

    public static class ModBlockLoot extends BlockLoot {
        private static final List<Block> BLOCKS =
                new ArrayList<>(List.of(RegistryHandler.GLANCE_BRICKS.get(),
                        RegistryHandler.POLISHED_GLANCE.get(),
                        RegistryHandler.CHISELED_GLANCE.get(),
                        RegistryHandler.GLANCE_STAIRS.get(),
                        RegistryHandler.GLANCE_SLAB.get(),
                        RegistryHandler.GLANCE_BRICKS_STAIRS.get(),
                        RegistryHandler.GLANCE_BRICKS_SLAB.get(),
                        RegistryHandler.GLANCE_WALL.get(),
                        RegistryHandler.GLANCE_BRICKS_WALL.get(),
                        RegistryHandler.WAXED_WHITE_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_ORANGE_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_MAGENTA_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_LIGHT_BLUE_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_YELLOW_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_LIME_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_PINK_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_GRAY_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_LIGHT_GRAY_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_CYAN_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_PURPLE_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_BLUE_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_BROWN_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_GREEN_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_RED_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_BLACK_CONCRETE_POWDER.get(),
                        RegistryHandler.WAXED_SPOTTED_GLANCE.get(),
                        RegistryHandler.SHRAPNEL_BOMB.get()));
        private static final Map<Block, Block> ENGRAVED_BLOCKS = new HashMap();

        private void setEngravedBlocks(Block... blocks) {
            Block engravedBlock = null;

            int i = 1;
            for (Block block : blocks) {
                if (!(i % 2 == 0)) {
                    engravedBlock = block;
                    BLOCKS.add(engravedBlock);
                } else ENGRAVED_BLOCKS.put(engravedBlock, block);
                ++i;
            }

        }

        @Override
        protected void addTables() {
            setEngravedBlocks(RegistryHandler.ENGRAVED_STONE_BRICKS.get(), Blocks.STONE_BRICKS,
                    RegistryHandler.ENGRAVED_POLISHED_BLACKSTONE_BRICKS.get(),
                    Blocks.POLISHED_BLACKSTONE_BRICKS, RegistryHandler.ENGRAVED_NETHER_BRICKS.get(),
                    Blocks.NETHER_BRICKS, RegistryHandler.ENGRAVED_RED_NETHER_BRICKS.get(),
                    Blocks.RED_NETHER_BRICKS, RegistryHandler.ENGRAVED_BRICKS.get(), Blocks.BRICKS,
                    RegistryHandler.ENGRAVED_CUT_COPPER.get(), Blocks.CUT_COPPER,
                    RegistryHandler.ENGRAVED_EXPOSED_CUT_COPPER.get(), Blocks.EXPOSED_CUT_COPPER,
                    RegistryHandler.ENGRAVED_WEATHERED_CUT_COPPER.get(), Blocks.WEATHERED_CUT_COPPER,
                    RegistryHandler.ENGRAVED_OXIDIZED_CUT_COPPER.get(), Blocks.OXIDIZED_CUT_COPPER,
                    RegistryHandler.ENGRAVED_WAXED_CUT_COPPER.get(), Blocks.WAXED_CUT_COPPER,
                    RegistryHandler.ENGRAVED_WAXED_EXPOSED_CUT_COPPER.get(),
                    Blocks.WAXED_EXPOSED_CUT_COPPER,
                    RegistryHandler.ENGRAVED_WAXED_WEATHERED_CUT_COPPER.get(),
                    Blocks.WAXED_WEATHERED_CUT_COPPER,
                    RegistryHandler.ENGRAVED_WAXED_OXIDIZED_CUT_COPPER.get(),
                    Blocks.WAXED_OXIDIZED_CUT_COPPER, RegistryHandler.ENGRAVED_DEEPSLATE_BRICKS.get(),
                    Blocks.DEEPSLATE_BRICKS, RegistryHandler.ENGRAVED_END_STONE_BRICKS.get(),
                    Blocks.END_STONE_BRICKS, RegistryHandler.ENGRAVED_QUARTZ_BRICKS.get(),
                    Blocks.QUARTZ_BRICKS, RegistryHandler.ENGRAVED_PRISMARINE_BRICKS.get(),
                    Blocks.PRISMARINE_BRICKS, RegistryHandler.ENGRAVED_GLANCE_BRICKS.get(),
                    RegistryHandler.GLANCE_BRICKS.get(),
                    RegistryHandler.WAXED_SPOTTED_GLANCE.get());
            BLOCKS.forEach(this::dropSelf);
            ENGRAVED_BLOCKS.forEach(this::dropOther);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BLOCKS;
        }
    }

    public static class ModRecipeProvider extends RecipeProvider {

        public ModRecipeProvider(DataGenerator pGenerator) {
            super(pGenerator);
        }

        public static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                       List<ItemLike> pIngredients, ItemLike pResult, float pExperience,
                                       int pCookingTime, String pRecipeName) {
            oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pResult,
                    pExperience, pCookingTime, pRecipeName, "_from_smelting");
        }

        public static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                       List<ItemLike> pIngredients, ItemLike pResult, float pExperience,
                                       int pCookingTime, String pRecipeName) {
            oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pResult,
                    pExperience, pCookingTime, pRecipeName, "_from_blasting");
        }

        private static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, SimpleCookingSerializer<
                ?> pCookingSerializer, List<ItemLike> pIngredients, ItemLike pResult, float pExperience,
                                       int pCookingTime, String pGroup, String pRecipeName) {
            for (ItemLike itemlike : pIngredients) {
                SimpleCookingRecipeBuilder.cooking(Ingredient.of(itemlike), pResult, pExperience, pCookingTime
                        , pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer, getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
            }

        }

        public static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                                   ItemLike pUnpacked, ItemLike pPacked) {
            nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpacked, pPacked, getSimpleRecipeName(pPacked),
                    (String) null, getSimpleRecipeName(pUnpacked), (String) null);
        }

        public static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                                   ItemLike pUnpacked, ItemLike pPacked,
                                                   String pPackingRecipeName, @Nullable String pPackingRecipeGroup
                , String pUnpackingRecipeName, @Nullable String pUnpackingRecipeGroup) {
            ShapelessRecipeBuilder.shapeless(pUnpacked, 9).requires(pPacked).group(pUnpackingRecipeGroup).unlockedBy(getHasName(pPacked), has(pPacked)).save(pFinishedRecipeConsumer, new ResourceLocation(pUnpackingRecipeName));
            ShapedRecipeBuilder.shaped(pPacked).define('#', pUnpacked).pattern("###").pattern("###").pattern("###").group(pPackingRecipeGroup).unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pFinishedRecipeConsumer, new ResourceLocation(pPackingRecipeName));
        }

        public static String getHasName(ItemLike pItemLike) {
            return "has_" + getItemName(pItemLike);
        }

        public static String getItemName(ItemLike pItemLike) {
            return Registry.ITEM.getKey(pItemLike.asItem()).getPath();
        }

        public static String getSimpleRecipeName(ItemLike pItemLike) {
            return getItemName(pItemLike);
        }

    }

}
