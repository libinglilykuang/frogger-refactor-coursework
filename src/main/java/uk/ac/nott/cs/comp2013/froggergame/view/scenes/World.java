package uk.ac.nott.cs.comp2013.froggergame.view.scenes;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import uk.ac.nott.cs.comp2013.froggergame.controller.input.KeyEventHandlerProvider;
import uk.ac.nott.cs.comp2013.froggergame.controller.input.KeyEventManager;
import uk.ac.nott.cs.comp2013.froggergame.controller.timer.TimerManager;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

public abstract class World extends Pane implements KeyEventHandlerProvider {
    private final TimerManager timerManager;
    private final KeyEventManager keyEventManager;
    private EventHandler<? super KeyEvent> keyPressedHandler;
    private EventHandler<? super KeyEvent> keyReleasedHandler;
    private final List<Actor> actors = new ArrayList<>();

    public World() {
        timerManager = new TimerManager(this, (now, actor) -> actor.act(now));
        keyEventManager = new KeyEventManager(this);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void start() {
        timerManager.initializeTimer();
        timerManager.startTimer();
    }

    public void addActor(Actor actor) {
        actors.add(actor);
        getChildren().add(actor);
    }

    public <A> List<A> getObjects(Class<A> cls) {
        return getChildren().stream()
                .filter(cls::isInstance)
                .map(cls::cast)
                .toList();
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
