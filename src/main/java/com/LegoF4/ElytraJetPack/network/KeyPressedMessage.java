package com.LegoF4.ElytraJetPack.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.LegoF4.ElytraJetPack.CommonProxy;
import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.Items.ItemManager;
import com.LegoF4.ElytraJetPack.Items.PackArmor;
import com.LegoF4.ElytraJetPack.capabilties.IJetFlying;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;
import com.LegoF4.ElytraJetPack.network.AbstractMessage.AbstractServerMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;

public class KeyPressedMessage extends AbstractServerMessage<KeyPressedMessage>{
	private int keyNumb;
	
	public KeyPressedMessage() {}
	
	public KeyPressedMessage(int keyNumb) {
		this.keyNumb=keyNumb;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.keyNumb = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(keyNumb);
		
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (keyNumb == 1) {
			ItemStack PlayerStack = player.inventory.armorInventory[2];
			if (PlayerStack.getItem() instanceof PackArmor) {
				IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
				int intMode = jetMode.isJetMode();
				intMode++;
				intMode %= 3;
				jetMode.setJetMode(intMode);
				String stringMode;
				int int1 = 1;
				int int2 = 2;
				if(intMode == 0) {
					stringMode = TextFormatting.GOLD + "Disabled";
					player.addChatComponentMessage(new TextComponentString(TextFormatting.BOLD + ("Jetpack Mode is:  " + stringMode) ));
				}
				if(intMode == 1) {
					stringMode = TextFormatting.GOLD + "Jetpack";
					player.addChatComponentMessage(new TextComponentString(TextFormatting.BOLD + ("Jetpack Mode is:  " + stringMode) ));
				}
				if(intMode == 2) {
					stringMode = TextFormatting.GOLD + "Elytrapack";
					player.addChatComponentMessage(new TextComponentString(TextFormatting.BOLD + ("Jetpack Mode is:  " + stringMode) ));
				}
				if (player instanceof EntityPlayerMP) {
					EntityPlayerMP playerMP = (EntityPlayerMP) player;
					PacketDispatcher.sendTo(new CapSyncMessage(intMode), playerMP);
					
				}
				
				
			}
		}
		if (keyNumb == 2) {
			IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
			if(!player.isInLava()) {;
				if(jetMode.isJetMode() == 2 && player.inventory.armorInventory[2].getItem() instanceof PackArmor) {
				
					ItemStack PlayerStack = player.inventory.armorInventory[2];
					Item jetpackItem = PlayerStack.getItem();
					FluidStack jetpackFuel = FluidStack.loadFluidStackFromNBT(PlayerStack.getTagCompound().getCompoundTag("Fluid"));
					int storedFuel = jetpackFuel.amount;
					//Thrusting Damage Code
					
					if (PlayerStack.getTagCompound().getInteger("Thruster") == 0 && !(storedFuel - 2 <= 0)) {
						storedFuel -= 2;
						PlayerStack.getTagCompound().getCompoundTag("Fluid").setInteger("Amount", storedFuel);
					}
					int postFuel = PlayerStack.getTagCompound().getCompoundTag("Fluid").getInteger("Amount");
					if (postFuel == 0) {
						
					}
					else {
						IJetFlying jetFlying = player.getCapability(Main.JETFLY_CAP, null);
						double d0 = player.motionX;
						double d1 = player.motionY;
						double d2 =player.motionZ;
						double playerSpeed = Math.sqrt((d0*d0)+(d1*d1)+(d2*d2));
						double r = ((0.7D * (double) PlayerStack.getTagCompound().getFloat("Drag"))*PlayerStack.getTagCompound().getInteger("Thrust"))/(PlayerStack.getTagCompound().getInteger("Weight")*20D);
						double max = 4.0D * PlayerStack.getTagCompound().getFloat("Drag");
						double lowermax = max * 0.6D;
						
						double pitchi = player.rotationPitch;
						double yawi = -1 * player.rotationYaw;
						pitchi *= -1;
						
						double pitch = Math.toRadians(pitchi);
						double yaw = Math.toRadians(yawi);
						double vY = Math.sin(pitch);
						double vX = Math.cos(pitch)*Math.sin(yaw);
						double vZ = Math.cos(pitch)*Math.cos(yaw);
						
						if (player.isInWater()) {
							r *= 0.5D;
							max *= 0.5D;
							lowermax *= 0.75D;
							
							if (playerSpeed >= max) {
								player.motionX += r*vX*Math.sqrt(max/playerSpeed);
								player.motionY += r*vY*Math.sqrt(max/playerSpeed);
								player.motionZ += r*vZ*Math.sqrt(max/playerSpeed);
							}
							if (playerSpeed >= lowermax && playerSpeed < max) {
								double rlm = r*0.5D;
								player.motionX += rlm*vX;
								player.motionY += rlm*vY;
								player.motionZ += rlm*vZ;
							}
							if (playerSpeed < lowermax) {
								player.motionX += r*vX;
								player.motionY += r*vY;
								player.motionZ += r*vZ;
							}
							player.velocityChanged = true;
						}
						else {
							if (playerSpeed >= max) {
								player.motionX += r*vX*Math.sqrt(max/playerSpeed);
								player.motionY += r*vY*Math.sqrt(max/playerSpeed);
								player.motionZ += r*vZ*Math.sqrt(max/playerSpeed);
							}
							if (playerSpeed >= lowermax && playerSpeed < max) {
								double rlm = r*0.5D;
								player.motionX += rlm*vX;
								player.motionY += rlm*vY;
								player.motionZ += rlm*vZ;
							}
							if (playerSpeed < lowermax) {
								player.motionX += r*vX;
								player.motionY += r*vY;
								player.motionZ += r*vZ;
							}
							player.velocityChanged = true;
							
						}
					}
				}
			}
			/** Particle **/
			/*
			
			double motionPX = vX + rand.nextGaussian() * 0.01D;
		    double motionPY = vY + rand.nextGaussian() * 0.01D;
		    double motionPZ = vZ + rand.nextGaussian() * 0.01D;*/
			
		}
		if (keyNumb == 3) {
			IJetFlying jetFlying = player.getCapability(Main.JETFLY_CAP, null);
			if (player.inventory.armorInventory[2].getItem() instanceof PackArmor) {
				if (!player.isInLava()) {
					IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
					if(player.fallDistance > 0 && jetMode.isJetMode() == 2) {
						jetFlying.setJetFlying(true);
					}
				}
			}
		}
		IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
		if (jetMode.isJetMode() == 1) {
			
		}
	}
}