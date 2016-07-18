package com.LegoF4.ElytraJetPack;

import java.util.List;
import java.util.UUID;

import com.LegoF4.ElytraJetPack.capabilties.IJetFlying;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;
import com.LegoF4.ElytraJetPack.capabilties.IJetTicks;
import com.LegoF4.ElytraJetPack.capabilties.JetFlyingDefaultImpl;
import com.LegoF4.ElytraJetPack.capabilties.JetFlyingStorage;
import com.LegoF4.ElytraJetPack.capabilties.JetModeDefaultImpl;
import com.LegoF4.ElytraJetPack.capabilties.JetModeStorage;
import com.LegoF4.ElytraJetPack.capabilties.JetTicksDefaultImpl;
import com.LegoF4.ElytraJetPack.capabilties.JetTicksStorage;
import com.LegoF4.ElytraJetPack.events.EventHandlerCommon;
import com.LegoF4.ElytraJetPack.events.JetFlyingHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy{
	
	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			return ctx.getServerHandler().playerEntity;
		}
		else if (ctx.side == Side.CLIENT) {
			return Minecraft.getMinecraft().thePlayer;
		}
		else {
			return null;
		}
		
	}
	/**
	 * Returns the current thread based on side during message handling,
	 * used for ensuring that the message is being handled by the main thread
	 */
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		if (ctx.side == Side.CLIENT) {
			return Minecraft.getMinecraft();
		}
		else if (ctx.side == Side.SERVER) {
			return ctx.getServerHandler().playerEntity.getServer();
		}
		else {
			return null;
		}
		
	}

	
	public void preInit(FMLPreInitializationEvent e) {
		CapabilityManager.INSTANCE.register(IJetFlying.class, new JetFlyingStorage(), JetFlyingDefaultImpl.class);
		CapabilityManager.INSTANCE.register(IJetMode.class, new JetModeStorage(), JetModeDefaultImpl.class);
		CapabilityManager.INSTANCE.register(IJetTicks.class, new JetTicksStorage(), JetTicksDefaultImpl.class);
    }

    public void init(FMLInitializationEvent e) {
    	EventHandlerCommon handler = new EventHandlerCommon();
    	MinecraftForge.EVENT_BUS.register(handler);
    	FMLCommonHandler.instance().bus().register(handler);
    	JetFlyingHandler handler2 = new JetFlyingHandler();
    	MinecraftForge.EVENT_BUS.register(handler2);
    	FMLCommonHandler.instance().bus().register(handler2);
    	CraftingManager.Crafting();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }

}
