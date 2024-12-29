package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.controller.loop.GameLoopManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.level.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Level;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import uk.ac.nott.cs.comp2013.froggergame.view.components.TimeBar;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;
import static org.mockito.Mockito.*;

class GameControllerTest {

    private GameController gameController;
    private MyStage mockBackground;
    private GameInitializer mockGameInitializer;
    private Animal mockAnimal;
    private GameObjectFactory mockGameObjectFactory;
    private Score mockScoreView;

    @BeforeEach
    void setUp() {
        mockBackground = mock(MyStage.class); // Mock MyStage
        ScoreManager mockScoreManager = mock(ScoreManager.class); // Mock ScoreManager
        mockGameObjectFactory = mock(GameObjectFactory.class); // Mock GameObjectFactory
        mockGameInitializer = mock(GameInitializer.class); // Mock GameInitializer
        GameStateChecker mockGameStateChecker = mock(GameStateChecker.class); // Mock GameStateChecker
        mockScoreView = mock(Score.class); // Mock Score
        mockAnimal = mock(Animal.class); // Mock Animal

        when(mockGameObjectFactory.createAnimal()).thenReturn(mockAnimal);

        // Mock TimeBar
        TimeBar mockTimeBar = mock(TimeBar.class);
        when(mockBackground.getTimeBar()).thenReturn(mockTimeBar);

        // Mock getStylesheets to return a non-null ObservableList
        when(mockBackground.getStylesheets()).thenReturn(javafx.collections.FXCollections.observableArrayList());

        // Mock getChildren to return a non-null ObservableList
        when(mockBackground.getChildren()).thenReturn(javafx.collections.FXCollections.observableArrayList());

        GameControllerConfig config = new GameControllerConfig(
                mockBackground,
                mockScoreManager,
                mockGameObjectFactory,
                mockGameStateChecker,
                mockScoreView,
                mockGameInitializer
        );
        gameController = new GameController(config);
    }

    @Test
    void testInitializeGame() {
        gameController.initializeGame();

        // Verify that game components are initialized correctly
        verify(mockGameInitializer).initializeComponents(mockScoreView);
        verify(mockBackground.getTimeBar()).setTimeOutListener(any());
    }

    @Test
    void testStartGameLoop() {
        // Inject mock GameLoopManager using reflection
        GameLoopManager mockGameLoopManager = mock(GameLoopManager.class);
        setPrivateField(gameController, "gameLoopManager", mockGameLoopManager);

        // Call the method to start the game loop
        gameController.startGameLoop();

        // Verify that the TimeBar's start method was called
        verify(mockBackground.getTimeBar()).start();

        // Verify that GameLoopManager's startGameLoopExecution method was called
        verify(mockGameLoopManager).startGameLoopExecution();
    }

    // Helper method to set private fields using reflection
    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testStopGame() {
        // Inject mock GameLoopManager using reflection
        GameLoopManager mockGameLoopManager = mock(GameLoopManager.class);
        setPrivateField(gameController, "gameLoopManager", mockGameLoopManager);

        // Call the method to stop the game
        gameController.stopGame();

        // Verify that GameLoopManager's stopGameLoopExecution method was called
        verify(mockGameLoopManager).stopGameLoopExecution();

        // Verify that the TimeBar's stop method was called
        verify(mockBackground.getTimeBar()).stop();

        // Verify that the background's stopMusic method was called
        verify(mockBackground).stopMusic();
    }

    @Test
    void testConfigureInputHandling() {
        Scene mockScene = mock(Scene.class);

        gameController.configureInputHandling(mockScene);

        verify(mockScene).setOnKeyPressed(any());
        verify(mockScene).setOnKeyReleased(any());
    }

    @Test
    void testUpdateGameState() {
        // Mock StateManager
        var mockStateManager = mock(StateManager.class);
        when(mockAnimal.getStateManager()).thenReturn(mockStateManager);
        when(mockStateManager.hasScoreChanged()).thenReturn(true);
        // Mock LevelManager and Level
        var mockLevelManager = mock(LevelManager.class);
        var mockLevel = mock(Level.class);
        when(mockLevelManager.hasLevelChanged()).thenReturn(true);
        when(mockLevelManager.getLevelView()).thenReturn(mockLevel);
        // Inject mocks into GameController
        setPrivateField(gameController, "animal", mockAnimal);
        setPrivateField(gameController, "levelManager", mockLevelManager);
        // Ensure necessary interactions occur
        gameController.updateGameState();
        // Verify interactions
        verify(mockStateManager).hasScoreChanged();
        verify(mockStateManager).resetScoreChangedFlag();
        verify(mockLevelManager).hasLevelChanged();
        verify(mockLevelManager).resetLevelChangedFlag();
        verify(mockLevel).updateLevelDisplay(anyInt());
    }
}
