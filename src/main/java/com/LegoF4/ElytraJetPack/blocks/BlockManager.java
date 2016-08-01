package com.LegoF4.ElytraJetPack.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {
	
	public static Block armorTable;
	
	public static void createBlocks() {
		GameRegistry.registerBlock(armorTable = new ArmorTableBlock("armorTable"));
    }
	
}
