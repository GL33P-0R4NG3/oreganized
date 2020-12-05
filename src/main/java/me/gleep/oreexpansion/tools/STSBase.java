package me.gleep.oreexpansion.tools;

import me.gleep.oreexpansion.OreExpansion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class STSBase extends SwordItem {
    private final boolean immuneToFire;

    public STSBase(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ItemGroup.COMBAT));
        this.immuneToFire = false;
    }

    public STSBase(IItemTier tier, int attackDamageIn, float attackSpeedIn, boolean immuneToFire) {
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ItemGroup.COMBAT));
        this.immuneToFire = immuneToFire;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ResourceLocation resourceLocation = new ResourceLocation(OreExpansion.MOD_ID, "undead_entities");
        if (EntityTypeTags.getCollection().getTagByID(resourceLocation).contains(target.getType())) {
            ExperienceOrbEntity xp = new ExperienceOrbEntity(EntityType.EXPERIENCE_ORB, target.getEntityWorld());
            xp.setPosition(target.getPosX(), target.getPosY(), target.getPosZ());
            xp.setMotion(random.nextDouble() * ((random.nextInt() % 2) > 0 ? -0.06D: 0.06D), 0.2D, random.nextDouble() * ((random.nextInt() % 2) > 0 ? -0.06D: 0.06D));
            xp.xpValue = 1;
            target.getEntityWorld().addEntity(xp);
        }
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public boolean isImmuneToFire() {
        return this.immuneToFire;
    }

    /*@Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        System.out.println(stack.getEnchantmentTagList().toString());
        PlayerEntity player = (PlayerEntity) entityLiving;
        player.inventory.setInventorySlotContents(player.inventory.currentItem, sword);
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }*/

    /*@Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.oreexpansion.silver_tinted.tooltip.line1")
                .setStyle(Style.EMPTY.setItalic(true).setColor(Color.fromHex("D1D1D1"))));
    }*/
}
