package com.openE.raindrops;

import com.openE.framework.gl.Font;
import com.openE.framework.gl.Texture;
import com.openE.framework.gl.TextureRegion;
import com.openE.framework.implementation.GLGame;

public class Assets {

	public static Texture gamesheet;
	public static TextureRegion waterdrop;
	public static TextureRegion dropSplash1;
	public static TextureRegion dropSplash2;
	public static TextureRegion floorSplash1;
	public static TextureRegion floorSplash2;
	public static TextureRegion pottery;
	public static TextureRegion potteryBack;
	public static TextureRegion potteryFront;
	public static TextureRegion potteryCrack1;
	public static TextureRegion potteryCrack2;
	public static TextureRegion potteryCrack3;
	public static TextureRegion potteryPiece1;
	public static TextureRegion potteryPiece2;
	public static TextureRegion potteryPiece3;
	public static TextureRegion potteryPiece4;
	public static TextureRegion potteryPiece5;
	public static TextureRegion potteryPiece6;
	public static TextureRegion hailstone;
	public static TextureRegion hailstoneDamage1;
	public static TextureRegion hailstoneDamage2;
	public static TextureRegion hailstoneBurst1;
	public static TextureRegion hailstoneBurst2;
	public static TextureRegion dropEvaporate1;
	public static TextureRegion dropEvaporate2;
	public static TextureRegion dropTSlow;

	
	public static Texture bg;
	public static TextureRegion bgRegion;
	public static TextureRegion bgbolt1;
	public static TextureRegion bgbolt2;
	public static TextureRegion bgSlow;
	
	public static Texture atmosphere;
	public static TextureRegion waterlevelRegion1;
	public static TextureRegion waterlevelRegion2;
	//public static TextureRegion waterlevelRegion3;
	//public static TextureRegion waterlevelRegion4;
	//public static TextureRegion waterlevelRegion5;
	public static TextureRegion waterEvaporate1;
	public static TextureRegion waterEvaporate2;
	public static TextureRegion waterEvaporate3;
	public static TextureRegion wind1;
	public static TextureRegion wind2;
	public static TextureRegion wind3;
	public static TextureRegion clouds1;
	public static TextureRegion clouds2;
	public static TextureRegion clouds3;
	
	public static Texture openEfont;
	public static Font font;
	
	public static Texture gameScore;
	public static TextureRegion boltIcon;
	public static TextureRegion tSlowIcon;
	
	public static void load(GLGame game) {

		bg = new Texture(((GLGame) game), "gameBg.png");
		bgRegion = new TextureRegion(bg, 377, 502, 375, 500);
		bgbolt1 = new TextureRegion(bg, 0, 0, 375, 500);
		bgbolt2 = new TextureRegion(bg, 0, 502, 375, 500);
		bgSlow = new TextureRegion(bg, 377, 0, 375, 500);
		
		gamesheet = new Texture(((GLGame) game), "gameElements.png");
		potteryBack = new TextureRegion(gamesheet, 722, 266, 130, 131);
		potteryFront = new TextureRegion(gamesheet, 462, 133, 130, 131);
		potteryCrack1 = new TextureRegion(gamesheet, 594, 0, 130, 131);
		potteryCrack2 = new TextureRegion(gamesheet, 590, 266, 130, 131);
		potteryCrack3 = new TextureRegion(gamesheet, 594, 133, 130, 131);
		potteryPiece1 = new TextureRegion(gamesheet, 330, 0, 130, 131);
		potteryPiece2 = new TextureRegion(gamesheet, 462, 0, 130, 131);
		potteryPiece3 = new TextureRegion(gamesheet, 458, 266, 130, 131);
		potteryPiece4 = new TextureRegion(gamesheet, 194, 370, 130, 131);
		potteryPiece5 = new TextureRegion(gamesheet, 326, 370, 130, 131);
		potteryPiece6 = new TextureRegion(gamesheet, 330, 133, 130, 131);
		
		dropEvaporate1 = new TextureRegion(gamesheet, 194, 197, 134, 171);
		dropEvaporate2 = new TextureRegion(gamesheet, 194, 0, 134, 195);
		dropTSlow = new TextureRegion(gamesheet, 0, 0, 192, 450);

		waterdrop = new TextureRegion(gamesheet, 856, 124, 69, 120);
		floorSplash1 = new TextureRegion(gamesheet, 330 ,266, 123, 92);
		floorSplash2 = new TextureRegion(gamesheet, 458, 399, 88, 76);
		dropSplash1 = new TextureRegion(gamesheet, 125, 452, 40, 28);
		dropSplash2 = new TextureRegion(gamesheet, 0, 452, 123, 43);
		
		hailstone =  new TextureRegion(gamesheet, 854, 384, 121, 121);
		hailstoneDamage1 = new TextureRegion(gamesheet, 856, 0, 121, 122);
		hailstoneDamage2 = new TextureRegion(gamesheet, 854, 260, 125, 122);
		hailstoneBurst1 =  new TextureRegion(gamesheet, 726, 130, 128, 128);
		hailstoneBurst2 =  new TextureRegion(gamesheet, 726, 0, 128, 128);
		
		atmosphere = new Texture(((GLGame) game), "gameAtmosphere.png");
		waterlevelRegion1 = new TextureRegion(atmosphere, 499, 395, 260, 390 );
		waterlevelRegion2 = new TextureRegion(atmosphere, 522, 0, 260, 390 );
		//waterlevelRegion3 = new TextureRegion(atmosphere, 499, 395, 260, 390 );
		//waterlevelRegion4 = new TextureRegion(atmosphere, 996, 0, 520, 779 );
		//waterlevelRegion5 = new TextureRegion(atmosphere, 3084, 0, 520, 779 );
		waterEvaporate1 = new TextureRegion(atmosphere, 0, 132, 520, 130);
		waterEvaporate2 = new TextureRegion(atmosphere, 0 , 264, 520, 129);
		waterEvaporate3 = new TextureRegion(atmosphere, 0, 0, 520, 130);
		clouds1 = new TextureRegion(atmosphere, 252, 787, 248, 69 );
		clouds2 = new TextureRegion(atmosphere, 0, 787, 250, 100 );
		clouds3 = new TextureRegion(atmosphere, 0, 889, 250, 69 );
		wind1 = new TextureRegion(atmosphere, 252, 858, 248, 64 );
		wind2 = new TextureRegion(atmosphere, 0, 570, 497, 150 );
		wind3 = new TextureRegion(atmosphere, 0, 395, 497, 173 );
		
		openEfont = new Texture(((GLGame) game), "openEfont.png");
		font = new Font(openEfont, 0, 0, 16, 16, 16);

		gameScore = new Texture(((GLGame) game), "gameScore.png");
		boltIcon = new TextureRegion(gameScore, 258, 0, 119, 241);
		tSlowIcon = new TextureRegion(gameScore, 0, 126, 84, 127);
	} 
	
	public static void reload() {
		gamesheet.reload();
		bg.reload();
		atmosphere.reload();
		openEfont.reload();
		gameScore.reload();
	}

}
