package me.gleep.oreganized.entities;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.registries.DataSerializerEntry;

import javax.annotation.Nullable;

public class ShrapnelTNTEntity extends Entity {

    private static final EntityDataAccessor<Integer> FUSE = null;//= net.minecraft.server.commands.data.EntityDataAccessor.PROVIDER(ShrapnelTNTEntity.class, DataSerializerEntry.VARINT);
    @Nullable
    private LivingEntity tntPlacedBy;
    private int fuse = 100;

    public ShrapnelTNTEntity(EntityType<? extends ShrapnelTNTEntity> entityTypeIn, Level level) {
        super(entityTypeIn, level);
        this.blocksBuilding = true;
    }

    /*public ShrapnelTNTEntity(Level worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        this(RegistryHandler.SHRAPNEL_TNT_ENTITY.get(), worldIn);
        this.setPos(x, y, z);
        double d0 = worldIn.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(100);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.tntPlacedBy = igniter;
    }*/

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }

        --this.fuse;
        if (this.fuse <= 0) {
            this.onClientRemoval();
            if (!this.level.isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level.isClientSide) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }



    protected void explode() {
        for (int i = 0; i < 8; i++) {
            LeadNuggetEntity nugget;
            if (i < 2) {
                nugget = this.createProjectile(true, true);
            } else if (i < 4) {
                nugget = this.createProjectile(true, false);
            } else if (i < 6) {
                nugget = this.createProjectile(false, false);
            } else {
                nugget = this.createProjectile(false, true);
            }

            nugget.setShooter(this.getOwner());
            this.level.addFreshEntity(nugget);
        }

        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 2.5F, Explosion.BlockInteraction.BREAK);
    }

    public void setFuse(int fuseIn) {
        //this.entityData.set(FUSE, fuseIn);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isAlive();
    }

    /**
     * Returns projectile with position around the tnt.
     * @param x if coordinate x should be positive
     * @param y if coordinate z should be positive
     * @return projectile with position and base velocity around the tnt.
     */
    private LeadNuggetEntity createProjectile(boolean x, boolean y) {
        float xVect = random.nextFloat() / 4;
        float yVect = random.nextFloat() / 4;
        LeadNuggetEntity nugget = new LeadNuggetEntity(this.level, this.getX() + (x ? xVect : -xVect), this.getY(), this.getZ() + (y ? yVect : -yVect));
        //nugget.setVelocity((xVect / 2.5D) * (x ? 1.0D : -1.0D), 0.0D, (yVect / 2.5D) * (y ? 1.0D : -1.0D));
        return nugget;
    }

    public int getFuse() {
        return this.fuse;
    }

    @Override
    protected float getEyeHeight(Pose p_19976_, EntityDimensions p_19977_) {
        return 0.15F;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_20059_) {
        //if (FUSE.e(p_20059_)) {
            this.fuse = this.getFuseDataManager();
        //}
    }

    public int getFuseDataManager() {
        return this.entityData.get(FUSE);
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.tntPlacedBy;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.set(FUSE, 100);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {
        this.setFuse(p_20052_.getShort("Fuse"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {
        p_20139_.putShort("Fuse", (short)this.getFuse());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
