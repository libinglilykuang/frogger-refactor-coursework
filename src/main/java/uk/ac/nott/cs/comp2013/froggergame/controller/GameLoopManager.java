package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;

public class GameLoopManager {
    private final LevelManager levelManager;
    private final ScoreManager scoreManager;
    private final Animal animal;
    private AnimationTimer gameLoop;
    private final Pane background;
    private GameEventListener eventListener;
    private long lastUpdateTime = 0;

    public GameLoopManager(LevelManager levelManager, ScoreManager scoreManager, Animal animal, Pane background, GameEventListener eventListener) {
        this.levelManager = levelManager;
        this.scoreManager = scoreManager;
        this.animal = animal;
        this.background = background;
        this.eventListener = eventListener;
    }

    public void setGameEventListener(GameEventListener listener) {
        this.eventListener = listener;
    }

    public void startGameLoopExecution() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdateTime == 0) {
                    lastUpdateTime = now;
                    return;
                }

                double elapsedTime = (now - lastUpdateTime) / 1_000_000_000.0;

                lastUpdateTime = now;

                if (eventListener != null) {
                    eventListener.update(elapsedTime);
                }
                scoreManager.updateScoreIfChanged(background, animal);
                GameStateChecker.checkGameState(levelManager, animal, eventListener);
            }
        };
        gameLoop.start();
    }

    public void stopGameLoopExecution() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}