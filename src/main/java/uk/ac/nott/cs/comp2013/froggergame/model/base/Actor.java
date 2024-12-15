package uk.ac.nott.cs.comp2013.froggergame.model.base;

import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all actors in the game.
 * Provides common functionalities like movement, collision detection, and input management.
 */
public abstract class Actor extends ImageView {

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

    /**
     * Gets the world this actor belongs to.
     *
     * @return The world instance.
     */
    public World getWorld() {
        return (World) getParent();
    }

    /**
     * Gets the width of the actor.
     *
     * @return The width of the actor in pixels.
     */
    public double getWidth() {
        return getBoundsInLocal().getWidth();
    }

    /**
     * Gets the height of the actor.
     *
     * @return The height of the actor in pixels.
     */
    public double getHeight() {
        return getBoundsInLocal().getHeight();
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

    /**
     * Helper method to retrieve objects of the specified type from the world.
     * This abstracts the direct call to `getWorld().getObjects`.
     *
     * @param cls The class type to filter objects.
     * @param <A> A type that extends Actor.
     * @return A list of objects of type A in the world.
     */
    private <A extends Actor> List<A> getObjectsFromWorld(Class<A> cls) {
        World world = getWorld();
        if (world == null) {
            throw new IllegalStateException("Actor is not attached to any world.");
        }
        return world.getObjects(cls);
    }


    /**
     * Handles input events for the actor.
     *
     * @param event The input event to handle.
     */
    public void manageInput(InputEvent event) {
        // Default implementation does nothing. Subclasses can override.
    }

    /**
     * Updates the actor's behavior.
     *
     * @param now The current timestamp of the animation timer.
     */
    public abstract void act(long now);

    public abstract void act(double elapsedTime);
}
