package me.gleep.oreganized.potion;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.capabilities.stunning.CapabilityStunning;
import me.gleep.oreganized.capabilities.stunning.IStunning;
import me.gleep.oreganized.effect.StunnedEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static me.gleep.oreganized.util.RegistryHandler.LEAD_INGOTS_ITEMTAG;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModPotions{

    public static MobEffect STUNNED = new StunnedEffect(MobEffectCategory.HARMFUL, 0x3B3B63);

    public static Potion STUNNING_POTION = new Potion( "stunning" , new MobEffectInstance( STUNNED , 40 * 20 ) );
    public static Potion STUNNING_POTION_LONG = new Potion( "stunning" , new MobEffectInstance( STUNNED , 80 * 20 ) );
    public static Potion STUNNING_POTION_POTENT = new Potion( "stunning" , new MobEffectInstance( STUNNED , 40 * 20 , 1 ) );


    @SubscribeEvent
    public static void registerEffects( RegistryEvent.Register <MobEffect> event ) {
        event.getRegistry().registerAll( STUNNED.setRegistryName( "stunned" ) );
    }

    @SubscribeEvent
    public static void registerPotions( RegistryEvent.Register <Potion> event ){
        event.getRegistry().registerAll(
                STUNNING_POTION.setRegistryName( "stunning_potion" ) ,
                STUNNING_POTION_LONG.setRegistryName( "stunning_potion_long" ) ,
                STUNNING_POTION_POTENT.setRegistryName( "stunning_potion_potent" )
        );
    }


}
