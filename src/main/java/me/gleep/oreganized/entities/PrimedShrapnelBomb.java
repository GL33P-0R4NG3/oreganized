package me.gleep.oreganized.entities;

import me.gleep.oreganized.potion.ModPotions;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;

public class PrimedShrapnelBomb extends PrimedTnt {
    public PrimedShrapnelBomb(EntityType<? extends PrimedShrapnelBomb> p_32076_, Level p_32077_) {
        super(p_32076_, p_32077_);
    }

    public PrimedShrapnelBomb(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
        this(RegistryHandler.SHRAPNEL_BOMB_ENTITY.get(), p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        double d0 = p_32079_.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(60);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.owner = p_32083_;
    }

    @Override
    protected void explode() {
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 6.0F, Explosion.BlockInteraction.NONE);
        if(!this.level.isClientSide()) ((ServerLevel)this.level).sendParticles(RegistryHandler.LEAD_SHRAPNEL.get(), this.getX(), this.getY(0.0625D) ,
                this.getZ() , 100, 0.0D , 0.0D , 0.0D, 5 );
        for (Entity entity :this.level.getEntities(this, new AABB(this.getX() - 30, this.getY() - 4, this.getZ() - 30,
                this.getX() + 30, this.getY() + 4, this.getZ() + 30))){
            int random = (int) (Math.random() * 100);
            boolean shouldPoison = false;
            if(entity.distanceToSqr(this) <= 4*4){
                shouldPoison = true;
            } else if (entity.distanceToSqr(this) <= 8*8) {
                if(random < 60) shouldPoison = true;
            } else if (entity.distanceToSqr(this) <= 15*15) {
                if(random < 30) shouldPoison = true;
            } else if (entity.distanceToSqr(this) <= 30*30) {
                if(random < 5) shouldPoison = true;
            }
            if(shouldPoison){
                if(entity instanceof LivingEntity livingEntity){
                    livingEntity.hurt(DamageSource.MAGIC, 2);
                    livingEntity.addEffect( new MobEffectInstance( ModPotions.STUNNED , 40 * 20 ) );
                    livingEntity.addEffect( new MobEffectInstance( MobEffects.POISON , 260 ) );
                }
            }
        }
    }
}
