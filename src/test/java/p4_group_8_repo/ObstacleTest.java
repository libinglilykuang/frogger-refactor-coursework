package p4_group_8_repo;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p4_group_8_repo.model.entities.Obstacle;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    private Obstacle obstacle;

    @BeforeEach
    void setUp() {
        obstacle = new Obstacle("/p4_group_8_repo/car.png", 100, 200, 5, 50, 50);
    }

    @Test
    void testInitialPosition() {
        assertEquals(100, obstacle.getX(), "Initial X position should be 100.");
        assertEquals(200, obstacle.getY(), "Initial Y position should be 200.");
    }

    @Test
    void testMoveRight() {
        double initialX = obstacle.getX();
        obstacle.act(0);
        assertEquals(initialX + 5, obstacle.getX(), "Obstacle should move right by speed.");
    }

    @Test
    void testMoveLeft() {
        // 测试向左移动逻辑
        obstacle = new Obstacle("/p4_group_8_repo/car.png", 100, 200, -5, 50, 50);
        double initialX = obstacle.getX();
        obstacle.act(0);
        assertEquals(initialX - 5, obstacle.getX(), "Obstacle should move left by speed.");
    }

    @Test
    void testRightBoundaryWrapping() {
        obstacle.setX(601);
        obstacle.act(0);
        assertEquals(-200, obstacle.getX(), "Obstacle should wrap to -200 when moving out of the right boundary.");
    }

    @Test
    void testLeftBoundaryWrapping() {
        obstacle = new Obstacle("/p4_group_8_repo/car.png", -51, 200, -5, 50, 50);
        obstacle.act(0);
        assertEquals(600, obstacle.getX(), "Obstacle should wrap to 600 when moving out of the left boundary.");
    }

    @Test
    void testImageInitialization() {
        Image image = obstacle.getImage();
        assertNotNull(image, "Obstacle should have an image assigned.");
    }
}
