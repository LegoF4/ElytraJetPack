package com.LegoF4.ElytraJetPack.capabilties;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class JetFlyingStorage implements IStorage<IJetFlying>{
	
	 @Override
     public NBTBase writeNBT(Capability<IJetFlying> capability, IJetFlying instance, EnumFacing side) {
     
         return new NBTTagByte((byte)(instance.isJetFlying() ? 1 : 0));
     }

     @Override
     public void readNBT(Capability<IJetFlying> capability, IJetFlying instance, EnumFacing side, NBTBase nbt) {
     
         instance.setJetFlying(((NBTPrimitive)nbt).getByte() == 1);
     }
     
}
