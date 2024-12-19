package uk.ac.nott.cs.comp2013.froggergame.controller.input;

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
            newScene.setOnKeyPressed(event -> handleKeyEvent(event, true));
            newScene.setOnKeyReleased(event -> handleKeyEvent(event, false));
        }
    }

    private void handleKeyEvent(KeyEvent event, boolean isKeyPressed) {
        List<KeyEventHandlerProvider> providers = world.getObjects(KeyEventHandlerProvider.class);

        for (KeyEventHandlerProvider provider : providers) {
            try {
                processKeyEventWithProvider(event, isKeyPressed, provider);
            } catch (Exception e) {
                logError(event, provider, e);
            }
        }
    }

    private void processKeyEventWithProvider(KeyEvent event, boolean isKeyPressed, KeyEventHandlerProvider provider) {
        EventHandler<? super KeyEvent> handler = isKeyPressed
                ? provider.getKeyPressedHandler()
                : provider.getKeyReleasedHandler();

        if (handler != null) {
            handler.handle(event);
        }
    }

    private void logError(KeyEvent event, KeyEventHandlerProvider provider, Exception e) {
        System.err.println("Error processing key event: " + event.getCode());
        System.err.println("Provider: " + provider.getClass().getSimpleName());
        e.printStackTrace();
    }

}
