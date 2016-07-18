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

public class CapSyncMessageInt extends AbstractClientMessage<CapSyncMessageInt>{
	
	private int intMode;
	private long mostBits;
	private long leastBits;
	
	public CapSyncMessageInt() {}
	
	public CapSyncMessageInt(int intMode, long mostBits, long leastBits) {
		this.intMode = intMode;
		this.mostBits = mostBits;
		this.leastBits = leastBits;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.intMode = buffer.readInt();
		this.mostBits = buffer.readLong();
		this.leastBits = buffer.readLong();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(intMode);
		buffer.writeLong(mostBits);
		buffer.writeLong(leastBits);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		UUID playerUUID = new UUID(mostBits, leastBits);
		EntityPlayer playerIn = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(playerUUID);
		IJetMode jetMode = playerIn.getCapability(Main.JETMODE_CAP, null);
		jetMode.setJetMode(intMode);
	}
}
