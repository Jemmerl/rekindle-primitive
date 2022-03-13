package com.jemmerl.rekindleprimitive.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class BasicCrucibleContainer extends Container {


    private final IItemHandler playerInventory;
    private final PlayerEntity playerEntity;
    private final ItemStackHandler itemStackHandler;


    public BasicCrucibleContainer(int windowId, PlayerInventory playerInv, PacketBuffer data) {
        this(windowId, playerInv, new ItemStackHandler(4));
    }

    public BasicCrucibleContainer(int id, PlayerInventory playerInventory, ItemStackHandler itemStackHandler) {
        super(ModContainers.BASIC_CRUCIBLE_CONTAINER.get(), id);

        this.playerInventory = new InvWrapper(playerInventory);
        this.playerEntity = playerInventory.player;
        this.itemStackHandler = itemStackHandler;

        layoutPlayerInventorySlots(8, 86); // start layout at 8 pix from left, 86 from top

        // Add crucible slots
        addSlot(new SlotItemHandler(itemStackHandler, 0, 71, 31));
        addSlot(new SlotItemHandler(itemStackHandler, 1, 89, 31));
        addSlot(new SlotItemHandler(itemStackHandler, 2, 71, 49));
        addSlot(new SlotItemHandler(itemStackHandler, 3, 89, 49));
    }

    // Adds player inventory slots
    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Add main inventory space
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
        topRow += 58;
        // Add hotbar
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    // Builds a box of slots
    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    // Builds a line of slots
    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            if ((handler == playerInventory) && (playerEntity.inventory.currentItem == index)) {
                // Prevents interaction with crucible item
                addSlot(new SlotLocked(handler, index, x, y));
            } else {
                addSlot(new SlotItemHandler(handler, index, x, y));
            }
            x += dx;
            index++;
        }
        return index;
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return true;
    }

    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack itemStackCopy = ItemStack.EMPTY;
        Slot slot = this.getSlot(index);
        if (slot.getHasStack()) {
            ItemStack itemStackOG = slot.getStack();
            itemStackCopy = itemStackOG.copy();
            int size = 4;
            if (index < size) {
                if (!mergeItemStack(itemStackOG, size, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(itemStackOG, 0, size, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStackOG.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemStackCopy;
    }

}
