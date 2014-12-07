package de.sirjavagaming.blockdrop;

import org.lwjgl.input.Keyboard;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameInterface implements ApplicationListener {
	
	public static final String TITLE = "BlockDrop by SirJavaGaming";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final Color INITIAL_COLOR = new Color(0f,0f,0f,1);
	
	private OrthographicCamera mainCam;
//	private OrthographicCamera gameCam;

	private SpriteBatch graphics;
//	private SpriteBatch gameGraphics;
	
	private Level level;
	
	private Player player;
	
	private GUI gui;
	
	private int state = STATE_START_MENU;

	private Music music;
	
	@Override
	public void create() {
		mainCam = new OrthographicCamera();
		mainCam.setToOrtho(false, WIDTH, HEIGHT);
		
		graphics = new SpriteBatch();
		graphics.setProjectionMatrix(mainCam.combined);
		
		
		gui = new GUI();
		gui.load();
		
		player = new Player();
		player.load();
		if(Gdx.files.internal("res/sound/music.mp3").exists()) {
		music = Gdx.audio.newMusic(Gdx.files.internal("res/sound/music.mp3"));
		music.setVolume(0.15f);
		music.play();
		music.setLooping(true);
		}
		
		ResourceManager.loadSound("br.ogg");
		ResourceManager.loadSound("bs.ogg");
		
		ResourceManager.loadTexture("bg.png");
		ResourceManager.loadSound("dead.ogg");
		
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
//		if(Boolean.TRUE) return;
		graphics.begin();
		switch (state) {
		case STATE_START_MENU:
			graphics.draw(ResourceManager.getTexture("main.png"), 0, 0, WIDTH, HEIGHT);
			input();
			break;
		case STATE_TUTORIAL:
			graphics.draw(ResourceManager.getTexture("tutorial.png"), 0, 0, WIDTH, HEIGHT);
			input();
			break;
		case STATE_PLAY:
			graphics.draw(ResourceManager.getTexture("bg.png"), 0, 0, WIDTH, HEIGHT);
			level.update(this);
			level.draw(graphics);
			player.update();
			player.render(graphics);
			gui.render(graphics, this);
			break;
		case STATE_DEAD:
			graphics.draw(ResourceManager.getTexture("bg.png"), 0, 0, WIDTH, HEIGHT);
			level.draw(graphics);
			player.render(graphics);
			gui.render(graphics, this);
			input();
		default:
			break;
		}
		graphics.end();
//		gameGraphics.begin();
//		gameGraphics.draw(ResourceManager.getTexture("test.png"),0, 0, 100, 100);
//		gameGraphics.end();
		
	}
	
	boolean t = false;
	boolean p = false;

	private void input() {
		if(state == STATE_START_MENU) {
			if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
				if(!p) {
					state = STATE_PLAY;
					level = new Level();
					level.load();
				}
				p = true;
			} else {
				p = false;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_T)) {
				if(!t) {
					state = STATE_TUTORIAL;
				}
				t = true;
			} else {
				t = false;
			}
		}
		if(state == STATE_TUTORIAL) {
			if(Keyboard.isKeyDown(Keyboard.KEY_T)) {
				if(!t) {
					state = STATE_START_MENU;
				}
				t = true;
			} else {
				t = false;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
				if(!p) {
					state = STATE_PLAY;
					level = new Level();
					level.load();
				}
				p = true;
			} else {
				p = false;
			}
		}
		if(state == STATE_DEAD) {
			if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
				if(!p) {
					level = new Level();
					level.load();
					
					gui = new GUI();
					gui.load();
					
					player = new Player();
					player.load();
					
					state = STATE_PLAY;
				}
				p = true;
			} else {
				p = false;
			}
		}
		
	}

	@Override
	public void resize(int arg0, int arg1) {}

	@Override
	public void resume() {}
	
	public OrthographicCamera getGameCam() {
		return mainCam;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public Level getLevel() {
		return level;
	}
	public int getState() {
		return state;
	}
	public static final int STATE_START_MENU = 94;
	public static final int STATE_PLAY = 644;
	public static final int STATE_DEAD = 646;
	public static final int STATE_TUTORIAL = 444;
}
