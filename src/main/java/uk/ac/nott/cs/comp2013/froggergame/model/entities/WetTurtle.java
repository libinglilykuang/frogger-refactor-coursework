package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.resources.ImageLoader;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.AnimationHandler;

public class WetTurtle extends Actor {
	private static final long ANIMATION_INTERVAL = 900_000_000;
	private final int speed;
	private boolean sunk = false;
	private final Image[] animationFrames;

	public WetTurtle(int xpos, int ypos, int speed, int width, int height) {
		this.animationFrames = new Image[]{
				ImageLoader.loadImage("TurtleAnimation1.png", width, height),
				ImageLoader.loadImage("TurtleAnimation2Wet.png", width, height),
				ImageLoader.loadImage("TurtleAnimation3Wet.png", width, height),
				ImageLoader.loadImage("TurtleAnimation4Wet.png", width, height)
		};
		setX(xpos);
		setY(ypos);
		this.speed = speed;
		setImage(animationFrames[0]);
		configureBounds(-75, 600, 600, -200);
	}

	public boolean isSunk() {
		return sunk;
	}

	@Override
	public void act(long now) {
		int frame = AnimationHandler.getCurrentFrameIndex(now, ANIMATION_INTERVAL, animationFrames.length);
		setImage(animationFrames[frame]);
		sunk = (frame == animationFrames.length - 1);
		move(speed, 0);
		resetPositionIfOutOfBounds(speed);
	}

	@Override
	public void act(double elapsedTime) {
	}
}
