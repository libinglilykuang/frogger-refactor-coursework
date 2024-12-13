package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.beans.value.ObservableValue;
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
        if (isKeyPressed && world.getOnKeyPressed() != null) {
            world.getOnKeyPressed().handle(event);
        } else if (!isKeyPressed && world.getOnKeyReleased() != null) {
            world.getOnKeyReleased().handle(event);
        }

        List<Actor> actors = world.getObjects(Actor.class);
        for (Actor actor : actors) {
            if (isKeyPressed && actor.getOnKeyPressed() != null) {
                actor.getOnKeyPressed().handle(event);
            } else if (!isKeyPressed && actor.getOnKeyReleased() != null) {
                actor.getOnKeyReleased().handle(event);
            }
        }
    }
}
