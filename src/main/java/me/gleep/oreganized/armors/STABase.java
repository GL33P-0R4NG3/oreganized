package me.gleep.oreganized.armors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class STABase extends ArmorItem {
    //Maximum durability of the tint
    public static final int MAX_TINT_DURABILITY = 50;
    private final boolean immuneToFire;
    //Used for tinted durability bar
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

    /**
     * Used to change durability bar when holding left shift or crouching.
     */
    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity pl = (PlayerEntity) entityIn;
            this.shouldDisplayTint = pl.isCrouching() || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT);
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.decreaseDurabilty(stack, attacker);
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
        super.onUse(worldIn, livingEntityIn, stack, count);
    }

    /**
     * Decrease the Tinted durability by one also handles break event
     *
     * @param stack         stack that is the durability be decreased
     * @param entityLiving  entity that is in interaction
     */
    public void decreaseDurabilty(ItemStack stack, LivingEntity entityLiving) {
        if (!(entityLiving instanceof PlayerEntity)) return;
        PlayerEntity pl = (PlayerEntity) entityLiving;
        if (pl.isCreative()) return;
        int durability = stack.getOrCreateTag().getInt("TintedDamage");
        if (durability == 0) {
            durability = MAX_TINT_DURABILITY;
            stack.getOrCreateTag().putInt("TintedDamage", durability);
        }

        if (durability - 1 < 1) {
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
                if (this.getArmorMaterial().equals(ArmorMaterial.DIAMOND)) {
                    switch (this.getEquipmentSlot()) {
                        case CHEST:
                            newArmorPiece = new ItemStack(Items.DIAMOND_CHESTPLATE, 1);
                            break;
                        case FEET:
                            newArmorPiece = new ItemStack(Items.DIAMOND_BOOTS, 1);
                            break;
                        case HEAD:
                            newArmorPiece = new ItemStack(Items.DIAMOND_HELMET, 1);
                            break;
                        case LEGS:
                            newArmorPiece = new ItemStack(Items.DIAMOND_LEGGINGS, 1);
                            break;
                    }
                } else if (this.getArmorMaterial().equals(ArmorMaterial.GOLD)) {
                    switch (this.getEquipmentSlot()) {
                        case CHEST:
                            newArmorPiece = new ItemStack(Items.GOLDEN_CHESTPLATE, 1);
                            break;
                        case FEET:
                            newArmorPiece = new ItemStack(Items.GOLDEN_BOOTS, 1);
                            break;
                        case HEAD:
                            newArmorPiece = new ItemStack(Items.GOLDEN_HELMET, 1);
                            break;
                        case LEGS:
                            newArmorPiece = new ItemStack(Items.GOLDEN_LEGGINGS, 1);
                            break;
                    }
                } else if (this.getArmorMaterial().equals(ArmorMaterial.NETHERITE)) {
                    switch (this.getEquipmentSlot()) {
                        case CHEST:
                            newArmorPiece = new ItemStack(Items.NETHERITE_CHESTPLATE, 1);
                            break;
                        case FEET:
                            newArmorPiece = new ItemStack(Items.NETHERITE_BOOTS, 1);
                            break;
                        case HEAD:
                            newArmorPiece = new ItemStack(Items.NETHERITE_HELMET, 1);
                            break;
                        case LEGS:
                            newArmorPiece = new ItemStack(Items.NETHERITE_LEGGINGS, 1);
                            break;
                    }
                }

                newArmorPiece.setTag(stack.getTag());
                newArmorPiece.getOrCreateTag().remove("TintedDamage");
                if (pl.inventory.armorInventory.contains(stack)) {
                    pl.inventory.armorInventory.set(this.getEquipmentSlot().getIndex(), newArmorPiece);
                } else {
                    pl.inventory.setInventorySlotContents(pl.inventory.getSlotFor(stack), newArmorPiece);
                }
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
    public double getDurabilityForDisplay(ItemStack stack) {
        if (this.shouldDisplayTint) {
            return this.getSilverDurabilityForDisplay(stack);
        }
        return (double) stack.getDamage() / (double) stack.getMaxDamage();
    }

    public double getSilverDurabilityForDisplay(ItemStack stack) {
        return stack.getOrCreateTag().getInt("TintedDamage") < 1 ? 0D : (double)  (MAX_TINT_DURABILITY - stack.getOrCreateTag().getInt("TintedDamage")) / (double) MAX_TINT_DURABILITY;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        if (this.shouldDisplayTint) {
            return MathHelper.hsvToRGB(200F / 360F, Math.max(0.0F, (float) this.getDurabilityForDisplay(stack)), 0.94F);
        }
        return MathHelper.hsvToRGB(Math.max(0.0F, (float) (1.0F - this.getDurabilityForDisplay(stack))) / 3.0F, 1.0F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.getOrCreateTag().getInt("TintedDamage") > 0) {
            ITextComponent text = ITextComponent.getTextComponentOrEmpty("Tint Durability: " + stack.getOrCreateTag().getInt("TintedDamage") + "/" + MAX_TINT_DURABILITY);
            text.getStyle().setColor(Color.fromInt(0xE1EBF0));
            tooltip.add(text);
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if (slot.equals(EquipmentSlotType.LEGS)) {
            switch ((ArmorMaterial) this.getArmorMaterial()) {
                case DIAMOND:
                    return "oreganized:textures/models/armor/silver_tinted_diamond_layer_2.png";
                case GOLD:
                    return "oreganized:textures/models/armor/silver_tinted_gold_layer_2.png";
                case NETHERITE:
                    return "oreganized:textures/models/armor/silver_tinted_netherite_layer_2.png";
            }
        } else {
            switch ((ArmorMaterial) this.getArmorMaterial()) {
                case DIAMOND:
                    return "oreganized:textures/models/armor/silver_tinted_diamond_layer_1.png";
                case GOLD:
                    return "oreganized:textures/models/armor/silver_tinted_gold_layer_1.png";
                case NETHERITE:
                    return "oreganized:textures/models/armor/silver_tinted_netherite_layer_1.png";
            }
        }
        return super.getArmorTexture(stack, entity, slot, type);
    }
}
