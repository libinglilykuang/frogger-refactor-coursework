package p4_group_8_repo.model.entities;

import javafx.scene.image.Image;
import p4_group_8_repo.model.base.Actor;

public class Log extends Actor {

	private static final int SCREEN_RIGHT_BOUND = 600;
	private static final int SCREEN_LEFT_BOUND = -300;
	private static final int RESET_RIGHT_POSITION = -180;
	private static final int RESET_LEFT_POSITION = 700;

	private final double speed;

	public Log(String imageLink, int size, int xpos, int ypos, double speed) {
		setImage(new Image(imageLink, size, size, true, true));
		setX(xpos);
		setY(ypos);
		this.speed = speed;
	}

	@Override
	public void act(long now) {
		move(speed, 0);
		resetPositionIfOutOfBounds();
	}

	public boolean getLeft() {
		return speed < 0;
	}

	private void resetPositionIfOutOfBounds() {
		if (getX() > SCREEN_RIGHT_BOUND && speed > 0) {
			setX(RESET_RIGHT_POSITION);
		} else if (getX() < SCREEN_LEFT_BOUND && speed < 0) {
			setX(RESET_LEFT_POSITION);
		}
	}

	private Image loadImage(String imageLink, int size) {
		if (getClass().getResource(imageLink) == null) {
			throw new IllegalArgumentException("Resource not found: " + imageLink);
		}
		return new Image(getClass().getResource(imageLink).toExternalForm(), size, size, true, true);
	}
}
