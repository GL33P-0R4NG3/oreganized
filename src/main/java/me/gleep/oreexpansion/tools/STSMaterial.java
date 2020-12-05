package me.gleep.oreexpansion.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public enum STSMaterial implements IItemTier {

    STDS(3, Math.round(Items.DIAMOND_SWORD.getDefaultInstance().getMaxDamage() * 0.05F), 8.0F, 3.0F, 10),

    STGS(0, Math.round(Items.GOLDEN_SWORD.getDefaultInstance().getMaxDamage() * 0.05F), 12.0F, 0.0F, 22),

    STNS(4, Math.round(Items.NETHERITE_SWORD.getDefaultInstance().getMaxDamage() * 0.05F), 9.0F, 4.0F, 15);

    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int harvestLevel;
    private final int enchantability;

    STSMaterial(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability) {
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
    }

    @Override
    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return null;
    }
}
