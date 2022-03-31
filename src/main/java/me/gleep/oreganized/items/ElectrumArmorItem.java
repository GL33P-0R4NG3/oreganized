package me.gleep.oreganized.items;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.client.model.ElectrumArmorModel;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ElectrumArmorItem extends ArmorItem {
    private static final ElectrumArmorMaterial material = new ElectrumArmorMaterial();
    private static final String TEXTURE = Oreganized.MOD_ID + ":textures/entity/electrum_armor.png";

    public ElectrumArmorItem(EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return TEXTURE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
                return new ElectrumArmorModel<>(ElectrumArmorModel.createBodyLayer().bakeRoot(), armorSlot);
            }
        });
    }

    private static class ElectrumArmorMaterial implements ArmorMaterial {
        private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

        @Override
        public int getDurabilityForSlot(EquipmentSlot slot) {
            return HEALTH_PER_SLOT[slot.getIndex()] * 37;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot slot) {
            int[] defensePerSlot = {3, 6, 8, 3};
            return defensePerSlot[slot.getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_CHAIN;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(RegistryHandler.ELECTRUM_INGOT.get());
        }

        @Override
        public String getName() {
            return "electrum";
        }

        @Override
        public float getToughness() {
            return 3.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
    }

}
