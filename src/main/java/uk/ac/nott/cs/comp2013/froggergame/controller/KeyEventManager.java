package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
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
        List<KeyEventHandlerProvider> providers = world.getObjects(KeyEventHandlerProvider.class);

        for (KeyEventHandlerProvider provider : providers) {
            processSingleKeyEvent(event, isKeyPressed, provider.getKeyPressedHandler(), provider.getKeyReleasedHandler());
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
