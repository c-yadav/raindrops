package com.openE.framework;

public interface Game {

    public Audio getAudio();

    public Input getInput();
    
    public AccelerometerHandler getAccelerometerHandler();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();
    
   
}
