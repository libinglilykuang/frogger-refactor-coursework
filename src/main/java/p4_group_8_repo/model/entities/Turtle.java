package p4_group_8_repo.model.entities;

import javafx.scene.image.Image;
import p4_group_8_repo.model.base.Actor;

public class Turtle extends Actor {

    private static final int SCREEN_RIGHT_BOUND = 600;
    private static final int SCREEN_LEFT_BOUND = -75;
    private static final int RESET_RIGHT_POSITION = -200;
    private static final int RESET_LEFT_POSITION = 600;
    private static final long ANIMATION_INTERVAL = 900_000_000;

    private final Image turtle1;
    private final Image turtle2;
    private final Image turtle3;
    private final int speed;

    public Turtle(int xpos, int ypos, int speed, int width, int height) {
        this.turtle1 = loadImage("/p4_group_8_repo/TurtleAnimation1.png", width, height);
        this.turtle2 = loadImage("/p4_group_8_repo/TurtleAnimation2.png", width, height);
        this.turtle3 = loadImage("/p4_group_8_repo/TurtleAnimation3.png", width, height);

        setX(xpos);
        setY(ypos);
        this.speed = speed;
        setImage(turtle2); // Set default image
    }

    @Override
    public void act(long now) {
        updateAnimation(now);
        move(speed, 0);
        resetPositionIfOutOfBounds();
    }

    private void updateAnimation(long now) {
        int frame = (int) ((now / ANIMATION_INTERVAL) % 3);
        switch (frame) {
            case 0 -> setImage(turtle2);
            case 1 -> setImage(turtle1);
            case 2 -> setImage(turtle3);
        }
    }

    private void resetPositionIfOutOfBounds() {
        if (getX() > SCREEN_RIGHT_BOUND && speed > 0) {
            setX(RESET_RIGHT_POSITION);
        } else if (getX() < SCREEN_LEFT_BOUND && speed < 0) {
            setX(RESET_LEFT_POSITION);
        }
    }

    private Image loadImage(String path, int width, int height) {
        if (getClass().getResource(path) == null) {
            throw new IllegalArgumentException("Resource not found: " + path);
        }
        return new Image(getClass().getResource(path).toExternalForm(), width, height, true, true);
    }
}
