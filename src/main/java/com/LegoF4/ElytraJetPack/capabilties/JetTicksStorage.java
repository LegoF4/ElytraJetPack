package com.LegoF4.ElytraJetPack.capabilties;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTBase.NBTPrimitive;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class JetTicksStorage implements IStorage<IJetTicks>{
	@Override
    public NBTBase writeNBT(Capability<IJetTicks> capability, IJetTicks instance, EnumFacing side) {
        return new NBTTagInt(instance.getJetTicks());
    }
    @Override
    public void readNBT(Capability<IJetTicks> capability, IJetTicks instance, EnumFacing side, NBTBase nbt) {
    
        instance.setJetTicks(((NBTPrimitive)nbt).getInt());
    }
    
}
