package com.LegoF4.ElytraJetPack;

import com.LegoF4.ElytraJetPack.capabilties.IJetFlying;
import com.LegoF4.ElytraJetPack.capabilties.IJetHover;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;
import com.LegoF4.ElytraJetPack.capabilties.IJetTicks;
import com.LegoF4.ElytraJetPack.client.render.items.ItemRenderRegister;
import com.LegoF4.ElytraJetPack.items.ItemManager;
import com.LegoF4.ElytraJetPack.items.PackArmor;
import com.LegoF4.ElytraJetPack.network.PacketDispatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main {
	public static final String MODID = "elytrajet";
	public static final String MODNAME = "Elytra Like Jetpacks";
	public static final String VERSION = "0.1.0";
	
	@CapabilityInject(IJetFlying.class)
    public static final Capability<IJetFlying> JETFLY_CAP = null;
	@CapabilityInject(IJetMode.class)
    public static final Capability<IJetMode> JETMODE_CAP = null;
	@CapabilityInject(IJetTicks.class)
    public static final Capability<IJetTicks> JETTICKS_CAP = null;
	@CapabilityInject(IJetHover.class)
    public static final Capability<IJetHover> JETHOVER_CAP = null;
	
	public static final Logger logger = LogManager.getLogger(MODID);

	@Instance
    public static Main instance = new Main();
	
	@SidedProxy(clientSide="com.LegoF4.ElytraJetPack.ClientProxy", serverSide="com.LegoF4.ElytraJetPack.ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		logger.info("Beginning pre-initialization");
		this.proxy.preInit(e);
		ItemManager.createItems();
		KeyBindings.init();
		PacketDispatcher.registerPackets();
	}
	        
	@EventHandler
	public void init(FMLInitializationEvent e) {
		this.proxy.init(e);
		PacketDispatcher.registerPackets();
	}
	        
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		this.proxy.postInit(e);
	}
}
