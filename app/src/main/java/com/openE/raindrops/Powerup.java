package com.openE.raindrops;

public class Powerup {
	
	
	public enum PowerupType {
		Normal, Timeslow, Thunderstorm
	}
	
	public static final int POWER_TYPE_TIMESLOW = 0;
	public static final int POWER_TYPE_THUNDERSTORM = 1;
	public static final int POWER_TIMESLOW_COUNTER = 30;
	public static boolean POWER_TIMESLOW_INUSE = false;
	public static boolean POWER_THUNDERSTORM_INUSE = false;
	public static final float POWER_THUNDERSTORM_DISPLAY_TIME = 0.6f;
	public static final float POWER_TIMESLOW_DISPLAY_TIME = 10f;
	
	public static int timeslowPowerups;
	public static int thunderstormPowerups;
	
	public Powerup() {
		timeslowPowerups = 5;
		thunderstormPowerups = 5;
	}
	
	public void incrementTimeslow() {
		timeslowPowerups++;
	}
	
	public void incrementThunderstorm() {
		thunderstormPowerups++;
	}
	
	public void decrementTimeslow() {
		timeslowPowerups--;
	}
	
	public void decrementThunderstorm() {
		thunderstormPowerups--;
	} 

}
