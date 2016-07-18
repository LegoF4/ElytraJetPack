package com.LegoF4.ElytraJetPack.capabilties;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTBase.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class JetHoverStorage implements IStorage<IJetHover>{
	
	 @Override
     public NBTBase writeNBT(Capability<IJetHover> capability, IJetHover instance, EnumFacing side) {
     
         return new NBTTagByte((byte)(instance.isJetHovering() ? 1 : 0));
     }

     @Override
     public void readNBT(Capability<IJetHover> capability, IJetHover instance, EnumFacing side, NBTBase nbt) {
     
         instance.setJetHovering(((NBTPrimitive)nbt).getByte() == 1);
     }
     
}
