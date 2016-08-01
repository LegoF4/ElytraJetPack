package com.LegoF4.ElytraJetPack.gui;

import com.LegoF4.ElytraJetPack.blocks.ArmorTableTileEntity;
import com.LegoF4.ElytraJetPack.gui.client.GuiArmorTableTileEntity;
import com.LegoF4.ElytraJetPack.gui.client.GuiPackArmorItem;
import com.LegoF4.ElytraJetPack.items.ItemInventory;
import com.LegoF4.ElytraJetPack.items.PackArmor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler  implements IGuiHandler {
	
	public static final int ARMOR_TABLE_TILE_ENTITY_GUI = 0;
	public static final int PACK_ARMOR_ITEM_GUI = 1;
	
	@Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == ARMOR_TABLE_TILE_ENTITY_GUI) {
	        return new ContainerArmorTableTileEntity(player.inventory, (ArmorTableTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
		}
		if (ID == PACK_ARMOR_ITEM_GUI) {
			if (player.getHeldItemMainhand().getItem() instanceof PackArmor) {
				return new ContainerPackArmorItem(player.inventory, new ItemInventory(player.getHeldItemMainhand(), player.getHeldItemMainhand().getDisplayName(), player.getHeldItemMainhand().getTagCompound().getInteger("ItemsMax")));
			}
		}
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    	if (ID == ARMOR_TABLE_TILE_ENTITY_GUI) {
    		return new GuiArmorTableTileEntity(new ContainerArmorTableTileEntity(player.inventory, (ArmorTableTileEntity) world.getTileEntity(new BlockPos(x, y, z))), world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
    	}
    	if (ID == PACK_ARMOR_ITEM_GUI) {
			if (player.getHeldItemMainhand().getItem() instanceof PackArmor) {
				return new GuiPackArmorItem(new ContainerPackArmorItem(player.inventory, new ItemInventory(player.getHeldItemMainhand(), player.getHeldItemMainhand().getDisplayName(), player.getHeldItemMainhand().getTagCompound().getInteger("ItemsMax"))), player);
			}
		}
        return null;
    }
}
