package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.*;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;

import java.util.List;
import java.util.Optional;

public class AnimalCollisionHandler implements CollisionHandler {
    private final Animal animal;
    private final StateManager stateManager;

    // Constants for configuration
    private static final double LOG_MOVE_LEFT_SPEED = -2.0;
    private static final double LOG_MOVE_RIGHT_SPEED = 0.75;
    private static final double WATER_DEATH_Y_THRESHOLD = 413.0;
    private static final int POINTS_PER_END = 50;
    private static final int POINTS_LOSS_REVISIT_END = -50;

    public AnimalCollisionHandler(Animal animal, StateManager stateManager) {
        this.animal = animal;
        this.stateManager = stateManager;
    }

    public void checkCollisions() {
        if (handleCollision(End.class, this::handleEndCollision)) return;
        if (handleCollision(Log.class, this::handleLogCollision)) return;
        if (handleCollision(Turtle.class, obj -> animal.move(-1, 0))) return;
        if (handleCollision(WetTurtle.class, this::handleWetTurtleCollision)) return;

        if (animal.getY() < WATER_DEATH_Y_THRESHOLD) {
            animal.setWaterDeath(true);
            return;
        }

        if (handleCollision(Obstacle.class, obj -> animal.setCarDeath(true))) return;
    }

    private <T> boolean handleCollision(Class<T> type, CollisionAction<T> action) {
        List<T> collisions = animal.getIntersectingObjects(type).stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();

        if (!collisions.isEmpty()) {
            collisions.forEach(action::onCollision);
            return true;
        }
        return false;
    }


    private void handleEndCollision(End endObj) {
        if (endObj.isActivated()) {
            stateManager.addPoints(POINTS_LOSS_REVISIT_END);
        } else {
            endObj.setEnd();
            stateManager.addPoints(POINTS_PER_END);
        }
        animal.resetPosition();
    }

    private void handleLogCollision(Log log) {
        double speed = log.getLeft() ? LOG_MOVE_LEFT_SPEED : LOG_MOVE_RIGHT_SPEED;
        animal.move(speed, 0);
    }

    private void handleWetTurtleCollision(WetTurtle wetTurtle) {
        if (wetTurtle.isSunk()) {
            animal.setWaterDeath(true);
            animal.resetPosition();
        } else {
            animal.move(-1, 0);
        }
    }

    // Functional interface for collision actions
    @FunctionalInterface
    private interface CollisionAction<T> {
        void onCollision(T obj);
    }
}
