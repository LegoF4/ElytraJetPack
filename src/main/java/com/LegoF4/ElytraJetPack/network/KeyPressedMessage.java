package com.LegoF4.ElytraJetPack.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
		ItemStack jetpackStack = player.inventory.armorInventory[2];
		if (keyNumb == 1) {
			System.out.println("Limb Swing Amount: " + player.limbSwingAmount);
		}
		if (jetpackStack != null) {
			if (jetpackStack.getItem() instanceof PackArmor) {
				IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
				IJetFlying jetFlying = player.getCapability(Main.JETFLY_CAP, null);
				UUID playerUUID = player.getUniqueID();
				long mostBits = playerUUID.getMostSignificantBits();
				long leastBits = playerUUID.getLeastSignificantBits();
				//Toggle mode
				if (keyNumb == 1) {		
						int intMode = jetMode.isJetMode();
						intMode++;
						intMode %= 3;
						jetMode.setJetMode(intMode);
						String stringMode;
						int int1 = 1;
						int int2 = 2;
						if(intMode == 0) {
							player.eyeHeight = 1.62f;
							stringMode = TextFormatting.GOLD + "Disabled";
							player.addChatComponentMessage(new TextComponentString(TextFormatting.BOLD + ("Jetpack Mode is:  " + stringMode) ));
						}
						if(intMode == 1) {
							player.eyeHeight = 1.62f;
							stringMode = TextFormatting.GOLD + "Jetpack";
							player.addChatComponentMessage(new TextComponentString(TextFormatting.BOLD + ("Jetpack Mode is:  " + stringMode) ));
						}
						if(intMode == 2) {
							player.eyeHeight = 1.62f;
							stringMode = TextFormatting.GOLD + "Elytrapack";
							player.addChatComponentMessage(new TextComponentString(TextFormatting.BOLD + ("Jetpack Mode is:  " + stringMode) ));
						}
						if (player instanceof EntityPlayerMP) {
							EntityPlayerMP playerMP = (EntityPlayerMP) player;
							PacketDispatcher.sendTo(new CapSyncMessageInt(intMode, mostBits, leastBits), playerMP);
							
						}
				}
				//Thrust
				if (keyNumb == 2 && jetFlying.isJetFlying()) {
					if(!player.isInLava()) {;
						if(jetMode.isJetMode() == 2) {
							Item jetpackItem = jetpackStack.getItem();
							FluidStack jetpackFuel = FluidStack.loadFluidStackFromNBT(jetpackStack.getTagCompound().getCompoundTag("Fluid"));
							int storedFuel = jetpackFuel.amount;
							int storedCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (!(storedCharge -jetpackStack.getTagCompound().getInteger("EnergyUsage")/1.7 <= 0) && !(storedFuel -jetpackStack.getTagCompound().getInteger("FuelUsage")/1.7 <= 0)) {
								storedFuel -= (int) jetpackStack.getTagCompound().getInteger("FuelUsage")/1.7;
								storedCharge -= (int) jetpackStack.getTagCompound().getInteger("EnergyUsage")/1.7;
								jetpackStack.getTagCompound().getCompoundTag("Fluid").setInteger("Amount", storedFuel);
								jetpackStack.getTagCompound().setInteger("EnergyStored", storedCharge);
							}
							int postFuel = jetpackStack.getTagCompound().getCompoundTag("Fluid").getInteger("Amount");
							int postCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (postFuel  - jetpackStack.getTagCompound().getInteger("FuelUsage")/1.7 < 0 || postFuel  - jetpackStack.getTagCompound().getInteger("EnergyUsage")/1.7 < 0) {
								
							}
							else {
								double d0 = player.motionX;
								double d1 = player.motionY;
								double d2 =player.motionZ;
								double playerSpeed = Math.sqrt((d0*d0)+(d1*d1)+(d2*d2));
								double r = ((0.7D * (double) jetpackStack.getTagCompound().getFloat("Drag"))*jetpackStack.getTagCompound().getInteger("Thrust"))/(jetpackStack.getTagCompound().getInteger("Weight")*20D);
								double max = 4.0D * jetpackStack.getTagCompound().getFloat("Drag");
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
					
				}
				//Fly-toggle
				if (keyNumb == 3) {
						if (!player.isInLava()) {
							if(player.fallDistance > 0 && jetMode.isJetMode() > 0) {
								jetFlying.setJetFlying(true);
								PacketDispatcher.sendToAll(new CapSyncMessageBool(jetFlying.isJetFlying(), mostBits, leastBits));
							}
						}
				}
				if (jetMode.isJetMode() == 1) {
					//Up
					if (keyNumb == 4) {
							FluidStack jetpackFuel = FluidStack.loadFluidStackFromNBT(jetpackStack.getTagCompound().getCompoundTag("Fluid"));
							int storedFuel = jetpackFuel.amount;
							int storedCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (!(storedCharge -jetpackStack.getTagCompound().getInteger("EnergyUsage")/2 <= 0) && !(storedFuel -jetpackStack.getTagCompound().getInteger("FuelUsage")/2 <= 0)) {
								storedFuel -= (int) jetpackStack.getTagCompound().getInteger("FuelUsage")/2;
								storedCharge -= (int) jetpackStack.getTagCompound().getInteger("EnergyUsage")/2;
								jetpackStack.getTagCompound().getCompoundTag("Fluid").setInteger("Amount", storedFuel);
								jetpackStack.getTagCompound().setInteger("EnergyStored", storedCharge);
							}
							int postFuel = jetpackStack.getTagCompound().getCompoundTag("Fluid").getInteger("Amount");
							int postCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (postFuel  - jetpackStack.getTagCompound().getInteger("FuelUsage")/2 >= 0 && postCharge  - jetpackStack.getTagCompound().getInteger("EnergyUsage")/2 >= 0) {
								double motionUp = jetpackStack.getTagCompound().getFloat("Drag")*jetpackStack.getTagCompound().getInteger("Thrust")/(jetpackStack.getTagCompound().getInteger("Weight")*98);
								player.motionY += motionUp;
								player.velocityChanged = true;
								jetFlying.setJetFlying(true);
								PacketDispatcher.sendToAll(new CapSyncMessageBool(jetFlying.isJetFlying(), mostBits, leastBits));
							}
					}
					//Down
					if (keyNumb == 5 && jetFlying.isJetFlying()) {
							FluidStack jetpackFuel = FluidStack.loadFluidStackFromNBT(jetpackStack.getTagCompound().getCompoundTag("Fluid"));
							int storedFuel = jetpackFuel.amount;
							int storedCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (!(storedCharge -jetpackStack.getTagCompound().getInteger("EnergyUsage")/3 <= 0) && !(storedFuel -jetpackStack.getTagCompound().getInteger("FuelUsage")/3 <= 0)) {
								storedFuel -= (int) jetpackStack.getTagCompound().getInteger("FuelUsage")/3;
								storedCharge -= (int) jetpackStack.getTagCompound().getInteger("EnergyUsage")/3;
								jetpackStack.getTagCompound().getCompoundTag("Fluid").setInteger("Amount", storedFuel);
								jetpackStack.getTagCompound().setInteger("EnergyStored", storedCharge);
							}
							int postFuel = jetpackStack.getTagCompound().getCompoundTag("Fluid").getInteger("Amount");
							int postCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (postFuel  - jetpackStack.getTagCompound().getInteger("FuelUsage")/3 >= 0 && postCharge  - jetpackStack.getTagCompound().getInteger("EnergyUsage")/3 >= 0) {
								double motionUp = jetpackStack.getTagCompound().getFloat("Drag")*jetpackStack.getTagCompound().getInteger("Thrust")/(jetpackStack.getTagCompound().getInteger("Weight")*93);
								player.motionY -= motionUp;
								player.velocityChanged = true;
							}
					}
					//Forward
					if (keyNumb == 6 && jetFlying.isJetFlying()) {
							FluidStack jetpackFuel = FluidStack.loadFluidStackFromNBT(jetpackStack.getTagCompound().getCompoundTag("Fluid"));
							int storedFuel = jetpackFuel.amount;
							int storedCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (!(storedCharge -jetpackStack.getTagCompound().getInteger("EnergyUsage")/5 <= 0) && !(storedFuel -jetpackStack.getTagCompound().getInteger("FuelUsage")/5 <= 0)) {
								storedFuel -= (int) jetpackStack.getTagCompound().getInteger("FuelUsage")/5;
								storedCharge -= (int) jetpackStack.getTagCompound().getInteger("EnergyUsage")/5;
								jetpackStack.getTagCompound().getCompoundTag("Fluid").setInteger("Amount", storedFuel);
								jetpackStack.getTagCompound().setInteger("EnergyStored", storedCharge);
							}
							int postFuel = jetpackStack.getTagCompound().getCompoundTag("Fluid").getInteger("Amount");
							int postCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (postFuel  - jetpackStack.getTagCompound().getInteger("FuelUsage")/5 >= 0 && postCharge  - jetpackStack.getTagCompound().getInteger("EnergyUsage")/5 >= 0) {
								double yawi = player.rotationYaw;
								double yaw = Math.toRadians(yawi);
								double vZ = Math.cos(yaw);
								double vX = -1*Math.sin(yaw);
								double r = jetpackStack.getTagCompound().getFloat("Drag")*jetpackStack.getTagCompound().getInteger("Thrust")/(jetpackStack.getTagCompound().getInteger("Weight")*140);
								player.motionX += vX*r;
								player.motionZ += vZ*r;
								player.velocityChanged = true;
							}
					}
					//Back
					if (keyNumb == 7 && jetFlying.isJetFlying()) {
							FluidStack jetpackFuel = FluidStack.loadFluidStackFromNBT(jetpackStack.getTagCompound().getCompoundTag("Fluid"));
							int storedFuel = jetpackFuel.amount;
							int storedCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (!(storedCharge -jetpackStack.getTagCompound().getInteger("EnergyUsage")/5 <= 0) && !(storedFuel -jetpackStack.getTagCompound().getInteger("FuelUsage")/5 <= 0)) {
								storedFuel -= (int) jetpackStack.getTagCompound().getInteger("FuelUsage")/5;
								storedCharge -= (int) jetpackStack.getTagCompound().getInteger("EnergyUsage")/5;
								jetpackStack.getTagCompound().getCompoundTag("Fluid").setInteger("Amount", storedFuel);
								jetpackStack.getTagCompound().setInteger("EnergyStored", storedCharge);
							}
							int postFuel = jetpackStack.getTagCompound().getCompoundTag("Fluid").getInteger("Amount");
							int postCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (postFuel  - jetpackStack.getTagCompound().getInteger("FuelUsage")/5 >= 0 && postCharge  - jetpackStack.getTagCompound().getInteger("EnergyUsage")/5 >= 0) {
								double yawi = player.rotationYaw;
								double yaw = Math.toRadians(yawi);
								double vZ = Math.cos(yaw);
								double vX = -1*Math.sin(yaw);
								double r = jetpackStack.getTagCompound().getFloat("Drag")*jetpackStack.getTagCompound().getInteger("Thrust")/(jetpackStack.getTagCompound().getInteger("Weight")*140);
								player.motionX -= vX*r;
								player.motionZ -= vZ*r;
								player.velocityChanged = true;
							}
					}
					//Left
					if (keyNumb == 8 && jetFlying.isJetFlying()) {
							FluidStack jetpackFuel = FluidStack.loadFluidStackFromNBT(jetpackStack.getTagCompound().getCompoundTag("Fluid"));
							int storedFuel = jetpackFuel.amount;
							int storedCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (!(storedCharge -jetpackStack.getTagCompound().getInteger("EnergyUsage")/5 <= 0) && !(storedFuel -jetpackStack.getTagCompound().getInteger("FuelUsage")/5 <= 0)) {
								storedFuel -= (int) jetpackStack.getTagCompound().getInteger("FuelUsage")/5;
								storedCharge -= (int) jetpackStack.getTagCompound().getInteger("EnergyUsage")/5;
								jetpackStack.getTagCompound().getCompoundTag("Fluid").setInteger("Amount", storedFuel);
								jetpackStack.getTagCompound().setInteger("EnergyStored", storedCharge);
							}
							int postFuel = jetpackStack.getTagCompound().getCompoundTag("Fluid").getInteger("Amount");
							int postCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (postFuel  - jetpackStack.getTagCompound().getInteger("FuelUsage")/5 >= 0 && postCharge  - jetpackStack.getTagCompound().getInteger("EnergyUsage")/5 >= 0) {
								double yawi = player.rotationYaw - 90;
								double yaw = Math.toRadians(yawi);
								double vZ = Math.cos(yaw);
								double vX = -1*Math.sin(yaw);
								double r = jetpackStack.getTagCompound().getFloat("Drag")*jetpackStack.getTagCompound().getInteger("Thrust")/(jetpackStack.getTagCompound().getInteger("Weight")*140);
								player.motionX += vX*r;
								player.motionZ += vZ*r;
								player.velocityChanged = true;
							}
					}
					//Right
					if (keyNumb == 9 && jetFlying.isJetFlying()) {
							FluidStack jetpackFuel = FluidStack.loadFluidStackFromNBT(jetpackStack.getTagCompound().getCompoundTag("Fluid"));
							int storedFuel = jetpackFuel.amount;
							int storedCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (!(storedCharge -jetpackStack.getTagCompound().getInteger("EnergyUsage")/5 <= 0) && !(storedFuel -jetpackStack.getTagCompound().getInteger("FuelUsage")/5 <= 0)) {
								storedFuel -= (int) jetpackStack.getTagCompound().getInteger("FuelUsage")/5;
								storedCharge -= (int) jetpackStack.getTagCompound().getInteger("EnergyUsage")/5;
								jetpackStack.getTagCompound().getCompoundTag("Fluid").setInteger("Amount", storedFuel);
								jetpackStack.getTagCompound().setInteger("EnergyStored", storedCharge);
							}
							int postFuel = jetpackStack.getTagCompound().getCompoundTag("Fluid").getInteger("Amount");
							int postCharge = jetpackStack.getTagCompound().getInteger("EnergyStored");
							if (postFuel  - jetpackStack.getTagCompound().getInteger("FuelUsage")/5 >= 0 && postCharge  - jetpackStack.getTagCompound().getInteger("EnergyUsage")/5 >= 0) {
								double yawi = player.rotationYaw + 90;
								double yaw = Math.toRadians(yawi);
								double vZ = Math.cos(yaw);
								double vX = -1*Math.sin(yaw);
								double r = jetpackStack.getTagCompound().getFloat("Drag")*jetpackStack.getTagCompound().getInteger("Thrust")/(jetpackStack.getTagCompound().getInteger("Weight")*140);
								player.motionX += vX*r;
								player.motionZ += vZ*r;
								player.velocityChanged = true;
							}
					}
				}
			}	
		}
	}
}
