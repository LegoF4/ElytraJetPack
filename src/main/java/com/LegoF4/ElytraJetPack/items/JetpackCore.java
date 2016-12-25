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

public class JetpackCore extends Item{
	private int modules;
	private int weight;
	private int tier;
	private float drag;
	
	public JetpackCore (String unlocalizedName, int modules, int weight, float drag, int tier) {
		super();
		this.setUnlocalizedName(unlocalizedName);
	    this.setCreativeTab(ItemManager.tabElytraJet);
	    this.maxStackSize = 4;
	    this.modules = modules;
	    this.weight = weight;
	    this.drag = drag;
	    this.tier = tier;
	}
	
	 public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		 if (itemStack.getItem() instanceof JetpackCore) {
			 NBTTagCompound nbt = itemStack.getTagCompound();
			 nbt.setInteger("Tier", this.tier);
			 nbt.setInteger("Weight", this.weight);
			 nbt.setInteger("MaxModules", this.modules);
			 nbt.setFloat("Drag", this.drag);
			 itemStack.setTagCompound(nbt);
		 }
		 
	 }
	 @SideOnly(Side.CLIENT)
	    public void getSubItems(Item item, CreativeTabs tab, List itemList) {
		 if (item instanceof JetpackCore) {
			 ItemStack coreStack = new ItemStack(item);
			 NBTTagCompound nbt = new NBTTagCompound();
			 coreStack.setTagCompound(nbt);
			 JetpackCore coreJet = (JetpackCore) item;
			 nbt.setInteger("Tier", coreJet.tier);
			 nbt.setInteger("Weight", coreJet.weight);
			 nbt.setInteger("MaxModules", coreJet.modules);
			 nbt.setFloat("Drag", coreJet.drag);
			 coreStack.setTagCompound(nbt);
			 itemList.add(coreStack);
		 }
	 }
	 @Override
	 @SideOnly(Side.CLIENT)
	 public void addInformation(ItemStack stack, EntityPlayer player,List tooltip, boolean isAdvanced) {
		 tooltip.add("Tier: " + stack.getTagCompound().getInteger("Tier"));
		 tooltip.add("Weight: " + stack.getTagCompound().getInteger("Weight") + "kg");
		 tooltip.add("Max Number of Modules: " + stack.getTagCompound().getInteger("MaxModules"));
		 tooltip.add("Drag Factor: " + stack.getTagCompound().getFloat("Drag"));
	 }
}
