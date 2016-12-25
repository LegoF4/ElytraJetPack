package com.LegoF4.ElytraJetPack.gui.client;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.blocks.ArmorTableTileEntity;
import com.LegoF4.ElytraJetPack.gui.ContainerArmorTableTileEntity;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiArmorTableTileEntity  extends GuiContainer {
	
	private TileEntity tileEntity;
	private IInventory playerInv;
	private GuiTextField text;
	
	public GuiArmorTableTileEntity(ContainerArmorTableTileEntity containerIn, TileEntity tileEntity, IInventory playerInv) {
		super(containerIn);
		
        this.xSize = 176;
        this.ySize = 200;
        this.tileEntity = tileEntity;
        this.playerInv = playerInv;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    	 this.mc.getTextureManager().bindTexture(new ResourceLocation(Main.MODID + ":gui/container/armorTable/armor_table_background.png"));
    	 this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    	
    }
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    	String s = this.tileEntity.getDisplayName().getUnformattedText();
    	this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, -9, 16777215);;            //#404040
    	String out = "Output";
    	this.fontRendererObj.drawString(out, 100, 6, 4210752);           //#404040
    }

}
