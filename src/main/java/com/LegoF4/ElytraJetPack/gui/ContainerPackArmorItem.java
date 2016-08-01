package com.LegoF4.ElytraJetPack.gui;

import com.LegoF4.ElytraJetPack.blocks.ArmorTableTileEntity;
import com.LegoF4.ElytraJetPack.items.ItemInventory;
import com.LegoF4.ElytraJetPack.items.PackArmor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPackArmorItem extends Container{
	
	 public ItemInventory inventory;
	 private PackArmor packArmor;
	
	 public ContainerPackArmorItem(IInventory playerInv, ItemInventory inventory) {
		 this.inventory = inventory;
		 
		 /**
		  * SLOTS:
		  * 
		  * Tile Entity 0-8 ........ 0  - 8
		  * Player Inventory 9-35 .. 9  - 35
		  * Player Inventory 0-8 ... 36 - 44
		  **/
		// Tile Entity, Slot 0, Slot IDs 0-2
		 this.addSlotToContainer(new FuelSlot(this.inventory, 0, 79, 35));
		 this.addSlotToContainer(new SingleItemSlot(this.inventory, 1, 36, 54));
		 this.addSlotToContainer(new SingleItemSlot(this.inventory, 2, 114, 54));
		 
		// Player Inventory, Slot 9-35, Slot IDs 3-29
		 for (int y = 0; y < 3; ++y) {
		        for (int x = 0; x < 9; ++x) {
		            this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
		        }
		    }
		 // Player Inventory, Slot 0-8, Slot IDs 30-39
		    for (int x = 0; x < 9; ++x) {
		        this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
		    }

		    
	 }
	 public ItemInventory getInventoryC() {
		 return this.inventory;
	 }
	 @Override
	 public boolean canInteractWith(EntityPlayer playerIn) {
		 return this.inventory.isUseableByPlayer(playerIn);
	}
	 
	 @Override
	 public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		 ItemStack previous = null;
		    Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		    if (slot != null && slot.getHasStack()) {
		        ItemStack current = slot.getStack();
		        previous = current.copy();

		        if (fromSlot < 3) {
		            // From Jetpack Inventory to Player Inventory
		            if (!this.mergeItemStack(current, 3, 39, true))
		                return null;
		        } else {
		            // From Player Inventory to Jetpack Inventory
		            if (!this.mergeItemStack(current, 0, 3, false))
		                return null;
		        }

		        if (current.stackSize == 0 && fromSlot != 0) {
		        	slot.putStack((ItemStack) null);
		        } else if (current.stackSize == 0 && fromSlot == 0) {
		            slot.getStack().stackSize = 0;
		        	slot.onSlotChanged();
		        } else {
		        	slot.onSlotChanged();
		        }
		        if (current.stackSize == previous.stackSize)
		            return null;
		        slot.onPickupFromSlot(playerIn, current);
		    }
		    return previous;
	 }
	 
	 @Override
	 protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {
	     boolean success = false;
	     int index = startIndex;

	     if (useEndIndex)
	         index = endIndex - 1;

	     Slot slot;
	     ItemStack stackinslot;

	     if (stack.isStackable()) {
	         while (stack.stackSize > 0 && (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex)) {
	             slot = (Slot) this.inventorySlots.get(index);
	             stackinslot = slot.getStack();

	             if (stackinslot != null && stackinslot.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == stackinslot.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, stackinslot)) {
	                 int l = stackinslot.stackSize + stack.stackSize;
	                 int maxsizei = Math.min(this.inventory.getInventoryStackLimit(), slot.getItemStackLimit(stack));
	                 int maxsize = Math.min(stack.getMaxStackSize(), maxsizei);

	                 if (l <= maxsize) {
	                     stack.stackSize = 0;
	                     stackinslot.stackSize = l;
	                     slot.onSlotChanged();
	                     success = true;
	                 } else if (stackinslot.stackSize < maxsize) {
	                     stack.stackSize -= stack.getMaxStackSize() - stackinslot.stackSize;
	                     stackinslot.stackSize = stack.getMaxStackSize();
	                     slot.onSlotChanged();
	                     success = true;
	                 }
	             }

	             if (useEndIndex) {
	                 --index;
	             } else {
	                 ++index;
	             }
	         }
	     }

	     if (stack.stackSize > 0) {
	         if (useEndIndex) {
	             index = endIndex - 1;
	         } else {
	             index = startIndex;
	         }

	         while (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex && stack.stackSize > 0) {
	             slot = (Slot) this.inventorySlots.get(index);
	             stackinslot = slot.getStack();

	             // Forge: Make sure to respect isItemValid in the slot.
	             if (stackinslot == null) {
	            	 boolean valid = false;
	            	 if (index < 3)
	            		 valid = this.inventory.isItemValidForSlot(index, stack) ? true : false;
	            	 else {
	            		 valid = slot.isItemValid(stack);
	            	 }
	            	 if (valid) { 
	            		 if (stack.stackSize < slot.getItemStackLimit(stack)) {
		                     slot.putStack(stack.copy());
		                     stack.stackSize = 0;
		                     success = true;
		                     break;
		                 } else {
		                     ItemStack newstack = stack.copy();
		                     newstack.stackSize = slot.getItemStackLimit(stack);
		                     slot.putStack(newstack);
		                     stack.stackSize -= slot.getItemStackLimit(stack);
		                     success = true;
		                 }
	            	 }
	             }

	             if (useEndIndex) {
	                 --index;
	             } else {
	                 ++index;
	             }
	         }
	     }

	     return success;
	 }
	 
}
