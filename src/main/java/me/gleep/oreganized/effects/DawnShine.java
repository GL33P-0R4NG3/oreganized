package me.gleep.oreganized.effects;

import me.gleep.oreganized.util.ModDamageSource;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class DawnShine extends MobEffect {

    public DawnShine() {
        super(MobEffectCategory.NEUTRAL, 0xAFAFFF);
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public void applyEffectTick(LivingEntity p_19467_, int amplifier) {
        if (p_19467_.isAffectedByPotions()) {
            if (p_19467_.getHealth() > 1.0F) {
                p_19467_.hurt(ModDamageSource.MAGIC, 1.0F);
            }

            if (amplifier > 0) {
                List<Entity> list = p_19467_.level.getEntities(p_19467_, new AABB(
                                p_19467_.getX() + 2.0D, p_19467_.getY() + 2.0D, p_19467_.getZ() + 2.0D,
                                p_19467_.getX() - 2.0D, p_19467_.getY() - 2.0D, p_19467_.getZ() - 2.0D),
                        (Entity entity) -> entity instanceof LivingEntity
                );

                int i = 0;
                for (Entity e : list) {
                    if (i <= Math.floor(Math.sqrt(amplifier))) {
                        if (((LivingEntity) e).getEffect(RegistryHandler.DAWN_SHINE.get()) == null && ((LivingEntity) e).getEffect(RegistryHandler.NON_DAWN_SHINE.get()) == null) {
                            ((LivingEntity) e).addEffect(new MobEffectInstance(RegistryHandler.NON_DAWN_SHINE.get(), 200, 0));
                            i++;
                        }
                    }
                }
            }
        } else {
            MobEffectInstance instance = p_19467_.removeEffectNoUpdate(RegistryHandler.DAWN_SHINE.get());
            if (instance != null) p_19467_.addEffect(new MobEffectInstance(RegistryHandler.NON_DAWN_SHINE.get(), instance.getDuration(), instance.getAmplifier()));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int i = 25 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}
