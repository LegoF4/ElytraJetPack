package com.LegoF4.ElytraJetPack.items;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemEnderEye;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemFireworkCharge;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.UniversalBucket;

public class ItemInventory implements IInventory{
	
	private String customName;
	private final ItemStack invItem;
	private ItemStack[] inventory;
	private int slots = 1;
	public int itemsStored;
	public int itemsMax;
	private int stackSize;
	
	public ItemInventory(ItemStack stack, String customNameI, int itemsMax) {
		this.customName = customNameI;
		this.inventory = new ItemStack[this.getSizeInventory()];
		this.invItem = stack;
		this.itemsMax = itemsMax - 32;
		this.stackSize = 64;
		readFromNBT(invItem.getTagCompound());
	}
	
	public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }
    
    public int getStoredItems() {
    	return this.itemsStored;
    }
    
    public int getMaxItems() {
    	return this.itemsMax;
    }
	
	@Override
	public String getName() {
	    return this.hasCustomName() ? this.customName : "container.tutorial_tile_entity";
	}

	@Override
	public boolean hasCustomName() {
	    return this.customName != null && !this.customName.equals("");
	}

	@Override
	public ITextComponent getDisplayName() {
	    return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentString(this.getName());
	}
	
	@Override
	public int getSizeInventory() {
	    return 3;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
	    if (index < 0 || index >= this.getSizeInventory())
	        return null;
	    return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
	    if (this.getStackInSlot(index) != null) {
	        ItemStack itemstack;
	        if (this.getStackInSlot(index).stackSize <= count) {
	            itemstack = this.getStackInSlot(index);
	            this.removeStackFromSlot(index);
	            this.markDirty();
	            return itemstack;
	        } 
	        else {
	            itemstack = this.getStackInSlot(index).splitStack(count);

	            if (this.getStackInSlot(index).stackSize <= 0) {
	                this.removeStackFromSlot(index);
	            }
	            this.markDirty();
	            return itemstack;
	        }
	    } 
	    else {
	        return null;
	    }
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
	    if (index < 0 || index >= this.getSizeInventory())
	        return;
	    if (!isItemValidForSlot(index, stack)) {
	    	return;
	    }
	    if (stack != null && stack.stackSize > this.getInventoryStackLimit())
	        stack.stackSize = this.getInventoryStackLimit();
	        
	    if (stack != null && stack.stackSize == 0)
	        stack = null;
	    if (index == 0 && stack != null) {
	    	//this.itemsStored = stack.stackSize;
	    }
	    this.inventory[index] = stack;
	    this.markDirty();
	}
	
	@Override
    public int getInventoryStackLimit() {
		if (this.itemsStored >= this.itemsMax) {
			return 32;
		}
        return 64;
    }
	
	@Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }
	
	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (stack == null)
			return true;
		Item item = stack.getItem();
		if (item instanceof PackArmor)
			return false;
		else if ((item instanceof IFluidContainerItem || item instanceof UniversalBucket || item instanceof ItemBucket)  && index == 1)
			return true;
		else if (item instanceof IEnergyContainerItem && index == 2) 
			return true;
		else if (item instanceof ItemFireball && index == 0)
			return true;
		else
			return false;
	}
	
	@Override
	public int getField(int id) {
	    return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
	    return 0;
	}
	
	@Override
	public void clear() {
		this.itemsStored = 0;
	    for (int i = 0; i < this.getSizeInventory(); i++)
	        this.setInventorySlotContents(i, null);
	    this.markDirty();
	}
	@Override
	public ItemStack removeStackFromSlot(int index) {
		readFromNBT(this.invItem.getTagCompound());
		if (index == 0 && this.itemsStored + this.inventory[0].stackSize <= 0) {
    		this.itemsStored = 0;
    		this.inventory[0].stackSize = 0;
    	}
		if (index == 0 && this.itemsStored + 32 > this.inventory[0].stackSize) {
			this.inventory[0].stackSize = 0;
			this.markDirty();
		}
		else {
			this.setInventorySlotContents(index, null);
		}
    	this.markDirty();
		return this.getStackInSlot(index);
	}
	@Override
	public void markDirty() {
		if (this.inventory[0] != null) {
	    	if (this.inventory[0].stackSize <= 32 && this.itemsStored > 0) {
	    		int used = Math.min(this.itemsStored, 32-this.inventory[0].stackSize);
	    		this.inventory[0].stackSize += used;
	    		this.itemsStored -= used;
	    	}
	    	if (this.inventory[0].stackSize > 32) {
	    		this.itemsStored += this.inventory[0].stackSize-32;
	    		this.inventory[0].stackSize = 32;
	    	}
	    }
		if (this.itemsStored > this.itemsMax) {
			this.itemsStored = this.itemsMax;
		}
		for (int i = 0; i < getSizeInventory(); ++i)
		{
			if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
				this.inventory[i] = null;
			}
		}
		// This line here does the work:		
		writeToNBT(invItem.getTagCompound());
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
	    NBTTagList list = new NBTTagList();
	    for (int i = 0; i < this.getSizeInventory(); ++i) {
	        if (this.getStackInSlot(i) != null) {
	            NBTTagCompound stackTag = new NBTTagCompound();
	            stackTag.setInteger("Slot", i);
	            this.getStackInSlot(i).writeToNBT(stackTag);
	            stackTag.setBoolean("Nulled", false);
	            list.appendTag(stackTag);
	        }
	        else {
	        	NBTTagCompound stackTag = new NBTTagCompound();
	        	stackTag.setInteger("Slot", i);
	        	stackTag.setBoolean("Nulled", true);
	        	list.appendTag(stackTag);
	        }
	    }
	    nbt.setTag("Items", list);
	    nbt.setInteger("ItemsStored", this.itemsStored);
	    if (this.hasCustomName()) {
	        nbt.setString("CustomName", this.getCustomName());
	    }
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
	    this.itemsStored = nbt.getInteger("ItemsStored");
	    for (int i = 0; i < this.getSizeInventory(); ++i) {
	    	this.inventory[i] = null;
	    }
	    NBTTagList list = nbt.getTagList("Items", 10);
	    for (int i = 0; i < list.tagCount(); ++i) {
	        NBTTagCompound stackTag = list.getCompoundTagAt(i);
	        int slot = stackTag.getInteger("Slot");
	        if (!stackTag.getBoolean("Nulled")) {
	        	this.inventory[slot] = ItemStack.loadItemStackFromNBT(stackTag);
		        this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
	        }
	        else {
	        	this.inventory[slot] = null;
		        this.setInventorySlotContents(slot, null);
	        }
	        
	    }
	    if (nbt.hasKey("CustomName", 8)) {
	        this.setCustomName(nbt.getString("CustomName"));
	    }
	}
	
}
