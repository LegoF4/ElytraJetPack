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

public class JetpackAvionics extends Item{
	private int weight;
	private int tier;
	
	public JetpackAvionics (String unlocalizedName, int weight, int tier) {
		super();
		this.setUnlocalizedName(unlocalizedName);
	    this.setCreativeTab(ItemManager.tabElytraJet);
	    this.maxStackSize = 4;
	    this.weight = weight;
	    this.tier = tier;
	}
	
	 public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		 if (itemStack.getItem() instanceof JetpackAvionics) {
			 NBTTagCompound nbt = itemStack.getTagCompound();
			 nbt.setInteger("Tier", this.tier);
			 nbt.setInteger("Weight", this.weight);
			 itemStack.setTagCompound(nbt);
		 }
		 
	 }
	 
	 @SideOnly(Side.CLIENT)
	    public void getSubItems(Item item, CreativeTabs tab, List itemList) {
		 if (item instanceof JetpackAvionics) {
			 ItemStack coreStack = new ItemStack(item);
			 NBTTagCompound nbt = new NBTTagCompound();
			 coreStack.setTagCompound(nbt);
			 JetpackAvionics coreJet = (JetpackAvionics) item;
			 nbt.setInteger("Tier", coreJet.tier);
			 nbt.setInteger("Weight", coreJet.weight);
			 coreStack.setTagCompound(nbt);
			 itemList.add(coreStack);
		 }
	 }
	 
	 @Override
	 @SideOnly(Side.CLIENT)
	 public void addInformation(ItemStack stack, EntityPlayer player,List tooltip, boolean isAdvanced) {
		 tooltip.add("Tier: " + stack.getTagCompound().getInteger("Tier"));
		 tooltip.add("Weight: " + stack.getTagCompound().getInteger("Weight") + "kg");
	 }
}
