package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.*;

public class AnimalCollisionHandler implements CollisionHandler{
    private final Animal animal;
    private final StateManager stateManager;

    private static final double LOG_MOVE_LEFT_SPEED = -2.0;
    private static final double LOG_MOVE_RIGHT_SPEED = 0.75;
    private static final double WATER_DEATH_Y_THRESHOLD = 413.0;

    public AnimalCollisionHandler(Animal animal, StateManager stateManager) {
        this.animal = animal;
        this.stateManager = stateManager;
    }

    public void checkCollisions() {
        if (!animal.getIntersectingObjects(End.class).isEmpty()) {
            handleEndCollision();
            return;
        }

        if (!animal.getIntersectingObjects(Log.class).isEmpty()) {
            handleLogCollision();
            return;
        }

        if (!animal.getIntersectingObjects(Turtle.class).isEmpty()) {
            animal.move(-1, 0);
            return;
        }

        if (!animal.getIntersectingObjects(WetTurtle.class).isEmpty()) {
            handleWetTurtleCollision();
            return;
        }

        if (animal.getY() < WATER_DEATH_Y_THRESHOLD) {
            animal.setWaterDeath(true);
            return;
        }

        if (!animal.getIntersectingObjects(Obstacle.class).isEmpty()) {
            animal.setCarDeath(true);
        }
    }

    private void handleEndCollision() {
        End endObj = animal.getIntersectingObjects(End.class).get(0);
        if (endObj.isActivated()) {
            stateManager.addPoints(-50);
        } else {
            endObj.setEnd();
            stateManager.addPoints(50);
        }
        animal.resetPosition();
    }

    private void handleLogCollision() {
        Log log = animal.getIntersectingObjects(Log.class).get(0);
        animal.move(log.getLeft() ? LOG_MOVE_LEFT_SPEED : LOG_MOVE_RIGHT_SPEED, 0);
    }

    private void handleWetTurtleCollision() {
        WetTurtle wetTurtle = animal.getIntersectingObjects(WetTurtle.class).get(0);
        if (wetTurtle.isSunk()) {
            animal.setWaterDeath(true);
            animal.resetPosition();
        } else {
            animal.move(-1, 0);
        }
    }

}
