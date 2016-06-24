package com.LegoF4.ElytraJetPack.events;

import java.util.ArrayList;
import java.util.List;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.capabilties.IJetFlying;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase.NBTPrimitive;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class EventHandlerCommon {
	
	@SubscribeEvent
	public void onEvent(LivingJumpEvent event) {
		
		if (event.getEntity() instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.getEntity();
			player.motionY *= 1.2D;
		
		}
	}

	/*@SubscribeEvent
	public void onEvent(ItemTooltipEvent event) {
		ItemStack jetpack = event.getItemStack();
		EntityPlayer player = event.getEntityPlayer();
		List<String> tooltip = event.getToolTip();
		IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
		ArrayList<String> names = new ArrayList();
		names.add("Off");
		names.add("Jetpack");
		names.add("Elytrapack");
		tooltip.set(0, "Mode: " + names.get(jetMode.isJetMode()));
		
	}*/
	
	@SubscribeEvent
    public void onEntityConstruct(AttachCapabilitiesEvent.Entity event)
    {
		if (event.getEntity() instanceof EntityPlayer) {
			 event.addCapability(new ResourceLocation(Main.MODID, "IJetFlying"), new ICapabilitySerializable<NBTPrimitive>()
		        {
		            IJetFlying instance = Main.JETFLY_CAP.getDefaultInstance();
		            @Override
		            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		                return capability == Main.JETFLY_CAP;
		            }

		            @Override
		            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		                return capability == Main.JETFLY_CAP ? Main.JETFLY_CAP.<T>cast(instance) : null;
		            }

		            @Override
		            public NBTPrimitive serializeNBT() {
		                return (NBTPrimitive)Main.JETFLY_CAP.getStorage().writeNBT(Main.JETFLY_CAP, instance, null);
		            }

		            @Override
		            public void deserializeNBT(NBTPrimitive nbt) {
		            	Main.JETFLY_CAP.getStorage().readNBT(Main.JETFLY_CAP, instance, null, nbt);
		            }
		        });
			 event.addCapability(new ResourceLocation(Main.MODID, "IJetMode"), new ICapabilitySerializable<NBTPrimitive>()
		        {
		            IJetMode instance = Main.JETMODE_CAP.getDefaultInstance();
		            @Override
		            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		                return capability == Main.JETMODE_CAP;
		            }

		            @Override
		            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		                return capability == Main.JETMODE_CAP ? Main.JETMODE_CAP.<T>cast(instance) : null;
		            }

		            @Override
		            public NBTPrimitive serializeNBT() {
		                return (NBTPrimitive)Main.JETMODE_CAP.getStorage().writeNBT(Main.JETMODE_CAP, instance, null);
		            }

		            @Override
		            public void deserializeNBT(NBTPrimitive nbt) {
		            	Main.JETMODE_CAP.getStorage().readNBT(Main.JETMODE_CAP, instance, null, nbt);
		            }
		        });
		}
    }
}
