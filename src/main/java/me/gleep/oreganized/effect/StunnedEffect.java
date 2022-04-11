package me.gleep.oreganized.effect;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.potion.ModPotions;
import net.minecraft.client.player.Input;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author SameButDifferent
 */
@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, value = Dist.CLIENT)
public class StunnedEffect extends MobEffect {
    public StunnedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.isOnGround() || entity.isInWater()) {
            entity.setDeltaMovement(Vec3.ZERO);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @SubscribeEvent
    public static void onInputUpdate(MovementInputUpdateEvent event) {
        Input input = event.getInput();
        if (event.getPlayer().hasEffect(ModPotions.STUNNED)) {
            input.up = false;
            input.down = false;
            input.left = false;
            input.right = false;
            input.forwardImpulse = 0;
            input.leftImpulse = 0;
            input.jumping = false;
            input.shiftKeyDown = false;
        }
    }
}
