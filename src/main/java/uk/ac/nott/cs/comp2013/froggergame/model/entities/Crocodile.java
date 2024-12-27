package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

/**
 * Represents a crocodile that moves between a target position and its initial position.
 */
public class Crocodile extends Actor {
    private static final double MOVE_DISTANCE = 400; // Distance to move horizontally
    private final double initialX;
    private boolean movingRight = true;
    private static final double SPEED = 1.0;

    /**
     * Constructor to initialize the crocodile's position and behavior.
     *
     * @param x      Initial x position
     * @param y      Initial y position
     * @param speed  Speed of movement
     * @param imagePath Path to the crocodile image
     */
    public Crocodile(double x, double y, double speed, String imagePath) {
        super((int) x, (int) y, speed);
        this.initialX = x;
        setImage(new Image(imagePath, 80, 80, true, true));
    }

    @Override
    public void act(long now) {
        if (movingRight) {
            move(SPEED, 0);
            if (getX() >= initialX + MOVE_DISTANCE) {
                movingRight = false;
            }
        } else {
            move(-SPEED, 0);
            if (getX() <= initialX) {
                movingRight = true;
            }
        }
    }

    @Override
    public void act(double elapsedTime) {
    }
}
