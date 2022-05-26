package com.jemmerl.rekindleprimitive.block;

import com.jemmerl.rekindleprimitive.RekindlePrimitive;
import com.jemmerl.rekindleprimitive.block.uniqueblocks.DryBrickMold;
import com.jemmerl.rekindleprimitive.block.uniqueblocks.WetBrickMold;
import com.jemmerl.rekindleprimitive.item.ModItemGroup;
import com.jemmerl.rekindleprimitive.item.ModItems;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, RekindlePrimitive.MOD_ID);

    // Create blocks
    public static final RegistryObject<Block> WET_BRICK_MOLD = registerBlock("wet_brick_mold",
            () -> new WetBrickMold(AbstractBlock.Properties.create(Material.LEAVES).tickRandomly().notSolid().hardnessAndResistance(0.5f)), 16,"tooltip.block.rekindlemod.wet_brick_mold");

    public static final RegistryObject<Block> DRY_BRICK_MOLD = registerBlock("dry_brick_mold",
            () -> new DryBrickMold(AbstractBlock.Properties.create(Material.LEAVES).notSolid().hardnessAndResistance(0.5f)), 16);

    // Block and BlockItem registry methods
    // OVERLOAD FOR TOOLTIPS
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, Integer stackSize, String tooltipKey) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, stackSize, tooltipKey);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, Integer stackSize, String tooltipKey) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.REKINDLEGROUP).maxStackSize(stackSize)) {
            @Override
            public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
                if(!Screen.hasShiftDown()) {
                    tooltip.add(new TranslationTextComponent("tooltip.all.rekindleprimitive.shiftinfo"));
                } else {
                    tooltip.add(new TranslationTextComponent(tooltipKey));
                }
                super.addInformation(stack, worldIn, tooltip, flagIn);
            }
        });
    }


    // STANDARD
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, Integer stackSize) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, stackSize);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, Integer stackSize) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.REKINDLEGROUP).maxStackSize(stackSize)));
    }

    public static void register(IEventBus eventBus) { BLOCKS.register(eventBus); }

}
