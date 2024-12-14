package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;
import java.util.List;

public class KeyEventManager {
    private final World world;

    public KeyEventManager(World world) {
        this.world = world;
        world.sceneProperty().addListener(this::configureKeyEvents);
    }

    private void configureKeyEvents(ObservableValue<? extends Scene> observable, Scene oldScene, Scene newScene) {
        if (newScene != null) {
            newScene.setOnKeyPressed(event -> processKeyEvent(event, true));
            newScene.setOnKeyReleased(event -> processKeyEvent(event, false));
        }
    }

    private void processKeyEvent(KeyEvent event, boolean isKeyPressed) {
        if (world != null) {
            processSingleKeyEvent(event, isKeyPressed, world.getOnKeyPressed(), world.getOnKeyReleased());
        }

        List<Actor> actors = world.getObjects(Actor.class).stream()
                .filter(actor -> actor.getOnKeyPressed() != null || actor.getOnKeyReleased() != null)
                .toList();

        for (Actor actor : actors) {
            processSingleKeyEvent(event, isKeyPressed, actor.getOnKeyPressed(), actor.getOnKeyReleased());
        }
    }

    private void processSingleKeyEvent(KeyEvent event, boolean isKeyPressed,
                                       EventHandler<? super KeyEvent> onPressedHandler,
                                       EventHandler<? super KeyEvent> onReleasedHandler) {
        if (isKeyPressed && onPressedHandler != null) {
            onPressedHandler.handle(event);
        } else if (!isKeyPressed && onReleasedHandler != null) {
            onReleasedHandler.handle(event);
        }
    }


}
