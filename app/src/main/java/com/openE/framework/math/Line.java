package com.openE.framework.math;

public class Line {
    public final Vector2 startPosition = new Vector2();
    public final Vector2 endPosition = new Vector2();

    public Line(Vector2 startPosition, Vector2 endPosition) {
        this.startPosition.set(startPosition);
        this.endPosition.set(endPosition);
    }
    
    public void setStartPosition(Vector2 lowerLeft) {
    	this.startPosition.set(lowerLeft);
    }
    
    public void setEndPosition() {
    	this.endPosition.set(endPosition);
    }
}
