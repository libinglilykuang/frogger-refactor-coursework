package uk.ac.nott.cs.comp2013.froggergame.controller.input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.StateManager;

public class InputHandler {
    private static final KeyCode JUMP_KEY = KeyCode.W;
    private static final int POINTS_FOR_JUMP = 10;

    public void processInput(Animal animal, KeyEvent event) {
        if (animal.getStateManager().isNoMove()) return;

        KeyCode key = event.getCode();

        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            handleKeyPressed(animal, key);
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            handleKeyReleased(animal, key);
        }
    }

    private void handleKeyPressed(Animal animal, KeyCode key) {
        StateManager stateManager = animal.getStateManager();
        stateManager.move(animal.getMovementX(key), animal.getMovementY(key), key, true);
    }

    private void handleKeyReleased(Animal animal, KeyCode key) {
        StateManager stateManager = animal.getStateManager();

        if (key == JUMP_KEY && animal.getY() < animal.getHighestY()) {
            updateHighestYAndScore(animal, stateManager);
        }

        stateManager.move(animal.getMovementX(key), animal.getMovementY(key), key, false);
    }

    private void updateHighestYAndScore(Animal animal, StateManager stateManager) {
        animal.setHighestY(animal.getY());
        stateManager.addPoints(POINTS_FOR_JUMP);
    }

}
