package com.LegoF4.ElytraJetPack.gui;

import com.LegoF4.ElytraJetPack.blocks.ArmorTableTileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerArmorTableTileEntity extends Container{
	
	 private ArmorTableTileEntity te;
	
	 public ContainerArmorTableTileEntity(IInventory playerInv, ArmorTableTileEntity te) {
		 this.te = te;
		 
		 /**
		  * SLOTS:
		  * 
		  * Tile Entity 0-8 ........ 0  - 8
		  * Player Inventory 9-35 .. 9  - 35
		  * Player Inventory 0-8 ... 36 - 44
		  **/
		 
		// Tile Entity, Slot 0-8, Slot IDs 0-8
		 for (int y = 0; y < 3; ++y) {
			 for (int x = 0; x < 3; ++x) {
		            this.addSlotToContainer(new Slot(te, x + y * 3, 62 + x * 18, 17 + y * 18));
		            }
		 }
		 
		// Player Inventory, Slot 9-35, Slot IDs 9-35
		 for (int y = 0; y < 3; ++y) {
		        for (int x = 0; x < 9; ++x) {
		            this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
		        }
		    }
		 // Player Inventory, Slot 0-8, Slot IDs 36-44
		    for (int x = 0; x < 9; ++x) {
		        this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
		    }

		    
	 }
	 
	 @Override
	 public boolean canInteractWith(EntityPlayer playerIn) {
		 return this.te.isUseableByPlayer(playerIn);
	}
	 
	 @Override
	 public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		 return null;
	 }
	 
}
