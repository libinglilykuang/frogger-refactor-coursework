package uk.ac.nott.cs.comp2013.froggergame.model.logic.state;

import uk.ac.nott.cs.comp2013.froggergame.controller.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;

public class GameStateChecker {

    public void checkGameState(Animal animal, GameEventListener eventListener) {
        StateManager stateManager = animal.getStateManager();

        if (stateManager.isVictory()) {
            notifyGameWon(eventListener, stateManager.getPoints());
        } else if (animal.isDead()) {
            notifyGameOver(eventListener);
        }
    }

    private void notifyGameWon(GameEventListener eventListener, int points) {
        if (eventListener != null) {
            eventListener.onGameWon(points);
        }
    }

    private void notifyGameOver(GameEventListener eventListener) {
        if (eventListener != null) {
            eventListener.onGameOver();
        }
    }
}
