package me.gleep.oreganized.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockItemBase extends BlockItem {


    /**
     * Assign to BUILDING_BLOCKS by default. If you want to change creative tab use {@link BlockItemBase#BlockItemBase(Block, int)}
     * @param block
     * Parent Block for Item Base
     */
    public BlockItemBase(Block block) { this(block, 0); }

    /**
     * @param block
     * Parent Block for Item Block
     * @param creativeTab
     * 0 - BUILDING_BLOCKS/
     * 1 - DECORATIONS/
     * 2 - REDSTONE/
     * 3 - TRANSPORTATION/
     * 6 - MISC/
     * 7 - FOOD/
     * 8 - TOOLS/
     * 9 - COMBAT/
     * 10 - BREWING/
     */
    public BlockItemBase(Block block, int creativeTab) {
        super(block, new Item.Properties().tab(CreativeModeTab.TABS[creativeTab]));
    }
}
