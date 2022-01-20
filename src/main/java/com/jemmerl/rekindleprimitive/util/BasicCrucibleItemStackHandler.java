package com.jemmerl.rekindleprimitive.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/*
 * Credit to:
 * https://github.com/VazkiiMods/Quark/blob/937bff1ea78005dfea046b2ec6ec97e4f27e3ed2/src/main/java/vazkii/quark/base/handler/ProxiedItemStackHandler.java
 * and
 * https://github.com/LeoBeliik/ConvenientCuriosContainer/blob/master/src/main/java/com/leobeliik/convenientcurioscontainer/common/ConvenientStackHandler.java
 */

public class BasicCrucibleItemStackHandler extends ItemStackHandler implements ICapabilityProvider {

    private final int size;
    private final ItemStack stack;
    private final int slotLimit = 4;

    public BasicCrucibleItemStackHandler(int size, ItemStack stack) {
        this.size = size;
        this.stack = stack;
    }

    // Only four items per stack, per slot
    @Override
    public int getSlotLimit(int slot) {
        return this.slotLimit;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, LazyOptional.of(() -> this));
    }

    @Override
    protected void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= size) {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + size + ")");
        }
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        validateSlotIndex(slot);
        writeStack(slot, stack);
        onContentsChanged(slot);
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        validateSlotIndex(slot);
        return readStack(slot);
    }

    private void writeStack(int index, @Nonnull ItemStack stack) {
        getStackList().set(index, stack.serializeNBT());
        onContentsChanged(index);
    }

    private ItemStack readStack(int index) {
        return ItemStack.read(getStackList().getCompound(index));
    }

    private ListNBT getStackList() {
        String key = "basic_crucible_inventory";
        ListNBT list;

        if (!stack.isEmpty() && stack.hasTag() && stack.getOrCreateTag().contains(key)) {
            list = stack.getOrCreateTag().getList(key, Constants.NBT.TAG_COMPOUND);
        } else {
            stack.getOrCreateTag().put(key, list = new ListNBT());
        }

        while (list.size() < size) {
            list.add(new CompoundNBT());
        }
        return list;
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        validateSlotIndex(slot);

        ItemStack existing = readStack(slot);

        int limit = getStackLimit(slot, stack);

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing)) {
                return stack;
            }
            limit -= existing.getCount();
        }

        if (limit <= 0) {
            return stack;
        }

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            writeStack(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0) {
            return ItemStack.EMPTY;
        }

        validateSlotIndex(slot);

        ItemStack existing = readStack(slot);

        if (existing.isEmpty()) {
            return ItemStack.EMPTY;
        }

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                writeStack(slot, ItemStack.EMPTY);
            }
            return existing;
        } else {
            if (!simulate) {
                writeStack(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
            }
            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

}
