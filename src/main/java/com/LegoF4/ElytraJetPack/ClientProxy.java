package com.LegoF4.ElytraJetPack;

import com.LegoF4.ElytraJetPack.client.render.items.ItemRenderRegister;
import com.LegoF4.ElytraJetPack.events.EventHandlerClient;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		// TODO Auto-generated method stub
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e) {
		// TODO Auto-generated method stub
		super.init(e);
		EventHandlerClient handler = new EventHandlerClient();
    	MinecraftForge.EVENT_BUS.register(handler);
    	FMLCommonHandler.instance().bus().register(handler);
	    ItemRenderRegister.registerItemRenderer();
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		// TODO Auto-generated method stub
		super.postInit(e);
	}

}
