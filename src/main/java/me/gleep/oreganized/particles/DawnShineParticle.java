package me.gleep.oreganized.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

import javax.annotation.Nullable;
import java.util.Random;

public class DawnShineParticle extends TextureSheetParticle {

    private static final Random RANDOM = new Random();
    private final SpriteSet spriteSet;

    protected DawnShineParticle(ClientLevel p_108328_, double x, double y, double z, double motionX, double motionY, double motionZ, SpriteSet spriteSet) {
        super(p_108328_, x, y, z, 0.5D - RANDOM.nextDouble(), motionY, 0.5D - RANDOM.nextDouble());
        this.spriteSet = spriteSet;
        this.yd *= (double)0.2F;
        if (motionX == 0.0D && motionZ == 0.0D) {
            this.xd *= (double)0.1F;
            this.zd *= (double)0.1F;
        }

        this.quadSize *= 0.9F;
        this.lifetime = (int)(7.0D / (Math.random() * 0.8D + 0.2D));
        this.hasPhysics = false;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.spriteSet);
            this.yd += 0.004D;
            this.move(this.xd, this.yo, this.zd);
            if (this.y == this.yo) {
                this.xd *= 1.1D;
                this.zd *= 1.1D;
            }

            this.xd *= (double)0.96F;
            this.yd *= (double)0.96F;
            this.zd *= (double)0.96F;
            if (this.onGround) {
                this.xd *= (double)0.7F;
                this.zd *= (double)0.7F;
            }

        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType p_107421_, ClientLevel p_107422_, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new DawnShineParticle(p_107422_, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
