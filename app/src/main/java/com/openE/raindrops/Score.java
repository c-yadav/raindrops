package com.openE.raindrops;

import com.openE.framework.gl.SpriteBatcher;
//import com.openE.framework.gl.TextureRegion;
import com.openE.framework.math.Rectangle;
import com.openE.framework.math.Vector2;

public class Score {

	public static float time;
	
	private String scoreText;
	private int score;
	private int pottery;
	
	private Vector2 firstGlymphPos;

	public static final int MAX_CHARS_DISPLAY = 12; // score: 12345 //
	public static final float CHAR_WIDTH = 0.5f;
	public static final float CHAR_HEIGHT = 1f;
	public static final float LINE_SPACING = 1f;
	public static final int MAX_NUM_LINES = 4;
	public static final float CHAR_DISTANCE_FROM_TOP = 1f;
	public static final float CHAR_DISTANCE_FROM_RIGHT = 1f;
	
	public static final Vector2 tStormIconPosition = new Vector2(
			RainGame.WORLD_WIDTH - (CHAR_DISTANCE_FROM_RIGHT + (MAX_CHARS_DISPLAY*CHAR_WIDTH)),
			RainGame.WORLD_HEIGHT - 
			(CHAR_DISTANCE_FROM_TOP + MAX_NUM_LINES* (CHAR_HEIGHT)));
	/*
	public static final Vector2 tSlowIconPosition = new Vector2(
			RainGame.WORLD_WIDTH - (CHAR_DISTANCE_FROM_RIGHT + (MAX_CHARS_DISPLAY*CHAR_WIDTH)),
			RainGame.WORLD_HEIGHT - 
			(CHAR_DISTANCE_FROM_TOP + (MAX_NUM_LINES + LINE_SPACING)* (CHAR_HEIGHT)));
	 */
	
	public static final float POWERUP_ICON_WIDTH = 0.5f;
	public static final float POWERUP_ICON_HEIGHT = 1f;
	
	public static final Vector2 tSlowIconPosition = new Vector2(
			RainGame.WORLD_WIDTH - (CHAR_DISTANCE_FROM_RIGHT + (MAX_CHARS_DISPLAY*CHAR_WIDTH) - (2*POWERUP_ICON_WIDTH + CHAR_WIDTH)),
			RainGame.WORLD_HEIGHT - 
			(CHAR_DISTANCE_FROM_TOP + MAX_NUM_LINES* (CHAR_HEIGHT)));
	

	
	public static final Rectangle tStormIconRect = new Rectangle(
			tStormIconPosition.x - POWERUP_ICON_WIDTH , tStormIconPosition.y
					- POWERUP_ICON_HEIGHT , 2*POWERUP_ICON_WIDTH,
			2*POWERUP_ICON_HEIGHT);
	public static final Rectangle tSlowIconRect = new Rectangle(
			tSlowIconPosition.x - POWERUP_ICON_WIDTH, tSlowIconPosition.y
					- POWERUP_ICON_HEIGHT, 2*POWERUP_ICON_WIDTH,
			2*POWERUP_ICON_HEIGHT);
	
	//public static final TextureRegion powerupThunderstormIcon = Assets.boltIcon;
	
	public Score() {

		firstGlymphPos = new Vector2(
				(float) ( RainGame.WORLD_WIDTH - (Score.MAX_CHARS_DISPLAY * Score.CHAR_WIDTH + Score.CHAR_DISTANCE_FROM_RIGHT) ),
				(float) ((RainGame.WORLD_HEIGHT) - Score.CHAR_DISTANCE_FROM_TOP));
		//firstGlymphPos = new Vector2(1,(float) ((RainGame.WORLD_HEIGHT) - Score.CHAR_DISTANCE_FROM_TOP));
		score = 0;
		scoreText = "score:" + score;
		pottery = 0;
		time = 0;
	}

	public void updateScore( int increment ) {
		score += increment;
		scoreText =  "score:" + score;
	}
	
	public void updatePotteryScore( int pottery ) {
		this.pottery = pottery;
	}
	
	public void updateTime(float deltaTime) {
		Score.time += deltaTime;
	}
	
	public void resetTime () { 
		Score.time = 0;
	}
	
	public void drawScore(SpriteBatcher batcher) { // x,y coordinates are center
													// of the first glymph
		Assets.font.drawText(batcher, scoreText, firstGlymphPos.x,
				firstGlymphPos.y, Score.CHAR_WIDTH, Score.CHAR_HEIGHT);
		Assets.font.drawText(batcher, "Time " + (int) Score.time,
				firstGlymphPos.x, firstGlymphPos.y - Score.LINE_SPACING,
				Score.CHAR_WIDTH, Score.CHAR_HEIGHT);
		Assets.font.drawText(batcher, "Pottery " + pottery, firstGlymphPos.x,
				firstGlymphPos.y - 2 * Score.LINE_SPACING, Score.CHAR_WIDTH,
				Score.CHAR_HEIGHT);
		Assets.font.drawText(batcher, "Level " + Level.LEVEL, firstGlymphPos.x,
				firstGlymphPos.y - 3 * Score.LINE_SPACING, Score.CHAR_WIDTH,
				Score.CHAR_HEIGHT);
		Assets.font.drawText(batcher, " " + Powerup.thunderstormPowerups,
				tStormIconPosition.x + POWERUP_ICON_WIDTH,
				tStormIconPosition.y, Score.CHAR_WIDTH, Score.CHAR_HEIGHT);
		Assets.font.drawText(batcher, " " + Powerup.timeslowPowerups,
				tSlowIconPosition.x + POWERUP_ICON_WIDTH, tSlowIconPosition.y,
				Score.CHAR_WIDTH, Score.CHAR_HEIGHT);

		/*
		 * Assets.font.drawText(batcher, "Stone " + Level.LEVEL_HAILSTONE +
		 * "|LevelRise " + Level.LEVEL_LEVELRISE , firstGlymphPos.x,
		 * firstGlymphPos.y + 3*Score.LINE_SPACING, Score.CHAR_WIDTH,
		 * Score.CHAR_HEIGHT); Assets.font.drawText(batcher, "DrpsToRise " +
		 * Level.LEVEL_NUMDROPS_FOR_LEVELRISE + "|Survive " +
		 * Level.LEVEL_TIME_TO_SURVIVE , firstGlymphPos.x, firstGlymphPos.y +
		 * 4*Score.LINE_SPACING, Score.CHAR_WIDTH, Score.CHAR_HEIGHT);
		 * Assets.font.drawText(batcher, "RandStone " +
		 * Level.LEVEL_HAILSTONE_RANDNUM + "|RandWind " +
		 * Level.LEVEL_WIND_RANDNUM , firstGlymphPos.x, firstGlymphPos.y +
		 * 5*Score.LINE_SPACING, Score.CHAR_WIDTH, Score.CHAR_HEIGHT);
		 */
	}

}
