package com.LegoF4.ElytraJetPack.capabilties;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class JetModeStorage implements IStorage<IJetMode>{
	@Override
    public NBTBase writeNBT(Capability<IJetMode> capability, IJetMode instance, EnumFacing side) {
        return new NBTTagInt(instance.isJetMode());
    }
    @Override
    public void readNBT(Capability<IJetMode> capability, IJetMode instance, EnumFacing side, NBTBase nbt) {
    
        instance.setJetMode(((NBTPrimitive)nbt).getInt());
    }
    
}
