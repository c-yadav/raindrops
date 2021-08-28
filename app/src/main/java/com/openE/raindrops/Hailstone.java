package com.openE.raindrops;

import com.openE.framework.gamedev2D.DynamicGameObject;
import com.openE.framework.gl.Animation;
import com.openE.framework.gl.TextureRegion;
import com.openE.framework.math.Rectangle;
import com.openE.framework.math.Vector2;
import com.openE.raindrops.Powerup.PowerupType;

public class Hailstone extends DynamicGameObject {

	public static final float FRAME_TIME = 0.2f;
	public static final float ANIM_TOTAL_TIME = 0.4f;

	public static float HAILSTONE_WIDTH = 1f;
	public static float HAILSTONE_HEIGHT = 1f;
	
	public PowerupType hailstoneState = PowerupType.Normal;
	public static int scalePosition; // to scale position, incase of timeSlow

	private int touchCount;
	private float burstTime;
	private TextureRegion textureRegion = Assets.hailstone;
	public final Rectangle boundsLong;
	
	private Animation hailstoneBurstAnim;

	public Hailstone(Vector2 position) {
		super(position, HAILSTONE_WIDTH, HAILSTONE_HEIGHT);
		accel.add(0, -0.5f);	// gravitational force
		touchCount = 0;
		burstTime = 0;
		textureRegion = Assets.hailstone;
		boundsLong = new Rectangle(position.x - Hailstone.HAILSTONE_WIDTH,
				position.y - Hailstone.HAILSTONE_HEIGHT,
				2 * Hailstone.HAILSTONE_WIDTH, 2 * Hailstone.HAILSTONE_HEIGHT);
		hailstoneBurstAnim = new Animation(FRAME_TIME, Assets.hailstoneBurst1,
				Assets.hailstoneBurst2);
	}

	public void touchUpdate() {
		if (touchCount < 3)
			touchCount += 1;
	}

	public void update(float deltaTime) {
		switch (hailstoneState) {
		case Normal:
			scalePosition = 1;
			break;
		case Thunderstorm:
			scalePosition = 1;
			break;
		case Timeslow:
			scalePosition = 3;
			if ( !Powerup.POWER_TIMESLOW_INUSE) {
				if (Powerup.POWER_THUNDERSTORM_INUSE)
					hailstoneState = PowerupType.Thunderstorm;
				else
					hailstoneState = PowerupType.Normal;
			}
			break;
		default:
			break;
		
		}
		updateCoordinate(deltaTime, scalePosition);
	}

	public void updateCoordinate(float deltaTime, int scalePosition) {
		velocity.add(accel.x * deltaTime, accel.y * deltaTime);
		position.add( (velocity.x*deltaTime)/scalePosition, (velocity.y*deltaTime)/scalePosition );
		Vector2 lowerLeft = new Vector2(
				(position.x - Hailstone.HAILSTONE_WIDTH / 2),
				(position.y - Hailstone.HAILSTONE_WIDTH / 2));
		bounds.lowerLeft.set(lowerLeft);
		boundsLong.lowerLeft.set(position.x - Hailstone.HAILSTONE_WIDTH,
				position.y - Hailstone.HAILSTONE_HEIGHT);

		switch (touchCount) {
		case 1:
			textureRegion = Assets.hailstoneDamage1;
			break;
		case 2:
			textureRegion = Assets.hailstoneDamage2;
			break;
		case 3:
			/*
			if (hailstoneBurstAnim != null) {
				burstTime += deltaTime;
				textureRegion = hailstoneBurstAnim.getKeyFrame(burstTime,
						Animation.ANIMATION_NONLOOPING);
			} else {
				animate();
				textureRegion = hailstoneBurstAnim.getKeyFrame(deltaTime,
						Animation.ANIMATION_NONLOOPING);
			}
			*/
			burstTime += deltaTime;
			textureRegion = hailstoneBurstAnim.getKeyFrame(burstTime,
					Animation.ANIMATION_NONLOOPING);
			break;
		}
	}
	
	public void changeState(PowerupType powerType) {
		hailstoneState = powerType;
	}
	
	public float getBurstTime() {
		return burstTime;
	}

	public void reposStone() {
		velocity.set(0, 0);
		position.set(position.x, Math.abs(position.y) + RainGame.WORLD_HEIGHT);
		Vector2 lowerLeft = new Vector2(
				(position.x - Hailstone.HAILSTONE_WIDTH / 2),
				(position.y - Hailstone.HAILSTONE_WIDTH / 2));
		bounds.lowerLeft.set(lowerLeft);
	}

	public void animate() {
		hailstoneBurstAnim = new Animation(FRAME_TIME, Assets.hailstoneBurst1,
				Assets.hailstoneBurst2);
	}

	public TextureRegion getFrame() {
		return textureRegion;
	}

}
