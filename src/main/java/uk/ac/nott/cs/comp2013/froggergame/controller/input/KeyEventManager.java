package uk.ac.nott.cs.comp2013.froggergame.controller.input;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;
import java.util.ArrayList;
import java.util.List;

public class KeyEventManager {
    private final World world;
    private final List<KeyEventHandlerProvider> providers = new ArrayList<>();

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
        for (KeyEventHandlerProvider provider : providers) {
            dispatchKeyEvent(event, isKeyPressed, provider);
        }
    }

    private void dispatchKeyEvent(KeyEvent event, boolean isKeyPressed, KeyEventHandlerProvider provider) {
        EventHandler<? super KeyEvent> handler = isKeyPressed
                ? provider.getKeyPressedHandler()
                : provider.getKeyReleasedHandler();
        if (handler != null) {
            handler.handle(event);
        }
    }

}
