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
			
			if (jetMode.isJetMode() == 2) {
				if (player.onGround && event.side == Side.SERVER) {
					jetFlying.setJetFlying(false);
		           }
			}
		}
	}
}
