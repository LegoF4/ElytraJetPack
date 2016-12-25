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

public class JetpackModule extends Item{
	private int weight;
	private String type;
	
	public JetpackModule (String unlocalizedName, int weight, String type) {
		super();
		this.setUnlocalizedName(unlocalizedName);
	    this.setCreativeTab(ItemManager.tabElytraJet);
	    this.maxStackSize = 4;
	    this.weight = weight;
	    this.type = type;
	}
	
	 public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		 if (itemStack.getItem() instanceof JetpackModule) {
			 NBTTagCompound nbt = itemStack.getTagCompound();
			 nbt.setInteger("Weight", this.weight);
			 nbt.setString("Type", this.type);
			 itemStack.setTagCompound(nbt);
		 }
		 
	 }
	 @SideOnly(Side.CLIENT)
	    public void getSubItems(Item item, CreativeTabs tab, List itemList) {
		 
	 }
}
