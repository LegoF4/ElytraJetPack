package com.LegoF4.ElytraJetPack;

import com.LegoF4.ElytraJetPack.Items.ItemManager;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingManager {
	public static void Crafting() {
		GameRegistry.addRecipe(new ItemStack(ItemManager.Jetpack), "R R", "EGE", "DLD", 'E', Items.ELYTRA, 'G', Blocks.GOLD_BLOCK, 'R', Blocks.REDSTONE_BLOCK, 'D', Items.DIAMOND, 'L', Items.LAVA_BUCKET);
		
	}
}
