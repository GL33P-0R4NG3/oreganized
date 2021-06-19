package me.gleep.oreganized.effects;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.gleep.oreganized.util.ModDamageSource;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class DawnShine extends Effect {

    public DawnShine() {
        super(EffectType.NEUTRAL, 0xAFAFFF);
    }

    @Override
    public boolean isInstant() {
        return false;
    }



    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return true;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) {
        return false;
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return true;
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.isEntityUndead()) {
            if (entityLivingBaseIn.getHealth() > 1.0F) {
                entityLivingBaseIn.attackEntityFrom(ModDamageSource.MAGIC, 1.0F);
            }

            if (amplifier > 0) {
                List<Entity> list = entityLivingBaseIn.getEntityWorld().getEntitiesInAABBexcluding(entityLivingBaseIn, new AxisAlignedBB(
                                entityLivingBaseIn.getPosX() + 2.0D, entityLivingBaseIn.getPosY() + 2.0D, entityLivingBaseIn.getPosZ() + 2.0D,
                                entityLivingBaseIn.getPosX() - 2.0D, entityLivingBaseIn.getPosY() - 2.0D, entityLivingBaseIn.getPosZ() - 2.0D),
                        Entity::isLiving
                );

                int i = 0;
                for (Entity e : list) {
                    if (i <= Math.floor(Math.sqrt(amplifier))) {
                        if (((LivingEntity) e).getActivePotionEffect(RegistryHandler.DAWN_SHINE.get()) == null && ((LivingEntity) e).getActivePotionEffect(RegistryHandler.NON_DAWN_SHINE.get()) == null) {
                            ((LivingEntity) e).addPotionEffect(new EffectInstance(RegistryHandler.NON_DAWN_SHINE.get(), 200, 0));
                            i++;
                        }
                    }
                }
            }
        } else {
            EffectInstance instance = entityLivingBaseIn.removeActivePotionEffect(RegistryHandler.DAWN_SHINE.get());
            if (instance != null) entityLivingBaseIn.addPotionEffect(new EffectInstance(RegistryHandler.NON_DAWN_SHINE.get(), instance.getDuration(), instance.getAmplifier()));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int i = 25 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }



    /**
     * Called to draw the this Potion onto the player's inventory when it's active.
     * This can be used to e.g. render Potion icons from your own texture.
     *
     * @param effect the active PotionEffect
     * @param gui    the gui instance
     * @param mStack The MatrixStack
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param z      the z level
     */
    @Override
    public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, MatrixStack mStack, int x, int y, float z) {

    }

    /**
     * Called to draw the this Potion onto the player's ingame HUD when it's active.
     * This can be used to e.g. render Potion icons from your own texture.
     *
     * @param effect the active PotionEffect
     * @param gui    the gui instance
     * @param mStack The MatrixStack
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param z      the z level
     * @param alpha  the alpha value, blinks when the potion is about to run out
     */
    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, MatrixStack mStack, int x, int y, float z, float alpha) {
    }
}
