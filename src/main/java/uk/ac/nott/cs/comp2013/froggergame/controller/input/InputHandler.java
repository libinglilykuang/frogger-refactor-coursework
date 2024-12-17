package uk.ac.nott.cs.comp2013.froggergame.controller.input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.StateManager;

public class InputHandler {
    public void processInput(Animal animal, KeyEvent event) {
        if (animal.getStateManager().isNoMove()) return;

        KeyCode key = event.getCode();
        StateManager stateManager = animal.getStateManager();

        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            stateManager.move(animal.getMovementX(key), animal.getMovementY(key), key, true);
        }
        else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            if (key == KeyCode.W && animal.getY() < animal.getHighestY()) {
                animal.setHighestY(animal.getY());
                stateManager.addPoints(10);
            }
            stateManager.move(animal.getMovementX(key), animal.getMovementY(key), key, false);
        }
    }
}
