package de.sirjavagaming.blockdrop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class LWJGLGame {
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = GameInterface.TITLE;
		config.width = GameInterface.WIDTH;
		config.height = GameInterface.HEIGHT;
		config.initialBackgroundColor = GameInterface.INITIAL_COLOR;
		config.resizable = false;
		new LwjglApplication(new GameInterface(), config);
	}

}
