package com.LegoF4.ElytraJetPack.items;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.capabilties.IJetHover;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;
import com.LegoF4.ElytraJetPack.gui.GuiHandler;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemEnderEye;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemFireworkCharge;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PackArmor extends ItemArmor implements IFluidContainerItem, IEnergyContainerItem//, IInventory
{
	
	/*private ItemStack[] inventory;
	private String customName;*/
	
	public PackArmor(String unlocalizedName, ArmorMaterial material, int renderIndex, EntityEquipmentSlot armorType) {
        super(material, renderIndex, armorType);
        this.setUnlocalizedName(unlocalizedName);
        this.setMaxDamage(1);
        this.setHasSubtypes(true);
        this.setCreativeTab(ItemManager.tabElytraJet);
        this.setNoRepair();
        /*this.inventory = new ItemStack[this.getSizeInventory()];
        this.setCustomName(this.getDisplayName().getUnformattedText());*/
        
        
    }
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List itemList) {
		ItemStack itemStack = new ItemStack(item);
		FluidStack fluidStack = new FluidStack(FluidRegistry.LAVA, 60000);
		itemStack.setTagCompound(new NBTTagCompound());
	
		NBTTagCompound fluidTag = fluidStack.writeToNBT(new NBTTagCompound());

		itemStack.getTagCompound().setInteger("Tier", Character.getNumericValue(itemStack.getUnlocalizedName().charAt(6)));
		itemStack.getTagCompound().setInteger("Modules", itemStack.getTagCompound().getInteger("Tier") == 1 ? 0 : itemStack.getTagCompound().getInteger("Tier"));
		itemStack.getTagCompound().setTag("Fluid", fluidTag);
		itemStack.getTagCompound().setInteger("Weight", 55);
		itemStack.getTagCompound().setInteger("Thrust", 330);
		itemStack.getTagCompound().setFloat("Drag", 0.9F);
		itemStack.getTagCompound().setInteger("Thruster", 0);
		//Fluids
		itemStack.getTagCompound().setInteger("Tanks", 3);
		itemStack.getTagCompound().setInteger("CapTanks", 20000);
		itemStack.getTagCompound().setInteger("CapFluid", itemStack.getTagCompound().getInteger("Tanks")*itemStack.getTagCompound().getInteger("CapTanks"));
		itemStack.getTagCompound().setInteger("FluidIO", 512);
		ArrayList<Integer> fuelValues = new ArrayList();
		fuelValues.add(11); 
		fuelValues.add(1);
		fuelValues.add(5);
		int fuelUsage = fuelValues.get(itemStack.getTagCompound().getInteger("Thruster"));
		fuelUsage *= itemStack.getTagCompound().getInteger("Thrust");
		fuelUsage = (int) fuelUsage/130;
		itemStack.getTagCompound().setInteger("FuelUsage", fuelUsage);
		//Energy
		itemStack.getTagCompound().setInteger("Batteries", 2);
		itemStack.getTagCompound().setInteger("CapBatteries", 100000);
		itemStack.getTagCompound().setInteger("BatteryIO", 512);
		itemStack.getTagCompound().setInteger("CapEnergy", itemStack.getTagCompound().getInteger("Batteries")*itemStack.getTagCompound().getInteger("CapBatteries"));
		itemStack.getTagCompound().setInteger("EnergyIO", itemStack.getTagCompound().getInteger("BatteryIO"));
		itemStack.getTagCompound().setInteger("EnergyStored", itemStack.getTagCompound().getInteger("CapEnergy")/8);
		ArrayList<Integer> energyValues = new ArrayList();
		energyValues.add(1);
		energyValues.add(8);
		energyValues.add(5);
		int energyUsage = fuelValues.get(itemStack.getTagCompound().getInteger("Thruster"));
		energyUsage *= itemStack.getTagCompound().getInteger("Thrust");
		energyUsage = (int) energyUsage/124;
		itemStack.getTagCompound().setInteger("EnergyUsage", energyUsage);
		//Armor
		itemStack.getTagCompound().setInteger("Plates", 1);
		itemStack.getTagCompound().setInteger("ArmorType", 0);
		itemStack.getTagCompound().setInteger("ArmorEnergyUsage", 100);
		int iarmor = itemStack.getTagCompound().getInteger("Tier")*2;
		iarmor += 1;
		itemStack.getTagCompound().setInteger("ArmorReduction", iarmor);

		itemStack.getTagCompound().setInteger("Avionics", 2);
		itemStack.getTagCompound().setInteger("ItemsMax", 128);
		itemStack.getTagCompound().setInteger("ItemsStored", 0);
		itemList.add(itemStack);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player,List tooltip, boolean isAdvanced) {
		IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
		FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag("Fluid"));
		ArrayList<String> names = new ArrayList();
		names.add("Disabled");
		names.add("Jetpack");
		names.add("Elytrapack");
		tooltip.add("Mode: " + names.get(jetMode.isJetMode()));
		if (fluidStack != null) {
			tooltip.add("Fuel: " + NumberFormat.getIntegerInstance().format(fluidStack.amount) + "/" + NumberFormat.getIntegerInstance().format(stack.getTagCompound().getInteger("CapFluid")) + " mB of " + fluidStack.getLocalizedName());
		}
		else {
			tooltip.add("Fuel: 0/" + NumberFormat.getIntegerInstance().format(stack.getTagCompound().getInteger("CapFluid")) + "mb of Fluid");
		}
        tooltip.add("Stored Charge: " + NumberFormat.getIntegerInstance().format(stack.getTagCompound().getInteger("EnergyStored")) + "/" + NumberFormat.getIntegerInstance().format(stack.getTagCompound().getInteger("CapEnergy")) +" RF");
        IJetHover jetHover = player.getCapability(Main.JETHOVER_CAP, null);
        int storedCharges;
        if (stack.getTagCompound().hasKey("Items")) {
        	if (stack.getTagCompound().getTagList("Items", 10).getCompoundTagAt(0) != null) {
        		ItemStack charges = ItemStack.loadItemStackFromNBT(stack.getTagCompound().getTagList("Items", 10).getCompoundTagAt(0));
        		if (charges != null)
        			if (charges.getItem() instanceof ItemFireball)
        				storedCharges = stack.getTagCompound().getInteger("ItemsStored") + charges.stackSize;
        			else
        				storedCharges = 0;
        		else
        			storedCharges = 0;
        	}
        	else storedCharges = 0;
        }
        else storedCharges = 0;
        
        tooltip.add("Solid Chagres: " + storedCharges + "/" + stack.getTagCompound().getInteger("ItemsMax"));
        tooltip.add("Hover: " + (jetHover.isJetHovering() == true ? "Enabled" : "Disabled"));
        if(GuiScreen.isShiftKeyDown()){
        	ArrayList<String> thrusters = new ArrayList();
        	thrusters.add("Chemical");
        	thrusters.add("Ion");
        	thrusters.add("Plasma");
        	tooltip.add("Engines: " + "Tier " + "1" + " " + thrusters.get(stack.getTagCompound().getInteger("Thruster")) + " Thrusters");
        	tooltip.add("Weight: " + stack.getTagCompound().getInteger("Weight") + "kg");
        	tooltip.add("Thrust: " + stack.getTagCompound().getInteger("Thrust") + " Newtons");
        	tooltip.add("Drag Factor: " + stack.getTagCompound().getFloat("Drag"));
        	tooltip.add("Fuel Usage: " + Integer.toString(stack.getTagCompound().getInteger("FuelUsage")) + " mB/t");
        	tooltip.add("Energy Usage: " + Integer.toString(stack.getTagCompound().getInteger("EnergyUsage")) + " RF/t");
      	}
        else {
      	  tooltip.add(TextFormatting.BLUE + "<Press SHIFT for more>");
        }
    }
	
	
	 public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		FluidStack fluidStack = new FluidStack(FluidRegistry.LAVA, 60000);
		itemStack.setTagCompound(new NBTTagCompound());
	
		NBTTagCompound fluidTag = fluidStack.writeToNBT(new NBTTagCompound());

		itemStack.getTagCompound().setInteger("Tier", Character.getNumericValue(itemStack.getUnlocalizedName().charAt(6)));
		itemStack.getTagCompound().setInteger("Modules", itemStack.getTagCompound().getInteger("Tier") == 1 ? 0 : itemStack.getTagCompound().getInteger("Tier"));
		itemStack.getTagCompound().setTag("Fluid", fluidTag);
		itemStack.getTagCompound().setInteger("Weight", 55);
		itemStack.getTagCompound().setInteger("Thrust", 330);
		itemStack.getTagCompound().setFloat("Drag", 0.9F);
		itemStack.getTagCompound().setInteger("Thruster", 0);
		//Fluids
		itemStack.getTagCompound().setInteger("Tanks", 1);
		itemStack.getTagCompound().setInteger("CapTanks", 20000);
		itemStack.getTagCompound().setInteger("CapFluid", itemStack.getTagCompound().getInteger("Tanks")*itemStack.getTagCompound().getInteger("CapTanks"));
		itemStack.getTagCompound().setInteger("FluidIO", 512);
		ArrayList<Integer> fuelValues = new ArrayList();
		fuelValues.add(11);
		fuelValues.add(1);
		fuelValues.add(5);
		int fuelUsage = fuelValues.get(itemStack.getTagCompound().getInteger("Thruster"));
		fuelUsage *= itemStack.getTagCompound().getInteger("Thrust");
		fuelUsage = (int) fuelUsage/130;
		itemStack.getTagCompound().setInteger("FuelUsage", fuelUsage);
		//Energy
		itemStack.getTagCompound().setInteger("Batteries", 1);
		itemStack.getTagCompound().setInteger("CapBatteries", 100000);
		itemStack.getTagCompound().setInteger("BatteryIO", 512);
		itemStack.getTagCompound().setInteger("CapEnergy", itemStack.getTagCompound().getInteger("Batteries")*itemStack.getTagCompound().getInteger("CapBatteries"));
		itemStack.getTagCompound().setInteger("EnergyIO", itemStack.getTagCompound().getInteger("Batteries")*itemStack.getTagCompound().getInteger("BatteryIO"));
		itemStack.getTagCompound().setInteger("EnergyStored", 0);
		ArrayList<Integer> energyValues = new ArrayList();
		energyValues.add(1);
		energyValues.add(8);
		energyValues.add(5);
		int energyUsage = fuelValues.get(itemStack.getTagCompound().getInteger("Thruster"));
		energyUsage *= itemStack.getTagCompound().getInteger("Thrust");
		energyUsage = (int) energyUsage/104;
		itemStack.getTagCompound().setInteger("EnergyUsage", energyUsage);
		//Armor
		itemStack.getTagCompound().setInteger("Plates", 1);
		itemStack.getTagCompound().setInteger("ArmorType", 0);
		itemStack.getTagCompound().setInteger("ArmorEnergyUsage", 100);
		int iarmor = itemStack.getTagCompound().getInteger("Tier")*2;
		iarmor += 1;
		itemStack.getTagCompound().setInteger("ArmorReduction", iarmor);
		itemStack.getTagCompound().setInteger("Avionics", 1);
		itemStack.getTagCompound().setInteger("ItemsMax", 128);
		itemStack.getTagCompound().setInteger("ItemsStored", 0);
		
	 }
	 
	 @Override
	 public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) { 
				// If player not sneaking, open the inventory gui
				if (player.isSneaking()) {
					player.openGui(Main.instance, GuiHandler.PACK_ARMOR_ITEM_GUI, world, 0, 0, 0);
				}
				else {
					if (player.inventory.armorInventory[2] == null) {
						player.inventory.armorInventory[2] = itemstack;
						itemstack = null;
					}
				}
		}
		return new ActionResult(EnumActionResult.SUCCESS, itemstack);
	}
	@Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
	
	@Override
	public FluidStack getFluid(ItemStack container) {
		if (!container.hasTagCompound() || !container.getTagCompound().hasKey("Fluid"))
        {
            return null;
        }
		int fluidPresent = container.getTagCompound().getInteger("Fluid");
		return FluidStack.loadFluidStackFromNBT(container.getTagCompound().getCompoundTag("Fluid"));
	}

	@Override
	public int getCapacity(ItemStack container) {
		int fluidCap = container.getTagCompound().getInteger("Tanks")*container.getTagCompound().getInteger("CapTanks");
		return fluidCap;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill) {
		if (resource == null) {
            return 0;
        }
		FluidStack fluid = this.getFluid(container);
		int fluidPresent = fluid != null ? fluid.amount : 0;
		int fluidAdded = Math.min( (this.getCapacity(container) - fluidPresent) , resource.amount);
		if (doFill == true) {
			fluidPresent += fluidAdded;
			container.getTagCompound().getCompoundTag("Fluid").setInteger("Amount", fluidPresent);
		}
		return fluidAdded;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {

        if (!container.hasTagCompound() || !container.getTagCompound().hasKey("Fluid"))
        {
            return null;
        }

        FluidStack stack = FluidStack.loadFluidStackFromNBT(container.getTagCompound().getCompoundTag("Fluid"));
        if (stack == null)
        {
            return null;
        }

        int currentAmount = stack.amount;
        stack.amount = Math.min(stack.amount, maxDrain);
        if (doDrain)
        {
            if (currentAmount == stack.amount)
            {
                container.getTagCompound().removeTag("Fluid");

                if (container.getTagCompound().hasNoTags())
                {
                    container.setTagCompound(null);
                }
                return stack;
            }

            NBTTagCompound fluidTag = container.getTagCompound().getCompoundTag("Fluid");
            fluidTag.setInteger("Amount", currentAmount - stack.amount);
            container.getTagCompound().setTag("Fluid", fluidTag);
        }
		return null;
	}
	
	@Override
	@SideOnly(Side.SERVER)
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		
	}
	
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		// TODO Auto-generated method stub
		if (container.getItem() instanceof PackArmor) {
			int energyStored = this.getEnergyStored(container);
			int energyReceived = Math.min(this.getMaxEnergyStored(container) - energyStored, Math.min(maxReceive, container.getTagCompound().getInteger("EnergyIO")));
			if (!simulate) {
				energyStored += energyReceived;
				container.getTagCompound().setInteger("EnergyStored", energyStored);
			} 
			return energyReceived;
		}
		return 0;
	}
	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		// TODO Auto-generated method stub
		if (container.getItem() instanceof PackArmor) {
			int energyStored = this.getEnergyStored(container);
	        int energyExtracted = Math.min(energyStored, Math.min(maxExtract, container.getTagCompound().getInteger("EnergyIO")));
	        if (!simulate) {
	        	energyStored -= energyExtracted;
	        	container.getTagCompound().setInteger("EnergyStored", energyStored);
	        }
			return energyExtracted;
			
		}
		return 0;
	}
	@Override
	public int getEnergyStored(ItemStack container) {
		// TODO Auto-generated method stub
		if (container.getItem() instanceof PackArmor) {
			return container.getTagCompound().getInteger("EnergyStored");
		}
		else {
			return 0;
		}
	}
	@Override
	public int getMaxEnergyStored(ItemStack container) {
		// TODO Auto-generated method stub
		if (container.getItem() instanceof PackArmor) {
			return container.getTagCompound().getInteger("CapEnergy");
		}
		else {
			return 0;
		}
	}
}
