package com.LegoF4.ElytraJetPack.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.fixes.ItemIntIDToString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JetpackModuleFluid extends JetpackModule{
	
	private int storage;
	private int output;
	private int input;
	private int drain;
	
	public JetpackModuleFluid (String unlocalizedName, int weight, String type, int storage, int output, int input, int drain) {
		super(unlocalizedName, weight, type);
		this.storage = storage;
		this.output = output;
		this.drain = drain;
		this.input = input;
	}
	
	 public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		 if (itemStack.getItem() instanceof JetpackModuleFluid) {
			 NBTTagCompound nbt = itemStack.getTagCompound();
			 nbt.setInteger("Storage", this.storage);
			 nbt.setInteger("Input", this.input);
			 nbt.setInteger("Output", this.output);
			 nbt.setInteger("Drain", this.drain);
			 itemStack.setTagCompound(nbt);
			 
		 }
		 
	 }
	 
	 @SideOnly(Side.CLIENT)
	    public void getSubItems(Item item, CreativeTabs tab, List itemList) {
		 if (item instanceof JetpackModuleFluid) {
			 ItemStack coreStack = new ItemStack(item);
			 NBTTagCompound nbt = new NBTTagCompound();
			 coreStack.setTagCompound(nbt);
			 JetpackModuleFluid coreJet = (JetpackModuleFluid) item;
			 nbt.setInteger("Storage", coreJet.storage);
			 nbt.setInteger("Input", coreJet.input);
			 nbt.setInteger("Output", coreJet.output);
			 nbt.setInteger("Drain", coreJet.drain);
			 coreStack.setTagCompound(nbt);
			 itemList.add(coreStack);
		 }
	 }
	 
	 @Override
	 @SideOnly(Side.CLIENT)
	 public void addInformation(ItemStack stack, EntityPlayer player,List tooltip, boolean isAdvanced) {
		 tooltip.add("Weight: " + stack.getTagCompound().getInteger("Weight") + "kg");
		 tooltip.add("Storage: " + stack.getTagCompound().getInteger("Storage") + "mB");
		 tooltip.add("Input: " + stack.getTagCompound().getInteger("Input") + " mB/t");
		 tooltip.add("Output: " + stack.getTagCompound().getInteger("Output") + " mB/t");
		 tooltip.add("Drain: " + stack.getTagCompound().getInteger("Drain") + " rf/t");
	 }
}
