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

public class AnimalCollisionHandler implements CollisionHandler {
    private final Animal animal;
    private final StateManager stateManager;
    private final Score scoreDisplay;

    private static final int POINTS_PER_END = 50;
    private static final int POINTS_LOSS_REVISIT_END = -50;
    private final Map<Class<?>, CollisionAction<?>> collisionActions = new HashMap<>();

    public AnimalCollisionHandler(Animal animal, StateManager stateManager, Score scoreDisplay) {
        this.animal = animal;
        this.stateManager = stateManager;
        this.scoreDisplay = scoreDisplay;
        registerCollisionActions();
    }

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

    private void handleCrocodileCollision() {
        animal.setCarDeath(true);
        stateManager.addPoints(-50);
        Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));
        animal.resetPosition();
    }

    private void handleSpiderCollision(Spider spider) {
        stateManager.addPoints(20);
        Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));

        Pane parentPane = spider.getParentPane();
        if (parentPane != null) {
            System.out.println("Removing Spider from parentPane...");
            Platform.runLater(() -> parentPane.getChildren().remove(spider));
        } else {
            System.err.println("Spider's parentPane is null!");
        }

        if (spider.getParentLog() != null) {
            spider.getParentLog().clearSpider();
        }
    }

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

    private void handleEasterEggCollision(End end) {
        if (end.hasEasterEgg()) {
            int easterEggBonus = 30;
            stateManager.addPoints(easterEggBonus);
            Platform.runLater(() -> scoreDisplay.updateScore(stateManager.getPoints()));
            end.clearEasterEgg();
        }
    }

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