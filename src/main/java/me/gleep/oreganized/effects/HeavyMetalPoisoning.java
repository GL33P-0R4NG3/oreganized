package me.gleep.oreganized.effects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.gleep.oreganized.Oreganized;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HeavyMetalPoisoning extends Effect {
    static int tick = 0;
    public HeavyMetalPoisoning() {
        super(EffectType.HARMFUL, 0x7A3181);
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return false;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, MatrixStack mStack, int x, int y, float z, float alpha) {
        if (tick > 40) {
            Oreganized.LOGGER.info("------------ Matrix4f ------------");
            Oreganized.LOGGER.info(mStack.getLast().getMatrix().toString());
            Oreganized.LOGGER.info("------------ Matrix3f ------------");
            Oreganized.LOGGER.info(mStack.getLast().getNormal().toString());
            Oreganized.LOGGER.info("------------ x - y - z - aplha ------------");
            Oreganized.LOGGER.info(x + " - " + y + " - " + z + " - " + alpha);

            tick = 0;
        }
        tick++;
        /*mStack.rotate(Quaternion.ONE);
        mStack.translate(0.0F, 0.0F, 0.5F);
        gui.blit(mStack, x, y, 15, 16, 15, 16);*/
    }
}
