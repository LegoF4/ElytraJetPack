package com.LegoF4.ElytraJetPack.gui;

import javax.annotation.Nullable;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SingleItemSlot extends Slot{
	
	public SingleItemSlot(IInventory inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
    @Override
    public boolean isItemValid(@Nullable ItemStack stack)
    {
    	if (stack == null) return true;
    	if (this.inventory.isItemValidForSlot(this.getSlotIndex(), stack)) return true;
    	else return false;
    }
}
