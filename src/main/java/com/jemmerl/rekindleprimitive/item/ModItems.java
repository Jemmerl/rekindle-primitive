package com.jemmerl.rekindleprimitive.item;

import com.jemmerl.rekindleprimitive.item.uniqueitems.BasicCrucible;
import net.minecraft.item.*;
import com.jemmerl.rekindleprimitive.RekindlePrimitive;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RekindlePrimitive.MOD_ID);

    // Create items
    public static final RegistryObject<Item> ASH = ITEMS.register("fire_ash",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP)));

    public static final RegistryObject<Item> STRAW_GRASS = ITEMS.register("straw_grass",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP)) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable IRecipeType<?> recipeType) { return 10; }
            });

    public static final RegistryObject<Item> BARK_STRIPS = ITEMS.register("bark_strips",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP)) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable IRecipeType<?> recipeType) { return 100; }
            });

    public static final RegistryObject<Item> DRY_UNFIRED_BRICK = ITEMS.register("dry_unfired_brick",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP)));

    public static final RegistryObject<Item> LIME_MORTAR = ITEMS.register("lime_mortar",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP)));

    public static final RegistryObject<Item> BRICK_CLAY = ITEMS.register("brick_clay",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP)));

    public static final RegistryObject<Item> HOT_BRICK = ITEMS.register("hot_brick",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP).maxStackSize(1)));

    public static final RegistryObject<Item> BRICK_MOLD = ITEMS.register("wooden_brick_mold",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP).maxStackSize(16)));

    public static final RegistryObject<Item> IMP_CAO = ITEMS.register("impure_calcium_oxide",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP)));

    public static final RegistryObject<Item> PELT = ITEMS.register("animal_pelt",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP).maxStackSize(32)));

    public static final RegistryObject<Item> SCRAPED_HIDE = ITEMS.register("scraped_hide",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP).maxStackSize(32)));

    public static final RegistryObject<Item> SOAKED_HIDE = ITEMS.register("soaked_hide",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP).maxStackSize(32)));

    public static final RegistryObject<Item> PREPARED_HIDE = ITEMS.register("prepared_hide",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP).maxStackSize(32)));

    public static final RegistryObject<Item> WET_TANNED_HIDE = ITEMS.register("wet_tanned_hide",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP).maxStackSize(32)));

    public static final RegistryObject<Item> LEATHER_STRIPS = ITEMS.register("leather_strips",
            () -> new Item(new Item.Properties().group(ModItemGroup.REKINDLEGROUP)));

    public static final RegistryObject<Item> DIGGING_STICK = ITEMS.register("digging_stick",
            () -> new ShovelItem(ModItemTier.BASIC, 1.0F, -3.0F,
                    new Item.Properties().group(ModItemGroup.REKINDLEGROUP)));

    public static final RegistryObject<Item> BONE_HOE = ITEMS.register("bone_shard_hoe",
            () -> new HoeItem(ModItemTier.BASIC, 0, -3.0F,
                    new Item.Properties().group(ModItemGroup.REKINDLEGROUP)));

    public static final RegistryObject<Item> BASIC_CRUCIBLE = ITEMS.register("basic_crucible",
            () -> new BasicCrucible(new Item.Properties().group(ModItemGroup.REKINDLEGROUP).maxStackSize(1)));

    // Item registry method
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
