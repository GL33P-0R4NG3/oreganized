package me.gleep.oreganized.tools;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.item.*;

public class STSBase extends SwordItem {
    private final boolean immuneToFire;

    public STSBase(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ItemGroup.COMBAT).setNoRepair());
        this.immuneToFire = false;
    }

    public STSBase(IItemTier tier, int attackDamageIn, float attackSpeedIn, boolean immuneToFire) {
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ItemGroup.COMBAT).setNoRepair());
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
