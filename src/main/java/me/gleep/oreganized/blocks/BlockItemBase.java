package me.gleep.oreganized.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class BlockItemBase extends BlockItem {


    /**
     * Assign to BUILDING_BLOCKS by default. If you want to change creative tab use {@link BlockItemBase#BlockItemBase(Block, int)}
     * @param block
     * Parent Block for Item Base
     */
    public BlockItemBase(Block block) { this(block, 1); }

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
        super(block, new Item.Properties().group(ItemGroup.GROUPS[creativeTab]));
    }
}
