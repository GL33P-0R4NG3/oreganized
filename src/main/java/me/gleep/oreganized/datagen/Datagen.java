package me.gleep.oreganized.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class Datagen {
	
	@SubscribeEvent
	public static void genData(GatherDataEvent event){
		if(event.includeServer()) {
			event.getGenerator().addProvider(new LootTableProvider(event.getGenerator()) {
				@Override
				protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
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
			event.getGenerator().addProvider(new BlockTagsProvider(event.getGenerator(),Oreganized.MOD_ID, event.getExistingFileHelper()){
				@Override
				protected void addTags() {
					this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
							RegistryHandler.RAW_LEAD_BLOCK.get(),
							RegistryHandler.DEEPSLATE_LEAD_ORE.get(),
							RegistryHandler.RAW_SILVER_BLOCK.get(),
							RegistryHandler.DEEPSLATE_SILVER_ORE.get(),
							RegistryHandler.GLANCE.get(),
							RegistryHandler.SPOTTED_GLANCE.get(),
							RegistryHandler.SILVER_ORE.get(),
							RegistryHandler.LEAD_ORE.get(),
							RegistryHandler.BLASTED_IRON_BLOCK.get(),
							RegistryHandler.CAST_IRON_BLOCK.get(),
							RegistryHandler.CUT_LEAD_COATING.get(),
							RegistryHandler.LEAD_COATING.get(),
							RegistryHandler.LEAD_BLOCK.get(),
							RegistryHandler.LIGHTENED_IRON_BLOCK.get(),
							RegistryHandler.SILVER_BLOCK.get(),
							RegistryHandler.TECHNICAL_NETHERITE_BLOCK.get()
					);
					this.tag(BlockTags.NEEDS_IRON_TOOL).add(
							RegistryHandler.RAW_SILVER_BLOCK.get(),
							RegistryHandler.DEEPSLATE_SILVER_ORE.get(),
							RegistryHandler.SILVER_ORE.get(),
							RegistryHandler.SILVER_BLOCK.get(),
							RegistryHandler.TECHNICAL_NETHERITE_BLOCK.get()
					);
					this.tag(BlockTags.NEEDS_STONE_TOOL).add(
							RegistryHandler.RAW_LEAD_BLOCK.get(),
							RegistryHandler.DEEPSLATE_LEAD_ORE.get(),
							RegistryHandler.BLASTED_IRON_BLOCK.get(),
							RegistryHandler.CAST_IRON_BLOCK.get(),
							RegistryHandler.CUT_LEAD_COATING.get(),
							RegistryHandler.LEAD_COATING.get(),
							RegistryHandler.LIGHTENED_IRON_BLOCK.get(),
							RegistryHandler.LEAD_BLOCK.get(),
							RegistryHandler.LEAD_ORE.get()
					);
				}
			});
			//blockstate
			event.getGenerator().addProvider(new BlockStateProvider(event.getGenerator(),Oreganized.MOD_ID, event.getExistingFileHelper()) {
				@Override
				protected void registerStatesAndModels() {
					//simpleBlock(RegistryHandler.LEAD_RAW_BLOCK.get());
					//simpleBlock(RegistryHandler.SILVER_RAW_BLOCK.get());
					simpleBlock(RegistryHandler.DEEPSLATE_LEAD_ORE.get());
					simpleBlock(RegistryHandler.DEEPSLATE_SILVER_ORE.get());
					simpleBlock(RegistryHandler.GLANCE.get());
					simpleBlock(RegistryHandler.SPOTTED_GLANCE.get());
				}
			});
			//items
			event.getGenerator().addProvider(new ItemModelProvider(event.getGenerator(), Oreganized.MOD_ID, event.getExistingFileHelper()) {
				@Override
				protected void registerModels() {
					withExistingParent(RegistryHandler.DEEPSLATE_LEAD_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Oreganized.MOD_ID,"block/deepslate_lead_ore"));
					withExistingParent(RegistryHandler.DEEPSLATE_SILVER_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Oreganized.MOD_ID,"block/deepslate_silver_ore"));
					withExistingParent(RegistryHandler.GLANCE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Oreganized.MOD_ID,"block/glance"));
					withExistingParent(RegistryHandler.SPOTTED_GLANCE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Oreganized.MOD_ID,"block/spotted_glance"));
				}
			});
			//recipes
			event.getGenerator().addProvider(new ModRecipeProvider(event.getGenerator()){
				@Override
				protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
					nineBlockStorageRecipes(pFinishedRecipeConsumer, RegistryHandler.RAW_LEAD.get(), RegistryHandler.RAW_LEAD_BLOCK.get());
					nineBlockStorageRecipes(pFinishedRecipeConsumer, RegistryHandler.RAW_SILVER.get(), RegistryHandler.RAW_SILVER_BLOCK.get());
					
					nineBlockStorageRecipes(pFinishedRecipeConsumer, RegistryHandler.LEAD_INGOT.get(), RegistryHandler.LEAD_BLOCK.get());
					
					oreSmelting(pFinishedRecipeConsumer, ImmutableList.of(RegistryHandler.RAW_LEAD.get(),RegistryHandler.LEAD_ORE.get(),RegistryHandler.DEEPSLATE_LEAD_ORE.get()),RegistryHandler.LEAD_INGOT.get(),1F,200,"lead_ingot");
					oreBlasting(pFinishedRecipeConsumer, ImmutableList.of(RegistryHandler.RAW_LEAD.get(),RegistryHandler.LEAD_ORE.get(),RegistryHandler.DEEPSLATE_LEAD_ORE.get()),RegistryHandler.LEAD_INGOT.get(),1F,100,"lead_ingot");
					oreSmelting(pFinishedRecipeConsumer, ImmutableList.of(RegistryHandler.RAW_SILVER.get(),RegistryHandler.SILVER_ORE.get(),RegistryHandler.DEEPSLATE_SILVER_ORE.get()),RegistryHandler.SILVER_INGOT.get(),1F,200,"silver_ingot");
					oreBlasting(pFinishedRecipeConsumer, ImmutableList.of(RegistryHandler.RAW_SILVER.get(),RegistryHandler.SILVER_ORE.get(),RegistryHandler.DEEPSLATE_SILVER_ORE.get()),RegistryHandler.SILVER_INGOT.get(),1F,100,"silver_ingot");
					
					
				}
			});
			
		}
	}
	
	public static class ModBlockLoot extends BlockLoot {
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
			return List.of(RegistryHandler.GLANCE.get(),RegistryHandler.SPOTTED_GLANCE.get(),RegistryHandler.DEEPSLATE_LEAD_ORE.get(),RegistryHandler.DEEPSLATE_SILVER_ORE.get(),
					RegistryHandler.RAW_LEAD_BLOCK.get(),RegistryHandler.RAW_SILVER_BLOCK.get(),RegistryHandler.LEAD_ORE.get(),RegistryHandler.SILVER_ORE.get());
		}
	}
	
	public static class ModRecipeProvider extends RecipeProvider {
		
		public ModRecipeProvider(DataGenerator pGenerator) {
			super(pGenerator);
		}
		
		public static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, ItemLike pResult, float pExperience, int pCookingTime, String pRecipeName) {
			oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pResult, pExperience, pCookingTime, pRecipeName, "_from_smelting");
		}
		
		public static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, ItemLike pResult, float pExperience, int pCookingTime, String pRecipeName) {
			oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pResult, pExperience, pCookingTime, pRecipeName, "_from_blasting");
		}
		
		private static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, SimpleCookingSerializer<?> pCookingSerializer, List<ItemLike> pIngredients, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
			for(ItemLike itemlike : pIngredients) {
				SimpleCookingRecipeBuilder.cooking(Ingredient.of(itemlike), pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer, getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
			}
			
		}
		
		public static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked) {
			nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpacked, pPacked, getSimpleRecipeName(pPacked), (String)null, getSimpleRecipeName(pUnpacked), (String)null);
		}
		
		public static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked, String pPackingRecipeName, @Nullable String pPackingRecipeGroup, String pUnpackingRecipeName, @Nullable String pUnpackingRecipeGroup) {
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
