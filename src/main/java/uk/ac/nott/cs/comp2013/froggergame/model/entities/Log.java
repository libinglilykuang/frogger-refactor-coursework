package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

public class Log extends Actor {
	private final double speed;

	public Log(String imageLink, int size, int xpos, int ypos, double speed) {
		setImage(new Image(imageLink, size, size, true, true));
		setX(xpos);
		setY(ypos);
		this.speed = speed;
		configureBounds(-300, 600, 700, -180);
	}

	public boolean getLeft() {
		return speed < 0;
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
	}
}
