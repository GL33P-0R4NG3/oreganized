package me.gleep.oreganized.entities;

import me.gleep.oreganized.util.ModDamageSource;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.HoneyBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public class LeadNuggetEntity extends Entity {
    @Nullable
    private BlockState inBlockState;
    protected boolean inGround;
    protected int timeInGround;
    private UUID field_234609_b_;
    private int field_234610_c_;
    private boolean field_234611_d_;
    public int arrowShake;
    private double damage = 1.0D;

    public LeadNuggetEntity(EntityType<? extends LeadNuggetEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public LeadNuggetEntity(World worldIn, double x, double y, double z) {
        this(RegistryHandler.LEAD_NUGGET_ENTITY.get(), worldIn);
        this.setPosition(x, y, z);
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        super.tick();
        boolean flag = this.getNoClip();
        Vector3d vector3d = this.getMotion();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(horizontalMag(vector3d));
            this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }

        BlockPos blockpos = this.getPosition();
        BlockState blockstate = this.world.getBlockState(blockpos);
        if (!blockstate.isAir(this.world, blockpos) && !flag) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
            if (!voxelshape.isEmpty()) {
                Vector3d vector3d1 = this.getPositionVec();

                for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
                    if (axisalignedbb.offset(blockpos).contains(vector3d1)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.arrowShake > 0) {
            --this.arrowShake;
        }

        if (this.isWet()) {
            this.extinguish();
        }

        if (this.inGround && !flag) {
            if (this.inBlockState != blockstate && this.func_234593_u_()) {
                this.func_234594_z_();
            } else if (!this.world.isRemote) {
                this.remove();
            }

        } else {
            Vector3d vector3d2 = this.getPositionVec();
            Vector3d vector3d3 = vector3d2.add(vector3d);
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                vector3d3 = raytraceresult.getHitVec();
            }

            if (!this.removed) {
                EntityRayTraceResult entityraytraceresult = this.rayTraceEntities(vector3d2, vector3d3);
                if (entityraytraceresult != null) {
                    raytraceresult = entityraytraceresult;
                }

                if (raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                    Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
                    Entity entity1 = this.func_234616_v_();
                    if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canAttackPlayer((PlayerEntity)entity)) {
                        raytraceresult = null;
                    }
                }

                if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                    this.onImpact(raytraceresult);
                    this.isAirBorne = true;
                }

            }

            vector3d = this.getMotion();
            double d3 = vector3d.x;
            double d4 = vector3d.y;
            double d0 = vector3d.z;

            double d5 = this.getPosX() + d3;
            double d1 = this.getPosY() + d4;
            double d2 = this.getPosZ() + d0;
            float f1 = MathHelper.sqrt(horizontalMag(vector3d));
            if (flag) {
                this.rotationYaw = (float)(MathHelper.atan2(-d3, -d0) * (double)(180F / (float)Math.PI));
            } else {
                this.rotationYaw = (float)(MathHelper.atan2(d3, d0) * (double)(180F / (float)Math.PI));
            }

            this.rotationPitch = (float)(MathHelper.atan2(d4, (double)f1) * (double)(180F / (float)Math.PI));
            this.rotationPitch = func_234614_e_(this.prevRotationPitch, this.rotationPitch);
            this.rotationYaw = func_234614_e_(this.prevRotationYaw, this.rotationYaw);
            float f2 = 0.99F;
            float f3 = 0.05F;
            if (this.isInWater()) {
                for(int j = 0; j < 4; ++j) {
                    float f4 = 0.25F;
                    this.world.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
                }

                f2 = this.getWaterDrag();
            }

            this.setMotion(vector3d.scale((double)f2));
            if (!this.hasNoGravity() && !flag) {
                Vector3d vector3d4 = this.getMotion();
                this.setMotion(vector3d4.x, vector3d4.y - (double)0.05F, vector3d4.z);
            }

            this.setPosition(d5, d1, d2);
            this.doBlockCollisions();
        }

    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        RayTraceResult.Type raytraceresult$type = result.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            this.onEntityHit((EntityRayTraceResult)result);
        } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
            this.func_230299_a_((BlockRayTraceResult)result);
        }

    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        float f = (float)this.getMotion().length();
        int i = MathHelper.ceil(MathHelper.clamp((double)f * this.damage, 0.0D, 2.147483647E9D));

        Entity entity1 = this.func_234616_v_();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = ModDamageSource.causeLeadProjectileDamage(this, this);
        } else {
            damagesource = ModDamageSource.causeLeadProjectileDamage(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastAttackedEntity(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;

        if (entity.attackEntityFrom(damagesource, (float)i)) {
            if (flag) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                if (!this.world.isRemote) {
                    this.addEffectToEntity(livingentity);
                }

                if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity1).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241770_g_, 0.0F));
                }
            }

            this.remove();
        } else {
            this.setMotion(this.getMotion().scale(-0.1D));
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
                this.remove();
            }
        }

    }

    protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
        this.inBlockState = this.world.getBlockState(p_230299_1_.getPos());
        BlockState blockstate = this.world.getBlockState(p_230299_1_.getPos());
        blockstate.onEntityCollision(this.world, p_230299_1_.getPos(), this);
        Vector3d vector3d = p_230299_1_.getHitVec().subtract(this.getPosX(), this.getPosY(), this.getPosZ());
        this.setMotion(vector3d);
        Vector3d vector3d1 = vector3d.normalize().scale((double)0.05F);
        this.setRawPosition(this.getPosX() - vector3d1.x, this.getPosY() - vector3d1.y, this.getPosZ() - vector3d1.z);
        this.inGround = true;
        this.arrowShake = 7;
    }

    /**
     * Adds Heavy Metal Poisoning effect to passed LivingEntity
     * @param entity the entity to add effect to
     */
    private void addEffectToEntity(LivingEntity entity) {
        int duration = 200;
        if (entity.getActivePotionEffect(RegistryHandler.HEAVY_METAL_POISONING.get()) != null) {
            duration += entity.getActivePotionEffect(RegistryHandler.HEAVY_METAL_POISONING.get()).getDuration();
        }
        entity.addPotionEffect(new EffectInstance(RegistryHandler.HEAVY_METAL_POISONING.get(), duration, 0));
    }

    /**
     * Removes the Lead Nugget Entity and adds Item Entity instead
     */
    @Override
    public void remove() {
        super.remove();
        world.addEntity(this.getLeadNugget());
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        if (this.field_234609_b_ != null) {
            compound.putUniqueId("Owner", this.field_234609_b_);
        }

        if (this.field_234611_d_) {
            compound.putBoolean("LeftOwner", true);
        }

        compound.putByte("shake", (byte)this.arrowShake);
        compound.putBoolean("inGround", this.inGround);
        compound.putDouble("damage", this.damage);
    }

    /**
     * Helper method to read entity data from NBT.
     */
    @Override
    public void readAdditional(CompoundNBT compound) {
        if (compound.hasUniqueId("Owner")) {
            this.field_234609_b_ = compound.getUniqueId("Owner");
        }

        this.field_234611_d_ = compound.getBoolean("LeftOwner");

        this.arrowShake = compound.getByte("shake") & 255;
        this.inGround = compound.getBoolean("inGround");
        if (compound.contains("damage", 99)) {
            this.damage = compound.getDouble("damage");
        }
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 53) {
            HoneyBlock.entitySlideParticles(this);
        }
    }

    /**
     * Checks if the entity is in range to render.
     */
    @Override
    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getRenderDistanceWeight();
        return distance < d0 * d0;
    }

    protected static float func_234614_e_(float p_234614_0_, float p_234614_1_) {
        while(p_234614_1_ - p_234614_0_ < -180.0F) {
            p_234614_0_ -= 360.0F;
        }

        while(p_234614_1_ - p_234614_0_ >= 180.0F) {
            p_234614_0_ += 360.0F;
        }

        return MathHelper.lerp(0.2F, p_234614_0_, p_234614_1_);
    }

    private boolean func_234593_u_() {
        return this.inGround && this.world.hasNoCollisions((new AxisAlignedBB(this.getPositionVec(), this.getPositionVec())).grow(0.06D));
    }

    private void func_234594_z_() {
        this.inGround = false;
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.mul((double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F)));
    }

    @Override
    public void move(MoverType typeIn, Vector3d pos) {
        super.move(typeIn, pos);
        if (typeIn != MoverType.SELF && this.func_234593_u_()) {
            this.func_234594_z_();
        }

    }

    /**
     * Gets the EntityRayTraceResult representing the entity hit
     */
    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
        return ProjectileHelper.rayTraceEntities(this.world, this, startVec, endVec, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), this::func_230298_a_);
    }

    protected boolean func_230298_a_(Entity p_230298_1_) {
        if (!p_230298_1_.isSpectator() && p_230298_1_.isAlive() && p_230298_1_.canBeCollidedWith()) {
            Entity entity = this.func_234616_v_();
            return entity == null || this.field_234611_d_ || !entity.isRidingSameEntity(p_230298_1_);
        } else {
            return false;
        }
    }

    @Nullable
    public Entity func_234616_v_() {
        if (this.field_234609_b_ != null && this.world instanceof ServerWorld) {
            return ((ServerWorld)this.world).getEntityByUuid(this.field_234609_b_);
        } else {
            return this.field_234610_c_ != 0 ? this.world.getEntityByID(this.field_234610_c_) : null;
        }
    }

    @Override
    protected void registerData() {

    }



    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    @Override
    public boolean canBeAttackedWithItem() {
        return false;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    /**
     * Creates spawnable item in the world of lead nugget
     * @return the new Item Entity of the lead nugget
     */
    private ItemEntity getLeadNugget() {
        ItemStack nugget = new ItemStack(RegistryHandler.LEAD_NUGGET.get(),1);
        return new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), nugget);
    }

    /**
     * Whether the arrow can noClip
     */
    public boolean getNoClip() {
        if (!this.world.isRemote) {
            return this.noClip;
        }
        return false;
    }

    @Override
    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.13F;
    }

    protected float getWaterDrag() {
        return 0.6F;
    }

    public double getDamage() {
        return this.damage;
    }



    /**
     * Sets a target for the client to interpolate towards over the next few ticks
     */
    @Override
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    /**
     * Updates the entity motion clientside, called by packets from the server
     */
    @Override
    public void setVelocity(double x, double y, double z) {
        super.setVelocity(x, y, z);
    }

    /**
     * Sets the owner of the lead nugget projectile. Used for later damage calculations.
     * @param entityIn the owner of the projectile
     */
    public void setShooter(@Nullable Entity entityIn) {
        if (entityIn != null) {
            this.field_234609_b_ = entityIn.getUniqueID();
            this.field_234610_c_ = entityIn.getEntityId();
        }
    }

    /**
     * Sets if this arrow can noClip
     */
    public void setNoClip(boolean noClipIn) {
        this.noClip = noClipIn;
    }

    public void setDamage(double damageIn) {
        this.damage = damageIn;
    }



    @NotNull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
