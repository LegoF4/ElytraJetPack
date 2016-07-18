package com.LegoF4.ElytraJetPack.network;

import java.io.IOException;
import java.util.UUID;

import com.LegoF4.ElytraJetPack.Main;
import com.LegoF4.ElytraJetPack.capabilties.IJetFlying;
import com.LegoF4.ElytraJetPack.capabilties.IJetMode;
import com.LegoF4.ElytraJetPack.capabilties.IJetTicks;
import com.LegoF4.ElytraJetPack.network.AbstractMessage.AbstractClientMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;

public class CapSyncMessageInt2 extends AbstractClientMessage<CapSyncMessageInt2>{
	
	private int intTicks;
	private long mostBits;
	private long leastBits;
	
	public CapSyncMessageInt2() {}
	
	public CapSyncMessageInt2(int intTicks, long mostBits, long leastBits) {
		this.intTicks = intTicks;
		this.mostBits = mostBits;
		this.leastBits = leastBits;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.intTicks = buffer.readInt();
		this.mostBits = buffer.readLong();
		this.leastBits = buffer.readLong();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(intTicks);
		buffer.writeLong(mostBits);
		buffer.writeLong(leastBits);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		UUID playerUUID = new UUID(mostBits, leastBits);
		EntityPlayer playerIn = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(playerUUID);
		IJetTicks jetTicks = playerIn.getCapability(Main.JETTICKS_CAP, null);
		jetTicks.setJetTicks(intTicks);
	}
}
