package me.gleep.oreganized.tools;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class STSBase extends SwordItem {

    public static final int MAX_DURABILITY = 150;
    private final boolean immuneToFire;

    public STSBase(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1));
        this.immuneToFire = false;
    }

    public STSBase(IItemTier tier, int attackDamageIn, float attackSpeedIn, boolean immuneToFire) {
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1));
        this.immuneToFire = immuneToFire;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.isEntityUndead()) {
            ExperienceOrbEntity xp = new ExperienceOrbEntity(EntityType.EXPERIENCE_ORB, target.getEntityWorld());
            xp.setPosition(target.getPosX(), target.getPosY(), target.getPosZ());
            xp.setMotion(random.nextDouble() * ((random.nextInt() % 2) > 0 ? -0.06D: 0.06D), 0.2D, random.nextDouble() * ((random.nextInt() % 2) > 0 ? -0.06D: 0.06D));
            xp.xpValue = 1;
            target.getEntityWorld().addEntity(xp);
        }

        this.decreaseDurabilty(stack, attacker);
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        this.decreaseDurabilty(stack, entityLiving);
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public boolean isImmuneToFire() {
        return this.immuneToFire;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    public void decreaseDurabilty(ItemStack stack, LivingEntity entityLiving) {
        int durability = stack.getOrCreateTag().getInt("TintedDamage");
        if (durability == 0) {
            durability = MAX_DURABILITY;
            stack.getOrCreateTag().putInt("TintedDamage", durability);
        }

        if (durability - 1 < 1) {
            if (entityLiving instanceof PlayerEntity) {
                PlayerEntity pl = (PlayerEntity) entityLiving;
                if (!stack.isEmpty()) {
                    if (!pl.isSilent()) {
                        pl.world.playSound(pl.getPosX(), pl.getPosY(), pl.getPosZ(), SoundEvents.ENTITY_ITEM_BREAK, pl.getSoundCategory(), 0.8F, 0.8F + pl.world.rand.nextFloat() * 0.4F, false);
                    }

                    for (int i = 0; i < 5; ++i) {
                        Vector3d vector3d = new Vector3d(((double)pl.getRNG().nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
                        vector3d = vector3d.rotatePitch(-pl.rotationPitch * ((float)Math.PI / 180F));
                        vector3d = vector3d.rotateYaw(-pl.rotationYaw * ((float)Math.PI / 180F));
                        double d0 = (double)(-pl.getRNG().nextFloat()) * 0.6D - 0.3D;
                        Vector3d vector3d1 = new Vector3d(((double)pl.getRNG().nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
                        vector3d1 = vector3d1.rotatePitch(-pl.rotationPitch * ((float)Math.PI / 180F));
                        vector3d1 = vector3d1.rotateYaw(-pl.rotationYaw * ((float)Math.PI / 180F));
                        vector3d1 = vector3d1.add(pl.getPosX(), pl.getPosYEye(), pl.getPosZ());
                        if (pl.world instanceof ServerWorld) //Forge: Fix MC-2518 spawnParticle is nooped on server, need to use server specific variant
                            ((ServerWorld)pl.world).spawnParticle(new ItemParticleData(ParticleTypes.ITEM, stack), vector3d1.x, vector3d1.y, vector3d1.z, 1, vector3d.x, vector3d.y + 0.05D, vector3d.z, 0.0D);
                        else
                            pl.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, stack), vector3d1.x, vector3d1.y, vector3d1.z, vector3d.x, vector3d.y + 0.05D, vector3d.z);
                    }

                    ItemStack newSword = ItemStack.EMPTY;
                    if (stack.getItem().equals(RegistryHandler.SILVER_TINTED_DIAMOND_SWORD.get())) {
                        newSword = new ItemStack(Items.DIAMOND_SWORD, 1);
                    } else if (stack.getItem().equals(RegistryHandler.SILVER_TINTED_GOLDEN_SWORD.get())) {
                        newSword = new ItemStack(Items.GOLDEN_SWORD, 1);
                    } else if (stack.getItem().equals(RegistryHandler.SILVER_TINTED_NETHERITE_SWORD.get())) {
                        newSword = new ItemStack(Items.NETHERITE_SWORD, 1);
                    }

                    newSword.setDamage(stack.getDamage());
                    stack.shrink(1);
                    entityLiving.setHeldItem(entityLiving.getActiveHand(), newSword);
                }
            }
        } else {
            durability--;
            stack.getOrCreateTag().putInt("TintedDamage", durability);
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.getAttachedEntity() instanceof PlayerEntity) {
            PlayerEntity pl = (PlayerEntity) stack.getAttachedEntity();
            if (pl.isCrouching()) return stack.getOrCreateTag().getInt("TintedDamage") < 1 ? (double) MAX_DURABILITY : (double) stack.getOrCreateTag().getInt("TintedDamage") / (double) MAX_DURABILITY;
        }

        return (double) stack.getDamage() / (double) stack.getMaxDamage();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(ITextComponent.getTextComponentOrEmpty("Tint Durability: " + stack.getOrCreateTag().getInt("TintedDamage") + "/" + MAX_DURABILITY));
    }
}
