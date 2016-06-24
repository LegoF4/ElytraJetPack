package com.LegoF4.ElytraJetPack;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindings {
	
	public static KeyBinding keyFly;
	public static KeyBinding keyFlyThrust;
	
	public static void init() {
		keyFly = new KeyBinding("key.keyFly",Keyboard.KEY_C,"key.categories.elytrajet");
		keyFlyThrust = new KeyBinding("key.keyFlyThrust",Keyboard.KEY_V,"key.categories.elytrajet");
		
		ClientRegistry.registerKeyBinding(keyFly);
		ClientRegistry.registerKeyBinding(keyFlyThrust);
	}
}
