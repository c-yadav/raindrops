package com.openE.raindrops;

import java.util.List;

import com.openE.framework.gl.Animation;
import com.openE.framework.gl.TextureRegion;
import com.openE.framework.math.Vector2;

public class Wind {
	
	public static final float FRAME_TIME = 0.5f;
	public static final float ANIM_TOTAL_TIME = 1.5f;
	public static final float WIND_WIDTH = 8f;
	public static final float WIND_HEIGHT = 8f;
	public static int windDirection;
	public static List<Animation> winds;
	
	private Vector2 windSpeed ;
	private Animation windAnim;
	private float stateTime;
		
	public Wind() {
		windSpeed = new Vector2();
		windDirection = 2; // no wind
		stateTime = 0;
	}

	public void createWind() { 
		int i = (int) ( Math.random() * (Level.LEVEL_WIND_RANDNUM - 0) + 0 ); // used to set wind speed in X direction
		float velocityX = 0;
		
		if ( i == 0 ) {
			velocityX = -(float) Math.random();
			windDirection = 0;  // wind direction -ve
			resetAnim(windDirection);
		}
		
		if ( i == 1 ) {
			velocityX = (float) Math.random();
			windDirection = 1; // wind direction +ve
			resetAnim(windDirection);
		}
		
		windSpeed.set(velocityX, 0);
	}
	
	public void update(float deltaTime) {
		if ( windAnim != null ) {
			stateTime += deltaTime;
			if ( stateTime >= Wind.ANIM_TOTAL_TIME) resetAnim(2);
		}
	}
	
	private void resetAnim (int windDirection) {
		stateTime = 0;
		Wind.windDirection = windDirection;
		windAnim = new Animation(FRAME_TIME, Assets.wind1, Assets.wind2, Assets.wind3);
	}
	
	public TextureRegion getKeyframe() {
		return windAnim.getKeyFrame(stateTime, Animation.ANIMATION_NONLOOPING);
	}
	
	public int getKey() {
		return windAnim.getKey(stateTime, Animation.ANIMATION_NONLOOPING);
	}
	
	public Vector2 getWind() {
		return windSpeed;
	}
		
	public float getStateTime() {
		return stateTime;
	}
}