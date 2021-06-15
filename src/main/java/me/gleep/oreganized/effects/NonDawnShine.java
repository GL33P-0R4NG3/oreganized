package me.gleep.oreganized.effects;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class NonDawnShine extends Effect {

    public NonDawnShine() {
        super(EffectType.NEUTRAL, 0xFFFFFF);
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
        return true;
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return true;
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.isEntityUndead()) {
            EffectInstance instance = entityLivingBaseIn.removeActivePotionEffect(RegistryHandler.NON_DAWN_SHINE.get());
            if (instance != null) entityLivingBaseIn.addPotionEffect(new EffectInstance(RegistryHandler.DAWN_SHINE.get(), instance.getDuration(), instance.getAmplifier()));
        } else {
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

    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, MatrixStack mStack, int x, int y, float z, float alpha) { }
}
