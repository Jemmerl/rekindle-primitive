package com.jemmerl.rekindleprimitive.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {

    public static final ItemGroup REKINDLEGROUP = new ItemGroup("rekindlePrimitiveTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.STRAW_GRASS.get());
        }
    };

}