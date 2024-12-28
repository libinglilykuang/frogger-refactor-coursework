package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.froggergame.controller.loop.GameLoopManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class GameLoopManagerTest {

    private GameLoopManager gameLoopManager;
    private Animal mockAnimal;
    private GameStateChecker mockGameStateChecker;
    private GameEventListener mockEventListener;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockAnimal = mock(Animal.class);
        mockGameStateChecker = mock(GameStateChecker.class);
        mockEventListener = mock(GameEventListener.class);

        // Initialize GameLoopManager
        gameLoopManager = new GameLoopManager(mockAnimal, mockGameStateChecker);

        // Set GameEventListener
        gameLoopManager.setGameEventListener(mockEventListener);
    }

    @Test
    void testStartGameLoopExecution() {
        gameLoopManager.startGameLoopExecution();

        // Validate AnimationTimer is initialized
        assertNotNull(getPrivateField(gameLoopManager, "gameLoop"), "GameLoop should be initialized");
    }

    @Test
    void testStopGameLoopExecution() {
        gameLoopManager.startGameLoopExecution();

        // Stop the game loop
        gameLoopManager.stopGameLoopExecution();

        // Validate that the AnimationTimer stop method is called
        AnimationTimer gameLoop = (AnimationTimer) getPrivateField(gameLoopManager, "gameLoop");
        assertNotNull(gameLoop, "GameLoop should be initialized");
        gameLoop.stop();
    }

    @Test
    void testCheckGameState() {
        // Start the game loop
        gameLoopManager.startGameLoopExecution();

        // Call the private method via reflection
        callPrivateMethod(gameLoopManager);

        // Verify that GameStateChecker is called
        verify(mockGameStateChecker).checkGameState(mockAnimal, mockEventListener);
    }

    // Utility method for invoking private methods
    private void callPrivateMethod(Object object) {
        try {
            var method = object.getClass().getDeclaredMethod("checkGameState");
            method.setAccessible(true);
            method.invoke(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void testExecuteGameLoop() {
        long initialTime = 1_000_000_000;
        long currentTime = 2_000_000_000;

        // Set the last update time
        setPrivateField(gameLoopManager, "lastUpdateTime", initialTime);

        // Mock the animal's active state
        when(mockAnimal.isActive()).thenReturn(true);

        // Invoke executeGameLoop method
        callPrivateMethod(gameLoopManager, "executeGameLoop", currentTime);

        // Verify elapsed time calculation and event listener update
        verify(mockEventListener).update(1.0);
        verify(mockGameStateChecker).checkGameState(mockAnimal, mockEventListener);
    }

    @Test
    void testCalculateElapsedTime() {
        long initialTime = 1_000_000_000;
        long currentTime = 2_000_000_000;

        // Set the last update time
        setPrivateField(gameLoopManager, "lastUpdateTime", initialTime);

        // Call calculateElapsedTime
        double elapsedTime = (double) callPrivateMethod(gameLoopManager, "calculateElapsedTime", currentTime);

        // Assert the result
        assertEquals(1.0, elapsedTime, 0.001);
    }

    // Utility methods for accessing private fields and methods
    private Object getPrivateField(Object object, String fieldName) {
        try {
            var field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            var field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object callPrivateMethod(Object object, String methodName, Object... args) {
        try {
            var method = object.getClass().getDeclaredMethod(methodName, long.class);
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
