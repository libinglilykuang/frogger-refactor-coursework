package uk.ac.nott.cs.comp2013.froggergame.view.scenes;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import uk.ac.nott.cs.comp2013.froggergame.controller.KeyEventHandlerProvider;
import uk.ac.nott.cs.comp2013.froggergame.controller.KeyEventManager;
import uk.ac.nott.cs.comp2013.froggergame.controller.TimerManager;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import java.util.List;

public abstract class World extends Pane implements KeyEventHandlerProvider {
    private final TimerManager timerManager;
    private final KeyEventManager keyEventManager;
    private EventHandler<? super KeyEvent> keyPressedHandler;
    private EventHandler<? super KeyEvent> keyReleasedHandler;

    public World() {
        timerManager = new TimerManager(this);
        keyEventManager = new KeyEventManager(this);
    }

    public void start() {
        timerManager.createAndStartTimer();
    }

    public void add(Actor actor) {
        validateActor(actor);
        getChildren().add(actor);
    }

    private void validateActor(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("Cannot add null Actor.");
        }
        if (actor.getImage() == null) {
            throw new IllegalArgumentException("Cannot add ImageView with null image to MyStage.");
        }
    }

    public void stop() {
        timerManager.stopTimer();
    }

    public void removeActor(Actor actor) {
        getChildren().remove(actor);
    }

    public <A> List<A> getObjects(Class<A> cls) {
        return getChildren().stream()
                .filter(cls::isInstance)
                .map(cls::cast)
                .toList();
    }
    public void setKeyPressedHandler(EventHandler<? super KeyEvent> handler) {
        this.keyPressedHandler = handler;
    }

    public void setKeyReleasedHandler(EventHandler<? super KeyEvent> handler) {
        this.keyReleasedHandler = handler;
    }

    @Override
    public EventHandler<? super KeyEvent> getKeyPressedHandler() {
        return keyPressedHandler;
    }

    @Override
    public EventHandler<? super KeyEvent> getKeyReleasedHandler() {
        return keyReleasedHandler;
    }

    public abstract void act(long now);

    public abstract void act(double elapsedTime);
}
