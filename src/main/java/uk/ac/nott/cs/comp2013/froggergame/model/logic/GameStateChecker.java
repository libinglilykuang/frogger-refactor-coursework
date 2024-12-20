package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import uk.ac.nott.cs.comp2013.froggergame.controller.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;

public class GameStateChecker {
    public static void checkGameState(LevelManager levelManager, Animal animal, GameEventListener eventListener) {
        if (levelManager.checkVictoryCondition()) {
            if (eventListener != null) {
                eventListener.onGameWon(animal.getStateManager().getPoints());
            }
        }

        else if (animal.isDead()) {
            if (eventListener != null) {
                eventListener.onGameOver();
            }
        }
    }
}
