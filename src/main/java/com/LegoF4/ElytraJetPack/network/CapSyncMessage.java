package com.LegoF4.ElytraJetPack.network;

import java.io.IOException;

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

public class CapSyncMessage extends AbstractClientMessage<CapSyncMessage>{
	
	private int intMode;
	
	public CapSyncMessage() {}
	
	public CapSyncMessage(int msgType) {
		this.intMode = msgType;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.intMode = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(intMode);
		
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		IJetMode jetMode = player.getCapability(Main.JETMODE_CAP, null);
		jetMode.setJetMode(intMode);
	}
}
