package com.openE.framework.implementation;

import java.util.List;

import android.view.View.OnTouchListener;

import com.openE.framework.Input.TouchEvent;
import com.openE.framework.math.Line;

public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);
    
    public int getTouchX(int pointer);
    
    public int getTouchY(int pointer);
    
    public List<TouchEvent> getTouchEvents();
    
	public List<Line> getTouchLines();
}
