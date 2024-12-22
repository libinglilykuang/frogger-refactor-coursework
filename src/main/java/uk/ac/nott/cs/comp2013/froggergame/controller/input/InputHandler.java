package uk.ac.nott.cs.comp2013.froggergame.controller.input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;

public class InputHandler {
    private static final KeyCode JUMP_KEY = KeyCode.W;
    private static final int POINTS_FOR_JUMP = 10;

    public void processInput(Animal animal, KeyEvent event) {
        if (animal.getStateManager().isNoMove()) return;
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            processKeyEvent(animal, event.getCode(), true);
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            processKeyEvent(animal, event.getCode(), false);
        }
    }

    private void processKeyEvent(Animal animal, KeyCode key, boolean isPressed) {
        StateManager stateManager = animal.getStateManager();
        if (!isPressed && isJumpKey(key, animal)) {
            updateHighestYAndScore(animal, stateManager);
        }
        stateManager.moveAnimal(animal.getMovementX(key), animal.getMovementY(key), key, isPressed);
    }

    private boolean isJumpKey(KeyCode key, Animal animal) {
        return key == JUMP_KEY && animal.getY() < animal.getHighestY();
    }

    private void updateHighestYAndScore(Animal animal, StateManager stateManager) {
        animal.setHighestY(animal.getY());
        stateManager.addPoints(POINTS_FOR_JUMP);
    }

}
