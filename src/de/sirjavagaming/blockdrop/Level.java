package de.sirjavagaming.blockdrop;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level {
	
	private Random random;
	
	private int level = 0;
	
	public int state;
	
	private long stateStartTime;
	
	private int[][] levelData;
	private boolean b = false;
	
	private int selected_block;
	
	private long anim1Start;
	
	public void load() {
		random = new Random();
		for(int i = 0; i < 12; i++) {
			ResourceManager.loadTexture("block" + i + ".png");
		}
		ResourceManager.loadTexture("mine.png");
		generateLevel();
	}
	
	public void update(GameInterface i) {
		if(state == STATE_MOVING) {
			if(System.currentTimeMillis() - stateStartTime > 4000) {
				state = STATE_COUNTDOWN;
				stateStartTime = System.currentTimeMillis();
				selected_block = random.nextInt(12);
			}
		} else if(state == STATE_COUNTDOWN) {
			if(System.currentTimeMillis() - stateStartTime > 5000) {
				state = STATE_NO_BLOCKS;
				stateStartTime = System.currentTimeMillis();
				anim1Start = System.currentTimeMillis();
				ResourceManager.playSoundEffect("br.ogg");
			}
		} else if(state == STATE_NO_BLOCKS) {
			checkAlive(i);
			if(System.currentTimeMillis() - stateStartTime > 1250) {
				if(!b) {
					b = true;
					ResourceManager.playSoundEffect("bs.ogg");
				}
			}
			if(System.currentTimeMillis() - stateStartTime > 1500) {
				state = STATE_MOVING;
				stateStartTime = System.currentTimeMillis();
				generateLevel();
				b = false;
			}
		}
	}
	
	private void checkAlive(GameInterface i) {
		float amtOfCellsX = (float)((int)(13.5f + (level < 10 ? ( 1.8 * level) : (18f))));
		float amtOfCellsY = (float)((int)(7.5f + (level < 10 ? ( 1 * level) : (10f))));
		
		float cellWidth = ((float)(GameInterface.WIDTH - 80) / (float)amtOfCellsX);
		float cellHeight = ((float)(GameInterface.HEIGHT - 200) / (float)amtOfCellsY);
		
		Player p = i.getPlayer();
		
		int x = (int)(((float)p.getX() - 40f) / cellWidth);
		int y = (int)(((float)p.getY() - 160f) / cellHeight);
		
		if(levelData[x][y] != selected_block) {
			p.remove();
			i.setState(GameInterface.STATE_DEAD);
		}
		
	}

	public int getSeconds() {
		return 5 - (int) ((System.currentTimeMillis() - stateStartTime) / 1000);
	}
	
	public void draw(SpriteBatch sb) {
		float amtOfCellsX = (float)((int)(13.5f + (level < 10 ? ( 1.8 * level) : (18f))));
		float amtOfCellsY = (float)((int)(7.5f + (level < 10 ? ( 1 * level) : (10f))));
		
		float cellWidth = ((float)(GameInterface.WIDTH - 80) / (float)amtOfCellsX);
		float cellHeight = ((float)(GameInterface.HEIGHT - 200) / (float)amtOfCellsY);
		
		for(int i = 0; i < levelData.length; i++) {
			for(int j = 0; j < levelData[0].length; j++) {
				if(state == STATE_NO_BLOCKS) {
					if(levelData[i][j] == selected_block) {
						sb.draw(ResourceManager.getTexture("block" + levelData[i][j] + ".png"), i * cellWidth + 40, j * cellHeight + 160, cellWidth, cellHeight);
					} else {
						long animstate = System.currentTimeMillis() - anim1Start;
						if(animstate < 300) {
							float xSize = ((float)cellWidth / (float)animstate * 40);
							float ySize = ((float)cellHeight / (float)animstate * 40);
							if(xSize > cellWidth) xSize = cellWidth;
							if(ySize > cellHeight) ySize = cellHeight;
							sb.draw(ResourceManager.getTexture("block" + levelData[i][j] + ".png"), i * cellWidth + ((cellWidth - xSize) / 2) + 40 , 160 +j * cellHeight + ((cellHeight - ySize) / 2), xSize, ySize);
						}
					}
				} else {
					sb.draw(ResourceManager.getTexture("block" + levelData[i][j] + ".png"), i * cellWidth + 40, j * cellHeight + 160, cellWidth, cellHeight);
				}
			}
		}
	
	}
	
	public void generateLevel() {
		level++;

		int amtOfCellsX = (int)(13.5f + (level < 10 ? ( 1.8f * level) : (18f)));
		int amtOfCellsY = (int)(7.5f + (level < 10 ? ( 1f * level) : (10f)));
		
		levelData = new int[(int) amtOfCellsX][(int) amtOfCellsY];
		
		for(int i = 0; i < amtOfCellsX; i++) {
			for(int j = 0; j < amtOfCellsY; j++) {
				levelData[i][j] = random.nextInt(12);
 			}
		}
		state = STATE_MOVING;
		stateStartTime = System.currentTimeMillis();
	}
	
	public int getSelectedBlock() {
		return selected_block;
	}
	
	public int getLevel() {
		return level;
	}
	
	public static final int STATE_MOVING = 49894;
	public static final int STATE_NO_BLOCKS = 191;
	public static final int STATE_COUNTDOWN = 8998;
	

}
