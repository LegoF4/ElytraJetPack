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

public class JetpackEngine extends Item{
	private int minAv;
	private int weight;
	private int tier;
	private int thrust;
	
	public JetpackEngine (String unlocalizedName, int minAv, int weight, int thrust, int tier) {
		super();
		this.setUnlocalizedName(unlocalizedName);
	    this.setCreativeTab(ItemManager.tabElytraJet);
	    this.maxStackSize = 4;
	    this.minAv = minAv;
	    this.weight = weight;
	    this.thrust = thrust;
	    this.tier = tier;
	}
	
	 public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		 
		 if (itemStack.getItem() instanceof JetpackEngine) {
			 NBTTagCompound nbt = itemStack.getTagCompound();
			 nbt.setInteger("Tier", this.tier);
			 nbt.setInteger("Weight", this.weight);
			 nbt.setInteger("MinAv", this.minAv);
			 nbt.setInteger("Thrust", this.thrust);
			 itemStack.setTagCompound(nbt);
		 }
		 
	 }
	 @SideOnly(Side.CLIENT)
	    public void getSubItems(Item item, CreativeTabs tab, List itemList) {
		 if (item instanceof JetpackEngine) {
			 ItemStack coreStack = new ItemStack(item);
			 NBTTagCompound nbt = new NBTTagCompound();
			 coreStack.setTagCompound(nbt);
			 JetpackEngine coreJet = (JetpackEngine) item;
			 nbt.setInteger("Tier", coreJet.tier);
			 nbt.setInteger("Weight", coreJet.weight);
			 nbt.setInteger("MinAv", coreJet.minAv);
			 nbt.setInteger("Thrust", coreJet.thrust);
			 coreStack.setTagCompound(nbt);
			 itemList.add(coreStack);
		 }
	 }
	 @Override
	 @SideOnly(Side.CLIENT)
	 public void addInformation(ItemStack stack, EntityPlayer player,List tooltip, boolean isAdvanced) {
		 tooltip.add("Tier: " + stack.getTagCompound().getInteger("Tier"));
		 tooltip.add("Weight: " + stack.getTagCompound().getInteger("Weight") + "kg");
		 tooltip.add("Minimum Avionics Tier: " + stack.getTagCompound().getInteger("MinAv"));
		 tooltip.add("Thrust: " + stack.getTagCompound().getFloat("Thrust") + " Newtons");
	 }
}
