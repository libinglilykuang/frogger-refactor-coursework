package uk.ac.nott.cs.comp2013.froggergame.model.base;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import uk.ac.nott.cs.comp2013.froggergame.controller.input.KeyEventHandlerProvider;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;

/**
 * Base class for all actors in the game.
 * Provides common functionalities like movement, collision detection, and input management.
 */
public abstract class Actor extends ImageView implements KeyEventHandlerProvider {
    private EventHandler<? super KeyEvent> keyPressedHandler;
    private EventHandler<? super KeyEvent> keyReleasedHandler;

    protected double speed;
    private int screenRightBound;
    private int screenLeftBound;
    private int resetRightPosition;
    private int resetLeftPosition;
    private boolean hasMoved = false;

    public Actor(int xpos, int ypos, double speed, int width, int height) {
        setX(xpos);
        setY(ypos);
        this.speed = speed;
    }

    public void move(double dx, double dy) {
        setX(getX() + dx);
        setY(getY() + dy);
        hasMoved = true;
    }

    public boolean hasMoved() {
        boolean result = hasMoved;
        hasMoved = false;
        return result;
    }

    public void configureBounds(int screenLeftBound, int screenRightBound, int resetLeftPosition, int resetRightPosition) {
        this.screenLeftBound = screenLeftBound;
        this.screenRightBound = screenRightBound;
        this.resetLeftPosition = resetLeftPosition;
        this.resetRightPosition = resetRightPosition;
    }

    public void resetPositionIfOutOfBounds(double speed) {
        if (isOutOfRightBound(speed)) {
            resetToRightBound();
        } else if (isOutOfLeftBound(speed)) {
            resetToLeftBound();
        }
    }

    private boolean isOutOfRightBound(double speed) {
        return getX() > screenRightBound && speed > 0;
    }

    private boolean isOutOfLeftBound(double speed) {
        return getX() < screenLeftBound && speed < 0;
    }

    private void resetToRightBound() {
        setX(resetRightPosition);
    }

    private void resetToLeftBound() {
        setX(resetLeftPosition);
    }

    public World getWorld() {
        return (World) getParent();
    }

    public double getSpeed() {
        return speed;
    }

    public <A> List<A> getIntersectingObjects(Class<A> cls) {
        List<A> objectsFromWorld = getObjectsFromWorld(cls);
        return objectsFromWorld.stream()
                .filter(this::isIntersecting)
                .toList();
    }

    private boolean isIntersecting(Object obj) {
        if (obj instanceof Node) {
            return this.getBoundsInParent().intersects(((Node) obj).getBoundsInParent());
        }
        return false;
    }

    public <A extends Actor> A getOneIntersectingObject(Class<A> cls) {
        return getIntersectingObjects(cls).stream().findFirst().orElse(null);
    }

    private <A extends Actor> List<A> filterIntersectingObjects(List<A> actors) {
        List<A> intersectingObjects = new ArrayList<>();
        for (A actor : actors) {
            if (actor != this && actor.intersects(getBoundsInLocal())) {
                intersectingObjects.add(actor);
            }
        }
        return intersectingObjects;
    }

    private <A> List<A> getObjectsFromWorld(Class<A> cls) {
        World world = getWorld();
        if (world == null) {
            throw new IllegalStateException("Actor is not attached to any world.");
        }
        return world.getObjects(cls);
    }

    public abstract void act(long now);

    public abstract void act(double elapsedTime);

    @Override
    public EventHandler<? super KeyEvent> getKeyPressedHandler() {
        return keyPressedHandler;
    }

    @Override
    public EventHandler<? super KeyEvent> getKeyReleasedHandler() {
        return keyReleasedHandler;
    }

}
