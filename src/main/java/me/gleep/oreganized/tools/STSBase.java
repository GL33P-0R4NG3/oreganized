package me.gleep.oreganized.tools;

import com.mojang.blaze3d.platform.InputConstants;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.List;

public class STSBase extends SwordItem {
    //Maximum durability of the tint
    public static final int MAX_TINT_DURABILITY = 150;
    private final boolean immuneToFire;
    //Used for tinted durability bar
    private boolean shouldDisplayTint;

    public STSBase(Tier p_43269_, int p_43270_, float p_43271_) {
        super(p_43269_, p_43270_, p_43271_, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1));
        this.immuneToFire = p_43269_ == Tiers.NETHERITE;
    }

    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity target, LivingEntity attacker) {
        long random =  Math.round(Math.random() * 100.0F);

        /*if (random <= 35) {
            target.addEffect(this.getDawnShine());
        }*/

        //Minecraft.getInstance().particleEngine.createTrackingEmitter(target, RegistryHandler.DAWN_SHINE_PARTICLE.get(), (int)(random / 4));

        this.decreaseDurabilty(p_43278_, attacker);
        return super.hurtEnemy(p_43278_, target, attacker);
    }

    @Override
    public boolean mineBlock(ItemStack p_43282_, Level p_43283_, BlockState p_43284_, BlockPos p_43285_, LivingEntity p_43286_) {
        this.decreaseDurabilty(p_43282_, p_43286_);
        return super.mineBlock(p_43282_, p_43283_, p_43284_, p_43285_, p_43286_);
    }

    @Override
    public boolean isFireResistant() {
        return this.immuneToFire;
    }

    /**
     * Used to change durability bar when holding left shift or crouching.
     */
    @Override
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
        if (p_41406_ instanceof Player) {
            Player pl = (Player) p_41406_;
            this.shouldDisplayTint = pl.isCrouching() || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT);
        }
    }

    public void decreaseDurabilty(ItemStack stack, LivingEntity entityLiving) {
        if (!(entityLiving instanceof Player)) return;
        Player pl = (Player) entityLiving;
        if (pl.isCreative()) return;
        int durability = stack.getOrCreateTag().getInt("TintedDamage");
        if (durability == 0) {
            durability = MAX_TINT_DURABILITY;
            stack.getOrCreateTag().putInt("TintedDamage", durability);
        }

        if (durability - 1 < 1) {
            if (!stack.isEmpty()) {
                if (!pl.isSilent()) {
                    pl.level.playSound(pl, pl.getX(), pl.getY(), pl.getZ(), SoundEvents.ITEM_BREAK, pl.getSoundSource(), 0.8F, 0.8F + pl.level.random.nextFloat() * 0.4F);
                }

                for (int i = 0; i < 5; ++i) {
                    Vec3 vector = new Vec3(((double)pl.getRandom().nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
                    vector = vector.xRot(-pl.getXRot() * ((float)Math.PI / 180F));
                    vector = vector.yRot(-pl.getYRot() * ((float)Math.PI / 180F));
                    double d0 = (double)(-pl.getRandom().nextFloat()) * 0.6D - 0.3D;
                    Vec3 vector1 = new Vec3(((double)pl.getRandom().nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
                    vector1 = vector1.xRot(-pl.getXRot() * ((float)Math.PI / 180F));
                    vector1 = vector1.yRot(-pl.getYRot() * ((float)Math.PI / 180F));
                    vector1 = vector1.add(pl.getX(), pl.getEyeY(), pl.getZ());
                    if (pl.level instanceof ServerLevel) //Forge: Fix MC-2518 spawnParticle is nooped on server, need to use server specific variant
                        ((ServerLevel)pl.level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), vector1.x, vector1.y, vector1.z, 1, vector.x, vector.y + 0.05D, vector.z, 0.0D);
                    else
                        pl.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), vector1.x, vector1.y, vector1.z, vector.x, vector.y + 0.05D, vector.z);
                }

                ItemStack newSword = ItemStack.EMPTY;
                if (this.getTier().equals(Tiers.DIAMOND)) {
                    newSword = new ItemStack(Items.DIAMOND_SWORD, 1);
                } else if (this.getTier().equals(Tiers.GOLD)) {
                    newSword = new ItemStack(Items.GOLDEN_SWORD, 1);
                } else if (this.getTier().equals(Tiers.NETHERITE)) {
                    newSword = new ItemStack(Items.NETHERITE_SWORD, 1);
                }

                newSword.setTag(stack.getTag());
                newSword.getOrCreateTag().remove("TintedDamage");
                pl.setItemInHand(pl.getUsedItemHand(), newSword);
            }
        } else {
            durability--;
            stack.getOrCreateTag().putInt("TintedDamage", durability);
        }
    }

    /**
     *
     * @return 0.0 for 100% (no damage / full bar), 1.0 for 0% (fully damaged / empty bar)
     */
    @Override
    public int getBarWidth(ItemStack stack) {
        if (this.shouldDisplayTint) {
            return this.getSilverDurabilityForDisplay(stack);
        }
        return stack.getDamageValue() / stack.getMaxDamage();
    }

    public int getSilverDurabilityForDisplay(ItemStack stack) {
        return stack.getOrCreateTag().getInt("TintedDamage") < 1 ? 0 : (MAX_TINT_DURABILITY - stack.getOrCreateTag().getInt("TintedDamage")) / MAX_TINT_DURABILITY;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        if (this.shouldDisplayTint) {
            return Mth.hsvToRgb(200F / 360F, Math.max(0.0F, this.getBarWidth(stack)), 0.94F);
        }
        return Mth.hsvToRgb(Math.max(0.0F, (float) (1.0F - this.getBarWidth(stack))) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        if (p_41421_.getOrCreateTag().getInt("TintedDamage") > 0) {
            TextComponent text = new TextComponent("Tint Durability: " + p_41421_.getOrCreateTag().getInt("TintedDamage") + "/" + MAX_TINT_DURABILITY);
            text.getStyle().withColor(TextColor.fromRgb(0xE1EBF0));
            p_41423_.add(text);
        }
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }

    /*public MobEffectInstance getDawnShine() {
        return new MobEffectInstance(RegistryHandler.DAWN_SHINE.get(), 400, 1);
    }*/
}
