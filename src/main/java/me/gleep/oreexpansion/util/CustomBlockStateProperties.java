package me.gleep.oreexpansion.util;

import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class CustomBlockStateProperties extends BlockStateProperties {
    public static final IntegerProperty CONTENTS_0_2 = IntegerProperty.create("content", 0, 2);
}
