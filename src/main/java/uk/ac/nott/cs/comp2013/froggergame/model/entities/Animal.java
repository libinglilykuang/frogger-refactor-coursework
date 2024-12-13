package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.base.ImageLoader;

public class Animal extends Actor {
	private final Image imgW1, imgA1, imgS1, imgD1, imgW2, imgA2, imgS2, imgD2;
	private int points = 0;
	private int endCount = 0;
	private boolean second = false;
	private boolean noMove = false;
	private boolean carDeath = false;
	private boolean waterDeath = false;
	private boolean scoreChanged = false;

	private static final double MOVEMENT_Y = 26.6667;
	private static final double MOVEMENT_X = 21.3333;
	private static final int IMAGE_SIZE = 40;
	private static final double DEFAULT_X = 300;
	private static final double DEFAULT_Y = 733 + MOVEMENT_Y;
	private static final double BOUNDARY_Y = 734;
	private static final double BOUNDARY_X = 600;
	private static final int DEATH_ANIMATION_FRAME_INTERVAL = 11;
	private static final int POINTS_INCREMENT = 50;
	private static final int POINTS_DECREMENT = 50;
	private static final int GAME_WIN_END_COUNT = 5;
	private static final double LOG_MOVE_LEFT_SPEED = -2.0;
	private static final double LOG_MOVE_RIGHT_SPEED = 0.75;
	private static final double INITIAL_HIGHEST_Y = 800.0;
	private static final double WATER_DEATH_Y_THRESHOLD = 413.0;

	private int deathAnimationFrame = 0;
	private double highestY = 800;

	public Animal(String imageLink) {
		setImage(new Image(imageLink, IMAGE_SIZE, IMAGE_SIZE, true, true));
		resetPosition();

		imgW1 = ImageLoader.loadImage("froggerUp.png", 40, 40);
		imgA1 = ImageLoader.loadImage("froggerLeft.png", 40, 40);
		imgS1 = ImageLoader.loadImage("froggerDown.png", 40, 40);
		imgD1 = ImageLoader.loadImage("froggerRight.png", 40, 40);
		imgW2 = ImageLoader.loadImage("froggerUpJump.png", 40, 40);
		imgA2 = ImageLoader.loadImage("froggerLeftJump.png", 40, 40);
		imgS2 = ImageLoader.loadImage("froggerDownJump.png", 40, 40);
		imgD2 = ImageLoader.loadImage("froggerRightJump.png", 40, 40);

		setOnKeyPressed(this::handleKeyPressed);
		setOnKeyReleased(this::handleKeyReleased);
	}

	public void resetPosition() {
		setX(DEFAULT_X);
		setY(DEFAULT_Y);
	}

	private void handleKeyPressed(KeyEvent event) {
		if (noMove) return;

		KeyCode key = event.getCode();
		Image image = second ? getImageForKey(key, false) : getImageForKey(key, true);

		if (image != null) {
			move(getMovementX(key), getMovementY(key), image);
			second = !second;
		}
	}

	private void handleKeyReleased(KeyEvent event) {
		if (noMove) return;

		KeyCode key = event.getCode();
		if (key == KeyCode.W && getY() < highestY) {
			highestY = getY();
			addPoints(10);
		}

		Image image = getImageForKey(key, false);
		if (image != null) {
			move(getMovementX(key), getMovementY(key), image);
		}
		second = false;
	}

	private double getMovementX(KeyCode key) {
		return switch (key) {
			case A -> -MOVEMENT_X;
			case D -> MOVEMENT_X;
			default -> 0;
		};
	}

	private double getMovementY(KeyCode key) {
		return switch (key) {
			case W -> -MOVEMENT_Y;
			case S -> MOVEMENT_Y;
			default -> 0;
		};
	}

	private Image getImageForKey(KeyCode key, boolean jumping) {
		return switch (key) {
			case W -> jumping ? imgW2 : imgW1;
			case A -> jumping ? imgA2 : imgA1;
			case S -> jumping ? imgS2 : imgS1;
			case D -> jumping ? imgD2 : imgD1;
			default -> null;
		};
	}

	private void move(double dx, double dy, Image img) {
		setImage(img);
		move(dx, dy);
	}

	@Override
	public void act(long now) {
		handleBounds();
		if (carDeath) animateDeath(now, "cardeath", 4);
		if (waterDeath) animateDeath(now, "waterdeath", 5);
		checkCollisions();
	}

	private void handleBounds() {
		if (getY() < 0 || getY() > BOUNDARY_Y) resetPosition();
		if (getX() < 0) move(MOVEMENT_X * 2, 0);
		if (getX() > BOUNDARY_X) move(-MOVEMENT_X * 2, 0);
	}

	private void animateDeath(long now, String type, int frameLimit) {
		noMove = true;

		if (now % DEATH_ANIMATION_FRAME_INTERVAL == 0) {
			deathAnimationFrame++;
		}

		if (deathAnimationFrame >= 1 && deathAnimationFrame < frameLimit) {
			String filePath = String.format("%s%d.png", type, deathAnimationFrame);
			setImage(ImageLoader.loadImage(filePath, IMAGE_SIZE, IMAGE_SIZE));
		}

		if (deathAnimationFrame == frameLimit) {
			resetAfterDeath();
		}
	}

	private void resetAfterDeath() {
		resetPosition();
		carDeath = false;
		waterDeath = false;
		deathAnimationFrame = 0;
		setImage(imgW1);
		noMove = false;
		deductPoints();
	}

	private void checkCollisions() {
		if (!noMove) {
			if (!getIntersectingObjects(End.class).isEmpty()) {
				handleEndCollision();
			}
			if (!getIntersectingObjects(Log.class).isEmpty()) {
				handleLogCollision();
				return;
			}
			if (!getIntersectingObjects(Turtle.class).isEmpty()) {
				move(-1, 0);
				return;
			}
			if (!getIntersectingObjects(WetTurtle.class).isEmpty()) {
				handleWetTurtleCollision();
				return;
			}
			if (getY() < WATER_DEATH_Y_THRESHOLD) {
				waterDeath = true;
			}
		}

		if (!getIntersectingObjects(Obstacle.class).isEmpty()) {
			carDeath = true;
		}
	}

	private void handleEndCollision() {
		End endObj = getIntersectingObjects(End.class).get(0);
		if (endObj.isActivated()) {
			endCount--;
			deductPoints();
		} else {
			endObj.setEnd();
			endCount++;
			addPoints(POINTS_INCREMENT);
		}
		highestY = INITIAL_HIGHEST_Y;
		resetPosition();
	}

	private void handleLogCollision() {
		Log log = getIntersectingObjects(Log.class).get(0);
		move(log.getLeft() ? LOG_MOVE_LEFT_SPEED : LOG_MOVE_RIGHT_SPEED, 0);
	}

	private void handleWetTurtleCollision() {
		WetTurtle turtle = getIntersectingObjects(WetTurtle.class).get(0);
		if (turtle.isSunk()) {
			waterDeath = true;
		} else {
			move(-1, 0);
		}
	}

	private void addPoints(int value) {
		points += value;
		scoreChanged = true;
	}

	private void deductPoints() {
		points = Math.max(points - Animal.POINTS_DECREMENT, 0);
		scoreChanged = true;
	}

	public int getPoints() {
		return points;
	}

	public boolean isGameWon() {
		return endCount == GAME_WIN_END_COUNT;
	}


	public boolean hasScoreChanged() {
		boolean changed = scoreChanged;
		scoreChanged = false;
		return changed;
	}
}
