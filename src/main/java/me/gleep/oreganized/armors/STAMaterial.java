package me.gleep.oreganized.armors;

import me.gleep.oreganized.Oreganized;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum STAMaterial implements IArmorMaterial {
    STDA(Oreganized.MOD_ID + ":silver_tinted_diamond", Items.DIAMOND_LEGGINGS.getDefaultInstance().getMaxDamage() / 15,
            new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F),
    STGA(Oreganized.MOD_ID + ":silver_tinted_gold", Items.GOLDEN_LEGGINGS.getDefaultInstance().getMaxDamage() / 15,
            new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F, 0.0F),
    STNA(Oreganized.MOD_ID + ":silver_tinted_netherite", Items.NETHERITE_LEGGINGS.getDefaultInstance().getMaxDamage() / 15,
            new int[]{3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F);

    private final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 }; // Boots, Leggings, Chestplate, Helmet
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;

    STAMaterial(String name, int maxDamageFactor, int[] damageReductionArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionArray = damageReductionArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return this.MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
