package com.openE.raindrops;

import java.math.BigDecimal;

import android.util.Log;

import com.openE.framework.Game;
import com.openE.framework.gamedev2D.DynamicGameObject;
import com.openE.framework.gl.TextureRegion;
import com.openE.framework.implementation.GLGame;
import com.openE.framework.math.Vector2;


public class Pottery extends DynamicGameObject {

	public static final float GLASS_WIDTH = 4f;
	public static final float GLASS_HEIGHT = 4f;
	public static final int NUM_DROPS_PER_POTTERY = 20;
	public static final int NUM_HAILSTONE_HIT_TO_DESTROY = 5;
	public static final float FALLING_PIECE_VELOCITY_Y = 1.0f;
	
	public static int numPottery = 1;
	public static int dropsCollected;
	public static int numHailstoneHit;
	
	private TextureRegion potteryFront = Assets.potteryFront;
	public final Vector2 positionPiece = new Vector2();
	
	public Pottery(Vector2 position) {
		super(position, GLASS_WIDTH, GLASS_HEIGHT);
		positionPiece.set(position);
		dropsCollected = 0;
		numHailstoneHit = 0;
		potteryFront = Assets.potteryFront;
	}

	public  synchronized void update(Game game,float deltaTime) {

		float accelX = ((GLGame)game).getAccelerometerHandler().getaccelX();
		//position.add( -accelX, WaterLevel.waterlevel );
		position.set(RainGame.WORLD_WIDTH/2 - accelX, WaterLevel.waterlevel);
		Vector2 lowerLeft = new Vector2((position.x - Pottery.GLASS_WIDTH / 2),
										(position.y - Pottery.GLASS_HEIGHT / 2));
		bounds.lowerLeft.set(lowerLeft);
	}
	
	public void update() {
		position.set(position.x, WaterLevel.waterlevel);
		Vector2 lowerLeft = new Vector2((position.x - Pottery.GLASS_WIDTH / 2),
										(position.y - Pottery.GLASS_HEIGHT / 2));
		bounds.lowerLeft.set(lowerLeft);
	}
	
	public synchronized void update(Vector2 position) {
		this.position.set(position);
		Vector2 lowerLeft = new Vector2((position.x - Pottery.GLASS_WIDTH / 2),
				(position.y - Pottery.GLASS_HEIGHT / 2));
		bounds.lowerLeft.set(lowerLeft);
	}
	
	public synchronized void update(float x) {
		this.position.set(x, this.position.y);
		Vector2 lowerLeft = new Vector2((position.x - Pottery.GLASS_WIDTH / 2),
				(position.y - Pottery.GLASS_HEIGHT / 2));
		bounds.lowerLeft.set(lowerLeft);
	}
	
	public void updateDamage() {
		switch (numHailstoneHit) {
		case 1:
			potteryFront = Assets.potteryCrack1;
			break;
		case 2:
			potteryFront = Assets.potteryCrack2;
			break;
		case 3:
			potteryFront = Assets.potteryCrack3;
			break;
		case 4:
			potteryFront = Assets.potteryPiece6;
			break;
		case 5:
			potteryFront = Assets.potteryPiece5;
			numPottery--;
			//Log.d("Num Pottery", ":" + numPottery);
			if ( numPottery <= 0 ) RainScreen.gameState = RainScreen.GAME_OVER;
			break;
		}
		
	}
	
	public void updateFallingPieces(float deltaTime) {
		switch (numHailstoneHit) {
		case 1:
		case 2:
		case 3:
			positionPiece.set(position);
			break;
		case 4:
			positionPiece.add( 0, -Pottery.FALLING_PIECE_VELOCITY_Y * deltaTime);
		case 5:
			position.add( 0, -Pottery.FALLING_PIECE_VELOCITY_Y * deltaTime);
			break;
		}
	}
	
	public TextureRegion getPotteryFront() {
		if (potteryFront != null) {
			return potteryFront;
		} else {
			return Assets.potteryFront;
		}
	}

	public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
