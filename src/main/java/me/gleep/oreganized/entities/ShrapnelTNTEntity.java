package me.gleep.oreganized.entities;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ShrapnelTNTEntity extends Entity {

    private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(ShrapnelTNTEntity.class, DataSerializers.VARINT);
    @Nullable
    private LivingEntity tntPlacedBy;
    private int fuse = 100;

    public ShrapnelTNTEntity(EntityType<? extends ShrapnelTNTEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.preventEntitySpawning = true;
    }

    public ShrapnelTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        this(RegistryHandler.SHRAPNEL_TNT_ENTITY.get(), worldIn);
        this.setPosition(x, y, z);
        double d0 = worldIn.rand.nextDouble() * (double)((float)Math.PI * 2F);
        this.setMotion(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(100);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.tntPlacedBy = igniter;
    }

    @Override
    public void tick() {
        if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(0.98D));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
        }

        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            if (!this.world.isRemote) {
                this.explode();
            }
        } else {
            this.func_233566_aG_();
            if (this.world.isRemote) {
                this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), 0.0D, 0.0D, 0.0D);
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

            nugget.setVelocity(0.0D, 0.05D, 0.0D);
            nugget.setShooter(this.getTntPlacedBy());
            this.world.addEntity(nugget);
        }

        this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(), 2.5F, Explosion.Mode.BREAK);
    }

    public void setFuse(int fuseIn) {
        this.dataManager.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isAlive();
    }

    private LeadNuggetEntity createProjectile(boolean x, boolean y) {
        float xVect = rand.nextFloat() / 2;
        float yVect = rand.nextFloat() / 2;
        LeadNuggetEntity nugget = new LeadNuggetEntity(this.world, this.getPosX() + (x ? xVect : -xVect), this.getPosY(), this.getPosZ() + (y ? yVect : -yVect));
        return nugget;
    }

    public int getFuse() {
        return this.fuse;
    }

    @Override
    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.15F;
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (FUSE.equals(key)) {
            this.fuse = this.getFuseDataManager();
        }
    }

    public int getFuseDataManager() {
        return this.dataManager.get(FUSE);
    }

    @Nullable
    public LivingEntity getTntPlacedBy() {
        return this.tntPlacedBy;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(FUSE, 100);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.setFuse(compound.getShort("Fuse"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putShort("Fuse", (short)this.getFuse());
    }

    @NotNull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
