package uk.ac.nott.cs.comp2013.froggergame.model.base;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import uk.ac.nott.cs.comp2013.froggergame.controller.KeyEventHandlerProvider;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;

/**
 * Base class for all actors in the game.
 * Provides common functionalities like movement, collision detection, and input management.
 */
public abstract class Actor extends ImageView implements KeyEventHandlerProvider {
    private EventHandler<? super KeyEvent> keyPressedHandler;
    private EventHandler<? super KeyEvent> keyReleasedHandler;
    private int screenRightBound;
    private int screenLeftBound;
    private int resetRightPosition;
    private int resetLeftPosition;

    /**
     * Moves the actor by the specified amounts in the X and Y directions.
     *
     * @param dx The amount to move in the X direction.
     * @param dy The amount to move in the Y direction.
     */
    public void move(double dx, double dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public void configureBounds(int screenLeftBound, int screenRightBound, int resetLeftPosition, int resetRightPosition) {
        this.screenLeftBound = screenLeftBound;
        this.screenRightBound = screenRightBound;
        this.resetLeftPosition = resetLeftPosition;
        this.resetRightPosition = resetRightPosition;
    }

    public void resetPositionIfOutOfBounds(double speed) {
        if (getX() > screenRightBound && speed > 0) {
            setX(resetRightPosition);
        } else if (getX() < screenLeftBound && speed < 0) {
            setX(resetLeftPosition);
        }
    }

    public World getWorld() {
        return (World) getParent();
    }

    public double getSpeed() {
        return 0;
    }

    @Override
    public EventHandler<? super KeyEvent> getKeyPressedHandler() {
        return keyPressedHandler;
    }

    @Override
    public EventHandler<? super KeyEvent> getKeyReleasedHandler() {
        return keyReleasedHandler;
    }

    /**
     * Gets all objects of the specified type that are intersecting with this actor.
     *
     * @param cls The class type of the objects to find.
     * @param <A> The type parameter.
     * @return A list of intersecting objects of the specified type.
     */
    public <A extends Actor> List<A> getIntersectingObjects(Class<A> cls) {
        List<A> intersectingObjects = new ArrayList<>();
        for (A actor : getWorld().getObjects(cls)) {
            if (actor != this && actor.intersects(getBoundsInLocal())) {
                intersectingObjects.add(actor);
            }
        }
        return intersectingObjects;
    }

    public <A extends Actor> A getOneIntersectingObject(Class<A> cls) {
        List<A> potentialObjects = getObjectsFromWorld(cls);

        for (A actor : potentialObjects) {
            if (actor != this && actor.intersects(getBoundsInLocal())) {
                return actor;
            }
        }
        return null;
    }
    private <A extends Actor> List<A> getObjectsFromWorld(Class<A> cls) {
        World world = getWorld();
        if (world == null) {
            throw new IllegalStateException("Actor is not attached to any world.");
        }
        return world.getObjects(cls);
    }


    /**
     * Updates the actor's behavior.
     *
     * @param now The current timestamp of the animation timer.
     */
    public abstract void act(long now);

    public abstract void act(double elapsedTime);
}
