package com.LegoF4.ElytraJetPack.gui;

import javax.annotation.Nullable;

import com.LegoF4.ElytraJetPack.items.ItemInventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FuelSlot extends Slot{
	
	private ItemInventory inventory;
	
	public FuelSlot(ItemInventory inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
        this.inventory = inventory;
    }

    @Override
    public int getSlotStackLimit() {
        return 64;
    }
    @Override
    public void onSlotChanged() {
    	this.inventory.markDirty();
    }
    @Override
    public boolean isItemValid(@Nullable ItemStack stack)
    {
    	if (stack == null) return true;
    	if (this.inventory.isItemValidForSlot(this.getSlotIndex(), stack) && stack.stackSize + this.inventory.getStoredItems() <= this.inventory.getMaxItems()) return true;
    	else return false;
    }
 }
