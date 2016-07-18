package com.LegoF4.ElytraJetPack.network;

import java.io.IOException;
import java.util.UUID;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.capabilties.IJetFlying;
import com.LegoF4.ElytraJetPack.capabilties.IJetHover;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;
import com.LegoF4.ElytraJetPack.network.AbstractMessage.AbstractClientMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;

public class CapSyncMessageBool2 extends AbstractClientMessage<CapSyncMessageBool2>{
	
	private boolean boolHover;
	private long mostBits;
	private long leastBits;
	
	public CapSyncMessageBool2() {}
	
	public CapSyncMessageBool2(boolean boolHover, long mostBits, long leastBits) {
		this.boolHover = boolHover;
		this.mostBits = mostBits;
		this.leastBits = leastBits;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.boolHover = buffer.readBoolean();
		this.mostBits = buffer.readLong();
		this.leastBits = buffer.readLong();
	}
	
	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBoolean(boolHover);
		buffer.writeLong(mostBits);
		buffer.writeLong(leastBits);
		
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		UUID playerUUID = new UUID(mostBits, leastBits);
		EntityPlayer playerIn = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(playerUUID);
		IJetHover jetHover = playerIn.getCapability(Main.JETHOVER_CAP, null);
		jetHover.setJetHovering(boolHover);
	}
}
