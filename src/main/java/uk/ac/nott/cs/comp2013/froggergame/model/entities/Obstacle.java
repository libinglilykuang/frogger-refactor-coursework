package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

public class Obstacle extends Actor {
	private final int speed;

	public Obstacle(String imageLink, int xpos, int ypos, int speed, int width, int height) {
		setImage(new Image(imageLink, width, height,true,true));
		setX(xpos);
		setY(ypos);
		this.speed = speed;
		configureBounds(-50, 600, 600, -200);
	}

	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public void act(long now) {
		move(speed, 0);
		resetPositionIfOutOfBounds(speed);
	}

	@Override
	public void act(double elapsedTime) {
		move(speed * elapsedTime, 0);
		resetPositionIfOutOfBounds(speed);
	}
}
