package com.LegoF4.ElytraJetPack.events;

import java.util.UUID;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.capabilties.IJetFlying;
import com.LegoF4.ElytraJetPack.capabilties.IJetHover;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;
import com.LegoF4.ElytraJetPack.capabilties.IJetTicks;
import com.LegoF4.ElytraJetPack.items.ItemManager;
import com.LegoF4.ElytraJetPack.items.PackArmor;
import com.LegoF4.ElytraJetPack.network.CapSyncMessageBool;
import com.LegoF4.ElytraJetPack.network.CapSyncMessageInt2;
import com.LegoF4.ElytraJetPack.network.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JetFlyingHandler {
	
	@SubscribeEvent
	public void onEvent(TickEvent.PlayerTickEvent event) {
		if (event.phase == Phase.END) {
			EntityPlayer player = (EntityPlayer) event.player;
			ItemStack jetpackStack = player.inventory.armorInventory[2];
			IJetFlying jetFlying = player.getCapability(Main.JETFLY_CAP, null);
			IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
			IJetTicks jetTicks = player.getCapability(Main.JETTICKS_CAP, null);
			IJetHover jetHover = player.getCapability(Main.JETHOVER_CAP, null);
			UUID playerUUID = player.getUniqueID();
			long mostBits = playerUUID.getMostSignificantBits();
			long leastBits = playerUUID.getLeastSignificantBits();
			if ((player.onGround || player.capabilities.isFlying) && event.side == Side.SERVER) {
				if (jetFlying.isJetFlying() == true || jetTicks.getJetTicks() > 0) {
					jetFlying.setJetFlying(false);
					PacketDispatcher.sendToAll(new CapSyncMessageBool(jetFlying.isJetFlying(), mostBits, leastBits));
					jetTicks.setJetTicks(0);
					PacketDispatcher.sendToAll(new CapSyncMessageInt2(jetTicks.getJetTicks(), mostBits, leastBits));
				}
		    }
			if (jetFlying.isJetFlying()==false) {
				
				player.eyeHeight = 1.62f;
				
			}
			
			if (jetpackStack != null) {
				if (jetpackStack.getItem() instanceof PackArmor) {
					if (jetFlying.isJetFlying()==true) {
						if (player.motionY > -1.0F) {
							player.fallDistance = 1.0F;
						}
						if (player.isCollidedHorizontally && jetMode.isJetMode() == 2) {
		                    double d1 = Math.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ);
		                    float speed = (float)(d1 * 10.0D - 3.0D);

		                    if (speed > 0.0F) {
		                        player.attackEntityFrom(DamageSource.flyIntoWall, speed);
		                    }
		                }
					}
					if (jetMode.isJetMode() == 1 && jetHover.isJetHovering() == true) {
						if (jetpackStack.getTagCompound().getInteger("Avionics") > 1) {
							if (!player.onGround) {
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
									player.motionY += 0.023F + (jetpackStack.getTagCompound().getInteger("Avionics")*0.001*2);
									player.motionY *= 0.4;
									player.motionX *= 0.5 + (jetpackStack.getTagCompound().getInteger("Avionics")*0.05);
									player.motionZ *= 0.5 + (jetpackStack.getTagCompound().getInteger("Avionics")*0.05);
								}
							}
						}
					}
					if (jetMode.isJetMode() == 2) {
						if (jetFlying.isJetFlying()==true) {
							jetTicks.setJetTicks(jetTicks.getJetTicks()+1);
							PacketDispatcher.sendToAll(new CapSyncMessageInt2(jetTicks.getJetTicks(), mostBits, leastBits));
							player.eyeHeight = 0.4f;
							player.fallDistance = 0;
							player.motionY += 0.08;
							Vec3d vec3d = player.getLookVec();
							player.motionX *= 1.0989010989010989010989010989011F;
							player.motionZ *= 1.0989010989010989010989010989011F;
					        double pitchi = Math.toRadians(player.rotationPitch);
					        
					        double horizontalLookVec = Math.sqrt(vec3d.xCoord * vec3d.xCoord + vec3d.zCoord * vec3d.zCoord);
					        double horizontalSpeed = Math.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ);
					        double playerSpeed = vec3d.lengthVector();
					        double pitchCos = Math.cos(pitchi);
					        pitchCos = pitchCos * pitchCos * Math.min(1.0D, playerSpeed / 0.4D);
					        player.motionY += -0.08D + (double)pitchCos * 0.06D;

					        if (player.motionY < 0.0D && horizontalLookVec > 0.0D)
					        {
					            double d2 = player.motionY * -0.1D * pitchCos;
					            player.motionY += d2;
					            player.motionX += vec3d.xCoord * d2 / horizontalLookVec;
					            player.motionZ += vec3d.zCoord * d2 / horizontalLookVec;
					        }

					        if (pitchi < 0.0D)
					        {
					            double d9 = horizontalSpeed * -Math.sin(pitchi) * 0.04D;
					            player.motionY += d9 * 3.2D;
					            player.motionX -= vec3d.xCoord * d9 / horizontalLookVec;
					            player.motionZ -= vec3d.zCoord * d9 / horizontalLookVec;
					        }

					        if (horizontalLookVec > 0.0D)
					        {
					            player.motionX += (vec3d.xCoord / horizontalLookVec * horizontalSpeed - player.motionX) * 0.1D;
					            player.motionZ += (vec3d.zCoord / horizontalLookVec * horizontalSpeed - player.motionZ) * 0.1D;
					        }
					        if (Math.sqrt(player.motionX * player.motionX + player.motionY * player.motionY + player.motionZ * player.motionZ) < 1.0F) {
					        	
					        }
					        float drag = (1 - jetpackStack.getTagCompound().getFloat("Drag")) / 6;
					        player.motionX *= 0.9900000095367432D - drag;
					        player.motionY *= 0.9800000190734863D - drag/1.2;
					        player.motionZ *= 0.9900000095367432D - drag;
					        player.moveEntity(player.motionX, player.motionY, player.motionZ);
						}
					}
				}
			}
		}
	}
}
