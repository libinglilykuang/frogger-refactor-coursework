package uk.ac.nott.cs.comp2013.froggergame.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.froggergame.JavaFXTestBase;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.*;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.collision.AnimalCollisionHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.StateManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AnimalCollisionHandlerTest extends JavaFXTestBase {
    private Animal mockAnimal;
    private StateManager mockStateManager;
    private Score mockScore;
    private AnimalCollisionHandler collisionHandler;

    @BeforeEach
    void setUp() {
        mockAnimal = mock(Animal.class);
        mockStateManager = mock(StateManager.class);
        mockScore = mock(Score.class);
        collisionHandler = new AnimalCollisionHandler(mockAnimal, mockStateManager, mockScore);
    }

    @Test
    void testObstacleCollision() {
        Obstacle mockObstacle = mock(Obstacle.class);
        when(mockAnimal.getIntersectingObjects(Obstacle.class)).thenReturn(List.of(mockObstacle));

        collisionHandler.checkCollisions();

        verify(mockAnimal).setCarDeath(true);
        verify(mockStateManager).addPoints(-50);
        verify(mockScore).updateScore(anyInt());
    }

    @Test
    void testLogCollision() {
        Log mockLog = mock(Log.class);
        when(mockAnimal.getIntersectingObjects(Log.class)).thenReturn(List.of(mockLog));

        collisionHandler.checkCollisions();

        verify(mockAnimal).setCurrentPlatform(mockLog);
    }

    @Test
    void testWetTurtleCollisionWhenSunk() {
        WetTurtle mockWetTurtle = mock(WetTurtle.class);
        when(mockAnimal.getIntersectingObjects(WetTurtle.class)).thenReturn(List.of(mockWetTurtle));
        when(mockWetTurtle.isSunk()).thenReturn(true);

        collisionHandler.checkCollisions();

        verify(mockAnimal).clearCurrentPlatform();
        verify(mockAnimal).setWaterDeath(true);
        verify(mockStateManager).addPoints(-50);
        verify(mockScore).updateScore(anyInt());
        verify(mockAnimal).resetPosition();
    }

    @Test
    void testEndCollision() {
        End mockEnd = mock(End.class);
        when(mockAnimal.getIntersectingObjects(End.class)).thenReturn(List.of(mockEnd));
        when(mockEnd.isActivated()).thenReturn(false);

        collisionHandler.checkCollisions();

        verify(mockEnd).setEnd();
        verify(mockStateManager).addPoints(50);
        verify(mockScore).updateScore(anyInt());
        verify(mockAnimal).resetPosition();
    }

    @Test
    void testSpiderCollision() {
        Spider mockSpider = mock(Spider.class);
        Pane mockPane = mock(Pane.class);
        Log mockLog = mock(Log.class);

        ObservableList<Node> mockChildren = FXCollections.observableArrayList();
        when(mockPane.getChildren()).thenReturn(mockChildren);

        when(mockAnimal.getIntersectingObjects(Spider.class)).thenReturn(List.of(mockSpider));
        when(mockSpider.getParentPane()).thenReturn(mockPane);
        when(mockSpider.getParentLog()).thenReturn(mockLog);

        mockChildren.add(mockSpider);

        collisionHandler.checkCollisions();

        verify(mockStateManager).addPoints(20);
        verify(mockScore).updateScore(anyInt());
        assertFalse(mockChildren.contains(mockSpider));
        verify(mockLog).clearSpider();
    }

    @Test
    void testNoCollisionClearsPlatform() {
        when(mockAnimal.isOnPlatform()).thenReturn(true);
        when(mockAnimal.getIntersectingObjects(any())).thenReturn(List.of());

        collisionHandler.checkCollisions();

        verify(mockAnimal).clearCurrentPlatform();
    }
}

