package com.wangyifan.speedrunhelper;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;

import java.util.Timer;

public class Main implements ModInitializer {
	private static KeyBinding startKey;
	private static KeyBinding stopKey;
	Timer t = null;
	MyTimer myTimer = null;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");

		startKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.speedrunhelper.start", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_K, // The keycode of the key
				"category.speedrunhelper.control" // The translation key of the keybinding's category.
		));

		stopKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.speedrunhelper.stop", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_L, // The keycode of the key
				"category.speedrunhelper.control" // The translation key of the keybinding's category.
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (startKey.wasPressed()) {
				myTimer = new MyTimer(client);
				t = new Timer();
				t.scheduleAtFixedRate(myTimer, 0,1);
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (stopKey.wasPressed()) {
				if (myTimer == null) {
					client.player.sendMessage(new LiteralText("§cThere's no timers!"), false);

				} else {
					client.player.sendMessage(new LiteralText("Stopped the timer at " + myTimer.formattedTime + "."), false);
					myTimer.cancel();
					myTimer = null;
					t.cancel();
					t = null;
				}
			}
		});
	}
}
