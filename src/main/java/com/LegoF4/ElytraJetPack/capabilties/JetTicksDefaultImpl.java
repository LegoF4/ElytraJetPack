package com.LegoF4.ElytraJetPack.capabilties;

public class JetTicksDefaultImpl implements IJetTicks{
	private int getJetTicks = 0;
    @Override public int getJetTicks() { return getJetTicks; }
    @Override public void setJetTicks(int value) { this.getJetTicks = value; }
}
