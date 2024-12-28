package uk.ac.nott.cs.comp2013.froggergame.model;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.froggergame.controller.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.level.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Level;
import uk.ac.nott.cs.comp2013.froggergame.view.components.TimeBar;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LevelManagerTest {

    private Animal mockAnimal;
    private Level mockLevelView;
    private TimeBar mockTimeBar;
    private GameEventListener mockEventListener;
    private LevelManager levelManager;

    @BeforeEach
    void setUp() {
        mockAnimal = mock(Animal.class);
        StateManager mockStateManager = mock(StateManager.class);

        when(mockAnimal.getStateManager()).thenReturn(mockStateManager);
        when(mockStateManager.getPoints()).thenReturn(100);

        mockLevelView = mock(Level.class);
        mockTimeBar = mock(TimeBar.class);
        mockEventListener = mock(GameEventListener.class);

        levelManager = new LevelManager(mockAnimal, mockLevelView, 3, mockTimeBar, mockEventListener);
    }

    @Test
    void testInitialLevelState() {
        assertEquals(1, levelManager.getCurrentLevel());
        assertFalse(levelManager.hasLevelChanged());
    }

    @Test
    void testSetLevel() {
        levelManager.setLevel(2);
        assertTrue(levelManager.hasLevelChanged());
        assertEquals(2, levelManager.getCurrentLevel());
    }

    @Test
    void testIncreaseLevel() {
        levelManager.increaseLevel();
        assertEquals(2, levelManager.getCurrentLevel());
        verify(mockLevelView).updateLevelDisplay(2);
        verify(mockLevelView).resetLevelLayout(any());
    }

    @Test
    void testOnTargetReached_LevelComplete() {
        // Simulate reaching all ends
        for (int i = 0; i < 3; i++) {
            levelManager.onTargetReached();
        }

        verify(mockLevelView).updateLevelDisplay(2);
        verify(mockAnimal).resetPosition();
        verify(mockTimeBar).reset();
    }

    @Test
    void testOnTargetReached_GameWon() {
        // Simulate reaching all ends for level 2
        levelManager.setLevel(2);
        for (int i = 0; i < 3; i++) {
            levelManager.onTargetReached();
        }

        verify(mockEventListener).onGameWon(anyInt());
    }

    @Test
    void testResetAnimalPosition() {
        levelManager.resetAnimalPosition();
        verify(mockAnimal).setX(300);
        verify(mockAnimal).setY(733);
    }

    @Test
    void testUpdateObjectsSpeedSimplified() {
        when(mockLevelView.getActors()).thenReturn(List.of());

        levelManager.updateObjectsSpeed(1.5);

        verify(mockLevelView).getActors(); // Verify that the method attempts to fetch actors
    }

}
