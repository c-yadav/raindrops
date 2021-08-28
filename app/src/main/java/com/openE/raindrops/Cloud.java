package com.openE.raindrops;

import com.openE.framework.gamedev2D.DynamicGameObject;
import com.openE.framework.gl.TextureRegion;
import com.openE.framework.math.Vector2;

public class Cloud extends DynamicGameObject {
	public static final float CLOUD_WIDTH  = 6f;
	public static final float CLOUD_HEIGHT = 2f;
	public static final float CLOUD_SPEEDX = 0.03f;
	
	private final TextureRegion cloudTextureRegion;
	private Vector2 basePosition = new Vector2();
	
	public Cloud(Vector2 position, TextureRegion cloudTextureRegion) {
		super(position, CLOUD_WIDTH, CLOUD_HEIGHT);
		this.cloudTextureRegion = cloudTextureRegion;
		basePosition.set(position);
	}
	
	public void update() {
		position.add(CLOUD_SPEEDX, 0);
		if ( bounds.lowerLeft.x == RainGame.WORLD_WIDTH) {
			position.set(basePosition);
		}
		
	}

	public TextureRegion getCloudTextureRegion() {
		return cloudTextureRegion;
	}

}
