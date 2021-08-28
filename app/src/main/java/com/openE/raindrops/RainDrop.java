package com.openE.raindrops;

import com.openE.framework.gamedev2D.DynamicGameObject;
import com.openE.framework.gl.Animation;
import com.openE.framework.gl.TextureRegion;
import com.openE.framework.math.OverlapTester;
import com.openE.framework.math.Rectangle;
import com.openE.framework.math.Vector2;
import com.openE.raindrops.Powerup.PowerupType;

public class RainDrop extends DynamicGameObject {
	
	public static final float DROP_WIDTH = 0.5f;
	public static final float DROP_HEIGHT = 1f;
	public static final float ANGLE_OFFSET = 270f;
	public final Rectangle boundsShort;
	public static int scalePosition; // to scale position, incase of timeSlow
	private float angle;
	private float X;	//@update, to save original x co-ordinate
	
	public static Rectangle screenRect = new Rectangle(0, 0, 22, 40);
	public boolean visible;
	public PowerupType raindropState = PowerupType.Normal;
	public static final float FRAME_TIME = 0.3f;
	public static final float ANIM_TOTAL_TIME = Powerup.POWER_THUNDERSTORM_DISPLAY_TIME;
	//private TextureRegion dropTextureRegion;
	private Animation thunderstormAnim;
	private float animTime;
	
	
	public RainDrop(Vector2 position) {
		super(position,DROP_WIDTH,DROP_HEIGHT);
		scalePosition = 1;
		accel.add(0,-0.5f); 						 	// gravitational force
		bounds.width = 2 * RainDrop.DROP_WIDTH;
		bounds.height = 2 * RainDrop.DROP_HEIGHT;
		boundsShort = new Rectangle(position.x - RainDrop.DROP_WIDTH/2,
				position.y - RainDrop.DROP_HEIGHT/2,
				RainDrop.DROP_WIDTH, RainDrop.DROP_HEIGHT);
		X = position.x;
		
		//dropTextureRegion = Assets.waterdrop;
		thunderstormAnim = new Animation(RainDrop.FRAME_TIME, Assets.dropEvaporate1, Assets.dropEvaporate2);
		animTime = 0;
		visible = true;
	}

	public void update(float deltaTime, Wind wind) {
		switch (raindropState) {
		case Normal:
			scalePosition = 1;
			//dropTextureRegion = Assets.waterdrop;
			break;
		case Thunderstorm:
			scalePosition = 1;
			animTime += deltaTime;
			//dropTextureRegion = thunderstormAnim.getKeyFrame(animTime, Animation.ANIMATION_NONLOOPING);
			if ( animTime > RainDrop.ANIM_TOTAL_TIME) {
				animTime = 0;
				if (Powerup.POWER_TIMESLOW_INUSE)
					raindropState = PowerupType.Timeslow;
				else
					raindropState = PowerupType.Normal;
				reposDrop();
			}
			break;
		case Timeslow:
			scalePosition = 3;
			if ( !Powerup.POWER_TIMESLOW_INUSE) {
				if (Powerup.POWER_THUNDERSTORM_INUSE)
					raindropState = PowerupType.Thunderstorm;
				else
					raindropState = PowerupType.Normal;
			}
			break;
		default:
			break;
		
		}
		updateCoordinate(deltaTime, wind, scalePosition);
		updateVisibility(bounds, screenRect);
	}
	
	public void changeState(PowerupType powerType) {
		raindropState = powerType;
		animTime = 0;
	}
	
	private void updateCoordinate(float deltaTime, Wind wind, int scalePosition) {
		float velocityX = wind.getWind().x;				// wind velocity
		float velocityY = accel.y * deltaTime;			// downward velocity
		
		float displaceX = velocityX * deltaTime;     	 // displacement due to wind
		float displaceY = velocityY * deltaTime;		 // displacement due to downward acceleration
		
		@SuppressWarnings("unused")
		Vector2 displacement  = new Vector2(displaceX, displaceY);
		//angle = displacement.angle() - ANGLE_OFFSET;
		
		velocity.add(velocityX, velocityY);
		angle = velocity.angle() - ANGLE_OFFSET;
		position.add( (velocity.x*deltaTime)/scalePosition, (velocity.y*deltaTime)/scalePosition);
		
		Vector2 lowerLeft = new Vector2( (position.x - RainDrop.DROP_WIDTH), (position.y - RainDrop.DROP_HEIGHT) );
		bounds.lowerLeft.set(lowerLeft);
		boundsShort.lowerLeft.set( (position.x - RainDrop.DROP_WIDTH/2), (position.y - RainDrop.DROP_HEIGHT/2) );		
	}
	
	private void updateVisibility(Rectangle bounds, Rectangle screenRect) {
		if ( OverlapTester.overlapRectangles(bounds,screenRect) ) {
			visible = true;
		}
		else
			visible = false;
		
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void reposDrop() {
		velocity.set(0,0);
		position.set(X, Math.abs(position.y) + RainGame.WORLD_HEIGHT);
		Vector2 lowerLeft = new Vector2( (X - RainDrop.DROP_WIDTH/2), (position.y - RainDrop.DROP_HEIGHT/2));
		bounds.lowerLeft.set(lowerLeft);
	}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Vector2 getaccel() {
		return accel;
	}
	
	public TextureRegion getFrame() {
		switch (raindropState) {
		case Normal:
			return Assets.waterdrop;
		case Thunderstorm:
			return thunderstormAnim.getKeyFrame(animTime,
					Animation.ANIMATION_NONLOOPING);
		case Timeslow:
			return Assets.dropTSlow;
		default:
			return Assets.waterdrop;

		}
	}
}
