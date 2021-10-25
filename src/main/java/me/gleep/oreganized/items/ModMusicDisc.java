package me.gleep.oreganized.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.registries.IRegistryDelegate;

import java.util.function.Supplier;

public class ModMusicDisc extends RecordItem {
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
        super(comparatorValue, soundSupplier, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).rarity(Rarity.RARE));
    }

    @Override
    public InteractionResult useOn(UseOnContext p_43048_) {
        return super.useOn(p_43048_);
    }
}
