package uk.ac.nott.cs.comp2013.froggergame.model.logic.collision;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.*;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

/**
 * Handles collision logic for the Animal entity with various game objects.
 * Registers and processes specific actions for each type of collision.
 */
public class AnimalCollisionHandler implements CollisionHandler {
    private final Animal animal;
    private final StateManager stateManager;
    private final Score scoreDisplay;

    private static final int POINTS_PER_END = 50;
    private static final int POINTS_LOSS_REVISIT_END = -50;
    private final Map<Class<?>, CollisionAction<?>> collisionActions = new HashMap<>();

    /**
     * Initializes the collision handler for the specified animal with state management and score display.
     *
     * @param animal       The Animal entity to handle collisions for.
     * @param stateManager Manages the state of the animal.
     * @param scoreDisplay Updates the displayed score.
     */
    public AnimalCollisionHandler(Animal animal, StateManager stateManager, Score scoreDisplay) {
        this.animal = animal;
        this.stateManager = stateManager;
        this.scoreDisplay = scoreDisplay;
        registerCollisionActions();
    }

    /**
     * Registers specific collision actions for different object types.
     */
    private void registerCollisionActions() {
        collisionActions.put(Obstacle.class, obj -> {
            animal.setCarDeath(true);
            stateManager.addPoints(-50);
            Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));
        });
        collisionActions.put(Log.class, log -> animal.setCurrentPlatform((Log) log));
        collisionActions.put(Turtle.class, turtle -> animal.setCurrentPlatform((Turtle) turtle));
        collisionActions.put(WetTurtle.class, this::handleWetTurtleCollision);
        collisionActions.put(End.class, this::handleEndCollision);
        collisionActions.put(Crocodile.class, obj -> handleCrocodileCollision());
        collisionActions.put(Spider.class, obj -> handleSpiderCollision((Spider) obj));
    }

    /**
     * Checks for collisions and invokes the appropriate actions for detected collisions.
     */
    @SuppressWarnings("unchecked")
    public void checkCollisions() {
        for (Map.Entry<Class<?>, CollisionAction<?>> entry : collisionActions.entrySet()) {
            Class<?> key = entry.getKey();
            CollisionAction<?> action = entry.getValue();
            if (handleCollision((Class<Object>) key, (CollisionAction<Object>) action)) {
                return; // Stop further checks after the first collision
            }
        }
        if (animal.isOnPlatform()) {
            animal.clearCurrentPlatform();
        }
    }

    /**
     * Handles a collision with a crocodile by marking the animal as dead and resetting its position.
     */
    private void handleCrocodileCollision() {
        animal.setCarDeath(true);
        stateManager.addPoints(-50);
        Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));
        animal.resetPosition();
    }

    /**
     * Handles a collision with a spider by awarding points and removing the spider from the game.
     *
     * @param spider The spider entity collided with.
     */
    private void handleSpiderCollision(Spider spider) {
        stateManager.addPoints(20);
        Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));

        Pane parentPane = spider.getParentPane();
        if (parentPane != null) {
            Platform.runLater(() -> parentPane.getChildren().remove(spider));
        }
        if (spider.getParentLog() != null) {
            spider.getParentLog().clearSpider();
        }
    }

    /**
     * Handles a collision with a wet turtle, checking if it is sunk and updating the animal's state.
     *
     * @param obj The WetTurtle entity collided with.
     */
    private void handleWetTurtleCollision(Object obj) {
        WetTurtle wetTurtle = (WetTurtle) obj;
        if (wetTurtle.isSunk()) {
            animal.clearCurrentPlatform();
            animal.setWaterDeath(true);
            stateManager.addPoints(-50);
            Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));
            animal.resetPosition();
        } else {
            animal.setCurrentPlatform(wetTurtle);
        }
    }

    /**
     * Handles a collision with an endpoint, awarding points and activating the endpoint.
     *
     * @param obj The End entity collided with.
     */
    private void handleEndCollision(Object obj) {
        End endObj = (End) obj;
        handleEasterEggCollision(endObj);
        if (endObj.isActivated()) {
            stateManager.addPoints(POINTS_LOSS_REVISIT_END);
            Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));
        } else {
            endObj.setEnd();
            stateManager.addPoints(POINTS_PER_END);
            Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));
        }
        animal.resetPosition();
    }

    /**
     * Handles collisions with Easter eggs at an endpoint, awarding bonus points.
     *
     * @param end The End entity containing the Easter egg.
     */
    private void handleEasterEggCollision(End end) {
        if (end.hasEasterEgg()) {
            int easterEggBonus = 30;
            stateManager.addPoints(easterEggBonus);
            Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));
            end.clearEasterEgg();
        }
    }

    /**
     * Handles collisions for a specific type of object and executes the corresponding action.
     *
     * @param type   The class type of the object to check for collisions.
     * @param action The action to perform upon collision.
     * @param <T>    The type of the object.
     * @return True if a collision was handled, false otherwise.
     */
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

    /**
     * Functional interface for defining collision actions.
     *
     * @param <T> The type of the object involved in the collision.
     */
    @FunctionalInterface
    private interface CollisionAction<T> {
        void onCollision(T obj);
    }
}
