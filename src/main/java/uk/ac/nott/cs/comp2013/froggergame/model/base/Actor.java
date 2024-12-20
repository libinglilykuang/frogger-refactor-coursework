package uk.ac.nott.cs.comp2013.froggergame.model.base;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.froggergame.controller.input.KeyEventHandlerProvider;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;

import java.util.List;
import java.util.stream.Collectors;

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

    public Actor(int xpos, int ypos, double speed) {
        setX(xpos);
        setY(ypos);
        this.speed = speed;
    }

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
        if (isOutOfBounds(speed)) {
            resetPosition(speed);
        }
    }

    private boolean isOutOfBounds(double speed) {
        return (getX() > screenRightBound && speed > 0) || (getX() < screenLeftBound && speed < 0);
    }

    private void resetPosition(double speed) {
        if (speed > 0) {
            setX(resetRightPosition);
        } else if (speed < 0) {
            setX(resetLeftPosition);
        }
    }

    public World getWorld() {
        World world = (World) getParent();
        if (world == null) {
            throw new IllegalStateException("Actor is not attached to any World instance.");
        }
        return world;
    }

    public double getSpeed() {
        return speed;
    }

    public <A> List<A> getIntersectingObjects(Class<A> cls) {
        return getObjectsFromWorld(cls).stream()
                .filter(this::isIntersecting)
                .collect(Collectors.toList());
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

    private <A> List<A> getObjectsFromWorld(Class<A> cls) {
        return getWorld().getObjects(cls);
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
