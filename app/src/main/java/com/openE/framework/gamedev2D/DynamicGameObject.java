package com.openE.framework.gamedev2D;

import com.openE.framework.math.Vector2;

public class DynamicGameObject extends GameObject {
	
    public final Vector2 velocity;
    public final Vector2 accel;
    
	public DynamicGameObject(Vector2 position, float width, float height) {
		super(position, width, height);
        velocity = new Vector2();
        accel = new Vector2();
		
	}

}
