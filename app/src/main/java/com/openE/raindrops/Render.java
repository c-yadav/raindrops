package com.openE.raindrops;

import javax.microedition.khronos.opengles.GL10;

import com.openE.framework.Game;
import com.openE.framework.gl.Camera2D;
import com.openE.framework.gl.SpriteBatcher;
import com.openE.framework.gl.TextureRegion;
import com.openE.framework.implementation.GLGame;
import com.openE.framework.implementation.GLGraphics;
import com.openE.framework.math.Vector2;
import com.openE.raindrops.Powerup.PowerupType;

public class Render {

	private Game game;
	private Camera2D camera;
	private SpriteBatcher batcher;
	private World world;
	
	private GLGraphics glGraphics;
	
	public Render(Game game, Camera2D camera, SpriteBatcher batcher, World world) {
		this.game = game;
		this.camera = camera;
		this.batcher = batcher;		
		this.world  = world;
		glGraphics = ((GLGame) this.game).getGLGraphics();
	}

	public void paint() {
		camera.setViewportAndMatrices();
		renderBackground();
		renderWorldobjects(); 
	}
	
	private void renderBackground() {
		batcher.beginBatch(Assets.bg);
		batcher.drawSprite(RainGame.WORLD_WIDTH / 2, RainGame.WORLD_HEIGHT / 2,
				RainGame.WORLD_WIDTH, RainGame.WORLD_HEIGHT,
				world.bg.getFrame());
		batcher.endBatch();
	}
	
	private void renderWorldobjects() {
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		//renderWaterlevel();
		//renderAtmosphere();
		renderScore();
			
		batcher.beginBatch(Assets.gamesheet);
		renderPotteryBack();
		renderDrops();
		renderHailstones();
		renderDropsplash();
		renderFloorsplash();
		renderPotteryFront();
		batcher.endBatch();
		
		renderAtmosphere();
		
		gl.glDisable(GL10.GL_BLEND);
	}

	private void renderAtmosphere() {
		batcher.beginBatch(Assets.atmosphere);
		renderWaterlevel();
		renderClouds();
		renderWind();
		batcher.endBatch();
	}

	private void renderWind() {
		Vector2 position0 = new Vector2(4,20);
		Vector2 position1 = new Vector2(8,20);
		Vector2 position2 = new Vector2(12,20);
		Vector2 position = new Vector2();
		int key;
			if ( Wind.windDirection == 0 ) {
				key = world.wind.getKey();
				switch(key) {
				case 0:
					position.set(position2);
					break;
				case 1:
					position.set(position1);
					break;
				case 2:
					position.set(position0);
					break;
				}
				batcher.drawSprite(position.x,	position.y, Wind.WIND_WIDTH,
						Wind.WIND_HEIGHT, world.wind.getKeyframe());
			}
			
			if ( Wind.windDirection == 1 ) {
				key = world.wind.getKey();
				switch(key) {
				case 0:
					position.set(position0);
					break;
				case 1:
					position.set(position1);
					break;
				case 2:
					position.set(position2);
					break;
				}
				batcher.drawSprite(position.x,	position.y, Wind.WIND_WIDTH,
						Wind.WIND_HEIGHT, 180, world.wind.getKeyframe());
			}
	}

	private void renderClouds() {
		/*
		for ( int i =0 ; i < world.clouds.length; i++ ) {
			batcher.drawSprite(world.clouds[i].position.x,
					world.clouds[i].position.y, Cloud.CLOUD_WIDTH,
					Cloud.CLOUD_HEIGHT, world.clouds[i].getCloudTextureRegion());
		}
		*/
		batcher.drawSprite(world.clouds[0].position.x,
				world.clouds[0].position.y, Cloud.CLOUD_WIDTH,
				Cloud.CLOUD_HEIGHT, Assets.clouds1);
		batcher.drawSprite(world.clouds[1].position.x,
				world.clouds[1].position.y, Cloud.CLOUD_WIDTH,
				Cloud.CLOUD_HEIGHT, Assets.clouds2);
		batcher.drawSprite(world.clouds[2].position.x,
				world.clouds[2].position.y, Cloud.CLOUD_WIDTH,
				Cloud.CLOUD_HEIGHT, Assets.clouds3);
	}

	private void renderWaterlevel() {
		batcher.drawSprite(world.waterLevel.getcenterPosition().x,
				world.waterLevel.getcenterPosition().y, WaterLevel.WATERLEVEL_WIDTH,
				WaterLevel.WATERLEVEL_HEIGHT, world.waterLevel.getFrame());
		
		if ( world.waterLevel.waterState  == PowerupType.Thunderstorm) {
			batcher.drawSprite(world.waterLevel.getcenterPosition().x, WaterLevel.waterlevel,
					WaterLevel.WATERLEVEL_WIDTH,
					WaterLevel.WATER_EVAPORATE_HEIGHT, world.waterLevel.getEvaporteFrame());
		}
	}
	
	private void renderScore() {
		batcher.beginBatch(Assets.openEfont);
		world.score.drawScore(batcher);
		batcher.endBatch();

		batcher.beginBatch(Assets.gameScore);
		batcher.drawSprite(Score.tStormIconPosition.x,
				Score.tStormIconPosition.y, Score.POWERUP_ICON_WIDTH,
				Score.POWERUP_ICON_HEIGHT, Assets.boltIcon);
		batcher.drawSprite(Score.tSlowIconPosition.x,
				Score.tSlowIconPosition.y, Score.POWERUP_ICON_WIDTH,
				Score.POWERUP_ICON_HEIGHT, Assets.tSlowIcon);
		batcher.endBatch();
	}
	
	private void renderPotteryBack() {
		if ( Pottery.numHailstoneHit < 5 ) 
		batcher.drawSprite(world.pottery.position.x, world.pottery.position.y,
				Pottery.GLASS_WIDTH, Pottery.GLASS_HEIGHT, Assets.potteryBack);
	}
	
	private void renderDrops() {
		for (int i = 0; i < world.rainDrops.length; i++) {
			for (int j = 0; j < world.rainDrops[i].length; j++) {
				RainDrop rainDrop = world.rainDrops[i][j];
				batcher.drawSprite(rainDrop.getPosition().x,
						rainDrop.getPosition().y, RainDrop.DROP_WIDTH,
						RainDrop.DROP_HEIGHT, rainDrop.getAngle(),world.rainDrops[i][j].getFrame());
			}
		}
	}
	
	private void renderHailstones() {
		for (int i = 0; i < world.hailstones.size(); i++) {
			batcher.drawSprite(world.hailstones.get(i).position.x,
					world.hailstones.get(i).position.y, Hailstone.HAILSTONE_WIDTH,
					Hailstone.HAILSTONE_HEIGHT, world.hailstones.get(i).getFrame());
			if (world.hailstones.get(i).getBurstTime() > Hailstone.ANIM_TOTAL_TIME) {
				world.hailstones.remove(i);
			}
		}
	}
	
	private void renderPotteryFront() {
		batcher.drawSprite(world.pottery.position.x, world.pottery.position.y,
				Pottery.GLASS_WIDTH, Pottery.GLASS_HEIGHT, world.pottery.getPotteryFront());
		// check for damage 
		switch (Pottery.numHailstoneHit) {
		case 4:
			batcher.drawSprite(world.pottery.positionPiece.x, world.pottery.positionPiece.y,
					Pottery.GLASS_WIDTH, Pottery.GLASS_HEIGHT, Assets.potteryPiece1);
			batcher.drawSprite(world.pottery.positionPiece.x, world.pottery.positionPiece.y,
					Pottery.GLASS_WIDTH, Pottery.GLASS_HEIGHT, Assets.potteryPiece2);
			break;
		case 5:
			batcher.drawSprite(world.pottery.position.x, world.pottery.position.y,
					Pottery.GLASS_WIDTH, Pottery.GLASS_HEIGHT, Assets.potteryPiece3);
			break;
		}
	}
	
	private void renderDropsplash() {
		if (!world.dropSplash.isEmpty()) {
			for (int i = 0; i < world.dropSplash.size(); i++) {
				TextureRegion splashFrame1 = world.dropSplash.get(i).getFrame();
				batcher.drawSprite(world.dropSplash.get(i).getPos().x, world.dropSplash
						.get(i).getPos().y, Splash.SPLASH_WIDTH,
						Splash.SPLASH_HEIGHT, splashFrame1);
				if (world.dropSplash.get(i).getSplashTime() > Splash.ANIM_TOTAL_TIME) {
					world.dropSplash.remove(i);
				}
			}
		}
	}

	/*
	private void renderFloorsplash() {
		if (!Powerup.POWER_THUNDERSTORM_INUSE) {
			if (!world.floorSplash.isEmpty()) {
				for (int i = 0; i < world.floorSplash.size(); i++) {
					TextureRegion splashFrame2 = world.floorSplash.get(i)
							.getFrame();
					batcher.drawSprite(world.floorSplash.get(i).getPos().x,
							world.floorSplash.get(i).getPos().y,
							Splash.SPLASH_WIDTH, Splash.SPLASH_HEIGHT,
							splashFrame2);
					if (world.floorSplash.get(i).getSplashTime() > Splash.ANIM_TOTAL_TIME) {
						world.floorSplash.remove(i);
					}
				}
			}
		}
	}
	*/
	
	private void renderFloorsplash() {
		if (!world.floorSplash.isEmpty()) {
			for (int i = 0; i < world.floorSplash.size(); i++) {
				/*
				 * @update, don't draw splash when thunderstorm is in use
				 */
				if (!Powerup.POWER_THUNDERSTORM_INUSE) {
					TextureRegion splashFrame2 = world.floorSplash.get(i).getFrame();
					batcher.drawSprite(world.floorSplash.get(i).getPos().x,
							world.floorSplash.get(i).getPos().y,
							Splash.SPLASH_WIDTH, Splash.SPLASH_HEIGHT,
							splashFrame2);
					if (world.floorSplash.get(i).getSplashTime() > Splash.ANIM_TOTAL_TIME) {
						world.floorSplash.remove(i);
					}
				} else {
					world.floorSplash.remove(i);
				}
			}
		}
	}

}
