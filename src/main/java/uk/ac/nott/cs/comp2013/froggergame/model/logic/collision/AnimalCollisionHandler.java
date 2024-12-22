package uk.ac.nott.cs.comp2013.froggergame.model.logic.collision;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.*;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;
import java.util.List;

public class AnimalCollisionHandler implements CollisionHandler {

    private final Animal animal;
    private final StateManager stateManager;

    private static final double WATER_DEATH_Y_THRESHOLD = 413.0;
    private static final int POINTS_PER_END = 50;
    private static final int POINTS_LOSS_REVISIT_END = -50;

    public AnimalCollisionHandler(Animal animal, StateManager stateManager) {
        this.animal = animal;
        this.stateManager = stateManager;
    }

    public void checkCollisions() {
        if (checkObstacleCollision()) return;
        if (handleCollision(Log.class, this::handleLogCollision)) return;
        if (handleCollision(Turtle.class, this::handleTurtleCollision)) return;
        if (handleCollision(WetTurtle.class, this::handleWetTurtleCollision)) return;
        if (handleCollision(End.class, this::handleEndCollision)) return;
        if (animal.isOnPlatform()) {
            animal.clearCurrentPlatform();
        }
    }

    private boolean checkObstacleCollision() {
        return handleCollision(Obstacle.class, obj -> animal.setCarDeath(true));
    }

    private boolean checkWaterDeath() {
        if (animal.getY() < WATER_DEATH_Y_THRESHOLD) {
            if (!animal.isOnTurtleOrLog()) {
                animal.clearCurrentPlatform();
                animal.setWaterDeath(true);
                animal.resetPosition();
                return true;
            }
        }
        return false;
    }

    private void handleLogCollision(Log log) {
        animal.setCurrentPlatform(log);
    }

    private void handleTurtleCollision(Turtle turtle) {
        animal.setCurrentPlatform(turtle);
    }

    private void handleWetTurtleCollision(WetTurtle wetTurtle) {
        if (wetTurtle.isSunk()) {
            animal.clearCurrentPlatform();
            animal.setWaterDeath(true);
            animal.resetPosition();
        } else {
            animal.setCurrentPlatform(wetTurtle);
        }
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

    @FunctionalInterface
    private interface CollisionAction<T> {
        void onCollision(T obj);
    }
}
