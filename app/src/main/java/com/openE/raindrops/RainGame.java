package com.openE.raindrops;

import com.openE.framework.Screen;
import com.openE.framework.implementation.GLGame;

public class RainGame extends GLGame {

	public static final float WORLD_WIDTH = 22f;
	public static final float WORLD_HEIGHT = 40f;
	public static final int NUMCELLS_X = 22;
	public static final int NUMCELLS_Y = 40;
	
	@Override
	public Screen getStartScreen() {
		return new RainScreen(this);
	}
	
    @Override
    public void onPause() {
        super.onPause();
    }
    
    public void onResume() {
    	super.onResume(); // to have game going after pause
    }

}
