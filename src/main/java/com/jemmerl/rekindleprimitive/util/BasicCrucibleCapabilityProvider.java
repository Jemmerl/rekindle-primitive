package com.jemmerl.rekindleprimitive.util;

/*

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class BasicCrucibleCapabilityProvider implements ICapabilitySerializable<INBT> {

    // Extension of ItemStackHandler that only allows permitted items and amounts to be added
    private BasicCrucibleItemStackHandler BasicCrucibleItemStackHandler;

    // This instantiates the Inventory only when it is first requested, and then caches it
    @Nonnull
    private BasicCrucibleItemStackHandler getCachedInventory() {
        if (BasicCrucibleItemStackHandler == null) {
            BasicCrucibleItemStackHandler = new BasicCrucibleItemStackHandler(4);
        }
        return BasicCrucibleItemStackHandler;
    }

    private final LazyOptional<IItemHandler> lazyInventory = LazyOptional.of(this::getCachedInventory);

    // Provides the Inventory
    @Nonnull @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (LazyOptional<T>)(lazyInventory);
        // If we needed to provide more than one capability, we'd simply add another check:
        // if (cap == SOME_OTHER_CAPABILITY) return (otherCapability)
        // TODO May need to do here if hot crucible wont be separate item

        return LazyOptional.empty();
    }

    // below this is good

    @Override
    public INBT serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedInventory(), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedInventory(), null, nbt);
    }


}
*/