package com.openE.raindrops;

public class Level {
	
	public static int LEVEL;
	
	public static int LEVEL_DROP ;
	public static int LEVEL_HAILSTONE;
	public static float LEVEL_LEVELRISE;
	public static int LEVEL_NUMDROPS_FOR_LEVELRISE;
	public static int LEVEL_TIME_TO_SURVIVE;
	
	public static int LEVEL_HAILSTONE_RANDNUM;
	public static int LEVEL_WIND_RANDNUM;
	
	public static final int LEVEL_DROP_INC = 1;
	public static final int LEVEL_HAILSTONE_INC = 1;
	public static final int LEVEL_TIME_INC = 60;
	public static final float LEVEL_LEVELRISE_INC = 0.5f;
	
	public static final int LEVEL_HAILSTONE_RANDNUM_DCR = 50;
	public static final int LEVEL_WIND_RANDNUM_DCR = 50;
	public static final int LEVEL_NUMDROPS_FOR_LEVELRISE_DCR = 1;
	
	private boolean levelInitialized = false;
	
	public Level() {
		
	}
	
	public void updateLevel() {
		if (! levelInitialized) {
			levelInitialized = true;
			LEVEL = 1;
			LEVEL_DROP = 3;
			LEVEL_HAILSTONE = 1;
			LEVEL_LEVELRISE = 0.2f;
			LEVEL_NUMDROPS_FOR_LEVELRISE = 10;
			LEVEL_TIME_TO_SURVIVE = 60;
			
			LEVEL_HAILSTONE_RANDNUM = 300;
			LEVEL_WIND_RANDNUM = 300;
		}
		
		else {
			LEVEL += 1;
			LEVEL_DROP += LEVEL_DROP_INC;
			LEVEL_HAILSTONE += LEVEL_HAILSTONE_INC;
			LEVEL_LEVELRISE += LEVEL_LEVELRISE_INC;
			LEVEL_NUMDROPS_FOR_LEVELRISE -= LEVEL_NUMDROPS_FOR_LEVELRISE_DCR;
			LEVEL_TIME_TO_SURVIVE += LEVEL_TIME_INC;
			
			LEVEL_HAILSTONE_RANDNUM -= LEVEL_HAILSTONE_RANDNUM_DCR;
			if (LEVEL_HAILSTONE_RANDNUM <= 0 ) LEVEL_HAILSTONE_RANDNUM =50;
			
			LEVEL_WIND_RANDNUM -= LEVEL_WIND_RANDNUM_DCR;
			if ( LEVEL_WIND_RANDNUM <= 0 ) LEVEL_WIND_RANDNUM=50;
			
		}
		
	}

}
