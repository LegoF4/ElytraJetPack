package com.LegoF4.ElytraJetPack.blocks;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.gui.GuiHandler;
import com.LegoF4.ElytraJetPack.items.ItemManager;
import com.LegoF4.ElytraJetPack.items.JetpackAvionics;
import com.LegoF4.ElytraJetPack.items.JetpackCore;
import com.LegoF4.ElytraJetPack.items.JetpackEngine;
import com.LegoF4.ElytraJetPack.items.JetpackModule;
import com.LegoF4.ElytraJetPack.items.JetpackModuleFluid;
import com.LegoF4.ElytraJetPack.items.PackArmor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ArmorTableTileEntity extends TileEntity  implements ITickable, IInventory{
	
	private ItemStack[] inventory;
	private String customName;
	
	public ArmorTableTileEntity() {
		this.inventory = new ItemStack[this.getSizeInventory()];
		this.customName = "Armor Assembly Table";
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
	    super.writeToNBT(nbt);

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

	    if (this.hasCustomName()) {
	        nbt.setString("CustomName", this.getCustomName());
	    }
	    return nbt;
	}


	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	    super.readFromNBT(nbt);

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
	
	public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }
	
    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.armorTable";
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
        return 15;
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
                this.setInventorySlotContents(index, null);
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.getStackInSlot(index).splitStack(count);

                if (this.getStackInSlot(index).stackSize <= 0) {
                    this.setInventorySlotContents(index, null);
                } else {
                    //Just to show that changes happened
                    this.setInventorySlotContents(index, this.getStackInSlot(index));
                }

                this.markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < 0 || index >= this.getSizeInventory())
            return;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
            stack.stackSize = this.getInventoryStackLimit();
            
        if (stack != null && stack.stackSize == 0)
            stack = null;

        this.inventory[index] = stack;
        this.markDirty();
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
    }
    
    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
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
    public boolean isItemValidForSlot(int index, ItemStack stack) {
    	if (stack.getItem() instanceof PackArmor && index == 0)
    		return true;
    	if (index == 6 && stack.getItem() instanceof JetpackCore)
    		return true;
    	if (index == 1 && stack.getItem() instanceof JetpackAvionics)
    		return true;
    	if (index == 12 && stack.getItem() instanceof JetpackEngine)
    		return true;
    	if (index > 1 && index < 11 && index != 6 && stack.getItem() instanceof JetpackModule)
    		return true;
        return false;
    }
    
    @Override
	public ItemStack removeStackFromSlot(int index) {
    	
    	this.setInventorySlotContents(index, null);
    	ItemStack stack = this.getStackInSlot(index);
		return stack;
	}
    
    @Override
    public void clear() {
        for (int i = 0; i < this.getSizeInventory(); i++)
            this.setInventorySlotContents(i, null);
    }
    
	public static void init() {
        GameRegistry.registerTileEntity(ArmorTableTileEntity.class, "armorTable");
    }
	
	@Override
    public void update() {
		
		if (this.inventory[6] != null) {
			if (this.inventory[6].getItem() instanceof JetpackCore) {
				ItemStack stack = new ItemStack(ItemManager.PackCompound, 1);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("Tier", this.inventory[6].getTagCompound().getInteger("Tier"));
				nbt.setInteger("MaxModules",  this.inventory[6].getTagCompound().getInteger("MaxModules"));
				nbt.setInteger("EnergyStored", 0);
				int tier = nbt.getInteger("Tier");
				float drag =  this.inventory[6].getTagCompound().getFloat("Drag");
				int weight =  this.inventory[6].getTagCompound().getInteger("Weight");
				int avionicsInt = 0;
				int modules = 0;
				int maxmodules = this.inventory[6].getTagCompound().getInteger("MaxModules");
				NBTTagList list = new NBTTagList();
				if (this.inventory[1] != null) {
					if (this.inventory[1].getItem() instanceof JetpackAvionics) {
						avionicsInt = this.inventory[1].getTagCompound().getInteger("Tier");
						if (avionicsInt > tier)
							avionicsInt = tier;
						weight += this.inventory[1].getTagCompound().getInteger("Weight");
						
						NBTTagCompound avionics = new NBTTagCompound();
						avionics.setString("Type", "Av");
						avionics.setInteger("Tier", tier);
						avionics.setInteger("Slot", avionicsInt);
						list.appendTag(avionics);
					}
					else {
						NBTTagCompound avionics = new NBTTagCompound();
						avionics.setString("Type", "Null");
						list.appendTag(avionics);
					}
				}
				else {
					NBTTagCompound avionics = new NBTTagCompound();
					avionics.setString("Type", "Null");
					list.appendTag(avionics);
				}
				nbt.setInteger("Avionics", avionicsInt);
				int energyCap = 0;
				int fluidCap = 0;
				int fluidI = 0;
				int fluidO = 0;
				int energyIO = 0;
				int rfDrain = 0;
				int fDrain = 0;
				for (int i = 2; i < 11; i++) {
					if (i != 6) {
						if (this.inventory[i] != null) {
							if (this.inventory[i].getItem() instanceof JetpackModule) {
								modules++;
								NBTTagCompound nbtI = this.inventory[i].getTagCompound();
								if (this.inventory[i].getItem() instanceof JetpackModuleFluid && nbtI.getString("Type").charAt(0) == 'T') {
									fluidCap += nbtI.getInteger("Storage");
									fluidI += nbtI.getInteger("Input");
									fluidO += nbtI.getInteger("Output");
									fDrain += nbtI.getInteger("Drain");
								}
								NBTTagCompound mod1 = new NBTTagCompound();
								mod1.setString("Type", "Mod");
								mod1.setString("Module", nbtI.getString("Type"));
								mod1.setInteger("Slot", i);
								list.appendTag(mod1);
							}
						}
						
					}
				}
				if (this.inventory[12] != null) {
					if (this.inventory[12].getItem() instanceof JetpackEngine) {
						weight += this.inventory[12].getTagCompound().getInteger("Weight");
					}
				}
				nbt.setInteger("CapFluid", fluidCap);
				nbt.setInteger("FluidIO", fluidI);
				nbt.setInteger("FuelDrain", fDrain);
				nbt.setInteger("CapEnergy", energyCap);
				nbt.setInteger("EnergyStored", 0);
				nbt.setInteger("EnergyIO", energyIO);
				nbt.setInteger("EnergyDrain", rfDrain);
				nbt.setTag("Modules", list);
				stack.setTagCompound(nbt);
				this.setInventorySlotContents(14, stack);
			}
		}
		if (this.inventory[0] != null) {
			boolean empty = true;
			for (int i = 1; i < 13; i++) {
				if (this.inventory[i] != null) {
					empty = false;
				}
			}
			if (empty) {
				ItemStack inputStack = this.inventory[0];
				NBTTagCompound nbtIn = inputStack.getTagCompound().getCompoundTag("Modules");
			}
		}
	}
}
