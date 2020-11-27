package me.gleep.oreexpansion.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum STSItem implements IItemTier {

    STDS(3, 1561, 7.0F, 3.0F, 10, () -> {
        return Ingredient.fromItems(Items.DIAMOND);
    }),

    STGS(2, 33, 10F, 3.0F, 22, () -> {
        return Ingredient.fromItems(Items.GOLD_INGOT);
    }),

    STNS(4, 2032, 7.0F, 3.0F, 10, () -> {
        return Ingredient.fromItems(Items.NETHERITE_INGOT);
    });

    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int harvestLevel;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    STSItem(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
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
        return repairMaterial.get();
    }
}
