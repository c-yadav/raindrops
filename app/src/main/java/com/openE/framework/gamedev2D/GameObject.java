package com.openE.framework.gamedev2D;

import com.openE.framework.math.Rectangle;
import com.openE.framework.math.Vector2;

public class GameObject {
    public final Vector2 position;
    public final Rectangle bounds;
    
    public GameObject(Vector2 position, float width, float height) {
        this.position = new Vector2(0,0); 
        this.position.set(position); // center position
        this.bounds = new Rectangle(position.x-width/2, position.y-height/2, width, height);
    }
}
