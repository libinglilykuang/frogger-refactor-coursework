package p4_group_8_repo.model.entities;

import javafx.scene.image.Image;
import p4_group_8_repo.model.base.Actor;

public class Obstacle extends Actor {

	private static final int SCREEN_RIGHT_BOUND = 600;
	private static final int SCREEN_LEFT_BOUND = -50;
	private static final int RESET_RIGHT_POSITION = -200;
	private static final int RESET_LEFT_POSITION = 600;

	private final int speed;

	public Obstacle(String imageLink, int xpos, int ypos, int speed, int width, int height) {
		setImage(new Image(imageLink, width, height,true,true));
		setX(xpos);
		setY(ypos);
		this.speed = speed;
	}

	@Override
	public void act(long now) {
		move(speed, 0);
		resetPositionIfOutOfBounds();
	}

	private void resetPositionIfOutOfBounds() {
		if (getX() > SCREEN_RIGHT_BOUND && speed > 0) {
			setX(RESET_RIGHT_POSITION);
		} else if (getX() < SCREEN_LEFT_BOUND && speed < 0) {
			setX(RESET_LEFT_POSITION);
		}
	}
}
