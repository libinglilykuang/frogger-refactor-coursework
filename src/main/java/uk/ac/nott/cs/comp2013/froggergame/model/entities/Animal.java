package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.AnimalCollisionHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.AnimalStateManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.CollisionHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.StateManager;

public class Animal extends Actor {
    private final StateManager stateManager;
    private final CollisionHandler collisionHandler;
    private static final double INITIAL_HIGHEST_Y = 800.0;
    private double highestY = INITIAL_HIGHEST_Y;

    // Constants
    private static final double MOVEMENT_Y = 26.6667;
    private static final double MOVEMENT_X = 21.3333;
    private static final int IMAGE_SIZE = 40;

    private static final double DEFAULT_X = 300;
    private static final double DEFAULT_Y = 733 + MOVEMENT_Y;

    private boolean carDeath = false;
    private boolean waterDeath = false;

    public Animal(String imageLink) {
        setImage(new Image(imageLink, IMAGE_SIZE, IMAGE_SIZE, true, true));
        this.stateManager = createDefaultStateManager();
        this.collisionHandler = createDefaultCollisionHandler();
        resetPosition();
    }

    private StateManager createDefaultStateManager() {
        return new AnimalStateManager(this);
    }

    private CollisionHandler createDefaultCollisionHandler() {
        return new AnimalCollisionHandler(this, stateManager);
    }

    public void resetPosition() {
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        carDeath = false;
        waterDeath = false;
        stateManager.reset();
    }

    public boolean isDead() {
        return carDeath || waterDeath;
    }

    public void setCarDeath(boolean carDeath) {
        this.carDeath = carDeath;
    }

    public void setWaterDeath(boolean waterDeath) {
        this.waterDeath = waterDeath;
    }

    public double getHighestY() {
        return highestY;
    }

    public void setHighestY(double y) {
        highestY = y;
    }

    @Override
    public void act(long now) {
        if (carDeath) {
            stateManager.animateDeath(now, "cardeath", 4);
        } else if (waterDeath) {
            stateManager.animateDeath(now, "waterdeath", 5);
        } else {
            collisionHandler.checkCollisions();
        }

        stateManager.update(now);
    }

    @Override
    public void act(double elapsedTime) {
        if (carDeath) {
            stateManager.animateDeath(System.nanoTime(), "cardeath", 4);
        } else if (waterDeath) {
            stateManager.animateDeath(System.nanoTime(), "waterdeath", 5);
        } else {
            collisionHandler.checkCollisions();
        }

        stateManager.update(elapsedTime);
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public double getMovementX(KeyCode key) {
        return switch (key) {
            case A -> -MOVEMENT_X;
            case D -> MOVEMENT_X;
            default -> 0;
        };
    }

    public double getMovementY(KeyCode key) {
        return switch (key) {
            case W -> -MOVEMENT_Y;
            case S -> MOVEMENT_Y;
            default -> 0;
        };
    }
}
