package com.LegoF4.ElytraJetPack.client.render.blocks;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.blocks.BlockManager;
import com.LegoF4.ElytraJetPack.items.ItemManager;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class BlockRenderRegister {
	
	public static String modid = Main.MODID;
	
	public static void registerBlockRenderer() {
		register(BlockManager.armorTable);
    }
	
	public static void register(Block block) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
	
}
