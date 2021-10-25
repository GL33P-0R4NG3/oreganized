package me.gleep.oreganized.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class ModDamageSource extends DamageSource {
    public static final DamageSource MOLTEN_LEAD = new ModDamageSource("molten_lead").setIsFire();

    public ModDamageSource(String damageTypeIn) {
        super(damageTypeIn);
    }

    /**
     * returns EntityDamageSourceIndirect of an arrow
     */
    public static DamageSource causeLeadProjectileDamage(Entity arrow, @Nullable Entity indirectEntityIn) {
        return (new IndirectEntityDamageSource("lead_nugget", arrow, indirectEntityIn)).setProjectile();
    }
}
