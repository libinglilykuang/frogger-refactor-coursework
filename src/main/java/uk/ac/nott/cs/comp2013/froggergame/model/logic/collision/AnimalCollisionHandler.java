package uk.ac.nott.cs.comp2013.froggergame.model.logic.collision;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.*;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.level.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalCollisionHandler implements CollisionHandler {
    private final Animal animal;
    private final StateManager stateManager;

    private static final double WATER_DEATH_Y_THRESHOLD = 413.0;
    private static final int POINTS_PER_END = 50;
    private static final int POINTS_LOSS_REVISIT_END = -50;
    private final Map<Class<?>, CollisionAction<?>> collisionActions = new HashMap<>();

    public AnimalCollisionHandler(Animal animal, StateManager stateManager) {
        this.animal = animal;
        this.stateManager = stateManager;

        registerCollisionActions();
    }

    private void registerCollisionActions() {
        collisionActions.put(Obstacle.class, obj -> animal.setCarDeath(true));
        collisionActions.put(Log.class, log -> animal.setCurrentPlatform((Log) log));
        collisionActions.put(Turtle.class, turtle -> animal.setCurrentPlatform((Turtle) turtle));
        collisionActions.put(WetTurtle.class, this::handleWetTurtleCollision);
        collisionActions.put(End.class, this::handleEndCollision);

        collisionActions.put(Crocodile.class, obj -> handleCrocodileCollision((Crocodile) obj));
    }

    @SuppressWarnings("unchecked")
    public void checkCollisions() {
        for (Map.Entry<Class<?>, CollisionAction<?>> entry : collisionActions.entrySet()) {
            Class<?> key = entry.getKey();
            CollisionAction<?> action = entry.getValue();

            if (handleCollision((Class<Object>) key, (CollisionAction<Object>) action)) {
                return;
            }
        }
        if (animal.isOnPlatform()) {
            animal.clearCurrentPlatform();
        }
    }

    private void handleCrocodileCollision(Crocodile crocodile) {
        System.out.println("Frog collided with a crocodile!");
        animal.setCarDeath(true);
        animal.resetPosition();
    }

    private void handleWetTurtleCollision(Object obj) {
        WetTurtle wetTurtle = (WetTurtle) obj;
        if (wetTurtle.isSunk()) {
            animal.clearCurrentPlatform();
            animal.setWaterDeath(true);
            animal.resetPosition();
        } else {
            animal.setCurrentPlatform(wetTurtle);
        }
    }

    private void handleEndCollision(Object obj) {
        End endObj = (End) obj;
        if (endObj.isActivated()) {
            stateManager.addPoints(POINTS_LOSS_REVISIT_END);
        } else {
            endObj.setEnd();
            stateManager.addPoints(POINTS_PER_END);
        }
        animal.resetPosition();
    }

    private <T> boolean handleCollision(Class<T> type, CollisionAction<T> action) {
        List<T> collisions = animal.getIntersectingObjects(type);
        if (!collisions.isEmpty()) {
            for (T obj : collisions) {
                action.onCollision(obj);
            }
            return true;
        }
        return false;
    }


    @FunctionalInterface
    private interface CollisionAction<T> {
        void onCollision(T obj);
    }
}
