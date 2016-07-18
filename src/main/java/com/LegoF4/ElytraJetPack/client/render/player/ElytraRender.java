package com.LegoF4.ElytraJetPack.client.render.player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.capabilties.IJetFlying;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;
import com.LegoF4.ElytraJetPack.capabilties.IJetTicks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ElytraRender {
	@SubscribeEvent
	public void onEvent(RenderPlayerEvent.Pre event) {
		PreRotateCorpse(event.getEntity());
	}
	public void PreRotateCorpse(Entity entity) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
			IJetFlying jetFly = player.getCapability(Main.JETFLY_CAP, null);
			if (jetMode.isJetMode() == 2) {
				if (jetFly.isJetFlying()) {
					GL11.glPushMatrix();
					IJetTicks jetTicks = player.getCapability(Main.JETTICKS_CAP, null);
					float f = jetTicks.getJetTicks() + Minecraft.getMinecraft().getRenderPartialTicks();
					float f1 = MathHelper.clamp_float(f * f / 100.0F, 0.0F, 1.0F);
					float z = (float) Math.cos(Math.toRadians(player.rotationYaw+180D));
					float x = (float) Math.sin(Math.toRadians(player.rotationYaw+180D));
					GL11.glRotatef(f1 * (-90.0F - player.rotationPitch), z, 0.0F, x);
					Vec3d vec3d = player.getLook(Minecraft.getMinecraft().getRenderPartialTicks());
					double d0 = player.motionX * player.motionX + player.motionZ * player.motionZ;
					double d1 = vec3d.xCoord * vec3d.xCoord + vec3d.zCoord * vec3d.zCoord;
					if (d0 > 0.0D && d1 > 0.0D) {
						double d2 = (player.motionX * vec3d.xCoord + player.motionZ * vec3d.zCoord) / (Math.sqrt(d0) * Math.sqrt(d1));
						double d3 = player.motionX * vec3d.zCoord - player.motionZ * vec3d.xCoord;
						GL11.glRotatef((float) (Math.signum(d3) * Math.acos(d2)) * 180.0F / (float) Math.PI, 1.0F, 1.0F, 1.0F);
					}
				}
			}
        }
	}
	
	@SubscribeEvent 
	public void onEvent(RenderPlayerEvent.Post event) {
		PostRotateCorpse(event.getEntity());
		}

	public void PostRotateCorpse(Entity entity) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
			IJetFlying jetFly = player.getCapability(Main.JETFLY_CAP, null);
			if (jetMode.isJetMode() == 2) {
				if (jetFly.isJetFlying()) {
					if (jetMode.isJetMode() == 2) {
						GL11.glRotatef(0.0F, 0.0F, 0.0F, 0.0F);
						GL11.glRotatef(0.0F, 0.0F, 0.0F, 0.0F);
						GL11.glPopMatrix();
					}
				}
			}
        }
	}
}
