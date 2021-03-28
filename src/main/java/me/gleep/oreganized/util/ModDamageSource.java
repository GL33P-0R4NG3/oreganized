package me.gleep.oreganized.util;

import me.gleep.oreganized.entities.LeadNuggetEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class ModDamageSource extends DamageSource {
    public static final DamageSource MOLTEN_LEAD = new ModDamageSource("molten_lead").setFireDamage();

    public ModDamageSource(String damageTypeIn) {
        super(damageTypeIn);
    }

    /**
     * returns EntityDamageSourceIndirect of an arrow
     */
    public static DamageSource causeLeadProjectileDamage(LeadNuggetEntity arrow, @Nullable Entity indirectEntityIn) {
        return (new IndirectEntityDamageSource("lead_nugget", arrow, indirectEntityIn)).setProjectile();
    }
}
