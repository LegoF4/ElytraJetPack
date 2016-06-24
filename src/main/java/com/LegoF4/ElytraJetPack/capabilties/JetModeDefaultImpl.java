package com.LegoF4.ElytraJetPack.capabilties;

public class JetModeDefaultImpl implements IJetMode{
	private int isJetMode = 0;
    @Override public int isJetMode() { return isJetMode; }
    @Override public void setJetMode(int value) { this.isJetMode = value; }
}
