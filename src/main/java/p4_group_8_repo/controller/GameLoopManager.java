package p4_group_8_repo.controller;

import javafx.animation.AnimationTimer;
import p4_group_8_repo.model.LevelManager;
import p4_group_8_repo.model.ScoreManager;
import p4_group_8_repo.model.entities.Animal;
import p4_group_8_repo.model.entities.End;

public class GameLoopManager {
    private LevelManager levelManager;
    private final ScoreManager scoreManager;
    private Animal animal;
    private AnimationTimer gameLoop;

    public GameLoopManager(ScoreManager scoreManager) {
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
            scoreManager.updateScoreDisplay(levelManager.getBackground(), currentScore);
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
