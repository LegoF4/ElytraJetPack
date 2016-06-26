package com.LegoF4.ElytraJetPack.events;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.Items.ItemManager;
import com.LegoF4.ElytraJetPack.Items.PackArmor;
import com.LegoF4.ElytraJetPack.capabilties.IJetFlying;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
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
			IJetFlying jetFlying = player.getCapability(Main.JETFLY_CAP, null);
			IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
			
			if (jetMode.isJetMode() > 0) {
				if (player.onGround && event.side == Side.SERVER) {
					jetFlying.setJetFlying(false);
		           }
			}
			if (jetFlying.isJetFlying()) {
				if (player.motionY > -1.0F) {
					player.fallDistance = 1.0F;
				}
				if (player.isCollidedHorizontally && jetMode.isJetMode() == 2)
                {
                    double d1 = Math.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ);
                    float f1 = (float)(d1 * 10.0D - 3.0D);

                    if (f1 > 0.0F)
                    {
                        player.attackEntityFrom(DamageSource.flyIntoWall, f1);
                    }
                }
			}
		}
	}
}
