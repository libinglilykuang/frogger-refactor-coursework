package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

public class Crocodile extends Actor {
    private static final double MOVE_DISTANCE = 400; // Distance to move horizontally
    private final double initialX;
    private boolean movingRight = true;
    private static final double SPEED = 1.0;

    public Crocodile(double x, double y, double speed) {
        super((int) x, (int) y, speed);
        this.initialX = x;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
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
