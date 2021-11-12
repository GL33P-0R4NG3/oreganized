package me.gleep.oreganized.util;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.potion.ModPotions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
	
	@SubscribeEvent
	public static void applyLeadEffect(LivingEntityUseItemEvent.Finish event){
		if(event.getItem().getItem().isEdible()){
			if(event.getEntityLiving() instanceof Player player){
				for(int i=0;i<9;i++){
					if(ItemTags.getAllTags().getTag(new ResourceLocation("forge","ingots/lead")).contains(player.getInventory().items.get(i).getItem())){
						player.addEffect(new MobEffectInstance(ModPotions.STUNNING,40*20));
						return;
					}
				}
				if(ItemTags.getAllTags().getTag(new ResourceLocation("forge","ingots/lead")).contains(player.getInventory().offhand.get(0).getItem())){
					player.addEffect(new MobEffectInstance(ModPotions.STUNNING,40*20));
				}
			}
		}
	}
	
	
}
