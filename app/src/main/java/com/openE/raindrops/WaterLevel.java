package com.openE.raindrops;

import android.util.Log;

import com.openE.framework.gl.Animation;
import com.openE.framework.gl.TextureRegion;
import com.openE.framework.math.Vector2;
import com.openE.raindrops.Powerup.PowerupType;

public class WaterLevel {
	
	public static final float INITIAL_LEVEL = 2f;
	public static final float FRAME_TIME = 0.2f;
	public static final float TIME_TO_EVAPORATE = Powerup.POWER_THUNDERSTORM_DISPLAY_TIME;

	public PowerupType waterState = PowerupType.Normal;
	public static final float WATERLEVEL_WIDTH = RainGame.WORLD_WIDTH;
	public static final float WATERLEVEL_HEIGHT = RainGame.WORLD_HEIGHT;
	public static final float WATER_EVAPORATE_HEIGHT = 4f;
		
	public static int drops;
	public static float waterlevel;
	public static float waterLevelDecreaseY;
	
	private float stateTime;
	private Animation waterlevelAnim;
	private Vector2 centerPosition;
	//private TextureRegion waterTextureRegion = Assets.waterlevelRegion1;
	
	private static Animation waterEvaporteAnim;
	//private TextureRegion evaporateTextureRegion = Assets.waterEvaporate1;
	private float evaporateAnimTime;
	/*
	 * width of texture is 720px so, 22m
	 * initial position of water level will be -
	 * WORLD_WIDTH/2, (-WATERLEVEL_HEIGHT/2 ) + INITIAL_LEVEL;
	 * 
	 */

	public WaterLevel() {
		drops = 0;
		stateTime = 0f;
		evaporateAnimTime = 0f;
		centerPosition = new Vector2();
		WaterLevel.waterlevel = WaterLevel.INITIAL_LEVEL;
		WaterLevel.waterLevelDecreaseY = WaterLevel.waterlevel/WaterLevel.TIME_TO_EVAPORATE;
		centerPosition.set( RainGame.WORLD_WIDTH/2, (-WATERLEVEL_HEIGHT/2) + WaterLevel.INITIAL_LEVEL );
		//waterTextureRegion = Assets.waterlevelRegion1;
		waterlevelAnim = new Animation(FRAME_TIME, Assets.waterlevelRegion1, Assets.waterlevelRegion2);
		waterEvaporteAnim = new Animation(FRAME_TIME, Assets.waterEvaporate1, Assets.waterEvaporate2, Assets.waterEvaporate3);
		//animate();
	}
	
	public void update(int drops, float deltaTime) {
		switch (waterState) {
		case Normal:
			WaterLevel.waterlevel = (WaterLevel.drops/Level.LEVEL_NUMDROPS_FOR_LEVELRISE) * Level.LEVEL_LEVELRISE ;
			break;
		case Thunderstorm:
			evaporateAnimTime += deltaTime;
			if ( evaporateAnimTime > WaterLevel.TIME_TO_EVAPORATE) {
				evaporateAnimTime = 0;
				waterState = PowerupType.Normal;
			}
			WaterLevel.drops = 0;
			WaterLevel.waterlevel -= WaterLevel.waterLevelDecreaseY;
			if ( WaterLevel.waterlevel < WaterLevel.INITIAL_LEVEL)
				WaterLevel.waterlevel = WaterLevel.INITIAL_LEVEL;
			Log.d("WaterLevel","" + WaterLevel.waterlevel);
			break;
		case Timeslow:
			WaterLevel.waterlevel = (WaterLevel.drops/Level.LEVEL_NUMDROPS_FOR_LEVELRISE) * Level.LEVEL_LEVELRISE ;
			if ( !Powerup.POWER_TIMESLOW_INUSE) {
				waterState = PowerupType.Normal;
			}
			break;
		default:
			WaterLevel.waterlevel = (WaterLevel.drops/Level.LEVEL_NUMDROPS_FOR_LEVELRISE) * Level.LEVEL_LEVELRISE ;
			break;
		}
		updateCoordinate(drops, deltaTime);
	}
	
	public void updateCoordinate( int drops , float deltaTime ) {
		stateTime += deltaTime;
		WaterLevel.drops = drops;
		//WaterLevel.waterlevel = (WaterLevel.drops/Level.LEVEL_NUMDROPS_FOR_LEVELRISE) * Level.LEVEL_LEVELRISE ;
		WaterLevel.waterLevelDecreaseY = WaterLevel.waterlevel/WaterLevel.TIME_TO_EVAPORATE;
		if ( WaterLevel.waterlevel < WaterLevel.INITIAL_LEVEL)
			WaterLevel.waterlevel = WaterLevel.INITIAL_LEVEL;
		centerPosition.set(RainGame.WORLD_WIDTH/2, (-WATERLEVEL_HEIGHT/2) + WaterLevel.waterlevel);
		//waterTextureRegion = waterlevelAnim.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
		//animate();
	}
	
	public void changeState(PowerupType powerType) {
		waterState = powerType;
		evaporateAnimTime = 0f;
	}

	public Vector2 getcenterPosition() {
		return centerPosition;
	}
	
	
	/*
	public void animate() {
		waterlevelAnim = new Animation(FRAME_TIME, Assets.waterlevelRegion1,
				Assets.waterlevelRegion3, Assets.waterlevelRegion4, Assets.waterlevelRegion5);
	}
	*/
	public TextureRegion getFrame() {
/*
		if ( waterlevelAnim.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING) == null ) {
			return Assets.waterlevelRegion1;
		}
		else
*/
			return waterlevelAnim.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
	}
	
	public TextureRegion getEvaporteFrame() {
		return waterEvaporteAnim.getKeyFrame(evaporateAnimTime,
				Animation.ANIMATION_NONLOOPING);
	}
	
}
