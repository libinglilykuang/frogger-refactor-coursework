package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import uk.ac.nott.cs.comp2013.froggergame.controller.GameLoopManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;

public class GameStateChecker {
    public static void checkGameState(LevelManager levelManager, Animal animal, GameLoopManager.GameEventListener eventListener) {
        if (levelManager.checkVictoryCondition(animal)) {
            eventListener.onGameWon(animal.getPoints());
        } else if (animal.isDead()) {
            eventListener.onGameOver();
        }
    }
}
