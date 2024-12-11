package p4_group_8_repo.model.entities;

import javafx.scene.image.Image;
import p4_group_8_repo.model.base.Actor;
import p4_group_8_repo.model.base.ImageLoader;

public class WetTurtle extends Actor {

	private static final int SCREEN_RIGHT_BOUND = 600;
	private static final int SCREEN_LEFT_BOUND = -75;
	private static final int RESET_RIGHT_POSITION = -200;
	private static final int RESET_LEFT_POSITION = 600;
	private static final long ANIMATION_INTERVAL = 900_000_000;

	private static final String[] IMAGE_PATHS = {
			"TurtleAnimation1.png",
			"TurtleAnimation2Wet.png",
			"TurtleAnimation3Wet.png",
			"TurtleAnimation4Wet.png"
	};

	private final Image turtle1;
	private final Image turtle2;
	private final Image turtle3;
	private final Image turtle4;

	private final int speed;
	private boolean sunk = false;

	public WetTurtle(int xpos, int ypos, int speed, int width, int height) {
		this.turtle1 = ImageLoader.loadImage(IMAGE_PATHS[0], width, height);
		this.turtle2 = ImageLoader.loadImage(IMAGE_PATHS[1], width, height);
		this.turtle3 = ImageLoader.loadImage(IMAGE_PATHS[2], width, height);
		this.turtle4 = ImageLoader.loadImage(IMAGE_PATHS[3], width, height);

		setX(xpos);
		setY(ypos);
		this.speed = speed;
		setImage(turtle2);
	}

	@Override
	public void act(long now) {
		updateAnimation(now);
		move(speed, 0);
		resetPositionIfOutOfBounds();
	}

	private void updateAnimation(long now) {
		int frame = (int) ((now / ANIMATION_INTERVAL) % 4);
		switch (frame) {
			case 0 -> {
				setImage(turtle2);
				sunk = false;
			}
			case 1 -> {
				setImage(turtle1);
				sunk = false;
			}
			case 2 -> {
				setImage(turtle3);
				sunk = false;
			}
			case 3 -> {
				setImage(turtle4);
				sunk = true;
			}
		}
	}

	private void resetPositionIfOutOfBounds() {
		if (getX() > SCREEN_RIGHT_BOUND && speed > 0) {
			setX(RESET_RIGHT_POSITION);
		} else if (getX() < SCREEN_LEFT_BOUND && speed < 0) {
			setX(RESET_LEFT_POSITION);
		}
	}

	public boolean isSunk() {
		return sunk;
	}
}
