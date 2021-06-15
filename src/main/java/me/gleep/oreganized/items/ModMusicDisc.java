package me.gleep.oreganized.items;

import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IRegistryDelegate;

import java.util.function.Supplier;

public class ModMusicDisc extends MusicDiscItem {
    /**
     * For mod use, allows to create a music disc without having to create a new
     * SoundEvent before their registry event is fired.
     *
     * @param comparatorValue The value this music disc should output on the comparator. Must be between 0 and 15.
     * @param soundSupplier   A supplier that provides the sound that should be played. Use a
     *                        {@link RegistryObject}{@code <SoundEvent>} or a
     *                        {@link IRegistryDelegate} for this parameter.
     */
    public ModMusicDisc(int comparatorValue, Supplier<SoundEvent> soundSupplier) {
        super(comparatorValue, soundSupplier, new Item.Properties().maxStackSize(1).group(ItemGroup.MISC).rarity(Rarity.RARE));
    }

    /**
     * Called when this item is used when targetting a Block
     *
     * @param context
     */
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return super.onItemUse(context);
    }
}
