package com.openE.framework;

public abstract class Screen {
	// An abstract class is a class that is declared abstract—it may or may not
	// include abstract methods. Abstract classes cannot be instantiated, but
	// they can be subclassed
	// Unlike interfaces, abstract classes can contain fields that are not
	// static and final, and they can contain implemented methods
	protected final Game game;

	public Screen(Game game) {
		this.game = game;
	}

	public abstract void update(float deltaTime);

	public abstract void paint(float deltaTime);

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();

	public abstract void backButton();
}
