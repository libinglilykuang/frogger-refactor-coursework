package uk.ac.nott.cs.comp2013.froggergame.controller.loop;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import uk.ac.nott.cs.comp2013.froggergame.controller.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.level.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

public class GameLoopManager {
    private static final double NANOSECONDS_TO_SECONDS = 1_000_000_000.0;
    private final Score scoreView;
    private final LevelManager levelManager;
    private final ScoreManager scoreManager;
    private final Animal animal;
    private AnimationTimer gameLoop;
    private final Pane background;
    private GameEventListener eventListener;
    private long lastUpdateTime = 0;
    private final GameStateChecker gameStateChecker;

    public GameLoopManager(LevelManager levelManager, ScoreManager scoreManager, Animal animal, Pane background, GameStateChecker gameStateChecker, Score scoreView) {
        this.levelManager = levelManager;
        this.scoreManager = scoreManager;
        this.animal = animal;
        this.background = background;
        this.gameStateChecker = gameStateChecker;
        this.scoreView = scoreView;
    }

    private void checkGameState() {
        gameStateChecker.checkGameState(animal, eventListener);
    }

    public void setGameEventListener(GameEventListener listener) {
        this.eventListener = listener;
    }

    public void stopGameLoopExecution() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    public void startGameLoopExecution() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    executeGameLoop(now);
                } catch (Exception e) {
                    stopGameLoopExecution();
                }
            }
        };
        gameLoop.start();
    }

    private void executeGameLoop(long now) {
        if (lastUpdateTime == 0) {
            lastUpdateTime = now;
            return;
        }
        double elapsedTime = calculateElapsedTime(now);
        if (eventListener != null) {
            eventListener.update(elapsedTime);
        }
        updateScore();
        checkGameState();
        lastUpdateTime = now;
    }

    private double calculateElapsedTime(long now) {
        return (now - lastUpdateTime) / NANOSECONDS_TO_SECONDS;
    }

    private void updateScore() {
        if (animal.getStateManager().hasScoreChanged()) {
            int currentPoints = animal.getStateManager().getPoints();
            scoreView.updateScoreDisplay(background, currentPoints, scoreManager);
            animal.getStateManager().resetScoreChangedFlag();
        }
    }


}