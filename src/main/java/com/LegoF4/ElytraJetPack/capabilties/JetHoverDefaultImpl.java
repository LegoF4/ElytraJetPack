package com.LegoF4.ElytraJetPack.capabilties;

public class JetHoverDefaultImpl implements IJetHover{
	private boolean isJetHover = false;
    @Override public boolean isJetHovering() { return isJetHover; }
    @Override public void setJetHovering(boolean value) { this.isJetHover = value; }
}
