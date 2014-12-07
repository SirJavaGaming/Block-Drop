package de.sirjavagaming.blockdrop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GUI {
	
	public void load() {
//		ResourceManager.loadTexture("1s.png");
//		ResourceManager.loadTexture("2s.png");
//		ResourceManager.loadTexture("3s.png");
//		ResourceManager.loadTexture("4s.png");
//		ResourceManager.loadTexture("5s.png");
//		ResourceManager.loadTexture("dontmove.png");
		ResourceManager.loadTexture("bar.png");
		ResourceManager.loadTexture("numbers.png");
		ResourceManager.loadTexture("text.png");
	}
	
	public void render(SpriteBatch graphics, GameInterface i) {
		Level l = i.getLevel();
		if(i.getState() == GameInterface.STATE_DEAD) {
			graphics.draw(ResourceManager.getTexture("dead.png"), 0, 0, GameInterface.WIDTH, GameInterface.HEIGHT);
			drawLevel(graphics, l, true);
		} else {
			graphics.draw(ResourceManager.getTexture("bar.png"), 0, 0, GameInterface.WIDTH, 160);
		if(l.state == Level.STATE_COUNTDOWN || l.state == Level.STATE_NO_BLOCKS) {
			graphics.draw(ResourceManager.getTexture("block" + l.getSelectedBlock() + ".png"), 538, 23, 64, 64);
		}
		if(l.state == Level.STATE_COUNTDOWN) {
			TextureRegion t;
			switch (l.getSeconds()) {
			case 1:
				t = new TextureRegion(ResourceManager.getTexture("text.png"), 256, 0, 256, 128);
				break;
			case 2:
				t = new TextureRegion(ResourceManager.getTexture("text.png"), 0, 387, 257, 129);
				break;
			case 3:
				t = new TextureRegion(ResourceManager.getTexture("text.png"), 0, 272, 257, 127);
				break;
			case 4:
				t = new TextureRegion(ResourceManager.getTexture("text.png"), 0, 128, 258, 127);
				break;
			case 5:
				t = new TextureRegion(ResourceManager.getTexture("text.png"), 0, 0, 256, 124);
				break;
			default:
				t = null;
				break;
			}
			if(t != null) {
				graphics.draw(t, 120, 40, 200, 110);
			}
		}
		if(l.state == Level.STATE_NO_BLOCKS) {
			graphics.draw(new TextureRegion(ResourceManager.loadTexture("text.png"), 256, 128, 256, 128), 120, 44, 200, 110);
		}
		drawLevel(graphics, l, false);
		}
		
	}

	private void drawLevel(SpriteBatch graphics, Level l, boolean b) {
		final int scale = 40;
		
		String level = String.valueOf(l.getLevel());
		int width = level.length() * 40;
		int startX = (GameInterface.WIDTH - width) / 2;
		if(b) startX += 165;
		for(int i = 0; i < level.length(); i++) {
			graphics.draw(getTexRegionFor((String) level.subSequence(i, i+1)), startX + i * scale, 30, scale, scale);
		}
		
	}
	
	public TextureRegion getTexRegionFor(String s) {
		switch (Integer.valueOf(s)) {
		case 0:
			return new TextureRegion(ResourceManager.getTexture("numbers.png"), 0, 0, 64, 64);
		case 1:
			return new TextureRegion(ResourceManager.getTexture("numbers.png"), 64, 0, 64, 64);
		case 2:
			return new TextureRegion(ResourceManager.getTexture("numbers.png"), 128, 0, 64, 64);
		case 3:
			return new TextureRegion(ResourceManager.getTexture("numbers.png"), 196, 0, 64, 64);
		case 4:
			return new TextureRegion(ResourceManager.getTexture("numbers.png"), 4, 64, 60, 62);
		case 5:
			return new TextureRegion(ResourceManager.getTexture("numbers.png"), 68, 64, 60, 64);
		case 6:
			return new TextureRegion(ResourceManager.getTexture("numbers.png"), 128, 64, 64, 64);
		case 7:
			return new TextureRegion(ResourceManager.getTexture("numbers.png"), 196, 64, 64, 62);
		case 8:
			return new TextureRegion(ResourceManager.getTexture("numbers.png"), 0, 128, 64, 64);
		case 9:
			return new TextureRegion(ResourceManager.getTexture("numbers.png"), 64, 129, 64, 61);
		}
		return null;
	}

}
