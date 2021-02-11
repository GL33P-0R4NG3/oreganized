package me.gleep.oreganized.armors;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class STABase extends ArmorItem {
    public static final int MAX_TINT_DURABILITY = 150;
    private final boolean immuneToFire;
    private boolean shouldDisplayTint;

    public STABase(IArmorMaterial materialIn, EquipmentSlotType slot) {
        super(materialIn, slot, new Properties().group(ItemGroup.COMBAT).maxStackSize(1));
        this.immuneToFire = materialIn == ArmorMaterial.NETHERITE;
    }

    @Override
    public boolean isImmuneToFire() {
        return this.immuneToFire;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return stack.getItemEnchantability() == ArmorMaterial.GOLD.getEnchantability();
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity pl = (PlayerEntity) entityIn;
            this.shouldDisplayTint = pl.isCrouching();
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (this.getEquipmentSlot().equals(EquipmentSlotType.CHEST) || this.getEquipmentSlot().equals(EquipmentSlotType.FEET)
                || this.getEquipmentSlot().equals(EquipmentSlotType.HEAD) || this.getEquipmentSlot().equals(EquipmentSlotType.LEGS)) {
            this.decreaseDurabilty(stack, target);
        } else {
            this.decreaseDurabilty(stack, attacker);
        }
        return super.hitEntity(stack, target, attacker);
    }

    public void decreaseDurabilty(ItemStack stack, LivingEntity entityLiving) {
        int durability = stack.getOrCreateTag().getInt("TintedDamage");
        if (durability == 0) {
            durability = MAX_TINT_DURABILITY;
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

                    ItemStack newArmorPiece = ItemStack.EMPTY;
                    if (stack.getItem().equals(RegistryHandler.SILVER_TINTED_DIAMOND_SWORD.get())) {
                        newArmorPiece = new ItemStack(Items.DIAMOND_SWORD, 1);
                    } else if (stack.getItem().equals(RegistryHandler.SILVER_TINTED_GOLDEN_SWORD.get())) {
                        newArmorPiece = new ItemStack(Items.GOLDEN_SWORD, 1);
                    } else if (stack.getItem().equals(RegistryHandler.SILVER_TINTED_NETHERITE_SWORD.get())) {
                        newArmorPiece = new ItemStack(Items.NETHERITE_SWORD, 1);
                    }

                    newArmorPiece.setTag(stack.getTag());
                    newArmorPiece.getOrCreateTag().remove("TintedDamage");
                    stack.shrink(1);
                    entityLiving.setHeldItem(entityLiving.getActiveHand(), newArmorPiece);
                }
            }
        } else {
            durability--;
            stack.getOrCreateTag().putInt("TintedDamage", durability);
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (this.shouldDisplayTint) {
            return stack.getOrCreateTag().getInt("TintedDamage") < 1 ? (double) MAX_TINT_DURABILITY : (double)  (MAX_TINT_DURABILITY - stack.getOrCreateTag().getInt("TintedDamage")) / (double) MAX_TINT_DURABILITY;
        }

        return (double) stack.getDamage() / (double) stack.getMaxDamage();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.getOrCreateTag().getInt("TintedDamage") > 0) {
            tooltip.add(2, ITextComponent.getTextComponentOrEmpty("Tint Durability: " + stack.getOrCreateTag().getInt("TintedDamage") + "/" + MAX_TINT_DURABILITY));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
