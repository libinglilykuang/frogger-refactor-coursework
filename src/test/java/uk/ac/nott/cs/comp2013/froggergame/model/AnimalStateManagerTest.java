package uk.ac.nott.cs.comp2013.froggergame.model;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.animation.DeathAnimationHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.AnimalStateManager;
import javafx.scene.input.KeyCode;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageManager;
import java.lang.reflect.Field;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AnimalStateManagerTest {

    private AnimalStateManager stateManager;
    private Animal mockAnimal;
    private ImageManager mockImageManager;
    private DeathAnimationHandler mockDeathAnimationHandler;

    @BeforeEach
    void setUp() throws Exception {
        mockAnimal = mock(Animal.class);
        mockImageManager = mock(ImageManager.class);
        mockDeathAnimationHandler = mock(DeathAnimationHandler.class);

        stateManager = new AnimalStateManager(mockAnimal, 5);

        Field imageManagerField = AnimalStateManager.class.getDeclaredField("imageManager");
        imageManagerField.setAccessible(true);
        imageManagerField.set(stateManager, mockImageManager);

        Field handlerField = AnimalStateManager.class.getDeclaredField("deathAnimationHandler");
        handlerField.setAccessible(true);
        handlerField.set(stateManager, mockDeathAnimationHandler);
    }

    @Test
    void testReset() {
        stateManager.reset();
        assertFalse(stateManager.isNoMove());
        assertEquals(0, stateManager.getPoints());
    }

    @Test
    void testUpdateDeathAnimation() {
        assertNotNull(mockDeathAnimationHandler);

        when(mockDeathAnimationHandler.animateDeath(anyLong(), any()))
                .thenReturn(DeathAnimationHandler.AnimationState.IN_PROGRESS);

        stateManager.updateDeathAnimation(System.nanoTime(), "cardeath", 4);

        verify(mockDeathAnimationHandler).animateDeath(anyLong(), any());
        assertTrue(stateManager.isNoMove());
    }

    @Test
    void testMoveAnimal() {
        KeyCode key = KeyCode.W;
        Image mockImage = mock(Image.class);
        when(mockImageManager.getImageForKey(key, true)).thenReturn(mockImage);

        stateManager.moveAnimal(10, -10, key, true);

        verify(mockAnimal).setImage(mockImage);
        verify(mockAnimal).move(10, -10);
    }


    @Test
    void testAddPoints() {
        stateManager.addPoints(20);
        assertEquals(20, stateManager.getPoints());
    }

    @Test
    void testHasScoreChanged() {
        assertFalse(stateManager.hasScoreChanged());

        stateManager.addPoints(10);
        assertTrue(stateManager.hasScoreChanged());

        stateManager.resetScoreChangedFlag();
        assertFalse(stateManager.hasScoreChanged());
    }
}
