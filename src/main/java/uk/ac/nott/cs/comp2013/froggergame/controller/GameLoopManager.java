package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.End;

public class GameLoopManager {
    private LevelManager levelManager;
    private final ScoreManager scoreManager;
    private Animal animal;
    private AnimationTimer gameLoop;
    private final Pane background;

    public GameLoopManager(Pane background, ScoreManager scoreManager) {
        this.background = background;
        this.scoreManager = scoreManager;
    }

    /**
     * Sets the LevelManager dependency for this GameLoopManager.
     *
     * @param levelManager The LevelManager to be set.
     */
    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    /**
     * Sets the Animal dependency for this GameLoopManager.
     *
     * @param animal The Animal to be set.
     */
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    /**
     * Starts the game loop.
     */
    public void startGameLoop() {
        if (levelManager == null || animal == null) {
            throw new IllegalStateException("GameLoopManager dependencies are not set!");
        }

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateScore();
                processTargetReached();
                checkGameWon();
            }
        };
        gameLoop.start();
    }

    /**
     * Stops the game loop.
     */
    public void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    /**
     * Updates the score display if the Animal's score has changed.
     */
    private void updateScore() {
        if (animal.hasScoreChanged()) {
            int currentScore = animal.getPoints();
            scoreManager.updateScoreDisplay(background, currentScore); // 直接使用 background
        }
    }

    /**
     * Processes logic when the target is reached.
     * Activates End points and triggers level progression if necessary.
     */
    private void processTargetReached() {
        if (!levelManager.isLevelReset() && !animal.getIntersectingObjects(End.class).isEmpty()) {
            for (End end : animal.getIntersectingObjects(End.class)) {
                if (!end.isActivated()) {
                    end.setEnd();
                    levelManager.onTargetReached();
                }
            }
        }
    }

    /**
     * Checks if the game has been won.
     * If the game is won, stops the game loop and triggers victory logic.
     */
    private void checkGameWon() {
        if (animal.isGameWon()) {
            stopGameLoop();
            System.out.println("Game won! Display appropriate UI message.");
        }
    }
}
