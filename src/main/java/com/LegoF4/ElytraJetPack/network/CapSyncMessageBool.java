package com.LegoF4.ElytraJetPack.network;

import java.io.IOException;
import java.util.UUID;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.capabilties.IJetFlying;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;
import com.LegoF4.ElytraJetPack.network.AbstractMessage.AbstractClientMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;

public class CapSyncMessageBool extends AbstractClientMessage<CapSyncMessageBool>{
	
	private boolean boolFly;
	private long mostBits;
	private long leastBits;
	
	public CapSyncMessageBool() {}
	
	public CapSyncMessageBool(boolean intMode, long mostBits, long leastBits) {
		this.boolFly = intMode;
		this.mostBits = mostBits;
		this.leastBits = leastBits;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.boolFly = buffer.readBoolean();
		this.mostBits = buffer.readLong();
		this.leastBits = buffer.readLong();
	}
	
	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBoolean(boolFly);
		buffer.writeLong(mostBits);
		buffer.writeLong(leastBits);
		
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		UUID playerUUID = new UUID(mostBits, leastBits);
		EntityPlayer playerIn = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(playerUUID);
		IJetFlying jetFly = playerIn.getCapability(Main.JETFLY_CAP, null);
		jetFly.setJetFlying(boolFly);
		
	}
}
