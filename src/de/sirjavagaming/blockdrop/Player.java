package de.sirjavagaming.blockdrop;

import static de.sirjavagaming.blockdrop.ResourceManager.*;

import org.lwjgl.input.Keyboard;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
	
	private int viewDir = FORWARD;
	
	private static final float PLAYER_SCALE = 1.5f;
	
	private boolean moving = false;
	
	private boolean removed = false;
	
	private long removeStart;
	
	private int x;
	private int y;
	
	private static final int SPEED = 4;
	
	
	private static final String[] playersTextures = new String[] { "playerf.png", "playerf1.png", "playerf2.png", "playerb.png", "playerb1.png", "playerb2.png", "playerl.png", "playerl1.png", "playerl2.png", "playerr.png", "playerr1.png", "playerr2.png" };
	
	public void load() {
		x = GameInterface.WIDTH / 2;
		y = (GameInterface.HEIGHT - 120) / 2;
		for(int i = 0; i < playersTextures.length; i++) {
			ResourceManager.loadTexture(playersTextures[i]);
		}
	}
	
	public void update() {
		if(removed) return;
		handleInput();
	}
	
	public void handleInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			viewDir = LEFT;
			x -= SPEED;
			if(x < 45) {
				x = 45;
			}
			moving = true;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			viewDir = RIGHT;
			x += SPEED;
			if(x > GameInterface.WIDTH - 45) {
				x = GameInterface.WIDTH - 45;
			}
			moving = true;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			viewDir = BACK;
			y += SPEED;
			if(y > GameInterface.HEIGHT - 45) {
				y = GameInterface.HEIGHT - 45;
			}
			moving = true;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			viewDir = FORWARD;
			y -= SPEED;
			if(y < 165) {
				y = 165;
			}
			moving = true;
		} else {
			moving = false;
		}
				 
				
	}
	
	public void render(SpriteBatch graphics) {
		if(removed) {
			if((System.currentTimeMillis() - removeStart) < 750) {
				float decrease = (System.currentTimeMillis() - removeStart) / 40f;
				if(decrease < 1) {
					decrease = 1;
				}
				graphics.draw(getTexture(playersTextures[viewDir * 3]), x - (16 * PLAYER_SCALE), y, 32 * PLAYER_SCALE / decrease, 32 * PLAYER_SCALE / decrease);
			}
		} else {
		if(moving) {
			boolean b = ((System.currentTimeMillis() % 200) < 100);
			graphics.draw(b ? getTexture(playersTextures[viewDir*3+1]) : getTexture(playersTextures[viewDir*3+2]), x - (16 * PLAYER_SCALE), y, 32 * PLAYER_SCALE, 32 * PLAYER_SCALE);
		} else {
			graphics.draw(getTexture(playersTextures[viewDir * 3]), x - (16 * PLAYER_SCALE), y, 32 * PLAYER_SCALE, 32 * PLAYER_SCALE);
		}
		}
	}
	
	public void remove() {
		removed = true;
		removeStart = System.currentTimeMillis() + 250;
		playSoundEffect("dead.ogg");
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public static final int BACK = 1;
	public static final int FORWARD = 0;
	public static final int RIGHT = 3;
	public static final int LEFT = 2;

	
}
