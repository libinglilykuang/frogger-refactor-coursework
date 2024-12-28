package uk.ac.nott.cs.comp2013.froggergame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Obstacle;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {
    private Obstacle obstacle;

    @BeforeEach
    void setUp() {
        obstacle = new Obstacle("default.png", 100, 200, 1.0, 80, 40);
    }

    @Test
    void testObstacleInitialization() {
        assertEquals(100, obstacle.getX());
        assertEquals(200, obstacle.getY());
        assertEquals(1.0, obstacle.getSpeed());
    }

    @Test
    void testMoveWithinBounds() {
        obstacle.act(0);
        assertEquals(101, obstacle.getX());
        assertEquals(200, obstacle.getY());
    }

    @Test
    void testResetPositionWhenOutOfBounds() {
        obstacle.setX(700);
        obstacle.act(0);
        assertTrue(obstacle.getX() <= 600);
    }
}
