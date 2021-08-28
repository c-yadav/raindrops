package com.openE.raindrops;

import com.openE.framework.gl.Animation;
import com.openE.framework.gl.TextureRegion;
import com.openE.framework.math.Vector2;

public class Splash {
	
    public static final int DROP_SPLASH = 0;
    public static final int FLOOR_SPLASH = 1;
	
	public static final float FRAME_TIME = 0.2f;
	
	public static final float SPLASH_WIDTH = 1.5f;
	public static final float SPLASH_HEIGHT = 1.5f;
	
	public static final float ANIM_TOTAL_TIME = 0.4f;
	
	private TextureRegion dropSplash1,dropSplash2;
	private TextureRegion floorSplash1,floorSplash2;
	
	private Animation splashAnim;
	
	private float splashTime;
	
	private Vector2 position;
	
	public Splash(Vector2 position, int mode) {
		
		dropSplash1 = Assets.dropSplash1;
		dropSplash2 = Assets.dropSplash2;
		
		floorSplash1 = Assets.floorSplash1;
		floorSplash2 = Assets.floorSplash2;
		
		if ( mode == DROP_SPLASH)
			splashAnim = new Animation(FRAME_TIME, dropSplash1, dropSplash2);
		else
			splashAnim = new Animation(FRAME_TIME, floorSplash1, floorSplash2);
		
		splashTime = 0f;
		
		this.position = position;
	}
	
	public void update(float deltaTime) {
		splashTime += deltaTime;
	}
		
	public TextureRegion getFrame() {
		return splashAnim.getKeyFrame(splashTime, Animation.ANIMATION_NONLOOPING);
	}
	
	public Vector2 getPos() {
		return position;
	}

	public float getSplashTime() {
		return splashTime;
	}
	
}
