package com.openE.raindrops;

import com.openE.framework.gl.Animation;
import com.openE.framework.gl.TextureRegion;
import com.openE.raindrops.Powerup.PowerupType;

public class Background {
	
	public PowerupType bgState = PowerupType.Normal;
	public static final float FRAME_TIME = 0.3f;
	public static final float ANIM_TOTAL_TIME = Powerup.POWER_THUNDERSTORM_DISPLAY_TIME;
	private Animation thunderstormAnim;
	private float animTime;
	
	public Background() {
		animTime = 0f;
		thunderstormAnim = new Animation(RainDrop.FRAME_TIME, Assets.bgbolt1, Assets.bgbolt2);
	}
	
	public void update(float deltaTime) {
		switch (bgState) {
		case Normal:
			break;
		case Thunderstorm:
			animTime += deltaTime;
			/*
			if ( animTime > RainDrop.ANIM_TOTAL_TIME) {
				animTime = 0;
				bgState = PowerupType.Normal;
			}
			*/
			if ( animTime > RainDrop.ANIM_TOTAL_TIME) {
				animTime = 0;
				if (Powerup.POWER_TIMESLOW_INUSE)
					bgState = PowerupType.Timeslow;
				else
					bgState = PowerupType.Normal;
			}
			break;
		case Timeslow:
			/*
			if ( !Powerup.POWER_TIMESLOW_INUSE) {
				bgState = PowerupType.Normal;
			}
			*/
			if ( !Powerup.POWER_TIMESLOW_INUSE) {
				if (Powerup.POWER_THUNDERSTORM_INUSE)
					bgState = PowerupType.Thunderstorm;
				else
					bgState = PowerupType.Normal;
			}
			break;
		default:
			break;
		}
	}
	
	public void changeState(PowerupType powerType) {
		bgState = powerType;
		animTime = 0f;
	}
	
	public TextureRegion getFrame() {
		switch (bgState) {
		case Normal:
			return Assets.bgRegion;
		case Thunderstorm:
			return thunderstormAnim.getKeyFrame(animTime, Animation.ANIMATION_NONLOOPING);
		case Timeslow:
			return Assets.bgSlow;
		default:
			return Assets.bgRegion;
		}
	}
}
