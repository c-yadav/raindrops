package com.openE.raindrops;

import com.openE.framework.math.Vector2;

public class OverheadRain {

	private Vector2 position;
	private Vector2 additionAccl;

	private int drops;

	public OverheadRain(Vector2 position, Vector2 additionAccl) {

		this.position = new Vector2(0, 0);
		this.additionAccl = new Vector2(0, 0);

		this.position.set(position);
		this.additionAccl.set(additionAccl);

		// drops = (int) (RainGame.WORLD_HEIGHT / (-10 * this.additionAccl.y));
		// drops = (int) (RainGame.WORLD_HEIGHT / 2 );
		
		drops = Level.LEVEL_DROP;

	}

	public RainDrop[] createDrops() {
		RainDrop[] rainDrops = new RainDrop[drops];
		for (int i = 0; i < drops; i++) {
			position.add(0, RainGame.WORLD_HEIGHT);
			rainDrops[i] = new RainDrop(position);
			rainDrops[i].getaccel().add(additionAccl);
		}
		return rainDrops;
	}

	public void updateDrops(RainDrop[] rainDrops, float deltaTime, Wind wind) {
		drops = rainDrops.length;
		for (int i = 0; i < drops; i++) {
			rainDrops[i].update(deltaTime,wind);
			if (rainDrops[i].getPosition().y < -1)
				rainDrops[i].reposDrop();
		}
	}

}
