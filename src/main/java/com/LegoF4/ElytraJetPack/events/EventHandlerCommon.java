package com.LegoF4.ElytraJetPack.events;

import java.util.ArrayList;
import java.util.List;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.capabilties.IJetFlying;
import com.LegoF4.ElytraJetPack.capabilties.IJetHover;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;
import com.LegoF4.ElytraJetPack.capabilties.IJetTicks;

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
import net.minecraft.util.math.AxisAlignedBB;
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
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class EventHandlerCommon {
	
	@SubscribeEvent
	public void onPostPlayerTick(PlayerTickEvent e) {
		if (e.phase == Phase.END) {
			IJetMode jetMode = e.player.getCapability(Main.JETMODE_CAP, null);
			IJetFlying jetFly = e.player.getCapability(Main.JETFLY_CAP, null);
			IJetTicks jetTicks = e.player.getCapability(Main.JETTICKS_CAP, null);
			boolean isElytraFlying = (jetMode.isJetMode() == 2 && jetFly.isJetFlying() == true) ? true : false;
			float f = 0.6f;
			float f1 = isElytraFlying ? 0.6f : 1.8f;
			if (isElytraFlying) {
				if (f != e.player.width || f1 != e.player.height) {
					AxisAlignedBB axisalignedbb = e.player.getEntityBoundingBox();
					axisalignedbb = new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + f, axisalignedbb.minY + f1, axisalignedbb.minZ + f);

					if (e.player.worldObj.getCollisionBoxes(axisalignedbb).isEmpty()) {
						float f2 = e.player.width;
						e.player.width = f;
						e.player.height = f1;
						e.player.setEntityBoundingBox(new AxisAlignedBB(e.player.getEntityBoundingBox().minX, e.player.getEntityBoundingBox().minY, e.player.getEntityBoundingBox().minZ, e.player.getEntityBoundingBox().minX + e.player.width, e.player.getEntityBoundingBox().minY + e.player.height, e.player.getEntityBoundingBox().minZ + e.player.width));

						if (e.player.width > f2 && !e.player.worldObj.isRemote) {
							e.player.moveEntity(f2 - e.player.width, 0.0D, f2 - e.player.width);
						}
					}
				}
			}
		}
	}
	
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
			 event.addCapability(new ResourceLocation(Main.MODID, "IJetTicks"), new ICapabilitySerializable<NBTPrimitive>()
		        {
		            IJetTicks instance = Main.JETTICKS_CAP.getDefaultInstance();
		            @Override
		            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		                return capability == Main.JETTICKS_CAP;
		            }

		            @Override
		            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		                return capability == Main.JETTICKS_CAP ? Main.JETTICKS_CAP.<T>cast(instance) : null;
		            }

		            @Override
		            public NBTPrimitive serializeNBT() {
		                return (NBTPrimitive)Main.JETTICKS_CAP.getStorage().writeNBT(Main.JETTICKS_CAP, instance, null);
		            }

		            @Override
		            public void deserializeNBT(NBTPrimitive nbt) {
		            	Main.JETTICKS_CAP.getStorage().readNBT(Main.JETTICKS_CAP, instance, null, nbt);
		            }
		        });
			 event.addCapability(new ResourceLocation(Main.MODID, "IJetHover"), new ICapabilitySerializable<NBTPrimitive>()
		        {
		            IJetHover instance = Main.JETHOVER_CAP.getDefaultInstance();
		            @Override
		            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		                return capability == Main.JETHOVER_CAP;
		            }

		            @Override
		            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		                return capability == Main.JETHOVER_CAP ? Main.JETHOVER_CAP.<T>cast(instance) : null;
		            }

		            @Override
		            public NBTPrimitive serializeNBT() {
		                return (NBTPrimitive)Main.JETHOVER_CAP.getStorage().writeNBT(Main.JETHOVER_CAP, instance, null);
		            }

		            @Override
		            public void deserializeNBT(NBTPrimitive nbt) {
		            	Main.JETHOVER_CAP.getStorage().readNBT(Main.JETHOVER_CAP, instance, null, nbt);
		            }
		        });
		}
    }
}
