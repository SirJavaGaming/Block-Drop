package de.sirjavagaming.blockdrop;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ResourceManager {
	
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	private static HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	

	public static Texture getTexture(String file) {
		Texture t = textures.get(file);
		if(t != null) {
			return t;
		} else {
			return loadTexture(file);
		}
		
	}

	public static Texture loadTexture(String file) {
		if(textures.containsKey(file)) return textures.get(file);
		Texture t = new Texture("res/img/" + file);
		textures.put(file, t);
		return t;
	}
	
	public static Sound getSound(String file) {
		Sound t = sounds.get(file);
		if(t != null) {
			return t;
		} else {
			return loadSound(file);
		}
		
	}

	public static Sound loadSound(String file) {
		if(sounds.containsKey(file)) return sounds.get(file);
		Sound t = Gdx.audio.newSound(Gdx.files.internal("res/sound/" + file));
		sounds.put(file, t);
		return t;
	}
	
	public static void playSoundEffect(String file, float volume) {
		getSound(file).play(volume);
	}
	
	public static void playSoundEffect(String file) {
		playSoundEffect(file, 0.4f);
	}

}
