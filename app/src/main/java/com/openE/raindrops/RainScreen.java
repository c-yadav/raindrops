package com.openE.raindrops;

import javax.microedition.khronos.opengles.GL10;

import com.openE.framework.Game;
import com.openE.framework.Screen;
import com.openE.framework.gl.Camera2D;
import com.openE.framework.gl.SpriteBatcher;
import com.openE.framework.implementation.GLGame;
import com.openE.framework.implementation.GLGraphics;

public class RainScreen extends Screen {
	
	public static final int GAME_READY = 0;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_PAUSED = 3;
	public static final int GAME_LEVEL_END = 4;
	public static final int GAME_OVER = 5;
	
	private GLGraphics glGraphics;
	private Camera2D camera;
	private SpriteBatcher batcher;
	private Level gameLevel;
	private World world;
	private Score score;
	private Render render;
	
	public static int gameState;
	
	public RainScreen(Game game) {
		super(game);
		// I've to get this sorted out, when to load images's, bit confusing :)//
		Assets.load((GLGame)game); 
		glGraphics = ((GLGame) game).getGLGraphics();
		
		/*
		 * camera = new Camera2D(glGraphics, 720, 1280);
		 * we'r targeting 720 x 1280 , so we have considered 32 pixels = 1 meter
		 * hence it turns out 22 x 40 meter
		 */
		
		camera = new Camera2D(glGraphics, RainGame.WORLD_WIDTH, RainGame.WORLD_HEIGHT);
		batcher = new SpriteBatcher(glGraphics, 10000);
		gameLevel = new Level();
		gameLevel.updateLevel();
		score = new Score();
		world = new World(game, camera, score);
		render = new Render(game, camera, batcher, world);
		gameState = GAME_READY;
	}

	@Override
	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;

		switch (gameState) {
		case GAME_READY:
			updateReady(deltaTime);
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			//world.update(deltaTime);
			break;
		case GAME_PAUSED:
			break;
		case GAME_LEVEL_END:
			updateLevel(deltaTime);
			break;
		case GAME_OVER:
			updateGameover(deltaTime);
			break;
		}
	}

	private void updateGameover(float deltaTime) {

	}

	private void updateReady(float deltaTime) {
		if ( game.getInput().getTouchEvents().size() > 0 ) {
			gameState = GAME_RUNNING;
		}
	}
	
	private void updateRunning(float deltaTime) {
		world.update(deltaTime);
		if ( (int) Score.time >= Level.LEVEL_TIME_TO_SURVIVE ) {
			gameState = GAME_LEVEL_END;
		}
	}
	
	private void updateLevel(float deltaTime) {
		if ( game.getInput().getTouchEvents().size() > 0 ) {
			gameState = GAME_READY;
			gameLevel.updateLevel();
			score.resetTime();
			world = new World(game, camera, score);
			render = new Render(game, camera, batcher, world);
		}
	}

	@Override
	public void paint(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		render.paint();

		camera.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		
		switch (gameState) {
		case GAME_READY:
			paintReady();
			break;
		case GAME_RUNNING:
			break;
		case GAME_PAUSED:
			break;
		case GAME_LEVEL_END:
			paintLevelchange();
			break;
		case GAME_OVER:
			paintGameover();
			break;
		}
	}

	private void paintGameover() {
		batcher.beginBatch(Assets.openEfont);
		Assets.font.drawText(batcher, "GAME OVER!", 8.5f, 20f, 1f, 1f);
		batcher.endBatch();
	}

	private void paintLevelchange() {
		batcher.beginBatch(Assets.openEfont);
		Assets.font.drawText(batcher, "Level UP!", 8.5f, 20f, 1f, 1f);
		batcher.endBatch();
	}

	private void paintReady() {
		batcher.beginBatch(Assets.openEfont);
		Assets.font.drawText(batcher, "Ready?", 8.5f, 20f, 1f, 1f);
		batcher.endBatch();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		Assets.load((GLGame)game);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}

}
