package com.openE.tests;

import javax.microedition.khronos.opengles.GL10;

import com.openE.framework.gl.Animation;
import com.openE.framework.Game;
import com.openE.framework.Screen;
import com.openE.framework.gamedev2D.DynamicGameObject;
import com.openE.framework.gl.Camera2D;
import com.openE.framework.gl.SpriteBatcher;
import com.openE.framework.gl.Texture;
import com.openE.framework.gl.TextureRegion;
import com.openE.framework.implementation.GLGame;
import com.openE.framework.implementation.GLGraphics;
import com.openE.framework.math.Vector2;

public class AnimationTest extends GLGame {
	
    public Screen getStartScreen() {    
        return new AnimationScreen(this);
    }
    
	static final float WORLD_WIDTH = 4.8f;
	static final float WORLD_HEIGHT = 3.2f;
	    
	static class Caveman extends DynamicGameObject {
	    public float walkingTime = 0;
	    
	    public Caveman(Vector2 position, float width, float height) {
	        super(position, width, height);
	        this.position.set((float)Math.random() * WORLD_WIDTH,
	                          (float)Math.random() * WORLD_HEIGHT);
	        this.velocity.set(Math.random() > 0.5f?-0.5f:0.5f, 0);
	        this.walkingTime = (float)Math.random() * 10;
	    }        
	    
	    public void update(float deltaTime) {        
	        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
	        if(position.x < 0) position.x = WORLD_WIDTH;
	        if(position.x > WORLD_WIDTH) position.x = 0;
	        walkingTime += deltaTime;
	    }
	}
    
	class AnimationScreen extends Screen {
	    static final int NUM_CAVEMEN = 10;
	    GLGraphics glGraphics;
	    Caveman[] cavemen;
	    SpriteBatcher batcher;
	    Camera2D camera;
	    Texture texture;
	    Animation walkAnim;
	
	    public AnimationScreen(Game game) {
	        super(game);
	        glGraphics = ((GLGame)game).getGLGraphics();
	        cavemen = new Caveman[NUM_CAVEMEN];
	        for(int i = 0; i < NUM_CAVEMEN; i++) {
	        	Vector2 pos = new Vector2((float)Math.random(), (float)Math.random());
	            cavemen[i] = new Caveman(pos, 1, 1);
	        }
	        batcher = new SpriteBatcher(glGraphics, NUM_CAVEMEN);
	        camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
	    }
	
	    @Override
	    public void resume() {
	        texture = new Texture(((GLGame)game), "walkanim.png");
	        walkAnim = new Animation( 0.2f,
	                                  new TextureRegion(texture, 0, 0, 64, 64),
	                                  new TextureRegion(texture, 64, 0, 64, 64),
	                                  new TextureRegion(texture, 128, 0, 64, 64),
	                                  new TextureRegion(texture, 192, 0, 64, 64));
	    }
	    
	    @Override
	    public void update(float deltaTime) {
	        int len = cavemen.length;
	        for(int i = 0; i < len; i++) {
	            cavemen[i].update(deltaTime);
	        }
	    }
	
	    @Override
	    public void paint(float deltaTime) {
	        GL10 gl = glGraphics.getGL();
	        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        camera.setViewportAndMatrices();
	        
	        gl.glEnable(GL10.GL_BLEND);
	        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	        gl.glEnable(GL10.GL_TEXTURE_2D);
	        
	        batcher.beginBatch(texture);
	        int len = cavemen.length;
	        for(int i = 0; i < len; i++) {
	            Caveman caveman = cavemen[i];
	            TextureRegion keyFrame = walkAnim.getKeyFrame(caveman.walkingTime, Animation.ANIMATION_LOOPING);
	            batcher.drawSprite(caveman.position.x, caveman.position.y, caveman.velocity.x < 0?1:-1, 1, keyFrame);
	        }
	        batcher.endBatch();
	    }
	
	    @Override
	    public void pause() {
	    }       
	
	    @Override
	    public void dispose() {
	    }

		@Override
		public void backButton() {
			// TODO Auto-generated method stub
			
		}        
	}
}
