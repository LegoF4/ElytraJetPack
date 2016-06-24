package com.LegoF4.ElytraJetPack.capabilties;

public class JetFlyingDefaultImpl implements IJetFlying{
	private boolean isJetFlying = false;
    @Override public boolean isJetFlying() { return isJetFlying; }
    @Override public void setJetFlying(boolean value) { this.isJetFlying = value; }
}
