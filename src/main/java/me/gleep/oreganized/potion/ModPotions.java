package me.gleep.oreganized.potion;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.capabilities.stunning.CapabilityStunning;
import me.gleep.oreganized.capabilities.stunning.IStunning;
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

    public static MobEffect STUNNED = new MobEffect( MobEffectCategory.HARMFUL , 0x3B3B63 ){
        @Override
        public boolean isDurationEffectTick( int pDuration , int pAmplifier ){
            int k = 300;
            return pDuration % k == 0;
        }

        @Override
        public void applyEffectTick( LivingEntity pLivingEntity , int pAmplifier ){
            Level level = pLivingEntity.level;
            IStunning stunningCap = pLivingEntity.getCapability(CapabilityStunning.STUNNING_CAPABILITY, null ).orElse(null);
            if(stunningCap != null){
                stunningCap.setPreviousPos( pLivingEntity.blockPosition() );
                stunningCap.setRemainingStunTime((int) Math.floor((Math.max( 5, Math.floor(Math.random() * 12) ) * 20) * ((3f/4f)*(pAmplifier+1))));
            }
            super.applyEffectTick( pLivingEntity, pAmplifier );
        }
    };

    public static Potion STUNNING_POTION = new Potion( "stunning_potion" , new MobEffectInstance( STUNNED , 40 * 20 ) );
    public static Potion STUNNING_POTION_LONG = new Potion( "stunning_potion_long" , new MobEffectInstance( STUNNED , 80 * 20 ) );
    public static Potion STUNNING_POTION_POTENT = new Potion( "stunning_potion_potent" , new MobEffectInstance( STUNNED , 40 * 20 , 1 ) );


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

        BrewingRecipeRegistry.addRecipe( new IBrewingRecipe(){
            @Override
            public boolean isInput( ItemStack input ){
                return PotionUtils.getPotion( input ) == Potions.WATER;
            }

            @Override
            public boolean isIngredient( ItemStack ingredient ){
                return ingredient.is(LEAD_INGOTS_ITEMTAG);
            }

            @Override
            public ItemStack getOutput( ItemStack input , ItemStack ingredient ){
                if(PotionUtils.getPotion( input ) == Potions.WATER && ingredient.is(LEAD_INGOTS_ITEMTAG) ){
                    ItemStack out = new ItemStack( Items.POTION );
                    PotionUtils.setPotion( out , STUNNING_POTION );
                    return out;
                }else return ItemStack.EMPTY;
            }
        } );

        BrewingRecipeRegistry.addRecipe( new IBrewingRecipe(){
            @Override
            public boolean isInput( ItemStack input ){
                return PotionUtils.getPotion( input ) == STUNNING_POTION;
            }

            @Override
            public boolean isIngredient( ItemStack ingredient ){
                return ingredient.getItem() == Items.REDSTONE;
            }

            @Override
            public ItemStack getOutput( ItemStack input , ItemStack ingredient ){
                if(PotionUtils.getPotion( input ) == STUNNING_POTION && ingredient.getItem() == Items.REDSTONE){
                    ItemStack out = new ItemStack( Items.POTION );
                    PotionUtils.setPotion( out , STUNNING_POTION_LONG );
                    return out;
                }else return ItemStack.EMPTY;
            }
        } );

        BrewingRecipeRegistry.addRecipe( new IBrewingRecipe(){
            @Override
            public boolean isInput( ItemStack input ){
                return PotionUtils.getPotion( input ) == STUNNING_POTION;
            }

            @Override
            public boolean isIngredient( ItemStack ingredient ){
                return ingredient.getItem() == Items.GLOWSTONE_DUST;
            }

            @Override
            public ItemStack getOutput( ItemStack input , ItemStack ingredient ){
                if(PotionUtils.getPotion( input ) == STUNNING_POTION && ingredient.getItem() == Items.GLOWSTONE_DUST){
                    ItemStack out = new ItemStack( Items.POTION );
                    PotionUtils.setPotion( out , STUNNING_POTION_POTENT );
                    return out;
                }else return ItemStack.EMPTY;
            }
        } );
    }


}
