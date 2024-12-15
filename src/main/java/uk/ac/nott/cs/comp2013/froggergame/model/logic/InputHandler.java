package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;

public class InputHandler {
    public void processInput(Animal animal, KeyEvent event) {
        if (animal.getStateManager().isNoMove()) return;

        KeyCode key = event.getCode();
        StateManager stateManager = animal.getStateManager();
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            Image image = stateManager.getImageForKey(key, true);
            if (image != null) {
                stateManager.move(animal.getMovementX(key), animal.getMovementY(key), image);
            }
        }
        else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            if (key == KeyCode.W && animal.getY() < animal.getHighestY()) {
                animal.setHighestY(animal.getY());
                stateManager.addPoints(10);
            }
            Image image = stateManager.getImageForKey(key, false);
            if (image != null) {
                stateManager.move(animal.getMovementX(key), animal.getMovementY(key), image);
            }
        }
    }
}
