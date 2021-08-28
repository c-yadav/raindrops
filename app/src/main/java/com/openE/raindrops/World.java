package com.openE.raindrops;

import java.util.ArrayList;
import java.util.List;

//import android.util.Log;

import com.openE.framework.Game;
import com.openE.framework.Input.TouchEvent;
import com.openE.framework.gl.Camera2D;
import com.openE.framework.implementation.AndroidInput;
import com.openE.framework.gl.TextureRegion;
import com.openE.framework.math.Line;
import com.openE.framework.math.OverlapTester;
import com.openE.framework.math.Rectangle;
import com.openE.framework.math.Vector2;

public class World {
		private Game game;
		private Camera2D camera;
		//private Level level;

		public final RainDrop[][] rainDrops;
		public final OverheadRain[] overheadRain;
		public List<Hailstone> hailstones;
		public final WaterLevel waterLevel;
		public final Rectangle floor;
		public final Rectangle ceil;
		public final Pottery pottery;
		public final Wind wind;
		public final Cloud[] clouds;
		public final Score score;
		public final Powerup powerup;
		public final Background bg;
		
		public final List<Splash> dropSplash;
		public final List<Splash> floorSplash;
		public static int dropsSplashedFloor;
		
		private static float tSlowTimer = 0f;
		//private static int timeslowCounter = 0;
		private static float tStormTimer = 0f;
		
		public World(Game game, Camera2D camera, Score score) {
			this.game = game;
			this.camera = camera;
			//level = new Level();
			//level.updateLevel();
			
			rainDrops = new RainDrop[(int) ((RainGame.WORLD_WIDTH -2)/ 2)][];
			overheadRain = new OverheadRain[rainDrops.length];
			constructRain();
			hailstones = new ArrayList<Hailstone>();
			waterLevel = new WaterLevel();
			floor = new Rectangle(0f, -1f, RainGame.WORLD_WIDTH ,WaterLevel.waterlevel); 
			ceil = new Rectangle(0f, RainGame.WORLD_HEIGHT -1, RainGame.WORLD_WIDTH, 1);
			pottery = new Pottery(new Vector2(RainGame.WORLD_WIDTH/2, WaterLevel.INITIAL_LEVEL));
			clouds = new Cloud[3];
			constructCloud();
			wind = new Wind();
			//score = new Score();
			this.score = score; 
			powerup = new Powerup();
			bg = new Background();
			
			dropSplash = new ArrayList<Splash>();
			floorSplash = new ArrayList<Splash>();
			dropsSplashedFloor = 0;
		}
		
		private void constructRain() {
			for (int i = 0, positionX = 2; positionX <= RainGame.WORLD_WIDTH -2; i++, positionX += 2) {
				Vector2 position = new Vector2(positionX, 10); // Y-> 10 offset
				overheadRain[i] = new OverheadRain( position, randomAccelY(0.1f, 0.9f, -1) );
				rainDrops[i] = (overheadRain[i].createDrops()).clone();
			}
		}
		
	private void constructCloud() {
		TextureRegion cloud1TextureRegion = Assets.clouds1;
		TextureRegion cloud2TextureRegion = Assets.clouds2;
		TextureRegion cloud3TextureRegion = Assets.clouds3;
		clouds[0] = new Cloud(new Vector2(-Cloud.CLOUD_WIDTH / 2,
				(RainGame.WORLD_HEIGHT - Cloud.CLOUD_HEIGHT)), cloud1TextureRegion);
		clouds[1] = new Cloud(new Vector2(
				-(Cloud.CLOUD_WIDTH / 2 + RainGame.WORLD_WIDTH/2),
				(RainGame.WORLD_HEIGHT - Cloud.CLOUD_HEIGHT)), cloud2TextureRegion);
		clouds[2] = new Cloud(new Vector2(
				-(Cloud.CLOUD_WIDTH / 2 + RainGame.WORLD_WIDTH),
				(RainGame.WORLD_HEIGHT - Cloud.CLOUD_HEIGHT)), cloud3TextureRegion);
	}

		private Vector2 randomAccelY(float lowerlimit, float upperlimit, int mode ) {
			Vector2 accelY = new Vector2();
			float accel = (float) Math.random();
			if ( accel < lowerlimit ) accel = lowerlimit;
			if ( accel > upperlimit ) accel = upperlimit;
			if ( mode == -1 ) accelY = new Vector2(0, -accel);
			if ( mode == 1)  accelY = new Vector2(0, accel);
			return accelY;
		}

	public void update(float deltaTime) {

		if (Powerup.POWER_THUNDERSTORM_INUSE) {
			tStormTimer += deltaTime;
			if (tStormTimer >= Powerup.POWER_THUNDERSTORM_DISPLAY_TIME) {
				Powerup.POWER_THUNDERSTORM_INUSE = false;
				tStormTimer = 0;
			}
		}

		if (Powerup.POWER_TIMESLOW_INUSE) {
			tSlowTimer += deltaTime;
			if (tSlowTimer >= Powerup.POWER_TIMESLOW_DISPLAY_TIME) {
				Powerup.POWER_TIMESLOW_INUSE = false;
				tSlowTimer = 0;
			}
		}
		
		updateWorld(deltaTime);
		/*
		 * if ( Powerup.POWER_TIMESLOW_INUSE) { tSlowTimer += deltaTime; if (
		 * tSlowTimer >= 0.5f && timeslowCounter <=
		 * Powerup.POWER_TIMESLOW_COUNTER) { updateWorld(deltaTime);
		 * tSlowTimer = 0f; timeslowCounter += 1; }
		 * 
		 * if (timeslowCounter == Powerup.POWER_TIMESLOW_COUNTER) {
		 * Powerup.POWER_TIMESLOW_INUSE = false; tSlowTimer = 0f;
		 * timeslowCounter = 0;
		 * 
		 * }
		 * 
		 * }
		 */
	}
		
		public void updateWorld(float deltaTime) {
			score.updateTime(deltaTime);
			wind.createWind();
			wind.update(deltaTime);
			updateClouds(deltaTime);
			updateDrops(deltaTime,wind);
			updateHailstone(deltaTime);
			waterLevel.update(dropsSplashedFloor, deltaTime);
			floor.height = WaterLevel.waterlevel;
			//pottery.update(game, deltaTime);
			potteryUpdate(deltaTime);
			bg.update(deltaTime);

			updateDropsplash(deltaTime);
			updateFloorsplash(deltaTime);
			
			checkCollisionTouchs();
			checkCollisionTouchLines();
			checkCollisionDropsPottery();
			checkCollisionHailstonePottery();
			checkCollisionFloorHailstones();
			//checkCollisionPotterySplash();
			checkCollisionFloorSplash();	
			checkCollistionPotteryCeil();
		}
		
	private void potteryUpdate(float deltaTime) {
		pottery.update();
		pottery.updateFallingPieces(deltaTime);
	}

	private void checkCollisionHailstonePottery() {
		if (hailstones.size() > 0) {
			for (int i = 0; i < hailstones.size(); i++) {
				if (OverlapTester.overlapRectangles(pottery.bounds,
						hailstones.get(i).bounds)) {
					Pottery.numHailstoneHit += 1;
					pottery.updateDamage();
					
					Vector2 pos = new Vector2();
					pos.set(hailstones.get(i).position); // hailstone hit at
															// overlap
					hailstones.remove(i);
				}
			}
		}
	}
			

	private void checkCollisionTouchLines() {
		List<Line> touchLines = ((AndroidInput) game.getInput()).getTouchLines();
		for (int i = 0; i < touchLines.size(); i++) {
			camera.touchToWorld(touchLines.get(i).startPosition);
			camera.touchToWorld(touchLines.get(i).endPosition);
			// Log.d("Line" + i, "lowerLeft|" + touchLines.get(i).lowerLeft.x +
			// "," + touchLines.get(i).lowerLeft.y );
			// Log.d("Line" + i, "upperRight|" + touchLines.get(i).upperRight.x
			// + "," + touchLines.get(i).upperRight.y );
			for (int j = 0; j < rainDrops.length; j++) {
				for (int k = 0; k < rainDrops[j].length; k++) {
					if (OverlapTester.overlapLineRectangle(touchLines.get(i),
							rainDrops[j][k].bounds)) {
						Vector2 splashPosition = new Vector2(0, 0);
						splashPosition.set(rainDrops[j][k].position.x, rainDrops[j][k].position.y);
						dropSplash.add(new Splash(splashPosition,
								Splash.DROP_SPLASH));
						rainDrops[j][k].reposDrop();
						score.updateScore(1);
					}
				}
			}
		}
	}

		private void checkCollisionDropsPottery() {
			for (int j = 0; j < rainDrops.length; j++) {
				for (int k = 0; k < rainDrops[j].length; k++) {
					if (OverlapTester.overlapRectangles( pottery.bounds, rainDrops[j][k].boundsShort )) {
						Pottery.dropsCollected += 1;
						score.updatePotteryScore( (Pottery.dropsCollected/Pottery.NUM_DROPS_PER_POTTERY) );
						/*
						Vector2 pos  = new Vector2();
						pos.set(rainDrops[j][k].position); // splash at overlap
						floorSplash.add(new Splash(pos, Splash.FLOOR_SPLASH));
						dropsSplashedFloor ++;
						rainDrops[j][k].reposDrop();
						*/
					}
				}
			}
		}

		private void updateClouds(float deltaTime) {
			for ( int i =0 ; i < clouds.length ; i++ ) {
				clouds[i].update();
			}
		}

		private void checkCollistionPotteryCeil() {
			if (OverlapTester.overlapRectangles(pottery.bounds, ceil)) {
				RainScreen.gameState = RainScreen.GAME_OVER;
			}
		}

		private void updateDrops(float deltaTime, Wind wind) {
			for (int i = 0; i < overheadRain.length; i++) {
				overheadRain[i].updateDrops( rainDrops[i] , deltaTime , wind);
			}
		}

		private void updateHailstone(float deltaTime) {
			if ( hailstones.size() > 0 ) {
				for ( int i = 0; i<hailstones.size(); i++) {
					hailstones.get(i).update(deltaTime);
				}
			}
			else {
				int i = (int) ( Math.random() * (Level.LEVEL_HAILSTONE_RANDNUM - 0) + 0 ); // used to create hailstone at random time
				if ( i == 0 )
				createHailstone();
			} 
		}
		
	private void createHailstone() {
		for (int i = 0; i < Level.LEVEL_HAILSTONE; i++) {
			// 10 is offset
			Vector2 stonePosition = new Vector2(((int) (Math.random()
					* (10 - 1) + 1)) * 2, (RainGame.WORLD_HEIGHT + 10));
			Hailstone hailstone = new Hailstone(stonePosition);
			hailstone.accel.add(randomAccelY(0.4f, 0.9f, -1));
			hailstones.add(hailstone);
		}
	}	

		private void updateFloorsplash(float deltaTime) {
			for ( int i=0 ; i < floorSplash.size(); i++ ) {
				floorSplash.get(i).update(deltaTime);
			}	
		} 
		
		private void updateDropsplash(float deltaTime) {
			for ( int i=0 ; i < dropSplash.size(); i++ ) {
				dropSplash.get(i).update(deltaTime);
			}
		}
		
	private void checkCollisionTouchs() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		Vector2 touchPosition = new Vector2();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			camera.touchToWorld(touchPosition.set(event.x, event.y));
			if (event.type == TouchEvent.TOUCH_UP) {
				for (int j = 0; j < rainDrops.length; j++) {
					for (int k = 0; k < rainDrops[j].length; k++) {
						if (OverlapTester.pointInRectangle(
								rainDrops[j][k].bounds, touchPosition)) {
							Vector2 splashPosition = new Vector2(0, 0);
							splashPosition.set(touchPosition.x, touchPosition.y);
							dropSplash.add(new Splash(splashPosition,
									Splash.DROP_SPLASH));
							rainDrops[j][k].reposDrop();
							score.updateScore(1);
						}
					}
				}

				for (int j = 0; j < hailstones.size(); j++) {
					if (OverlapTester.pointInRectangle(
							hailstones.get(j).boundsLong, touchPosition)) {
						hailstones.get(j).touchUpdate();
						score.updateScore(1);
					}
				}
				
				if (OverlapTester.pointInRectangle(Score.tStormIconRect, touchPosition)) {
					if (!Powerup.POWER_THUNDERSTORM_INUSE) {
						Powerup.POWER_THUNDERSTORM_INUSE = true;
						powerup.decrementThunderstorm();
						if (Powerup.thunderstormPowerups >= 0) {
							for (int j = 0; j < rainDrops.length; j++) {
								for (int k = 0; k < rainDrops[j].length; k++) {
									rainDrops[j][k].changeState(Powerup.PowerupType.Thunderstorm);
									score.updateScore(1);
								}
							}
						bg.changeState(Powerup.PowerupType.Thunderstorm);
						waterLevel.changeState(Powerup.PowerupType.Thunderstorm);
						World.dropsSplashedFloor = 0;
						}
					}
				}	
				
				if (OverlapTester.pointInRectangle(Score.tSlowIconRect,	touchPosition)) {
					if (!Powerup.POWER_TIMESLOW_INUSE) {
						Powerup.POWER_TIMESLOW_INUSE = true;
						powerup.decrementTimeslow();
						if (Powerup.timeslowPowerups >= 0) {
							for (int j = 0; j < rainDrops.length; j++) {
								for (int k = 0; k < rainDrops[j].length; k++) {
									rainDrops[j][k].changeState(Powerup.PowerupType.Timeslow);
								}
							}
							bg.changeState(Powerup.PowerupType.Timeslow);
							waterLevel.changeState(Powerup.PowerupType.Timeslow);
							
							for (int l = 0; l < hailstones.size(); l++)  {
								hailstones.get(l).changeState(Powerup.PowerupType.Timeslow);
							}
						}
					}
				}
			}
			
			if ( event.type  == TouchEvent.TOUCH_DRAGGED ) {
				if ( OverlapTester.pointInRectangle(pottery.bounds, touchPosition) ) {
					pottery.update(touchPosition.x);
				}
				/*
				 * Added to support raindrop destroy as user drag's on screen
				 */
				for (int j = 0; j < rainDrops.length; j++) {
					for (int k = 0; k < rainDrops[j].length; k++) {
						if (OverlapTester.pointInRectangle(
								rainDrops[j][k].bounds, touchPosition)) {
							Vector2 splashPosition = new Vector2(0, 0);
							splashPosition.set(touchPosition.x, touchPosition.y);
							dropSplash.add(new Splash(splashPosition,
									Splash.DROP_SPLASH));
							rainDrops[j][k].reposDrop();
							score.updateScore(1);
						}
					}
				}
				
				/*
				 * Added to support hailstone destroy as user drag's on screen
				 * 
				 */
				for (int j = 0; j < hailstones.size(); j++) {
					if (OverlapTester.pointInRectangle(
							hailstones.get(j).boundsLong, touchPosition)) {
						hailstones.get(j).touchUpdate();
						score.updateScore(1);
					}
				}
				
			}
		}
	}
	
	private void checkCollisionFloorHailstones() {
		if (hailstones.size() > 0) {
			for (int i = 0; i < hailstones.size(); i++) {
				if (OverlapTester.overlapRectangles(floor,
						hailstones.get(i).bounds)) {
					Vector2 pos = new Vector2();
					pos.set(hailstones.get(i).position); // splash at overlap
					floorSplash.add(new Splash(pos, Splash.FLOOR_SPLASH));
					hailstones.remove(i);
				}
			}
		}
	}
/*
		private void checkCollisionPotterySplash() {
			for (int j = 0; j < rainDrops.length; j++) {
				for (int k = 0; k < rainDrops[j].length; k++) {
					if (OverlapTester.overlapRectangles( pottery.bounds, rainDrops[j][k].bounds )) {
						Vector2 pos  = new Vector2();
						pos.set(rainDrops[j][k].position); // splash at overlap
						floorSplash.add(new Splash(pos, Splash.FLOOR_SPLASH));
						dropsSplashedFloor ++;
						rainDrops[j][k].reposDrop();
					}
				}
			}
		}
*/		
	private void checkCollisionFloorSplash() {
		for (int j = 0; j < rainDrops.length; j++) {
			for (int k = 0; k < rainDrops[j].length; k++) {
				if (OverlapTester.overlapRectangles(floor,
						rainDrops[j][k].bounds)) {
					Vector2 pos = new Vector2();
					pos.set(rainDrops[j][k].position); // splash at overlap
					floorSplash.add(new Splash(pos, Splash.FLOOR_SPLASH));
					dropsSplashedFloor++;
					rainDrops[j][k].reposDrop();
				}
			}
		}
	}

}
