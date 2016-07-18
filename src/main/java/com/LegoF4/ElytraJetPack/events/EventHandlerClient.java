package com.LegoF4.ElytraJetPack.events;

import com.LegoF4.ElytraJetPack.KeyBindings;
import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.Items.PackArmor;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;
import com.LegoF4.ElytraJetPack.network.KeyPressedMessage;
import com.LegoF4.ElytraJetPack.network.PacketDispatcher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class EventHandlerClient {
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if (!FMLClientHandler.instance().isGUIOpen(GuiChat.class)) {
			if (KeyBindings.keyFly.isPressed()) {
				PacketDispatcher.sendToServer(new KeyPressedMessage(1));
			}
			if (Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()) {
				PacketDispatcher.sendToServer(new KeyPressedMessage(3));
			}
		}
    }
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onEvent(TickEvent.PlayerTickEvent event) {
		if (event.phase == Phase.END) {
			if (!FMLClientHandler.instance().isGUIOpen(GuiChat.class)) {
				EntityPlayer player = event.player;
				IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
				ItemStack jetpackStack = player.inventory.armorInventory[2];
				if (jetpackStack != null) {
					if (jetpackStack.getItem() instanceof PackArmor) {
						if (jetMode.isJetMode() == 1) {
							
							if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()) {
								PacketDispatcher.sendToServer(new KeyPressedMessage(4));
							}
							if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) {
								PacketDispatcher.sendToServer(new KeyPressedMessage(5));
							}
							if (Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown()) {
								PacketDispatcher.sendToServer(new KeyPressedMessage(6));
							}
							if (Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown()) {
								PacketDispatcher.sendToServer(new KeyPressedMessage(7));
							}
							if (Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown()) {
								PacketDispatcher.sendToServer(new KeyPressedMessage(8));
							}
							if (Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown()) {
								PacketDispatcher.sendToServer(new KeyPressedMessage(9));
							}
						}
						if (jetMode.isJetMode() == 2) {
							if (KeyBindings.keyFlyThrust.isKeyDown()) {
								PacketDispatcher.sendToServer(new KeyPressedMessage(2));
							}
						}
					}
				}
			}
		}
	}
}
 