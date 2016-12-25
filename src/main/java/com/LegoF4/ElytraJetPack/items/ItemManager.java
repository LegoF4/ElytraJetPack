package com.LegoF4.ElytraJetPack.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.common.util.EnumHelper;


public final class ItemManager {
	
	public static ArmorMaterial MTE1 = EnumHelper.addArmorMaterial("MTE1", "elytrajet:te1", 0, new int[] {0, 0, 0, 0}, 0, null, 0);
	
	
	public static Item Jetpack;
	public static Item PackCompound;
	//Components
	public static Item Core1;
	public static Item Core2;
	public static Item Core3;
	public static Item Core4;
	
	public static Item Av1;
	public static Item Av2;
	public static Item Av3;
	public static Item Av4;
	
	public static Item EngineC;
	public static Item EngineI;
	public static Item EngineP;
	public static Item EngineG;
	
	public static Item Tank1;
	public static Item Tank2;
	public static Item Tank3;
	
	
	public static final CreativeTabs tabElytraJet = new CreativeTabs("ElytraJetPack") {
		@Override public Item getTabIconItem() {
	        return ItemManager.Jetpack;
	    }
	};
	
	
	public static void createItems() {
		GameRegistry.registerItem(Jetpack = new Jetpack("Jetpack"), "Jetpack");
		GameRegistry.registerItem(PackCompound = new PackArmor("PackTE1", MTE1, 1, EntityEquipmentSlot.CHEST), "PackTE1");
		//Components
		GameRegistry.registerItem(Core1 = new JetpackCore("JCore1", 2, 20, 0.5F, 1), "JCore1");
		GameRegistry.registerItem(Core2 = new JetpackCore("JCore2", 4, 25, 0.6F, 2), "JCore2");
		GameRegistry.registerItem(Core3 = new JetpackCore("JCore3", 6, 30, 0.65F, 3), "JCore3");
		GameRegistry.registerItem(Core4 = new JetpackCore("JCore4", 8, 35, 0.7F, 4), "JCore4");
		
		GameRegistry.registerItem(Av1 = new JetpackAvionics("JAv1", 1, 1), "JAv1");
		GameRegistry.registerItem(Av2 = new JetpackAvionics("JAv2", 2, 2), "JAv2");
		GameRegistry.registerItem(Av3 = new JetpackAvionics("JAv3", 3, 3), "JAv3");
		GameRegistry.registerItem(Av4 = new JetpackAvionics("JAv4", 5, 4), "JAv4");
		
		GameRegistry.registerItem(EngineC = new JetpackEngine("JEngineC", 0, 9, 530, 1), "JEngineC");
		GameRegistry.registerItem(EngineI = new JetpackEngine("JEngineI", 1, 12, 235, 2), "JEngineI");
		GameRegistry.registerItem(EngineP = new JetpackEngine("JEngineP", 3, 13, 460, 3), "JEngineP");
		GameRegistry.registerItem(EngineG = new JetpackEngine("JEngineG", 4, 15, 425, 4), "JEngineG");
		
		GameRegistry.registerItem(Tank1 = new JetpackModuleFluid("JTank1",12,"T1",8000,96,48,0), "JTank1");
		GameRegistry.registerItem(Tank2 = new JetpackModuleFluid("JTank2",25,"T2",32000,196,192,0), "JTank2");
		GameRegistry.registerItem(Tank3 = new JetpackModuleFluid("JTank3",67,"T3",72000,256,256,3), "JTank3");
    }
}
