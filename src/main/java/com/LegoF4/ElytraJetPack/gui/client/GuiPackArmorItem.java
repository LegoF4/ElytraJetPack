package com.LegoF4.ElytraJetPack.gui.client;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.blocks.ArmorTableTileEntity;
import com.LegoF4.ElytraJetPack.gui.ContainerArmorTableTileEntity;
import com.LegoF4.ElytraJetPack.gui.ContainerPackArmorItem;
import com.LegoF4.ElytraJetPack.items.ItemInventory;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiPackArmorItem  extends GuiContainer {
	
	private final ItemInventory inventory;
	private IInventory playerInv;
	
	public GuiPackArmorItem(ContainerPackArmorItem containerItem, EntityPlayer playerIn) {
		super (containerItem);
        this.xSize = 176;
        this.ySize = 166;
        this.inventory = containerItem.getInventoryC();
        this.playerInv = playerIn.inventory;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    	 this.mc.getTextureManager().bindTexture(new ResourceLocation(Main.MODID + ":gui/container/armorPack/pack_armor_background.png"));
    	 this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    	
    }
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawString(this.inventory.getDisplayName().getUnformattedText(), 88 - this.fontRendererObj.getStringWidth(this.inventory.getDisplayName().getUnformattedText()) / 2, -9, 16777215);            //#404040
        this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);      //#404040
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
   	 	this.mc.getTextureManager().bindTexture(new ResourceLocation(Main.MODID + ":gui/container/armorPack/pack_armor_foreground.png"));
   	 	this.drawTexturedModalRect(0, 0, 0, 0, this.xSize, this.ySize);
   	 	this.fontRendererObj.drawString("Fluid", 7, 3, 4210752); //#404040
   	 	String energyName = "Energy";
   	 	this.fontRendererObj.drawString("Energy", 167-this.fontRendererObj.getStringWidth(energyName), 3, 4210752);  //#404040
   	 	String itemName = "Items: " + Integer.toString(this.inventory.getStackInSlot(0)!= null ? this.inventory.itemsStored + this.inventory.getStackInSlot(0).stackSize : 0)  + " / " + (this.inventory.itemsMax+32);
   	 	this.fontRendererObj.drawString(itemName, 88-this.fontRendererObj.getStringWidth(itemName)/2, 22, 4210752);  //#404040
    }

}
