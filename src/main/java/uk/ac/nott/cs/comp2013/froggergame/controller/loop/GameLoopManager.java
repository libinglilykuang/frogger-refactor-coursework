package uk.ac.nott.cs.comp2013.froggergame.controller.loop;

import javafx.animation.AnimationTimer;
import uk.ac.nott.cs.comp2013.froggergame.controller.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;

public class GameLoopManager {
    private static final double NANOSECONDS_TO_SECONDS = 1_000_000_000.0;
    private final Animal animal;
    private AnimationTimer gameLoop;
    private GameEventListener eventListener;
    private long lastUpdateTime = 0;
    private final GameStateChecker gameStateChecker;

    public GameLoopManager(Animal animal,GameStateChecker gameStateChecker) {
        this.animal = animal;
        this.gameStateChecker = gameStateChecker;
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
        if (!animal.isActive()) {
            return;
        }

        if (eventListener != null) {
            eventListener.update(elapsedTime);
        }
        checkGameState();
        lastUpdateTime = now;
    }

    private double calculateElapsedTime(long now) {
        return (now - lastUpdateTime) / NANOSECONDS_TO_SECONDS;
    }
}