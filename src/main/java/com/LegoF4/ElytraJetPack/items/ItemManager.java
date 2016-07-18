package com.LegoF4.ElytraJetPack.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.common.util.EnumHelper;


public final class ItemManager {
	
	public static ArmorMaterial MTE1 = EnumHelper.addArmorMaterial("MTE1", "elytrajet:te1", 0, new int[] {0, 0, 3, 0}, 0, null, 0);
	public static ArmorMaterial MTE2 = EnumHelper.addArmorMaterial("MTE2", "elytrajet:te2", 0, new int[] {0, 0, 5, 0}, 0, null, 0);
	public static ArmorMaterial MTE3 = EnumHelper.addArmorMaterial("MTE3", "elytrajet:te3", 0, new int[] {0, 0, 7, 0}, 0, null, 0);
	public static ArmorMaterial MTE4 = EnumHelper.addArmorMaterial("MTE4", "elytrajet:te4", 0, new int[] {0, 0, 9, 0}, 0, null, 0);
	
	
	public static Item Jetpack;
	public static Item PackTE1;
	public static Item PackTE2;
	public static Item PackTE3;
	public static Item PackTE4;
	
	
	public static final CreativeTabs tabElytraJet = new CreativeTabs("ElytraJetPack") {
		@Override public Item getTabIconItem() {
	        return ItemManager.Jetpack;
	    }
	};
	
	
	public static void createItems() {
		GameRegistry.registerItem(Jetpack = new Jetpack("Jetpack"), "Jetpack");
		GameRegistry.registerItem(PackTE1 = new PackArmor("PackTE1", MTE1, 1, EntityEquipmentSlot.CHEST), "PackTE1");
		GameRegistry.registerItem(PackTE2 = new PackArmor("PackTE2", MTE2, 1, EntityEquipmentSlot.CHEST), "PackTE2");
		GameRegistry.registerItem(PackTE3 = new PackArmor("PackTE3", MTE3, 1, EntityEquipmentSlot.CHEST), "PackTE3");
		GameRegistry.registerItem(PackTE4 = new PackArmor("PackTE4", MTE4, 1, EntityEquipmentSlot.CHEST), "PackTE4");
		
    }
}
