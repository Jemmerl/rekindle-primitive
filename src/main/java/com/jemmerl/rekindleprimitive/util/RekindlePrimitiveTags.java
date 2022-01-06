package com.jemmerl.rekindleprimitive.util;

import com.jemmerl.rekindleprimitive.RekindlePrimitive;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class RekindlePrimitiveTags {

    public static class Blocks {

        // Block tags go here

        private static Tags.IOptionalNamedTag<Block> createTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(RekindlePrimitive.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Block> createForgeTag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }

    }

    public static class Items {

        // Item (items or blocks as items) tags go here
        public static final Tags.IOptionalNamedTag<Item> BINDINGS = createTag("bindings");

        private static Tags.IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(RekindlePrimitive.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Item> createForgeTag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }

    }
}
