package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.collision.AnimalCollisionHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.collision.CollisionHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.AnimalStateManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;

public class Animal extends Actor {

    private static final double INITIAL_HIGHEST_Y = 800.0;
    private static final double MOVEMENT_Y = 26.6667;
    private static final double MOVEMENT_X = 21.3333;
    private static final int IMAGE_SIZE = 40;
    private static final double DEFAULT_X = 300;
    private static final double DEFAULT_Y = 733 + MOVEMENT_Y;
    private static final double DEFAULT_SPEED = 0.0;
    private static final double WATER_BOUNDARY_Y = 413.0;

    private final StateManager stateManager;
    private final CollisionHandler collisionHandler;
    private double highestY = INITIAL_HIGHEST_Y;
    private boolean carDeath = false;
    private boolean waterDeath = false;

    public Animal(String imageLink) {
        super((int) DEFAULT_X, (int) DEFAULT_Y, DEFAULT_SPEED);
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

    public void setCarDeath(boolean carDeath) {
        this.carDeath = carDeath;
    }

    public void setWaterDeath(boolean waterDeath) {
        this.waterDeath = waterDeath;
    }

    public void setHighestY(double y) {
        highestY = y;
    }

    public double getHighestY() {
        return highestY;
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

    private boolean isInWaterWithoutPlatform() {
        return !isOnLog() && !isOnTurtle() && getY() < WATER_BOUNDARY_Y ;
    }

    private boolean isOnLog() {
        return getOneIntersectingObject(Log.class) != null;
    }

    private boolean isOnTurtle() {
        return getOneIntersectingObject(Turtle.class) != null;
    }

    public boolean isDead() {
        return carDeath || waterDeath;
    }

    private void applyPlatformMovement() {
        if (isOnLog()) {
            moveWithPlatform(getOneIntersectingObject(Log.class).getSpeed());
        } else if (isOnTurtle()) {
            moveWithPlatform(getOneIntersectingObject(Turtle.class).getSpeed());
        }
    }

    private void moveWithPlatform(double speed) {
        setX(getX() + speed);
    }

    private void handleDeathAnimation(long now, String deathType, int frameCount) {
        stateManager.updateDeathAnimation(now, deathType, frameCount);
    }

    private void updateState() {
        collisionHandler.checkCollisions();
        applyPlatformMovement();
        if (isInWaterWithoutPlatform()) {
            waterDeath = true;
        }
    }

    @Override
    public void act(long now) {
        if (isDead()) {
            handleDeathAnimation(now, carDeath ? "cardeath" : "waterdeath", carDeath ? 4 : 5);
        } else {
            updateState();
        }
        stateManager.update(now);
    }

    @Override
    public void act(double elapsedTime) {
        if (isDead()) {
            handleDeathAnimation(System.nanoTime(), carDeath ? "cardeath" : "waterdeath", carDeath ? 4 : 5);
        } else {
            updateState();
        }
        stateManager.update(elapsedTime);
    }
}
