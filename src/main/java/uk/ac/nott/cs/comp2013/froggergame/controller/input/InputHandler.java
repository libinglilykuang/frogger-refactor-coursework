package uk.ac.nott.cs.comp2013.froggergame.controller.input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;

public class InputHandler {
    private static final KeyCode JUMP_KEY = KeyCode.W;
    private static final int POINTS_FOR_JUMP = 10;

    private Animal animal;

    public InputHandler(Animal animal) {
        this.animal = animal;
    }

    public void handleKeyPressed(KeyEvent event) {
        processInput(event, true);
    }

    public void handleKeyReleased(KeyEvent event) {
        processInput(event, false);
    }

    private void processInput(KeyEvent event, boolean isPressed) {
        if (animal.getStateManager().isNoMove()) return;
        processKeyEvent(event.getCode(), isPressed);
    }

    private void processKeyEvent(KeyCode key, boolean isPressed) {
        StateManager stateManager = animal.getStateManager();
        if (!isPressed && isJumpKey(key)) {
            updateHighestYAndScore(stateManager);
        }
        stateManager.moveAnimal(animal.getMovementX(key), animal.getMovementY(key), key, isPressed);
    }

    private boolean isJumpKey(KeyCode key) {
        return key == JUMP_KEY && animal.getY() < animal.getHighestY();
    }

    private void updateHighestYAndScore(StateManager stateManager) {
        animal.setHighestY(animal.getY());
        stateManager.addPoints(POINTS_FOR_JUMP);
    }
}
