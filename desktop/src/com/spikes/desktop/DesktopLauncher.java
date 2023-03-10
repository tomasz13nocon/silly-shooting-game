package com.spikes.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.spikes.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Rybki";
		config.resizable = false;
		config.width = 1200;
		config.height = 700;
		config.addIcon("icon.gif", Files.FileType.Internal);
		new LwjglApplication(new Main(), config);
	}
}
