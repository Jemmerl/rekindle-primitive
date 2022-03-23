package com.jemmerl.rekindleprimitive.item.uniqueitems;

import com.jemmerl.rekindleprimitive.inventory.container.BasicCrucibleContainer;
import com.jemmerl.rekindleprimitive.util.BasicCrucibleItemStackHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BasicCrucible extends Item {

    public BasicCrucible(Properties properties) {
        super(properties);
    }

    @Override @Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        BasicCrucibleItemStackHandler handler = new BasicCrucibleItemStackHandler(4, stack);
        return handler;
    }

    private ItemStackHandler getItemHandler(ItemStack stack) {
        return (ItemStackHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .orElse(new BasicCrucibleItemStackHandler(4, stack));
    }

    @Override
    @ParametersAreNonnullByDefault
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        INamedContainerProvider containerProvider = new INamedContainerProvider() {

            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.rekindleprimitive.basic_crucible");
            }

            @Override
            @Nonnull
            public Container createMenu(int id, PlayerInventory inventory, PlayerEntity playerIn) {
                return new BasicCrucibleContainer(id, inventory, getItemHandler(itemStack));
            }
        };
        if (!worldIn.isRemote()) {
            NetworkHooks.openGui(((ServerPlayerEntity) playerIn), containerProvider);
        }
        return ActionResult.resultSuccess(itemStack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }
}
