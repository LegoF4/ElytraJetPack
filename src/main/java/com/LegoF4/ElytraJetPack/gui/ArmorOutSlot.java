package com.LegoF4.ElytraJetPack.gui;

import javax.annotation.Nullable;

import com.LegoF4.ElytraJetPack.blocks.ArmorTableTileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ArmorOutSlot extends Slot{
	
	public ArmorOutSlot(ArmorTableTileEntity inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
    @Override
    public boolean isItemValid(@Nullable ItemStack stack)
    {
    	return false;
    }
    @Override
    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
    {
        super.onPickupFromSlot(playerIn, stack);
        for (int i = 1; i < 15; i++) {
        	inventory.setInventorySlotContents(i, null);
        }
    }
}
