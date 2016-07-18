package com.LegoF4.ElytraJetPack.client.render.items;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.items.ItemManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ItemRenderRegister {
	
	public static String modid = Main.MODID;
	
	public static void registerItemRenderer() {
		register(ItemManager.Jetpack);
    }
	
	public static void register(Item item) {
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
	
}
