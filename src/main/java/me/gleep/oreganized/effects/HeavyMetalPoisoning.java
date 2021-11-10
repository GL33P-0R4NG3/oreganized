package me.gleep.oreganized.effects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.rmi.registry.Registry;

public class HeavyMetalPoisoning extends Effect {

    public HeavyMetalPoisoning() {
        super(EffectType.HARMFUL, 0x7A3181);
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
        int duration = entityLivingBaseIn.getActivePotionEffect(this).getDuration();

        EffectInstance poison = new EffectInstance(Effects.POISON, duration, amplifier, false, false);
        EffectInstance nausea = new EffectInstance(Effects.NAUSEA, duration + 40, amplifier, false, false);
        EffectInstance instance = new EffectInstance(RegistryHandler.HEAVY_METAL_POISONING.get(), duration, amplifier, false, true);

        entityLivingBaseIn.addPotionEffect(poison);
        entityLivingBaseIn.addPotionEffect(nausea);
        entityLivingBaseIn.addPotionEffect(instance);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration < 0 || duration % 40 == 0;
    }

    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, MatrixStack mStack, int x, int y, float z, float alpha) { }
}
